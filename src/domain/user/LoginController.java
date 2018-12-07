package domain.user;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.dao.DaoException;
import db.services.UserPersistenceService;
import db.services.impl.UserPersistenceServiceImpl;

/**
 * Servlet implementation class Login
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserPersistenceService userService = UserPersistenceServiceImpl.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String pass = request.getParameter("password");
	
		Login login = new Login(username, pass);
		User user = null;
		String message = null;
		String redirect = null;
		try {
			user = userService.retrieveByUsername(login.getUsername());
			if (user != null) {
				if (!user.isActive()) {
					redirect = "login.jsp";
					message = "User is deactivated!";
				} else if (user.checkPassword(login.getPassword())) {
					request.getSession().setAttribute("name", user.getName());
					request.getSession().setAttribute("userId", user.getUserId());
					request.getSession().setAttribute("invnId", user.getInventory().getInvnId());
					request.getSession().setAttribute("cartId", user.getCart().getCartId());
					redirect = "home.jsp";
					message = "Logged in!";
				} else {
					redirect = "login.jsp";
					message = "Password incorrect!";
				}
			} else {
				redirect = "register.jsp";
				message = "Username not found. Please register!";
			}
		} catch (SQLException ex) {
			System.out.println(ex);
			ex.printStackTrace();
			redirect = "login.jsp";
			message = "Unable to connect to database!";
		} catch (DaoException ex) {
			System.out.println(ex);
			ex.printStackTrace();
			redirect = "login.jsp";
			message = "Unable to connect to database!";
		}
		request.setAttribute("message", message);
		request.getRequestDispatcher(redirect).forward(request, response);

	}

}
