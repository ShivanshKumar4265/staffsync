package com.jmd.staffsync_both.controller;

import com.jmd.staffsync_both.dto.UserDetailsDto;
import com.jmd.staffsync_both.dto.request_dto.ReqRegisterUserDTO;
import com.jmd.staffsync_both.service.UserService;
import com.jmd.staffsync_both.utils.GenricDTO;
import com.jmd.staffsync_both.utils.StringConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employer")
public class BusinessController {
    private UserService userService;

    public BusinessController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register_business")
    public ResponseEntity<GenricDTO<UserDetailsDto>> createRole(@RequestBody ReqRegisterUserDTO users) {
        GenricDTO<UserDetailsDto> response = userService.registerBusiness(users);
        if (StringConstant.SUCCESS.equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else if (StringConstant.INVALID_REQUEST.equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else if (StringConstant.CONFLICT.equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } else if (StringConstant.UNAUTHORIZED.equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else if (StringConstant.FORBIDDEN.equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
