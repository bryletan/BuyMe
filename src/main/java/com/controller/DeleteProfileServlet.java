package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.Auction;
import com.model.Bid;
import com.model.Item;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

@WebServlet("/deleteprofile")
public class DeleteProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       // Get the session
       HttpSession session = request.getSession();

       // Retrieve form data
       int askerid = (int) session.getAttribute("askerid");
       int questionid = (int) session.getAttribute("questionid");

        // JDBC variables
        String jdbcUrl = "jdbc:mysql://localhost:3306/Buyme";
        String username = "root";
        String password = "pass";

        // JDBC objects
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            // Connect to the database
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            conn.setAutoCommit(false); // Start a transaction

            String deleteUserSQL = "DELETE FROM users WHERE userid = ?";
            pstmt = conn.prepareStatement(deleteUserSQL);
            pstmt.setInt(1, askerid);
            pstmt.executeUpdate();
            response.getWriter().println("The account has been successfully deleted.");
        
            String updateReplySQL = "UPDATE question SET reply=? WHERE questionid=?";
            PreparedStatement pstmtUpdateUsername = conn.prepareStatement(updateReplySQL);
            pstmtUpdateUsername.setString(1, "Account Deleted");
            pstmtUpdateUsername.setInt(2, questionid);
            pstmtUpdateUsername.executeUpdate();

            String deleteAskersQuestionsSQL = "DELETE FROM question WHERE askerid = ?";
            PreparedStatement pstmtDeleteAskersQuestions = conn.prepareStatement(deleteAskersQuestionsSQL);
            pstmtDeleteAskersQuestions.setInt(1, askerid);
            pstmtDeleteAskersQuestions.executeUpdate();

            // Commit the transaction
            conn.commit();

            request.getRequestDispatcher("customerrep.jsp").forward(request, response);

        } catch (SQLException e) {
            // Rollback the transaction in case of an error
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            // Handle any database errors
            e.printStackTrace();
        } finally {
            // Close JDBC objects
            try {
                //if (rs != null)
                //    rs.close();
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