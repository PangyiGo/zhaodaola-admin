package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.User;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.dto.UserDto;
import com.sise.zhaodaola.business.service.dto.UserQueryDto;
import com.sise.zhaodaola.business.service.dto.UserUpdateDto;
import com.sise.zhaodaola.tool.utils.PageHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
     * 不分页查询用户列表
     *
     * @param userQueryDto 条件
     * @return /
     */
    List<User> findAll(UserQueryDto userQueryDto);

    /**
     * 导出用户数据
     *
     * @param userList 导出用户的数据
     * @param response /
     */
    void downloadUser(List<User> userList, HttpServletResponse response) throws IOException;

    /**
     * 分页查询用户列表
     *
     * @param userQueryDto 条件
     * @param criteria     分页
     * @return /
     */
    PageHelper getUserList(UserQueryDto userQueryDto, PageQueryCriteria criteria);

    /**
     * 用户信息修改
     *
     * @param userUpdateDto 数据
     */
    void updateUser(UserUpdateDto userUpdateDto);

    /**
     * 重置密码
     *
     * @param uid 用户ID
     */
    void resetPasswordUser(List<Integer> uid);
}
