package com.example.AuthApiApp.service;

import com.example.AuthApiApp.model.Person;
import com.example.AuthApiApp.repository.UserStore;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Слой бизнес обработки модели User
 */

@Service
public class UserServiceImpl implements UserService {
    private final UserStore userStore;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserStore userStore, PasswordEncoder encoder) {
        this.userStore = userStore;
        this.encoder = encoder;
    }

    @Override
    public Person findByUsername(String username) {
        return userStore.findByUsername(username);
    }

    /**
     * Пароли хешируются и прямом виде не хранятся в базе.
     * @param person
     */
    @Override
    public void save(Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        userStore.save(person);
    }

    /**
     * Проверяет, существует ли пользователь с указанным паролем.
     * BCrypt — это алгоритм одностороннего хеширования, уникальный
     * хеш невозможно преобразовать для получения исходного пароля.
     * С помощью BCrypt.matches() можно сравнить необработанный
     * пароль (введенный пользователем) с сохраненным хешем.
     * @param rawPassword пароль в открытом виде (не хэшированный)
     * @return true если найден пользователь с совпадающим паролем
     */
    @Override
    public boolean containsUserWithPassword(String rawPassword) {
        return userStore.getAllUsers().stream()
                .anyMatch(person -> encoder.matches(rawPassword, person.getPassword()));
    }

}
