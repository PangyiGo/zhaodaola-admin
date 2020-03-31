package com.sise.zhaodaola.core.setting;

import com.sise.zhaodaola.business.entity.Banner;
import com.sise.zhaodaola.business.service.BannerService;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.annotation.AnonymousAccess;
import com.sise.zhaodaola.tool.annotation.Log;
import com.sise.zhaodaola.tool.utils.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User: PangYi
 * Date: 2020-03-23
 * Time: 9:02
 * Description:
 */
@RestController
@RequestMapping("/api/banner")
@Slf4j
public class BannerController {

    private BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @Log("新增轮播图")
    @RequestMapping("/create")
    public ResponseEntity<Object> createBanner(@RequestBody Banner banner) {
        bannerService.createBanner(banner);
        return new ResponseEntity<>("新增成功", HttpStatus.OK);
    }

    @Log("删除轮播图")
    @RequestMapping("/delete")
    public ResponseEntity<Object> deleteBanner(@RequestBody List<Integer> bannerIds) {
        bannerService.deleteBanner(bannerIds);
        return new ResponseEntity<>("删除成功", HttpStatus.OK);
    }

    @Log("修改轮播图")
    @RequestMapping("/update")
    public ResponseEntity<Object> updateBanner(@RequestBody Banner banner) {
        bannerService.updateBanner(banner);
        return new ResponseEntity<>("修改成功", HttpStatus.OK);
    }

    @Log("查询轮播图")
    @RequestMapping("/list")
    public ResponseEntity<Object> getBannerList(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria) {
        PageHelper bannerList = bannerService.getBannerList(basicQueryDto, queryCriteria);
        return ResponseEntity.ok(bannerList);
    }

    @RequestMapping("/getOne/{bannerId}")
    public ResponseEntity<Object> getOne(@PathVariable("bannerId") Integer bannerId) {
        Banner banner = bannerService.getOne(bannerId);
        return ResponseEntity.ok(banner);
    }

    @PostMapping("/show/{type}")
    @AnonymousAccess
    public ResponseEntity<Object> getBanners(@PathVariable("type")Integer type) {
        return ResponseEntity.ok(bannerService.getBanners(type));
    }
}
