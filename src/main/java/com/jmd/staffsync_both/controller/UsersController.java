package com.jmd.staffsync_both.controller;

import com.jmd.staffsync_both.dto.UsersDto;
import com.jmd.staffsync_both.entity.Users;
import com.jmd.staffsync_both.service.UserService;
import com.jmd.staffsync_both.utils.StringConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employer")
public class UsersController {
    private UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register_user")
    public ResponseEntity<UsersDto> createRole(@RequestBody Users users) {
        UsersDto response = userService.registerUser(users);
        if (StringConstant.SUCCESS.equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else if (StringConstant.INVALID_REQUEST.equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else if (StringConstant.CONFLICT.equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
