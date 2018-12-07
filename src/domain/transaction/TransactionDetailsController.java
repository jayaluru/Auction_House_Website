package domain.transaction;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TransactionDetailsController")
public class TransactionDetailsController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer trxnId = Integer.parseInt(request.getParameter("trxnId"));
		request.setAttribute("trxnId", trxnId);
		RequestDispatcher rs = request.getRequestDispatcher("transactiondetails.jsp");
		rs.forward(request, response);
	}
}