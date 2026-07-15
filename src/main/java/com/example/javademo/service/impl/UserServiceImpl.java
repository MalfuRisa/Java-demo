package com.example.javademo.service.impl;

import com.example.javademo.entity.User;
import com.example.javademo.repository.UserRepository;
import com.example.javademo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private static final String DEFAULT_ROLE = "USER";
    private static final int MIN_PASSWORD_LENGTH = 6;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void register(String username, String rawPassword) {
        String normalizedUsername = normalizeUsername(username);

        validatePassword(rawPassword);

        if (userRepository.existsByUsername(normalizedUsername)) {
            throw new IllegalArgumentException("用户名已经存在");
        }

        User user = new User();
        user.setUsername(normalizedUsername);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(DEFAULT_ROLE);

        userRepository.save(user);
    }

    /**
     * 校验并清理用户名。
     */
    private String normalizeUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("用户名不能为空");
        }

        String normalizedUsername = username.trim();

        if (normalizedUsername.length() > 50) {
            throw new IllegalArgumentException("用户名长度不能超过50个字符");
        }

        return normalizedUsername;
    }

    /**
     * 校验用户输入的明文密码。
     */
    private void validatePassword(String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("密码不能为空");
        }

        if (rawPassword.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException(
                    "密码长度不能少于" + MIN_PASSWORD_LENGTH + "位"
            );
        }
    }

}
