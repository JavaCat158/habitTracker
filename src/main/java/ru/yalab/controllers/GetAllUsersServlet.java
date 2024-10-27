package ru.yalab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.yalab.dto.UserDto;
import ru.yalab.services.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/users")
public class GetAllUsersServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GetAllUsersServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Получаем всех пользователей
            List<UserDto> users = userService.getAllUsers();
            if (users.isEmpty()) {
                // Если пользователей нет, возвращаем статус 404
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Пользователи не найдены
                resp.getWriter().write("{\"error\":\"Пользователи не найдены\"}");
            } else {
                // Возвращаем статус 200 и список пользователей
                resp.setStatus(HttpServletResponse.SC_OK); // 200 Успешно
                String jsonResponse = objectMapper.writeValueAsString(users);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonResponse);
            }
        } catch (SQLException e) {
            // В случае ошибки возвращаем 500
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Внутренняя ошибка сервера
            resp.getWriter().write("{\"error\":\"Ошибка сервера при получении списка пользователей\"}");
        }
    }
}
