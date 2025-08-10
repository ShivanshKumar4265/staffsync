package com.jmd.staffsync_both.repository;

import com.jmd.staffsync_both.entity.Business;
import com.jmd.staffsync_both.entity.RoleMappingTable;
import com.jmd.staffsync_both.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleMappingRepository extends JpaRepository<RoleMappingTable, Long> {
    List<RoleMappingTable> findByUserUserId(Long userId);

    boolean existsByRoleAndUser(Roles role, Business user);

    // SELECT * FROM role_mapping
    //WHERE user_id = :userId
    //  AND is_active = true;
    List<RoleMappingTable> findByUserUserIdAndIsActiveTrue(Long userId);


}
