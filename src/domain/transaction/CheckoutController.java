package domain.transaction;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.services.CartPersistenceService;
import db.services.TransactionPersistenceService;
import db.services.impl.CartPersistenceServiceImpl;
import db.services.impl.TransactionPersistenceServiceImpl;
import domain.product.Product;
import domain.user.Cart;

@WebServlet("/CheckoutController")
public class CheckoutController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private CartPersistenceService cartService = CartPersistenceServiceImpl.getInstance();
	private TransactionPersistenceService trxnService = TransactionPersistenceServiceImpl.getInstance();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sess = request.getSession(true);
		Integer userId = (Integer) sess.getAttribute("userId");
		
		try {
			Cart cart = cartService.retrieve(userId);
			for (Product prod : cart.getProducts()) {
				if (prod.isSold()) {
					throw new Exception("Cannot purchase sold product!");
				}
			}
			
			Transaction trxn = cart.checkout();
			cartService.update(cart);
			trxnService.create(trxn, userId);
			
			// remove sold products from other user's carts.
			for (Product prod : trxn.getProducts()) {
				cartService.removeProductFromAllCarts(prod);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		RequestDispatcher rs = request.getRequestDispatcher("complete.jsp");
		rs.forward(request, response);
	}

}