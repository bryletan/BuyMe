<%@ page import="com.model.Bid" %>
<%@ page import="com.model.Item" %>
<%@ page import="com.model.Auction" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Your Bids</title>
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
        <div class="viewusers-form-container">
            <h1 style="text-align: center;">User History</h1>
            <div class="viewusers-input-container">
                <h3 style="text-align: center">View a user's listed auctions:</h3>
                <form action="viewlistauction" method="GET">
                    <label for="list_auction">ID of user:</label>
                    <input type="number" name="list_auction"/>  
                    <button class="in-line-search" type="submit">Search</button>
                </form>
                <h3 style="text-align: center;">View a user's participated auctions:</h3>
                <form action="viewparticipateauction" method="GET">
                    <label for="participate_auction">ID of user:</label>
                    <input type="number" name="participate_auction"/>  
                    <button class="in-line-search" type="submit">Search</button>
                </form>
                <div class="form-actions">
                    <button type="button" class="viewusers-back-button" onclick="window.location='fetchitem';">Back</button>
                </div>
            </div>
        </div>
        <div class="item-grid">
                <% 
                String list_auction = request.getParameter("list_auction");
                String participate_auction = request.getParameter("participate_auction");

                if (list_auction != null || participate_auction != null) { 
                    List<Item> itemList = (ArrayList<Item>) session.getAttribute("itemList");
                    List<Auction> auctionList = (ArrayList<Auction>) session.getAttribute("auctionList");
                    
                    if (itemList != null && auctionList != null) { 
                        for (Auction auction : auctionList) {
                            for (Item item : itemList) {
                                if (auction.getItemID() == item.getItemID()) {
                %>
                <div class="item">
                    <h2 class="item-row2"><%= item.getName() %></h2>
                    <p class="item-row3"><%=item.getCond()%></p>
                    <%
                    if(item.getTypeID() == 1) {%>
                        <p class="grayed-row3"> | Shirt | <%=item.getBrand()%> | <%=item.getColor()%> </p>
                    <%
                    }
                    if(item.getTypeID() == 2) {%>
                        <p class="grayed-row3"> | Pants | <%=item.getBrand()%> | <%=item.getColor()%> </p>
                    <%
                    }
                    if(item.getTypeID() == 3) {%>
                        <p class="grayed-row3"> | Shoes | <%=item.getBrand()%> | <%=item.getColor()%> </p>
                    <%
                    }%>
                    <h2 class="item-row4">$<%= item.getPrice() %></h2>
                </div>
                <% 
                                }
                            }
                        }
                    } else {
                        // Handle the case when either itemList or auctionList is null
                        %>
                        <p style="text-align: center;">No items or auctions available.</p>
                    <% } 
                } %>
        </div>  
    </main>
</div>
</body>
</html>
