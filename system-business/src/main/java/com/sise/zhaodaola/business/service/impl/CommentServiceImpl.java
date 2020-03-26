package com.sise.zhaodaola.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Comment;
import com.sise.zhaodaola.business.entity.Found;
import com.sise.zhaodaola.business.entity.Lost;
import com.sise.zhaodaola.business.entity.User;
import com.sise.zhaodaola.business.mapper.CommentMapper;
import com.sise.zhaodaola.business.service.CommentService;
import com.sise.zhaodaola.business.service.FoundService;
import com.sise.zhaodaola.business.service.LostService;
import com.sise.zhaodaola.business.service.UserService;
import com.sise.zhaodaola.business.service.dto.CommentQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.vo.CommentQueryVo;
import com.sise.zhaodaola.business.service.vo.CommentShowVo;
import com.sise.zhaodaola.tool.dict.DictManager;
import com.sise.zhaodaola.tool.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private LostService lostService;

    @Autowired
    private FoundService foundService;

    @Autowired
    private UserService userService;


    @Override
    public PageHelper getComment(CommentQueryDto commentQueryDto, PageQueryCriteria queryCriteria) {
        Page<Comment> commentPage = new Page<>(queryCriteria.getPage(), queryCriteria.getSize());
        Page<Comment> result = super.page(commentPage, wrapper(commentQueryDto));
        List<CommentQueryVo> queryVoList = recode(result.getRecords());
        return PageUtils.toPage(queryVoList, result.getCurrent(), result.getSize(), result.getTotal());
    }

    @Override
    public void download(List<CommentQueryVo> commentQueryVos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>(0);
        commentQueryVos.forEach(commentQueryVo -> {
            Map<String, Object> map = new HashMap<>();
            map.put("ID", "");
            map.put("帖子编号", commentQueryVo.getPostCode());
            map.put("帖子标题", commentQueryVo.getTitle());
            map.put("类型", DictManager.comment(commentQueryVo.getType()));
            map.put("评论者", commentQueryVo.getFromUsername());
            map.put("被评论者", commentQueryVo.getToUsername());
            map.put("内容", commentQueryVo.getContent());
            map.put("父级ID", commentQueryVo.getPid());
            map.put("评论时间", DateUtil.format(commentQueryVo.getCreateTime(), DatePattern.NORM_DATETIME_PATTERN));
            mapList.add(map);
        });
        FileUtils.downloadExcel(mapList, response);
    }

    @Override
    public List<CommentQueryVo> getAll(CommentQueryDto commentQueryDto) {
        return recode(super.list(wrapper(commentQueryDto)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deelteComment(List<Integer> commentIds) {
        super.remove(Wrappers.<Comment>lambdaUpdate().in(Comment::getPid, commentIds));
        super.removeByIds(commentIds);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void submitComment(Comment comment) {
        Integer id = userService.findByUsername(SecurityUtils.getUsername()).getId();
        comment.setUserId(id);
        comment.setCreateTime(LocalDateTime.now());
        super.save(comment);
    }

    @Override
    public PageHelper showComment(String postCode, PageQueryCriteria queryCriteria) {
        Page<Comment> commentPage = new Page<>(queryCriteria.getPage(), queryCriteria.getSize());
        LambdaQueryWrapper<Comment> query = Wrappers.<Comment>lambdaQuery()
                .eq(Comment::getPostCode, postCode)
                .eq(Comment::getPid, 0)
                .orderByDesc(Comment::getCreateTime);
        Page<Comment> page = super.page(commentPage, query);


        List<CommentShowVo> commentShowVoList = new ArrayList<>(0);

        page.getRecords().forEach(comment -> {
            CommentQueryVo commentQueryVo = single(comment);
            LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = Wrappers.<Comment>lambdaQuery()
                    .eq(Comment::getPid, commentQueryVo.getId())
                    .orderByDesc(Comment::getCreateTime);
            List<Comment> commentList = super.list(commentLambdaQueryWrapper);

            CommentShowVo commentShowVo = new CommentShowVo();
            BeanUtil.copyProperties(commentQueryVo, commentShowVo);

            commentShowVo.setCount(commentList.size());
            commentShowVo.setChildren(recode(commentList));

            commentShowVoList.add(commentShowVo);
        });

        return PageUtils.toPage(commentShowVoList, page.getCurrent(), page.getSize(), page.getTotal());
    }


    private List<CommentQueryVo> recode(List<Comment> commentList) {
        List<CommentQueryVo> commentQueryVoList = new ArrayList<>(0);
        commentList.forEach(comment -> {
            commentQueryVoList.add(single(comment));
        });
        return commentQueryVoList;
    }

    private CommentQueryVo single(Comment comment) {
        if (comment == null) return new CommentQueryVo();
        CommentQueryVo commentQueryVo = new CommentQueryVo();
        BeanUtil.copyProperties(comment, commentQueryVo);
        // post title
        String title = "";
        if (comment.getType() == 1) {
            Lost lost = lostService.getByUuid(comment.getPostCode());
            if (ObjectUtils.isNotNull(lost))
                title = lost.getTitle();
        } else {
            Found found = foundService.getByUuid(comment.getPostCode());
            if (ObjectUtils.isNotNull(found))
                title = found.getTitle();
        }
        commentQueryVo.setTitle(title);
        // user name
        if (comment.getUserId() != null) {
            User fromUser = userService.getById(comment.getUserId());
            commentQueryVo.setFromUsername(fromUser.getUsername());
            commentQueryVo.setAvatar(fromUser.getAvatar());
        }
        if (comment.getReplayId() != null) {
            User toUser = userService.getById(comment.getReplayId());
            commentQueryVo.setToUsername(toUser.getUsername());
        }
        return commentQueryVo;
    }

    private LambdaQueryWrapper<Comment> wrapper(CommentQueryDto commentQueryDto) {
        LambdaQueryWrapper<Comment> wrapper = Wrappers.<Comment>lambdaQuery();
        if (commentQueryDto != null) {
            wrapper.like(StringUtils.isNoneBlank(commentQueryDto.getWord()), Comment::getContent, commentQueryDto.getWord());
            wrapper.eq(commentQueryDto.getType() > 0, Comment::getType, commentQueryDto.getType());
            wrapper.eq(StringUtils.isNoneBlank(commentQueryDto.getPostCode()), Comment::getPostCode, commentQueryDto.getPostCode());
            if (StringUtils.isNoneBlank(commentQueryDto.getUsernmae())) {
                LambdaQueryWrapper<User> select = Wrappers.<User>lambdaQuery().like(User::getUsername, commentQueryDto.getUsernmae()).select(User::getId);
                List<User> userList = userService.list(select);
                List<Integer> userIds = userList.stream().map(User::getId).collect(Collectors.toList());
                wrapper.in(Comment::getUserId, userIds);
            }
            if (StringUtils.isNotBlank(commentQueryDto.getStart()) && StringUtils.isNotBlank(commentQueryDto.getEnd())) {
                wrapper.between(Comment::getCreateTime,
                        DateTimeUtils.dateTime(commentQueryDto.getStart(),
                                DatePattern.NORM_DATETIME_PATTERN),
                        DateTimeUtils.dateTime(commentQueryDto.getEnd(),
                                DatePattern.NORM_DATETIME_PATTERN));
            }
        }
        return wrapper;
    }
}
