package com.sise.zhaodaola.core;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.sise.zhaodaola.business.entity.Menu;
import com.sise.zhaodaola.business.entity.Role;
import com.sise.zhaodaola.business.mapper.RoleMapper;
import com.sise.zhaodaola.business.service.LostService;
import com.sise.zhaodaola.business.service.MenuService;
import com.sise.zhaodaola.business.service.RoleService;
import com.sise.zhaodaola.business.service.UserService;
import com.sise.zhaodaola.business.service.dto.*;
import com.sise.zhaodaola.business.service.vo.MenusVo;
import com.sise.zhaodaola.tool.utils.PageHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private LostService lostService;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void contextLoads() {
        ResponseEntity<String> exchange = restTemplate.exchange("http://www.nanhonghj.com:11112", HttpMethod.POST, null, String.class);
        System.out.println(exchange);
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
    void test10() {
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setUsername("1640129466");
        userUpdateDto.setRealName("郑敏明");
        userUpdateDto.setIdCard("440804199611051142");
        userUpdateDto.setTelephone("17620106523");
        userUpdateDto.setEmail("2016545@163.com");
        userUpdateDto.setGender(2);
        userUpdateDto.setDept("软件系");
        userUpdateDto.setRoles(CollectionUtil.newHashSet(3));

        userService.createUser(userUpdateDto);
    }

    @Test
    void test11() {
        userService.deleteUser(CollectionUtil.newArrayList(4));
    }

    @Test
    void test12() {
        LostFoundQueryDto lostFoundQueryDto = new LostFoundQueryDto();
        lostFoundQueryDto.setStart("2019-12-21");
        lostFoundQueryDto.setEnd("2020-01-01");
        PageQueryCriteria queryCriteria = new PageQueryCriteria();

        PageHelper listToPage = lostService.getListToPage(lostFoundQueryDto, queryCriteria);

        System.out.println(listToPage);
    }

    @Test
    void test13() {
        List<Menu> menuList = menuService.list();
        List<MenusVo> menusVoList = buildMenus(0, menuList);

        System.out.println(menusVoList);
    }

    private List<MenusVo> buildMenus(Integer parentId, List<Menu> menuList) {
        List<MenusVo> menusVoList = new ArrayList<>();
        List<Menu> menus = menuList.stream().filter(menu -> menu.getPid().equals(parentId)).collect(Collectors.toList());
        menus.forEach(menu -> {
            MenusVo menusVo = new MenusVo();
            BeanUtil.copyProperties(menu, menusVo);
            menusVo.setChildren(buildMenus(menu.getId(), menuList));
            menusVoList.add(menusVo);
        });
        return menusVoList;
    }
}
