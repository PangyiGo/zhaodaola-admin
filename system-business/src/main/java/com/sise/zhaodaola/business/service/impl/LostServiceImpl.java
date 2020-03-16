package com.sise.zhaodaola.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Lost;
import com.sise.zhaodaola.business.mapper.LostMapper;
import com.sise.zhaodaola.business.service.LostService;
import com.sise.zhaodaola.business.service.UserService;
import com.sise.zhaodaola.business.service.dto.LostFoundBasicDto;
import com.sise.zhaodaola.business.service.dto.UserDto;
import com.sise.zhaodaola.tool.exception.BadRequestException;
import com.sise.zhaodaola.tool.utils.SecurityUtils;
import com.sise.zhaodaola.tool.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LostServiceImpl extends ServiceImpl<LostMapper, Lost> implements LostService {

    private UserService userService;

    public LostServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publishLost(LostFoundBasicDto lostFoundBasicDto) {
        Lost lost = new Lost();
        // copy attribute
        BeanUtil.copyProperties(lostFoundBasicDto, lost);
        if (StringUtils.isNotBlank(lostFoundBasicDto.getLostTime()))
            try {
                lost.setLostTime(DateUtil.parse(lostFoundBasicDto.getLostTime(), DatePattern.NORM_DATE_PATTERN));
            } catch (Exception e) {
                throw new BadRequestException("时间格式化异常");
            }
        if (ObjectUtil.isNotNull(lostFoundBasicDto.getImages())) {
            List<String> images = lostFoundBasicDto.getImages();
            String imagesUrl = StringUtils.join(images, ",");
            lost.setImages(imagesUrl);
        }
        // init
        UserDto userDto = userService.findByUsername(SecurityUtils.getUsername());
        lost.setUserId(userDto.getId());
        lost.setUuid("sss");
        lost.setStatus(1);
        lost.setCreateTime(LocalDateTime.now());
        lost.setUpdateTime(LocalDateTime.now());
        super.save(lost);
    }
}
