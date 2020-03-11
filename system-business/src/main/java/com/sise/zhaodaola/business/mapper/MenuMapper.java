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

    /**
     * 查询用户的权限列表
     * @param username 用户名
     * @param type 1表示菜单，2表示权限
     * @return /
     */
    @Select("SELECT\n" + "\tmenu.*\n" + "FROM\n" + "\tsystem_user USER\n" + "INNER JOIN system_user_roles user_roles " +
            "ON `user`.id = user_roles.user_id\n" + "INNER JOIN system_role_menus role_menus ON role_menus.role_id = " +
            "user_roles.role_id\n" + "INNER JOIN system_menu menu ON menu.id = role_menus.mune_id\n" + "WHERE\n" +
            "\t`user`.username = #{username}\n" + "AND menu.type = #{type}\n" + "AND menu.`title` IS NOT NULL\n" +
            "ORDER" +
            " " +
            "BY\n" + "\tmenu.sort ASC;")
    List<Menu> findMenusByUsername(String username,Integer type);
}
