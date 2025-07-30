package com.jmd.staffsync_both.service_impl;

import com.jmd.staffsync_both.dto.UserDetailsDto;
import com.jmd.staffsync_both.dto.UsersDto;
import com.jmd.staffsync_both.entity.Users;
import com.jmd.staffsync_both.repository.UsersRepository;
import com.jmd.staffsync_both.service.UserService;
import com.jmd.staffsync_both.utils.StringConstant;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class UsersServiceImpl implements UserService {

    UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UsersDto registerUser(Users user) {
        try {
            if (user.getUserName() == null || user.getUserName().isBlank()) {
                return new UsersDto(StringConstant.INVALID_REQUEST, "User name is required", null);
            }

            if (user.getCompanyName() == null || user.getCompanyName().isBlank()) {
                return new UsersDto(StringConstant.INVALID_REQUEST, "Company name is required", null);
            }

            if (user.getMobileNumber() == null || user.getMobileNumber().isBlank()) {
                return new UsersDto(StringConstant.INVALID_REQUEST, "Mobile number is required", null);
            }

            if (usersRepository.existsByEmailId(user.getEmailId())) {
                return new UsersDto(StringConstant.CONFLICT, "Email ID already exists", null);
            }


            if (user.getEmailId() == null || user.getEmailId().isBlank()) {
                return new UsersDto(StringConstant.INVALID_REQUEST, "Email ID is required", null);
            }

            if (usersRepository.existsByMobileNumber(user.getMobileNumber())) {
                return new UsersDto(StringConstant.CONFLICT, "Mobile number already exists", null);
            }


            // Email validation using regex
            if (!user.getEmailId().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                return new UsersDto(StringConstant.INVALID_REQUEST, "Invalid email format", null);
            }

            // Mobile number validation (10-digit Indian numbers)
            if (!user.getMobileNumber().matches("^[6-9]\\d{9}$")) {
                return new UsersDto(StringConstant.INVALID_REQUEST, "Invalid mobile number format", null);
            }

            user.setCreated_at(LocalDateTime.now());
            user.setActive(true);
            Users users = usersRepository.save(user);

            UserDetailsDto userDetails = new UserDetailsDto(
                    users.getUserName(),
                    users.getMobileNumber(),
                    users.getEmailId(),
                    users.getProfilePic()
            );

            return new UsersDto(StringConstant.SUCCESS, "User registered successfully", userDetails);
        } catch (Exception e) {
            return new UsersDto(StringConstant.ERROR, "Something Went wrong, please try again", null);

        }

    }
}
