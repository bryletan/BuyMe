<%
if (session != null) {
    session.invalidate(); // Invalidate the session
}
response.sendRedirect("login.jsp");
%>