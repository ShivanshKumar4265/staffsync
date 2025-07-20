package com.jmd.staffsync_both.service;

import com.jmd.staffsync_both.dto.RoleResponseDto;
import com.jmd.staffsync_both.entity.Roles;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    RoleResponseDto createRole(Roles role);
}
