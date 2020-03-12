package com.sise.zhaodaola.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.User;
import com.sise.zhaodaola.business.mapper.UserMapper;
import com.sise.zhaodaola.business.service.UserService;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.dto.UserDto;
import com.sise.zhaodaola.business.service.dto.UserQueryDto;
import com.sise.zhaodaola.tool.exception.EntityNotFoundException;
import com.sise.zhaodaola.tool.utils.DateTimeUtils;
import com.sise.zhaodaola.tool.utils.PageHelper;
import com.sise.zhaodaola.tool.utils.PageUtils;
import com.sise.zhaodaola.tool.utils.StringUtils;
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

    @Override
    public PageHelper getUserList(UserQueryDto userQueryDto, PageQueryCriteria criteria) {
        // 分页
        Page<User> userPage = new Page<>(criteria.getPage(), criteria.getSize());
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if (userQueryDto != null) {
            wrapper.like(StringUtils.isNotBlank(userQueryDto.getUsername()), User::getUsername, userQueryDto.getUsername());
            wrapper.like(StringUtils.isNotBlank(userQueryDto.getRealName()), User::getRealName, userQueryDto.getRealName());
            wrapper.like(StringUtils.isNotBlank(userQueryDto.getEmail()), User::getEmail, userQueryDto.getEmail());
            wrapper.eq(userQueryDto.getStatus() != 0, User::getStatus, userQueryDto.getStatus());
            wrapper.between(StringUtils.isNotBlank(userQueryDto.getStart()) && StringUtils.isNotBlank(userQueryDto.getEnd()), User::getCreateTime, DateTimeUtils.dateTime(userQueryDto.getStart(), DatePattern.NORM_DATETIME_PATTERN), DateTimeUtils.dateTime(userQueryDto.getEnd(), DatePattern.NORM_DATETIME_PATTERN));
        }
        IPage<User> page = super.page(userPage, wrapper);
        return PageUtils.toPage(page.getRecords(), page.getCurrent(), page.getSize(), page.getTotal());
    }
}
