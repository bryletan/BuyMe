<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Your Profile</title>
  <link rel="stylesheet" href="styles.css?v=1">
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
          <div class="profile-container">

            <h1>Hello, <%= session.getAttribute("user") %></h1>
            <h2>Edit your profile:</h2>

            <form action="editprofile" method="POST">
              <div class="input-container">
                <label for="username">Change username:</label>
                <input type="text" name="username" />
                <label for="password">Change password:</label>
                <input type="password" name="password" />

                <div class="form-actions">
                  <button type="submit" class="submit-button" value="Submit">Submit</button>
                  <button type="button" class="button-back-button" onclick="window.location='fetchitem';">Back</button>
                </div>
              </div>
            </form>

          </div>
            
            
        </main>
    </div>
</body>
</html>