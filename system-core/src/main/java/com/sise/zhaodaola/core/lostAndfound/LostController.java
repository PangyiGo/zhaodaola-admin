package com.sise.zhaodaola.core.lostAndfound;

import com.sise.zhaodaola.tool.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: PangYi
 * Date: 2020-03-13
 * Time: 15:50
 * Description: 寻物
 */
@RestController
@RequestMapping("/api/losts")
@Slf4j
public class LostController {

    @Log("查询寻物列表")
    @PreAuthorize("@auth.check('lost:list')")
    @PostMapping("/list")
    public ResponseEntity<Object> getLosts() {
        return null;
    }
}
