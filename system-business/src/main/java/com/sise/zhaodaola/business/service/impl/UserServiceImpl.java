package com.sise.zhaodaola.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Role;
import com.sise.zhaodaola.business.entity.User;
import com.sise.zhaodaola.business.mapper.RoleMapper;
import com.sise.zhaodaola.business.mapper.UserMapper;
import com.sise.zhaodaola.business.service.UserRolesService;
import com.sise.zhaodaola.business.service.UserService;
import com.sise.zhaodaola.business.service.dto.*;
import com.sise.zhaodaola.tool.dict.DictManager;
import com.sise.zhaodaola.tool.exception.EntityNotFoundException;
import com.sise.zhaodaola.tool.utils.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@CacheConfig(cacheNames = "user")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private RoleMapper roleMapper;
    private UserRolesService userRolesService;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(RoleMapper roleMapper, UserRolesService userRolesService, PasswordEncoder passwordEncoder) {
        this.roleMapper = roleMapper;
        this.userRolesService = userRolesService;
        this.passwordEncoder = passwordEncoder;
    }

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
    public List<User> findAll(UserQueryDto userQueryDto) {
        return super.list(warpper(userQueryDto));
    }

    @Override
    public void downloadUser(List<User> userList, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> download = new ArrayList<>(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);
        userList.forEach(user -> {
            List<String> rolesName = roleMapper.findByUserId(user.getId()).stream().map(Role::getRemark).collect(Collectors.toList());
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("账号", user.getUsername());
            map.put("名字", user.getRealName());
            map.put("昵称", user.getNickName());
            map.put("身份证", user.getIdCard());
            map.put("联系电话", user.getTelephone());
            map.put("邮箱", user.getEmail());
            map.put("性别", DictManager.gender(user.getGender()));
            map.put("专业", user.getDept());
            map.put("状态", DictManager.status(user.getStatus()));
            map.put("角色", rolesName.toString());
            map.put("创建时间", formatter.format(user.getCreateTime()));
            map.put("更新时间", formatter.format(user.getUpdateTime()));
            download.add(map);
        });
        FileUtils.downloadExcel(download, response);
    }

    @Override
    public PageHelper getUserList(UserQueryDto userQueryDto, PageQueryCriteria criteria) {
        // 分页
        Page<User> userPage = new Page<>(criteria.getPage(), criteria.getSize());
        IPage<User> page = super.page(userPage, warpper(userQueryDto));
        List<UserListDto> userListDtoList = new ArrayList<>(0);
        page.getRecords().forEach(user -> {
            UserListDto userListDto = new UserListDto();
            BeanUtil.copyProperties(user, userListDto);
            userListDto.setRoles(roleMapper.findByUserId(user.getId()));
            userListDtoList.add(userListDto);
        });
        return PageUtils.toPage(userListDtoList, page.getCurrent(), page.getSize(), page.getTotal());
    }

    private LambdaQueryWrapper<User> warpper(UserQueryDto userQueryDto) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if (ObjectUtils.isNotEmpty(userQueryDto)) {
            wrapper.like(StringUtils.isNotBlank(userQueryDto.getUsername()), User::getUsername, userQueryDto.getUsername());
            wrapper.like(StringUtils.isNotBlank(userQueryDto.getRealName()), User::getRealName, userQueryDto.getRealName());
            wrapper.like(StringUtils.isNotBlank(userQueryDto.getEmail()), User::getEmail, userQueryDto.getEmail());
            wrapper.eq(userQueryDto.getStatus() != 0, User::getStatus, userQueryDto.getStatus());
            if (StringUtils.isNotBlank(userQueryDto.getStart()) && StringUtils.isNotBlank(userQueryDto.getEnd()))
                wrapper.between(User::getCreateTime, DateTimeUtils.dateTime(userQueryDto.getStart(), DatePattern.NORM_DATETIME_PATTERN), DateTimeUtils.dateTime(userQueryDto.getEnd(), DatePattern.NORM_DATETIME_PATTERN));
        }
        wrapper.ne(User::getUsername, SecurityUtils.getUsername());
        return wrapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUser(UserUpdateDto userUpdateDto) {
        User user = new User();
        BeanUtil.copyProperties(userUpdateDto, user);
        if (super.updateById(user)) {
            // 修改角色
            userRolesService.updateUserRoles(user.getId(), userUpdateDto.getRoles());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void resetPasswordUser(List<Integer> uid) {
        String initPassword = passwordEncoder.encode("111111");
        LambdaUpdateWrapper<User> wrapper = Wrappers.<User>lambdaUpdate().set(User::getPassword, initPassword).in(CollectionUtil.isNotEmpty(uid), User::getId, uid);
        super.update(wrapper);
    }
}
