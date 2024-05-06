package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.Bid;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

@WebServlet("/addalert")
public class AddAlertServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the session
        HttpSession session = request.getSession();
        int userid = (int) session.getAttribute("userid");

        // Retrieve form data
        int itemId = Integer.parseInt(request.getParameter("itemid"));

        // JDBC variables
        String jdbcUrl = "jdbc:mysql://localhost:3306/Buyme";
        String username = "root";
        String password = "pass";

        // JDBC objects
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Connect to the database
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            conn.setAutoCommit(false); // Start a transaction

            String insertAlertSQL = "INSERT INTO alert (message_code, itemid, userid) "
            + "VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(insertAlertSQL);
            pstmt.setInt(1, 0);
            pstmt.setInt(2, itemId);
            pstmt.setInt(3, userid);
            pstmt.executeUpdate();

            /* 
            String checkDuplicateAlertSQL = "SELECT * FROM alert WHERE itemid = ? AND userid = ?";
            pstmt = conn.prepareStatement(checkDuplicateAlertSQL);
            pstmt.setInt(1, itemId);
            pstmt.setInt(2, userid);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                response.getWriter().println("You Already Have Duplicate Alert");
            } else {
                //get current bid
                String getBidSQL = "SELECT * FROM bid WHERE itemid = ? AND userid = ?";
                pstmt = conn.prepareStatement(getBidSQL);
                pstmt.setInt(1, itemId);
                pstmt.setInt(2, userid);
                rs = pstmt.executeQuery();
                // Place the new alert
                if (rs.next()) {
                    String alertSQL = "INSERT INTO alert (message_code, itemid, userid, bidid)"
                        + "VALUES (?, ?, ?, ?)";
                    PreparedStatement pstmtAlert = conn.prepareStatement(alertSQL);
                    pstmtAlert = conn.prepareStatement(alertSQL);
                    pstmtAlert.setInt(1, 1); // 1 = outbidded
                    pstmtAlert.setInt(2, itemId);
                    pstmtAlert.setInt(3, userid);
                    pstmtAlert.setInt(4, rs.getInt("bidid"));
                    pstmtAlert.executeUpdate();
                }
            }
            */
            // Commit the transaction
            conn.commit();
            request.getRequestDispatcher("sucessalert.jsp").forward(request, response);

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