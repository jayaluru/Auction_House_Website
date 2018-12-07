package domain.product;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.services.CartPersistenceService;
import db.services.CraftPersistenceService;
import db.services.PaintingPersistenceService;
import db.services.SculpturePersistenceService;
import db.services.impl.CartPersistenceServiceImpl;
import db.services.impl.CraftPersistenceServiceImpl;
import db.services.impl.PaintingPersistenceServiceImpl;
import db.services.impl.SculpturePersistenceServiceImpl;
import domain.user.Cart;

@WebServlet("/DetailsController")
public class DetailsController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private CartPersistenceService cartService = CartPersistenceServiceImpl.getInstance();
	private PaintingPersistenceService paintService = PaintingPersistenceServiceImpl.getInstance();
	private SculpturePersistenceService sculptService = SculpturePersistenceServiceImpl.getInstance();
	private CraftPersistenceService craftService = CraftPersistenceServiceImpl.getInstance();


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sess = request.getSession(true);
		Integer userId = (Integer) sess.getAttribute("userId");
		Integer prodId = Integer.parseInt(request.getParameter("prodId"));
		Integer catId = Integer.parseInt(request.getParameter("catId"));
		
		if(null != request.getParameter("ViewDetails"))
		{
			request.setAttribute("prodId", prodId);
			request.setAttribute("catId", catId);
			RequestDispatcher rs = request.getRequestDispatcher("productdetails.jsp");
			rs.forward(request, response);
		}
		
		else if(null!=request.getParameter("Remove")) {
		
			try {
				Cart cart = cartService.retrieve(userId);
				Product prod = null;			
				if (catId == 1) {
					prod = paintService.retrieve(prodId);
				}
				else if (catId == 2) {
					prod = sculptService.retrieve(prodId);
				}
				else if (catId == 3) {
					prod = craftService.retrieve(prodId);
				}
				
				cart.removeProduct(prod);
				cartService.update(cart);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e);
				// TODO return error msg.
			}
			
			//request.("prodId");
			RequestDispatcher rs = request.getRequestDispatcher("cart.jsp");
			rs.forward(request, response);
		}
		}

}