<%@ page import="com.model.Bid" %>
<%@ page import="com.model.Item" %>
<%@ page import="com.model.Alert" %>
<%@ page import="com.model.Auction" %>

<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Alerts</title>
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
        <h2 style="text-align: center;">Your Current Alerts</h2>
        <div class="form-actions">
            <form action="fetchalerts" method="GET"> 
                <button>Refresh</button>
            </form>
            <div class="button-space"></div>
        </div>
        <div class="item-grid">
            <%
            List<Alert> alertList = (ArrayList<Alert>) session.getAttribute("alertList");
            List<Item> itemList = (ArrayList<Item>) session.getAttribute("itemList");   
            int currentUserId = (int) session.getAttribute("userid");

            if (alertList != null) {
                for (Alert alert : alertList) {
                    for(Item item : itemList) {
                        if(alert.getUserID() == currentUserId) {
                            if(alert.getItemID() == item.getItemID()) {
                                if (alert.getMessageCode() == 0) {
                                    %>
                                    <div class="alert">
                                        <div class='alert-container'>
                                            <h2 class="alert-row1">Alert</h2>
                                            <p class="alert-row2">Alert ID: <%= alert.getAlertID() %></p>
                                            <p class="alert-row3"><%= item.getName() %> is closed!</p>
                                        </div>
                                        <div class="flex-grow"></div>
                                        <form action="deletealert" method="GET">
                                            <input type="hidden" name="alertid" value="<%= alert.getAlertID() %>">
                                            <button type="submit">Dismiss</button>
                                        </form>
                                    </div>
                                    
                                    <%
                                }
                                else if (alert.getMessageCode() == 1) {
                                    %>
                                    <div class="alert">
                                        <div class="alert-container">
                                            <h2 class="alert-row1">Alert</h2>
                                            <p class="alert-row2">Alert ID: <%= alert.getAlertID() %></p>
                                            <p class="alert-row3">You've been outbidded for <%= item.getName() %>!</p>
                                        </div>
                                        <div class="flex-grow"></div>
                                        <form action="deletealert" method="GET">
                                            <input type="hidden" name="alertid" value="<%= alert.getAlertID() %>">
                                            <button type="submit">Dismiss</button>
                                        </form>
                                    </div>
                                    
                                    <%
                                }
                                else if (alert.getMessageCode() == 2) {
                                    %>
                                    <div class="alert">
                                        <div class="alert-container">
                                            <h2 class="alert-row1">Alert</h2>
                                            <p class="alert-row2">Alert ID: <%= alert.getAlertID() %></p>
                                            <p class="alert-row3">Your upper limit has been outbidded for <%= item.getName() %>!</p>
                                        </div>
                                        <div class="flex-grow"></div>
                                        <form action="deletealert" method="GET">
                                            <input type="hidden" name="alertid" value="<%= alert.getAlertID() %>">
                                            <button type="submit">Dismiss</button>
                                        </form>
                                    </div>
                                    
                                    <%
                                }
                                else if (alert.getMessageCode() == 3) {
                                    %>
                                    <div class="alert">
                                        <div class="alert-container">
                                            <h2 class="alert-row1">Alert</h2>
                                            <p class="alert-row2">Alert ID: <%= alert.getAlertID() %></p>
                                            <p class="alert-row3">You have won a bid on <%= item.getName() %>!</p>
                                        </div>
                                        <div class="flex-grow"></div>
                                        <form action="deletealert" method="GET">
                                            <input type="hidden" name="alertid" value="<%= alert.getAlertID() %>">
                                            <button type="submit">Dismiss</button>
                                        </form>
                                    </div>
                                    
                                    <%
                                }
                                else if (alert.getMessageCode() == 4) {
                                    %>
                                    <div class="alert">
                                        <div class="alert-container">
                                            <h2 class="alert-row1">Alert</h2>
                                            <p class="alert-row2">Alert ID: <%= alert.getAlertID() %></p>
                                            <p class="alert-row3">You have lost a bid on <%= item.getName() %>!</p>
                                        </div>
                                        <div class="flex-grow"></div>
                                        <form action="deletealert" method="GET">
                                            <input type="hidden" name="alertid" value="<%= alert.getAlertID() %>">
                                            <button type="submit">Dismiss</button>
                                        </form>
                                    </div>
                                    
                                    <%
                                }
                                else if (alert.getMessageCode() == 5) {
                                    %>
                                    <div class="alert">
                                        <div class="alert-container">
                                            <h2 class="alert-row1">Alert</h2>
                                            <p class="alert-row2">Alert ID: <%= alert.getAlertID() %></p>
                                            <p class="alert-row3"><%= item.getName() %> is available!</p>
                                        </div>
                                        <div class="flex-grow"></div>
                                        <form action="deletealert" method="GET">
                                            <input type="hidden" name="alertid" value="<%= alert.getAlertID() %>">
                                            <button type="submit">Dismiss</button>
                                        </form>
                                    </div>
                                    
                                    <%
                                }
                            }
                        }
                    }
                }
            }
        %>
        </div>
    </main>
</div>
</body>
</html>