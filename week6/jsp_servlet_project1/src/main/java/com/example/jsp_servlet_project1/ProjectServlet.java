package com.example.jsp_servlet_project1;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

// WebServlet 어노테이션: 서블릿 3.0 이상부터 지원
// 이 어노테이션을 사용하면 web.xml에 명시할 필요 없음
@WebServlet("/ProjectServlet") // /ProjectServlet에 매핑
public class ProjectServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}