package com.example.miniTrackingProject.controller;

import com.example.miniTrackingProject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/v1/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
}
