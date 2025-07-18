package com.example.AuthApiApp.repository;

import com.example.AuthApiApp.model.Person;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Пользователей будем хранить в памяти.
 * В дальнейшем этот класс можно заменить на базу данных.
 */
@Component
public class UserStore {
    private final ConcurrentHashMap<String, Person> users = new ConcurrentHashMap<>();

    public void save(Person person) {
        users.put(person.getUsername(), person);
    }

    public Collection<Person> getAllUsers() {
        return users.values();
    }

    public Person findByUsername(String username) {
        return users.get(username);
    }

}
