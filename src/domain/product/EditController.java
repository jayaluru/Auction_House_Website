package domain.product;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditController")
public class EditController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer prodId = Integer.parseInt(request.getParameter("prodId"));
		request.setAttribute("prodId", prodId);
		RequestDispatcher rs = request.getRequestDispatcher("productedit.jsp");
		rs.forward(request, response);
	}

}