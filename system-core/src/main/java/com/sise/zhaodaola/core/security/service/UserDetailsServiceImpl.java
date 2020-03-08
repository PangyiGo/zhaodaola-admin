package com.sise.zhaodaola.core.security.service;

import cn.hutool.core.util.ObjectUtil;
import com.sise.zhaodaola.business.service.RoleSerivce;
import com.sise.zhaodaola.business.service.UserSerivce;
import com.sise.zhaodaola.business.service.dto.UserDto;
import com.sise.zhaodaola.core.security.security.vo.JwtUser;
import com.sise.zhaodaola.tool.dict.DictManager;
import com.sise.zhaodaola.tool.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
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

    private UserSerivce userSerivce;
    private RoleSerivce roleSerivce;

    public UserDetailsServiceImpl(UserSerivce userSerivce, RoleSerivce roleSerivce) {
        this.userSerivce = userSerivce;
        this.roleSerivce = roleSerivce;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userSerivce.findByUsername(username);
        if (ObjectUtil.isEmpty(userDto))
            throw new BadRequestException("账号不存在");
        if (!DictManager.isEnable(userDto.getStatus()))
            throw new BadRequestException("账号不可用，请联系管理员");
        Collection<GrantedAuthority> grantedAuthorities = roleSerivce.mapToGrantedAuthoritries(userDto.getId());
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
