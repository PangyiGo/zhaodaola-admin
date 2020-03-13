package com.sise.zhaodaola.core;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.sise.zhaodaola.business.entity.Menu;
import com.sise.zhaodaola.business.entity.Role;
import com.sise.zhaodaola.business.mapper.RoleMapper;
import com.sise.zhaodaola.business.service.MenuService;
import com.sise.zhaodaola.business.service.RoleService;
import com.sise.zhaodaola.business.service.UserService;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.dto.UserDto;
import com.sise.zhaodaola.business.service.dto.UserQueryDto;
import com.sise.zhaodaola.business.service.dto.UserUpdateDto;
import com.sise.zhaodaola.tool.utils.PageHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@SpringBootTest
class CoreApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Test
    void contextLoads() {
        String encode = passwordEncoder.encode("py1653@scse.com.cn");
        System.out.println(encode);
    }

    @Test
    void test01() {
        String username = "py1653@scse.com.cn";
        UserDto userDto = userService.findByUsername(username);
        System.out.println(userDto);
    }

    @Test
    void test02() {
        Set<Menu> byRolesToMenus = roleMapper.findByRolesToMenus(1);
        Set<Role> roles = roleMapper.findByUserId(1);
        roles.forEach(System.out::println);
        byRolesToMenus.forEach(System.out::println);
    }

    @Test
    void test03() {
        Collection<GrantedAuthority> authorization = roleService.mapToGrantedAuthorization(2);
        authorization.forEach(a -> System.out.println(a.getAuthority()));
    }

    @Test
    void test04() {
        Set<String> keyByUsername = menuService.findMenusKeyByUsername();
        keyByUsername.forEach(System.out::println);
    }

    @Test
    void test05() {
        UserQueryDto queryDto = new UserQueryDto();
        queryDto.setWord(null);
        queryDto.setStatus(1);

        PageQueryCriteria criteria = new PageQueryCriteria();
        criteria.setPage(1);
        criteria.setSize(10);

        PageHelper userList = userService.getUserList(null, criteria);

        System.out.println(userList);
    }

    @Test
    void test06() {
        String username = "1640129430";
        UserDto userDto = userService.findByUsername(username);

        UserUpdateDto userUpdateDto = new UserUpdateDto();
        BeanUtil.copyProperties(userDto, userUpdateDto);
        userUpdateDto.setRoles(CollectionUtil.newHashSet(1, 2));

        userService.updateUser(userUpdateDto);
    }

    @Test
    void test07() {
        userService.resetPasswordUser(CollectionUtil.newArrayList(1, 2));
    }

    @Test
    void test08() {
        List<Role> roleList = roleService.getRoleList();
        roleList.forEach(System.out::println);
    }

    @Test
    void test09() {
        userService.resetPasswordUser(Arrays.asList(1, 2));
    }

    @Test
    void test10(){
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setUsername("1640129433");
        userUpdateDto.setRealName("吴伟康");
        userUpdateDto.setIdCard("440804199611051142");
        userUpdateDto.setTelephone("17620106523");
        userUpdateDto.setEmail("2016545@163.com");
        userUpdateDto.setGender(1);
        userUpdateDto.setDept("软件系");
        userUpdateDto.setRoles(CollectionUtil.newHashSet(2));

        userService.createUser(userUpdateDto);
    }

    @Test
    void test11(){
        userService.deleteUser(CollectionUtil.newArrayList(4));
    }
}
