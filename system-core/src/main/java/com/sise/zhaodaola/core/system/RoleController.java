package com.sise.zhaodaola.core.system;

import com.sise.zhaodaola.business.service.RoleService;
import com.sise.zhaodaola.tool.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PreAuthorize("@auth.check('role:all')")
    @PostMapping("/all")
    public ResponseEntity<Object> getRoleList() {
        return ResponseEntity.ok(roleService.getRoleList());
    }
}
