package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Announce;
import com.sise.zhaodaola.business.entity.Category;
import com.sise.zhaodaola.business.service.dto.CategoryQueryDto;
import com.sise.zhaodaola.tool.utils.PageHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface CategoryService extends IService<Category> {

    /**
     * 分页查询物品分类列表
     *
     * @param categoryQueryDto /
     * @return /
     */
    PageHelper getCategoryList(CategoryQueryDto categoryQueryDto);

    /**
     * 不分页查询物品分类列表
     *
     * @param categoryQueryDto /
     * @return /
     */
    List<Category> getAllList(CategoryQueryDto categoryQueryDto);

    /**
     * 导出数据
     *
     * @param categoryList /
     * @param response     /
     */
    void download(List<Category> categoryList, HttpServletResponse response) throws IOException;

    /**
     * 删除物品分类，存在外键关联无法删除
     *
     * @param cids /
     */
    void deleteCategory(List<Integer> cids);

    /**
     * 物品分类修改
     *
     * @param category /
     */
    void updateCategory(Category category);

    /**
     * 新增物品分类
     *
     * @param category /
     */
    void createCategory(Category category);
}
