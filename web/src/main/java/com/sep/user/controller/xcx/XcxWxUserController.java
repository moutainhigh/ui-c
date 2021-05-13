package com.sep.user.controller.xcx;

import com.sep.common.controller.BaseController;
import com.sep.common.model.response.ResponseData;
import com.sep.common.utils.JwtUtils;
import com.sep.user.dto.AuthorizeUserInfoDto;
import com.sep.user.dto.IsAuthDto;
import com.sep.user.dto.LoginDto;
import com.sep.user.dto.TokenDto;
import com.sep.user.service.WxUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tianyu
 * @since 2020-01-07
 */
@RestController
@RequestMapping("/xcx/user")
@Api(value = "小程序用户相关API", tags = {"小程序用户相关API"})
public class XcxWxUserController extends BaseController {

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Value("${token.isShowZdw}")
    private String isShowZdw;

    @PostMapping(value = "/login")
    @ApiOperation(value = "登录凭证校验。通过 wx.login 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程", httpMethod = "POST")
    public ResponseData<String> login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        return ResponseData.OK(wxUserService.login(loginDto, request));
    }

    @PostMapping(value = "/authorizeUserInfo")
    @ApiOperation(value = "授权用户信息", httpMethod = "POST")
    public ResponseData<Integer> authorizeUserInfo(@RequestBody AuthorizeUserInfoDto authorizeUserInfoDto) {
        return ResponseData.OK(wxUserService.authorizeUserInfo(authorizeUserInfoDto));
    }

    @PostMapping(value = "/authorizeUserPhone")
    @ApiOperation(value = "授权用户手机号", httpMethod = "POST")
    public ResponseData<Integer> authorizeUserPhone(@RequestBody AuthorizeUserInfoDto authorizeUserInfoDto) {
        return ResponseData.OK(wxUserService.authorizeUserPhone(authorizeUserInfoDto));
    }

    @PostMapping(value = "/getUserPhoneNum")
    @ApiOperation(value = "获取用户手机号", httpMethod = "POST")
    public ResponseData<String> getUserPhoneNum(@RequestBody TokenDto tokenDto) {
        int userId = (int) JwtUtils.parseJWT(tokenDto.getToken()).get("id");
        return ResponseData.OK(wxUserService.getUserPhoneNum(userId));
    }

    @PostMapping(value = "/isAuthorizeUserInfo")
    @ApiOperation(value = "是否授权用户信息", httpMethod = "POST")
    public ResponseData<Integer> isAuthorizeUserInfo(@RequestBody IsAuthDto isAuthDto) {
        return ResponseData.OK(wxUserService.isAuthorizeUserInfo(isAuthDto));
    }

    @PostMapping(value = "/isAuthorizeUserPhone")
    @ApiOperation(value = "是否授权用户手机号", httpMethod = "POST")
    public ResponseData<Integer> isAuthorizeUserPhone(@RequestBody IsAuthDto isAuthDto) {
        return ResponseData.OK(wxUserService.isAuthorizeUserPhone(isAuthDto));
    }

    @PostMapping(value = "/getInviteQrCode")
    @ApiOperation(value = "获取用户邀请二维码", httpMethod = "POST")
    public ResponseData<Integer> getInviteQrCode(@RequestBody TokenDto tokenDto) {
        return ResponseData.OK(wxUserService.getInviteQrCode(tokenDto.getToken()));
    }


    @PostMapping(value = "/getExtendNum")
    @ApiOperation(value = "获取总的推广人数（一级+二级）", httpMethod = "POST")
    public ResponseData<Integer> getExtendNum(@RequestBody TokenDto tokenDto) {
        return ResponseData.OK(wxUserService.getExtendNum(tokenDto.getToken()));
    }

    @PostMapping(value = "/getUserId")
    @ApiOperation(value = "token转ID", httpMethod = "POST")
    public ResponseData<Integer> getUserId(@RequestBody TokenDto tokenDto) {
        return ResponseData.OK(Integer.parseInt(JwtUtils.parseJWT(tokenDto.getToken()).get("id").toString()));
    }


    @PostMapping(value = "/getIsShowZdw")
    @ApiOperation(value = "获取是否显示折到位按钮当前状态", httpMethod = "POST")
    public ResponseData<String> getIsShowZdw() {
        return ResponseData.OK(redisTemplate.opsForValue().get(isShowZdw));
    }


}
