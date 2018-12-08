<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.*" %>
<%@ page import="domain.product.*" %>
<%@ page import="domain.product.Product" %>
<%@page import="db.services.*"%>
<%@page import="db.services.impl.*"%>
<%@page import="db.dao.impl.*"%>
<%@ page import="javax.servlet.http.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search Results</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.12/js/dataTables.bootstrap.min.js"></script>

<link href="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.12/css/dataTables.bootstrap.min.css" rel="stylesheet"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"/>
<link rel="stylesheet" href="index.css"/>


    <script type="text/javascript">
    $(document).ready(function () {
        $("#ProductTable").dataTable({
            "sPaginationType": "full_numbers",
            "bJQueryUI": true
        });
    });
    </script>

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
	String name = (String) sess.getAttribute("name");
	List<Product> products = (List<Product>) request.getAttribute("searchResults");
	String searchCriteria = (String) request.getAttribute("searchCriteria");
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
 	
	<h4>Search Results:</h4>	
   <% 
	if (products.size() > 0){
	
   	%>  
	<table id="ProductTable" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%">
		<tr>
			<th>Image</th>
       		<th>Name</th>
       		<th>Description</th>
       		<th>Price</th>
       		<th>Action</th>   
   		</tr>
   		
     	<%for(Product prod : products) {%>
			<tr>
			<td><img src="data:image/jpeg;base64, <%= prod.getEncodedImage() %> " height="100" width="100" alt="bye"/></td>
			<td><%= prod.getName() %></td>
			<td><%= prod.getDescription() %></td>
			<td><%= prod.getPrice() %></td>
			<td>
				<form name="profileform" action="ProfileController" method="post">
						<input type="hidden" name="prodId" value="<%= prod.getProdId().toString() %>">
						<input class="demo btn btn-primary" type="submit" name="UserProfile" value = "View User" style="left: 460px;">
				</form>
			</td>
			</tr>
			<%}%>
	</table>
   	<br>
	<% } else {%>
		<p> No results found. </p>
	<%}%>
</body>
</html> 