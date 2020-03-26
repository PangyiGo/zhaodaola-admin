package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Announce;
import com.sise.zhaodaola.business.entity.Comment;
import com.sise.zhaodaola.business.service.dto.CommentQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.vo.CommentQueryVo;
import com.sise.zhaodaola.tool.utils.PageHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface CommentService extends IService<Comment> {


    /**
     * 查询评论列表
     *
     * @param commentQueryDto /
     * @param queryCriteria   /
     * @return /
     */
    PageHelper getComment(CommentQueryDto commentQueryDto, PageQueryCriteria queryCriteria);

    /**
     * 导出数据
     *
     * @param commentQueryVos /
     * @param response        /
     */
    void download(List<CommentQueryVo> commentQueryVos, HttpServletResponse response) throws IOException;

    /**
     * 查询
     *
     * @param commentQueryDto /
     * @return /
     */
    List<CommentQueryVo> getAll(CommentQueryDto commentQueryDto);

    /**
     * 删除评论
     *
     * @param commentIds /
     */
    void deelteComment(List<Integer> commentIds);

    /**
     * 提交评论
     *
     * @param comment /
     */
    void submitComment(Comment comment);

    /**
     * 显示帖子评论列表
     *
     * @param postCode /
     * @return /
     */
    PageHelper showComment(String postCode, PageQueryCriteria queryCriteria);
}
