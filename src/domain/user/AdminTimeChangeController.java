package domain.user;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.dao.DaoException;
import db.services.AdminService;
import db.services.impl.AdminServiceImpl;


@WebServlet("/AdminTimeChangeController")
public class AdminTimeChangeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private AdminService adminService = AdminServiceImpl.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String redirect = "setauctionschedule.jsp";
		AuctionTimings auctionTimings = new AuctionTimings();
		auctionTimings.setStartTime(request.getParameter("starttime"));
		auctionTimings.setEndTime(request.getParameter("endtime"));
		
		Date date = new Date(System.currentTimeMillis());
		try {
			date = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date")).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		auctionTimings.setAuctionDate(date);
		
		try {
			adminService.update(auctionTimings);
			request.setAttribute("message_admin",  "Auction Timings changed");
		} catch (SQLException ex) {
			request.setAttribute("message_admin",  ex.getMessage());
		} catch (DaoException ex) {
			request.setAttribute("message_admin",  ex.getMessage());
			ex.printStackTrace();
		}
		request.getRequestDispatcher(redirect).forward(request, response);
	}

}
