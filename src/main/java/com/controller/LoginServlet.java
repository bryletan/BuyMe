package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/webapp/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Database connection details
        String dbURL = "jdbc:mysql://localhost:3306/Buyme";
        String dbUser = "root";
        String dbPassword = "pass";

        // Initialize JDBC objects
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isAuthenticated = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            String sql = "SELECT * FROM users WHERE username = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            // Check if the result set contains any rows
            if (rs.next()) {
                // User found, check if the provided password matches the stored password
                String storedPassword = rs.getString("password");
                if (password.equals(storedPassword)) {
                    // Passwords match, set isAuthenticated to true
                    isAuthenticated = true;
                }
            }

            if (isAuthenticated) {
                // Store user information in the session
                HttpSession session = request.getSession();
                session.setAttribute("userid", rs.getInt("userid"));
                session.setAttribute("user", username);
                Boolean isCustomerRep = rs.getBoolean("isCustomerRep");
                if (username.equals("admin")) {
                    session.setAttribute("report", "");
                    response.sendRedirect("admin.jsp");
                } else if (isCustomerRep) {
                    response.sendRedirect("fetchquestion");
                } else {
                    // Redirect to a protected page or welcome page
                    session.setAttribute("report", "");
                    response.sendRedirect("fetchitem");
                }
                // request.getRequestDispatcher("success.jsp").forward(request, response);
            } else {
                response.sendRedirect("error.jsp");
                // request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Close JDBC objects in the finally block
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
