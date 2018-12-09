package domain.product;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import db.dao.DaoException;
import db.services.ProductPersistenceService;
import db.services.impl.ProductPersistenceServiceImpl;

@WebServlet("/AdvancedSearchController")
public class AdvancedSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductPersistenceService prodService = ProductPersistenceServiceImpl.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String searchCriteria = request.getParameter("searchCriteria");
		Date startDate= null;
		try {
			startDate = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("startdate")).getTime());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date endDate=null;
		try {
			endDate = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("enddate")).getTime());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		List<Product> products;
		List<Product> results = new ArrayList<>();

		SearchStrategy forSaleStrategy = new ForSaleSearchStrategy();
		SearchStrategy matchStrategy = new NameOrDescriptionSearchStrategy(searchCriteria,startDate,endDate);
		
		try {
			products = prodService.retrieveAll();
			results = forSaleStrategy.filter2(products);
			results = matchStrategy.filter2(results);			
		} catch (SQLException | DaoException e) {
			e.printStackTrace();
		}
		request.setAttribute("searchResults", results);
		request.setAttribute("searchCriteria", searchCriteria);
		request.getRequestDispatcher("searchresults.jsp").forward(request, response);
	}
}