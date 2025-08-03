package com.jmd.staffsync_both.service_impl;

import com.jmd.staffsync_both.config.ApiProperties;
import com.jmd.staffsync_both.dto.ConnectionDto;
import com.jmd.staffsync_both.entity.Connection;
import com.jmd.staffsync_both.repository.GetConnectionRepository;
import com.jmd.staffsync_both.service.ConnectionService;
import com.jmd.staffsync_both.utils.GenricDTO;
import com.jmd.staffsync_both.utils.RandomString;
import com.jmd.staffsync_both.utils.StringConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    private final GetConnectionRepository connectionRepository;

    private final ApiProperties apiProperties;


    public ConnectionServiceImpl(ApiProperties apiProperties, GetConnectionRepository connectionRepository, RandomString randomString) {
        this.apiProperties = apiProperties;
        this.connectionRepository = connectionRepository;
    }


    @Override
    public GenricDTO<ConnectionDto> createConnection(String api_key) {
        try {
            if (api_key == null || api_key.isBlank()) {
                return new GenricDTO<>(StringConstant.ERROR, "API key is missing", null);
            }

            if (!apiProperties.getKey().equals(api_key)) {
                return new GenricDTO<>(StringConstant.INVALID_REQUEST, "Invalid API key", null);
            }

            String generatedConnectionId = RandomString.generateRandomString(25);
            Connection connection = new Connection();
            connection.setConnectionId(generatedConnectionId);
            connection.setCreated_at(LocalDateTime.now());
            connection.setUpdatedAt(LocalDateTime.now());

            Connection saved = connectionRepository.save(connection);
            ConnectionDto dto = new ConnectionDto(saved.getConnectionId());

            return new GenricDTO<>(StringConstant.SUCCESS, "Connection created successfully", dto);

        } catch (Exception e) {
            // Use logger in production
            e.printStackTrace();
            return new GenricDTO<>(StringConstant.ERROR, "Internal server error: " + e.getMessage(), null);
        }
    }


}
