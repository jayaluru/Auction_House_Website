<%@ page import="java.util.*" %>    
<%@page import="domain.user.Inventory"%>
<%@page import="db.services.InventoryPersistenceService"%>
<%@page import="db.services.impl.InventoryPersistenceServiceImpl"%>
<%@page import="db.services.impl.UserPersistenceServiceImpl"%>
<%@page import="db.services.impl.TransactionPersistenceServiceImpl"%>
<%@page import="domain.user.User"%>
<%@page import="domain.product.Product"%>
<%@page import="domain.transaction.Transaction"%>
<%@page import= "db.services.UserPersistenceService"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Profile</title>
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
	Integer profileUserId = (Integer) request.getAttribute("userId");
	if (userId == null){
		response.sendRedirect("login.jsp");
		return;
	}
	if(profileUserId == null)
	{
		profileUserId = userId;
	}
	boolean sameUser = userId == profileUserId;
	
	UserPersistenceService userService = UserPersistenceServiceImpl.getInstance();
	User profileUser = userService.retrieve(profileUserId);
	%>
	<div class="menu" align = "Center">
		<a href="home.jsp" name="menuhome">Home</a>
		<a href="category.jsp" name="menucategory">Category</a>
		<a href="cart.jsp" name="menucart">Cart</a>
		<a href="inventory.jsp" name="menuinventory">Inventory</a>
		<a href="transactions.jsp" name="menutransactions">Transactions</a>
		<a href="about.jsp" name="menuabout">About</a>
		<a href="faq.jsp" name="menufaq">FAQs</a>
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
	<% 
	if (sameUser) 
	{
		%><h3>Your Profile:</h3><%
	}
	else {
		%><h3>User Profile:</h3><%
	}
	Inventory invn = profileUser.getInventory();
	List<Product> prods = invn.getProducts();
	int totalSold = 0;
	double priceSum = 0.0;
	double soldTotal = 0.0;
	for(Product prod : prods){
		if(prod.isSold()){
			totalSold += 1;
			soldTotal += prod.getPrice();
		}
		priceSum += prod.getPrice();
	}
	double avgPrice = 0.0;
	if (prods.size() > 0){
		avgPrice = priceSum / (double) prods.size();
	}
	%>
	<br>
	<table>
		<tr><th>User Details:&nbsp&nbsp&nbsp&nbsp&nbsp </th><th></th></tr>
		<tr><td>Username:</td><td><%=profileUser.getUsername()%></td></tr>
		<tr><td>Description:</td><td><%=profileUser.getDescription()%></td></tr>
		<tr><td>Products Sold:</td><td><%=totalSold%></td></tr>
		<tr><td>Average Price:</td><td><%=avgPrice%></td></tr>
		<% 
		if (sameUser) {
			List<Transaction> trxns = TransactionPersistenceServiceImpl.getInstance().retrieveByUser(userId);
			double trxnTotal = 0.0;
			for (Transaction trxn : trxns) {
				trxnTotal += trxn.getPrice();
			}
			double balance = soldTotal - trxnTotal;
			%> 
			<tr><td>Balance:</td><td><%=balance%></td></tr>
			<tr><td>Deactivate?</td>
				<td>
					<form name="deactivateForm" action="DeactivateController" method="post">
						<input class="demo" type="submit" name="Deactivate" value = "Deactivate" style="left: 460px;">
					</form>
				</td>
			</tr>
			<%
		}
		%>
	</table>
	<hr>	
 	<h4>Inventory:</h4> 	
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
					<form name="detailsform" action="DetailsController" method="post">
						<input type="hidden" name="prodId" value="<%= prod.getProdId().toString() %>">
						<input type="hidden" name="catId" value="<%= prod.getCategory().getCatId().toString() %>">
						<input class="demo" type="submit" name="ViewDetails" value = "View Details" style="left: 460px;">
					</form>	
				</td>
			</tr>
		<%}%>
	</table>
	<br>
	<% } else {%>
		<p> This user's inventory is empty. </p>
	<%}%>
	
</body>
</html>  