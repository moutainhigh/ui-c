package com.sep.sku.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.sku.dto.*;
import com.sep.sku.model.SkuInfo;
import com.sep.sku.vo.SearchSkuRespVo;
import com.sep.sku.vo.SimpleSkuInfoVo;
import com.sep.sku.vo.SkuInfoRespVo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author zhangkai
 * @since 2019-12-28
 */
public interface SkuInfoService extends IService<SkuInfo> {

    /**
     * 创建商品信息
     * @param saveSkuDto
     * @return
     */
    int saveSkuInfo(SaveSkuDto saveSkuDto);

    /**
     * 修改商品信息
     * @param saveSkuDto
     * @return
     */
    boolean updateSkuInfo(SaveSkuDto saveSkuDto);

    /**
     * 分页查询SKU信息
     * @param searchSkuDto
     * @return
     */
    IPage<SearchSkuRespVo> pageSearchSkuInfo(SearchSkuDto searchSkuDto);

    /**
     * 查询全部商品上架中的商品
     * @param
     * @return
     */
    List<SimpleSkuInfoVo> getSkuByOnPutaway();

    /**
     * 小程序调用分页查询SKU信息
     * @param searchSkuDto
     * @return
     */
    IPage<SearchSkuRespVo> pageSearchSkuInfoByXcx(SearchSkuDto searchSkuDto);

    /**
     * 批量修改商品状态
     * @param updateSkuStatusDto
     * @return
     */
    int batchUpdateSkuStatus(UpdateSkuStatusDto updateSkuStatusDto);

    /**
     * 根据id查询商品详情
     * @param id
     * @return
     */
    SearchSkuRespVo getSkuInfoById(Integer id);

    /**
     * 根据id和用户查询商品详情
     * @param skuDetailDto
     * @return
     */
    SearchSkuRespVo getSkuInfoBySkuDetailDto(SkuDetailDto skuDetailDto);

    /**
     * 获取推荐商品
     * @param searchRecommendSkuDto 推荐类型
     * @return
     */
    List<SearchSkuRespVo> getSkuListByRecommendType(SearchRecommendSkuDto searchRecommendSkuDto);

    /**
     * 删除商品
     * @param id
     * @return
     */
    Integer deleteSkuInfo(Integer id);

    /**
     * 生成商品分享二维码
     * @param generateShareQrCodeDto
     * @return
     */
    String generateShareQrCode(GenerateShareQrCodeDto generateShareQrCodeDto);

    /**
     * 批量处理商品销量
     * @param skuBuyNumMap key：商品id，value：当前购买的商品数量
     */
    void skuSaleNumDeal(Map<Integer, Integer> skuBuyNumMap);


    /**
     * 统计上架的商品总量
     * @return
     */
    int statisticalSkuCount();


    List<SkuInfoRespVo> getSkuListByIds(BatchSearchSkuInfoDto batchSearchSkuInfoDto);

    boolean isSkuOnlineStatus(Integer skuId);

    List<SkuInfoRespVo> hotSkuList();

    /**
     * 定时任务更新商品状态
     * */

    void taskUpdateSkuStatus();


    List<SkuInfo> getSkuByIds(List<Integer> skuIds);



}
