package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Comment;
import com.sise.zhaodaola.business.entity.Message;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.utils.PageHelper;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface MessageService extends IService<Message> {

    /**
     * 查询用户信息，1表示系统信息，2表示评论信息，3表示认领确认信息
     *
     * @param basicQueryDto /
     * @param queryCriteria /
     * @return /
     */
    PageHelper getMessage(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria);

    /**
     * 保存回复评论消息
     *
     * @param comment /
     */
    void saveCommentMessage(Comment comment);

    /**
     * 查看未读消息个数
     *
     * @return /
     */
    int showNoreadMessage(int type);

    /**
     * 标记已读消息
     *
     * @param type /
     */
    void setMsgread(int type);
}
