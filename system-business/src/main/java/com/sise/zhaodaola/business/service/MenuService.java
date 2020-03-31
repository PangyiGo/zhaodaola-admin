package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Menu;
import com.sise.zhaodaola.business.service.vo.MenuQueryVo;
import com.sise.zhaodaola.business.service.vo.MenusVo;

import java.util.List;
import java.util.Set;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface MenuService extends IService<Menu> {

    /**
     * 查询当前用户的菜单name值
     *
     * @return /
     */
    Set<String> findMenusKeyByUsername();

    /**
     * 菜单树形列表
     *
     * @return /
     */
    List<MenusVo> buildMenus();

    /**
     * 获取系统所有的菜单
     *
     * @return /
     */
    List<MenuQueryVo> getAllMenus();

    /**
     * 获取用户的菜单id列表
     *
     * @return /
     */
    List<Integer> findMenuIdByRoleId(Integer roleId);
}
