package com.sise.zhaodaola.core.lostAndfound;

import com.sise.zhaodaola.business.service.PictureService;
import com.sise.zhaodaola.tool.annotation.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * User: PangYi
 * Date: 2020-03-16
 * Time: 10:23
 * Description:
 */
@RestController
@RequestMapping("/api/picture")
public class PictureController {

    private PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @Log("文件上传")
    @PostMapping("/upload")
    public ResponseEntity<Object> uploadImages(@RequestParam("file") MultipartFile[] files) {
        List<String> list = pictureService.saveFile(Arrays.asList(files));
        return ResponseEntity.ok(list);
    }
}
