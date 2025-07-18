package com.example.AuthApiApp.controller;

import com.example.AuthApiApp.model.Person;
import com.example.AuthApiApp.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void signUp_ShouldCallServiceSave() {
        Person testPerson = new Person();
        testPerson.setUsername("Ivan");
        testPerson.setPassword("12345");

        userController.signUp(testPerson);

        // Проверка: был ли вызван метод save у сервиса ровно 1 раз
        // Примечание: Мы не проверяем результат, так как метод void
        // Главное - убедиться, что сервис был вызван с правильными данными
        verify(userService, times(1)).save(testPerson);
    }

    @Test
    void check_WhenPasswordExists_ShouldReturnTrue() {
        // Настраиваем mock: при вызове containsUserWithPassword с "12345" возвращаем true
        String testPassword = "12345";
        when(userService.containsUserWithPassword(testPassword)).thenReturn(true);

        // Вызываем метод контроллера
        ResponseEntity<Boolean> response = userController.check(testPassword);

        // Проверяем:
        // 1. Что тело ответа - true
        // 2. Что статус ответа - 200 OK
        assertTrue(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void check_WhenPasswordNotExists_ShouldReturnFalse() {
        // Arrange
        String testPassword = "wrong";
        when(userService.containsUserWithPassword(testPassword)).thenReturn(false);

        // Act
        ResponseEntity<Boolean> response = userController.check(testPassword);

        // Assert
        assertFalse(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
}
