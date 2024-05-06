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

import javax.servlet.annotation.WebServlet;

@WebServlet("/sort")
public class SortServlet extends HttpServlet {
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

        String sortPrice = request.getParameter("sort_price");
        String sortName = request.getParameter("sort_name");
        String sortType = request.getParameter("sort_type");

        String sortClosing = request.getParameter("sort_closing");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            String sql = "SELECT * FROM item";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            boolean hasParameters = false;
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String paramValue = request.getParameter(paramName);
                if (paramValue != null && !paramValue.isEmpty()) {
                    hasParameters = true;
                    break;
                }
            }
            if (hasParameters) {
                String auctionSQL = "SELECT * FROM auction";
                StringBuilder itemSQL = new StringBuilder("SELECT * FROM item WHERE 1=1");

                sortPrice = request.getParameter("sort_price");
                sortName = request.getParameter("sort_name");
                sortType = request.getParameter("sort_type");
                // sortClosing = request.getParameter("sort_closing");

                if (sortPrice != null && !sortPrice.isEmpty()) {
                    // Sorting option is selected, include it in the SQL query
                    if ("Low_to_High".equals(sortPrice)) {
                        itemSQL.append(" ORDER BY price ASC");
                    } else if ("High_to_Low".equals(sortPrice)) {
                        itemSQL.append(" ORDER BY price DESC");
                    }

                    pstmt = conn.prepareStatement(itemSQL.toString());
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
                }

                if (sortName != null && !sortName.isEmpty()) {
                    // Sorting option is selected, include it in the SQL query
                    if ("a-z".equals(sortName)) {
                        itemSQL.append(" ORDER BY name ASC");
                    } else if ("z-a".equals(sortName)) {
                        itemSQL.append(" ORDER BY name DESC");
                    }

                    pstmt = conn.prepareStatement(itemSQL.toString());
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
                }

                if (sortType != null && !sortType.isEmpty()) {
                    // Sorting option is selected, include it in the SQL query
                    if ("shirt".equals(sortType)) {
                        itemSQL.append(" AND typeid = 1");
                    } else if ("pants".equals(sortType)) {
                        itemSQL.append(" AND typeid = 2");
                    } else if ("shoes".equals(sortType)) {
                        itemSQL.append(" AND typeid = 3");
                    }

                    pstmt = conn.prepareStatement(itemSQL.toString());
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
                }

                // ADD THIS TO sort.jsp
                // <label for="sort_closing">Bid Closing Time: </label>
                // <select name="sort_closing">
                //     <option value="">Select</option>
                //     <option value="Ending_Soonest">Ending Soonest</option>
                //     <option value="Ending_Latest">Ending Latest</option>
                // </select>
                // pstmt = conn.prepareStatement(auctionSQL);
                // rs = pstmt.executeQuery();
                // while (rs.next()) {
                // Auction auction = new Auction();
                // auction.setAuctionID(rs.getInt("auctionid"));
                // auction.setItemID(rs.getInt("itemid"));
                // Timestamp timestamp = rs.getTimestamp("closing_time");
                // LocalDateTime closingTime = null;
                // if (timestamp != null) {
                // closingTime = timestamp.toLocalDateTime();
                // }
                // auction.setClosingTime(closingTime);
                // auction.setMinPrice(rs.getDouble("min_price"));
                // auction.setUserID(rs.getInt("userid"));
                // auctionList.add(auction);
                // }

                // // sort by closing date
                // StringBuilder closingSQL = new StringBuilder(
                // "SELECT item.*, auction.* FROM item INNER JOIN auction ON item.itemid =
                // auction.itemid WHERE 1=1");
                // if (sortClosing != null && !sortClosing.isEmpty()) {
                // // Sorting option is selected, include it in the SQL query
                // if ("Ending_Soonest".equals(sortClosing)) { // change the closingSQL by the
                // earlist date
                // closingSQL.append(" ORDER BY STR_TO_DATE(closing_time, '%Y-%m-%dT%H:%i')
                // ASC");
                // } else { // change the closingSQL by the latest date
                // closingSQL.append(" ORDER BY STR_TO_DATE(closing_time, '%Y-%m-%dT%H:%i')
                // DESC");
                // }
                // }

                // pstmt = conn.prepareStatement(closingSQL.toString());
                // rs = pstmt.executeQuery();

                // while (rs.next()) {
                // Item item = new Item();
                // item.setItemID(rs.getInt("itemid"));
                // item.setPrice(rs.getDouble("price"));
                // item.setName(rs.getString("name"));
                // item.setColor(rs.getString("color"));
                // item.setSize(rs.getString("size"));
                // item.setCond(rs.getString("cond"));
                // item.setBrand(rs.getString("brand"));

                // itemList.add(item);
                // }

            } else {

            }
        } catch (SQLException |

                ClassNotFoundException e) {
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

        request.getRequestDispatcher("sort.jsp").forward(request, response);
    }
}