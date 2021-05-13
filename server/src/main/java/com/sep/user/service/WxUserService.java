package com.sep.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.user.dto.AuthorizeUserInfoDto;
import com.sep.user.dto.IsAuthDto;
import com.sep.user.dto.LoginDto;
import com.sep.user.dto.WxUserPageSearchDto;
import com.sep.user.input.GetLowerInput;
import com.sep.user.input.GetUserByIdsInput;
import com.sep.user.input.GetUserInput;
import com.sep.user.input.StatisticalUserLowerByIdsInput;
import com.sep.user.model.WxUser;
import com.sep.user.output.LowerIdOutput;
import com.sep.user.output.StatisticalUserLowerRespOutput;
import com.sep.user.output.UserOutput;
import com.sep.user.vo.WxUserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tianyu
 * @since 2020-01-08
 */
public interface WxUserService extends IService<WxUser> {


    /**
     * 用户登录接口
     */
    String login(LoginDto loginDto, HttpServletRequest request);

    /**
     * 获取用户信息接口
     */
    Integer authorizeUserInfo(AuthorizeUserInfoDto authorizeUserInfoDto);

    /**
     * 获取用户手机号
     */
    Integer authorizeUserPhone(AuthorizeUserInfoDto authorizeUserInfoDto);


    String getUserPhoneNum(Integer userId);

    /**
     * 获取用户信息接口
     */
    Integer isAuthorizeUserInfo(IsAuthDto isAuthDto);

    /**
     * 获取用户手机号
     */
    Integer isAuthorizeUserPhone(IsAuthDto isAuthDto);


    /**
     * 内部服务 : 根据用户Id获取用户信息
     */
    UserOutput getUser(GetUserInput getUserInput);


    /**
     * 内部服务 : 根据用户Id获取上级用户
     */
    UserOutput getParentUser(GetUserInput getUserInput);

    /**
     * 内部服务 : 根据用户Id获取下级集合
     */
    Page<UserOutput> getLower1List(GetLowerInput input);

    /**
     * 内部服务 : 根据用户Id获取下下级集合
     */
    Page<UserOutput> getLower2List(GetLowerInput input);

    String getInviteQrCode(String token);

    /**
     * 获取用户的推广人数
     */
    Integer getExtendNum(String token);

    /**
     * 根据用户ID集合查询
     *
     * @param input ID集合
     * @return 用户信息集合
     */
    StatisticalUserLowerRespOutput statisticalUserLowerByIds(StatisticalUserLowerByIdsInput input);

    /**
     * 根据ID集合查询用户
     *
     * @param input ID集合
     * @return 用户信息集合
     */
    List<UserOutput> getUserByIds(GetUserByIdsInput input);


    /**
     * 分页查询
     *
     * @param dto 请求参数
     * @return 用户信息
     */
    IPage<WxUserVo> pageSearch(WxUserPageSearchDto dto);

    /**
     * 获取用户粉丝ID集合
     *
     * @param input 查询参数
     * @return ID集合
     */
    List<LowerIdOutput> getLowerIds(GetUserInput input);




}