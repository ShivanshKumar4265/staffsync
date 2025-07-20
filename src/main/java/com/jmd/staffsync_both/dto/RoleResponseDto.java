package com.jmd.staffsync_both.dto;

import com.jmd.staffsync_both.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleResponseDto {
    private String status;
    private String message;
    private Roles role_details;
}
