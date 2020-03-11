package com.sise.zhaodaola.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.User;
import com.sise.zhaodaola.business.mapper.UserMapper;
import com.sise.zhaodaola.business.service.UserService;
import com.sise.zhaodaola.business.service.dto.UserDto;
import com.sise.zhaodaola.tool.exception.EntityNotFoundException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@CacheConfig(cacheNames = "user")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Cacheable(key = "'loadUserByUsername:'+#p0")
    @Override
    public UserDto findByUsername(String username) {
        User user;
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery().eq(User::getUsername, username);
        user = super.getOne(wrapper, true);
        if (ObjectUtils.isEmpty(user))
            throw new EntityNotFoundException(User.class, "username", username);
        UserDto userDto = new UserDto();
        BeanUtil.copyProperties(user, userDto);
        return userDto;
    }
}
