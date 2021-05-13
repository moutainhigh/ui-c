package com.sep.admin.controller.background;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sep.admin.bean.PermissionInfo;
import com.sep.admin.dto.*;
import com.sep.admin.model.Role;
import com.sep.admin.service.RoleService;
import com.sep.admin.vo.SearchRoleRespVo;
import com.sep.common.model.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangkai
 * @since 2020-02-06
 */
@RestController
@RequestMapping("/background/role")
@Api(value = "员工角色API", tags = {"员工角色API"})
public class BackRoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping(value = "/pageSearchRoleInfo")
    @ApiOperation(value = "分页查询角色信息", httpMethod = "POST")
    public ResponseData<IPage<SearchRoleRespVo>> pageSearchSkuInfo(@RequestBody SearchRoleDto searchRoleDto){
        IPage<SearchRoleRespVo> roleInfoPage = roleService.pageSearchRoleInfo(searchRoleDto);
        return ResponseData.OK(roleInfoPage);
    }

    @PostMapping(value = "/searchAllRoleInfo")
    @ApiOperation(value = "查询全部角色信息", httpMethod = "POST")
    public ResponseData<List<Role>> searchAllRoleInfo(){
        List<Role> roleList = roleService.searchAllRoleInfo();
        return ResponseData.OK(roleList);
    }

    @PostMapping(value = "/searchRoleInfo")
    @ApiOperation(value = "查询角色信息", httpMethod = "POST")
    public ResponseData<SearchRoleRespVo> searchRoleInfo(@RequestBody IdDto idDto){
        SearchRoleRespVo searchRoleRespVo = roleService.searchRoleInfoById(idDto.getId());
        return ResponseData.OK(searchRoleRespVo);
    }

    @PostMapping(value = "/saveRoleInfo")
    @ApiOperation(value = "保存角色信息", httpMethod = "POST")
    public ResponseData saveRoleInfo(@RequestBody SaveRoleDto saveRoleDto){
        int result = roleService.saveRoleInfo(saveRoleDto);
        return result > 0 ? ResponseData.OK() : ResponseData.ERROR("保存角色失败");
    }

    @PostMapping(value = "/updateRoleInfo")
    @ApiOperation(value = "修改角色信息", httpMethod = "POST")
    public ResponseData updateRoleInfo(@RequestBody UpdateRoleDto updateRoleDto){
        int result = roleService.updateRoleInfo(updateRoleDto);
        return result > 0 ? ResponseData.OK() : ResponseData.ERROR("修改角色失败");
    }

    @PostMapping(value = "/deleteRoleInfo")
    @ApiOperation(value = "删除角色信息", httpMethod = "POST")
    public ResponseData deleteRoleInfo(@RequestBody IdDto idDto){
        boolean result = roleService.removeById(idDto.getId());
        return result ? ResponseData.OK() : ResponseData.ERROR("删除角色失败");
    }

    @PostMapping(value = "/searchAllPermissionInfo")
    @ApiOperation(value = "查询所有权限信息", httpMethod = "POST")
    public ResponseData<List<PermissionInfo>> searchAllPermissionInfo() {
        return ResponseData.OK(roleService.searchAllPermissionInfo());
    }
}
