package com.sise.zhaodaola.core.security.service;

import cn.hutool.core.util.ObjectUtil;
import com.sise.zhaodaola.business.service.RoleService;
import com.sise.zhaodaola.business.service.UserService;
import com.sise.zhaodaola.business.service.dto.UserDto;
import com.sise.zhaodaola.core.security.security.vo.JwtUser;
import com.sise.zhaodaola.tool.dict.DictManager;
import com.sise.zhaodaola.tool.exception.BadRequestException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @Author: PangYi
 * @Date 2020/3/81:32 下午
 */
@Service("userDetailsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;
    private RoleService roleService;

    public UserDetailsServiceImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userService.findByUsername(username);
        if (ObjectUtil.isEmpty(userDto))
            throw new BadRequestException("账号不存在");
        if (!DictManager.isEnable(userDto.getStatus()))
            throw new BadRequestException("账号不可用，请联系管理员");
        Collection<GrantedAuthority> grantedAuthorities = roleService.mapToGrantedAuthorization(userDto.getId());
        return this.createJwtUser(userDto, grantedAuthorities);
    }

    private UserDetails createJwtUser(UserDto userDto, Collection<GrantedAuthority> grantedAuthorities) {
        return new JwtUser(
                userDto.getId(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getRealName(),
                userDto.getNickName(),
                userDto.getIdCard(),
                userDto.getTelephone(),
                userDto.getEmail(),
                DictManager.gender(userDto.getGender()),
                userDto.getAvatar(),
                userDto.getDept(),
                DictManager.isEnable(userDto.getStatus()),
                grantedAuthorities
        );
    }
}
