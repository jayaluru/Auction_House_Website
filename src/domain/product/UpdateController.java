package domain.product;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import db.dao.DaoException;
import db.services.*;
import db.services.impl.*;

@WebServlet("/UpdateController")
public class UpdateController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Integer catId = Integer.parseInt(request.getParameter("catId"));
		Integer prodId = Integer.parseInt(request.getParameter("prodId"));
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		double price = Double.parseDouble(request.getParameter("price"));
		
//		try {
//			
//			
//			
//		} catch (SQLException | DaoException e) {
//			e.printStackTrace();
//		}
		RequestDispatcher rs = request.getRequestDispatcher("inventory.jsp");
		rs.forward(request, response);
	}

}