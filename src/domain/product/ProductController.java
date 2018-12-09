package domain.product;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
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

import db.services.ProductPersistenceService;
import db.services.impl.ProductPersistenceServiceImpl;


@MultipartConfig
@WebServlet("/ProductController")
public class ProductController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private ProductPersistenceService productServieImpl = ProductPersistenceServiceImpl.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sess = request.getSession(true);
		Integer invnId = (Integer) sess.getAttribute("invnId");
		
		if(request.getParameter("price") == null || request.getParameter("name") == null || 
				request.getParameter("biddate") == null || request.getPart("file") == null ||
				request.getParameter("price").equals("") || request.getParameter("name").equals("") || 
				request.getParameter("biddate").equals("") || request.getPart("file").equals("")) {
			request.setAttribute("message_newProduct",  "Please enter all values");
			RequestDispatcher rs = request.getRequestDispatcher("newproduct.jsp");
			rs.forward(request, response);
			return;
		}
		
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		Double price = Double.parseDouble(request.getParameter("price"));
		Date date = new Date(System.currentTimeMillis());
		try {
			date = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("biddate")).getTime());
			
			if(new Date(System.currentTimeMillis()).toString().compareTo(date.toString())>=0) {
				request.setAttribute("message_newProduct",  "Please enter a future date");
				RequestDispatcher rs = request.getRequestDispatcher("newproduct.jsp");
				rs.forward(request, response);
				return;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String startbid = request.getParameter("startbid");
		String endbid = request.getParameter("endbid");
		
		if(startbid == null) {
			startbid = "";
		}
		
		if(endbid == null) {
			endbid = "";
		}
		
		
		// obtains the upload file part in this multipart request
        Part filePart = request.getPart("file");
        // obtains input stream of the upload file
        InputStream inputStream = filePart.getInputStream();
        BufferedImage image = ImageIO.read(inputStream);
        if (image == null)
        {
        	image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        }
        
        
        
		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		product.setPrice(price);
		product.setSold(false);
		product.setBidStartTime(startbid);
		product.setBidEndTime(endbid);
		product.setBidDate(date);
		product.setImage(image);

		try {
			productServieImpl.create(product, invnId);
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace(System.out);
			// TODO return failure message
		}
		
		RequestDispatcher rs = request.getRequestDispatcher("inventory.jsp");
		rs.forward(request, response);
	}
	
}