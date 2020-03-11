package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Menu;

import java.util.Set;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface MenuService extends IService<Menu> {

    /**
     * 查询当前用户的菜单name值
     * @param username 用户名
     * @return /
     */
    Set<String> findMenusKeyByUsername(String username);
}
