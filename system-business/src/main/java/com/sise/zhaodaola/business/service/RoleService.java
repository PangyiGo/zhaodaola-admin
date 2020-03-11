package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface RoleService extends IService<Role> {

    /**
     * 获取指定用户ID的权限列表
     *
     * @param uid 用户ID
     * @return /
     */
    Collection<GrantedAuthority> mapToGrantedAuthorization(Integer uid);
}
