package com.jmd.staffsync_both.service;

import com.jmd.staffsync_both.dto.ConnectionDto;
import org.springframework.stereotype.Service;

@Service
public interface ConnectionService {
    ConnectionDto createConnection(String api_key);
}
