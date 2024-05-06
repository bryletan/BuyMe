package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/createauction")
public class CreateAuctionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form data
        int userid = Integer.parseInt(request.getParameter("userid"));
        int typeid = Integer.parseInt(request.getParameter("typeid"));
        String itemName = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String color = request.getParameter("color");

        int sizeInt = 0;
        String sizeString = request.getParameter("size");
        
        if(typeid != 1) {
            sizeInt = Integer.parseInt(sizeString);
        }
        
        String condition = request.getParameter("cond");
        String brand = request.getParameter("brand");
        String closingTime = request.getParameter("closingtime");
        double minPrice = Double.parseDouble(request.getParameter("minprice"));

        // JDBC variables
        String jdbcUrl = "jdbc:mysql://localhost:3306/Buyme";
        String username = "root";
        String password = "pass";

        // JDBC objects
        Connection conn = null;
        PreparedStatement pstmtItem = null;
        PreparedStatement pstmtAuction = null;
        ResultSet rs = null;

        try {
            // Connect to the database
            conn = DriverManager.getConnection(jdbcUrl, username, password);

            // Insert item details into the 'item' table
            String insertItemSQL = "INSERT INTO item (name, price, color, cond, brand, typeID) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            // Create a prepared statement to insert item
            pstmtItem = conn.prepareStatement(insertItemSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmtItem.setString(1, itemName);
            pstmtItem.setDouble(2, price);
            pstmtItem.setString(3, color);
            pstmtItem.setString(4, condition);
            pstmtItem.setString(5, brand);
            pstmtItem.setInt(6, typeid);

            pstmtItem.executeUpdate();

            // Retrieve the auto-generated item ID
            rs = pstmtItem.getGeneratedKeys();
            int itemId = -1;
            if (rs.next()) {
                itemId = rs.getInt(1);
            }

            if (typeid == 1) {
                String insertShirtSQL = "INSERT INTO shirt (itemid, size) " + "VALUES (?, ?)";

                pstmtItem = conn.prepareStatement(insertShirtSQL);
                pstmtItem.setInt(1, itemId);
                pstmtItem.setString(2, sizeString);

                pstmtItem.executeUpdate();
            }
            else if (typeid == 2) {
                String insertShirtSQL = "INSERT INTO pants (itemid, size) " + "VALUES (?, ?)";

                pstmtItem = conn.prepareStatement(insertShirtSQL);
                pstmtItem.setInt(1, itemId);
                pstmtItem.setInt(2, sizeInt);

                pstmtItem.executeUpdate();
            }
            else if (typeid == 3) {
                String insertShirtSQL = "INSERT INTO shoes (itemid, size) " + "VALUES (?, ?)";

                pstmtItem = conn.prepareStatement(insertShirtSQL);
                pstmtItem.setInt(1, itemId);
                pstmtItem.setInt(2, sizeInt);

                pstmtItem.executeUpdate();
            }
            else {}

            // Insert auction details into the 'auction' table using the generated item ID
            String insertAuctionSQL = "INSERT INTO auction (itemid, closing_time, min_price, userid, is_closed) "
                    + "VALUES (?, ?, ?, ?, ?)";

            // Create a prepared statement to insert auction
            pstmtAuction = conn.prepareStatement(insertAuctionSQL);
            pstmtAuction.setInt(1, itemId);
            pstmtAuction.setString(2, closingTime);
            pstmtAuction.setDouble(3, minPrice);
            pstmtAuction.setInt(4, userid);
            pstmtAuction.setBoolean(5, false);

            pstmtAuction.executeUpdate();

        } catch (SQLException e) {
            // Handle any database errors
            e.printStackTrace();
        } finally {
            // Close JDBC objects
            try {
                if (rs != null)
                    rs.close();
                if (pstmtItem != null)
                    pstmtItem.close();
                if (pstmtAuction != null)
                    pstmtAuction.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        request.getRequestDispatcher("fetchitem").forward(request, response);
    }
}