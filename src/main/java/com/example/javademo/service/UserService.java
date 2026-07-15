package com.example.javademo.service;


public interface UserService {
    /**
     * 注册用户。
     *
     * @param username 用户名
     * @param rawPassword 用户输入的明文密码
     */

    void register(String username, String rawPassword);
}
