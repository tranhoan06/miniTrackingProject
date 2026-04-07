package com.example.miniTrackingProject.common;

import com.example.miniTrackingProject.entity.UserEntity;
import com.example.miniTrackingProject.exception.JavaBuilderException;
import com.example.miniTrackingProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityHelper {

    private final UserRepository userRepository;

    public UserEntity getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new JavaBuilderException(ErrorCode.USER_NOT_FOUND);
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof UserEntity user) {
            return user;
        }
        return userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.USER_NOT_FOUND));
    }
}