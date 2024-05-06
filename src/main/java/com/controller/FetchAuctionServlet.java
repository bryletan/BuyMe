package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.model.Item;
import com.model.Auction;
import com.model.Bid;

import javax.servlet.annotation.WebServlet;

@WebServlet("/fetchauction")
public class FetchAuctionServlet extends HttpServlet {
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
        List<Item> itemList = new ArrayList<Item>();
        List<Auction> auctionList = new ArrayList<Auction>();
        List<Bid> bidList = new ArrayList<Bid>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            String sql = "SELECT * FROM item";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setItemID(rs.getInt("itemID"));
                item.setPrice(rs.getDouble("price"));
                item.setName(rs.getString("name"));
                item.setColor(rs.getString("color"));
                
                item.setCond(rs.getString("cond"));
                item.setBrand(rs.getString("brand"));
                item.setTypeID(rs.getInt("typeid"));

                itemList.add(item);
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

                auctionList.add(auction);
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

        HttpSession session = request.getSession();
        session.setAttribute("itemList", itemList);
        session.setAttribute("auctionList", auctionList);
        session.setAttribute("bidList", bidList);

        request.getRequestDispatcher("viewauction.jsp").forward(request, response);
    }
}