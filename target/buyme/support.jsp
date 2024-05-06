<%@ page import="com.model.Question" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Send a Question</title>
  <link rel="stylesheet" href="styles.css?v=3">
</head>
<body>
    <div class="container">
        <nav class="sidebar">
            <ul>
                <li><a href="fetchitem">Home</a></li>
                <li><a href="fetchbid">Bids</a></li>
                <li><a href="fetchalerts">Alerts</a></li>
                <li><a href="support.jsp">Support</a></li>
                <li><a href="login.jsp">Log out</a></li>
            </ul>
        </nav>
        <main class="main-content">
            <h1 style="text-align: center">Ask a Question</h1>
            <div class="form-actions">
                <img src="fetchmyquestions" width="1" height="1">
                <form action="fetchmyquestions" method="GET"> 
                    <button>Refresh</button>
                </form>
                <div class="button-space"></div>
                <button type="button" class="button-back-button" onclick="window.location='fetchitem';">Back</button>
            </div>
            <div class="support-grid-content">
                <div class="support-container">
                    <form action="addquestion" method="POST">
                        <input type="hidden" name="userid" value="<%= session.getAttribute("userid") %>">

                        <div class="support-input-container">
                            <label for="message">Ask Question Here:</label>
                            <input type="text" name="message"/>

                            <div class="form-actions">
                                <button type="submit" class="submit-button" value ="Submit">Submit</button>
                            </div>
                        </div>                 
                    </form>
                    <h2 style="text-align: center">My Questions</h2>
                    <div class="item-grid">
                    <%
                    List<Question> questionList = (ArrayList<Question>) session.getAttribute("questionList");
                    
                    int currentUserId = (int) session.getAttribute("userid");
                        
                    if (questionList != null) {
                        // Create an array to store the questions for each question ID
                        Question[] myQuestions = new Question[questionList.size()];
                        int myQuestionsIndex = 0;
                        for (Question question : questionList) {
                            if (question.getAskerID() != currentUserId) {
                                continue; // Skip questions that are not from the current user
                            }
                            else{
                                myQuestions[myQuestionsIndex] = question;
                            }
                            myQuestionsIndex++;
                        }
                        // Display questions
                        for (int i = 0; i < questionList.size(); i++) {
                            Question currQuestion = myQuestions[i];
                            if (currQuestion != null) {
                    %>
                                <div class="question">
                                    <h2 class="question-row1">Question #<%=currQuestion.getQuestionID() %></h2>
                                    <p class="question-row2">Question: <%=currQuestion.getMessage()%></p>
                                    <p class="question-row3">Status: <%=currQuestion.isClaimed()%></p>
                                    <p class="quesiton-row4">Response: <%=currQuestion.getReplyDisplay()%></p>
                                </div>
                    <%
                                }
                            }
                        }
                    %>
                    </div>
                </div>
                <div class="support-container">
                    <form action="searchquestion" method="GET">
                        <input type="hidden" name="userid" value="<%= session.getAttribute("userid") %>">
                        <div class="support-input-container">
                            <label for="keyword">Keyword:</label>
                            <input type="text" name="keyword" value=""/>
        
                            <div class="form-actions">
                                <button type="submit" class="submit-button" value="Submit">Search</button>
                            </div>
                        </div>
                        <div class="item-grid">
                            <%
                            // Check if the form has been submitted
                            String keyword = request.getParameter("keyword");
            
                            if (keyword != null && !keyword.equals("")) {
                                List<Question> searchedQuestionList = (ArrayList<Question>) session.getAttribute("searchedQuestionList");
                                if (searchedQuestionList != null) {
                                    for (Question question : searchedQuestionList) {
                                        %>
                                        <div class="item">
                                            <h2 class="question-row2"><%=question.getMessage() %></h2>
                                            <p class="question-row3"><%=question.isClaimed()%></p>
                                            <p class="question-row3"><%=question.getReplyDisplay()%></p>
                                        </div>
                                        <%
                                    }
                                }else {
                                %>
                                    <div class="item">
                                        <p class="question-row2">No questions found matching the keyword.</p>
                                    </div>
                                <%
                                }
                            }
                            %>
                        </div>
                    </form>
                </div>  
            </div>
        </main>   
    </div>
</body>