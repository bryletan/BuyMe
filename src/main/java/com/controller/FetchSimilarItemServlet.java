package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.model.Item;
import com.model.Auction;
import com.model.Bid;

import javax.servlet.annotation.WebServlet;

@WebServlet("/similar")
public class FetchSimilarItemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        int userid = (int) session.getAttribute("userid");
        String itemName = request.getParameter("name");

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
            String name = request.getParameter("name");
            String similarItemSQL = "SELECT i2.* FROM item i1 JOIN item i2 ON i1.color = i2.color AND i1.cond = i2.cond WHERE i1.name = ?";
            pstmt = conn.prepareStatement(similarItemSQL);
            pstmt.setString(1, name);
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
        request.getRequestDispatcher("similar.jsp").forward(request, response);
    }
}
