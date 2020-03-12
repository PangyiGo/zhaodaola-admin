package com.sise.zhaodaola.core.user;

import com.sise.zhaodaola.business.service.UserService;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.dto.UserQueryDto;
import com.sise.zhaodaola.tool.annotation.Log;
import com.sise.zhaodaola.tool.utils.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
