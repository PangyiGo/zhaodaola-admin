package com.sise.zhaodaola.core.news;

import com.sise.zhaodaola.business.entity.NewsType;
import com.sise.zhaodaola.business.service.NewsTypeService;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.annotation.AnonymousAccess;
import com.sise.zhaodaola.tool.annotation.Log;
import com.sise.zhaodaola.tool.utils.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User: PangYi
 * Date: 2020-03-19
 * Time: 9:11
 * Description:
 */
@RestController
@Slf4j
@RequestMapping("/api/newsType/")
public class NewsTypeController {

    private NewsTypeService newsTypeService;

    public NewsTypeController(NewsTypeService newsTypeService) {
        this.newsTypeService = newsTypeService;
    }

    @Log("查询校园资讯分类列表")
    @PostMapping("/list")
    public ResponseEntity<Object> getNewsTypeList(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria) {
        PageHelper pageHelper = newsTypeService.getList(basicQueryDto, queryCriteria);
        return ResponseEntity.ok(pageHelper);
    }

    @PostMapping("/getAll")
    @AnonymousAccess
    public ResponseEntity<Object> getAllNewsType() {
        List<NewsType> newsTypeList = newsTypeService.getAll();
        return ResponseEntity.ok(newsTypeList);
    }

    @Log("校园资讯分类新增")
    @PostMapping("/create")
    public ResponseEntity<Object> createNewsType(@RequestBody NewsType newsType) {
        newsTypeService.createNewsType(newsType);
        return new ResponseEntity<>("校园资讯新增成功", HttpStatus.OK);
    }

    @Log("校园资讯分类修改")
    @PostMapping("/update")
    public ResponseEntity<Object> updateNewsType(@RequestBody NewsType newsType) {
        newsTypeService.updateNewsType(newsType);
        return new ResponseEntity<>("校园资讯修改成功", HttpStatus.OK);
    }

    @Log("校园资讯分类删除")
    @PostMapping("/delete")
    public ResponseEntity<Object> deleteNewsType(@RequestBody List<Integer> typeIds) {
        newsTypeService.deleteNewsType(typeIds);
        return new ResponseEntity<>("校园资讯删除成功", HttpStatus.OK);
    }
}
