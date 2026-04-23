package com.example.miniTrackingProject.service;

public interface MailService {
    void send(String to, String subject, String htmlOrTextBody);

}
