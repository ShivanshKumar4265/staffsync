package com.jmd.staffsync_both.dto;

import com.jmd.staffsync_both.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    private String status;
    private String message;
    private UserDetailsDto user_details;
}
