package com.sise.zhaodaola.business.service.impl;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Category;
import com.sise.zhaodaola.business.mapper.CategoryMapper;
import com.sise.zhaodaola.business.service.CategoryService;
import com.sise.zhaodaola.business.service.dto.CategoryQueryDto;
import com.sise.zhaodaola.tool.dict.DictManager;
import com.sise.zhaodaola.tool.exception.BadRequestException;
import com.sise.zhaodaola.tool.utils.FileUtils;
import com.sise.zhaodaola.tool.utils.PageHelper;
import com.sise.zhaodaola.tool.utils.PageUtils;
import com.sise.zhaodaola.tool.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Wrapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public PageHelper getCategoryList(CategoryQueryDto categoryQueryDto) {
        Page<Category> categoryPage = new Page<>(categoryQueryDto.getPage(), categoryQueryDto.getSize());
        IPage<Category> page = super.page(categoryPage, wrapper(categoryQueryDto));
        return PageUtils.toPage(page.getRecords(), page.getCurrent(), page.getSize(), page.getTotal());
    }

    @Override
    public List<Category> getAllList(CategoryQueryDto categoryQueryDto) {
        return super.list(wrapper(categoryQueryDto));
    }

    @Override
    public void download(List<Category> categoryList, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);
        categoryList.forEach(category -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("名称", category.getName());
            map.put("备注", category.getAbout());
            map.put("创建时间", formatter.format(category.getCreateTime()));
            map.put("更新时间", formatter.format(category.getUpdateTime()));
            map.put("状态", DictManager.show(category.getStatus()));
            mapList.add(map);
        });
        FileUtils.downloadExcel(mapList, response);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCategory(List<Integer> cids) {
        super.removeByIds(cids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCategory(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        super.updateById(category);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createCategory(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        LambdaQueryWrapper<Category> wrapper = Wrappers.<Category>lambdaQuery().eq(StringUtils.isNoneBlank(category.getName()), Category::getName, category.getName());
        if (ObjectUtils.isNotEmpty(super.getOne(wrapper)))
            throw new BadRequestException("新增物品名称：" + category.getName() + "已存在，不允许重复添加");
        super.save(category);
    }

    @Override
    public Category findbyName(String name) {
        return super.getOne(Wrappers.<Category>lambdaQuery().eq(Category::getName, name));
    }

    private LambdaQueryWrapper<Category> wrapper(CategoryQueryDto categoryQueryDto) {
        LambdaQueryWrapper<Category> wrapper = Wrappers.<Category>lambdaQuery();
        wrapper.eq(categoryQueryDto.getStatus() != 0, Category::getStatus, categoryQueryDto.getStatus());
        wrapper.and(StringUtils.isNoneBlank(categoryQueryDto.getWord()), q -> {
            q.or().like(Category::getName, categoryQueryDto.getWord());
            q.or().like(Category::getAbout, categoryQueryDto.getWord());
        });
        return wrapper;
    }


}
