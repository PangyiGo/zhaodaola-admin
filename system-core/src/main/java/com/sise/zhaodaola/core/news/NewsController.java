package com.sise.zhaodaola.core.news;

import com.sise.zhaodaola.business.entity.News;
import com.sise.zhaodaola.business.service.NewsService;
import com.sise.zhaodaola.business.service.dto.NewsQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.vo.NewsQueryVo;
import com.sise.zhaodaola.tool.annotation.AnonymousAccess;
import com.sise.zhaodaola.tool.annotation.Log;
import com.sise.zhaodaola.tool.utils.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User: PangYi
 * Date: 2020-03-19
 * Time: 9:11
 * Description:
 */
@RestController
@Slf4j
@RequestMapping("/api/news")
public class NewsController {

    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @Log("校园资讯发布")
    @PreAuthorize("@auth.check('news:publish')")
    @PostMapping("/publish")
    public ResponseEntity<Object> publish(@RequestBody News news) {
        newsService.publishNews(news);
        return new ResponseEntity<>("校园资讯发布成功", HttpStatus.OK);
    }

    @Log("校园资讯查询")
    @PreAuthorize("@auth.check('news:list')")
    @PostMapping("/list")
    public ResponseEntity<Object> getList(NewsQueryDto newsQueryDto, PageQueryCriteria queryCriteria) {
        PageHelper pageHelper = newsService.getListNews(newsQueryDto, queryCriteria);
        return ResponseEntity.ok(pageHelper);
    }

    @AnonymousAccess
    @PostMapping("/showNews")
    public ResponseEntity<Object> showNewsList(NewsQueryDto newsQueryDto, PageQueryCriteria queryCriteria) {
        PageHelper pageHelper = newsService.showNewsList(newsQueryDto, queryCriteria);
        return ResponseEntity.ok(pageHelper);
    }

    @PostMapping("/showIndex")
    @AnonymousAccess
    public ResponseEntity<Object> getListShow() {
        List<NewsQueryVo> newsQueryVos = newsService.showIndex();
        return ResponseEntity.ok(newsQueryVos);
    }

    @Log("校园资讯删除")
    @PreAuthorize("@auth.check('news:delete')")
    @PostMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody List<Integer> newsIds) {
        newsService.delete(newsIds);
        return new ResponseEntity<>("校园资讯删除成功", HttpStatus.OK);
    }

    @AnonymousAccess
    @PostMapping("/getOne/{newsId}")
    public ResponseEntity<Object> getOne(@PathVariable("newsId") Integer newsId) {
        NewsQueryVo newsQueryVo = newsService.viewNews(newsId);
        return ResponseEntity.ok(newsQueryVo);
    }

    @PostMapping("/edtior/{newsId}")
    public ResponseEntity<Object> getEdtior(@PathVariable("newsId") Integer newsId) {
        News news = newsService.edtior(newsId);
        return ResponseEntity.ok(news);
    }

    @Log("校园资讯修改")
    @PreAuthorize("@auth.check('news:update')")
    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody News news) {
        newsService.update(news);
        return new ResponseEntity<>("校园资讯信息修改成功", HttpStatus.OK);
    }
}
