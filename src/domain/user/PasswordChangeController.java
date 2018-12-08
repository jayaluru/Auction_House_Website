package domain.user;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.dao.DaoException;
import db.services.UserPersistenceService;
import db.services.impl.UserPersistenceServiceImpl;


@WebServlet("/PasswordChangeController")
public class PasswordChangeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserPersistenceService userService = UserPersistenceServiceImpl.getInstance();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String password1 = request.getParameter("password");
		String password2 = request.getParameter("password1");
		String message = "";
		String redirect = "passwordChange.jsp";
		
		HttpSession sess = request.getSession(true);
		String username = (String) sess.getAttribute("username");
		
		try {
			if(password1.equals(password2)) {
				User user = userService.retrieveByUsername(username);
				
				user.setPassword(password1);
				
				userService.update(user);
				
				redirect = "login.jsp";
				message = "Password changes successfully. Please Login.";
			}else {
				message = "Passwords do not match!";
			}
		} catch (SQLException ex) {
			System.out.println(ex);
			ex.printStackTrace();
			message = "Passwords do not match!";
		} catch (DaoException ex) {
			System.out.println(ex);
			ex.printStackTrace();
			message = "Passwords do not match!";
		}
		request.setAttribute("message",  message);
		request.getRequestDispatcher(redirect).forward(request, response);
	}

}
