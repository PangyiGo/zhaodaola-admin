package com.sise.zhaodaola.business.service.impl;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Menu;
import com.sise.zhaodaola.business.entity.Role;
import com.sise.zhaodaola.business.entity.RoleMenus;
import com.sise.zhaodaola.business.mapper.RoleMapper;
import com.sise.zhaodaola.business.service.RoleMenusService;
import com.sise.zhaodaola.business.service.RoleService;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.dto.RoleMenuDto;
import com.sise.zhaodaola.tool.exception.BadRequestException;
import com.sise.zhaodaola.tool.utils.DateTimeUtils;
import com.sise.zhaodaola.tool.utils.PageHelper;
import com.sise.zhaodaola.tool.utils.PageUtils;
import com.sise.zhaodaola.tool.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@CacheConfig(cacheNames = "role")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenusService roleMenusService;

    @Cacheable(key = "'loadPermissionByUser'+#p0")
    @Override
    public Collection<GrantedAuthority> mapToGrantedAuthorization(Integer uid) {
        Set<Role> roles = baseMapper.findByUserId(uid);
        Set<String> permission = roles.stream().filter(role -> StringUtils.isNotBlank(role.getName())).map(Role::getName).collect(Collectors.toSet());
        Set<Menu> rolesToMenus = baseMapper.findByRolesToMenus(uid);
        permission.addAll(rolesToMenus.stream().filter(menu -> StringUtils.isNotBlank(menu.getPermission())).map(Menu::getPermission).collect(Collectors.toSet()));
        return permission.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override
    public List<Role> getRoleList() {
        return super.list();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createRole(Role role) {
        if (findByName(role.getName()) != null)
            throw new BadRequestException("新增角色名称不允许重复");
        role.setCreateTime(LocalDateTime.now());
        super.save(role);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRole(List<Integer> roleIds) {
        try {
            super.removeByIds(roleIds);
        } catch (Exception e) {
            throw new BadRequestException("角色与用户存在关联，无法删除");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRole(Role role) {
        super.updateById(role);
    }

    @Override
    public PageHelper getROleList(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria) {
        Page<Role> rolePage = new Page<>(queryCriteria.getPage(), queryCriteria.getSize());
        Page<Role> page = super.page(rolePage, wrapper(basicQueryDto));
        return PageUtils.toPage(page.getRecords(), page.getCurrent(), page.getSize(), page.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRoleToMenu(RoleMenuDto roleMenuDto) {
        // 删除原有的权限
        roleMenusService.remove(Wrappers.<RoleMenus>lambdaQuery().eq(RoleMenus::getRoleId, roleMenuDto.getRoleId()));

        List<RoleMenus> roleMenusList = new ArrayList<>(0);
        // 批量保存
        roleMenuDto.getMenuIds().forEach(item -> {
            RoleMenus roleMenus = new RoleMenus();
            roleMenus.setRoleId(roleMenuDto.getRoleId());
            roleMenus.setMenuId(item);
            roleMenusList.add(roleMenus);
        });
        if (roleMenusList.size() > 0) {
            roleMenusService.saveBatch(roleMenusList);
        }
    }

    private LambdaQueryWrapper<Role> wrapper(BasicQueryDto basicQueryDto) {
        LambdaQueryWrapper<Role> wrapper = Wrappers.<Role>lambdaQuery();
        if (ObjectUtils.isNotEmpty(basicQueryDto)) {
            wrapper.and(StringUtils.isNoneBlank(basicQueryDto.getWord()), q -> {
                q.or().like(Role::getName, basicQueryDto.getWord());
                q.or().like(Role::getRemark, basicQueryDto.getWord());
            });
            if (StringUtils.isNotBlank(basicQueryDto.getStart()) && StringUtils.isNotBlank(basicQueryDto.getEnd()))
                wrapper.between(Role::getCreateTime, DateTimeUtils.dateTime(basicQueryDto.getStart(), DatePattern.NORM_DATETIME_PATTERN), DateTimeUtils.dateTime(basicQueryDto.getEnd(), DatePattern.NORM_DATETIME_PATTERN));
        }
        return wrapper;
    }

    private Role findByName(String name) {
        LambdaQueryWrapper<Role> wrapper = Wrappers.<Role>lambdaQuery().eq(StringUtils.isNotBlank(name), Role::getName, name);
        return super.getOne(wrapper);
    }
}
