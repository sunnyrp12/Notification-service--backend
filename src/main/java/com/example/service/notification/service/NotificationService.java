package com.example.service.notification.service;

import com.example.service.notification.dto.NotificationRequestDTO;
import com.example.service.notification.dto.Response;

public interface NotificationService {

    Response getSupervisors();

    Response addNotificationRequest(NotificationRequestDTO reqBody);
}
