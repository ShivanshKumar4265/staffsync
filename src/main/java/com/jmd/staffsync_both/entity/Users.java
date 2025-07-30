package com.jmd.staffsync_both.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "user_name")
    @JsonProperty("user_name")
    private String userName;

    @Column(name = "company_name")
    @JsonProperty("company_name")

    private String companyName;

    @Column(name = "mobile_number", unique = true)
    @JsonProperty("mobile_number")
    private String mobileNumber;

    @Column(name = "whatsapp_no", unique = true)
    @JsonProperty("whatsapp_no")

    private String whatsappNo;

    @Column(name = "email_id", unique = true)
    @JsonProperty("email_id")

    private String emailId;

    @Column(name = "profile_pic")
    @JsonProperty("profile_pic")
    private String profilePic;

    @Column(name = "is_active")
    @JsonProperty("is_active")
    private boolean isActive;

    @Column(name = "created_at")
    @JsonProperty("created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private LocalDateTime updated_at;


}
