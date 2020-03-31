package com.sise.zhaodaola.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.zhaodaola.business.entity.Menu;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/610:39 下午
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    @Select("SELECT\n" + "\tmenu.* \n" + "FROM\n" + "\tsystem_user\n" + "\tUSER INNER JOIN system_user_roles user_roles ON USER.id = user_roles.user_id\n" + "\tINNER JOIN system_role_menus role_menus ON role_menus.role_id = user_roles.role_id\n" + "\tINNER JOIN system_menu menu ON menu.id = role_menus.menu_id \n" + "WHERE\n" + "\tUSER.id = #{uid} \n" + "ORDER BY\n" + "\tmenu.sort ASC;")
    List<Menu> findMenusByUsername(Integer uid);

    @Select("SELECT\n" + "\tmenu.*\n" + "FROM\n" + "\tsystem_menu menu\n" + "INNER JOIN system_role_menus role_menu ON menu.id = role_menu.menu_id\n" + "INNER JOIN system_role role ON role.id = role_menu.role_id\n" + "WHERE\n" + "\trole.id = #{roleId}")
    List<Menu> findMenusByRoleId(Integer roleId);
}
