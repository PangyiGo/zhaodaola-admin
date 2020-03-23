package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Role;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.utils.PageHelper;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

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

    /**
     * 获取全部角色列表
     *
     * @return /
     */
    List<Role> getRoleList();

    /**
     * 添加角色
     *
     * @param role /
     */
    void createRole(Role role);

    /**
     * 删除角色
     *
     * @param roleIds /
     */
    void deleteRole(List<Integer> roleIds);

    /**
     * 修改角色
     *
     * @param role /
     */
    void updateRole(Role role);

    /**
     * 查询角色
     *
     * @param basicQueryDto /
     * @param queryCriteria /
     * @return /
     */
    PageHelper getROleList(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria);
}
