package domain.user;

import java.io.IOException;
import java.sql.Date;
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
 * Servlet implementation class Register
 */
@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserPersistenceService userService = UserPersistenceServiceImpl.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = UserPersistenceServiceImpl.getInstance().getUser();
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setDescription(request.getParameter("description"));
		user.setAddress(request.getParameter("address"));
		CreditCard card = new CreditCard();
		card.setNumber(request.getParameter("number"));
		card.setExpDate(Date.valueOf(request.getParameter("expdate")));
		card.setCvv(request.getParameter("cvv"));
		user.setCreditCard(card);
		user.setActive(true);
		String message = "Registration complete, please login.";
		String redirect = "login.jsp";
		try {
			userService.create(user);
		} catch (SQLException ex) {
			String msg = ex.getMessage();
			if (msg.startsWith("Duplicate entry \'" + user.getUsername() + "\' for key")) {
				message = "Username already exists!";
			}
			else {
				ex.printStackTrace();
				message = "Registratin failed!";
			}
		} catch (DaoException ex) {
			System.out.println(ex);
			ex.printStackTrace();
			message = "Registratin failed!";
		}
		request.setAttribute("message",  message);
		request.getRequestDispatcher(redirect).forward(request, response);
	}

}
