package com.jmd.staffsync_both.service_impl;

import com.jmd.staffsync_both.dto.RoleResponseDto;
import com.jmd.staffsync_both.entity.Roles;
import com.jmd.staffsync_both.repository.RolesRepository;
import com.jmd.staffsync_both.service.RoleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    RolesRepository rolesRepository;

    public RoleServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public RoleResponseDto createRole(Roles role) {
        try {

            if (role == null || role.getRoleName() == null || role.getRoleName().isEmpty()) {
                return new RoleResponseDto("invalid request", "Role name cannot be null or empty", null);
            }

            String roleName = role.getRoleName();
            if (roleName != null && !roleName.isEmpty()) {
                roleName = Arrays.stream(roleName.trim().toLowerCase().split("\\s+"))
                        .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                        .collect(Collectors.joining(" "));
            }


            if (rolesRepository.existsByRoleName(roleName)) {
                return new RoleResponseDto("conflict", "Role already exists", null);
            }



            Roles newRole = new Roles();
            newRole.setRoleName(roleName);
            newRole.setCreated_at(LocalDateTime.now());
            newRole.setUpdated_at(LocalDateTime.now());
            newRole.setActive(true);
            Roles savedRole = rolesRepository.save(newRole);


            return new RoleResponseDto("success", "Role created successfully", savedRole);
        } catch (Exception e) {
            return new RoleResponseDto("error", "Failed to create role: " + e.getMessage(), null);
        }
   }
}
