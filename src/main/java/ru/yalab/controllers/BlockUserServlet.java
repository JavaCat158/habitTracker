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
import java.io.PrintWriter;

@WebServlet("/user/block")
public class BlockUserServlet extends HttpServlet {
    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BlockUserServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = objectMapper.readTree(req.getInputStream()).get("email").asText();
        try {
            UserDto userDto = userService.blockUser(email);
            if (userDto != null) {
                resp.setStatus(HttpServletResponse.SC_OK);
                String jsonResponse = objectMapper.writeValueAsString(userDto);
                PrintWriter out = resp.getWriter();
                out.write(jsonResponse);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"Ошибка\":\"Пользователь не найден\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"Ошибка\":\"Ошибка сервера\"}");
        }
    }
}
