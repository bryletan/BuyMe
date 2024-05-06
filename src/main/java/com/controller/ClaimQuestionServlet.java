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

import javax.servlet.annotation.WebServlet;

@WebServlet("/claimquestion")
public class ClaimQuestionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the session
        HttpSession session = request.getSession();

        // Retrieve form data
        int repid = (int) session.getAttribute("userid");
        int questionid = Integer.parseInt(request.getParameter("questionid"));
        int askerid = Integer.parseInt(request.getParameter("askerid"));
        String questionmessage = request.getParameter("questionmessage");
        List<Question> questionList = new ArrayList<Question>();

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


            // Update repid in the item table
            String updateRepIDSQL = "UPDATE question SET repid=? WHERE questionid=?";
            PreparedStatement pstmtUpdateRepID = conn.prepareStatement(updateRepIDSQL);
            pstmtUpdateRepID.setDouble(1, repid);
            pstmtUpdateRepID.setInt(2, questionid);
            pstmtUpdateRepID.executeUpdate();


            String sql = "SELECT * FROM question";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Question question = new Question();
                question.setQuestionID(rs.getInt("questionid"));
                question.setAskerID(rs.getInt("askerid"));
                question.setRepID(rs.getInt("repid"));
                question.setMessage(rs.getString("message"));
                
                questionList.add(question);
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
        session.setAttribute("askerid", askerid);
        session.setAttribute("questionid", questionid);
        session.setAttribute("questionList", questionList);
        session.setAttribute("questionmessage", questionmessage);
        
        request.getRequestDispatcher("fetchuserbids").forward(request, response);
    }
}