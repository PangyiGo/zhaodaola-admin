package com.sise.zhaodaola.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Menu;
import com.sise.zhaodaola.business.mapper.MenuMapper;
import com.sise.zhaodaola.business.service.MenuService;
import com.sise.zhaodaola.business.service.UserService;
import com.sise.zhaodaola.business.service.dto.UserDto;
import com.sise.zhaodaola.business.service.vo.MenuQueryVo;
import com.sise.zhaodaola.business.service.vo.MenusVo;
import com.sise.zhaodaola.tool.utils.SecurityUtils;
import com.sise.zhaodaola.tool.utils.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@CacheConfig(cacheNames = "menu")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private UserService userService;

    public MenuServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Set<String> findMenusKeyByUsername() {
        UserDto userDto = userService.findByUsername(SecurityUtils.getUsername());
        List<Menu> menuList = baseMapper.findMenusByUsername(userDto.getId());
        return menuList.stream().filter(menu -> StringUtils.isNotBlank(menu.getName())).map(Menu::getName).collect(Collectors.toSet());
    }

    @Override
    public List<MenusVo> buildMenus() {
        List<Menu> menuList = super.list();
        return buildMenus(0, menuList);
    }

    @Override
    public List<MenuQueryVo> getAllMenus() {
        List<MenuQueryVo> menuQueryVos = new ArrayList<>(0);
        List<Menu> menuList = super.list();
        menuList.forEach(menu -> {
            MenuQueryVo menuQueryVo = new MenuQueryVo();
            menuQueryVo.setId(menu.getId());
            menuQueryVo.setTitle(menu.getTitle());
            menuQueryVo.setParent(menu.getPid());
            menuQueryVos.add(menuQueryVo);
        });
        return menuQueryVos;
    }

    @Override
    public List<Integer> findMenuIdByRoleId(Integer roleId) {
        List<Menu> menuList = baseMapper.findMenusByRoleId(roleId);
        if (menuList != null && menuList.size() > 0)
            return menuList.stream().map(Menu::getId).collect(Collectors.toList());
        return new ArrayList<>(0);
    }

    private List<MenusVo> buildMenus(Integer parentId, List<Menu> menuList) {
        List<MenusVo> menusVoList = new ArrayList<>(0);
        List<Menu> menus = menuList.stream().filter(menu -> menu.getPid().equals(parentId)).collect(Collectors.toList());
        menus.forEach(menu -> {
            MenusVo menusVo = new MenusVo();
            BeanUtil.copyProperties(menu, menusVo);
            menusVo.setChildren(buildMenus(menu.getId(), menuList));
            menusVoList.add(menusVo);
        });
        return menusVoList;
    }
}
