package com.sise.zhaodaola.core.setting;

import com.sise.zhaodaola.business.entity.Announce;
import com.sise.zhaodaola.business.service.AnnounceService;
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
 * @Author: PangYi
 * @Date 2020/3/2210:29 下午
 */
@RestController
@RequestMapping("/api/announce")
@Slf4j
public class AnnounceController {

    private AnnounceService announceService;

    public AnnounceController(AnnounceService announceService) {
        this.announceService = announceService;
    }

    @Log("新增网站公告")
    @RequestMapping("/create")
    public ResponseEntity<Object> createAnnounce(@RequestBody Announce announce) {
        announceService.createAnnounce(announce);
        return new ResponseEntity<>("新增成功", HttpStatus.OK);
    }

    @Log("删除网站公告")
    @RequestMapping("/delete")
    public ResponseEntity<Object> deleteAnnounce(@RequestBody List<Integer> announceIds) {
        announceService.deleteAnnounce(announceIds);
        return new ResponseEntity<>("删除成功", HttpStatus.OK);
    }

    @Log("修改网站公告")
    @RequestMapping("/update")
    public ResponseEntity<Object> updateAnnounce(@RequestBody Announce announce) {
        announceService.updateAnnonce(announce);
        return new ResponseEntity<>("修改成功", HttpStatus.OK);
    }

    @Log("查询网站公告")
    @RequestMapping("/list")
    public ResponseEntity<Object> getAnnounceList(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria) {
        PageHelper announceList = announceService.getAnnounceList(basicQueryDto, queryCriteria);
        return ResponseEntity.ok(announceList);
    }

    @RequestMapping("/getOne/{announceId}")
    public ResponseEntity<Object> getOne(@PathVariable("announceId") Integer announceId) {
        Announce announce = announceService.getOne(announceId);
        return ResponseEntity.ok(announce);
    }

    @AnonymousAccess
    @RequestMapping("/showIndex")
    public ResponseEntity<Object> showIndex() {
        List<Announce> announces = announceService.showIndex();
        return ResponseEntity.ok(announces);
    }

    @AnonymousAccess
    @PostMapping("/showList")
    public ResponseEntity<Object> showAnnounce(PageQueryCriteria pageQueryCriteria) {
        PageHelper pageHelper = announceService.showList(pageQueryCriteria);
        return ResponseEntity.ok(pageHelper);
    }

    @AnonymousAccess
    @PostMapping("/showOne/{announceId}")
    public ResponseEntity<Object> showOne(@PathVariable("announceId") Integer id) {
        Announce announce = announceService.showOne(id);
        return ResponseEntity.ok(announce);
    }
}
