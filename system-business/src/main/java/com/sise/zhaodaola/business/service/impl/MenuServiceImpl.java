package com.sise.zhaodaola.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Menu;
import com.sise.zhaodaola.business.mapper.MenuMapper;
import com.sise.zhaodaola.business.service.MenuService;
import com.sise.zhaodaola.business.service.UserService;
import com.sise.zhaodaola.business.service.dto.UserDto;
import com.sise.zhaodaola.tool.utils.SecurityUtils;
import com.sise.zhaodaola.tool.utils.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
}
