package com.sise.zhaodaola.core.system;

import com.sise.zhaodaola.business.service.MenuService;
import com.sise.zhaodaola.business.service.RoleService;
import com.sise.zhaodaola.business.service.dto.RoleMenuDto;
import com.sise.zhaodaola.business.service.vo.MenuQueryVo;
import com.sise.zhaodaola.business.service.vo.MenusVo;
import com.sise.zhaodaola.tool.annotation.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * User: PangYi
 * Date: 2020-03-11
 * Time: 11:18
 * Description:
 */
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 查询用户的菜单name列表
     *
     * @return /
     */
    @PostMapping("/names")
    public ResponseEntity<Object> getMenuNameList() {
        Set<String> keyByUsername = menuService.findMenusKeyByUsername();
        return ResponseEntity.ok(keyByUsername);
    }

    @Log("获取菜单树形列表")
    @PreAuthorize("@auth.check('menu:tree')")
    @PostMapping("/tree")
    public ResponseEntity<Object> buildMenus() {
        List<MenusVo> menusVoList = menuService.buildMenus();
        return ResponseEntity.ok(menusVoList);
    }

    @PostMapping("/getAll")
    public ResponseEntity<Object> getAllMenus() {
        List<MenuQueryVo> allMenus = menuService.getAllMenus();
        return ResponseEntity.ok(allMenus);
    }

    @PostMapping("/getMenuIds/{roleId}")
    public ResponseEntity<Object> getMenuId(@PathVariable("roleId") Integer roleId) {
        List<Integer> menuIdByUsername = menuService.findMenuIdByRoleId(roleId);
        return ResponseEntity.ok(menuIdByUsername);
    }

    @PostMapping("/saveMenu")
    public ResponseEntity<Object> saveMenu(RoleMenuDto roleMenuDto) {
        roleService.saveRoleToMenu(roleMenuDto);
        return ResponseEntity.ok("保存成功");
    }
}
