package domain.product;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import db.dao.DaoException;
import db.services.ProductPersistenceService;
import db.services.impl.ProductPersistenceServiceImpl;


@MultipartConfig
@WebServlet("/BidController")
public class BidController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private ProductPersistenceService productServieImpl = ProductPersistenceServiceImpl.getInstance();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sess = request.getSession(true);
		Integer userId = (Integer) sess.getAttribute("userId");
		
		try {
			Product product = productServieImpl.retrieveCurrrentAuctionProduct();
			Double price = Double.parseDouble(request.getParameter("bid"));
			
			ProductBid productBid = new ProductBid();
			productBid.setPrice(price);
			productBid.setProdId(product.getProdId());
			productBid.setUserId(userId);
			
			ProductBid productBid1 = productServieImpl.placeBid(productBid);
			
			if(productBid1 == null) {
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RequestDispatcher rs = request.getRequestDispatcher("product.jsp");
		rs.forward(request, response);
	}
	
}