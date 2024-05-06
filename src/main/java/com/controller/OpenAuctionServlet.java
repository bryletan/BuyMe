package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.Bid;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

@WebServlet("/openauction")
public class OpenAuctionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the session
        HttpSession session = request.getSession();
        int userid = (int) session.getAttribute("userid");

        // Retrieve form data
        int auctionid = Integer.parseInt(request.getParameter("auctionid"));
        int itemid = Integer.parseInt(request.getParameter("itemid"));       

        // JDBC variables
        String jdbcUrl = "jdbc:mysql://localhost:3306/Buyme";
        String username = "root";
        String password = "pass";

        // JDBC objects
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Bid> bidList = new ArrayList<Bid>();

        try {
            // Connect to the database
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            conn.setAutoCommit(false); // Start a transaction

            // Check if the user has placed a bid on the specified item
            String updateAuctionSQL = "UPDATE auction SET is_closed = FALSE WHERE auctionid = ?";
            pstmt = conn.prepareStatement(updateAuctionSQL);
            pstmt.setInt(1, auctionid);
            pstmt.executeUpdate();

            String sql = "SELECT * FROM alert WHERE itemid = ? AND message_code = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, itemid);
            pstmt.setInt(2, 0);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                String updateAlertSQL = "UPDATE alert SET message_code = ? WHERE itemid = ?";
                pstmt = conn.prepareStatement(updateAlertSQL);
                pstmt.setInt(1, 5);  
                pstmt.setInt(2, itemid);           
                pstmt.executeUpdate();
            }

            conn.commit();
            request.getRequestDispatcher("fetchitem").forward(request, response);

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
