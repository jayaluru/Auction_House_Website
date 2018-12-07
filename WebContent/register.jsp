<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="javax.servlet.http.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script type="text/javascript" src="script.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Register</title>
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
	<h4 align="left"> ${message} </h4>
	<form name="regform" action="RegisterController" method="post" onsubmit="return regValidate()">
		Username: <input type="text" name="username"> 
		<br>
		<div id="username_error"></div>
		<br>
		Name: <input type="text" name="name">
		<br>
		Description: <input type="text" name="description">
		<br>
		Password: <input type="password" name="password" id="password"> 
		<br>
		Re-Type Password: <input type="password" name="retry-password" id="retry-password"> 
		<br>
		Address: <input type="text" name="address"> 
		<br>
		Credit Card Number: <input type="text" name="number"> 
		<br>	
		ExpDate: <input type="date" name="expdate" value='<%=new Date(System.currentTimeMillis())%>'> 
		<br>	
		CVV: <input type="text" name="cvv"> 
		<br>
		<div id="password_error"></div>
		<br>
		<input type="submit" name="submit" value="register" >
		<input type="reset" name="reset">		
	</form>
	
</body>
</html>