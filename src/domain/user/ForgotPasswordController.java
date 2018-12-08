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


@WebServlet("/ForgotPasswordController")
public class ForgotPasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserPersistenceService userService = UserPersistenceServiceImpl.getInstance();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String message = "";
		String redirect = "forgotpassword.jsp";
		
		HttpSession sess = request.getSession(true);
		sess.setAttribute("username", username);
		try {
			User user = userService.retrieveByUsername(username);
			
			redirect = "asksecurity.jsp";
			request.setAttribute("securityQuestion",  user.getSecurityQuestion());
		} catch (SQLException ex) {
			System.out.println(ex);
			ex.printStackTrace();
			message = "Your profile is not found!";
		} catch (DaoException ex) {
			System.out.println(ex);
			ex.printStackTrace();
			message = "Your profile is not found!";
		}
		request.setAttribute("message2",  message);
		request.getRequestDispatcher(redirect).forward(request, response);
	}

}
