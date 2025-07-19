package com.jmd.staffsync_both.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "connections")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "connection_id")
    private String connection_id;
    @Column(name = "auth_code")
    private String auth_code;
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "user_id")
    private int user_id;


    /**
     * import jakarta.persistence.PrePersist;
     * import jakarta.persistence.PreUpdate;
     *
     * @PrePersist
     * protected void onCreate() {
     *     createdAt = LocalDateTime.now();
     *     updatedAt = LocalDateTime.now();
     * }
     *
     * @PreUpdate
     * protected void onUpdate() {
     *     updatedAt = LocalDateTime.now();
     * }
     */
}
