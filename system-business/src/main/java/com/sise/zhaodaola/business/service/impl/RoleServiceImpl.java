package com.sise.zhaodaola.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Announce;
import com.sise.zhaodaola.business.entity.Menu;
import com.sise.zhaodaola.business.entity.Role;
import com.sise.zhaodaola.business.mapper.AnnounceMapper;
import com.sise.zhaodaola.business.mapper.RoleMapper;
import com.sise.zhaodaola.business.service.AnnounceSerivce;
import com.sise.zhaodaola.business.service.RoleSerivce;
import com.sise.zhaodaola.tool.utils.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@CacheConfig(cacheNames = "role")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleSerivce {


    @Cacheable(key = "'loadPermissionByUser'+#p0")
    @Override
    public Collection<GrantedAuthority> mapToGrantedAuthoritries(Integer uid) {
        Set<Role> roles = baseMapper.findbyUserId(uid);
        Set<String> permission = roles.stream().filter(role -> StringUtils.isNotBlank(role.getName())).map(Role::getName).collect(Collectors.toSet());
        Set<Menu> rolesToMenus = baseMapper.findByRolesToMenus(uid);
        permission.addAll(rolesToMenus.stream()
                .filter(menu -> StringUtils.isNotBlank(menu.getPermission()))
                .map(Menu::getPermission)
                .collect(Collectors.toSet())
        );
        return permission.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }
}
