package domain.product;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import db.services.CategoryPersistenceService;
import db.services.PaintingPersistenceService;
import db.services.impl.CategoryPersistenceServiceImpl;
import db.services.impl.PaintingPersistenceServiceImpl;

@MultipartConfig
@WebServlet("/PaintingController")
public class PaintingController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private PaintingPersistenceService paintService = PaintingPersistenceServiceImpl.getInstance();
	private CategoryPersistenceService catService = CategoryPersistenceServiceImpl.getInstance();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sess = request.getSession(true);
		Integer invnId = (Integer) sess.getAttribute("invnId");
		
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		Double price = Double.parseDouble(request.getParameter("price"));
		String canvasType = request.getParameter("canvasType");
		String paintType = request.getParameter("paintType");
		Double length = Double.parseDouble(request.getParameter("length"));
		Double width = Double.parseDouble(request.getParameter("width"));
		
		// obtains the upload file part in this multipart request
        Part filePart = request.getPart("file");
        // obtains input stream of the upload file
        InputStream inputStream = filePart.getInputStream();
        BufferedImage image = ImageIO.read(inputStream);
        if (image == null)
        {
        	image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        }
        
		Painting painting = PaintingPersistenceServiceImpl.getInstance().getProd();
		painting.setName(name);
		painting.setDescription(description);
		painting.setPrice(price);
		painting.setSold(false);
		painting.setCanvasType(canvasType);
		painting.setPaintType(paintType);
		painting.setLength(length);
		painting.setWidth(width);
		painting.setImage(image);

		try {
			Category cat = catService.retrieve(Category.PAINTING);
			painting.setCategory(cat);			
			paintService.create(painting, invnId);
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace(System.out);
			// TODO return failure message
		}
		
		RequestDispatcher rs = request.getRequestDispatcher("inventory.jsp");
		rs.forward(request, response);
	}
	
}