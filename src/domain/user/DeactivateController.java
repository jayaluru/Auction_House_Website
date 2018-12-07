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

/**
 * Servlet implementation class Deactivate
 */
@WebServlet("/DeactivateController")
public class DeactivateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserPersistenceService userService = UserPersistenceServiceImpl.getInstance();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sess = request.getSession(true);
		Integer userId = (Integer) sess.getAttribute("userId");
		try {
			User user = userService.retrieve(userId);
			user.setActive(false);
			userService.update(user);
		} catch (SQLException | DaoException ex) {
			ex.printStackTrace();
		}
		sess.invalidate();
		request.setAttribute("message",  "User deactivated.");
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

}
