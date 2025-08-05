package com.jmd.staffsync_both.repository;

import com.jmd.staffsync_both.entity.RoleMappingTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleMappingRepository extends JpaRepository<RoleMappingTable, Long> {
    List<RoleMappingTable> findByUserUserId(Long userId);

}
