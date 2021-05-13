package com.sep.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.admin.dto.SaveEmployeeDto;
import com.sep.admin.dto.SearchEmployeeDto;
import com.sep.admin.dto.UpdateEmployeeDto;
import com.sep.admin.model.Employee;
import com.sep.admin.vo.SearchEmployeeRespVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 员工信息表 服务类
 * </p>
 *
 * @author zhangkai
 * @since 2020-02-21
 */
public interface EmployeeService extends IService<Employee> {

    IPage<SearchEmployeeRespVo> pageSearchEmployeeInfo(SearchEmployeeDto searchEmployeeDto);

    SearchEmployeeRespVo searchEmployeeInfo(int id);

    int saveEmployeeInfo(SaveEmployeeDto saveEmployeeDto, HttpServletRequest request);

    int updateEmployeeInfo(UpdateEmployeeDto updateEmployeeDto);

    /**
     * 用户名登录
     * @param userName 用户名
     * @param passWord  密码
     * @return
     */
    Employee userNameLogin(String userName, String passWord);

}
