package com.jmd.staffsync_both.service;

import com.jmd.staffsync_both.dto.ConnectionDto;
import com.jmd.staffsync_both.utils.GenricDTO;
import org.springframework.stereotype.Service;

@Service
public interface ConnectionService {
    GenricDTO<ConnectionDto> createConnection(String api_key);
}
