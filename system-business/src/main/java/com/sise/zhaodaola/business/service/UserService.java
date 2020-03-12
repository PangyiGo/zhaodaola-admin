package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.User;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.dto.UserDto;
import com.sise.zhaodaola.business.service.dto.UserQueryDto;
import com.sise.zhaodaola.tool.utils.PageHelper;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查询
     *
     * @param username /
     * @return /
     */
    UserDto findByUsername(String username);

    /**
     * 分页查询用户列表
     *
     * @param userQueryDto 条件
     * @param criteria     分页
     * @return /
     */
    PageHelper getUserList(UserQueryDto userQueryDto, PageQueryCriteria criteria);
}
