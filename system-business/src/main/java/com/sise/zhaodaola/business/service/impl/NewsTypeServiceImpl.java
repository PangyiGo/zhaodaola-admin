package com.sise.zhaodaola.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.NewsType;
import com.sise.zhaodaola.business.mapper.NewsTypeMapper;
import com.sise.zhaodaola.business.service.NewsTypeService;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.exception.BadRequestException;
import com.sise.zhaodaola.tool.utils.PageHelper;
import com.sise.zhaodaola.tool.utils.PageUtils;
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
public class NewsTypeServiceImpl extends ServiceImpl<NewsTypeMapper, NewsType> implements NewsTypeService {

    @Override
    public PageHelper getList(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria) {
        Page<NewsType> newsTypePage = new Page<>(queryCriteria.getPage(), queryCriteria.getSize());
        Page<NewsType> typePage = super.page(newsTypePage, wrapper(basicQueryDto));
        return PageUtils.toPage(typePage.getRecords(), typePage.getCurrent(), typePage.getSize(), typePage.getTotal());
    }

    @Override
    public List<NewsType> getAll() {
        BasicQueryDto basicQueryDto = new BasicQueryDto();
        basicQueryDto.setStatus(1);
        return super.list(wrapper(basicQueryDto));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createNewsType(NewsType newsType) {
        newsType.setCreateTime(LocalDateTime.now());
        super.save(newsType);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateNewsType(NewsType newsType) {
        super.updateById(newsType);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteNewsType(List<Integer> typeIds) {
        try {
            super.removeByIds(typeIds);
        } catch (Exception e) {
            throw new BadRequestException("存在数据关联，无法删除，请解除绑定");
        }
    }

    private LambdaQueryWrapper<NewsType> wrapper(BasicQueryDto basicQueryDto) {
        LambdaQueryWrapper<NewsType> wrapper = Wrappers.<NewsType>lambdaQuery();
        wrapper.and(StringUtils.isNotBlank(basicQueryDto.getWord()), query -> {
            query.or().like(NewsType::getName, basicQueryDto.getWord());
            query.or().like(NewsType::getRemark, basicQueryDto.getWord());
        });
        wrapper.eq(basicQueryDto.getStatus() > 0, NewsType::getStatus, basicQueryDto.getStatus());
        return wrapper;
    }
}
