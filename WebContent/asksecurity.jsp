<%@page import="db.DbManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script type="text/javascript" src="script.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Ask Security Question</title>
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
	<h4 align="left"> Forgot Password </h4>
	
	<form name="loginform" action="SecurityQuestionCheckController" method="post">
	Security Question: ${securityQuestion}
	<br>
	<br>
	Security Answer: <input type="text" name="securityAnswer">
	<br>
	<br>
	<input type="submit" name="submit" value="Continue">
	</form>
	<h4 align="left"> ${message1} </h4>
</body>
</html>