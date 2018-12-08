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
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Edit Profile</title>
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
		<a href="product.jsp" name="menuproduct">Product</a>
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
	<h3>Your Profile:</h3>
	<br>
	<h4 align="left"> ${message} </h4>
	<br>
	<form name="regform" action="EditUserController" method="post" onsubmit="return regValidate()">
		Username: <input type="text" name="username" value="<%=profileUser.getUsername()%>"> 
		<br>
		<div id="username_error"></div>
		<br>
		Name: <input type="text" name="name" value="<%=profileUser.getName()%>">
		<br>
		<br>
		Description: <input type="text" name="description" value="<%=profileUser.getDescription()%>">
		<br>
		<br>
		Password: <input type="password" name="password" id="password" value="<%=profileUser.getPassword()%>">
		<br>
		<br>
		Address: <input type="text" name="address" value="<%=profileUser.getAddress()%>"> 
		<br>
		<br>
		Security Question: <input type="text" name="securityQuestion" value="Your childhood name?">
		<br>
		<br>
		Security Answer: <input type="text" name="securityAnswer">
		<br>
		<br>
		<input type="submit" name="submit" value="Update Profile">
	</form>
	
</body>
</html>  