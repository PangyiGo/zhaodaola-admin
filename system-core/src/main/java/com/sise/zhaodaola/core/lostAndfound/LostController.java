package com.sise.zhaodaola.core.lostAndfound;

import com.sise.zhaodaola.business.service.LostService;
import com.sise.zhaodaola.business.service.dto.LostFoundBasicDto;
import com.sise.zhaodaola.business.service.dto.LostFoundQueryDto;
import com.sise.zhaodaola.business.service.dto.LostSingleUpdateDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.vo.LostFoundQueryVo;
import com.sise.zhaodaola.tool.annotation.AnonymousAccess;
import com.sise.zhaodaola.tool.annotation.Log;
import com.sise.zhaodaola.tool.utils.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * User: PangYi
 * Date: 2020-03-13
 * Time: 15:50
 * Description: 寻物
 */
@RestController
@RequestMapping("/api/losts")
@Slf4j
public class LostController {

    @Autowired
    private LostService lostService;


    @PostMapping("/list")
    @AnonymousAccess
    public ResponseEntity<Object> getLosts(LostFoundQueryDto lostFoundQueryDto, PageQueryCriteria queryCriteria) {
        PageHelper listToPage = lostService.getListToPage(lostFoundQueryDto, queryCriteria);
        return ResponseEntity.ok(listToPage);
    }

    @Log("寻物启事数据导出")
    @PostMapping("/download")
    public void download(LostFoundQueryDto lostFoundQueryDto, HttpServletResponse response) throws IOException {
        lostService.download(lostService.getAll(lostFoundQueryDto), response);
    }

    @Log("寻物启事删除")
    @PostMapping("/delete")
    public ResponseEntity<Object> deleteLost(@RequestBody List<Integer> lostIds) {
        lostService.deleteLost(lostIds);
        return new ResponseEntity<>("寻物启事删除成功", HttpStatus.OK);
    }

    @Log("寻物启事删除")
    @PostMapping("/delete/{lostId}")
    public ResponseEntity<Object> deleteLost(@PathVariable("lostId") Integer lostId) {
        lostService.delete(lostId);
        return new ResponseEntity<>("寻物启事删除成功", HttpStatus.OK);
    }

    @PostMapping("/getOne/{lostId}")
    public ResponseEntity getOne(@PathVariable("lostId") Integer lostId) {
        LostSingleUpdateDto lostSingleUpdateDto = lostService.getOne(lostId);
        return ResponseEntity.ok(lostSingleUpdateDto);
    }

    @Log("寻物启事发布")
    @PostMapping("/publish")
    public ResponseEntity<Object> publishLost(@RequestBody LostFoundBasicDto lostFoundBasicDto) {
        lostService.publishLost(lostFoundBasicDto);
        return new ResponseEntity<>("寻物启事登记成功", HttpStatus.OK);
    }

    @Log("寻物启事修改")
    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody LostSingleUpdateDto lostSingleUpdateDto) {
        lostService.updateLost(lostSingleUpdateDto);
        return new ResponseEntity<>("修改成功", HttpStatus.OK);
    }

    @AnonymousAccess
    @PostMapping("/index")
    public ResponseEntity<Object> getLostIndex() {
        List<LostFoundQueryVo> lostIndex = lostService.getLostIndex();
        return ResponseEntity.ok(lostIndex);
    }

    @PostMapping("/showInfo/{lostId}")
    public ResponseEntity<Object> getLostSingle(@PathVariable("lostId") Integer lostId) {
        LostFoundQueryVo lostFoundQueryVo = lostService.showLostOne(lostId);
        return ResponseEntity.ok(lostFoundQueryVo);
    }

    @PostMapping("/pushLost")
    public ResponseEntity<Object> pushLost(String name, Integer slfe) {
        List<LostFoundQueryVo> lostFoundQueryVos = lostService.pushLost(name, slfe);
        return ResponseEntity.ok(lostFoundQueryVos);
    }
}
