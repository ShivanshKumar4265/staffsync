package com.jmd.staffsync_both.service_impl;

import com.jmd.staffsync_both.dto.UserDetailsDto;
import com.jmd.staffsync_both.dto.request_dto.ReqRegisterUserDTO;
import com.jmd.staffsync_both.entity.Connection;
import com.jmd.staffsync_both.entity.Business;
import com.jmd.staffsync_both.entity.Roles;
import com.jmd.staffsync_both.repository.GetConnectionRepository;
import com.jmd.staffsync_both.repository.BusinessRepository;
import com.jmd.staffsync_both.repository.RolesRepository;
import com.jmd.staffsync_both.service.UserService;
import com.jmd.staffsync_both.utils.GenricDTO;
import com.jmd.staffsync_both.utils.RandomString;
import com.jmd.staffsync_both.utils.StringConstant;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.time.LocalDateTime;


@Service
public class UsersServiceImpl implements UserService {

    BusinessRepository usersRepository;
    GetConnectionRepository getConnectionRepository;
    RolesRepository rolesRepository;

    public UsersServiceImpl(BusinessRepository usersRepository, GetConnectionRepository getConnectionRepository, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.getConnectionRepository = getConnectionRepository;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public GenricDTO<UserDetailsDto> registerBusiness(ReqRegisterUserDTO user) {
        try {
            if (user.getConnection_id() == null || user.getConnection_id().isBlank()) {
                return new GenricDTO<>(StringConstant.INVALID_REQUEST, "Connection ID is required", null);
            }

            Connection connection = getConnectionRepository.findByConnectionId(user.getConnection_id());
            if (connection == null) {
                return new GenricDTO<>(StringConstant.UNAUTHORIZED, "Invalid Connection Id", null);
            } else {
                if (connection.getUser() != null) {
                    return new GenricDTO<>(StringConstant.FORBIDDEN, "Access Denied, Establish new connection", null);
                }
            }

            if (user.getUserName() == null || user.getUserName().isBlank()) {
                return new GenricDTO<>(StringConstant.INVALID_REQUEST, "User name is required", null);
            }

            if (user.getCompanyName() == null || user.getCompanyName().isBlank()) {
                return new GenricDTO<>(StringConstant.INVALID_REQUEST, "Company name is required", null);
            }

            if (user.getMobileNumber() == null || user.getMobileNumber().isBlank()) {
                return new GenricDTO<>(StringConstant.INVALID_REQUEST, "Mobile number is required", null);
            }

            if (user.getWhatsappNo() == null || user.getWhatsappNo().isBlank()) {
                return new GenricDTO<>(StringConstant.INVALID_REQUEST, "Whatsapp number is required", null);
            }

            if (user.getEmailId() == null || user.getEmailId().isBlank()) {
                return new GenricDTO<>(StringConstant.INVALID_REQUEST, "Email ID is required", null);
            }

            if (usersRepository.existsByMobileNumber(user.getMobileNumber())) {
                return new GenricDTO<>(StringConstant.CONFLICT, "Mobile number already exists", null);
            }

            if (usersRepository.existsByWhatsappNo(user.getWhatsappNo())) {
                return new GenricDTO<>(StringConstant.CONFLICT, "Whatsapp number already exists", null);
            }

            if (usersRepository.existsByEmailId(user.getEmailId())) {
                return new GenricDTO<>(StringConstant.CONFLICT, "Email ID already exists", null);
            }

            if (!user.getEmailId().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                return new GenricDTO<>(StringConstant.INVALID_REQUEST, "Invalid email format", null);
            }

            if (!user.getMobileNumber().matches("^\\d{10}$")) {
                return new GenricDTO<>(StringConstant.INVALID_REQUEST, "Invalid mobile number format", null);
            }

            if (!user.getWhatsappNo().matches("^\\d{10}$")) {
                return new GenricDTO<>(StringConstant.INVALID_REQUEST, "Invalid whatsapp number format", null);
            }

            if (!user.getWhatsappNo().matches("^\\d{10}$")) {
                return new GenricDTO<>(StringConstant.INVALID_REQUEST, "Invalid mobile number format", null);
            }

            Business newUsers = new Business();
            newUsers.setUserName(user.getUserName());
            newUsers.setActive(true);
            newUsers.setCompanyName(user.getCompanyName());
            newUsers.setMobileNumber(user.getMobileNumber());
            newUsers.setEmailId(user.getEmailId());
            newUsers.setWhatsappNo(user.getWhatsappNo());
            newUsers.setProfilePic(user.getProfilePic());
            newUsers.setCreated_at(LocalDateTime.now());

            Roles roles = rolesRepository.findByRoleName(StringConstant.SUPER_ADMIN);
            if (roles != null) {
                newUsers.setRole(roles);
            } else {
                Roles superAdminRole = new Roles();
                superAdminRole.setRoleName(StringConstant.SUPER_ADMIN);
                superAdminRole.setActive(true);
                superAdminRole.setCreated_at(LocalDateTime.now());
                superAdminRole = rolesRepository.save(superAdminRole);
                newUsers.setRole(superAdminRole);
            }

            Business savedUser = usersRepository.save(newUsers);

            connection.setUser(savedUser);
            connection.setAuthCode(RandomString.generateRandomString(25));
            getConnectionRepository.save(connection);


            UserDetailsDto userDetailsDto = getUserDetailsDto(savedUser, connection);


            return new GenricDTO<>(StringConstant.SUCCESS, "User registered successfully", userDetailsDto);
        } catch (Exception e) {
            System.out.println("Error in registerBusiness: " + e.getMessage());
            return new GenricDTO<>(StringConstant.ERROR, "Something Went wrong, please try again", null);
        }

    }

    private static UserDetailsDto getUserDetailsDto(Business savedUser, Connection connection) {
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setUserName(savedUser.getUserName());
        userDetailsDto.setCompany_name(savedUser.getCompanyName());
        userDetailsDto.setMobileNumber(savedUser.getMobileNumber());
        userDetailsDto.setEmailId(savedUser.getEmailId());
        userDetailsDto.setWhatsapp_no(savedUser.getWhatsappNo());
        userDetailsDto.setProfilePic(savedUser.getProfilePic());
        userDetailsDto.setAuth_code(connection.getAuthCode());
        return userDetailsDto;
    }
}
