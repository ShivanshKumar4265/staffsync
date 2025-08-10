package com.jmd.staffsync_both.service_impl;

import com.jmd.staffsync_both.dto.RoleListDTO;
import com.jmd.staffsync_both.dto.request_dto.ReqCommonDTO;
import com.jmd.staffsync_both.dto.request_dto.ReqCreateRoleDTO;
import com.jmd.staffsync_both.dto.request_dto.ReqUpdateRole;
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

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
            // Validate connection/auth
            Business business = connectionAuthValidator.validateConnectionAuth(
                    reqCreateRole.getConnectionId(),
                    reqCreateRole.getAuthCode()
            );

            if (business == null) {
                return new GenricDTO<>(StringConstant.UNAUTHORIZED, "Unauthorized access", null);
            }

            // Validate role name
            if (reqCreateRole.getRoleName() == null || reqCreateRole.getRoleName().isEmpty()) {
                return new GenricDTO<>(StringConstant.INVALID_REQUEST, "Role name cannot be null or empty", null);
            }

            // Format role name (capitalize words)
            String roleName = Arrays.stream(reqCreateRole.getRoleName().trim().toLowerCase().split("\\s+"))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                    .collect(Collectors.joining(" "));

            Roles role = rolesRepository.findByRoleName(roleName);

            if (role != null) {
                // Check if this role is already assigned to the business
                boolean alreadyAssigned = roleMappingRepository.existsByRoleAndUser(role, business);
                if (alreadyAssigned) {
                    return new GenricDTO<>(StringConstant.CONFLICT, "Role already exists", role);
                }

                // Assign existing role
                RoleMappingTable roleMappingTable = new RoleMappingTable();
                roleMappingTable.setCreateAt(LocalDateTime.now());
                roleMappingTable.setRole(role);
                roleMappingTable.setUser(business);
                roleMappingTable.setActive(true);
                roleMappingRepository.save(roleMappingTable);

                return new GenricDTO<>(StringConstant.SUCCESS, "Role assigned successfully", role);
            }

            // If role doesn't exist, create it
            Roles newRole = new Roles();
            newRole.setRoleName(roleName);
            newRole.setCreated_at(LocalDateTime.now());
            newRole.setActive(true);
            Roles savedRole = rolesRepository.save(newRole);

            RoleMappingTable roleMappingTable = new RoleMappingTable();
            roleMappingTable.setCreateAt(LocalDateTime.now());
            roleMappingTable.setRole(savedRole);
            roleMappingTable.setUser(business);
            roleMappingTable.setActive(true);
            roleMappingRepository.save(roleMappingTable);

            return new GenricDTO<>(StringConstant.SUCCESS, "Role created and assigned successfully", savedRole);

        } catch (Exception e) {
            return new GenricDTO<>(StringConstant.ERROR, "Failed to create/assign role: " + e.getMessage(), null);
        }
    }


    @Override
    public GenricDTO<List<RoleListDTO>> getRoles(ReqCommonDTO reqCommonDTO) {
        try {

            Business business = connectionAuthValidator.validateConnectionAuth(
                    reqCommonDTO.getConnectionId(),
                    reqCommonDTO.getAuthCode()  // assuming this getter exists
            );

            if (business == null) {
                return new GenricDTO<>(StringConstant.UNAUTHORIZED, "Unauthorized access", null);
            }

            List<RoleMappingTable> mappings = roleMappingRepository.findByUserUserIdAndIsActiveTrue(business.getUserId());

            List<RoleListDTO> roles = new ArrayList<>();
            for (RoleMappingTable mapping : mappings) {
                Roles role = mapping.getRole();
                RoleListDTO dto = new RoleListDTO(role.getRoleName(), role.getId());
                roles.add(dto);
            }


            if (roles.isEmpty()) {
                return new GenricDTO<>(StringConstant.SUCCESS, "No Role Found!!", roles);
            } else {
                return new GenricDTO<>(StringConstant.SUCCESS, "Role List fetched", roles);
            }
        } catch (Exception e) {
            return new GenricDTO<>(StringConstant.ERROR, "Something went wrong, please try again" + e.getMessage(), null);
        }
    }

    @Override
    public GenricDTO<Roles> updateRole(ReqUpdateRole reqUpdateRole) {
        try {
            // Validate connection/auth
            Business business = connectionAuthValidator.validateConnectionAuth(
                    reqUpdateRole.getConnectionId(),
                    reqUpdateRole.getAuthCode()
            );

            if (business == null) {
                return new GenricDTO<>(StringConstant.UNAUTHORIZED, "Unauthorized access", null);
            }

            if (reqUpdateRole.getRoleId() == null || reqUpdateRole.getRoleId().isEmpty()) {
                return new GenricDTO<>(StringConstant.INVALID_REQUEST, "Role ID cannot be null or empty", null);
            }


            // Fetch existing role mapping
            RoleMappingTable existingMapping = roleMappingRepository.findById(Long.parseLong(reqUpdateRole.getRoleId()))
                    .orElse(null);

            if (existingMapping == null) {
                return new GenricDTO<>(StringConstant.NOT_FOUND, "You doesn't have this role", null);
            }

            String newRoleName = reqUpdateRole.getRoleName() != null
                    ? Arrays.stream(reqUpdateRole.getRoleName().trim().toLowerCase().split("\\s+"))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                    .collect(Collectors.joining(" "))
                    : null;

            // Case 1: Role rename
            if (newRoleName != null && !newRoleName.isEmpty() && !newRoleName.equals(existingMapping.getRole().getRoleName())) {
                // Create new role
                Roles newRole = new Roles();
                newRole.setRoleName(newRoleName);
                newRole.setCreated_at(LocalDateTime.now());
                newRole.setActive(true);
                newRole = rolesRepository.save(newRole);

                // Create new mapping for the business
                RoleMappingTable newMapping = new RoleMappingTable();
                newMapping.setRole(newRole);
                newMapping.setUser(business);
                newMapping.setActive(true);
                newMapping.setCreateAt(LocalDateTime.now());
                roleMappingRepository.save(newMapping);

                // Deactivate old mapping
                existingMapping.setActive(false);
                existingMapping.setUpdatedAt(LocalDateTime.now());
                roleMappingRepository.save(existingMapping);

                return new GenricDTO<>(StringConstant.SUCCESS, "Role renamed successfully", newRole);
            }

            // Case 2: Only update active status
            existingMapping.setActive(reqUpdateRole.isActive());
            existingMapping.setUpdatedAt(LocalDateTime.now());
            roleMappingRepository.save(existingMapping);

            return new GenricDTO<>(StringConstant.SUCCESS, "Role status updated successfully", existingMapping.getRole());

        } catch (Exception e) {
            return new GenricDTO<>(StringConstant.ERROR, "Failed to update role: " + e.getMessage(), null);
        }
    }

}
