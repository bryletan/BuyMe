<!DOCTYPE html>
<html>
  <head>
    <title>Buyme, Buy Anything!</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
      }

      .container {
        width: 300px;
        margin: 0 auto;
        padding: 20px;
        background-color: #fff;
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      }

      input[type="text"],
      input[type="password"] {
        width: 100%;
        padding: 10px;
        margin: 5px 0;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
      }

      input[type="submit"] {
        width: 100%;
        background-color:#007BFF;
        color: white;
        padding: 14px 20px;
        margin: 8px 0;
        border: none;
        border-radius: 4px;
        cursor: pointer;
      }

      input[type="submit"]:hover {
        background-color: #0056b3;
      }
    </style>
  </head>
  <body>
    <h2 style="text-align: center">Welcome to Buyme!</h2>
    <div class="container">
      <form action="login" method="POST">
        <div style="text-align: center">
          <label for="username">Username:</label><br />
          <input type="text" name="username" /><br />
          <label for="password">Password:</label><br />
          <input type="password" name="password" /><br />
          <input type="submit" value="Submit" />
        </div>
      </form>
      <hr />
      <form action="register" method="POST">
        <div style="text-align: center">
          <h3>Create an Account</h3>
          <label for="signup_username">Username:</label><br />
          <input type="text" name="signup_username" /><br />
          <label for="signup_password">Password:</label><br />
          <input type="password" name="signup_password" /><br />
          <input type="submit" value="Register" />
        </div>
      </form>
    </div>
    <% if (request.getAttribute("message") != null) { %>
      <script type="text/javascript">
          alert('<%= request.getAttribute("message") %>');
      </script>
    <% } else {}%>
  </body>
</html>