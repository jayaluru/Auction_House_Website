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
import domain.user.User;

@WebServlet("/ProfileController")
public class ProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserPersistenceService userService = UserPersistenceServiceImpl.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Integer prodId = Integer.parseInt(request.getParameter("prodId"));
		User profileUser = null;
		try {
			profileUser = userService.retrieveByProduct(prodId);
		} catch (SQLException | DaoException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("userId", profileUser.getUserId());
		request.getRequestDispatcher("profile.jsp").forward(request, response);

	}
}

