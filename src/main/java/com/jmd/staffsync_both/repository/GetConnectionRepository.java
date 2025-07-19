package com.jmd.staffsync_both.repository;

import com.jmd.staffsync_both.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GetConnectionRepository extends JpaRepository<Connection, Long>{
    // This interface will automatically provide CRUD operations for the GetConnection entity
    // due to the JpaRepository inheritance.
    // No additional methods are needed unless custom queries are required.

//     Connection save(String connectionId);
}
