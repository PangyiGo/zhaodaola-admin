package com.sise.zhaodaola.core.news;

import com.sise.zhaodaola.business.entity.News;
import com.sise.zhaodaola.business.service.NewsService;
import com.sise.zhaodaola.tool.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
