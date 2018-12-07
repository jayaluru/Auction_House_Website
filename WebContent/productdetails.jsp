<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>    
<%@ page import="domain.product.Product" %>
<%@ page import="domain.product.Painting" %>
<%@ page import="domain.product.Sculpture" %>
<%@ page import="domain.product.Craft" %>
<%@ page import="domain.product.Category" %>
<%@page import="db.services.PaintingPersistenceService"%>
<%@page import="db.services.SculpturePersistenceService"%>
<%@page import="db.services.CraftPersistenceService"%>
<%@page import="db.services.impl.PaintingPersistenceServiceImpl"%>
<%@page import="db.services.impl.SculpturePersistenceServiceImpl"%>
<%@page import="db.services.impl.CraftPersistenceServiceImpl"%>
<%@ page import="javax.servlet.http.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Product Details</title>
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
	Integer prodId = (Integer) request.getAttribute("prodId");
	
	Integer catId = (Integer) request.getAttribute("catId"); 
	Product prod = null;

	if (catId == Category.PAINTING){
		PaintingPersistenceService paintService = PaintingPersistenceServiceImpl.getInstance();
		Painting paint = paintService.retrieve(prodId);
		prod = paint; %>
		<img src="data:image/jpeg;base64, <%= paint.getEncodedImage() %> " height="100" width="100" alt="bye"/>
		<table>
			<tr><th>Painting Details:</th></tr>
			<tr><td>Name: </td><td><%=paint.getName()%></td></tr>
			<tr><td>Description: </td><td><%=paint.getDescription()%></td></tr>
			<tr><td>Price: </td><td><%=paint.getPrice()%></td></tr>
			<tr><td>Sold: </td><td><%=paint.isSold()%></td></tr>
			<tr><td>Canvas Type: </td><td><%=paint.getCanvasType()%></td></tr>
			<tr><td>Paint Type: </td><td><%=paint.getPaintType()%></td></tr>
			<tr><td>Length: </td><td><%=paint.getLength()%></td></tr>
			<tr><td>Width: </td><td><%=paint.getWidth()%></td></tr>
		</table>
	<%} else if (catId == Category.SCULPTURE) {
		SculpturePersistenceService sculptService = SculpturePersistenceServiceImpl.getInstance();
		Sculpture sculpt = sculptService.retrieve(prodId); 
		prod = sculpt; %>
		<img src="data:image/jpeg;base64, <%= sculpt.getEncodedImage() %> " height="100" width="100" alt="bye"/>
		<table>
			<tr><th>Sculpture Details:</th></tr>
			<tr><td>Name: </td><td><%=sculpt.getName()%></td></tr>
			<tr><td>Description: </td><td><%=sculpt.getDescription()%></td></tr>
			<tr><td>Price: </td><td><%=sculpt.getPrice()%></td></tr>
			<tr><td>Sold: </td><td><%=sculpt.isSold()%></td></tr>
			<tr><td>Material: </td><td><%=sculpt.getMaterial()%></td></tr>
			<tr><td>Weight: </td><td><%=sculpt.getWeight()%></td></tr>
			<tr><td>Length: </td><td><%=sculpt.getLength()%></td></tr>
			<tr><td>Width: </td><td><%=sculpt.getWidth()%></td></tr>
		</table>
		
	<% } else if (catId == Category.CRAFT) {
		CraftPersistenceService craftService = CraftPersistenceServiceImpl.getInstance();
		Craft craft = craftService.retrieve(prodId);
		prod = craft;  %>
		<img src="data:image/jpeg;base64, <%= craft.getEncodedImage() %> " height="100" width="100" alt="bye"/>
		<table>
			<tr><th>Craft Details:</th></tr>
			<tr><td>Name: </td><td><%=craft.getName()%></td></tr>
			<tr><td>Description: </td><td><%=craft.getDescription()%></td></tr>
			<tr><td>Price: </td><td><%=craft.getPrice()%></td></tr>
			<tr><td>Sold: </td><td><%=craft.isSold()%></td></tr>
			<tr><td>Usage: </td><td><%=craft.getUsage()%></td></tr>
			<tr><td>Length: </td><td><%=craft.getLength()%></td></tr>
			<tr><td>Width: </td><td><%=craft.getWidth()%></td></tr>
			<tr><td>Height: </td><td><%=craft.getHeight()%></td></tr>
		</table>
	
	<%}
	if (!prod.isSold() ){ %>
	<form name="addCartForm" action="CartController" method="post">
		<input type="hidden" name="prodId" value="<%= prodId.toString() %>">
		<input type="hidden" name="catId" value="<%= prod.getCategory().getCatId().toString() %>">
		<input class="demo" type="submit" name="AddToCart" value = "Add to Cart" style="left: 460px;">
	</form>
	<%} %>
	<form name="profileform" action="ProfileController" method="post">
		<input type="hidden" name="prodId" value="<%= prod.getProdId().toString() %>">
		<input class="demo" type="submit" name="UserProfile" value = "View User" style="left: 460px;">
	</form>
</body>
</html>