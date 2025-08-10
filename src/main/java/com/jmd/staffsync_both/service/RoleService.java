package com.jmd.staffsync_both.service;

import com.jmd.staffsync_both.dto.RoleListDTO;
import com.jmd.staffsync_both.dto.request_dto.ReqCommonDTO;
import com.jmd.staffsync_both.dto.request_dto.ReqCreateRoleDTO;
import com.jmd.staffsync_both.dto.request_dto.ReqUpdateRole;
import com.jmd.staffsync_both.entity.Roles;
import com.jmd.staffsync_both.utils.GenricDTO;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;

@Service
public interface RoleService {
    GenricDTO<Roles> createRole(ReqCreateRoleDTO reqCreateRole);

    GenricDTO<List<RoleListDTO>> getRoles(ReqCommonDTO reqCommonDTO);

    GenricDTO<Roles> updateRole(ReqUpdateRole reqUpdateRole);
}
