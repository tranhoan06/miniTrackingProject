package com.example.miniTrackingProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/v1/api/noti-template")
@RequiredArgsConstructor
public class NotificationTemplatesController {
}
