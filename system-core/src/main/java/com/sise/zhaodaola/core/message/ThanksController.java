package com.sise.zhaodaola.core.message;

import com.sise.zhaodaola.business.entity.Thanks;
import com.sise.zhaodaola.business.service.ThanksService;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.annotation.Log;
import com.sise.zhaodaola.tool.utils.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/2911:14 上午
 */
@RestController
@RequestMapping("/api/thanks")
public class ThanksController {

    @Autowired
    private ThanksService thanksService;

    @Log("发表留言")
    @PostMapping("/create")
    public ResponseEntity<Object> createThanks(@RequestBody Thanks thanks) {
        thanksService.createThankes(thanks);
        return ResponseEntity.ok("发表成功");
    }

    @PostMapping("/getlist")
    public ResponseEntity<Object> getList(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria) {
        PageHelper list = thanksService.getList(basicQueryDto, queryCriteria);
        return ResponseEntity.ok(list);
    }

    @Log("删除留言")
    @PostMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody List<Integer> commentIds) {
        thanksService.removeByIds(commentIds);
        return ResponseEntity.ok("删除成功");
    }
}
