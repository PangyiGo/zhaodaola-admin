package com.sise.zhaodaola.core.lostAndfound;

import com.sise.zhaodaola.business.entity.Category;
import com.sise.zhaodaola.business.service.CategoryService;
import com.sise.zhaodaola.business.service.dto.CategoryQueryDto;
import com.sise.zhaodaola.tool.annotation.AnonymousAccess;
import com.sise.zhaodaola.tool.annotation.Log;
import com.sise.zhaodaola.tool.utils.PageHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/1410:13 下午
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Log("物品分类查询")
    @PostMapping("/list")
    public ResponseEntity<Object> getCategory(CategoryQueryDto queryDto) {
        PageHelper categoryList = categoryService.getCategoryList(queryDto);
        return ResponseEntity.ok(categoryList);
    }

    @Log("物品分类数据导出")
    @PostMapping("/download")
    public void download(CategoryQueryDto categoryQueryDto, HttpServletResponse response) throws IOException {
        categoryService.download(categoryService.getAllList(categoryQueryDto), response);
    }

    @Log("物品分类新增")
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Category category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>("新增物品分类成功", HttpStatus.OK);
    }

    @Log("物品分类删除")
    @PostMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody List<Integer> cids) {
        categoryService.deleteCategory(cids);
        return new ResponseEntity<>("物品分类删除成功", HttpStatus.OK);
    }

    @Log("物品分类修改")
    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Category category) {
        categoryService.updateCategory(category);
        return new ResponseEntity<>("物品分类修改成功", HttpStatus.OK);
    }

    // 获取全部分类信息
    @AnonymousAccess
    @PostMapping("/getall")
    public ResponseEntity<Object> getAll() {
        CategoryQueryDto queryDto = new CategoryQueryDto();
        queryDto.setStatus(1);
        List<Category> allList = categoryService.getAllList(queryDto);
        return ResponseEntity.ok(allList);
    }
}
