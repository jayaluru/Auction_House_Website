package domain.user;

import java.awt.List;
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
import db.services.impl.AdminServiceImpl;


@WebServlet("/AuctionTimingsSearchController")
public class AuctionTimingsSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private AdminService adminService = AdminServiceImpl.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String redirect = "searchresultsauctiontime.jsp";
		String starttime = null;
		String endtime = null;
		
		AuctionTimings auctionTimings = new AuctionTimings();
		Date startdate =null;
		
		try {
			startdate = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("startdate")).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		
		/*try {
			//date = new Date(System.currentTimeMillis());
			date = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date")).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		//Date date = new Date(System.currentTimeMillis());
		//date = new Date(System.currentTimeMillis());
		//auctionTimings.setAuctionDate(date);
		
		
		ArrayList<AuctionTimings> allauctions = new ArrayList<AuctionTimings>();
		
		for(int i=0; i<30 ; i++)
		{
			Calendar today = Calendar.getInstance();
			today.setTime(startdate);
			today.add(Calendar.DAY_OF_YEAR, i);
			java.sql.Date gtoday = new java.sql.Date(today.getTime().getTime()); ;

			
			try {
				auctionTimings = adminService.getTimings(gtoday);
				auctionTimings.setAuctionDate(gtoday);
				allauctions.add(auctionTimings);
			} catch (SQLException ex) {
				
			} catch (DaoException ex) {
				//request.setAttribute("starttime",  ex.getMessage());
				//request.setAttribute("endtime",  ex.getMessage());
				ex.printStackTrace();
			}
		}
		
		request.setAttribute("allauctions",allauctions);
		
		
		request.getRequestDispatcher(redirect).forward(request, response);
	}

}
