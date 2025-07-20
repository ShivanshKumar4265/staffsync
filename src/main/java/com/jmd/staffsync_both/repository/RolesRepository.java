package com.jmd.staffsync_both.repository;

import com.jmd.staffsync_both.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    boolean existsByRoleName(String roleName);
}
