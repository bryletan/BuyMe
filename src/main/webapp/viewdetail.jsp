<!-- 1.End-users (buyers and sellers) -->
<%@ page import="com.model.Item" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>View Details</title>
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
      <h1 style="text-align: center;">Item Details</h1>
      <div class="item-grid">
        <%
        Item item = (Item)session.getAttribute("item");
        if(item.getTypeID() == 1) {
            %>
            <div class="item">
              <h2 class="item-row2" style="text-align: center;"><%=item.getName() %></h2>
              <p class="item-row2" style="text-align: center;">Brand: <%=item.getBrand()%></p>
              <p class="item-row2" style="text-align: center;">Color: <%=item.getColor()%></p>
              <p class="item-row2" style="text-align: center;">Size: <%=item.getShirtSize()%></p>
              <p class="item-row2" style="text-align: center;">Condition: <%=item.getCond()%></p>
              <p class="item-row4" style="text-align: center;">Current Price: $<%= item.getPrice() %></p>
            </div>
            <%
        }
        else if(item.getTypeID() == 2) {
            %>
            <div class="item">
              <h2 class="item-row2" style="text-align: center;"><%=item.getName() %></h2>
              <p class="item-row2" style="text-align: center;">Brand: <%=item.getBrand()%></p>
              <p class="item-row2" style="text-align: center;">Color: <%=item.getColor()%></p>
              <p class="item-row2" style="text-align: center;">Size: <%=item.getPantSize()%></p>
              <p class="item-row2" style="text-align: center;">Condition: <%=item.getCond()%></p>
              <p class="item-row4" style="text-align: center;">Current Price: $<%= item.getPrice() %></p>
            </div>
            <%
        }
        else if(item.getTypeID() == 3) {
            %>
            <div class="item">
              <h2 class="item-row2" style="text-align: center;"><%=item.getName() %></h2>
              <p class="item-row2" style="text-align: center;">Brand: <%=item.getBrand()%></p>
              <p class="item-row2" style="text-align: center;">Color: <%=item.getColor()%></p>
              <p class="item-row2" style="text-align: center;">Size: <%=item.getShoeSize()%></p>
              <p class="item-row2" style="text-align: center;">Condition: <%=item.getCond()%></p>
              <p class="item-row4" style="text-align: center;">Current Price: $<%= item.getPrice() %></p>
            <%
        }
        else {}
        %>
      </div>
      <div class="center-refresh" style="margin-top: 10px;">
        <button type="button" class="button-back-button" onclick="window.location='fetchitem';">Back</button>
      </div>
    </main>
  </div>
</body>
</html>
