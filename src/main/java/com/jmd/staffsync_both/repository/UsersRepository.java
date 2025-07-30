package com.jmd.staffsync_both.repository;

import com.jmd.staffsync_both.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByEmailId(String emailId);

    boolean existsByMobileNumber(String mobileNumber);

    boolean existsByWhatsappNo(String whatsappNo);

}
