package com.sise.zhaodaola.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.*;
import com.sise.zhaodaola.business.mapper.MessageMapper;
import com.sise.zhaodaola.business.service.*;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.dto.UserDto;
import com.sise.zhaodaola.business.service.vo.MessageQueryVo;
import com.sise.zhaodaola.tool.utils.PageHelper;
import com.sise.zhaodaola.tool.utils.PageUtils;
import com.sise.zhaodaola.tool.utils.SecurityUtils;
import com.sise.zhaodaola.tool.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.print.attribute.standard.NumberUp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private LostService lostService;

    @Autowired
    private FoundService foundService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Override
    public PageHelper getMessage(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria) {
        Page<Message> messagePage = new Page<>(queryCriteria.getPage(), queryCriteria.getSize());
        Page<Message> page = super.page(messagePage, wrapper(basicQueryDto));
        return PageUtils.toPage(recode(page.getRecords()), page.getCurrent(), page.getSize(), page.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCommentMessage(Comment comment) {
        Message message = new Message();
        message.setType(2);
        message.setCommentId(comment.getId());
        message.setStatus(1);
        message.setCreateTime(LocalDateTime.now());
        message.setPortCode(comment.getPostCode());
        message.setContent(comment.getContent());
        // 接受者
        message.setToUser(comment.getReplayId());
        // 发送者
        message.setFromUser(comment.getUserId());

        super.save(message);
    }

    @Override
    public int showNoreadMessage(int type) {
        BasicQueryDto basicQueryDto = new BasicQueryDto();
        basicQueryDto.setUsername(SecurityUtils.getUsername());
        basicQueryDto.setStatus(1);
        basicQueryDto.setCategory(type);
        List<Message> list = super.list(wrapper(basicQueryDto));
        return list.size();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setMsgread(int type) {
        LambdaUpdateWrapper<Message> eq = Wrappers.<Message>lambdaUpdate().set(Message::getStatus, 2)
                .eq(Message::getToUser, userService.findByUsername(SecurityUtils.getUsername()).getId()).eq(Message::getStatus, 1)
                .eq(Message::getType, type);
        super.update(eq);
    }


    private List<MessageQueryVo> recode(List<Message> messageList) {
        List<MessageQueryVo> messageQueryVoList = new ArrayList<>(0);
        messageList.forEach(message -> {
            messageQueryVoList.add(single(message));
        });
        return messageQueryVoList;
    }

    private MessageQueryVo single(Message message) {
        if (message == null) return new MessageQueryVo();
        MessageQueryVo messageQueryVo = new MessageQueryVo();
        // copy
        BeanUtils.copyProperties(message, messageQueryVo);

        // search fromuser and touser
        if (message.getFromUser() != null) {
            User user = userService.getById(message.getFromUser()); //消息发送者
            messageQueryVo.setFromUsername(user.getUsername());
            messageQueryVo.setFromAvatar(user.getAvatar());
            messageQueryVo.setFromNickName(user.getNickName());
        }
        if (message.getToUser() != null) {
            User user = userService.getById(message.getToUser()); //消息接受者
            messageQueryVo.setToUsername(user.getUsername());
            messageQueryVo.setToAvatar(user.getAvatar());
            messageQueryVo.setToNickName(user.getNickName());
        }

        if (message.getType() == 2) {
            // 评论消息
            Comment comment = commentService.getById(message.getCommentId());
            if (comment.getType() == 1) {
                messageQueryVo.setCommentType(comment.getType());
                // 寻物评论
                Lost lost = lostService.getOne(Wrappers.<Lost>lambdaQuery().eq(Lost::getUuid, message.getPortCode()));
                if (lost != null) {
                    messageQueryVo.setPortId(lost.getId());
                    messageQueryVo.setPortTitle(lost.getTitle());
                }
            } else {
                Found found = foundService.getOne(Wrappers.<Found>lambdaQuery().eq(Found::getUuid, message.getPortCode()));
                if (found != null) {
                    messageQueryVo.setPortId(found.getId());
                    messageQueryVo.setPortTitle(found.getTitle());
                }
            }
        }

        if (message.getType() == 3) {
            // 认领确定
            Found found = foundService.getOne(Wrappers.<Found>lambdaQuery().eq(Found::getUuid, message.getPortCode()));
            if (found != null) {
                messageQueryVo.setPortId(found.getId());
                messageQueryVo.setPortTitle(found.getTitle());
            }
        }

        return messageQueryVo;
    }

    private LambdaQueryWrapper<Message> wrapper(BasicQueryDto basicQueryDto) {
        LambdaQueryWrapper<Message> wrapper = Wrappers.<Message>lambdaQuery();
        if (StringUtils.isNoneBlank(basicQueryDto.getUsername())) {
            UserDto userDto = userService.findByUsername(basicQueryDto.getUsername());
            if (userDto != null) {
                // 获取我的信息
                wrapper.eq(Message::getToUser, userDto.getId());
            }
        }
        wrapper.eq(basicQueryDto.getStatus() > 0, Message::getStatus, basicQueryDto.getStatus());
        wrapper.eq(basicQueryDto.getCategory() > 0, Message::getType, basicQueryDto.getCategory());
        wrapper.orderByDesc(Message::getCreateTime);
        return wrapper;
    }
}
