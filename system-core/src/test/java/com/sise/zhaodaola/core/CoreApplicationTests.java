package com.sise.zhaodaola.core;

import com.sise.zhaodaola.business.entity.Menu;
import com.sise.zhaodaola.business.entity.Role;
import com.sise.zhaodaola.business.mapper.RoleMapper;
import com.sise.zhaodaola.business.service.RoleSerivce;
import com.sise.zhaodaola.business.service.UserSerivce;
import com.sise.zhaodaola.business.service.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Set;

@SpringBootTest
class CoreApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserSerivce userSerivce;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleSerivce roleSerivce;

    @Test
    void contextLoads() {
        String encode = passwordEncoder.encode("py1653@scse.com.cn");
        System.out.println(encode);
    }

    @Test
    void test01() {
        String username = "py1653@scse.com.cn";
        UserDto userDto = userSerivce.findByUsername(username);

        System.out.println(userDto);
    }

    @Test
    void test02() {
        Set<Menu> byRolesToMenus = roleMapper.findByRolesToMenus(1);
        Set<Role> roles = roleMapper.findbyUserId(1);
        roles.forEach(System.out::println);
        byRolesToMenus.forEach(System.out::println);
    }

    @Test
    void test03() {
        Collection<GrantedAuthority> authoritries = roleSerivce.mapToGrantedAuthoritries(2);

        authoritries.forEach(a -> System.out.println(a.getAuthority()));
    }
}
