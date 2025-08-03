package com.jmd.staffsync_both.repository;

import com.jmd.staffsync_both.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GetConnectionRepository extends JpaRepository<Connection, Long> {
    Connection findByConnectionId(String connectionId);
}
