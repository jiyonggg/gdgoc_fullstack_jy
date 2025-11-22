package com.example.jsp_servlet_project2;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username);
        System.out.println(password);

        request.setAttribute("username", username);
        request.setAttribute("password", password);

        // request에 저장한 attribute와 함께 result.jsp로 포워딩
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }
}