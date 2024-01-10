package com.example.zadanie.service;

import com.example.zadanie.dto.RegisterDTO;
import com.example.zadanie.entity.Role;
import com.example.zadanie.entity.User;
import com.example.zadanie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void isUsernameAlreadyExists(String username) {
        if(userRepository.existsByUsername(username)) {
            throw new RuntimeException("User with this username already exists");
        }
    }

    public void registerUser(RegisterDTO register) {
        isUsernameAlreadyExists(register.getUsername());
        User user = createUser(register);
        userRepository.save(user);
    }

    private User createUser(RegisterDTO register) {
        return User.builder()
                .username(register.getUsername())
                .password(passwordEncoder.encode(register.getPassword()))
                .isEnabled(true)
                .role(Role.CUSTOMER)
                .build();
    }

}
