<!-- 1.End-users (buyers and sellers) -->
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
  <title>Front Page</title>
  <link rel="stylesheet" href="styles.css?v=3">
</head>
<body>    
    <main class="main-content">
      <div class="welcome-text">View Auctions</div>
      <form action="fetchauction" method="get">
        <div class="form-actions">
          <button>Refresh</button>
          <div class="button-space"></div>
          <button type="button" class="button-back-button" onclick="window.location='fetchquestion';">Back</button>
        </div>
        
      </form>
      <script type="text/javascript">
        setTimeout(function(){
          location=''
        }, 60*100)
      </script>

      <div class="item-grid">
        <%List<Item> itemList = (ArrayList<Item>) session.getAttribute("itemList");
          List<Auction> auctionList = (ArrayList<Auction>) session.getAttribute("auctionList");
          List<Bid> bidList = (ArrayList<Bid>) session.getAttribute("bidList");

          if (itemList != null) {
            for (Item item : itemList) {
              for (Auction auction: auctionList){
                if(item.getItemID() == auction.getItemID()){%>
                  <div class="alert">
                    <div class="alert-container">
                      <p class="item-row1">Closing Date: <%= auction.getClosingTime() %></p>
                      <h2 class="item-row2"><%=item.getName() %></h2>
                      <p class="item-row3"><%=item.getCond()%> | <%=item.getBrand()%> | <%=item.getColor()%></p>
                      <h2 class="item-row4">$<%= item.getPrice() %></h2>
                    </div>
                    <div class="flex-grow"></div>
                    <form action="deleteauction" method="get">
                      <input type="hidden" name="itemid" value="<%= item.getItemID() %>">
                      <button type="submit">Remove Auction</button>
                    </form>
                  </div>
              <%}
              }
            }
          } else {}%>
      </div>
    </main>

</body>
</html>