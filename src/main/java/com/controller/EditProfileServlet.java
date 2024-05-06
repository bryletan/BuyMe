package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.model.Bid;
import com.model.Item;
import com.model.Question;
import com.model.User;

import javax.servlet.annotation.WebServlet;

@WebServlet("/editprofile")
public class EditProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the session
        HttpSession session = request.getSession();

        // Retrieve form data
        int askerid = (int) session.getAttribute("askerid");
        String newUsername = request.getParameter("username");
        String newPassword = request.getParameter("password");
        List<User> userList = new ArrayList<User>();

        // JDBC variables
        String jdbcUrl = "jdbc:mysql://localhost:3306/Buyme";
        String username = "root";
        String password = "pass";

        // JDBC objects
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            conn.setAutoCommit(false);

            String sql = "SELECT * FROM users";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                
                userList.add(user);
            }


            if((newUsername == null || newUsername.equals("")) && (newPassword == null || newPassword.equals(""))){
                request.setAttribute("changeuserinfomsg", "Fields must not be empty.");
            }
            else{
                if((newUsername != null && !newUsername.equals("")) && (newPassword == null || newPassword.equals(""))){
                    //check if username is taken
                    boolean userTaken = false;
                    for(User user: userList){
                        if(newUsername.equals(user.getUsername())) userTaken = true;
                    }
                    if(userTaken){
                        request.setAttribute("changeuserinfomsg", "Username Taken");
                    }else{
                        // Update username in the item table
                        String updateUsernameSQL = "UPDATE users SET username=? WHERE userid=?";
                        PreparedStatement pstmtUpdateUsername = conn.prepareStatement(updateUsernameSQL);
                        pstmtUpdateUsername.setString(1, newUsername);
                        pstmtUpdateUsername.setInt(2, askerid);
                        pstmtUpdateUsername.executeUpdate();
                        
                        request.setAttribute("changeuserinfomsg", "Username Changed to :"+newUsername);
                    }
                }
                else if((newUsername == null || newUsername.equals("")) && (newPassword != null && !newPassword.equals(""))){
                    // Update password in the item table
                    String updatePasswordSQL = "UPDATE users SET password=? WHERE userid=?";
                    PreparedStatement pstmtUpdatePassword = conn.prepareStatement(updatePasswordSQL);
                    pstmtUpdatePassword.setString(1, newUsername);
                    pstmtUpdatePassword.setInt(2, askerid);
                    pstmtUpdatePassword.executeUpdate();

                    request.setAttribute("changeuserinfomsg", "Password Changed to :"+newPassword);
                }
                else{
                    //check if username is taken
                    boolean userTaken = false;
                    for(User user: userList){
                        if(newUsername.equals(user.getUsername())) userTaken = true;
                    }
                    if(userTaken){
                        request.setAttribute("changeuserinfomsg", "Username Taken");
                    }else{
                        // Update username in the item table
                        String updateUsernameSQL = "UPDATE users SET username=? WHERE userid=?";
                        PreparedStatement pstmtUpdateUsername = conn.prepareStatement(updateUsernameSQL);
                        pstmtUpdateUsername.setString(1, newUsername);
                        pstmtUpdateUsername.setInt(2, askerid);
                        pstmtUpdateUsername.executeUpdate();

                        String updatePasswordSQL = "UPDATE users SET password=? WHERE userid=?";
                        PreparedStatement pstmtUpdatePassword = conn.prepareStatement(updatePasswordSQL);
                        pstmtUpdatePassword.setString(1, newUsername);
                        pstmtUpdatePassword.setInt(2, askerid);
                        pstmtUpdatePassword.executeUpdate();

                        request.setAttribute("changeuserinfomsg", "Username Changed to :"
                            +newUsername+" and Password Changed to :"+newPassword);
                    }

                }
            }
            conn.commit();


        } catch (SQLException | ClassNotFoundException e) {
            // Rollback the transaction in case of an error
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            // Handle any database errors
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
        session.setAttribute("askerid", askerid);

        request.getRequestDispatcher("answerquestion.jsp").forward(request, response);
    }
}