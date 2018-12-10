<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>AdminHome</title>
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
	String username = (String) sess.getAttribute("username");
	if (username == null){
		response.sendRedirect("login.jsp");
		return;
	}
	%>
	<div class="menu" align = "Center">
		<a href="admin_home.jsp" name="menuhome">Home</a>
		<a href="setauctionschedule.jsp" name="menuauctionschedule">Auction Schedule</a>
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
	<h3 align="center" style="color:brown;"> Welcome to Auction Website! </h3>
</body>
</html> 