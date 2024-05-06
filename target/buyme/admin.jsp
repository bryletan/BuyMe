<!-- admin page -->
<%
if (!session.getAttribute("user").equals("admin")) {
%>
  <div style="text-align: center; font-family: Arial, sans-serif; padding: 20px;">
    <h2>You are not logged in</h2>
    <p style="margin-bottom: 20px;">Please <a href="login.jsp" style="color: #007bff; text-decoration: none;">Login</a></p>
  </div>
<%
} else {
%>
  <div style="text-align: center; font-family: Arial, sans-serif; padding: 20px;">
    <h2>Welcome </h2>
    <p style="margin-bottom: 20px;">You are currently logged in.</p>
    <a href="logout.jsp" style="background-color: #dc3545; color: #fff; padding: 10px 20px; text-decoration: none; border-radius: 5px;">Log out</a>
  </div>

  <div style="text-align: center; font-family: Arial, sans-serif; padding: 20px;">
    <h3>Create Account for Customer Representative</h3>
    <form action="admin" method="POST">
      Username: <input type="text" name="username" required /><br />
      Password: <input type="password" name="password" required /><br />
      <input type="submit" value="Create Account" />
    </form>
  </div>
  <div style="text-align: center; font-family: Arial, sans-serif; padding: 20px;">
    <h3>Generate Sales Report By:</h3>
    <form action="generatereport" method="get" style="display: inline;">
      <input type="hidden" name="reporttype" value=1>
      <button type="submit">Total Earnings</button>
    </form>
    <form action="generatereport" method="get" style="display: inline;">
      <input type="hidden" name="reporttype" value=2>
      <button type="submit">Earnings Per Item</button>
    </form>
    <form action="generatereport" method="get" style="display: inline;">
      <input type="hidden" name="reporttype" value=3>
      <button type="submit">Earnings Per Item Type</button>
    </form>
    <form action="generatereport" method="get" style="display: inline;">
      <input type="hidden" name="reporttype" value=4>
      <button type="submit">Earnings Per End User</button>
    </form>
    <form action="generatereport" method="get" style="display: inline;">
      <input type="hidden" name="reporttype" value=5>
      <button type="submit">Best-Selling Items</button>
    </form>
    <form action="generatereport" method="get" style="display: inline;">
      <input type="hidden" name="reporttype" value=6>
      <button type="submit">Best Buyers</button>
    </form>
    <div style="flex-direction: column;">
      <p><%=session.getAttribute("report")%></p>
    </div>
  </div>
<%
}
%>