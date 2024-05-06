package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

import com.model.Alert;
import com.model.Bid;
import com.model.Item;
import com.model.Auction;

import javax.servlet.annotation.WebServlet;

@WebServlet("/fetchalerts")
public class FetchAlertsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // JDBC URL, username, and password of MySQL server
        String dbURL = "jdbc:mysql://localhost:3306/Buyme";
        String dbUser = "root";
        String dbPassword = "pass";

        // Initialize JDBC objects
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Bid> bidList = new ArrayList<Bid>();
        List<Alert> alertList = new ArrayList<Alert>();
        List<Auction> auctionList = new ArrayList<Auction>();
        List<Item> itemList = new ArrayList<Item>();

        HttpSession session = request.getSession();
        int userid = (int) session.getAttribute("userid");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            String selectSQL = "SELECT * FROM alert WHERE userid = ?";
            pstmt = conn.prepareStatement(selectSQL);
            pstmt.setInt(1, userid);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Alert alert = new Alert();
                alert.setAlertID(rs.getInt("alertid"));
                alert.setMessageCode(rs.getInt("message_code"));
                alert.setItemID(rs.getInt("itemid"));
                alert.setUserID(rs.getInt("userid"));
                alertList.add(alert);
            }

            String auctionSQL = "SELECT * FROM auction";
            pstmt = conn.prepareStatement(auctionSQL);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Auction auction = new Auction();
                auction.setAuctionID(rs.getInt("auctionid"));
                auction.setItemID(rs.getInt("itemid"));

                Timestamp timestamp = rs.getTimestamp("closing_time");
                LocalDateTime closingTime = null;
                if (timestamp != null) {
                    closingTime = timestamp.toLocalDateTime();
                }
                auction.setClosingTime(closingTime);
                auction.setMinPrice(rs.getDouble("min_price"));
                auction.setUserID(rs.getInt("userid"));
                auction.setIsClosed(rs.getBoolean("is_closed"));
                auctionList.add(auction);
            }

            String sql = "SELECT * FROM item";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setItemID(rs.getInt("itemid"));
                item.setPrice(rs.getDouble("price"));
                item.setName(rs.getString("name"));
                item.setColor(rs.getString("color"));
                
                item.setCond(rs.getString("cond"));
                item.setBrand(rs.getString("brand"));
                item.setTypeID(rs.getInt("typeid"));

                itemList.add(item);
            }

            String bidSQL = "SELECT * FROM bid";
            pstmt = conn.prepareStatement(bidSQL);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Bid bid = new Bid();
                bid.setBidID(rs.getInt("bidid"));
                bid.setItemID(rs.getInt("itemid"));
                bid.setBidPrice(rs.getDouble("bid_price"));
                bid.setUpperLimit(rs.getDouble("upper_limit"));
                bid.setBidIncrement(rs.getDouble("bid_increment"));
                bid.setUserID(rs.getInt("userid"));
                bid.setHasAlert(rs.getBoolean("has_alert"));
                bid.setAuctionID(rs.getInt("auctionid"));

                bidList.add(bid);
            }

            //checking winner whenever user clicks refresh alerts
            LocalDateTime currentDateTime = LocalDateTime.now();

            // Map to store the maximum bid price for each auction
            Map<Integer, Double> maxBidPricesMap = new HashMap<>();

            // Iterate through the bids to find the maximum bid price for each auction
            for (Bid bid : bidList) {
                int itemId = bid.getItemID();
                double bidPrice = bid.getBidPrice();

                // Check if the bid price is greater than the current maximum bid price for the auction
                if (!maxBidPricesMap.containsKey(itemId) || bidPrice > maxBidPricesMap.get(itemId)) {
                    maxBidPricesMap.put(itemId, bidPrice);
                }
            }

            // Iterate through the auctions to determine the winners and losers
            for (Auction auction : auctionList) {
                int itemId = auction.getItemID();
                double maxBidPrice = maxBidPricesMap.getOrDefault(itemId, 0.0);

                // Check if the auction has closed
                if (auction.getClosingTime().isBefore(currentDateTime) && auction.getIsClosed() == false) {
                    // Send alerts to winners and losers
                    for (Bid bid : bidList) {
                        if (bid.getItemID() == itemId) {
                            
                            String closeAuctionSQL = "UPDATE auction SET is_closed = true WHERE itemid = ?";
                            pstmt = conn.prepareStatement(closeAuctionSQL);
                            pstmt.setInt(1, itemId);
                            pstmt.executeUpdate();
                            
                            if(auction.getMinPrice() < maxBidPrice) {
                                String checkDuplicateAlertSQL = "SELECT * FROM alert WHERE itemid = ? AND userid = ?";
                            pstmt = conn.prepareStatement(checkDuplicateAlertSQL);
                            pstmt.setInt(1, itemId);
                            pstmt.setInt(2, bid.getUserID());
                            rs = pstmt.executeQuery();

                            if (rs.next()) {
                                
                            } else {
                                String insertAlertSQL = "INSERT INTO alert (message_code, itemid, userid) "
                                    + "VALUES (?, ?, ?)";
                                pstmt = conn.prepareStatement(insertAlertSQL);
                                if (bid.getBidPrice() == maxBidPrice) {
                                    // Winner
                                    pstmt.setInt(1, 3);
                                } else {
                                    // Loser
                                    pstmt.setInt(1, 4);
                                }
                                pstmt.setInt(2, itemId);
                                pstmt.setInt(3, bid.getUserID());
                                pstmt.executeUpdate();
                            }
                            }
                        }
                    }
                }
            }
            /* 
            for (Item item : itemList) {
                // Check if any existing bid with an alert loses to the current price
                String getAlertBidSQL = "SELECT * FROM bid WHERE itemid = ? AND bid_price < ? AND userid != ? AND has_alert = ?";
                pstmt = conn.prepareStatement(getAlertBidSQL);
                pstmt.setInt(1, item.getItemID());
                pstmt.setDouble(2, item.getPrice());
                pstmt.setInt(3, userid);
                pstmt.setBoolean(4, true);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    //Send alerts to other users
                    String insertAlertSQL = "INSERT INTO alert (message_code, itemid, userid, bidid) "
                    + "VALUES (?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(insertAlertSQL);
                    pstmt.setInt(1, 1);
                    pstmt.setInt(2, rs.getInt("itemid"));
                    pstmt.setInt(3, rs.getInt("userid"));
                    pstmt.setInt(4, rs.getInt("bidid"));
                    pstmt.executeUpdate();
                }
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
                String insertAlertSQL = "INSERT INTO alert (message_code, itemid, userid, bidid) "
                + "VALUES (?, ?, ?, ?)";
                pstmt = conn.prepareStatement(insertAlertSQL);
                pstmt.setInt(1, 2);
                pstmt.setInt(2, rs.getInt("itemid"));
                pstmt.setInt(3, rs.getInt("userid"));
                pstmt.setInt(4, rs.getInt("bidid"));
                pstmt.executeUpdate();
            }
            */
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
        session.setAttribute("itemList", itemList);
        session.setAttribute("alertList", alertList);
        request.getRequestDispatcher("alerts.jsp").forward(request, response);
    }
}