<%@ page import="java.util.*" %>    
<%@ page import="java.sql.*"%>
<%@ page import="java.sql.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script type="text/javascript" src="script.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>New Product</title>
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
		<a href="product.jsp" name="menucategory">Product</a>
		<a href="inventory.jsp" name="menuinventory">Inventory</a>
		<a href="searchItemsDateRange.jsp" name="menuinventory">Advanced Search</a>
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
	
	<h4 align="left"> Create Painting: </h4>
	<form name="newproductform" enctype='multipart/form-data' action="ProductController" method="post" onsubmit="return productValidate()">
	
		Name: <input type="text" name="name" id="name">
		<br>
		Description: <input type="text" name="description" id="description">
		<br>
		Bid Price: <input type="number" min="0" step="0.01" name="price" id="price">
		<br>		
		Upload an Image: <input type="file" name="file" >
		<br>
		Bidding Date: <input type="date" name="biddate" id="biddate" value='<%=new Date(System.currentTimeMillis())%>'>
		<br>
		Bid Start Duration: <input type="text" name="startbid" id="startbid">
		<br>
		Bid End Duration: <input type="text" name="endbid" id="endbid">
		<br>
		<input type="submit" name="submit" value="create" >
	</form>
</body>
</html> 