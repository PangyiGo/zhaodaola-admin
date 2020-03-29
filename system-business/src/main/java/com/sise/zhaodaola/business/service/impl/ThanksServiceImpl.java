package com.sise.zhaodaola.business.service.impl;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.News;
import com.sise.zhaodaola.business.entity.Site;
import com.sise.zhaodaola.business.entity.Thanks;
import com.sise.zhaodaola.business.entity.User;
import com.sise.zhaodaola.business.mapper.ThanksMapper;
import com.sise.zhaodaola.business.service.ThanksService;
import com.sise.zhaodaola.business.service.UserService;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.dto.UserDto;
import com.sise.zhaodaola.business.service.vo.ThanksQueryVo;
import com.sise.zhaodaola.tool.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ThanksServiceImpl extends ServiceImpl<ThanksMapper, Thanks> implements ThanksService {

    @Autowired
    private UserService userService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createThankes(Thanks thanks) {
        UserDto userDto = userService.findByUsername(SecurityUtils.getUsername());
        thanks.setUserId(userDto.getId());
        thanks.setCreateTime(LocalDateTime.now());
        thanks.setStatus(1);
        super.save(thanks);
    }

    @Override
    public PageHelper getList(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria) {
        Page<Thanks> thanksPage = new Page<>(queryCriteria.getPage(), queryCriteria.getSize());
        Page<Thanks> page = super.page(thanksPage, wrapper(basicQueryDto));
        List<ThanksQueryVo> thanksQueryVoList = new ArrayList<>(0);
        page.getRecords().forEach(thanks -> {
            ThanksQueryVo thanksQueryVo = new ThanksQueryVo();
            BeanUtils.copyProperties(thanks, thanksQueryVo);

            User user = userService.getById(thanks.getUserId());
            thanksQueryVo.setUsername(user.getUsername());
            thanksQueryVo.setNickName(user.getNickName());
            thanksQueryVo.setAvatar(user.getAvatar());

            thanksQueryVoList.add(thanksQueryVo);
        });
        return PageUtils.toPage(thanksQueryVoList, page.getCurrent(), page.getSize(), page.getTotal());
    }

    private LambdaQueryWrapper<Thanks> wrapper(BasicQueryDto basicQueryDto) {
        LambdaQueryWrapper<Thanks> wrapper = Wrappers.<Thanks>lambdaQuery();
        wrapper.eq(basicQueryDto.getStatus() != 0, Thanks::getStatus, basicQueryDto.getStatus());
        wrapper.and(StringUtils.isNoneBlank(basicQueryDto.getWord()), q -> {
            q.or().like(Thanks::getTitle, basicQueryDto.getWord());
            q.or().like(Thanks::getContent, basicQueryDto.getWord());
        });
        if (StringUtils.isNoneBlank(basicQueryDto.getUsername())) {
            LambdaQueryWrapper<User> like = Wrappers.<User>lambdaQuery().like(User::getUsername, basicQueryDto.getUsername());
            List<User> list = userService.list(like);
            if (list != null && list.size() > 0)
                wrapper.in(Thanks::getUserId, list.stream().map(User::getId).collect(Collectors.toList()));
            else
                wrapper.in(Thanks::getUserId, Arrays.asList(-1));
        }
        if (StringUtils.isNotBlank(basicQueryDto.getStart()) && StringUtils.isNotBlank(basicQueryDto.getEnd()))
            wrapper.between(Thanks::getCreateTime, DateTimeUtils.dateTime(basicQueryDto.getStart(),
                    DatePattern.NORM_DATETIME_PATTERN), DateTimeUtils.dateTime(basicQueryDto.getEnd(),
                    DatePattern.NORM_DATETIME_PATTERN));
        wrapper.orderByDesc(Thanks::getCreateTime);
        return wrapper;
    }
}
