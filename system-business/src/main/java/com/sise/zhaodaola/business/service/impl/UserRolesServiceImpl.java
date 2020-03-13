package com.sise.zhaodaola.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.UserRoles;
import com.sise.zhaodaola.business.mapper.UserRolesMapper;
import com.sise.zhaodaola.business.service.UserRolesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserRolesServiceImpl extends ServiceImpl<UserRolesMapper, UserRoles> implements UserRolesService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserRoles(Integer uid, Set<Integer> rids) {
        this.deleteUserRoles(uid, null);
        rids.forEach(roles -> {
            UserRoles userRoles = new UserRoles();
            userRoles.setUserId(uid);
            userRoles.setRoleId(roles);
            super.save(userRoles);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUserRoles(Integer uid, Set<Integer> rids) {
        LambdaUpdateWrapper<UserRoles> wrapper = Wrappers.<UserRoles>lambdaUpdate();
        wrapper.eq(UserRoles::getUserId, uid);
        wrapper.in(CollectionUtil.isNotEmpty(rids), UserRoles::getRoleId, rids);
        super.remove(wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUserRoles(Integer uid, Set<Integer> rids) {
        List<UserRoles> userRolesList = new ArrayList<>(0);
        rids.forEach(role->{
            UserRoles userRoles = new UserRoles();
            userRoles.setUserId(uid);
            userRoles.setRoleId(role);
            userRolesList.add(userRoles);
        });
        super.saveBatch(userRolesList);
    }
}
