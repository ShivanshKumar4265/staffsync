package com.jmd.staffsync_both.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {
    private String userName;
    private String mobileNumber;
    private String emailId;
    private String profilePic;
}
