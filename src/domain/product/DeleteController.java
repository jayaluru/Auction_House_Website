package domain.product;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import db.dao.DaoException;
import db.services.*;
import db.services.impl.*;

@WebServlet("/DeleteController")
public class DeleteController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private PaintingPersistenceService paintService = PaintingPersistenceServiceImpl.getInstance();
	private SculpturePersistenceService sculptureService = SculpturePersistenceServiceImpl.getInstance();
	private CraftPersistenceService craftService = CraftPersistenceServiceImpl.getInstance();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer catId = Integer.parseInt(request.getParameter("catId"));
		Integer prodId = Integer.parseInt(request.getParameter("prodId"));

		try {
			if (catId == Category.PAINTING) {
				Painting painting = paintService.retrieve(prodId);
				paintService.delete(painting);
			} else if (catId == Category.SCULPTURE) {
				Sculpture sculpture = sculptureService.retrieve(prodId);
				sculptureService.delete(sculpture);
			} else if (catId == Category.CRAFT) {
				Craft craft = craftService.retrieve(prodId);
				craftService.delete(craft);
			}
		} catch (SQLException | DaoException e) {
			e.printStackTrace();
		}
		RequestDispatcher rs = request.getRequestDispatcher("inventory.jsp");
		rs.forward(request, response);
	}

}