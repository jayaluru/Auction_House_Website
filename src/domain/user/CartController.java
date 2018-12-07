package domain.user;

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
import domain.product.Product;

@WebServlet("/CartController")
public class CartController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private CartPersistenceService cartService = CartPersistenceServiceImpl.getInstance();
	private PaintingPersistenceService paintService = PaintingPersistenceServiceImpl.getInstance();
	private SculpturePersistenceService sculptService = SculpturePersistenceServiceImpl.getInstance();
	private CraftPersistenceService craftService = CraftPersistenceServiceImpl.getInstance();


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO determine best way to handle products other than paintings
		HttpSession sess = request.getSession(true);
		Integer userId = (Integer) sess.getAttribute("userId");
		Integer prodId = Integer.parseInt(request.getParameter("prodId"));
		Integer catId = Integer.parseInt(request.getParameter("catId"));
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
			cart.addProduct(prod);
			cartService.update(cart);
			// TODO determine if count added new product.
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			// TODO return error msg.
		}
	
				
		request.setAttribute("prodId", prodId);
		RequestDispatcher rs = request.getRequestDispatcher("cart.jsp");
		rs.forward(request, response);
	}

}