package com.jmd.staffsync_both.repository;

import com.jmd.staffsync_both.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    boolean existsByEmailId(String emailId);

    boolean existsByMobileNumber(String mobileNumber);

    boolean existsByWhatsappNo(String whatsappNo);

}
