package com.jmd.staffsync_both.service_impl;

import com.jmd.staffsync_both.dto.request_dto.ReqCreateRoleDTO;
import com.jmd.staffsync_both.entity.Business;
import com.jmd.staffsync_both.entity.Connection;
import com.jmd.staffsync_both.entity.RoleMappingTable;
import com.jmd.staffsync_both.entity.Roles;
import com.jmd.staffsync_both.repository.BusinessRepository;
import com.jmd.staffsync_both.repository.GetConnectionRepository;
import com.jmd.staffsync_both.repository.RoleMappingRepository;
import com.jmd.staffsync_both.repository.RolesRepository;
import com.jmd.staffsync_both.service.RoleService;
import com.jmd.staffsync_both.utils.ConnectionAuthValidator;
import com.jmd.staffsync_both.utils.GenricDTO;
import com.jmd.staffsync_both.utils.StringConstant;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    RolesRepository rolesRepository;
    RoleMappingRepository roleMappingRepository;
    BusinessRepository businessRepository;
    GetConnectionRepository connectionRepository;
    ConnectionAuthValidator connectionAuthValidator;

    public RoleServiceImpl(
            RolesRepository rolesRepository,
            BusinessRepository businessRepository,
            GetConnectionRepository connectionRepository,
            ConnectionAuthValidator connectionAuthValidator,
            RoleMappingRepository roleMappingRepository
    ) {
        this.rolesRepository = rolesRepository;
        this.businessRepository = businessRepository;
        this.connectionRepository = connectionRepository;
        this.connectionAuthValidator = connectionAuthValidator;
        this.roleMappingRepository = roleMappingRepository;
    }

    @Override
    public GenricDTO<Roles> createRole(ReqCreateRoleDTO reqCreateRole) {
        try {

            Business business = connectionAuthValidator.validateConnectionAuth(
                    reqCreateRole.getConnectionId(),
                    reqCreateRole.getAuthCode()  // assuming this getter exists
            );

            if (business == null) {
                return new GenricDTO<>(StringConstant.UNAUTHORIZED, "Unauthorized access", null);
            }


            if (reqCreateRole.getRoleName() == null || reqCreateRole.getRoleName().isEmpty()) {
                return new GenricDTO<>(StringConstant.INVALID_REQUEST, "Role name cannot be null or empty", null);
            }

            String roleName = reqCreateRole.getRoleName();
            if (roleName != null && !roleName.isEmpty()) {
                roleName = Arrays.stream(roleName.trim().toLowerCase().split("\\s+"))
                        .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                        .collect(Collectors.joining(" "));
            }


            if (rolesRepository.existsByRoleName(roleName)) {
                return new GenricDTO<>(StringConstant.CONFLICT, "Role already exists", null);
            }


            Roles newRole = new Roles();
            newRole.setRoleName(roleName);
            newRole.setCreated_at(LocalDateTime.now());
            newRole.setActive(true);
            Roles savedRole = rolesRepository.save(newRole);

            RoleMappingTable roleMappingTable = new RoleMappingTable();
            roleMappingTable.setCreateAt(LocalDateTime.now());
            roleMappingTable.setRole(newRole);
            roleMappingTable.setUser(business);
            roleMappingTable.setActive(true);
            roleMappingRepository.save(roleMappingTable);


            return new GenricDTO<>(StringConstant.SUCCESS, "Role created successfully", savedRole);
        } catch (Exception e) {
            return new GenricDTO<>(StringConstant.ERROR, "Failed to create role: " + e.getMessage(), null);
        }
    }
}
