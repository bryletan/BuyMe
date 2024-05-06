<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Place a Bid</title>
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
            <div class="bid-form-container">
                <form action="addbid" method="get">
                    <!-- Hidden input field for itemid -->
                    <input type="hidden" name="itemid" value="<%= request.getParameter("itemid") %>">
                    <input type="hidden" name="userid" value="<%= session.getAttribute("userid") %>">
                    <input type="hidden" name="auctionid" value="<%= request.getParameter("auctionid") %>">

                    <h1 class="header">Place a bid</h1>

                    <div class="input-container">
                        <label for="bidprice">Price of bid:</label>
                        <input type="number" name="bidprice" min="0" required/>

                        <label for="upperlimit">Upper limit:</label>
                        <input type="number" name="upperlimit" id="upperlimit" min="0"/>

                        <label for="bidincrement">Bid increment:</label>
                        <input type="number" name="bidincrement" id="bidincrement" min="0"/>

                        <div class="form-actions">
                            <button type="submit" class="submit-button" value="Submit">Submit</button>
                            <button type="button" class="button-back-button" onclick="window.location='fetchitem';">Back</button>
                        </div>
                    </div>
                </form>
            </div>
        </main>
