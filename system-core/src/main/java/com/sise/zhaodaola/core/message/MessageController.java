package com.sise.zhaodaola.core.message;

import com.sise.zhaodaola.business.service.MessageService;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.utils.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: PangYi
 * @Date 2020/3/3012:00 上午
 */
@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/getMessage")
    public ResponseEntity<Object> getMessage(BasicQueryDto basicQueryDto, PageQueryCriteria pageQueryCriteria) {
        PageHelper pageHelper = messageService.getMessage(basicQueryDto, pageQueryCriteria);
        return ResponseEntity.ok(pageHelper);
    }

    @PostMapping("/getNoread/{type}")
    public ResponseEntity<Object> showNoread(@PathVariable("type") int type) {
        int noreadMessage = messageService.showNoreadMessage(type);
        return ResponseEntity.ok(noreadMessage);
    }

    @PostMapping("/setMsgread/{type}")
    public ResponseEntity<Object> setMsgread(@PathVariable("type") int type) {
        messageService.setMsgread(type);
        return ResponseEntity.ok("设置信息已读");
    }


    @PostMapping("/delete/{msgId}")
    public ResponseEntity<Object> delete(@PathVariable("msgId") Integer msgId) {
        messageService.removeById(msgId);
        return ResponseEntity.ok("删除");
    }
}
