package com.example.miniTrackingProject.config;

import com.example.miniTrackingProject.common.RoleEnum;
import com.example.miniTrackingProject.entity.UserEntity;
import com.example.miniTrackingProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("k tim thay user");
        }

        RoleEnum role = user.get().getRole();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

//        return new User(username, user.get().getPassword(), List.of(authority));
        return user.get();
    }

}
