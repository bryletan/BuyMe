package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

import javax.servlet.annotation.WebServlet;

@WebServlet("/generatereport")
public class GenerateReportServlet extends HttpServlet {
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
        String reportString = "";

        HttpSession session = request.getSession();

        int reporttype = Integer.parseInt(request.getParameter("reporttype"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            String setupSQL = "CREATE TEMPORARY TABLE winning_bids AS "+
                                "SELECT b.bidid, b.itemid, b.bid_price, b.userid, b.auctionid "+
                                "FROM bid b "+
                                "JOIN ( "+
                                    "SELECT MAX(bid_price) AS max_bid_price, bid.auctionid "+
                                    "FROM bid "+
                                    "JOIN auction ON bid.auctionid = auction.auctionid "+
                                    "WHERE auction.is_closed = 1 "+
                                    "GROUP BY bid.auctionid "+
                                ") max_bids ON b.bid_price = max_bids.max_bid_price AND b.auctionid = max_bids.auctionid "+
                                "JOIN auction a ON b.auctionid = a.auctionid "+
                                "WHERE a.is_closed = 1;";

            pstmt = conn.prepareStatement(setupSQL);
            pstmt.executeUpdate();

            //get total earnnings
            if(reporttype == 1){
                String sql = "SELECT SUM(bid_price) AS total_earnings FROM winning_bids;";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    // Retrieve the value of "total_earnings" column from the result set
                    double totalEarnings = rs.getDouble("total_earnings");
                    reportString = "Total Earnings: $" + totalEarnings;
                } else {
                    // Handle case where no result is returned
                    reportString = "No earnings found.";
                }
            }

            //get earnnings by item
            if(reporttype == 2){
                String sql = "SELECT i.name AS item_name, SUM(wb.bid_price) AS total_earnings " + 
                                "FROM winning_bids wb " + 
                                "JOIN item i ON wb.itemid = i.itemid " + 
                                "GROUP BY i.name;";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    do{
                        double earnings = rs.getDouble("total_earnings");
                        reportString += rs.getString("item_name")+": $" + earnings + "\n";
                    }while(rs.next());
                } else {
                    // Handle case where no result is returned
                    reportString = "No earnings found.";
                }
            }
            
            //get earnnings by item type
            if(reporttype == 3){
                String sql = "SELECT i.typeid AS item_type, SUM(wb.bid_price) AS total_earnings " + 
                                "FROM winning_bids wb " + 
                                "JOIN item i ON wb.itemid = i.itemid " + 
                                "GROUP BY i.typeid "+
                                "ORDER BY i.typeid;";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    do{
                        int itemtype =  rs.getInt("item_type");
                        if(itemtype == 1){
                            double earnings = rs.getDouble("total_earnings");
                            reportString += "Shirts: $" + earnings;
                        }
                        if(itemtype == 2){
                            double earnings = rs.getDouble("total_earnings");
                            reportString += "\nPants: $" + earnings;
                        }
                        if(itemtype == 3){
                            double earnings = rs.getDouble("total_earnings");
                            reportString += "\nShoes: $" + earnings;
                        }
                    }while(rs.next());
                } else {
                    // Handle case where no result is returned
                    reportString = "No earnings found.";
                }
            }

            //get earnnings by seller
            if(reporttype == 4){
                String sql = "SELECT a.userid AS seller, SUM(wb.bid_price) AS total_earnings " + 
                                "FROM winning_bids wb " + 
                                "JOIN auction a ON wb.itemid = a.itemid " + 
                                "GROUP BY a.userid "+
                                "ORDER BY a.userid;";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    do{
                        double earnings = rs.getDouble("total_earnings");
                        reportString += rs.getInt("seller")+"'s Total: $" + earnings + "\n";
                    }while(rs.next());
                } else {
                    // Handle case where no result is returned
                    reportString = "No earnings found.";
                }
            }

            //get earnnings by best selling items
            if(reporttype == 5){
                String sql = "SELECT i.name AS item_name, SUM(wb.bid_price) AS total_earnings " + 
                                "FROM winning_bids wb " + 
                                "JOIN item i ON wb.itemid = i.itemid " + 
                                "GROUP BY i.name " +
                                "ORDER BY total_earnings DESC";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    do{
                        double earnings = rs.getDouble("total_earnings");
                        reportString += rs.getString("item_name")+": $" + earnings + "\n";
                    }while(rs.next());
                } else {
                    // Handle case where no result is returned
                    reportString = "No earnings found.";
                }
            }

            //get earnnings by best buyer
            if(reporttype == 6){
                String sql = "SELECT userid AS buyer, SUM(wb.bid_price) AS total_earnings " + 
                                "FROM winning_bids wb " + 
                                "GROUP BY userid "+
                                "ORDER BY total_earnings DESC;";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    do{
                        double earnings = rs.getDouble("total_earnings");
                        reportString += rs.getInt("buyer")+"'s Total: $" + earnings + "\n";
                    }while(rs.next());
                } else {
                    // Handle case where no result is returned
                    reportString = "No earnings found.";
                }
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

        session.setAttribute("report", reportString);

        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }
}