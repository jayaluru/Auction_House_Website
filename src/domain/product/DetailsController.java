package domain.product;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/DetailsController")
public class DetailsController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sess = request.getSession(true);
		Integer userId = (Integer) sess.getAttribute("userId");
		Integer prodId = Integer.parseInt(request.getParameter("prodId"));
		
		if(null != request.getParameter("ViewDetails"))
		{
			request.setAttribute("prodId", prodId);
			RequestDispatcher rs = request.getRequestDispatcher("productdetails.jsp");
			rs.forward(request, response);
		}
		
		else if(null!=request.getParameter("Remove")) {
		
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e);
				// TODO return error msg.
			}
			
			//request.("prodId");
			RequestDispatcher rs = request.getRequestDispatcher("cart.jsp");
			rs.forward(request, response);
		}
		}

}