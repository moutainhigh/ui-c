package com.sep.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sep.admin.bean.PermissionInfo;
import com.sep.admin.dto.SaveRoleDto;
import com.sep.admin.dto.SearchRoleDto;
import com.sep.admin.dto.UpdateRoleDto;
import com.sep.admin.model.Role;
import com.sep.admin.vo.SearchRoleRespVo;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author zhangkai
 * @since 2020-02-21
 */
public interface RoleService extends IService<Role> {

    IPage<SearchRoleRespVo> pageSearchRoleInfo(SearchRoleDto searchRoleDto);

    SearchRoleRespVo searchRoleInfoById(int id);

    int saveRoleInfo(SaveRoleDto saveRoleDto);

    int updateRoleInfo(UpdateRoleDto updateRoleDto);

    List<Role> searchAllRoleInfo();

    List<PermissionInfo> searchAllPermissionInfo();

}
