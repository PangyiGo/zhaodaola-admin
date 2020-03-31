package com.sise.zhaodaola.core.lostAndfound;

import com.sise.zhaodaola.business.service.FoundService;
import com.sise.zhaodaola.business.service.dto.FoundQueryDto;
import com.sise.zhaodaola.business.service.dto.FoundSingleDto;
import com.sise.zhaodaola.business.service.dto.LostFoundBasicDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.vo.FoundQueryVo;
import com.sise.zhaodaola.tool.annotation.AnonymousAccess;
import com.sise.zhaodaola.tool.annotation.Log;
import com.sise.zhaodaola.tool.utils.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * User: PangYi
 * Date: 2020-03-16
 * Time: 10:28
 * Description:
 */
@RestController
@RequestMapping("/api/founds")
public class FoundController {

    @Autowired
    private FoundService foundService;

    @Log("认领启事发布")
    @PostMapping("/publish")
    public ResponseEntity<Object> publishFound(@RequestBody LostFoundBasicDto lostFoundBasicDto) {
        foundService.publishLost(lostFoundBasicDto);
        return new ResponseEntity<>("认领启事登记成功", HttpStatus.OK);
    }

    @PostMapping("/list")
    @AnonymousAccess
    public ResponseEntity<Object> getListToPage(FoundQueryDto foundQueryDto, PageQueryCriteria pageQueryCriteria) {
        PageHelper listToPage = foundService.getListToPage(foundQueryDto, pageQueryCriteria);
        return ResponseEntity.ok(listToPage);
    }

    @Log("认领数据导出")
    @PostMapping("/download")
    public void download(FoundQueryDto foundQueryDto, HttpServletResponse response) throws IOException {
        foundService.download(foundService.getAll(foundQueryDto), response);
    }

    @Log("认领启事删除")
    @PostMapping("/delete")
    public ResponseEntity<Object> deleteForIds(@RequestBody List<Integer> foundIds) {
        foundService.deleteFound(foundIds);
        return new ResponseEntity<>("认领启事删除成功", HttpStatus.OK);
    }
    @Log("认领启事删除")
    @PostMapping("/delete/{foundId}")
    public ResponseEntity<Object> deleteForIds(@PathVariable("foundId")Integer foundId) {
        foundService.deleteOne(foundId);
        return new ResponseEntity<>("认领启事删除成功", HttpStatus.OK);
    }

    @Log("查询认领启事")
    @PostMapping("/getOne/{foundId}")
    public ResponseEntity<Object> getOne(@PathVariable("foundId") Integer foundId) {
        FoundSingleDto foundSingleDto = foundService.getOne(foundId);
        return ResponseEntity.ok(foundSingleDto);
    }

    @Log("认领启事修改")
    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody FoundSingleDto foundSingleDto) {
        foundService.updateFound(foundSingleDto);
        return new ResponseEntity<>("修改成功", HttpStatus.OK);
    }

    @AnonymousAccess
    @PostMapping("/index")
    public ResponseEntity<Object> getLostIndex() {
        List<FoundQueryVo> foundIndex = foundService.getFoundIndex();
        return ResponseEntity.ok(foundIndex);
    }

    @PostMapping("/showInfo/{foundId}")
    public ResponseEntity<Object> getLostSingle(@PathVariable("foundId") Integer foundId) {
        FoundQueryVo foundQueryVo = foundService.showFoundOne(foundId);
        return ResponseEntity.ok(foundQueryVo);
    }

    @PostMapping("/pushFound")
    public ResponseEntity<Object> pushFound(String name, Integer slfe) {
        List<FoundQueryVo> foundQueryVos = foundService.pushFound(name, slfe);
        return ResponseEntity.ok(foundQueryVos);
    }
}
