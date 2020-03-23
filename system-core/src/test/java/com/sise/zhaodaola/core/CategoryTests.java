package com.sise.zhaodaola.core;

import cn.hutool.core.collection.CollectionUtil;
import com.sise.zhaodaola.business.entity.Category;
import com.sise.zhaodaola.business.service.CategoryService;
import com.sise.zhaodaola.business.service.dto.CategoryQueryDto;
import com.sise.zhaodaola.tool.utils.PageHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: PangYi
 * @Date 2020/3/1411:03 下午
 */
@SpringBootTest
class CategoryTests {

    @Autowired
    private CategoryService categoryService;

    @Test
    void test01() {
        CategoryQueryDto categoryQueryDto = new CategoryQueryDto();
        categoryQueryDto.setWord("卡");
        PageHelper list = categoryService.getCategoryList(categoryQueryDto);
        System.out.println(list);
    }

    @Test
    void test02() {
        Category category = new Category();
        category.setName("校园一卡通").setAbout("ss").setStatus(1);
        Category category2 = new Category();
        category2.setName("校园一卡通1").setAbout("ss").setStatus(1);
        Category category3 = new Category();
        category3.setName("校园一卡通2").setAbout("ss").setStatus(1);
        Category category4 = new Category();
        category4.setName("校园一卡通3").setAbout("ss").setStatus(1);

        categoryService.createCategory(category);
        categoryService.createCategory(category2);
        categoryService.createCategory(category3);
        categoryService.createCategory(category4);
    }

    @Test
    void test03(){
        Category category = new Category();
        category.setId(1);
        category.setAbout("ss");
        category.setName("钥匙");
        category.setStatus(2);
        categoryService.updateCategory(category);
    }

    @Test
    void test04(){
        categoryService.deleteCategory(CollectionUtil.newArrayList(1,2,3,4));
    }
}
