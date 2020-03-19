package com.sise.zhaodaola.core.news;

import com.sise.zhaodaola.business.service.NewsService;
import lombok.extern.slf4j.Slf4j;
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


}
