package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.Auction;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;

import javax.servlet.annotation.WebServlet;

@WebServlet("/addbid")
public class AddBidServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        int itemId = Integer.parseInt(request.getParameter("itemid"));
        int userid = Integer.parseInt(request.getParameter("userid"));
        int auctionid = Integer.parseInt(request.getParameter("auctionid"));
        double bidPrice = Double.parseDouble(request.getParameter("bidprice"));
        String upperLimitStr = request.getParameter("upperlimit");
        String bidIncrementStr = request.getParameter("bidincrement");

        double upperLimit = bidPrice;
        double bidIncrement = 0.00;

        if (upperLimitStr != null && !upperLimitStr.isEmpty()) {
            upperLimit = Double.parseDouble(upperLimitStr);
        }

        if (bidIncrementStr != null && !bidIncrementStr.isEmpty()) {
            bidIncrement = Double.parseDouble(bidIncrementStr);
        }

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

            String getAuctionSQL = "SELECT * FROM auction WHERE itemid=?";
            pstmt = conn.prepareStatement(getAuctionSQL);
            pstmt.setInt(1, itemId);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                //if the user is not the user who made the auction
                if(rs.getInt("userid") != userid) {
                    //if the auction is not closed, let the user place a bid
                    if(rs.getBoolean("is_closed") == false) {
                        // Retrieve current price of the item
                        String getCurrentPriceSQL = "SELECT price FROM item WHERE itemid=?";
                        PreparedStatement pstmtSelectItem = conn.prepareStatement(getCurrentPriceSQL);
                        pstmtSelectItem.setInt(1, itemId);
                        ResultSet itemSet = pstmtSelectItem.executeQuery();
                        double currentPrice = 0.0;
                        if (itemSet.next()) {
                            currentPrice = itemSet.getDouble("price");
                        }
    
                        if (bidPrice > currentPrice) {
                            // Update current price in the item table
                            String updateCurrentPriceSQL = "UPDATE item SET price=? WHERE itemid=?";
                            PreparedStatement pstmtUpdateCurrentPrice = conn.prepareStatement(updateCurrentPriceSQL);
                            pstmtUpdateCurrentPrice.setDouble(1, bidPrice);
                            pstmtUpdateCurrentPrice.setInt(2, itemId);
                            pstmtUpdateCurrentPrice.executeUpdate();
    
                            // Check if any existing autobids need to be updated
                            String getAutoBidsSQL = "SELECT * FROM bid WHERE itemid = ? AND bid_price < ? AND upper_limit > ? AND bid_increment > ? AND userid != ?";
                            pstmt = conn.prepareStatement(getAutoBidsSQL);
                            pstmt.setInt(1, itemId);
                            pstmt.setDouble(2, bidPrice);
                            pstmt.setDouble(3, bidPrice);
                            pstmt.setDouble(4, 0); // as long as they have a bid increment, check for autobid
                            pstmt.setInt(5, userid);
                            rs = pstmt.executeQuery();
    
                            // Update autobids
                            while (rs.next()) {
                                double newAutoBidPrice = bidPrice + rs.getDouble("bid_increment");
                                if (newAutoBidPrice > rs.getDouble("upper_limit")) {
                                    continue;
                                }
                                else {
    
                                    String insertAutoBidSQL = "INSERT INTO bid (itemid, bid_price, upper_limit, bid_increment, userid, has_alert, auctionid) "
                                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
                                    pstmt = conn.prepareStatement(insertAutoBidSQL);
                                    pstmt.setInt(1, rs.getInt("itemid"));
                                    pstmt.setDouble(2, newAutoBidPrice);
                                    pstmt.setDouble(3, rs.getDouble("upper_limit"));
                                    pstmt.setDouble(4, rs.getDouble("bid_increment"));
                                    pstmt.setInt(5, rs.getInt("userid"));
                                    pstmt.setBoolean(6, rs.getBoolean("has_alert"));
                                    pstmt.setInt(7, rs.getInt("auctionid"));
                                    pstmt.executeUpdate();
    
                                    // Update current price in the item table
                                    String updateAutoBidPriceSQL = "UPDATE item SET price=? WHERE itemid=?";
                                    PreparedStatement pstmtItem = conn.prepareStatement(updateAutoBidPriceSQL);
                                    pstmtItem.setDouble(1, newAutoBidPrice);
                                    pstmtItem.setInt(2, itemId);
                                    pstmtItem.executeUpdate();
                                }
                            }
    
                            // Place the new bid
                            String insertBidSQL = "INSERT INTO bid (itemid, bid_price, upper_limit, bid_increment, userid, has_alert, auctionid) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
                            pstmt = conn.prepareStatement(insertBidSQL);
                            pstmt.setInt(1, itemId);
                            pstmt.setDouble(2, bidPrice);
                            pstmt.setDouble(3, upperLimit);
                            pstmt.setDouble(4, bidIncrement);
                            pstmt.setInt(5, userid);
                            pstmt.setBoolean(6, false);
                            pstmt.setInt(7, auctionid);
                            pstmt.executeUpdate();

                            RequestDispatcher dispatcher = request.getRequestDispatcher("successbid.jsp");
                            dispatcher.forward(request,response);
    
                            // Check if any existing bid with an alert loses to the current price
                            String getAlertBidSQL = "SELECT * FROM bid WHERE itemid = ? AND bid_price < ? AND userid != ? AND has_alert = ?";
                            pstmt = conn.prepareStatement(getAlertBidSQL);
                            pstmt.setInt(1, itemId);
                            pstmt.setDouble(2, bidPrice);
                            pstmt.setInt(3, userid);
                            pstmt.setBoolean(4, true);
                            rs = pstmt.executeQuery();
    
                            while (rs.next()) {
                                //Send alerts to other users
                                String insertAlertSQL = "INSERT INTO alert (message_code, itemid, userid) "
                                + "VALUES (?, ?, ?)";
                                pstmt = conn.prepareStatement(insertAlertSQL);
                                pstmt.setInt(1, 1);
                                pstmt.setInt(2, rs.getInt("itemid"));
                                pstmt.setInt(3, rs.getInt("userid"));
                                pstmt.executeUpdate();
                            }
    
                            // Check if any existing bid lose to the current bid price
                            String getBidsSQL = "SELECT * FROM bid WHERE itemid = ? AND bid_price < ? AND upper_limit < ? AND userid != ?";
                            pstmt = conn.prepareStatement(getBidsSQL);
                            pstmt.setInt(1, itemId);
                            pstmt.setDouble(2, bidPrice);
                            pstmt.setDouble(3, bidPrice);
                            pstmt.setInt(4, userid);
                            rs = pstmt.executeQuery();
    
                            while (rs.next()) {
                                //Send alerts to other users
                                String insertAlertSQL = "INSERT INTO alert (message_code, itemid, userid) "
                                + "VALUES (?, ?, ?)";
                                pstmt = conn.prepareStatement(insertAlertSQL);
                                pstmt.setInt(1, 2);
                                pstmt.setInt(2, rs.getInt("itemid"));
                                pstmt.setInt(3, rs.getInt("userid"));
                                pstmt.executeUpdate();
                            }
                        } else { 
                            RequestDispatcher dispatcher = request.getRequestDispatcher("errorbid.jsp");
                            dispatcher.forward(request,response);
                        }
                    }
                    else {
                        //unable to place bid since auction is closed
                        RequestDispatcher dispatcher = request.getRequestDispatcher("errorbidclosed.jsp");
                        dispatcher.forward(request,response);
                        
                    }
                }
                else {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("errorbidown.jsp");
                    dispatcher.forward(request,response);
                }
            }
            // Commit the transaction
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