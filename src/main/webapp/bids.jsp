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
    <title>View your Bids</title>
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
        <h1 style="text-align: center;">Your current bids</h1>
        <div class="form-actions">
            <form action="fetchbid" method="GET"> 
                <button>Refresh</button>
            </form>
            <div class="button-space"></div>
            <button type="button" class="viewusers-back-button" onclick="window.location='fetchitem';">Back</button>
        </div>    
        <div class="item-grid">
            <%
            List<Bid> bidList = (ArrayList<Bid>) session.getAttribute("bidList");
            List<Item> itemList = (ArrayList<Item>) session.getAttribute("itemList");
            List<Auction> auctionList = (ArrayList<Auction>) session.getAttribute("auctionList");
            int currentUserId = (int) session.getAttribute("userid");
            
            // Map to store the highest bid for each item
            Map<Integer, Bid> highestBids = new HashMap<>();
            
            // Find the highest bid for each item placed by the current user
            for (Bid bid : bidList) {
                if (bid.getUserID() == currentUserId) {
                    int itemId = bid.getItemID();
                    if (!highestBids.containsKey(itemId) || bid.getBidPrice() > highestBids.get(itemId).getBidPrice()) {
                        highestBids.put(itemId, bid);
                    }
                }
            }
            
            // Display items with their highest bids
            for (Item item : itemList) {
                Bid highestBid = highestBids.get(item.getItemID());
                if (highestBid != null) {
                %>
                <div class="item">
                    <h2 class="item-row2"><%= item.getName() %></h2>
                    <p class="item-row3"><%= item.getCond() %></p>
                    <%
                    if(item.getTypeID() == 1) {%>
                        <p class="grayed-row3"> | Shirt | <%=item.getBrand()%> | <%=item.getColor()%> </p>
                    <%
                    }
                    if(item.getTypeID() == 2) {%>
                        <p class="grayed-row3"> | Pants | <%=item.getBrand()%> | <%=item.getColor()%></p>
                    <%
                    }
                    if(item.getTypeID() == 3) {%>
                        <p class="grayed-row3"> | Shoes | <%=item.getBrand()%> | <%=item.getColor()%></p>
                    <%
                    }%>
                    
                    <h2 class="item-row4">$<%= item.getPrice() %></h2>
                    <p class="item-row2">Current Bid Price: $<%= highestBid.getBidPrice() %></p>
                    <p class="item-row2">Upper Limit: $<%= highestBid.getUpperLimit() %></p>
                    <p class="item-row2">Bid Increment: $<%= highestBid.getBidIncrement() %></p>
                </div>
                <%
                }
            }
            %>
        </div>
    </main>
</div>
</body>
</html>
