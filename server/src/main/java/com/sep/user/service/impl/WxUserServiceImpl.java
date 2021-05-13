package com.sep.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.http.HttpClient4;
import com.sep.common.utils.AESUtil;
import com.sep.common.utils.JwtUtils;
import com.sep.point.dto.PointIncreaseInput;
import com.sep.point.service.PointService;
import com.sep.sku.bean.StatisticalOrderInfo;
import com.sep.sku.dto.StatisticalOrderDto;
import com.sep.sku.service.OrderService;
import com.sep.sku.vo.StatisticalOrderRespVo;
import com.sep.user.dto.AuthorizeUserInfoDto;
import com.sep.user.dto.IsAuthDto;
import com.sep.user.dto.LoginDto;
import com.sep.user.dto.WxUserPageSearchDto;
import com.sep.user.enums.BizErrorCode;
import com.sep.user.input.GetLowerInput;
import com.sep.user.input.GetUserByIdsInput;
import com.sep.user.input.GetUserInput;
import com.sep.user.input.StatisticalUserLowerByIdsInput;
import com.sep.user.model.WxUser;
import com.sep.user.output.LowerIdOutput;
import com.sep.user.output.StatisticalUserLowerOutput;
import com.sep.user.output.StatisticalUserLowerRespOutput;
import com.sep.user.output.UserOutput;
import com.sep.user.repository.WxUserMapper;
import com.sep.user.service.AsyncTask;
import com.sep.user.service.WxUserService;
import com.sep.user.vo.WxUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tianyu
 * @since 2020-01-08
 */
