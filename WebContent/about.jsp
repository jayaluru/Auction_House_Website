<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>About</title>
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
	<h3 align="center" style="color:brown;"> About Us </h3>
	<h4 align="left"> Art is one of the oldest skills displayed by humankind. However, even in the 21st
century, there are few outlets for these artists to exhibit their work. This project focuses on
creating a website with the objective of providing a platform for these artists to showcase their
work, while also encouraging them to sell their creations. </h4>

	<h4 align="left"> We believe any individual can be an artist. Therefore, every user that registers with us
can both buy other’s artwork as well as sell their own creations. Once a user logs in, he/she can
search and scan through a wide array of paintings, sculptures and crafts that are for sale by
various individual artists. They can add any number of creations they like to their cart for
purchase at their convenience. On the other hand, the user can add any number of their own
creations for sale under any of the above categories. Additionally, we will also provide artists
with their own profile page where they can display creations and statistics, which they can utilize
to advance their careers. We also provide the user with a detailed transaction history to track how
many items they have sold and how many of them have they purchased. </h4>

<h4 align="left"> The objective of this project is to encourage talent and reduce the upfront cost involved in
setting up art exhibitions. We believe that any individual can be an artist, but we want to make
sure that every individual has that opportunity. This project will provide a platform for artists to
do just that: both show off their work and make money doing what they love. </h4>
</body>
</html> 