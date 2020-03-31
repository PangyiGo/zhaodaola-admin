package com.sise.zhaodaola.core.message;

import com.sise.zhaodaola.business.entity.Comment;
import com.sise.zhaodaola.business.service.CommentService;
import com.sise.zhaodaola.business.service.dto.CommentQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.annotation.Log;
import com.sise.zhaodaola.tool.utils.PageHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/249:46 下午
 */
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Log("查询评论")
    @PostMapping("/list")
    public ResponseEntity<Object> getCommentList(CommentQueryDto commentQueryDto, PageQueryCriteria queryCriteria) {
        PageHelper comment = commentService.getComment(commentQueryDto, queryCriteria);
        return ResponseEntity.ok(comment);
    }

    @Log("评论数据导出")
    @PostMapping("/download")
    public void getCommentList(CommentQueryDto commentQueryDto, HttpServletResponse response) throws IOException {
        commentService.download(commentService.getAll(commentQueryDto), response);
    }

    @Log("删除评论")
    @PostMapping("/delete")
    public ResponseEntity<Object> deleteComment(@RequestBody List<Integer> commentIds) {
        commentService.deelteComment(commentIds);
        return ResponseEntity.ok("删除评论成功");
    }

    @Log("提交评论")
    @PostMapping("/submitComment")
    public ResponseEntity<Object> submitComment(@RequestBody Comment comment) {
        commentService.submitComment(comment);
        return ResponseEntity.ok("提交评论成功");
    }

    @PostMapping("/showComment")
    public ResponseEntity<Object> showComentList(String postCode, PageQueryCriteria queryCriteria) {
        PageHelper pageHelper = commentService.showComment(postCode, queryCriteria);
        return ResponseEntity.ok(pageHelper);
    }
}
