package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.User;
import com.sise.zhaodaola.business.service.dto.UserDto;

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
}
