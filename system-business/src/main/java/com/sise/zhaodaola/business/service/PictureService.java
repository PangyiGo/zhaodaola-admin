package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface PictureService extends IService<Picture> {

    /**
     * 文件上传
     *
     * @param files 文件列表
     */
    List<String> saveFile(List<MultipartFile> files);
}
