package com.sise.zhaodaola.core.lostAndfound;

import com.sise.zhaodaola.business.service.LostService;
import com.sise.zhaodaola.business.service.dto.LostFoundBasicDto;
import com.sise.zhaodaola.tool.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    private LostService lostService;

    public LostController(LostService lostService) {
        this.lostService = lostService;
    }

    @Log("查询寻物列表")
    @PreAuthorize("@auth.check('lost:list')")
    @PostMapping("/list")
    public ResponseEntity<Object> getLosts() {
        return null;
    }

    @Log("寻物启事发布")
    @PostMapping("/publish")
    public ResponseEntity<Object> publishLost(@RequestBody LostFoundBasicDto lostFoundBasicDto) {
        lostService.publishLost(lostFoundBasicDto);
        return new ResponseEntity<>("寻物启事登记成功", HttpStatus.OK);
    }
}
