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


@WebServlet("/SecurityQuestionCheckController")
public class SecurityQuestionCheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserPersistenceService userService = UserPersistenceServiceImpl.getInstance();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession sess = request.getSession(true);
		String username = (String) sess.getAttribute("username");
		
		String message = "";
		String redirect = "asksecurity.jsp";
		
		try {
			User user = userService.retrieveByUsername(username);
			
			String password = request.getParameter("securityAnswer");
			
			if(user.getSecurityAnswer().equals(password)) {
				redirect = "passwordChange.jsp";
			}else {
				message = "Wrong Password";
			}
			
			
		} catch (SQLException ex) {
			System.out.println(ex);
			ex.printStackTrace();
			message = "Wrong Password. Try Again!";
		} catch (DaoException ex) {
			System.out.println(ex);
			ex.printStackTrace();
			message = "Wrong Password. Try Again!";
		}
		request.setAttribute("message1",  message);
		request.getRequestDispatcher(redirect).forward(request, response);
	}

}
