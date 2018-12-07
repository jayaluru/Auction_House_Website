<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>FAQ</title>
</head>
<style>
.content {
    max-width: 1000px;
    margin: auto;
    background: white;
    padding: 50px;
    line-height: 0.3;
}
.italic {
    font-style: italic;
}

.bold {
    font-weight: bold;
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
	<h3 align="center" style="color:brown;"> FAQs! </h3>
	<h4 align="left"> We hope you found your answer! </h4>
	<div id="qanda">
		<ul>
			<li class="bold">How to get my products listed?</li>
			<p class="italic">It is simple. Go to invetory tab, click on 'Add a product' option, enter the products details and list your product.</p>
			<li class="bold">How do I get my purchased products delivered?</li>
			<p class="italic">The purchased products will be delivered through standard shipping to the address specified in your account.</p>
			<li class="bold">Can I buy and sell products as a guest?</li>
			<p class="italic">No. Only registered users will be allowed to buy/sell here. </p>
			<li class="bold">I am having trouble in listing my products </li>
			<p class="italic">It might be because of heavy traffic to the server. We use high end efficient servers, in no time it should be up and running.</p>
			<li class="bold">The products I bought has not arrived yet!</li>
			<p class="italic">Your products might be on the way arriving. If your products were not delivered within 15 days of your order, you will receive a full refund.</p>
			<li class="bold">Can I edit/remove the products which I have listed for selling?</li>
			<p class="italic">Yes. There is an option called 'Edit/Remove' for the products you have listed in inventory page.</p>
		</ul>
	</div>
	
</body>
</html> 