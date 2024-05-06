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
  <title>Sort Items</title>
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
            <form action="sort" method="GET">
                <div class="search-form-container">
                
                    <input type="hidden" name="userid" value="<%= session.getAttribute("userid") %>">

                    <h1>Sort By</h1>
                    <div class="input-container"> 
                        <label for="sort_name">Item Name: </label>
                        <select name="sort_name">
                            <option value="">Select</option>
                            <option value="a-z">a-z</option>
                            <option value="z-a">z-a</option>
                        </select>
    
                        <label for="sort_price">Bidding Price: </label>
                        <select name="sort_price">
                            <option value="">Select</option>
                            <option value="Low_to_High">Low to High</option>
                            <option value="High_to_Low">High to Low</option>
                        </select>
    
                        <label for="sort_type">Type: </label>
                        <select name="sort_type">
                            <option value="">Select</option>
                            <option value="shirt">Shirt</option>
                            <option value="pants">Pants</option>
                            <option value="shoes">Shoes</option>
                        </select>
    
                        <div class="form-actions">
                            <button type="submit" class="submit-button" value="Submit">Sort</button>
                            <button type="button" class="button-back-button" onclick="window.location='fetchitem';">Back</button>
                        </div>
                    </div>
                </div>
                <div class="item-grid">
                        <% // Check if the form has been submitted
                            String sortName = request.getParameter("sort_name");
                            String sortPrice = request.getParameter("sort_price");
                            String sortType = request.getParameter("sort_type");
                            if (sortName != null || sortPrice != null || sortType != null) {
                                List<Item> itemList = (ArrayList<Item>) session.getAttribute("itemList");
                                List<Auction> auctionList = (ArrayList<Auction>) session.getAttribute("auctionList");
    
                                Map<Integer, Auction> auctionMap = new HashMap<>();
                                for (Auction auction : auctionList) {
                                    auctionMap.put(auction.getItemID(), auction);
                                }
                                
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
            </form>
        </main>
