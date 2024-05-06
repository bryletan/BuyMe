package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.model.Bid;
import com.model.Item;
import com.model.Auction;

import javax.servlet.annotation.WebServlet;

@WebServlet("/viewparticipateauction")
public class ViewParticipateAuctionServlet extends HttpServlet {
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

        HttpSession session = request.getSession();

        // Retrieve form data
        int participate_auction;

        // Check if the parameter exists in the request
        String participateAuctionParam = request.getParameter("participate_auction");
        if (participateAuctionParam != null && !participateAuctionParam.isEmpty()) {
            // If the parameter is provided, parse its value
            participate_auction = Integer.parseInt(participateAuctionParam);
        } else {
            // If the parameter is not provided, set a default value of -1
            participate_auction = -1;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            if(participate_auction != -1) {
                String auctionSQL = "SELECT DISTINCT a.* " +
                        "FROM auction a " +
                        "INNER JOIN bid b ON a.auctionid = b.auctionid " +
                        "WHERE b.userid = ?";
                pstmt = conn.prepareStatement(auctionSQL);
                pstmt.setInt(1, participate_auction);
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
            }
            
            String itemSQL = "SELECT * FROM item";
            PreparedStatement pstmtItem = conn.prepareStatement(itemSQL);
            rs = pstmtItem.executeQuery();

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

        session.setAttribute("auctionList", auctionList);
        session.setAttribute("itemList", itemList);

        request.getRequestDispatcher("viewusersauction.jsp").forward(request, response);
    }
}