package ru.yalab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.yalab.dto.LoginRequest;
import ru.yalab.dto.LoginResponse;
import ru.yalab.services.UserService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoginServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LoginRequest loginRequest = objectMapper.readValue(req.getInputStream(), LoginRequest.class);
            LoginResponse loginResponse = userService.login(loginRequest);

            // Проверяем статус входа
            if ("Успешно".equals(loginResponse.getStatus())) {
                resp.setStatus(HttpServletResponse.SC_OK); // 200 OK
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            }

            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), loginResponse); // Возвращаем объект LoginResponse

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Внутренняя ошибка сервера
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\":\"Ошибка сервера при выполнении входа\"}");
        }
    }
}