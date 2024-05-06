<%@ page import="com.model.Bid" %>
<%@ page import="com.model.Item" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bid History</title>
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
        <div class="history-form-container">
            <h1>Bid History</h1> 
            <div class="form-actions">
                <button type="button" class="viewusers-back-button" onclick="window.location='fetchitem';">Back</button>
            </div>
        </div>
        <div class="item-grid">
            <%
            List<Bid> bidList = (ArrayList<Bid>) session.getAttribute("bidList");
            List<Item> itemList = (ArrayList<Item>) session.getAttribute("itemList");

            int currentUserId = (int) session.getAttribute("userid");

            for (Bid bid : bidList) {
                for (Item item : itemList) {
                    if (bid.getItemID() == item.getItemID()) {
                        %>
                        <div class="item">
                            <h2 class="item-row2"><%=item.getName() %></h2>
                            <p class="item-row3"><%=item.getCond()%></p>
                            <p class="grayed-row3"> | <%=item.getBrand()%> | <%=item.getColor()%></p>
                            <h2 class="item-row4">$<%= item.getPrice() %></h2>
                            <p class="item-row4">Current Bid Price: $<%= bid.getBidPrice() %></p>
                            <p class="item-row4">Upper Limit: $<%= bid.getUpperLimit() %></p>
                            <p class="item-row4">Bid Increment: $<%= bid.getBidIncrement() %></p>
                        </div>
                        <%
                    }
                }
            }
            %>
        </div>
    </main>
</div>
</body>
</html>
