package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

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
                ps.setString(2, password);
                ps.setBoolean(3, true);
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

        request.getRequestDispatcher("/admin.jsp").forward(request, response);
    }
}