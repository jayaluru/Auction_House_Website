<%@ page import="java.util.*" %>    
<%@page import="domain.user.Inventory"%>
<%@page import="db.services.InventoryPersistenceService"%>
<%@page import="db.services.impl.InventoryPersistenceServiceImpl"%>
<%@page import="domain.product.Product"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Inventory</title>
</head>
<style>
.content {
    max-width: 1000px;
    margin: auto;
    background: white;
    padding: 50px;
    line-height: 0.3;
}
</style>
<body>
	<% 
	HttpSession sess = request.getSession(true);
	Integer userId = (Integer) sess.getAttribute("userId");
	if (userId == null){
		response.sendRedirect("login.jsp");
		return;
	}
	Integer invnId = (Integer) sess.getAttribute("invnId");
	Integer cartId = (Integer) sess.getAttribute("cartId");
	String name = (String) sess.getAttribute("name");
	%>
	<div class="menu" align = "Center">
		<a href="home.jsp" name="menuhome">Home</a>
		<a href="product.jsp" name="menuproduct">Product</a>
		<a href="inventory.jsp" name="menuinventory">Inventory</a>
		<a href="transactions.jsp" name="menutransactions">Transactions</a>
		<a href="profile.jsp" name="menuprofile">Profile</a>
		<a href="logout.jsp" name="menulogout">Logout</a>
		<hr>
		<div class="searchbar" align ="Center"> 
			<form method="post" action="SearchController">
				<input type="text" name="searchCriteria" placeholder="Search..">
				<input type="submit" name="searchSubmit" value="Go">
			</form>
		</div>
	</div>
 	<hr>
 	
 	<form>
		<a name = "Product" href="newproduct.jsp">Add a product</a>
	</form>
 	
 	<h4>Total Earnings:</h4>
 	<%
	InventoryPersistenceService invnService = InventoryPersistenceServiceImpl.getInstance();
	Inventory invn = invnService.retrieve(userId);
	
	List<Product> prods = invn.getProducts();
	double total = 0.0;
	double paintTotal = 0.0;
	double sculptTotal = 0.0;
	double craftTotal = 0.0;
 	for (Product prod : prods){
 		if (prod.isSold()){
 			double price = prod.getPrice();
 			total += price;
 		}
 	}
 	%>
 	<table border="1" style="margin-top: 20px; margin-right: 20px; margin-left: 29px; border-top-width: 2px;">
		<col width="130">
	  	<col width="80">
		<tr>
			<td>Total Profit : </td>
		 	<td><%= total %></td>
		</tr>
	</table>
 	<h4>Products in Inventory:</h4>
	<%
	
	if (prods.size() > 0){
	
	%>
	<table border="1" style="margin-top: 20px; margin-right: 20px; margin-left: 29px; border-top-width: 2px;">
		<tr>
			<th>Image</th>
			<th>Name</th>
			<th>Description</th>
			<th>Price</th>
			<th>Sold</th>
			<th>Action</th>   
		</tr>
	     
		<%for(Product prod : prods) {%>
			<tr>			
				<td><img src="data:image/jpeg;base64, <%= prod.getEncodedImage() %> " height="100" width="100" alt="bye"/></td>
				<td><%= prod.getName() %></td>
				<td><%= prod.getDescription() %></td>
				<td><%= prod.getPrice() %></td>
				<td><%= prod.isSold() %></td>
				
				<td>
					<form name="deleteform" action="DeleteController" method="post">
						<input type="hidden" name="prodId" value="<%= prod.getProdId().toString() %>">
						<input class="demo" type="submit" <%=prod.isSold() ? "disabled=\"\"" : "" %> name="Delete Product" value = "Delete Product" style="left: 460px;">
					</form>
				</td>
			</tr>
		<%}%>
	</table>
	<br>
	<% } else {%>
		<p> Your Inventory is empty. </p>
	<%}%>
</body>
</html> 