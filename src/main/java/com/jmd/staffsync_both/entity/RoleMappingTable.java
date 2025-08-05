package com.jmd.staffsync_both.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "role_mapping")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMappingTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Roles role;

    @ManyToOne
    @JoinColumn(name = "added_by", referencedColumnName = "user_id")
    private Business user;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createAt;


    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
