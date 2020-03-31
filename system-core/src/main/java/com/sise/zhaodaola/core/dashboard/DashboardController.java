package com.sise.zhaodaola.core.dashboard;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sise.zhaodaola.business.entity.Category;
import com.sise.zhaodaola.business.entity.Found;
import com.sise.zhaodaola.business.entity.Lost;
import com.sise.zhaodaola.business.service.*;
import com.sise.zhaodaola.tool.annotation.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: PangYi
 * @Date 2020/4/15:46 上午
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final UserService userService;

    private final LostService lostService;

    private final FoundService foundService;

    private final NewsService newsService;

    private final AnnounceService announceService;

    private final ThanksService thanksService;

    private final CategoryService categoryService;

    public DashboardController(UserService userService, LostService lostService, FoundService foundService, NewsService newsService, AnnounceService announceService, ThanksService thanksService, CategoryService categoryService) {
        this.userService = userService;
        this.lostService = lostService;
        this.foundService = foundService;
        this.newsService = newsService;
        this.announceService = announceService;
        this.thanksService = thanksService;
        this.categoryService = categoryService;
    }

    @Log("统计数量")
    @PostMapping("/count")
    public ResponseEntity<Object> countAll() {
        Map<String, Object> result = new HashMap<>(0);
        int userCoount = userService.count();
        int lostCount = lostService.count();
        int foundCount = foundService.count();
        int newsCount = newsService.count();
        int announceCount = announceService.count();
        int thanksCount = thanksService.count();
        result.put("userAccount", userCoount);
        result.put("lostCount", lostCount);
        result.put("foundCount", foundCount);
        result.put("newsCount", newsCount);
        result.put("announceCount", announceCount);
        result.put("thanksCount", thanksCount);
        return ResponseEntity.ok(result);
    }

    @Log("失物招领启事比例")
    @PostMapping("/bili")
    public ResponseEntity<Object> bili() {
        List<Map<String, Object>> mapList = new ArrayList<>(0);
        int lostCount = lostService.count();
        int lostNocount = lostService.count(Wrappers.<Lost>lambdaQuery().eq(Lost::getStatus, 1));
        int lostYescount = lostService.count(Wrappers.<Lost>lambdaQuery().eq(Lost::getStatus, 2));
        Map<String, Object> lost = new HashMap<>();
        lost.put("count", lostCount);
        lost.put("no", lostNocount);
        lost.put("yes", lostYescount);
        mapList.add(lost);
        int foundCount = foundService.count();
        int foundNocount = foundService.count(Wrappers.<Found>lambdaQuery().eq(Found::getStatus, 1));
        int foundYescount = foundService.count(Wrappers.<Found>lambdaQuery().eq(Found::getStatus, 2));
        Map<String, Object> found = new HashMap<>();
        found.put("count", foundCount);
        found.put("no", foundNocount);
        found.put("yes", foundYescount);
        mapList.add(found);
        return ResponseEntity.ok(mapList);
    }

    @PostMapping("/lostTypeCount")
    public ResponseEntity<Object> lostTypeCount() {
        List<Map<String, Object>> mapList = new ArrayList<>(0);
        List<Category> categoryList = categoryService.list();
        categoryList.forEach(category -> {
            Map<String, Object> map = new HashMap<>(0);
            map.put("type", category.getName());
            map.put("count", lostService.count());
            mapList.add(map);
        });
        return ResponseEntity.ok(mapList);
    }

    @PostMapping("/foundTypeCount")
    public ResponseEntity<Object> foundTypeCount() {
        List<Map<String, Object>> mapList = new ArrayList<>(0);
        List<Category> categoryList = categoryService.list();
        categoryList.forEach(category -> {
            Map<String, Object> map = new HashMap<>(0);
            map.put("type", category.getName());
            map.put("count", foundService.count());
            mapList.add(map);
        });
        return ResponseEntity.ok(mapList);
    }
}
