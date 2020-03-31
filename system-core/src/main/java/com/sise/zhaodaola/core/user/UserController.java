package com.sise.zhaodaola.core.user;

import com.sise.zhaodaola.business.entity.User;
import com.sise.zhaodaola.business.service.UserService;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.dto.UserDto;
import com.sise.zhaodaola.business.service.dto.UserQueryDto;
import com.sise.zhaodaola.business.service.dto.UserUpdateDto;
import com.sise.zhaodaola.business.service.impl.UserPassDto;
import com.sise.zhaodaola.tool.annotation.Log;
import com.sise.zhaodaola.tool.exception.BadRequestException;
import com.sise.zhaodaola.tool.utils.PageHelper;
import com.sise.zhaodaola.tool.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Log("查询用户")
    @PostMapping("/list")
    public ResponseEntity<Object> getUser(UserQueryDto userQueryDto, PageQueryCriteria criteria) {
        PageHelper userList = userService.getUserList(userQueryDto, criteria);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @Log("修改用户")
    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        userService.updateUser(userUpdateDto);
        return new ResponseEntity<>("用户信息修改成功", HttpStatus.OK);
    }

    @Log("重置密码")
    @PostMapping("/resetPassword")
    public ResponseEntity<Object> resetPasswordUser(@RequestBody List<Integer> uid) {
        userService.resetPasswordUser(uid);
        return new ResponseEntity<>("重置密码成功", HttpStatus.OK);
    }

    @Log("用户数据导出")
    @PostMapping("/download")
    public void downloadUser(UserQueryDto userQueryDto, HttpServletResponse response) throws IOException {
        userService.downloadUser(userService.findAll(userQueryDto), response);
    }

    @Log("新增用户")
    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody UserUpdateDto userUpdateDto) {
        userService.createUser(userUpdateDto);
        return new ResponseEntity<>("新增用户成功", HttpStatus.OK);
    }

    @Log("删除用户")
    @PostMapping("/delete")
    public ResponseEntity<Object> deleteUser(@RequestBody List<Integer> userIds) {
        /*
            删除规则：先解除角色绑定，再删除用户如存在外键异常，删除失败
         */
        userService.deleteUser(userIds);
        return new ResponseEntity<>("用户已成功删除", HttpStatus.OK);
    }

    @Log("用户批量导入")
    @PostMapping("/import")
    public ResponseEntity<Object> importUser(MultipartFile file) {
        userService.importUser(file);
        return ResponseEntity.ok("批量导入数据成功");
    }

    @PostMapping(value = "/updateAvatar")
    public ResponseEntity<Object> updateAvatar(@RequestParam MultipartFile file) {
        userService.updateAvatar(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("修改用户密码")
    @PostMapping(value = "/updatePass")
    public ResponseEntity<Object> updatePass(@RequestBody UserPassDto userPassDto) {
        // 密码加密
        UserDto user = userService.findByUsername(SecurityUtils.getUsername());
        if (!passwordEncoder.matches(userPassDto.getOldPass(), user.getPassword())) {
            throw new BadRequestException("修改失败，旧密码错误");
        }
        if (passwordEncoder.matches(userPassDto.getNewPass(), user.getPassword())) {
            throw new BadRequestException("新密码不能与旧密码相同");
        }
        userService.updatePassword(passwordEncoder.encode(userPassDto.getNewPass()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/updateInfo")
    public ResponseEntity<Object> updateUserInfo(@RequestBody User user){
        userService.updateUserInfo(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
