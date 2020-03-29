package com.sise.zhaodaola.business.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Announce;
import com.sise.zhaodaola.business.entity.User;
import com.sise.zhaodaola.business.mapper.AnnounceMapper;
import com.sise.zhaodaola.business.service.AnnounceService;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.utils.*;
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
public class AnnouceServiceImpl extends ServiceImpl<AnnounceMapper, Announce> implements AnnounceService {


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createAnnounce(Announce announce) {
        announce.setUuid(IdUtil.simpleUUID());
        announce.setUsername(SecurityUtils.getUsername());
        announce.setCreateTime(LocalDateTime.now());
        announce.setUpdateTime(LocalDateTime.now());
        announce.setStatus(1);
        super.save(announce);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAnnounce(List<Integer> announceIds) {
        super.removeByIds(announceIds);
    }

    @Override
    public void updateAnnonce(Announce announce) {
        announce.setUpdateTime(LocalDateTime.now());
        super.updateById(announce);
    }

    @Override
    public PageHelper getAnnounceList(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria) {
        Page<Announce> announcePage = new Page<>(queryCriteria.getPage(), queryCriteria.getSize());
        Page<Announce> page = super.page(announcePage, wrapper(basicQueryDto));
        return PageUtils.toPage(page.getRecords(), page.getCurrent(), page.getSize(), page.getTotal());
    }

    @Override
    public Announce getOne(Integer announceId) {
        return super.getById(announceId);
    }

    @Override
    public List<Announce> showIndex() {
        Page<Announce> announcePage = new Page<>(1, 10);
        BasicQueryDto basicQueryDto = new BasicQueryDto();
        basicQueryDto.setStatus(1);
        Page<Announce> page = super.page(announcePage, wrapper(basicQueryDto));
        return page.getRecords();
    }

    @Override
    public PageHelper showList(PageQueryCriteria pageQueryCriteria) {
        Page<Announce> announcePage = new Page<>(pageQueryCriteria.getPage(), pageQueryCriteria.getSize());

        BasicQueryDto basicQueryDto = new BasicQueryDto();
        basicQueryDto.setStatus(1);

        Page<Announce> page = super.page(announcePage, wrapper(basicQueryDto));
        return PageUtils.toPage(page.getRecords(), page.getCurrent(), page.getSize(), page.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Announce showOne(Integer id) {
        Announce announce = super.getById(id);
        if (announce == null)
            return new Announce();
        announce.setBrowse(announce.getBrowse() + 1);
        super.updateById(announce);
        return announce;
    }

    private LambdaQueryWrapper<Announce> wrapper(BasicQueryDto basicQueryDto) {
        LambdaQueryWrapper<Announce> wrapper = Wrappers.<Announce>lambdaQuery();
        if (ObjectUtils.isNotEmpty(basicQueryDto)) {
            wrapper.and(StringUtils.isNoneBlank(basicQueryDto.getWord()), q -> {
                q.or().like(Announce::getTitle, basicQueryDto.getWord());
                q.or().like(Announce::getUsername, basicQueryDto.getWord());
            });
            wrapper.eq(basicQueryDto.getStatus() != 0, Announce::getStatus, basicQueryDto.getStatus());
            if (StringUtils.isNotBlank(basicQueryDto.getStart()) && StringUtils.isNotBlank(basicQueryDto.getEnd()))
                wrapper.between(Announce::getCreateTime, DateTimeUtils.dateTime(basicQueryDto.getStart(), DatePattern.NORM_DATETIME_PATTERN), DateTimeUtils.dateTime(basicQueryDto.getEnd(), DatePattern.NORM_DATETIME_PATTERN));
        }
        wrapper.orderByDesc(Announce::getCreateTime);
        return wrapper;
    }
}
