<!-- 1.Customer Representatives -->
<%@ page import="com.model.Question" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Customer Rep</title>
  <link rel="stylesheet" href="styles.css?v=3">
</head>
<body>
    <div style="text-align: center; font-family: Arial, sans-serif; padding: 20px;">
        <h2>Welcome <%= session.getAttribute("user") %>!</h2>
        <p style="margin-bottom: 20px;">You are currently logged in.</p>
        <div class="center-refresh">
            <button type="button" class="viewusers-back-button" onclick="window.location='logout.jsp';">Log Out</button>
        </div>
    </div>

      <main class="main-content">
            <script type="text/javascript">
                setTimeout(function(){
                    location = ''
                },60*100)
            </script>
            <img src="fetchquestion" width="1" height="1">
            <div class="form-actions">
                <form action="fetchquestion" method="get">
                    <button>Refresh</button>
                </form>
                <div class="button-space"></div>
                <form action="fetchauction" class="action-button">
                    <button>View Auctions</button>
                </form>
            </div>
            
            
            <h1 style="text-align: center;">My Claimed Questions</h1>
            <div class="item-grid">
                <%
                List<Question> questionList = (ArrayList<Question>) session.getAttribute("questionList");
                    
                int currentUserId = (int) session.getAttribute("userid");
                    
                if (questionList != null) {
                    // Create an array to store the questions for each question ID
                    Question[] myQuestions = new Question[questionList.size()];
                    int myQuestionsIndex = 0;
                    for (Question question : questionList) {
                        if (question.isResolved() || question.getRepID() != currentUserId) {
                            continue; // Skip questions that are not unresolved from the current user
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
                            <div class="alert">
                                <div class='alert-container'>
                                    <h2 class="alert-row3">Question #<%=currQuestion.getQuestionID() %></h2>
                                    <p class="alert-row2">Asked by User # <%=currQuestion.getAskerID()%></p>
                                    <p class="alert-row3">Question: <%=currQuestion.getMessage()%></p>
                                </div>
                                <div class="flex-grow"></div>
                                <form action="claimquestion" method="get" style="display: inline;">
                                    <input type="hidden" name="questionid" value="<%=currQuestion.getQuestionID()%>">
                                    <input type="hidden" name="askerid" value="<%=currQuestion.getAskerID()%>">
                                    <input type="hidden" name="questionmessage" value="<%=currQuestion.getMessage()%>">
                                    <button type="submit">Continue Resolving</button>
                                </form>
                            </div>
                <%
                            }
                        }
                    }
                %>
            </div>
            <h1 style="text-align: center;">Unclaimed Questions</h1>
            <div class="item-grid">
                <%
                    
                if (questionList != null) {
                    // Create an array to store the questions for each item ID
                    Question[] unclaimedQuestions = new Question[questionList.size()];
                    int unclaimedQuestionsIndex = 0;
                    for (Question question : questionList) {
                        if (question.getRepID() != 0) {
                            continue; // Skip questions that are not from the current user
                        }
                        else{
                            unclaimedQuestions[unclaimedQuestionsIndex] = question;
                        }
                        unclaimedQuestionsIndex++;
                    }
                    // Display questions
                    for (int i = 0; i < questionList.size(); i++) {
                        Question currQuestion = unclaimedQuestions[i];
                        if (currQuestion != null) {
                %>
                            <div class="alert">
                                <div class="alert-container">
                                    <h2 class='alert-row3'>Question #<%=currQuestion.getQuestionID() %></h2>
                                    <p class="alert-row2">Asked by User # <%=currQuestion.getAskerID()%></p>
                                    <p class="alert-row3">Question: <%=currQuestion.getMessage()%></p>
                                </div>
                                <div class="flex-grow"></div>
                                <form action="claimquestion" method="get" style="display: inline;">
                                    <input type="hidden" name="questionid" value="<%=currQuestion.getQuestionID()%>">
                                    <input type="hidden" name="askerid" value="<%=currQuestion.getAskerID()%>">
                                    <input type="hidden" name="questionmessage" value="<%=currQuestion.getMessage()%>">
                                    <button type="submit">Claim Question</button>
                                  </form>
                            </div>
                <%
                            }
                        }
                    }
                %>
            </div>
        </main>
</body>
</html>
