package com.sise.zhaodaola.core.lostAndfound;

import com.sise.zhaodaola.business.service.LostService;
import com.sise.zhaodaola.business.service.dto.LostFoundBasicDto;
import com.sise.zhaodaola.business.service.dto.LostFoundQueryDto;
import com.sise.zhaodaola.business.service.dto.LostSingleUpdateDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.annotation.Log;
import com.sise.zhaodaola.tool.utils.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @Log("查询寻物列表")
    @PreAuthorize("@auth.check('lost:list')")
    @PostMapping("/list")
    public ResponseEntity<Object> getLosts(LostFoundQueryDto lostFoundQueryDto, PageQueryCriteria queryCriteria) {
        PageHelper listToPage = lostService.getListToPage(lostFoundQueryDto, queryCriteria);
        return ResponseEntity.ok(listToPage);
    }

    @Log("寻物启事数据导出")
    @PreAuthorize("@auth.check('lost:download')")
    @PostMapping("/download")
    public void download(LostFoundQueryDto lostFoundQueryDto, HttpServletResponse response) throws IOException {
        lostService.download(lostService.getAll(lostFoundQueryDto), response);
    }

    @Log("寻物启事删除")
    @PreAuthorize("@auth.check('lost:delete')")
    @PostMapping("/delete")
    public ResponseEntity<Object> deleteLost(@RequestBody List<Integer> lostIds) {
        lostService.deleteLost(lostIds);
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
}
