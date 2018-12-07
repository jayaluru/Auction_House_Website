package domain.product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import db.dao.DaoException;
import db.services.ProductPersistenceService;
import db.services.impl.ProductPersistenceServiceImpl;

@WebServlet("/SearchController")
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductPersistenceService prodService = ProductPersistenceServiceImpl.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String searchCriteria = request.getParameter("searchCriteria");
		List<Product> products;
		List<Product> results = new ArrayList<>();

		SearchStrategy forSaleStrategy = new ForSaleSearchStrategy();
		SearchStrategy matchStrategy = new NameOrDescriptionSearchStrategy(searchCriteria);
		
		try {
			products = prodService.retrieveAll();
			results = forSaleStrategy.filter(products);
			results = matchStrategy.filter(results);			
		} catch (SQLException | DaoException e) {
			e.printStackTrace();
		}
		request.setAttribute("searchResults", results);
		request.setAttribute("searchCriteria", searchCriteria);
		request.getRequestDispatcher("searchresults.jsp").forward(request, response);
	}
}