package db.dao.impl;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import db.dao.PaintingDao;
import db.services.impl.PaintingPersistenceServiceImpl;
import domain.product.Painting;

public class PaintingDaoImpl extends AbstractProductCategoryDao<Painting> implements PaintingDao {

	private static final String createQuery = 
			"INSERT INTO "
			+ "PAINTING (PRODID, CANVASTYPE, PAINTTYPE, LENGTH, WIDTH) "
			+ "VALUES (?, ?, ?, ?, ?) ";
	
	private static final String retrieveQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, p.PHOTO, i.CANVASTYPE, i.PAINTTYPE, i.LENGTH, i.WIDTH "
			+ "FROM PRODUCT p "
			+ "JOIN PAINTING i ON p.PRODID = i.PRODID "
			+ "WHERE p.PRODID = ? ";

	private static final String retrieveAllQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, p.PHOTO, i.CANVASTYPE, i.PAINTTYPE, i.LENGTH, i.WIDTH "
			+ "FROM PRODUCT p "
			+ "JOIN PAINTING i ON p.PRODID = i.PRODID ";

	private static final String updateQuery = 
			"UPDATE "
			+ "PAINTING "
			+ "SET CANVASTYPE = ?, PAINTTYPE = ?, LENGTH = ?, WIDTH = ? "
			+ "WHERE PRODID = ? ";

	private static final String deleteQuery = 
			"DELETE FROM "
			+ "PAINTING "
			+ "WHERE PRODID = ? ";

	private static PaintingDao instance;
	
	private PaintingDaoImpl() {
		super(createQuery, retrieveQuery, retrieveAllQuery, updateQuery, deleteQuery);
	}

	public static PaintingDao getInstance() {
		if (instance == null) {
			instance = new PaintingDaoImpl();
		}
		return instance;
	}
	
	@Override
	protected Painting build(ResultSet rs) throws SQLException {
		Painting painting = PaintingPersistenceServiceImpl.getInstance().getProd();
		painting.setProdId(rs.getInt(1));
		painting.setName(rs.getString(2));
		painting.setDescription(rs.getString(3));
		painting.setPrice(rs.getDouble(4));
		painting.setSold(rs.getBoolean(5));
		try {
			painting.setImage(ImageIO.read(rs.getBlob(6).getBinaryStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		painting.setCanvasType(rs.getString(7));
		painting.setPaintType(rs.getString(8));
		painting.setLength(rs.getDouble(9));
		painting.setWidth(rs.getDouble(10));
		return painting;
	}

	@Override
	protected int createStatement(int index, PreparedStatement statement, Painting product) throws SQLException {
		statement.setString(index++, product.getCanvasType());
		statement.setString(index++, product.getPaintType());
		statement.setDouble(index++, product.getLength());
		statement.setDouble(index++, product.getWidth());
		return index;
	}
	
}