@Service
@Slf4j
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements WxUserService {


    @Value("${wx.grant_type}")
    private String grant_type;

    @Value("${wx.appId}")
    private String appId;


    @Value("${wx.appSecret}")
    private String appSecret;

    @Value("${wx.jscode2session}")
    private String jscode2session;

    @Value("${point}")
    private Integer point;


    @Autowired
    private AsyncTask asyncTask;

    @Resource
    private WxUserMapper wxUserMapper;

    @Resource
    private OrderService orderService;

    @Autowired
    private PointService pointService;

    @Override
    public String login(LoginDto loginDto, HttpServletRequest request) {

        String result = null;
        String url = jscode2session.replaceAll("APPID", appId).
                replaceAll("SECRET", appSecret).replaceAll("JSCODE", loginDto.getCode()).
                replaceAll("authorization_code", grant_type);
        String userCodeJson = HttpClient4.doGet(url);
        JSONObject jsonObject = JSONObject.parseObject(userCodeJson);
        String session_key = jsonObject.getString("session_key");
        String openid = jsonObject.getString("openid");
        String unionid = jsonObject.getString("unionId");
        if (StringUtils.isNotBlank(session_key) && StringUtils.isNotBlank(openid)) {
            List<WxUser> users = lambdaQuery().eq(WxUser::getOpenid, openid).list();
            if (!users.isEmpty()) {
                WxUser wxUser = users.get(0);
                wxUser.setSessionKey(session_key);
                if (isInterval(wxUser.getTs())) {

                    this.increase(wxUser.getId(), 1);
                }
                if (StringUtils.isNotBlank(loginDto.getTelnum())) {
                    wxUser.setTelnum(loginDto.getTelnum());
                }
                wxUser.setTs(LocalDateTime.now());
                wxUser.updateById();
                result = JwtUtils.createJWT(wxUser.getId(), wxUser.getOpenid(), wxUser.getUnionid());
                asyncTask.synchronousAreaCode(wxUser.getId(), request);
            } else {
                WxUser user = new WxUser();
                user.setSessionKey(session_key);
                user.setOpenid(openid);
                user.setUnionid(unionid);
                if (loginDto.getInviteParentId() != null && loginDto.getInviteParentId() > 0) {
                    user.setInviteParentId(loginDto.getInviteParentId());
                    this.increase(loginDto.getInviteParentId(), 2);
                }
                user.setAvatarurl("https://sep-1255938217.cos.ap-beijing.myqcloud.com/about_us_top.png");
                user.setNickname(this.getRandomNum());
                user.setTs(LocalDateTime.now());
                if (StringUtils.isNotBlank(loginDto.getTelnum())) {
                    user.setTelnum(loginDto.getTelnum());
                }
                user.insert();
                this.increase(user.getId(), 1);
                //生成用户邀请码
                asyncTask.generateQRCode(user.getId());
                //获取用户位置
                asyncTask.synchronousAreaCode(user.getId(), request);
                //申请分销身份
                asyncTask.apply(user.getId());
                result = JwtUtils.createJWT(user.getId(), user.getOpenid(), user.getUnionid());
            }
        }
        log.info("token--------------{}", result);
        return result;
    }

    private void increase(Integer userId, Integer type) {
        log.info("是否给积分--------------{}", point);
        if (point > 0) {
            PointIncreaseInput pointIncreaseInput = new PointIncreaseInput();
            pointIncreaseInput.setUserId(userId);
            pointIncreaseInput.setFundChangeType(type);
            pointService.increase(pointIncreaseInput);
        }
    }

    private String getRandomNum() {
        int max = 99999, min = 10000;
        long randomNum = System.currentTimeMillis();
        int ran3 = (int) (randomNum % (max - min) + min);
        return String.valueOf(ran3);

    }

    @Override
    public Integer authorizeUserInfo(AuthorizeUserInfoDto authorizeUserInfoDto) {
        Integer result = 0;
        String userId = JwtUtils.parseJWT(authorizeUserInfoDto.getToken()).get("id").toString();
        if (StringUtils.isNotBlank(userId)) {
            WxUser user = getById(Integer.parseInt(userId));
            if (user != null) {
                JSONObject json = AESUtil.decryptionWxInfo(user.getSessionKey(), authorizeUserInfoDto.getEncryptedData(), authorizeUserInfoDto.getIv());
                if (json == null) {
                    throw new SepCustomException(BizErrorCode.LOGIN_OVERDUE);
                }
                user.setOpenid(json.getString("openId"));
                user.setUnionid(json.getString("unionId"));
                user.setNickname(json.getString("nickName"));
                user.setAvatarurl(json.getString("avatarUrl"));
                user.setGender(json.getInteger("gender"));
                user.setCountry(json.getString("country"));
                user.setProvince(json.getString("province"));
                user.setCity(json.getString("city"));
                user.setLanguage(json.getString("language"));
                user.setIsAuthUserinfo(1);
                result = user.updateById() ? 1 : 0;
                this.increase(user.getId(), 0);

            }
        }
        return result;
    }

    @Override
    public Integer authorizeUserPhone(AuthorizeUserInfoDto authorizeUserInfoDto) {
        String userId = JwtUtils.parseJWT(authorizeUserInfoDto.getToken()).get("id").toString();
        if (StringUtils.isNotBlank(userId)) {
            WxUser user = getById(Integer.parseInt(userId));
            if (user != null) {
                JSONObject json = AESUtil.decryptionWxInfo(user.getSessionKey(), authorizeUserInfoDto.getEncryptedData(), authorizeUserInfoDto.getIv());
                if (json == null) {
                    throw new SepCustomException(BizErrorCode.LOGIN_OVERDUE);
                }
                user.setTelnum(json.getString("purePhoneNumber"));
                return user.updateById() ? 1 : 0;
            }
        }
        return null;
    }

    @Override
    public Integer isAuthorizeUserInfo(IsAuthDto isAuthDto) {
        String userId = JwtUtils.parseJWT(isAuthDto.getToken()).get("id").toString();
        if (StringUtils.isNotBlank(userId)) {
            WxUser user = getById(Integer.parseInt(userId));
            return user.getIsAuthUserinfo();
        }

        return null;
    }

    @Override
    public String getUserPhoneNum(Integer userId) {
        if (!Objects.isNull(userId) && userId > 0) {
            WxUser user = getById(userId);
            if (StringUtils.isNotBlank(user.getTelnum())) {
                return user.getTelnum();
            }
        }
        return null;
    }


    @Override
    public Integer isAuthorizeUserPhone(IsAuthDto isAuthDto) {
        String userId = JwtUtils.parseJWT(isAuthDto.getToken()).get("id").toString();
        if (StringUtils.isNotBlank(userId)) {
            WxUser user = getById(Integer.parseInt(userId));
            if (StringUtils.isNotBlank(user.getTelnum())) {
                return 1;
            } else {
                return -1;
            }
        }
        return null;
    }

    @Override
    public UserOutput getUser(GetUserInput getUserInput) {
        UserOutput userOutput = new UserOutput();
        LambdaQueryWrapper<WxUser> lambda = new QueryWrapper<WxUser>().lambda();
        if (Objects.nonNull(getUserInput.getUserId())) {
            lambda.eq(WxUser::getId, getUserInput.getUserId());
        }
        if (StringUtils.isNotEmpty(getUserInput.getTelnum())) {
            lambda.eq(WxUser::getTelnum, getUserInput.getTelnum());
        }
        WxUser user = getOne(lambda);
        if (Objects.isNull(user)) {
            return null;
        }
        BeanUtils.copyProperties(user, userOutput);
        return userOutput;
    }

    @Override
    public UserOutput getParentUser(GetUserInput getUserInput) {
        UserOutput userOutput = new UserOutput();
        WxUser user = getById(getUserInput.getUserId());
        if (user == null) {
            return null;
        }
        WxUser result = getById(user.getInviteParentId());
        if (result == null) {
            return null;
        }
        BeanUtils.copyProperties(result, userOutput);
        return userOutput;
    }

    @Override
    public Page<UserOutput> getLower1List(GetLowerInput input) {
        Page<UserOutput> result = new Page<>();
        //构建查询条件
        LambdaQueryWrapper<WxUser> lambda = new QueryWrapper<WxUser>().lambda();
        lambda.eq(WxUser::getInviteParentId, input.getUserId());
        if (CollectionUtils.isNotEmpty(input.getLowerIds())) {
            lambda.in(WxUser::getId, input.getLowerIds());
        }
        if (StringUtils.isNotEmpty(input.getLowerTelnum())) {
            lambda.eq(WxUser::getTelnum, input.getLowerTelnum());
        }

        //执行查询
        IPage<WxUser> page = page(new Page<>(input.getCurrentPage(), input.getPageSize()), lambda);
        BeanUtils.copyProperties(page, result);
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return result;
        }

        //统计一级粉丝数
        List<Integer> ids = page.getRecords().stream().map(WxUser::getId).collect(Collectors.toList());
        Map<Integer, Integer> countLower1Number = countLower1Number(ids);
        //结果转换
        List<UserOutput> userOutputs = page.getRecords().stream().map(e -> {
            UserOutput output = new UserOutput();
            BeanUtils.copyProperties(e, output);
            output.setLower1Count(countLower1Number.getOrDefault(e.getId(), 0));
            return output;
        }).collect(Collectors.toList());
        result.setRecords(userOutputs);
        return result;
    }

    @Override
    public Page<UserOutput> getLower2List(GetLowerInput input) {
        Page<UserOutput> result = new Page<>();
        List<Integer> userIds = lambdaQuery().eq(WxUser::getInviteParentId, input.getUserId()).list().stream()
                .map(WxUser::getId).collect(Collectors.toList());
        if (userIds.isEmpty()) {
            return result;
        }

        LambdaQueryWrapper<WxUser> lambda = new QueryWrapper<WxUser>().lambda();
        lambda.in(WxUser::getInviteParentId, userIds);
        if (CollectionUtils.isNotEmpty(input.getLowerIds())) {
            lambda.in(WxUser::getId, input.getLowerIds());
        }
        if (StringUtils.isNotEmpty(input.getLowerTelnum())) {
            lambda.eq(WxUser::getTelnum, input.getLowerTelnum());
        }
        IPage<WxUser> page = page(new Page<>(input.getCurrentPage(), input.getPageSize()), lambda);
        List<UserOutput> userOutputs = page.getRecords().stream().map(e -> {
            UserOutput output = new UserOutput();
            BeanUtils.copyProperties(e, output);
            return output;
        }).collect(Collectors.toList());
        BeanUtils.copyProperties(page, result);
        result.setRecords(userOutputs);
        return result;
    }

    @Override
    public String getInviteQrCode(String token) {

        String userId = JwtUtils.parseJWT(token).get("id").toString();
        if (StringUtils.isNotBlank(userId)) {
            WxUser user = getById(Integer.parseInt(userId));
            return user.getInviteQrCode();
        }
        return null;
    }

    @Override
    public Integer getExtendNum(String token) {
        String userId = JwtUtils.parseJWT(token).get("id").toString();
        Integer result = 0;
        if (StringUtils.isNotBlank(userId)) {
            List<Integer> level1Ids = lambdaQuery().eq(WxUser::getInviteParentId, Integer.parseInt(userId)).list().stream()
                    .map(WxUser::getId).collect(Collectors.toList());
            if (!level1Ids.isEmpty()) {
                result += level1Ids.size();
                result += lambdaQuery().in(WxUser::getInviteParentId, level1Ids).count();
            }
        }
        return result;
    }

    @Override
    public StatisticalUserLowerRespOutput statisticalUserLowerByIds(StatisticalUserLowerByIdsInput input) {
        Map<Integer, Integer> countLower1Number = input.isCountLower1() ?
                countLower1Number(input.getUserIds()) : Collections.EMPTY_MAP;
        Map<Integer, Integer> countLower2Number = input.isCountLower2() ?
                countLower2Number(input.getUserIds()) : Collections.EMPTY_MAP;

        List<WxUser> wxUsers = lambdaQuery().in(WxUser::getId, input.getUserIds()).list();
        Map<Integer, StatisticalUserLowerOutput> statisticalMap = wxUsers
                .stream().map(e -> {
                    StatisticalUserLowerOutput output = new StatisticalUserLowerOutput();
                    output.setId(e.getId());
                    output.setNickname(e.getNickname());
                    output.setLower1Count(countLower1Number.getOrDefault(e.getId(), 0));
                    output.setLower2Count(countLower2Number.getOrDefault(e.getId(), 0));
                    output.setInviteParentId(e.getInviteParentId());
                    output.setQuickMark(e.getInviteQrCode());
                    output.setTelnum(e.getTelnum());
                    return output;
                }).collect(Collectors.toMap(StatisticalUserLowerOutput::getId, e -> e));
        StatisticalUserLowerRespOutput result = new StatisticalUserLowerRespOutput();
        result.setStatisticalMap(statisticalMap);
        return result;
    }

    @Override
    public List<UserOutput> getUserByIds(GetUserByIdsInput input) {
        return listByIds(input.getUserIds()).stream().map(e -> {
            UserOutput output = new UserOutput();
            BeanUtils.copyProperties(e, output);
            return output;
        }).collect(Collectors.toList());
    }

    @Override
    public IPage<WxUserVo> pageSearch(WxUserPageSearchDto dto) {
        IPage<WxUserVo> result = new Page<>();
        LambdaQueryWrapper<WxUser> lambda = new QueryWrapper<WxUser>().lambda();
        lambda.orderByDesc(WxUser::getCreateTime);
        if (StringUtils.isNotEmpty(dto.getNickname())) {
            lambda.eq(WxUser::getNickname, dto.getNickname());
        }
        if (StringUtils.isNotEmpty(dto.getTelnum())) {
            lambda.eq(WxUser::getTelnum, dto.getTelnum());
        }
        IPage<WxUser> page = page(new Page<>(dto.getCurrentPage(), dto.getPageSize()), lambda);
        BeanUtils.copyProperties(page, result);
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return result;
        }
        result.setRecords(userVoConvertor(page.getRecords()));
        return result;
    }

    @Override
    public List<LowerIdOutput> getLowerIds(GetUserInput input) {
        //查询一级
        LambdaQueryWrapper<WxUser> lower1Lambda = new QueryWrapper<WxUser>().lambda();
        lower1Lambda.select(WxUser::getId);
        lower1Lambda.eq(WxUser::getInviteParentId, input.getUserId());
        List<Integer> lower1Ids = list(lower1Lambda).stream().map(WxUser::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(lower1Ids)) {
            return Collections.emptyList();
        }

        List<LowerIdOutput> lower1 = lower1Ids.stream().map(id -> {
            LowerIdOutput output = new LowerIdOutput();
            output.setId(id);
            output.setFansRank(0);
            return output;
        }).collect(Collectors.toList());

        //查询二级
        LambdaQueryWrapper<WxUser> lower2Lambda = new QueryWrapper<WxUser>().lambda();
        lower2Lambda.select(WxUser::getId);
        lower2Lambda.in(WxUser::getInviteParentId, lower1Ids);
        List<Integer> lower2Ids = list(lower2Lambda).stream().map(WxUser::getId).collect(Collectors.toList());

        List<LowerIdOutput> lower2 = lower2Ids.stream().map(id -> {
            LowerIdOutput output = new LowerIdOutput();
            output.setId(id);
            output.setFansRank(1);
            return output;
        }).collect(Collectors.toList());

        List<LowerIdOutput> result = Lists.newArrayList();
        result.addAll(lower1);
        result.addAll(lower2);
        return result;
    }

    private Map<Integer, Integer> countLower1Number(List<Integer> userIds) {
        LambdaQueryWrapper<WxUser> lambda = new QueryWrapper<WxUser>().lambda();
        lambda.in(WxUser::getInviteParentId, userIds);
        lambda.groupBy(WxUser::getInviteParentId);
        List<Map<String, Number>> countLower1Number = wxUserMapper.countLower1Number(lambda);
        return countLower1Number.stream()
                .collect(Collectors.toMap(map -> map.get("invite_parent_id").intValue(),
                        map -> map.get("count").intValue()));
    }

    private Map<Integer, Integer> countLower2Number(List<Integer> userIds) {
        LambdaQueryWrapper<WxUser> lambda = new QueryWrapper<WxUser>().lambda();
        lambda.in(WxUser::getInviteParentId, userIds);
        List<Map<String, Number>> countLower1Number = wxUserMapper.countLower2Number(lambda);
        return countLower1Number.stream()
                .collect(Collectors.toMap(map -> map.get("invite_parent_id").intValue(),
                        map -> map.get("count").intValue()));
    }

    private List<WxUserVo> userVoConvertor(List<WxUser> users) {
        List<Integer> userIds = users.stream().map(WxUser::getId).collect(Collectors.toList());
        StatisticalOrderDto orderDto = new StatisticalOrderDto();
        orderDto.setUserIdList(userIds);
        StatisticalOrderRespVo respVo = orderService.statisticalOrder(orderDto);
        Map<Integer, StatisticalOrderInfo> orderInfoMap = respVo.getStatisticalOrderMap();

        return users.stream().map(wxUser -> {
            WxUserVo vo = new WxUserVo();
            BeanUtils.copyProperties(wxUser, vo);
            if (!CollectionUtils.isEmpty(orderInfoMap)
                    && orderInfoMap.containsKey(wxUser.getId())) {
                StatisticalOrderInfo orderInfo = orderInfoMap.get(wxUser.getId());
                vo.setMonetary(null == orderInfo.getTotalAmount() ? BigDecimal.ZERO : orderInfo.getTotalAmount());
                vo.setOrderCount(orderInfo.getTotalNum());
            }
            return vo;
        }).collect(Collectors.toList());
    }

    public Boolean isInterval(LocalDateTime current) {
        LocalDateTime today_start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime today_end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        return today_start.isBefore(current) && today_end.isAfter(current);

    }

}