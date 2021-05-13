package com.sep.sku.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.sep.common.utils.LocalDateTimeUtils;
import com.sep.content.dto.IdDto;
import com.sep.content.enums.CollectType;
import com.sep.content.service.CollectService;
import com.sep.point.service.PointService;
import com.sep.sku.bean.SkuParamInfo;
import com.sep.sku.bean.SkuPropertyInfo;
import com.sep.sku.bean.SkuPropertyValueInfo;
import com.sep.sku.enums.BizErrorCode;
import com.sep.sku.enums.DistributionType;
import com.sep.sku.enums.RecommendType;
import com.sep.sku.enums.SkuStatus;
import com.sep.sku.dto.*;
import com.sep.sku.model.PropertyValueDict;
import com.sep.sku.model.SkuInfo;
import com.sep.sku.model.SkuParam;
import com.sep.sku.model.SkuProperty;
import com.sep.sku.repository.SkuInfoMapper;
import com.sep.sku.service.*;
import com.sep.sku.tool.TencentCos;
import com.sep.sku.vo.SearchSkuRespVo;
import com.sep.common.enums.YesNo;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import com.sep.sku.vo.SimpleSkuInfoVo;
import com.sep.sku.vo.SkuInfoRespVo;
import com.sep.user.service.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
@Service
@Slf4j
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {

    @Autowired
    private SkuPropertyService skuPropertyService;

    @Autowired
    private SkuParamService skuParamService;

    @Autowired
    private PropertyValueDictService propertyValueDictService;

    @Autowired
    private TencentCos tencentCos;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private PointService pointService;

    @Autowired
    private FacilitatorService facilitatorService;

    @Autowired
    private CollectService collectService;

    @Value("${sku_type.rebate}")
    private Integer rebate;


    @Value("${sku_type.volume}")
    private Integer volume;

    @Value("${token.prefix}")
    private String tokenPrefix;

    @Autowired
    @Lazy
    private AsyncTask asyncTask;


    @Override
    @Transactional
    public int saveSkuInfo(SaveSkuDto saveSkuDto) {
        if (saveSkuDto == null) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品信息为空!");
        }
        SkuInfo skuInfo = new SkuInfo();
        LocalDateTime now = LocalDateTime.now();
        String adminUserName = ""; // todo 获取登录管理员用户名
        BeanUtils.copyProperties(saveSkuDto, skuInfo);
        skuInfo.setCreateTime(now);
        skuInfo.setCreateUid(adminUserName);
        if (!StringUtils.isEmpty(saveSkuDto.getSkuEndTime())) {
            LocalDateTime skuEndTiem = LocalDateTimeUtils.localTime(saveSkuDto.getSkuEndTime());
            skuInfo.setSkuEndTime(skuEndTiem);
        }
        if (!StringUtils.isEmpty(saveSkuDto.getPeriodTime())) {
            LocalDateTime periodTime = LocalDateTimeUtils.localTime(saveSkuDto.getPeriodTime());
            skuInfo.setPeriodTime(periodTime);
        }


        setSkuDistributionAndPictureInfo(skuInfo, saveSkuDto);

        int skuId = baseMapper.insert(skuInfo);
        if (skuId <= 0) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品表插入失败!");
        }
        saveSkuPropertyAndParamInfo(saveSkuDto, skuInfo.getId(), now, adminUserName);
        return skuId;
    }

    @Override
    @Transactional
    public boolean updateSkuInfo(SaveSkuDto saveSkuDto) {
        if (saveSkuDto == null) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品信息为空!");
        }
        if (saveSkuDto.getId() == null) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品ID为空!");
        }
        SkuInfo skuInfo = getById(saveSkuDto.getId());
        if (skuInfo == null) {
            throw new SepCustomException(ResponseData.SKU_NO_EXIST, "商品不存在!");
        }
        LocalDateTime now = LocalDateTime.now();
        String adminUserName = ""; // todo 获取登录管理员用户名
        BeanUtils.copyProperties(saveSkuDto, skuInfo);
        skuInfo.setUpdateTime(now);
        skuInfo.setUpdateUid(adminUserName);
        if (!StringUtils.isEmpty(saveSkuDto.getSkuEndTime())) {
            LocalDateTime skuEndTiem = LocalDateTimeUtils.localTime(saveSkuDto.getSkuEndTime());
            skuInfo.setSkuEndTime(skuEndTiem);
        }
        if (!StringUtils.isEmpty(saveSkuDto.getPeriodTime())) {
            LocalDateTime periodTime = LocalDateTimeUtils.localTime(saveSkuDto.getPeriodTime());
            skuInfo.setPeriodTime(periodTime);
        }
        setSkuDistributionAndPictureInfo(skuInfo, saveSkuDto);
        int updateResult = baseMapper.updateById(skuInfo);
        if (updateResult <= 0) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品表更新失败!");
        }
        //删除商品原有的属性和参数
        List<SkuProperty> skuPropertyList = skuPropertyService.findSkuPropertyListBySkuId(skuInfo.getId());
        if (!CollectionUtils.isEmpty(skuPropertyList)) {
            List<Integer> propertyIdList = skuPropertyList.stream().map(skuProperty -> skuProperty.getId()).collect(Collectors.toList());
            boolean removeProperty = skuPropertyService.removeByIds(propertyIdList);
            if (!removeProperty) {
                throw new SepCustomException(ResponseData.STATUS_CODE_400, "更新商品属性表失败!");
            }
        }
        List<SkuParam> skuParamList = skuParamService.findSkuParamListBySkuId(skuInfo.getId());
        if (!CollectionUtils.isEmpty(skuParamList)) {
            List<Integer> paramIdList = skuParamList.stream().map(skuParam -> skuParam.getId()).collect(Collectors.toList());
            boolean removeParam = skuParamService.removeByIds(paramIdList);
            if (!removeParam) {
                throw new SepCustomException(ResponseData.STATUS_CODE_400, "更新商品参数表失败!");
            }
        }
        // 添加新的属性和参数
        saveSkuPropertyAndParamInfo(saveSkuDto, skuInfo.getId(), now, adminUserName);
        return true;
    }

    @Override
    public IPage<SearchSkuRespVo> pageSearchSkuInfo(SearchSkuDto searchSkuDto) {

        IPage<SearchSkuRespVo> searchSkuRespVoPage = new Page<>();
        QueryWrapper<SkuInfo> skuInfoQueryWrapper = new QueryWrapper<>();
        if (searchSkuDto.getId() != null) {
            skuInfoQueryWrapper.eq("id", searchSkuDto.getId());
        }
        if (!StringUtils.isEmpty(searchSkuDto.getSkuName())) {
            skuInfoQueryWrapper.like("sku_name", searchSkuDto.getSkuName());
        }
        if (searchSkuDto.getCategoryId() != null) {
            skuInfoQueryWrapper.eq("category_id", searchSkuDto.getCategoryId());
        }
        if (searchSkuDto.getStatus() != null) {
            skuInfoQueryWrapper.eq("status", searchSkuDto.getStatus());
//            if (searchSkuDto.getStatus() == SkuStatus.SELL_OUT.getCode()) {
//                // 比较已售量>=库存量
//                skuInfoQueryWrapper.gt("stock_num", 0);
//                skuInfoQueryWrapper.apply("sale_num >= stock_num");
//            } else {
//                skuInfoQueryWrapper.eq("status", searchSkuDto.getStatus());
//                if (searchSkuDto.getStatus() == SkuStatus.ON_PUTAWAY.getCode()) {
//                    // 未设库存 or 比较已售量<库存量
//                    skuInfoQueryWrapper.and(Wrapper -> Wrapper.eq("stock_num", 0).or().apply("sale_num < stock_num"));
//                }
//            }
        }
        skuInfoQueryWrapper.orderByDesc("id");
        IPage<SkuInfo> skuInfoPage = baseMapper.selectPage(new Page<>(searchSkuDto.getCurrentPage(), searchSkuDto.getPageSize()), skuInfoQueryWrapper);
        BeanUtils.copyProperties(skuInfoPage, searchSkuRespVoPage);
        if (skuInfoPage != null && !CollectionUtils.isEmpty(skuInfoPage.getRecords())) {
            List<SearchSkuRespVo> searchSkuRespVoList = Lists.newArrayList();
            skuInfoPage.getRecords().forEach(e -> {
                SearchSkuRespVo searchSkuRespVo = packageSkuRespVoFormSkuInfo(e);
                if (searchSkuRespVo != null) {
                    searchSkuRespVoList.add(searchSkuRespVo);
                }
            });
            searchSkuRespVoPage.setRecords(searchSkuRespVoList);
        }
        return searchSkuRespVoPage;
    }

    @Override
    public List<SimpleSkuInfoVo> getSkuByOnPutaway() {
        List<SkuInfo> list = lambdaQuery().eq(SkuInfo::getStatus, SkuStatus.ON_PUTAWAY.getCode()).list();
        return list.stream().map(e -> {
            SimpleSkuInfoVo vo = new SimpleSkuInfoVo();
            BeanUtils.copyProperties(e, vo);
            if (!StringUtils.isEmpty(e.getSkuPictureUrl())) {
                vo.setSkuFirstPictureUrl(e.getSkuPictureUrl().split(",")[0]);
            }
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public IPage<SearchSkuRespVo> pageSearchSkuInfoByXcx(SearchSkuDto searchSkuDto) {

        IPage<SearchSkuRespVo> searchSkuRespVoPage = new Page<>();
        QueryWrapper<SkuInfo> skuInfoQueryWrapper = new QueryWrapper<>();
        if (searchSkuDto.getId() != null) {
            skuInfoQueryWrapper.eq("id", searchSkuDto.getId());
        }
        if (!StringUtils.isEmpty(searchSkuDto.getSkuName())) {
            skuInfoQueryWrapper.like("sku_name", searchSkuDto.getSkuName());
        }
        if (searchSkuDto.getCategoryId() != null) {
            skuInfoQueryWrapper.eq("category_id", searchSkuDto.getCategoryId());
        }

        List<Integer> statuss = new ArrayList<>();
        statuss.add(SkuStatus.ON_PUTAWAY.getCode());
        statuss.add(SkuStatus.SELL_OUT.getCode());
        statuss.add(SkuStatus.CLOSURE.getCode());
        skuInfoQueryWrapper.in("status", statuss);
//        if (searchSkuDto.getStatus() != null && searchSkuDto.getStatus() >= 0) {
//            if (searchSkuDto.getStatus() == SkuStatus.SELL_OUT.getCode()) {
//                // 比较已售量>=库存量
//                skuInfoQueryWrapper.gt("stock_num", 0);
//                skuInfoQueryWrapper.apply("sale_num >= stock_num");
//            } else {
//                skuInfoQueryWrapper.eq("status", searchSkuDto.getStatus());
//                if (searchSkuDto.getStatus() == SkuStatus.ON_PUTAWAY.getCode()) {
//                    // 未设库存 or 比较已售量<库存量
//                    skuInfoQueryWrapper.and(Wrapper -> Wrapper.eq("stock_num", 0).or().apply("sale_num < stock_num"));
//                }
//            }
//        }
        //  skuInfoQueryWrapper.orderByDesc("hot_sku");
        skuInfoQueryWrapper.orderByDesc("id");
        IPage<SkuInfo> skuInfoPage = baseMapper.selectPage(new Page<>(searchSkuDto.getCurrentPage(), searchSkuDto.getPageSize()), skuInfoQueryWrapper);
        BeanUtils.copyProperties(skuInfoPage, searchSkuRespVoPage);
        if (skuInfoPage != null && !CollectionUtils.isEmpty(skuInfoPage.getRecords())) {
            List<SearchSkuRespVo> searchSkuRespVoList = Lists.newArrayList();
            skuInfoPage.getRecords().forEach(e -> {
                SearchSkuRespVo searchSkuRespVo = packageSkuRespVoFormSkuInfo(e);
                if (searchSkuRespVo != null) {
                    searchSkuRespVoList.add(searchSkuRespVo);
                }
            });
            searchSkuRespVoPage.setRecords(searchSkuRespVoList);
        }
        return searchSkuRespVoPage;
    }

    @Override
    public int batchUpdateSkuStatus(UpdateSkuStatusDto updateSkuStatusDto) {
        if (SkuStatus.valueOf(updateSkuStatusDto.getStatus()) == null) {
            throw new SepCustomException(ResponseData.SKU_STATUS_ERROR, "商品状态错误");
        }
        List<SkuInfo> skuInfoList = baseMapper.selectBatchIds(updateSkuStatusDto.getIds());
        if (skuInfoList == null || skuInfoList.size() != updateSkuStatusDto.getIds().size()) {
            throw new SepCustomException(ResponseData.SKU_NO_EXIST, "商品ID有误");
        }
        if (updateSkuStatusDto.getStatus() == SkuStatus.ON_PUTAWAY.getCode()) {
            skuInfoList.forEach(e -> {
                if (e.getSkuEndTime() != null && e.getSkuEndTime().isBefore(LocalDateTime.now())) {
                    throw new SepCustomException(ResponseData.SKU_STATUS_ERROR, "请修改商品结束时间:商品Id" + e.getId());
                }
                if (e.getPeriodTime() != null && e.getPeriodTime().isBefore(LocalDateTime.now())) {
                    throw new SepCustomException(ResponseData.SKU_STATUS_ERROR, "请修改商品有效期:商品Id" + e.getId());
                }
                if (e.getStockNum() > 0 && e.getSaleNum() >= e.getStockNum()) {
                    throw new SepCustomException(ResponseData.SKU_STATUS_ERROR, "请修改库存数量,该商品已售罄:商品Id" + e.getId());
                }
            });


        }
        //TODO上架商品需要验证商品的结束时间
        skuInfoList.forEach(e -> {
            e.setStatus(updateSkuStatusDto.getStatus());
        });
        boolean result = updateBatchById(skuInfoList);
        return result ? 1 : 0;
    }

    @Override
    public SearchSkuRespVo getSkuInfoById(Integer id) {
        if (id == null) {
            return null;
        }
        SkuInfo skuInfo = getById(id);
        if (skuInfo == null) {
            return null;
        }
        return packageSkuRespVoFormSkuInfo(skuInfo);
    }

    @Override
    public SearchSkuRespVo getSkuInfoBySkuDetailDto(SkuDetailDto skuDetailDto) {
        log.info("调用商品详情页------->{}", skuDetailDto);
        if (skuDetailDto == null) {
            return null;
        }
        SkuInfo skuInfo = getById(skuDetailDto.getId());
        if (skuInfo == null) {
            return null;
        }
        SearchSkuRespVo searchSkuRespVo = packageSkuRespVoFormSkuInfo(skuInfo);
        searchSkuRespVo.setIsOnlyIntegralExchange(!skuInfo.getIsCashPay() && skuInfo.getIsIntegralExchange());
//        int userId = (int) JwtUtils.parseJWT(skuDetailDto.getToken()).get("id");
//        int userIntegralCount = pointService.current(userId);
        // searchSkuRespVo.setIsIntegralEnough(userIntegralCount >= skuInfo.getIntegralNum());
        return searchSkuRespVo;
    }

    @Override
    public List<SearchSkuRespVo> getSkuListByRecommendType(SearchRecommendSkuDto searchRecommendSkuDto) {
        if (searchRecommendSkuDto == null || searchRecommendSkuDto.getRecommendType() == null) {
            return Lists.newArrayList();
        }
        if (RecommendType.valueOf(searchRecommendSkuDto.getRecommendType()) == null) {
            return Lists.newArrayList();
        }
        QueryWrapper<SkuInfo> wrapper = new QueryWrapper<>();
        if (searchRecommendSkuDto.getRecommendType() == RecommendType.HOME_RECOMMEND.getCode()) {
            wrapper.eq("is_recommend_home", YesNo.YES.getCode());
        }
        if (searchRecommendSkuDto.getRecommendType() == RecommendType.HOT_SALE_RECOMMEND.getCode()) {
            wrapper.eq("is_recommend_hot", YesNo.YES.getCode());
        }
        if (searchRecommendSkuDto.getRecommendType() == RecommendType.SIDE_SHOW_RECOMMEND.getCode()) {
            wrapper.eq("is_recommend_side", YesNo.YES.getCode());
        }
        if (searchRecommendSkuDto.getStatus() != null && searchRecommendSkuDto.getStatus() >= 0) {
            if (searchRecommendSkuDto.getStatus() == SkuStatus.SELL_OUT.getCode()) {
                // 比较已售量>=库存量
                wrapper.gt("stock_num", 0);
                wrapper.apply("sale_num >= stock_num");
            } else {
                wrapper.eq("status", searchRecommendSkuDto.getStatus());
                if (searchRecommendSkuDto.getStatus() == SkuStatus.ON_PUTAWAY.getCode()) {
                    // 未设库存 or 比较已售量<库存量
                    wrapper.and(Wrapper -> Wrapper.eq("stock_num", 0).or().apply("sale_num < stock_num"));
                }
            }
        }
        wrapper.orderByDesc("id");
        wrapper.last("limit 0,4");
        List<SkuInfo> skuInfoList = baseMapper.selectList(wrapper);
        List<SearchSkuRespVo> skuRespVoList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(skuInfoList)) {
            skuInfoList.forEach(e -> {
                SearchSkuRespVo respVo = packageSkuRespVoFormSkuInfo(e);
                skuRespVoList.add(respVo);
            });

        }

        return skuRespVoList;
    }

    @Override
    public Integer deleteSkuInfo(Integer id) {
        if (id == null) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品ID为空!");
        }
        SkuInfo skuInfo = getById(id);
        if (skuInfo == null) {
            throw new SepCustomException(ResponseData.SKU_NO_EXIST, "商品不存在!");
        }
        if (skuInfo.getStatus() == SkuStatus.ON_PUTAWAY.getCode()) {
            throw new SepCustomException(BizErrorCode.SKU_IS_ONLINE);
        }
        boolean removeResult = removeById(id);
        if (!removeResult) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "删除商品失败!");
        }
        QueryWrapper<SkuProperty> removePropertyWrapper = new QueryWrapper<>();
        removePropertyWrapper.eq("sku_id", id);
        boolean removeProperty = skuPropertyService.remove(removePropertyWrapper);
        if (!removeProperty) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "删除商品失败!");
        }
        QueryWrapper<SkuParam> removeParamWrapper = new QueryWrapper<>();
        removeParamWrapper.eq("sku_id", id);
        boolean removeParam = skuParamService.remove(removeParamWrapper);
        if (!removeParam) {
            throw new SepCustomException(ResponseData.STATUS_CODE_400, "删除商品失败!");
        }
        return 1;
    }

    @Override
    public void skuSaleNumDeal(Map<Integer, Integer> skuBuyNumMap) {
        try {
            for (Integer skuId : skuBuyNumMap.keySet()) {
                SkuInfo skuInfo = getById(skuId);
                if (skuInfo != null) {
                    int result = updateSaleNumBySkuId(skuId, skuInfo.getSaleNum() == null ? 0 : skuInfo.getSaleNum() + skuBuyNumMap.get(skuId));
                    if (result <= 0) {
                        log.error("【skuSaleNumDeal】商品销量处理失败,skuId:{},salNum:{}", skuId, skuBuyNumMap.get(skuId));
                    }
                }
            }
        } catch (Exception e) {
            log.error("【skuSaleNumDeal】-> 商品销量处理发生异常， exception:{}", e);
        }

    }

    @Override
    public int statisticalSkuCount() {
        return baseMapper.statisticalSkuCount();
    }

    @Override
    public List<SkuInfoRespVo> getSkuListByIds(BatchSearchSkuInfoDto batchSearchSkuInfoDto) {
        log.info(">>>>>>>>getSkuListByIds,list:{}", JSON.toJSONString(batchSearchSkuInfoDto));
        List<SkuInfo> skuInfoList = (List<SkuInfo>) listByIds(batchSearchSkuInfoDto.getSkuIdList());
        if (CollectionUtils.isEmpty(skuInfoList)) {
            return Lists.newArrayList();
        }
        List<SkuInfoRespVo> skuInfoRespVoList = skuInfoList.stream().map(new Function<SkuInfo, SkuInfoRespVo>() {
            @Override
            public SkuInfoRespVo apply(SkuInfo skuInfo) {
                SkuInfoRespVo skuInfoRespVo = new SkuInfoRespVo();
                BeanUtils.copyProperties(skuInfo, skuInfoRespVo);
                if (!StringUtils.isEmpty(skuInfo.getSkuPictureUrl())) {
                    skuInfoRespVo.setSkuFirstPictureUrl(skuInfo.getSkuPictureUrl().split(",")[0]);
                }
                return skuInfoRespVo;
            }
        }).collect(Collectors.toList());
        return skuInfoRespVoList;
    }

    @Override
    public boolean isSkuOnlineStatus(Integer skuId) {
        if (skuId == null) {
            return false;
        }
        SkuInfo skuInfo = getById(skuId);
        if (skuInfo == null || skuInfo.getStatus() != SkuStatus.ON_PUTAWAY.getCode()) {
            return false;
        }
        return true;
    }

    @Override
    public List<SkuInfoRespVo> hotSkuList() {
        SearchRecommendSkuDto searchRecommendSkuDto = new SearchRecommendSkuDto();
        searchRecommendSkuDto.setRecommendType(RecommendType.HOT_SALE_RECOMMEND.getCode());
        searchRecommendSkuDto.setStatus(SkuStatus.ON_PUTAWAY.getCode());
        List<com.sep.sku.vo.SearchSkuRespVo> skuInfoList = getSkuListByRecommendType(searchRecommendSkuDto);
        if (CollectionUtils.isEmpty(skuInfoList)) {
            return Lists.newArrayList();
        }
        List<SkuInfoRespVo> skuInfoRespVoList = skuInfoList.stream().map(skuInfo -> {
            SkuInfoRespVo skuInfoRespVo = new SkuInfoRespVo();
            BeanUtils.copyProperties(skuInfo, skuInfoRespVo);
            return skuInfoRespVo;
        }).collect(Collectors.toList());
        return skuInfoRespVoList;
    }

    @Override
    public void taskUpdateSkuStatus() {
        List<SkuInfo> list = lambdaQuery().eq(SkuInfo::getStatus, SkuStatus.ON_PUTAWAY.getCode()).list();
        if (list != null && list.size() > 0) {
            list.forEach(item -> {
                if (item.getSkuEndTime() != null && item.getSkuEndTime().isBefore(LocalDateTime.now())) {
                    item.setStatus(SkuStatus.CLOSURE.getCode());
                    updateById(item);
                }
                if (item.getPeriodTime() != null && item.getPeriodTime().isBefore(LocalDateTime.now())) {
                    item.setStatus(SkuStatus.SOLD_OUT.getCode());
                    updateById(item);
                }
            });
        }
    }

    @Override
    public List<SkuInfo> getSkuByIds(List<Integer> skuIds) {
        if (CollectionUtils.isEmpty(skuIds))
            return null;

        return lambdaQuery().in(SkuInfo::getId, skuIds).list();
    }

    @Override
    public String generateShareQrCode(GenerateShareQrCodeDto generateShareQrCodeDto) {
        Integer userId = (Integer) JwtUtils.parseJWT(generateShareQrCodeDto.getToken()).get("id");
        return asyncTask.generateQRCodeBySku(userId, generateShareQrCodeDto.getSkuId());

    }

    private int updateSaleNumBySkuId(Integer skuId, int saleNum) {
        SkuInfo skuInfo = getById(skuId);
        if (skuInfo == null) {
            throw new SepCustomException(ResponseData.SKU_NO_EXIST, "商品不存在!");
        }
        LocalDateTime now = LocalDateTime.now();
        String adminUserName = ""; // todo 获取登录管理员用户名
        skuInfo.setUpdateTime(now);
        skuInfo.setUpdateUid(adminUserName);
        skuInfo.setSaleNum(saleNum);
        if (skuInfo.getStockNum() > 0 && skuInfo.getSaleNum() == skuInfo.getStockNum()) {
            skuInfo.setStatus(SkuStatus.SELL_OUT.getCode());
        }
        return baseMapper.updateById(skuInfo);
    }

    /**
     * 生成文件的名称
     *
     * @return
     */
    public static String getNewFileName(int userId) {
        return "sku_share_" + System.currentTimeMillis() + "_" + userId + ".png";
    }

    private SearchSkuRespVo packageSkuRespVoFormSkuInfo(SkuInfo skuInfo) {
        if (skuInfo == null) {
            return null;
        }
        SearchSkuRespVo skuRespVo = new SearchSkuRespVo();
        BeanUtils.copyProperties(skuInfo, skuRespVo);
        if (skuInfo.getSkuEndTime() != null) {
            Duration duration = Duration.between(LocalDateTime.now(), skuInfo.getSkuEndTime());
            skuRespVo.setMillis(duration.toMillis());
        }
        if (skuInfo.getCategoryId().equals(rebate)) {
            skuRespVo.setSkuType(1);
        }
        if (skuInfo.getCategoryId().equals(volume)) {
            skuRespVo.setSkuType(2);
        }
        if (!Objects.isNull(skuInfo.getFacilitatoId())) {
            IdDto idDto = new IdDto();
            idDto.setId(skuInfo.getFacilitatoId());
            FacilitatorDto facilitatorDto = facilitatorService.getFacilitatorDto(idDto);
            if (facilitatorDto != null) {
                skuRespVo.setFacilitatName(facilitatorDto.getFacilitatorName());
            }

        }
        Integer collectNum = collectService.getCollecNum(CollectType.SKU.getCode(), skuInfo.getId());
        skuRespVo.setCollectNum(collectNum == null ? 0 : collectNum);
        if (skuInfo.getStockNum() > 0 && skuInfo.getSaleNum() >= skuInfo.getStockNum()) {
            skuRespVo.setStatus(SkuStatus.SELL_OUT.getCode());
        }
        if (!StringUtils.isEmpty(skuInfo.getSkuPictureUrl())) {
            skuRespVo.setSkuPictureUrlList(Lists.newArrayList(skuInfo.getSkuPictureUrl().split(",")));
            skuRespVo.setSkuFirstPictureUrl(skuInfo.getSkuPictureUrl().split(",")[0]);
        }
        if (!StringUtils.isEmpty(skuInfo.getDistributionType())) {
            List<String> distributionTypeList = Lists.newArrayList();
            List<Integer> distributionTypeIdList = Lists.newArrayList();
            Lists.newArrayList(skuInfo.getDistributionType().split(",")).forEach(e -> {
                int distributionTypeId = Integer.parseInt(e);
                distributionTypeIdList.add(distributionTypeId);
                DistributionType distributionType = DistributionType.valueOf(distributionTypeId);
                if (distributionType != null) {
                    distributionTypeList.add(distributionType.getDescription());
                }
            });
            skuRespVo.setDistributionTypeList(distributionTypeList);
            skuRespVo.setDistributionTypeIdList(distributionTypeIdList);
        }
        // 查询商品属性
        List<SkuProperty> skuPropertyList = skuPropertyService.findSkuPropertyListBySkuId(skuInfo.getId());
        if (!CollectionUtils.isEmpty(skuPropertyList)) {
            List<SkuPropertyInfo> skuPropertyInfoList = Lists.newArrayList();
            skuPropertyList.forEach(e -> {
                SkuPropertyInfo skuPropertyInfo = new SkuPropertyInfo();
                BeanUtils.copyProperties(e, skuPropertyInfo);
                skuPropertyInfo.setSkuPropertyId(e.getId());
                List<SkuPropertyValueInfo> propertyValueInfoList = Lists.newArrayList();

                // 解析产品属性值集合
                if (!StringUtils.isEmpty(e.getPropertyValueDictId())) {
                    List<SkuPropertyValueInfo> dictPropertyValueInfoList =
                            Lists.newArrayList(e.getPropertyValueDictId().split(",")).stream().map(dictValueId -> {
                                SkuPropertyValueInfo skuPropertyValueInfo = new SkuPropertyValueInfo();
                                skuPropertyValueInfo.setPropertyValueDictId(Integer.parseInt(dictValueId));
                                // 查询自定义属性可选值表，获取自定义属性可选值名称
                                PropertyValueDict propertyValueDict = propertyValueDictService.getById(dictValueId);
                                if (propertyValueDict != null) {
                                    skuPropertyValueInfo.setPropertyValueName("");
                                }
                                return skuPropertyValueInfo;
                            }).collect(Collectors.toList());
                    propertyValueInfoList.addAll(dictPropertyValueInfoList);
                }
                if (!StringUtils.isEmpty(e.getPropertyValue())) {
                    List<SkuPropertyValueInfo> ownerPropertyValueInfoList =
                            Lists.newArrayList(e.getPropertyValue().split(",")).stream().map(ownerValue -> {
                                SkuPropertyValueInfo skuPropertyValueInfo = new SkuPropertyValueInfo();
                                skuPropertyValueInfo.setPropertyValueName(ownerValue);
                                return skuPropertyValueInfo;
                            }).collect(Collectors.toList());
                    propertyValueInfoList.addAll(ownerPropertyValueInfoList);
                }
                skuPropertyInfo.setPropertyValueList(propertyValueInfoList);
                skuPropertyInfoList.add(skuPropertyInfo);
            });
            skuRespVo.setSkuPropertyInfoList(skuPropertyInfoList);
        }
        // 查询商品参数
        List<SkuParam> skuParamList = skuParamService.findSkuParamListBySkuId(skuInfo.getId());
        if (!CollectionUtils.isEmpty(skuParamList)) {
            List<SkuParamInfo> skuParamInfoList = Lists.newArrayList();
            skuParamList.forEach(e -> {
                SkuParamInfo skuParamInfo = new SkuParamInfo();
                BeanUtils.copyProperties(e, skuParamInfo);
                skuParamInfoList.add(skuParamInfo);
            });
            skuRespVo.setSkuParamInfoList(skuParamInfoList);
        }
        return skuRespVo;
    }

    private void setSkuDistributionAndPictureInfo(SkuInfo skuInfo, SaveSkuDto saveSkuDto) {
        if (!CollectionUtils.isEmpty(saveSkuDto.getSkuPictureUrlList())) {
            skuInfo.setSkuPictureUrl(Strings.join(saveSkuDto.getSkuPictureUrlList(), ','));
        }
        if (!CollectionUtils.isEmpty(saveSkuDto.getDistributionTypeIdList())) {
            skuInfo.setDistributionType(Strings.join(saveSkuDto.getDistributionTypeIdList(), ','));
        }
    }

    private void saveSkuPropertyAndParamInfo(SaveSkuDto saveSkuDto, Integer skuId, LocalDateTime now, String adminUserName) {
        // 插入商品属性表
        if (!CollectionUtils.isEmpty(saveSkuDto.getSkuPropertySaveInfoList())) {
            List<SkuProperty> skuPropertyList = Lists.newArrayList();
            saveSkuDto.getSkuPropertySaveInfoList().forEach(e -> {
                if (CollectionUtils.isEmpty(e.getPropertyValue()) && CollectionUtils.isEmpty(e.getPropertyValueDictId())) {
                    return;
                }
                SkuProperty skuProperty = new SkuProperty();
                BeanUtils.copyProperties(e, skuProperty);
                if (!CollectionUtils.isEmpty(e.getPropertyValueDictId())) {
                    List<String> propertyValueDictIdStrList = e.getPropertyValueDictId().stream().filter(dictId -> dictId != null).map(dictId -> dictId.toString()).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(propertyValueDictIdStrList)) {
                        skuProperty.setPropertyValueDictId(Strings.join(propertyValueDictIdStrList, ','));
                    }
                }
                if (!CollectionUtils.isEmpty(e.getPropertyValue())) {
                    skuProperty.setPropertyValue(Strings.join(e.getPropertyValue(), ','));
                }
                skuProperty.setSkuId(skuId);
                skuProperty.setCreateTime(now);
                skuProperty.setCreateUid(adminUserName);
                skuPropertyList.add(skuProperty);
            });
            boolean skuPropertyBatchSaveResult = skuPropertyService.saveBatch(skuPropertyList);
            if (!skuPropertyBatchSaveResult) {
                throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品属性表插入失败!");
            }
        }
        // 插入商品参数表
        if (!CollectionUtils.isEmpty(saveSkuDto.getSkuParamInfoList())) {
            List<SkuParam> skuParamList = Lists.newArrayList();
            saveSkuDto.getSkuParamInfoList().forEach(e -> {
                SkuParam skuParam = new SkuParam();
                BeanUtils.copyProperties(e, skuParam);
                skuParam.setCreateTime(now);
                skuParam.setCreateUid(adminUserName);
                skuParam.setSkuId(skuId);
                skuParamList.add(skuParam);
            });
            boolean skuParamBatchSaveResult = skuParamService.saveBatch(skuParamList);
            if (!skuParamBatchSaveResult) {
                throw new SepCustomException(ResponseData.STATUS_CODE_400, "商品参数表插入失败!");
            }
        }
    }
}
