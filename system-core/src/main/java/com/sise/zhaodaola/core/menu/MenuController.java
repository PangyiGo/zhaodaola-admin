package com.sise.zhaodaola.core.menu;

import com.sise.zhaodaola.business.service.MenuService;
import com.sise.zhaodaola.tool.utils.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 查询用户的菜单name列表
     * @return /
     */
    @PostMapping("/names")
    public ResponseEntity<Object> getMenuNameList(){
        String username = SecurityUtils.getUsername();
        Set<String> keyByUsername = menuService.findMenusKeyByUsername(username);
        return ResponseEntity.ok(keyByUsername);
    }
}
