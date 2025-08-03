package com.jmd.staffsync_both.dto.request_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ReqRegisterUserDTO {

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("mobile_number")
    private String mobileNumber;

    @JsonProperty("whatsapp_no")
    private String whatsappNo;

    @JsonProperty("email_id")
    private String emailId;

    @JsonProperty("profile_pic")
    private String profilePic;

    @JsonProperty("connection_id")
    private String connection_id;

}
