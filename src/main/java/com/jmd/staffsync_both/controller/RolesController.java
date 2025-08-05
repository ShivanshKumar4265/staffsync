package com.jmd.staffsync_both.controller;

import com.jmd.staffsync_both.dto.RoleListDTO;
import com.jmd.staffsync_both.dto.request_dto.ReqCommonDTO;
import com.jmd.staffsync_both.dto.request_dto.ReqCreateRoleDTO;
import com.jmd.staffsync_both.entity.Roles;
import com.jmd.staffsync_both.service.RoleService;
import com.jmd.staffsync_both.utils.GenricDTO;
import com.jmd.staffsync_both.utils.StringConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employer")
public class RolesController {

    private RoleService roleService;

    public RolesController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create_roles")
    public ResponseEntity<GenricDTO<Roles>> createRole(@RequestBody ReqCreateRoleDTO role) {
        GenricDTO<Roles> response = roleService.createRole(role);
        if (StringConstant.SUCCESS.equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else if (StringConstant.INVALID_REQUEST.equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else if (StringConstant.CONFLICT.equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } else if (StringConstant.UNAUTHORIZED.equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/get_roles")
    public ResponseEntity<GenricDTO<List<RoleListDTO>>> getRoles(@RequestBody ReqCommonDTO ReqCommonDTO) {
        GenricDTO<List<RoleListDTO>> response = roleService.getRoles(ReqCommonDTO);

        if (StringConstant.SUCCESS.equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else if (StringConstant.INVALID_REQUEST.equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else if (StringConstant.CONFLICT.equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } else if (StringConstant.UNAUTHORIZED.equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
