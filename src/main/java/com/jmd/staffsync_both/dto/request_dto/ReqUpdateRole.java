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
public class ReqUpdateRole extends ReqCommonDTO {
    @JsonProperty("role_id")
    private String roleId;
    @JsonProperty("role_name")
    private String roleName;
    @JsonProperty("is_active")
    private boolean isActive;

}
