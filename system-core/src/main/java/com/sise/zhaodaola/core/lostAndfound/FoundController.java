package com.sise.zhaodaola.core.lostAndfound;

import com.sise.zhaodaola.business.service.FoundService;
import com.sise.zhaodaola.business.service.dto.LostFoundBasicDto;
import com.sise.zhaodaola.tool.annotation.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: PangYi
 * Date: 2020-03-16
 * Time: 10:28
 * Description:
 */
@RestController
@RequestMapping("/api/founds")
public class FoundController {

    private FoundService foundService;

    public FoundController(FoundService foundService) {
        this.foundService = foundService;
    }

    @Log("认领启事发布")
    @PostMapping("/publish")
    public ResponseEntity<Object> publishFound(@RequestBody LostFoundBasicDto lostFoundBasicDto) {
        foundService.publishLost(lostFoundBasicDto);
        return new ResponseEntity<>("认领启事登记成功", HttpStatus.OK);
    }
}
