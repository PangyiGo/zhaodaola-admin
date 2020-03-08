package com.sise.zhaodaola.core.security.security.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @Author: PangYi
 * @Date 2020/3/83:54 下午
 */
@Getter
@AllArgsConstructor
public class JwtUser implements UserDetails {

    private Integer id;

    private String username;

    @JsonIgnore
    private String password;

    private String realName;

    private String nickName;

    private String idCard;

    private String telephone;

    private String email;

    private String gender;

    private String avatar;

    private String dept;

    private Boolean status;

    @JsonIgnore
    private final Collection<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status;
    }

    public Collection getRoles() {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }
}
