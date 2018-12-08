package domain.product;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.dao.DaoException;
import db.services.*;
import db.services.impl.*;

@WebServlet("/DeleteController")
public class DeleteController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private ProductPersistenceService productService = ProductPersistenceServiceImpl.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer prodId = Integer.parseInt(request.getParameter("prodId"));
		HttpSession sess = request.getSession(true);
		Integer invnId = (Integer) sess.getAttribute("invnId");
		
		try {
			Product product = productService.retrieve(prodId);
			
			productService.delete(product, invnId);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		
		
		RequestDispatcher rs = request.getRequestDispatcher("inventory.jsp");
		rs.forward(request, response);
	}

}