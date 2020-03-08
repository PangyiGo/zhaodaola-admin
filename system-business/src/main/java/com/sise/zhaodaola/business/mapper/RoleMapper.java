package com.sise.zhaodaola.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.zhaodaola.business.entity.Announce;
import com.sise.zhaodaola.business.entity.Menu;
import com.sise.zhaodaola.business.entity.Role;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @Author: PangYi
 * @Date 2020/3/610:39 下午
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    @Select("SELECT\n" +
            "\tmenu.* \n" +
            "FROM\n" +
            "\tsystem_user\n" +
            "\tUSER INNER JOIN system_user_roles user_roles ON USER.id = user_roles.user_id\n" +
            "\tINNER JOIN system_role_menus role_menus ON role_menus.role_id = user_roles.role_id\n" +
            "\tINNER JOIN system_menu menu ON menu.id = role_menus.mune_id \n" +
            "WHERE\n" +
            "\tUSER.id = #{uid}")
    Set<Menu> findByRolesToMenus(Integer uid);

    @Select("SELECT\n" +
            "\trole.* \n" +
            "FROM\n" +
            "\tsystem_user user\n" +
            "\tINNER JOIN system_user_roles user_roles ON user.id = user_roles.user_id\n" +
            "\tINNER JOIN system_role role ON role.id = user_roles.role_id \n" +
            "WHERE\n" +
            "\tUSER.id = #{id}")
    Set<Role> findbyUserId(Integer uid);
}
