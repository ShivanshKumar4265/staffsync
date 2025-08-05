package com.jmd.staffsync_both.dto.request_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ReqCommonDTO {
    @JsonProperty("connection_id")
    private String connectionId;

    @JsonProperty("auth_code")
    private String authCode;
}
