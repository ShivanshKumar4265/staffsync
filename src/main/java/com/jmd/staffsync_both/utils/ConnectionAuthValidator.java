package com.jmd.staffsync_both.utils;

import com.jmd.staffsync_both.entity.Business;
import com.jmd.staffsync_both.entity.Connection;
import com.jmd.staffsync_both.repository.GetConnectionRepository;
import org.springframework.stereotype.Service;

@Service
public class ConnectionAuthValidator {

    private final GetConnectionRepository connectionRepository;

    public ConnectionAuthValidator(GetConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }


    public Business validateConnectionAuth(String connectionId, String authCode) {
        if (connectionId == null || connectionId.isEmpty() || authCode == null || authCode.isEmpty()) {
            return null; // Invalid input
        }

        Connection connection = connectionRepository.findByConnectionId(connectionId);
        if (connection == null) {
            return null; // Connection ID not found
        }

        // Check if authCode matches
        if (!authCode.equals(connection.getAuthCode())) {
            return null; // Auth code does not match
        }

        // Get associated user
        Business user = connection.getUser();
        if (user == null) {
            return null; // No user associated
        }

        return user;
    }
}
