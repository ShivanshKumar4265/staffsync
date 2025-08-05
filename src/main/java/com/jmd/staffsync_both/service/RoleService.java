package com.jmd.staffsync_both.service;

import com.jmd.staffsync_both.dto.request_dto.ReqCreateRoleDTO;
import com.jmd.staffsync_both.entity.Roles;
import com.jmd.staffsync_both.utils.GenricDTO;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;

@Service
public interface RoleService {
    GenricDTO<Roles> createRole(ReqCreateRoleDTO reqCreateRole);
}
