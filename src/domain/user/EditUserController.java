package domain.user;

import java.io.IOException;
import java.sql.Date;
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


@WebServlet("/EditUserController")
public class EditUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserPersistenceService userService = UserPersistenceServiceImpl.getInstance();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sess = request.getSession(true);
		Integer userId = (Integer) sess.getAttribute("userId");

		String message = "";
		String redirect = "editprofile.jsp";
		User user = new User();
		user.setUserId(userId);
		try {
			user = userService.retrieve(userId);

			String username = request.getParameter("username");
			if(username!=null && !username.equals("")) {
				user.setUsername(username);
			}

			String password = request.getParameter("password");
			if(password!=null && !password.equals("")) {
				user.setPassword(password);
			}

			String name = request.getParameter("name");
			if(name!=null && !name.equals("")) {
				user.setName(name);
			}

			String description = request.getParameter("description");
			if(description!=null && !description.equals("")) {
				user.setDescription(description);
			}

			String address = request.getParameter("address");
			if(address!=null && !address.equals("")) {
				user.setAddress(address);
			}

			String securityQuestion = request.getParameter("securityQuestion");
			if(securityQuestion!=null && !securityQuestion.equals("")) {
				user.setSecurityQuestion(securityQuestion);
			}

			String securityAnswer = request.getParameter("securityAnswer");
			if(securityAnswer!=null && !securityAnswer.equals("")) {
				user.setSecurityAnswer(securityAnswer);
			}

			user.setActive(true);
			redirect = "profile.jsp";
			userService.update(user);
		} catch (SQLException ex) {
			String msg = ex.getMessage();
			if (msg.startsWith("Duplicate entry \'" + user.getUsername() + "\' for key")) {
				message = "Username already exists!";
			}
			else {
				ex.printStackTrace();
				message = "Could update your profile!";
			}
		} catch (DaoException ex) {
			System.out.println(ex);
			ex.printStackTrace();
			message = "Could update your profile!";
		}
		request.setAttribute("message",  message);
		request.getRequestDispatcher(redirect).forward(request, response);
	}

}
