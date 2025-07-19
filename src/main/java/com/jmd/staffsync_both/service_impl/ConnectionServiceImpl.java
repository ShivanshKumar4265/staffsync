package com.jmd.staffsync_both.service_impl;

import com.jmd.staffsync_both.config.ApiProperties;
import com.jmd.staffsync_both.dto.ConnectionDto;
import com.jmd.staffsync_both.entity.Connection;
import com.jmd.staffsync_both.repository.GetConnectionRepository;
import com.jmd.staffsync_both.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    @Autowired
    private GetConnectionRepository connectionRepository;

    private final ApiProperties apiProperties;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    public ConnectionServiceImpl(ApiProperties apiProperties) {
        this.apiProperties = apiProperties;
    }

    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }


    @Override
    public ConnectionDto createConnection(String api_key) {
        try{
            if (!apiProperties.getKey().equals(api_key)) {
                return new ConnectionDto("error", "Invalid API key", "");
            }
            String generatedConnectionId = generateRandomString(25);
            Connection connection = new Connection();
            connection.setConnection_id(generatedConnectionId);
            connection.setCreated_at(LocalDateTime.now());
            connection.setUpdated_at(LocalDateTime.now());
            Connection saved = connectionRepository.save(connection);
            return new ConnectionDto("success", "create successfully", saved.getConnection_id()==null?"": saved.getConnection_id());
        }catch (Exception e){
            return new ConnectionDto("error", e.getMessage(), "");
        }
    }
}
