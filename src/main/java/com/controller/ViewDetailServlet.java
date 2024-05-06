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

@WebServlet("/viewdetail")
public class ViewDetailServlet extends HttpServlet {
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
        Item item = new Item();

        int itemid = Integer.parseInt(request.getParameter("itemid"));
        int typeid = Integer.parseInt(request.getParameter("typeid"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            String sql = "SELECT * FROM item where itemid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, itemid);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                item.setItemID(rs.getInt("itemID"));
                item.setPrice(rs.getDouble("price"));
                item.setName(rs.getString("name"));
                item.setColor(rs.getString("color"));
                item.setCond(rs.getString("cond"));
                item.setBrand(rs.getString("brand"));
                item.setTypeID(rs.getInt("typeid"));
                
                if(typeid == 1) {
                    String shirtSQL = "SELECT * FROM shirt where itemid = ?";
                    pstmt = conn.prepareStatement(shirtSQL);
                    pstmt.setInt(1, itemid);
                    rs = pstmt.executeQuery();
                    while(rs.next()) {
                        item.setShirtSize(rs.getString("size"));
                    }
                }
                else if(typeid == 2) {
                    String pantsSQL = "SELECT * FROM pants where itemid = ?";
                    pstmt = conn.prepareStatement(pantsSQL);
                    pstmt.setInt(1, itemid);
                    rs = pstmt.executeQuery();
                    while(rs.next()) {
                        item.setPantSize(rs.getInt("size"));
                    }
                }
                else if(typeid == 3) {
                    String shoesSQL = "SELECT * FROM shoes where itemid = ?";
                    pstmt = conn.prepareStatement(shoesSQL);
                    pstmt.setInt(1, itemid);
                    rs = pstmt.executeQuery();
                    while(rs.next()) {
                        item.setShoeSize(rs.getInt("size"));
                    }
                }
                else {}
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
        session.setAttribute("item", item);

        request.getRequestDispatcher("viewdetail.jsp").forward(request, response);
    }
}
