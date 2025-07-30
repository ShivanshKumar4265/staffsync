package com.jmd.staffsync_both.service;

import com.jmd.staffsync_both.dto.RoleResponseDto;
import com.jmd.staffsync_both.dto.UsersDto;
import com.jmd.staffsync_both.entity.Roles;
import com.jmd.staffsync_both.entity.Users;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UsersDto registerUser(Users user);
}
