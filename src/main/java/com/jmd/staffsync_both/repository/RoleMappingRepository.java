package com.jmd.staffsync_both.repository;

import com.jmd.staffsync_both.entity.RoleMappingTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleMappingRepository extends JpaRepository<RoleMappingTable, Long> {

}
