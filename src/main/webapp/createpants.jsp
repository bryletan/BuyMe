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
            <div class="auction-form-container">
                <form action="createauction" method="GET">
                    <input type="hidden" name="userid" value="<%= session.getAttribute("userid") %>">
                    <input type="hidden" name="typeid" value="<%= 2 %>">

                    <h1>Please enter item details.</h1>

                    <div class="input-container">
                        <label for="name">Name of item:</label>
                        <input type="text" name="name" required/>

                        <label for="price">Price of item:</label>
                        <input type="number" name="price" step="0.01" min="0.01" required/>

                        <label for="color">Color of item:</label>
                        <select name="color" required>
                            <option value="Black">Black</option>
                            <option value="White">White</option>
                            <option value="Gray">Gray</option>
                            <option value="Brown">Brown</option>
                            <option value="Red">Red</option>
                            <option value="Blue">Blue</option>
                            <option value="Yellow">Yellow</option>
                            <option value="Green">Green</option>
                            <option value="Orange">Orange</option>
                            <option value="Purple">Purple</option>
                        </select>
                    
                        <label for="size">Waist size:</label>
                        <input type="number" name="size" required/>

                        <label for="cond">Condition of item:</label>
                        <select name="cond" required>
                            <option value="New">New</option>
                            <option value="Used">Used</option>
                        </select>

                        <label for="brand">Brand of item:</label>
                        <input type="text" name="brand" required/>

                        <label for="closingtime">Auction expiration date:</label>
                        <input type="datetime-local" name="closingtime" required/>

                        <label for="minprice">Minimum price (hidden):</label>
                        <input type="number" name="minprice" step="0.01" min="0.01" required/>

                        <div class="form-actions">
                            <button type="submit" class="submit-button" value="Submit">Submit</button>
                            <button type="button" class="button-back-button" onclick="window.location='home.jsp';">Back</button>
                        </div>
                    </div>   
                </form>
            </div>
        </main>
    </div>
</body>
