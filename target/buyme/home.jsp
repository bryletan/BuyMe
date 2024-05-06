<!-- 1.End-users (buyers and sellers) -->
<%@ page import="com.model.Item" %>
<%@ page import="com.model.Auction" %>
<%@ page import="com.model.Bid" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDateTime" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>BuyMe Home</title>
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
      <div class="welcome-text">Welcome, <%= session.getAttribute("user") %>!</div>
      <div class="actions">
        <div class="flex-grow"></div>
        <form action="chooseitemtype.jsp" class="action-button">
          <button>Create Auction</button>
        </form>
        <div class="flex-grow"></div>
        <form action="search.jsp" class="action-button">
          <button>Search</button>
        </form>
        <div class="flex-grow"></div>
        <form action="sort.jsp" class="action-button">
          <button>Sort</button>
        </form>
        <div class="flex-grow"></div>
        <form action="similar.jsp" class="action-button">
          <button>View Similar Items</button>
        </form>
        <div class="flex-grow"></div>
        <form action="viewusersauction.jsp" class="action-button">
          <button type="submit">View users</button>
        </form>
        <div class="flex-grow"></div>
      </div>
      <div class="item-grid">
        <%List<Item> itemList = (ArrayList<Item>) session.getAttribute("itemList");
          List<Auction> auctionList = (ArrayList<Auction>) session.getAttribute("auctionList");
          List<Bid> bidList = (ArrayList<Bid>) session.getAttribute("bidList");
          int currentUserId = (int) session.getAttribute("userid");

          if (itemList != null) {
            for (Item item : itemList) {
              for (Auction auction: auctionList){
                if(item.getItemID() == auction.getItemID()){%>
                  <div class="item">
                    <p class="item-row1">Closing Date: <%= auction.getClosingTime() %></p>
                    <p class="item-row2">Auction Closed: <%= auction.getIsClosed() %></p>
                    <h2 class="item-row2"><%=item.getName() %></h2>
                    <p class="item-row3"><%=item.getCond()%></p>
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
                    
                    <div class="item-actions">
                      <form action="addbid.jsp" method="GET">
                        <input type="hidden" name="itemid" value="<%= item.getItemID() %>">
                        <input type="hidden" name="auctionid" value="<%= auction.getAuctionID() %>">
                        <button type="submit" style="background-color: rgb(0, 189, 0);">Place Bid</button>
                      </form>

                      <div class="button-space"></div>

                      <form action="viewdetail" method="GET">
                        <input type="hidden" name="itemid" value="<%= item.getItemID() %>">
                        <input type="hidden" name="typeid" value="<%= item.getTypeID() %>">
                        <button type="submit">View details</button>
                      </form>

                      <div class="button-space"></div>
  
                      <form action="addalert" method="GET">
                        <input type="hidden" name="itemid" value="<%= item.getItemID() %>">
                        <button type="submit">Add alert</button>
                      </form>

                      <div class="button-space"></div>
  
                      <form action="viewhistory" method="GET">
                        <input type="hidden" name="itemid" value="<%= item.getItemID() %>">
                        <button type="submit">View history</button>
                      </form>

                      <div class="flex-grow"></div>

                      <%
                    if(auction.getUserID() == currentUserId) {
                      LocalDateTime currentDateTime = LocalDateTime.now();
                      //check if auction is not closed and the closing time has not passed the current time
                      if(auction.getIsClosed() == false) {
                        if(auction.getClosingTime().isAfter(currentDateTime)) {
                          %>
                          <form action="closeauction" method="GET">
                            <input type="hidden" name="auctionid" value="<%= auction.getAuctionID() %>">
                            <button type="submit">Close auction</button>
                          </form>
                          <%
                        }
                      }
                      if(auction.getIsClosed() == true) {
                        if(auction.getClosingTime().isAfter(currentDateTime)) {
                          %>
                          <form action="openauction" method="GET">
                            <input type="hidden" name="auctionid" value="<%= auction.getAuctionID() %>">
                            <input type="hidden" name="itemid" value="<%= item.getItemID() %>">
                            <button type="submit">Open auction</button>
                          </form>
                          <%
                        }
                      }
                    }
                    %>
                    </div>
                  </div>
              <%}
              }
            }
          } else {}%>
      </div>
    </main>
  </div>
</body>
</html>
