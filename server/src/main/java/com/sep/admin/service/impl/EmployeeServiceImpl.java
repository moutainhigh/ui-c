package com.sep.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sep.admin.dto.SaveEmployeeDto;
import com.sep.admin.dto.SearchEmployeeDto;
import com.sep.admin.dto.UpdateEmployeeDto;
import com.sep.admin.enums.BizErrorCode;
import com.sep.admin.model.Employee;
import com.sep.admin.model.Role;
import com.sep.admin.repository.EmployeeMapper;
import com.sep.admin.service.EmployeeService;
import com.sep.admin.service.RoleService;
import com.sep.admin.utils.TokenUtils;
import com.sep.admin.vo.SearchEmployeeRespVo;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.model.response.ResponseData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 员工信息表 服务实现类
 * </p>
 *
 * @author zhangkai
 * @since 2020-02-21
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public IPage<SearchEmployeeRespVo> pageSearchEmployeeInfo(SearchEmployeeDto searchEmployeeDto) {
        IPage<SearchEmployeeRespVo> searchEmployeeRespVoPage = new Page<>();
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        if(searchEmployeeDto.getRoleId() != null){
            queryWrapper.eq("employee_roleId",searchEmployeeDto.getRoleId());
        }
        if(StringUtils.isNotBlank(searchEmployeeDto.getEmployeeName())){
            String employeeName = searchEmployeeDto.getEmployeeName();
            if(!StringUtils.isNumeric(employeeName)){
                queryWrapper.eq("employee_name",employeeName);
            }else {
                queryWrapper.apply("employee_name like %" + employeeName + "% or id = " + employeeName);
            }
        }
        queryWrapper.orderByDesc("id");
        IPage<Employee> employeeInfoPage = baseMapper.selectPage(new Page<>(searchEmployeeDto.getCurrentPage(),searchEmployeeDto.getPageSize()),queryWrapper);
        if(employeeInfoPage != null){
            BeanUtils.copyProperties(employeeInfoPage,searchEmployeeRespVoPage);
            if(!CollectionUtils.isEmpty(employeeInfoPage.getRecords())){
                List<SearchEmployeeRespVo> searchEmployeeRespVoList = employeeInfoPage.getRecords().stream().map(employeeInfo->{
                    return convertEmployeeToRespVo(employeeInfo);
                }).collect(Collectors.toList());
                searchEmployeeRespVoPage.setRecords(searchEmployeeRespVoList);
            }
        }
        return searchEmployeeRespVoPage;
    }

    @Override
    public SearchEmployeeRespVo searchEmployeeInfo(int id) {
        Employee employee = baseMapper.selectById(id);
        if(employee == null){
            return null;
        }
        return convertEmployeeToRespVo(employee);
    }

    @Override
    public int saveEmployeeInfo(SaveEmployeeDto saveEmployeeDto, HttpServletRequest request) {
        if(getEmloyeeByUserAccount(saveEmployeeDto.getEmployeeAccount()) != null){
            throw new SepCustomException(BizErrorCode.ADMIN_IS_EXIST);
        }
        Employee employee = new Employee();
        BeanUtils.copyProperties(saveEmployeeDto,employee);
        employee.setEmployeePassword(DigestUtils.md5DigestAsHex((saveEmployeeDto.getEmployeeAccount()+"123").getBytes()));
        // 查询最大的序列号
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("employee_no");
        IPage<Employee> employeeIPage = baseMapper.selectPage(new Page<>(1,1),wrapper);
        employee.setEmployeeNo(employeeIPage != null && !CollectionUtils.isEmpty(employeeIPage.getRecords())
            && employeeIPage.getRecords().get(0).getEmployeeNo() != null ? employeeIPage.getRecords().get(0).getEmployeeNo() + 1 : 1);
        LocalDateTime now = LocalDateTime.now();
        Employee admin = tokenUtils.getCurrentAdminUname(request);
        if(admin != null){
            employee.setCreateUid(admin.getEmployeeAccount());
        }
        employee.setCreateTime(now);
        return baseMapper.insert(employee);
    }

    @Override
    public int updateEmployeeInfo(UpdateEmployeeDto updateEmployeeDto) {
        Employee employee = baseMapper.selectById(updateEmployeeDto.getId());
        if(employee == null){
            throw new SepCustomException(ResponseData.STATUS_CODE_400,"员工不存在");
        }
        BeanUtils.copyProperties(updateEmployeeDto,employee);
        employee.setEmployeePassword(DigestUtils.md5DigestAsHex((updateEmployeeDto.getEmployeeAccount()+"123").getBytes()));
        String adminUserName = ""; // todo 获取登录管理员用户名
        employee.setUpdateUid(adminUserName);
        return baseMapper.updateById(employee);
    }

    @Override
    public Employee userNameLogin(String userName, String passWord) {
        if(StringUtils.isBlank(userName)){
            throw new SepCustomException(ResponseData.STATUS_CODE_400,"登录账号不能为空");
        }
        if(StringUtils.isBlank(passWord)){
            throw new SepCustomException(ResponseData.STATUS_CODE_400,"密码不能为空");
        }
        Employee sysUser = getEmloyeeByUserAccount(userName);
        if(sysUser == null){
            throw new SepCustomException(BizErrorCode.ADMIN_NOT_EXIST);
        }
        if(!sysUser.getEmployeePassword().equals(DigestUtils.md5DigestAsHex(StringUtils.trim(passWord).getBytes()))){
            throw new SepCustomException(BizErrorCode.PASSWORD_ERROR);
        }
        return sysUser;
    }

    private Employee getEmloyeeByUserAccount(String userAccount) {
        if(StringUtils.isBlank(userAccount)){
            return null;
        }
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.eq("employee_account", userAccount);
        List<Employee> sysUserList = baseMapper.selectList(wrapper);
        if(CollectionUtils.isEmpty(sysUserList)){
            return null;
        }
        return sysUserList.get(0);
    }

    private SearchEmployeeRespVo convertEmployeeToRespVo(Employee employee){
        SearchEmployeeRespVo searchEmployeeRespVo = new SearchEmployeeRespVo();
        BeanUtils.copyProperties(employee,searchEmployeeRespVo);
        // 查询角色名称
        Role role = roleService.getById(employee.getEmployeeRoleid());
        if(role != null){
            searchEmployeeRespVo.setRoleName(role.getRoleName());
        }
        return searchEmployeeRespVo;
    }
}
