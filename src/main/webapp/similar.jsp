<%@ page import="com.model.Item" %>
<%@ page import="com.model.Auction" %>
<%@ page import="com.model.Bid" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Similar Items</title>
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
            <div class="search-form-container">
                <form action="similar" method="GET">
                    <input type="hidden" name="userid" value="<%= session.getAttribute("userid") %>">
                    <h1>View Similar Items</h1>
                    <div class="input-container">
                        <label for="name">Type in Item Name to View Other Similar Items:</label>
                        <input type="text" name="name" value=""/>
    
                        <div class="form-actions">
                            <button type="submit" class="submit-button" value="Submit">View Similar Items</button>
                            <button type="button" class="button-back-button" onclick="window.location='fetchitem';">Back</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="item-grid">
                    <% 
                        // Check if the form has been submitted
                        String itemName = request.getParameter("name");
                        if (itemName != null) {
                            List<Item> itemList = (ArrayList<Item>) session.getAttribute("itemList");
                            List<Auction> auctionList = (ArrayList<Auction>) session.getAttribute("auctionList");
                            List<Bid> bidList = (ArrayList<Bid>) session.getAttribute("bidList");
                            
                            if (itemList != null) {
                                for (Item item : itemList) {
                                %>
                                <div class="item">
                                    <h2 class="item-row2"><%=item.getName() %></h2>
                                    <p class="item-row3">Condition: <%=item.getCond()%></p>
                                    <%
                                    if(item.getTypeID() == 1) {%>
                                        <p class="grayed-row3"> | Shirt | <%=item.getBrand()%> | <%=item.getColor()%></p>
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
