package com.sep.admin.controller.background;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.admin.dto.*;
import com.sep.admin.model.Employee;
import com.sep.admin.service.EmployeeService;
import com.sep.admin.service.RoleService;
import com.sep.admin.utils.TokenUtils;
import com.sep.admin.vo.SearchEmployeeRespVo;
import com.sep.admin.vo.SearchRoleRespVo;
import com.sep.admin.vo.SysUserLoginRespVo;
import com.sep.common.model.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangkai
 * @since 2020-02-06
 */
@RestController
@RequestMapping("/background/employee")
@Api(value = "员工相关API", tags = {"员工相关API"})
@Slf4j
public class BackEmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Value("${token.isShowZdw}")
    private String isShowZdw;

    @PostMapping(value = "/pageSearchEmployeeInfo")
    @ApiOperation(value = "分页查询员工信息", httpMethod = "POST")
    public ResponseData<IPage<SearchEmployeeRespVo>> pageSearchEmployeeInfo(@RequestBody SearchEmployeeDto searchEmployeeDto) {
        IPage<SearchEmployeeRespVo> employeeInfoPage = employeeService.pageSearchEmployeeInfo(searchEmployeeDto);
        return ResponseData.OK(employeeInfoPage);
    }

    @PostMapping(value = "/searchEmployeeInfo")
    @ApiOperation(value = "查询员工信息", httpMethod = "POST")
    public ResponseData<SearchEmployeeRespVo> searchEmployeeInfo(@RequestBody IdDto idDto) {
        SearchEmployeeRespVo searchEmployeeRespVo = employeeService.searchEmployeeInfo(idDto.getId());
        return ResponseData.OK(searchEmployeeRespVo);
    }

    @PostMapping(value = "/saveEmployeeInfo")
    @ApiOperation(value = "保存员工信息", httpMethod = "POST")
    public ResponseData saveEmployeeInfo(@RequestBody SaveEmployeeDto saveEmployeeDto,
                                         HttpServletRequest request) {
        int result = employeeService.saveEmployeeInfo(saveEmployeeDto, request);
        return result > 0 ? ResponseData.OK() : ResponseData.ERROR("保存员工失败");
    }

    @PostMapping(value = "/updateEmployeeInfo")
    @ApiOperation(value = "修改员工信息", httpMethod = "POST")
    public ResponseData updateEmployeeInfo(@RequestBody UpdateEmployeeDto updateEmployeeDto) {
        int result = employeeService.updateEmployeeInfo(updateEmployeeDto);
        return result > 0 ? ResponseData.OK() : ResponseData.ERROR("修改员工失败");
    }

    @PostMapping(value = "/deleteEmployeeInfo")
    @ApiOperation(value = "删除员工信息", httpMethod = "POST")
    public ResponseData deleteEmployeeInfo(@RequestBody IdDto idDto) {
        boolean result = employeeService.removeById(idDto.getId());
        return result ? ResponseData.OK() : ResponseData.ERROR("删除员工失败");
    }

    @RequestMapping(value = "/login")
    @ApiOperation(value = "系统用户登录", httpMethod = "POST")
    public ResponseData<SysUserLoginRespVo> sysUserLogin(@RequestBody SysUserLoginDto sysUserLoginDto,
                                                         HttpServletResponse response) {
        log.debug(">>>>>sysUserLogin,sysUserLoginDto:{}", JSON.toJSONString(sysUserLoginDto));
        Employee employee = employeeService.userNameLogin(sysUserLoginDto.getKeyword(), sysUserLoginDto.getPassWord());
        SearchRoleRespVo roleRespVo = roleService.searchRoleInfoById(employee.getEmployeeRoleid());
        String token = tokenUtils.create(employee.getId());
        Cookie cookie = new Cookie("Authentication", token);
        //将cookie对象添加到response对象中，这样服务器在输出response对象中的内容时就会把cookie也输出到客户端浏览器
        cookie.setPath("/");
        response.addCookie(cookie);
        SysUserLoginRespVo sysUserLoginRespVo = new SysUserLoginRespVo();
        sysUserLoginRespVo.setPermissionIdList(roleRespVo.getPermissionIdList());
        sysUserLoginRespVo.setToken(token);
        return ResponseData.OK(sysUserLoginRespVo);

    }

    @PostMapping(value = "/getIsShowZdw")
    @ApiOperation(value = "获取是否显示折到位按钮当前状态", httpMethod = "POST")
    public ResponseData<String> getIsShowZdw() {
        return ResponseData.OK(redisTemplate.opsForValue().get(isShowZdw));
    }

    @GetMapping(value = "/setIsShowZdw/{status}")
    @ApiOperation(value = "设置折到位按钮状态:1显示，0不显示", httpMethod = "GET")
    public ResponseData<String> setIsShowZdw(@PathVariable Integer status) {
        redisTemplate.opsForValue().set(isShowZdw, String.valueOf(status));
        return ResponseData.OK();
    }


}
