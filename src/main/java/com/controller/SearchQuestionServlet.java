package com.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.model.Question;

import javax.servlet.annotation.WebServlet;

@WebServlet("/searchquestion")
public class SearchQuestionServlet extends HttpServlet {
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
        List<Question> searchedQuestionList = new ArrayList<Question>();

        HttpSession session = request.getSession();
        String keyword = request.getParameter("keyword");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            if (keyword != null && !keyword.isEmpty()) {
                String questionSQL = "SELECT * FROM question WHERE message LIKE CONCAT('%', ?, '%')";
                pstmt = conn.prepareStatement(questionSQL.toString());
                pstmt.setString(1, keyword);

                rs = pstmt.executeQuery();

                while (rs.next()) {
                    Question question = new Question();
                    question.setQuestionID(rs.getInt("questionid"));
                    question.setAskerID(rs.getInt("askerid"));
                    question.setRepID(rs.getInt("repid"));
                    question.setMessage(rs.getString("message"));
                    question.setReply(rs.getString("reply"));
                    searchedQuestionList.add(question);
                }
            }else {
                searchedQuestionList = null;
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

        session.setAttribute("searchedQuestionList", searchedQuestionList);
        request.getRequestDispatcher("support.jsp").forward(request, response);
    }
}