<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>    
<%@ page import="domain.transaction.Transaction" %>
<%@ page import="domain.product.Product" %>
<%@page import="db.services.TransactionPersistenceService"%>
<%@page import="db.services.impl.TransactionPersistenceServiceImpl"%>
<%@ page import="javax.servlet.http.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Transactions</title>   
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
 	<h4>Total Spent:</h4>
	<%
	TransactionPersistenceService trxnService = TransactionPersistenceServiceImpl.getInstance();
	List<Transaction> trxns = trxnService.retrieveByUser(userId);
	double total = 0.0;
	for (Transaction trxn : trxns) {
		total += trxn.getPrice();
	}
	%>
	<%= total %>
	<h4>Transactions:</h4>
	<% 
	if (trxns.size() > 0){
	%>  
  	<table border="1" style="margin-top: 20px; margin-right: 20px; margin-left: 29px; border-top-width: 2px;">
	    <tr>
	      <th>Date</th>
	      <th>Price</th>
	      <th>Size</th>  
	      <th>Size</th>    
	    </tr>
	    
	    <%for(Transaction trxn : trxns) {%>
		<tr>
		<td><%= trxn.getDate().toString() %></td>
		<td><%= trxn.getPrice() %></td>
		<td><%= trxn.getProducts().size() %></td>
		<td>
			<form name="trxnDetailsform" action="TransactionDetailsController" method="post">
				<input type="hidden" name="trxnId" value="<%= trxn.getTrxnId().toString() %>">
				<input class="demo" type="submit" name="ViewDetails" value = "View Details" style="left: 460px;">
			</form>
		</td>
		</tr>
		<%}%>
	</table>
	<br>
	<% } else {%>
		<p> You have no transactions. </p>
	<%}%>
   
</body>
</html> 