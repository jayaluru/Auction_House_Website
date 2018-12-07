<%@ page import="java.util.*" %>    
<%@ page import="java.sql.*"%>
<%@page import="domain.product.Category"%>
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
	Integer catId = Integer.valueOf(request.getParameter("catId"));
	if(catId == Category.PAINTING)
	
	{
	%>
	<h4 align="left"> Create Painting: </h4>
	<form name="newpaintform" enctype='multipart/form-data' action="PaintingController" method="post" onsubmit="return paintValidate()">
	
		Name: <input type="text" name="name" id="name">
		<br>
		Description: <input type="text" name="description" id="description">
		<br>
		Price: <input type="number" min="0" step="0.01" name="price" id="price">
		<br>
		Canvas Type: <input type="text" name="canvasType" id="canvasType">
		<br>
		Paint Type: <input type="text" name="paintType" id="paintType">
		<br>
		Length: <input type="number" min="0" step="0.01" name="length" id="length">
		<br>
		Width: <input type="number" min="0" step="0.01" name="width" id="width" >
		<br>		
		Upload an Image: <input type="file" name="file" >
		<br>
		<input type="submit" name="submit" value="create" >
		</form>
		<% 
	}
	else if (catId == Category.SCULPTURE)
	{
		%>
		<h4 align="left"> Create Sculpture: </h4>
		<form name="newsculptform" enctype='multipart/form-data' action="SculptureController" method="post" onsubmit="return sculptValidate()">		
		Name: <input type="text" name="name" id="name">
		<br>
		Description: <input type="text" name="description" id="description">
		<br>
		Price: <input type="number" min="0" step="0.01" name="price" id="price">
		<br>
		Length: <input type="number" min="0" step="0.01" name="length" id="length">
		<br>
		Width: <input type="number" min="0" step="0.01" name="width" id="width" >
		<br>
		Height: <input type="number" min="0" step="0.01" name="height" id="height" >
		<br>
		Material: <input type="text"  name="material" id="material" >
		<br>
		Weight: <input type="number" min="0" step="0.01" name="weight" id="weight" >
		<br>
		Upload an Image: <input type="file" name="file" >
		<br>
		<input type="submit" name="submit" value="create" >
		<br>
		</form>
		
		<% 
	}
	else if(catId == Category.CRAFT)
	{
		
	
	%>
	<h4 align="left"> Create Craft: </h4>
		<form name="newcraftform" enctype='multipart/form-data' action="CraftController" method="post" onsubmit="return craftValidate()">		
		Name: <input type="text" name="name" id="name">
		<br>
		Description: <input type="text" name="description" id="description">
		<br>
		Price: <input type="number" min="0" step="0.01" name="price" id="price">
		<br>
		Length: <input type="number" min="0" step="0.01" name="length" id="length">
		<br>
		Width: <input type="number" min="0" step="0.01" name="width" id="width" >
		<br>
		Height: <input type="number" min="0" step="0.01" name="height" id="height" >
		<br>
		Usage: <input type="text"  name="usage" id="usage">
		<br>
		Upload an Image: <input type="file" name="file" >
		<br>
		<input type="submit" name="submit" value="create" >
		<br>
		
		<% 
	}
	%>
		
	</form>	
</body>
</html> 