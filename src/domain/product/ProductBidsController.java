package domain.product;

import java.util.List;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.dao.DaoException;
import db.services.AdminService;
import db.services.ProductPersistenceService;
import db.services.impl.AdminServiceImpl;
import db.services.impl.ProductPersistenceServiceImpl;


@WebServlet("/ProductBidsController")
public class ProductBidsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//private AdminService adminService = AdminServiceImpl.getInstance();
	private ProductPersistenceService productServieImpl = ProductPersistenceServiceImpl.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String redirect = "allbids.jsp";
		//String starttime = null;
		//String endtime = null;
		
		int prodId = Integer.parseInt(request.getParameter("prodId"));
				
		List<ProductBid> allBids = new ArrayList<>();
		
		
		try {
			allBids= productServieImpl.getAllBids(prodId);
		} catch (SQLException | DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		request.setAttribute("allBids",allBids);
		
		
		request.getRequestDispatcher(redirect).forward(request, response);
	}

}
