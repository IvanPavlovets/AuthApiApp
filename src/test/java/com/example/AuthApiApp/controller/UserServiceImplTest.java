package com.example.AuthApiApp.controller;

import com.example.AuthApiApp.model.Person;
import com.example.AuthApiApp.repository.UserStore;
import com.example.AuthApiApp.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserStore userStore;

    @Mock
    private BCryptPasswordEncoder encoder;

    // Тестируемый сервис
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void save_ShouldEncodePasswordAndCallStoreSave() {
        // Arrange
        Person testPerson = new Person();
        testPerson.setUsername("Ivan");
        testPerson.setPassword("12345");

        // Настраиваем mock: при вызове encode("12345") возвращаем "encodedPassword"
        when(encoder.encode("12345")).thenReturn("encodedPassword");

        // Act
        userService.save(testPerson);

        // Assert
        verify(encoder, times(1)).encode("12345");
        // Проверяем:
        // 1. Что пароль был закодирован
        assertEquals("encodedPassword", testPerson.getPassword());
        verify(userStore, times(1)).save(testPerson);
    }

    @Test
    void containsUserWithPassword_WhenPasswordMatches_ShouldReturnTrue() {
        // Arrange
        Person person1 = new Person();
        person1.setUsername("Ivan");
        person1.setPassword("encodedPassword1");

        Person person2 = new Person();
        person2.setUsername("Petr");
        person2.setPassword("encodedPassword2");

        // Настраиваем mock хранилища
        when(userStore.getAllUsers()).thenReturn(Arrays.asList(person1, person2));
        // Настраиваем mock кодировщика:
        // - для "12345" и "encoded1" возвращаем true (пароль совпадает)
        when(encoder.matches("12345", "encodedPassword1")).thenReturn(true);

        // Вызываем тестируемый метод
        boolean result = userService.containsUserWithPassword("12345");

        // Проверяем что найдено совпадение
        assertTrue(result);
    }

    @Test
    void containsUserWithPassword_WhenNoMatchingPassword_ShouldReturnFalse() {
        Person person = new Person();
        person.setUsername("Ivan");
        person.setPassword("encodedPassword");

        when(userStore.getAllUsers()).thenReturn(Collections.singletonList(person));
        when(encoder.matches("wrong", "encodedPassword")).thenReturn(false);

        boolean result = userService.containsUserWithPassword("wrong");

        assertFalse(result);
    }

    @Test
    void findByUsername_ShouldCallStoreFindByUsername() {
        String username = "Ivan";
        Person expectedPerson = new Person();
        expectedPerson.setUsername(username);
        expectedPerson.setPassword("encodedPassword");

        when(userStore.findByUsername(username)).thenReturn(expectedPerson);

        Person result = userService.findByUsername(username);

        assertEquals(expectedPerson, result);
        verify(userStore, times(1)).findByUsername(username);
    }
}
