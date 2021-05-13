package com.sep.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.sep.admin.bean.PermissionInfo;
import com.sep.admin.dto.SaveRoleDto;
import com.sep.admin.dto.SearchEmployeeDto;
import com.sep.admin.dto.SearchRoleDto;
import com.sep.admin.dto.UpdateRoleDto;
import com.sep.admin.model.Role;
import com.sep.admin.repository.RoleMapper;
import com.sep.admin.service.EmployeeService;
import com.sep.admin.service.RoleService;
import com.sep.admin.vo.SearchEmployeeRespVo;
import com.sep.admin.vo.SearchRoleRespVo;
import com.sep.common.exceptions.SepCustomException;
import com.sep.common.model.response.ResponseData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author zhangkai
 * @since 2020-02-21
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private EmployeeService employeeService;

    @Value("${role.permission}")
    private String rolePermissions;

    @Override
    public IPage<SearchRoleRespVo> pageSearchRoleInfo(SearchRoleDto searchRoleDto) {
        IPage<SearchRoleRespVo> searchRoleRespVoPage = new Page<>();
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        IPage<Role> roleInfoPage = baseMapper.selectPage(new Page<>(searchRoleDto.getCurrentPage(),searchRoleDto.getPageSize()),queryWrapper);
        if(roleInfoPage != null){
            BeanUtils.copyProperties(roleInfoPage,searchRoleRespVoPage);
            if(!CollectionUtils.isEmpty(roleInfoPage.getRecords())){
                List<SearchRoleRespVo> searchRoleRespVoList = roleInfoPage.getRecords().stream().map(roleInfo->{
                   return convertRoleToRespVo(roleInfo);
                }).collect(Collectors.toList());
                searchRoleRespVoPage.setRecords(searchRoleRespVoList);
            }
        }
        return searchRoleRespVoPage;
    }

    @Override
    public SearchRoleRespVo searchRoleInfoById(int id) {
        Role role = baseMapper.selectById(id);
        if(role == null){
            return null;
        }
        return convertRoleToRespVo(role);
    }

    @Override
    public int saveRoleInfo(SaveRoleDto saveRoleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(saveRoleDto,role);
        LocalDateTime now = LocalDateTime.now();
        String adminUserName = ""; // todo 获取登录管理员用户名
        role.setCreateTime(now);
        role.setCreateUid(adminUserName);
        if(!CollectionUtils.isEmpty(saveRoleDto.getPermissionIdList())){
            role.setPermissionIds(Joiner.on(",").join(saveRoleDto.getPermissionIdList()));
        }
        return baseMapper.insert(role);
    }

    @Override
    public int updateRoleInfo(UpdateRoleDto updateRoleDto) {
        Role role = baseMapper.selectById(updateRoleDto.getId());
        if(role == null){
            throw new SepCustomException(ResponseData.STATUS_CODE_400,"角色不存在");
        }
        BeanUtils.copyProperties(updateRoleDto,role);
        if(!CollectionUtils.isEmpty(updateRoleDto.getPermissionIdList())){
            role.setPermissionIds(Joiner.on(",").join(updateRoleDto.getPermissionIdList()));
        }
        String adminUserName = ""; // todo 获取登录管理员用户名
        role.setUpdateUid(adminUserName);
        int result = baseMapper.updateById(role);
        return result;
    }

    @Override
    public List<Role> searchAllRoleInfo() {
        return baseMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public List<PermissionInfo> searchAllPermissionInfo() {
        List<PermissionInfo> permissionInfoList = Lists.newArrayList();
        List<String> currentSystemPermissions = Lists.newArrayList(rolePermissions.split(","));
        for(String permission : currentSystemPermissions){
            PermissionInfo permissionInfo = new PermissionInfo();
            permissionInfo.setId(Integer.parseInt(permission.split("-")[0]));
            permissionInfo.setName(permission.split("-")[1]);
            permissionInfoList.add(permissionInfo);
        }
        return permissionInfoList;
    }

    private SearchRoleRespVo convertRoleToRespVo(Role role){
        SearchRoleRespVo searchRoleRespVo = new SearchRoleRespVo();
        BeanUtils.copyProperties(role,searchRoleRespVo);
        if(!StringUtils.isEmpty(role.getPermissionIds())){
            List<Integer> permissionIdList = Lists.newArrayList(role.getPermissionIds().split(",")).stream()
                    .map(permissionStr -> Integer.parseInt(permissionStr)).collect(Collectors.toList());
            searchRoleRespVo.setPermissionIdList(permissionIdList);
        }
        SearchRoleRespVo roleRespVo = new SearchRoleRespVo();
        BeanUtils.copyProperties(role,roleRespVo);
        SearchEmployeeDto searchEmployeeDto = new SearchEmployeeDto();
        searchEmployeeDto.setCurrentPage(1);
        searchEmployeeDto.setPageSize(1);
        searchEmployeeDto.setRoleId(role.getId());
        IPage<SearchEmployeeRespVo> searchEmployeePage = employeeService.pageSearchEmployeeInfo(searchEmployeeDto);
        if(searchEmployeePage != null){
            roleRespVo.setEmployeeCount((int)searchEmployeePage.getTotal());
        }
        roleRespVo.setPermissionIdList(Lists.newArrayList());
        return searchRoleRespVo;
    }
}
