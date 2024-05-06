package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/addquestion")
public class AddQuestionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form data
        // int caseID = Integer.parseInt(request.getParameter("caseID"));
        String message = request.getParameter("message");
        HttpSession session = request.getSession();
        int askerid = (int) session.getAttribute("userid");
        int repid = 0;

        // JDBC variables
        String jdbcUrl = "jdbc:mysql://localhost:3306/Buyme";
        String username = "root";
        String password = "pass";

        // JDBC objects
        Connection conn = null;
        PreparedStatement pstmtQuestion = null;
        ResultSet rs = null;

        try {
            // Connect to the database
            conn = DriverManager.getConnection(jdbcUrl, username, password);

            // Insert question details into the 'question' table
            String insertQuestionSQL = "INSERT INTO question (askerid, repid, message, reply) "
                    + "VALUES (?, ?, ?, ?)";

            // Create a prepared statement to insert question
            pstmtQuestion = conn.prepareStatement(insertQuestionSQL);
            // pstmtQuestion.setInt(1, caseID);
            pstmtQuestion.setInt(1, askerid);
            pstmtQuestion.setInt(2, repid);
            pstmtQuestion.setString(3, message);
            pstmtQuestion.setString(4, null);


            // Execute the query to insert question
            pstmtQuestion.executeUpdate();
            request.getRequestDispatcher("successquestion.jsp").forward(request, response);
            /*
             * // Retrieve the auto-generated case ID
             * rs = pstmtQuestion.getGeneratedKeys();
             * int caseID = -1;
             * if (rs.next()) {
             * caseid = rs.getInt(1);
             * }
             */

            // Display a success message

        } catch (SQLException e) {
            // Handle any database errors
            e.printStackTrace();
        } finally {
            // Close JDBC objects
            try {
                if (rs != null)
                    rs.close();
                if (pstmtQuestion != null)
                    pstmtQuestion.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}