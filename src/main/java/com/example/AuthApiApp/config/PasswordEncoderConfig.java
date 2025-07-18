package com.example.AuthApiApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *  PasswordEncoder - бин компонент используеться
 *  при создании новых пользователей и аутентификации.
 *  BCryptPasswordEncoder - инструмент шифрования паролей.
 *
 *  Для решения проблемы зацикливания выносом бин PasswordEncoder
 *  в отдельный класс, в SecurityConfig инициализируем по конструктору.
 */
@Configuration
public class PasswordEncoderConfig {

    @Bean //Степень кодировки, с которой кодировали пароль в базе
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }
}
