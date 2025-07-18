package com.example.AuthApiApp.controller;

import com.example.AuthApiApp.model.Person;
import com.example.AuthApiApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    public UserController(UserService users) {
        this.users = users;
    }

    private final UserService users;

    /**
     * Метод принимает пароль и юзернейм и добавляет их в сессионный кэш
     * @param person
     */
    @PostMapping("/sign-up")
    public void signUp(@RequestBody Person person) {
        users.save(person);
    }

    /**
     * Метод принимает пароль и проверяет есть ли в кэше данный юзер и возвращает да/нет
     * @param password
     * @return
     */
    @GetMapping("/check")
    public ResponseEntity<Boolean> check(@RequestParam String password) {
        boolean userExists = users.containsUserWithPassword(password);
        return ResponseEntity.ok(userExists);
    }

}
