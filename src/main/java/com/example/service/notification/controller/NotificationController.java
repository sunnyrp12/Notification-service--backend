package com.example.service.notification.controller;

import com.example.service.notification.dto.NotificationRequestDTO;
import com.example.service.notification.dto.Response;
import com.example.service.notification.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/supervisors")
    public Response getSupervisors() {
        return notificationService.getSupervisors();
    }

    @PostMapping("/submit")
    public Response addNotificationRequest(@RequestBody NotificationRequestDTO reqBody) {
        return notificationService.addNotificationRequest(reqBody);
    }

}
