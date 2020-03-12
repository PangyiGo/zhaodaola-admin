package com.sise.zhaodaola.core.user;

import com.sise.zhaodaola.business.service.UserService;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.dto.UserQueryDto;
import com.sise.zhaodaola.business.service.dto.UserUpdateDto;
import com.sise.zhaodaola.tool.annotation.Log;
import com.sise.zhaodaola.tool.utils.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * User: PangYi
 * Date: 2020-03-12
 * Time: 10:01
 * Description:
 */
@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Log("查询用户")
    @PreAuthorize("@auth.check('user:list')")
    @PostMapping("/list")
    public ResponseEntity<Object> getUser(UserQueryDto userQueryDto, PageQueryCriteria criteria) {
        PageHelper userList = userService.getUserList(userQueryDto, criteria);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @Log("用户修改")
    @PreAuthorize("@auth.check('user:update')")
    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        userService.updateUser(userUpdateDto);
        return new ResponseEntity<>("用户信息修改成功", HttpStatus.OK);
    }

    @Log("重置密码")
    @PreAuthorize("@auth.check('user:resetPassword')")
    @PostMapping("/resetPassword")
    public ResponseEntity<Object> resetPasswordUser(@RequestBody List<Integer> uid) {
        userService.resetPasswordUser(uid);
        return new ResponseEntity<>("重置密码成功", HttpStatus.OK);
    }

    @Log("用户数据导出")
    @PreAuthorize("@auth.check('user:list')")
    @PostMapping("/download")
    public void downloadUser(UserQueryDto userQueryDto, HttpServletResponse response) throws IOException {
        userService.downloadUser(userService.findAll(userQueryDto), response);
    }
}