package com.sise.zhaodaola.core.system;

import com.sise.zhaodaola.business.entity.Role;
import com.sise.zhaodaola.business.service.RoleService;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.annotation.Log;
import com.sise.zhaodaola.tool.utils.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/142:02 上午
 */
@RestController
@RequestMapping("/api/roles")
@Slf4j
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Log("全部角色查询")
    @PostMapping("/all")
    public ResponseEntity<Object> getRoleList() {
        return ResponseEntity.ok(roleService.getRoleList());
    }

    @Log("角色列表")
    @PostMapping("/list")
    public ResponseEntity<Object> getRoleList(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria) {
        PageHelper rOleList = roleService.getROleList(basicQueryDto, queryCriteria);
        return ResponseEntity.ok(rOleList);
    }

    @Log("新增角色")
    @PostMapping("/create")
    public ResponseEntity<Object> createRole(@RequestBody Role role) {
        roleService.createRole(role);
        return ResponseEntity.ok("新增成功");
    }

    @Log("删除角色")
    @PostMapping("/delete")
    public ResponseEntity<Object> deleteRole(@RequestBody List<Integer> roleIds) {
        roleService.deleteRole(roleIds);
        return ResponseEntity.ok("删除成功");
    }

    @Log("修改角色")
    @PostMapping("/update")
    public ResponseEntity<Object> updateRole(@RequestBody Role role) {
        roleService.updateRole(role);
        return ResponseEntity.ok("修改成功");
    }
}
