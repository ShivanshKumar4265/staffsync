package com.jmd.staffsync_both.controller;

import com.jmd.staffsync_both.dto.ConnectionDto;
import com.jmd.staffsync_both.service.ConnectionService;
import com.jmd.staffsync_both.utils.StringConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employer")
public class ConnectionController {

    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @PostMapping("/get_connection")
    public ResponseEntity<ConnectionDto> getAllConnections(String api_key) {
        ConnectionDto response = connectionService.createConnection(api_key);
        if (StringConstant.SUCCESS.equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201 Created
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500 Error
        }
    }
}
