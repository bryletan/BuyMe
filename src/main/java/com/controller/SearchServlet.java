package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.model.Item;

import javax.servlet.annotation.WebServlet;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
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
        List<Item> searchedItemList = new ArrayList<Item>();

        HttpSession session = request.getSession();
        int userid = (int) session.getAttribute("userid");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            /* 
            String itemSQL = "SELECT * FROM item";
            PreparedStatement pstmtItem = conn.prepareStatement(itemSQL);
            rs = pstmtItem.executeQuery();
            */

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
                StringBuilder itemSQL = new StringBuilder("SELECT * FROM item WHERE 1=1");

                if (request.getParameter("name") != null && !request.getParameter("name").isEmpty()) {
                    itemSQL.append(" AND name = ?");
                }
                if (request.getParameter("lower_price") != null && !request.getParameter("lower_price").isEmpty()
                        && request.getParameter("upper_price") != null && !request.getParameter("upper_price").isEmpty()) {
                    itemSQL.append(" AND price BETWEEN ? AND ?");
                }
                if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
                    itemSQL.append(" AND size = ?");
                }
                if (request.getParameter("cond") != null && !request.getParameter("cond").isEmpty()) {
                    itemSQL.append(" AND cond = ?");
                }
                if (request.getParameter("color") != null && !request.getParameter("color").isEmpty()) {
                    itemSQL.append(" AND color = ?");
                }
                if (request.getParameter("brand") != null && !request.getParameter("brand").isEmpty()) {
                    itemSQL.append(" AND brand = ?");
                }

                pstmt = conn.prepareStatement(itemSQL.toString());

                // Set parameters based on user input
                int parameterIndex = 1;
                if (request.getParameter("name") != null && !request.getParameter("name").isEmpty()) {
                    pstmt.setString(parameterIndex++, request.getParameter("name"));
                }
                if (request.getParameter("lower_price") != null && !request.getParameter("lower_price").isEmpty()
                        && request.getParameter("upper_price") != null && !request.getParameter("upper_price").isEmpty()) {
                    pstmt.setDouble(parameterIndex++, Double.parseDouble(request.getParameter("lower_price")));
                    pstmt.setDouble(parameterIndex++, Double.parseDouble(request.getParameter("upper_price")));
                }
                if (request.getParameter("color") != null && !request.getParameter("color").isEmpty()) {
                    pstmt.setString(parameterIndex++, request.getParameter("color"));
                }
                if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
                    pstmt.setString(parameterIndex++, request.getParameter("size"));
                }
                if (request.getParameter("cond") != null && !request.getParameter("cond").isEmpty()) {
                    pstmt.setString(parameterIndex++, request.getParameter("cond"));
                }
                if (request.getParameter("brand") != null && !request.getParameter("brand").isEmpty()) {
                    pstmt.setString(parameterIndex++, request.getParameter("brand"));
                }

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

                    searchedItemList.add(item);
                }
            }
            else {
                searchedItemList = null;
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

        session.setAttribute("searchedItemList", searchedItemList);

        request.getRequestDispatcher("search.jsp").forward(request, response);
    }
}