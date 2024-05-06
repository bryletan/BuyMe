<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Create Auction</title>
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
            <div class="choose-form-container">
                <h2>What article of clothing would you like to auction?</h2>
                <div class="button-group">
                    <form action="createshirt.jsp" class="action-button">
                        <button>Create Shirt</button>
                    </form>
                    <div class="button-space"></div>
                    <form action="createpants.jsp" class="action-button">
                        <button>Create Pants</button>
                    </form>
                    <div class="button-space"></div>
                    <form action="createshoes.jsp" class="action-button">
                        <button>Create Shoes</button>
                    </form>
                </div>   
                <button type="button" class="button-back-button" onclick="window.location='fetchitem';">Back</button>
            </div>
        </main>
    </div>
</body>
