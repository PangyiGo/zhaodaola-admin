package com.sise.zhaodaola.core.lostAndfound;

import com.sise.zhaodaola.business.service.PictureService;
import com.sise.zhaodaola.tool.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

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

    private PictureService pictureService;

    public LostController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @Log("查询寻物列表")
    @PreAuthorize("@auth.check('lost:list')")
    @PostMapping("/list")
    public ResponseEntity<Object> getLosts() {
        return null;
    }

    @Log("失物图片上传")
    @PostMapping("/upload")
    public ResponseEntity<Object> uploadImages(@RequestParam("file") MultipartFile[] files) {
        List<String> list = pictureService.saveFile(Arrays.asList(files));
        return ResponseEntity.ok(list);
    }
}
