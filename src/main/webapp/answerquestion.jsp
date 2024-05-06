<!-- 1.Customer Representatives -->
<%@ page import="com.model.Question" %>
<%@ page import="com.model.Item" %>
<%@ page import="com.model.Auction" %>
<%@ page import="com.model.Bid" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Answer Question</title>
  <link rel="stylesheet" href="styles.css?v=3">
</head>
<body>
    
    <h1>Answer Question</h1>
    <div class="container">
      <main class="main-content">
            <h2>Question # <%= session.getAttribute("questionid") %></h2>
            <h2>Question Text: <%= session.getAttribute("questionmessage") %></h2>
            <h2>Edit Asker's profile:</h2>

            <form action="editprofile" method="post">
              <div>
                <label for="username">Change username:</label>
                <input type="text" name="username" />
                <label for="password">Change password:</label>
                <input type="password" name="password" />

                <div class="form-actions">
                  <button type="submit" class="submit-button" value="Submit">Submit</button>
                </div>
              </div>
            </form>
            <form action="deleteprofile" method="post">
                <input type="hidden" name="askerid" value="<%= session.getAttribute("askerid") %>">
                <button>Delete User</button>
            </form>
            <form action="resolvequestion" method="get">
                <div>
                  <label for="reply">Reply:</label>
                  <input type="text" name="reply" />
  
                  <div class="form-actions">
                    <button type="submit" class="submit-button" value="Submit">Resolve Question</button>
                  </div>
                </div>
              </form>
            <form action="fetchuserbids" method="get">
                <input type="hidden" name="questionid" value="<%= session.getAttribute("questionid") %>">
                <button>Refresh</button>
            </form>
            <form action="fetchauction" class="action-button">
                <button>View Auctions</button>
            </form>

            <h1>Asker <%= session.getAttribute("askerid") %>'s Bids</h1>
            <%
            List<Bid> bidList = (ArrayList<Bid>) session.getAttribute("bidList");
            List<Item> itemList = (ArrayList<Item>) session.getAttribute("itemList");
            int askerid = (int) session.getAttribute("askerid");
            
            // Map to store the highest bid for each item
            Map<Integer, Bid> highestBids = new HashMap<>();
            
            // Find the highest bid for each item placed by the current user
            for (Bid bid : bidList) {
                if (bid.getUserID() == askerid) {
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
                    <h2><%= item.getName() %></h2>
                    <%
                    if(item.getTypeID() == 1) {%>
                    <p>Type: Shirt</p>
                    <%
                    }
                    if(item.getTypeID() == 2) {%>
                    <p>Type: Pants</p>
                    <%
                    }
                    if(item.getTypeID() == 3) {%>
                    <p>Type: Shoes</p>
                    <%
                    }%>
                    <p>Brand: <%= item.getBrand() %></p>
                    <p>Color: <%= item.getColor() %></p>
                    <p>Condition: <%= item.getCond() %></p>
                    <p>Current Item Price: $<%= item.getPrice() %></p>
                    <p>Current Bid Price: $<%= highestBid.getBidPrice() %></p>
                    <p>Upper Limit: $<%= highestBid.getUpperLimit() %></p>
                    <p>Bid Increment: $<%= highestBid.getBidIncrement() %></p>
                    <form action="deletebid" method="get">
                        <input type="hidden" name="itemid" value="<%= item.getItemID() %>">
                        <button type="submit">Delete Bid</button>
                    </form>
                </div>
                <%
                }
            }
            %>
      </main>
    </div>
    <% if (request.getAttribute("changeuserinfomsg") != null) { %>
        <script type="text/javascript">
            alert('<%= request.getAttribute("changeuserinfomsg") %>');
        </script>
      <% } else {}%>
</body>
</html>