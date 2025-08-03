package com.jmd.staffsync_both.service;

import com.jmd.staffsync_both.dto.UserDetailsDto;
import com.jmd.staffsync_both.dto.request_dto.ReqRegisterUserDTO;
import com.jmd.staffsync_both.utils.GenricDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    GenricDTO<UserDetailsDto> registerBusiness(ReqRegisterUserDTO user);
}
