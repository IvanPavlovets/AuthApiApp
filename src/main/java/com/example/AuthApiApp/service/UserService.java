package com.example.AuthApiApp.service;

import com.example.AuthApiApp.model.Person;

public interface UserService {

    Person findByUsername(String username);

    void save(Person person);

    boolean containsUserWithPassword(String password);
}
