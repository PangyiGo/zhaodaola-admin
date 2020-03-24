package com.sise.zhaodaola.core.message;

import com.sise.zhaodaola.business.service.CommentService;
import com.sise.zhaodaola.business.service.dto.CommentQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.annotation.Log;
import com.sise.zhaodaola.tool.utils.PageHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
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
    @PreAuthorize("@auth.check('comment:list')")
    @PostMapping("/list")
    public ResponseEntity<Object> getCommentList(CommentQueryDto commentQueryDto, PageQueryCriteria queryCriteria) {
        PageHelper comment = commentService.getComment(commentQueryDto, queryCriteria);
        return ResponseEntity.ok(comment);
    }

    @Log("评论数据导出")
    @PreAuthorize("@auth.check('comment:list')")
    @PostMapping("/download")
    public void getCommentList(CommentQueryDto commentQueryDto, HttpServletResponse response) throws IOException {
        commentService.download(commentService.getAll(commentQueryDto), response);
    }

    @Log("删除评论")
    @PreAuthorize("@auth.check('comment:delete')")
    @PostMapping("/delete")
    public ResponseEntity<Object> deleteComment(@RequestBody List<Integer> commentIds) {
        commentService.deelteComment(commentIds);
        return ResponseEntity.ok("删除评论成功");
    }
}
