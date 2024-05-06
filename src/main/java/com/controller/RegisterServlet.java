package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Preprocess request: we actually don't need to do any business stuff, so just
        // display JSP.
        request.getRequestDispatcher("/webapp/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("signup_username");
        String password = request.getParameter("signup_password");

        // Database connection details
        String dbURL = "jdbc:mysql://localhost:3306/Buyme";
        String dbUser = "root";
        String dbPassword = "pass";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                    PreparedStatement ps = conn
                            .prepareStatement(
                                    "INSERT INTO users(username, password, isCustomerRep) VALUES (?, ?, ?)")) {
                ps.setString(1, username);
                ps.setString(2, password); // try to implement hashing password later
                ps.setBoolean(3, false);
                int result = ps.executeUpdate();

                if (result > 0) {
                    request.setAttribute("message", "Registration successful!");
                } else {
                    request.setAttribute("message", "Registration failed: Unable to register user.");
                }
            }
        } catch (Exception e) {
            request.setAttribute("message", "Registration failed: " + e.getMessage());
        } finally {
            try {
                java.sql.DriverManager.deregisterDriver(new com.mysql.cj.jdbc.Driver());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
