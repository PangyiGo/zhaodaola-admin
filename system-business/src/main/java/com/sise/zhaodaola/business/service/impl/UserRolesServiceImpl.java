package com.sise.zhaodaola.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.UserRoles;
import com.sise.zhaodaola.business.mapper.UserRolesMapper;
import com.sise.zhaodaola.business.service.UserRolesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserRolesServiceImpl extends ServiceImpl<UserRolesMapper, UserRoles> implements UserRolesService {

    @Override
    public void updateUserRoles(Integer uid, Integer rid) {
        LambdaUpdateWrapper<UserRoles> wrapper = Wrappers.<UserRoles>lambdaUpdate().set(UserRoles::getRoleId, rid).eq(UserRoles::getUserId, uid);
        super.update(wrapper);
    }
}
