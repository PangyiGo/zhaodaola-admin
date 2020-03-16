package com.sise.zhaodaola.business.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Picture;
import com.sise.zhaodaola.business.mapper.PictureMapper;
import com.sise.zhaodaola.business.service.PictureService;
import com.sise.zhaodaola.tool.exception.BadRequestException;
import com.sise.zhaodaola.tool.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {

    @Value("${file.path}")
    private String path;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<String> saveFile(List<MultipartFile> files) {
        List<String> fileNames = new ArrayList<>(0);
        files.forEach(multipartFile -> {
            String suffix = FileUtils.getExtensionName(multipartFile.getOriginalFilename());
            String type = FileUtils.getFileType(suffix);
            File file = FileUtils.upload(multipartFile, path + type);
            if (ObjectUtil.isNull(file)) {
                throw new BadRequestException("上传失败");
            }
            Picture picture = new Picture();
            picture.setFileName(file.getName());
            picture.setRealName(multipartFile.getOriginalFilename());
            picture.setPath(file.getPath());
            picture.setSize(FileUtils.getSize(multipartFile.getSize()));
            picture.setCreateTime(LocalDateTime.now());
            super.save(picture);
            // 文件名
            fileNames.add(picture.getFileName());
        });
        return fileNames;
    }
}
