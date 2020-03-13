package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.UserRoles;

import java.util.Set;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface UserRolesService extends IService<UserRoles> {

    /**
     * 修改用户角色
     *
     * @param uid  用户ID
     * @param rids 角色ID列表
     */
    void updateUserRoles(Integer uid, Set<Integer> rids);

    /**
     * 解除用户与角色关联
     *
     * @param uid  用户ID
     * @param rids 默认null解除全部
     */
    void deleteUserRoles(Integer uid, Set<Integer> rids);

    /**
     * 绑定用户与角色
     * @param uid 用户ID
     * @param rids 角色ID列表
     */
    void addUserRoles(Integer uid,Set<Integer> rids);
}
