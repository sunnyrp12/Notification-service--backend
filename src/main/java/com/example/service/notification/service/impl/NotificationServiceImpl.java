package com.example.service.notification.service.impl;

import com.example.service.notification.dto.NotificationRequestDTO;
import com.example.service.notification.dto.Response;
import com.example.service.notification.dto.JurisdictionDTO;
import com.example.service.notification.service.NotificationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final String API_URL = "https://o3m5qixdng.execute-api.us-east-1.amazonaws.com/api/managers";

    @Override
    public Response getSupervisors() {

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Object> response = restTemplate.getForEntity(API_URL, Object.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to fetch supervisors data");
            }

            ArrayList<LinkedHashMap<String, Object>> linkedHashMapList = (ArrayList<LinkedHashMap<String, Object>>) response.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            List<JurisdictionDTO> jurisdictionDTOList = objectMapper.convertValue(
                    linkedHashMapList,
                    new TypeReference<List<JurisdictionDTO>>() {}
            );

            jurisdictionDTOList.removeIf(jurisdictionDTO -> jurisdictionDTO.getJurisdiction().matches("-?\\d+(\\.\\d+)?"));

            List<String> supervisorsList = new ArrayList<>();
            if (!jurisdictionDTOList.isEmpty()) {
                jurisdictionDTOList.forEach(jurisdictionDTO -> {
                    String supervisor = jurisdictionDTO.getJurisdiction() + "-" + jurisdictionDTO.getLastName() + " ," + jurisdictionDTO.getFirstName();
                    supervisorsList.add(supervisor);
                });
            }

            Collections.sort(supervisorsList);

            return new Response(false, "Success", supervisorsList);
        } catch (Exception e) {
            return new Response(true, "Fails", e.getLocalizedMessage());
        }
    }

    @Override
    public Response addNotificationRequest(NotificationRequestDTO reqBody) {

        if (reqBody.getFirstName() == null || reqBody.getFirstName().isEmpty()) {
            return new Response(true, "First Name should not be null", null);
        } else if (reqBody.getLastName() == null || reqBody.getLastName().isEmpty()) {
            return new Response(true, "Last Name should not be null", null);
        } else if (reqBody.getSupervisor() == null || reqBody.getSupervisor().isEmpty()) {
            return new Response(true, "Supervisor should not be null", null);
        } else if (reqBody.getFirstName().matches(".*\\d.*")) {
            return new Response(true, "Name must only contain letters", null);
        } else if (reqBody.getPhoneNumber() != null && !reqBody.getPhoneNumber().isEmpty() && !reqBody.getPhoneNumber().matches("^\\d{10}$")) {
            return new Response(true, "Phone number is not valid", null);
        } else if (reqBody.getEmail() != null && !reqBody.getEmail().isEmpty() && !reqBody.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            return new Response(true, "Email is not valid", null);
        }

        System.out.println(reqBody.toString());
        return new Response(false, "Success", null);
    }
}
