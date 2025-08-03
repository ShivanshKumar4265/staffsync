package com.jmd.staffsync_both.controller;

import com.jmd.staffsync_both.dto.ConnectionDto;
import com.jmd.staffsync_both.service.ConnectionService;
import com.jmd.staffsync_both.utils.GenricDTO;
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
    public ResponseEntity<GenricDTO<ConnectionDto>> getAllConnections(String api_key) {
        GenricDTO<ConnectionDto> response = connectionService.createConnection(api_key);

        HttpStatus status;

        if (StringConstant.SUCCESS.equals(response.getStatus())) {
            status = HttpStatus.CREATED;
        } else if (StringConstant.INVALID_REQUEST.equals(response.getStatus())) {
            status = HttpStatus.UNAUTHORIZED;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(status).body(response);
    }


}
