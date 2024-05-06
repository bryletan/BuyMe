package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.model.Bid;
import com.model.Item;

import javax.servlet.annotation.WebServlet;

@WebServlet("/fetchuserbids")
public class FetchUserBidsServlet extends HttpServlet {
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
        List<Item> itemList = new ArrayList<Item>();

        HttpSession session = request.getSession();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            /* 
            String getAskerIDSQL = "SELECT askerid FROM question where questionid = ?";
            pstmt = conn.prepareStatement(getAskerIDSQL);
            pstmt.setInt(1, questionid);
            ResultSet askerIDSet = pstmt.executeQuery();
            int askerid = 0;
            if (askerIDSet.next()) {
                askerid = askerIDSet.getInt("askerID");
            }*/

            String sql = "SELECT * FROM bid";
            pstmt = conn.prepareStatement(sql);
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

            String itemSQL = "SELECT * FROM item";
            PreparedStatement pstmtItem = conn.prepareStatement(itemSQL);
            ResultSet rsItem = pstmtItem.executeQuery();

            while (rsItem.next()) {
                Item item = new Item();
                item.setItemID(rsItem.getInt("itemID"));
                item.setPrice(rsItem.getDouble("price"));
                item.setName(rsItem.getString("name"));
                item.setColor(rsItem.getString("color"));
                
                item.setCond(rsItem.getString("cond"));
                item.setBrand(rsItem.getString("brand"));
                item.setTypeID(rsItem.getInt("typeid"));

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

        session.setAttribute("bidList", bidList);
        session.setAttribute("itemList", itemList);

        request.getRequestDispatcher("answerquestion.jsp").forward(request, response);
    }
}