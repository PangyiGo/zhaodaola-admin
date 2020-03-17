package com.sise.zhaodaola.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Found;
import com.sise.zhaodaola.business.mapper.FoundMapper;
import com.sise.zhaodaola.business.service.FoundService;
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
public class FoundServiceImpl extends ServiceImpl<FoundMapper, Found> implements FoundService {

    private UserService userService;

    public FoundServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publishLost(LostFoundBasicDto lostFoundBasicDto) {
        Found found = new Found();
        // copy attribute
        BeanUtil.copyProperties(lostFoundBasicDto, found);
        if (StringUtils.isNotBlank(lostFoundBasicDto.getLostTime()))
            try {
                found.setLostTime(DateUtil.toLocalDateTime(DateUtil.parse(lostFoundBasicDto.getLostTime())));
            } catch (Exception e) {
                throw new BadRequestException("时间格式化异常");
            }
        if (ObjectUtil.isNotNull(lostFoundBasicDto.getImages())) {
            List<String> images = lostFoundBasicDto.getImages();
            String imagesUrl = StringUtils.join(images, ",");
            found.setImages(imagesUrl);
        }
        // init
        UserDto userDto = userService.findByUsername(SecurityUtils.getUsername());
        found.setUserId(userDto.getId());
        found.setUuid(IdUtil.simpleUUID());
        found.setStatus(1);
        found.setCreateTime(LocalDateTime.now());
        found.setUpdateTime(LocalDateTime.now());
        super.save(found);
    }
}
