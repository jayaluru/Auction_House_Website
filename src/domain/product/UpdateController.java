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

@WebServlet("/UpdateController")
public class UpdateController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private PaintingPersistenceService paintService = PaintingPersistenceServiceImpl.getInstance();
	private SculpturePersistenceService sculptureService = SculpturePersistenceServiceImpl.getInstance();
	private CraftPersistenceService craftService = CraftPersistenceServiceImpl.getInstance();
	//

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Integer catId = Integer.parseInt(request.getParameter("catId"));
		Integer prodId = Integer.parseInt(request.getParameter("prodId"));
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		double price = Double.parseDouble(request.getParameter("price"));
		
		try {
			if (catId == Category.PAINTING) {
				Painting painting = paintService.retrieve(prodId);
				String canvasType = request.getParameter("canvasType");
				String paintType = request.getParameter("paintType");
				Double length = Double.parseDouble(request.getParameter("length"));
				Double width = Double.parseDouble(request.getParameter("width"));
				painting.setName(name);
				painting.setDescription(description);
				painting.setPrice(price);
				painting.setCanvasType(canvasType);
				painting.setPaintType(paintType);
				painting.setLength(length);
				painting.setWidth(width);
				paintService.update(painting);
			}
			else if (catId == Category.SCULPTURE) {
				Sculpture sculpture = sculptureService.retrieve(prodId);
				String material = request.getParameter("material");
				Double weight = Double.parseDouble(request.getParameter("weight"));
				Double length = Double.parseDouble(request.getParameter("length"));
				Double width = Double.parseDouble(request.getParameter("width"));
				Double height = Double.parseDouble(request.getParameter("height"));
				sculpture.setName(name);
				sculpture.setDescription(description);
				sculpture.setPrice(price);
				sculpture.setMaterial(material);
				sculpture.setWeight(weight);
				sculpture.setLength(length);
				sculpture.setWidth(width);
				sculpture.setHeight(height);
				sculptureService.update(sculpture);
			}
			else if (catId == Category.CRAFT) {
				Craft craft = craftService.retrieve(prodId);
				String usage = request.getParameter("usage");
				Double length = Double.parseDouble(request.getParameter("length"));
				Double width = Double.parseDouble(request.getParameter("width"));
				Double height = Double.parseDouble(request.getParameter("height"));
				craft.setName(name);
				craft.setDescription(description);
				craft.setPrice(price);
				craft.setUsage(usage);
				craft.setLength(length);
				craft.setWidth(width);
				craft.setHeight(height);
				craftService.update(craft);
			}
		} catch (SQLException | DaoException e) {
			e.printStackTrace();
		}
		RequestDispatcher rs = request.getRequestDispatcher("inventory.jsp");
		rs.forward(request, response);
	}

}