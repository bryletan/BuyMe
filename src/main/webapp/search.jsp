<%@ page import="com.model.Item" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Search</title>
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
            <form action="search" method="GET">
                <div class="search-form-container">
                    <input type="hidden" name="userid" value="<%= session.getAttribute("userid") %>">

                    <h1>Search By</h1>
                    <div class="input-container">
                        <label for="name">Name:</label>
                        <input type="text" name="name" value=""/>

                        <label for="price">Price range:</label>
                        <input type="number" name="lower_price" step="0.01" value="" placeholder="Enter lower price"/>
                        <input type="number" name="upper_price" step="0.01" value="" placeholder="Enter upper price"/>

                        <label for="color">Color:</label>
                        <select type="text" name="color" value="">
                            <option value="">Select color</option>
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

                        <label for="cond">Condition:</label>
                        <select type="text" name="cond" value="">
                            <option value="">Select condition</option>
                            <option value="New">New</option>
                            <option value="Used">Used</option>
                        </select>

                        <label for="brand">Brand:</label>
                        <input type="text" name="brand" value=""/>

                        <div class="form-actions">
                            <button type="submit" class="submit-button" value="Submit">Search</button>
                            <button type="button" class="button-back-button" onclick="window.location='fetchitem';">Back</button>
                        </div>
                    </div>
                </div>
                <div class="item-grid">
                    <%
                    // Check if the form has been submitted
                    String name = request.getParameter("name");
                    String lowerPrice = request.getParameter("lower_price");
                    String upperPrice = request.getParameter("upper_price");
                    String color = request.getParameter("color");
                    String cond = request.getParameter("cond");
                    String brand = request.getParameter("brand");
    
                    if (name != null || lowerPrice != null || upperPrice != null || color != null || cond != null || brand != null) {
                        List<Item> itemList = (ArrayList<Item>) session.getAttribute("searchedItemList");
                        if (itemList != null) {
                            for (Item item : itemList) {
                                %>
                                <div class="item">
                                    <h2 class="item-row2"><%=item.getName() %></h2>
                                    <p class="item-row3">Condition: <%=item.getCond()%></p>
                                    <%
                                    if(item.getTypeID() == 1) {%>
                                        <p class="grayed-row3"> | Shirt | <%=item.getBrand()%> | <%=item.getColor()%></p>
                                    <%
                                    }
                                    if(item.getTypeID() == 2) {%>
                                        <p class="grayed-row3"> | Pants | <%=item.getBrand()%> | <%=item.getColor()%></p>
                                    <%
                                    }
                                    if(item.getTypeID() == 3) {%>
                                        <p class="grayed-row3"> | Shoes | <%=item.getBrand()%> | <%=item.getColor()%></p>
                                    <%
                                    }%>                                    
                                    <h2 class="item-row4">$<%= item.getPrice() %></h2>
                                </div>
                                <%
                            }
                        }
                    }
                    %>
                </div>
            </form>           
        </main>
    </div>
</body>
