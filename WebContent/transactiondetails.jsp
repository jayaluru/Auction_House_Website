<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>    
<%@ page import="domain.transaction.Transaction" %>
<%@ page import="domain.product.Product" %>
<%@page import="db.services.TransactionPersistenceService"%>
<%@page import="db.services.impl.TransactionPersistenceServiceImpl"%>
<%@ page import="javax.servlet.http.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Transaction Details</title>
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
	<%
	Integer trxnId = (Integer) request.getAttribute("trxnId");
	TransactionPersistenceService trxnService = TransactionPersistenceServiceImpl.getInstance();
	Transaction trxn = trxnService.retrieve(trxnId);
	List<Product> prods = trxn.getProducts();
	%>
	<h3>Transaction Details</h3>
	<h4>Date: <%=trxn.getDate().toString() %></h4>
	<h5>Total Price: <%=trxn.getPrice() %></h5>
	<h5>Total Size: <%=prods.size() %></h5>
	<h5>Products: </h5>
	<table border="1" style="margin-top: 20px; margin-right: 20px; margin-left: 29px; border-top-width: 2px;">
     	<tr>
			<th>Image</th>
       		<th>Name</th>
       		<th>Description</th>
       		<th>Price</th>
       		<th>Action</th>   
   		</tr>
     
     	<%for(Product prod : prods) {%>
			
			<tr>
			<td><img src="data:image/jpeg;base64, <%= prod.getEncodedImage() %> " height="100" width="100" alt="bye"/></td>
			<td><%= prod.getName() %></td>
			<td><%= prod.getDescription() %></td>
			<td><%= prod.getPrice() %></td>
			<td>
				<form name="detailsform" action="DetailsController" method="post">
					<input type="hidden" name="prodId" value="<%= prod.getProdId().toString() %>">
					<input type="hidden" name="catId" value="<%= prod.getCategory().getCatId().toString() %>">
					<input class="demo" type="submit" name="ViewDetails" value = "View Details" style="left: 460px;">
				</form>			
				<form name="profileform" action="ProfileController" method="post">
						<input type="hidden" name="prodId" value="<%= prod.getProdId().toString() %>">
						<input class="demo" type="submit" name="UserProfile" value = "View User" style="left: 460px;">
				</form>
			</td>
			</tr>
			
		<%}%>
   	</table>
   	<br>
	<br>
</body>
</html>