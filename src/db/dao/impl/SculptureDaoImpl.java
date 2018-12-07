package db.dao.impl;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import db.dao.SculptureDao;
import db.services.impl.SculpturePersistenceServiceImpl;
import domain.product.Sculpture;

public class SculptureDaoImpl extends AbstractProductCategoryDao<Sculpture> implements SculptureDao {

	private static final String createQuery = 
			"INSERT INTO "
			+ "SCULPTURE (PRODID, MATERIAL, WEIGHT, LENGTH, WIDTH, HEIGHT) "
			+ "VALUES (?, ?, ?, ?, ?, ?) ";

	private static final String retrieveQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, p.PHOTO, i.MATERIAL, i.WEIGHT, i.LENGTH, i.WIDTH, i.HEIGHT "
			+ "FROM PRODUCT p "
			+ "JOIN SCULPTURE i ON p.PRODID = i.PRODID "
			+ "WHERE p.PRODID = ? ";

	private static final String retrieveAllQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, p.PHOTO, i.MATERIAL, i.WEIGHT, i.LENGTH, i.WIDTH, i.HEIGHT "
			+ "FROM PRODUCT p "
			+ "JOIN SCULPTURE i ON p.PRODID = i.PRODID ";

	private static final String updateQuery = 
			"UPDATE "
			+ "SCULPTURE "
			+ "SET MATERIAL = ?, WEIGHT = ?, LENGTH = ?, WIDTH = ?, HEIGHT = ? "
			+ "WHERE PRODID = ? ";

	private static final String deleteQuery = 
			"DELETE FROM "
			+ "SCULPTURE "
			+ "WHERE PRODID = ? ";

	private static SculptureDao instance;
	
	private SculptureDaoImpl() {
		super(createQuery, retrieveQuery, retrieveAllQuery, updateQuery, deleteQuery);
	}

	public static SculptureDao getInstance() {
		if (instance == null) {
			instance = new SculptureDaoImpl();
		}
		return instance;
	}
	
	@Override
	protected Sculpture build(ResultSet rs) throws SQLException {
		Sculpture sculpture = SculpturePersistenceServiceImpl.getInstance().getProd();
		sculpture.setProdId(rs.getInt(1));
		sculpture.setName(rs.getString(2));
		sculpture.setDescription(rs.getString(3));
		sculpture.setPrice(rs.getDouble(4));
		sculpture.setSold(rs.getBoolean(5));
		try {
			sculpture.setImage(ImageIO.read(rs.getBlob(6).getBinaryStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		sculpture.setMaterial(rs.getString(7));
		sculpture.setWeight(rs.getDouble(8));
		sculpture.setLength(rs.getDouble(9));
		sculpture.setWidth(rs.getDouble(10));
		sculpture.setHeight(rs.getDouble(11));
		return sculpture;
	}

	@Override
	protected int createStatement(int index, PreparedStatement statement, Sculpture product) throws SQLException {
		statement.setString(index++, product.getMaterial());
		statement.setDouble(index++, product.getWeight());
		statement.setDouble(index++, product.getLength());
		statement.setDouble(index++, product.getWidth());
		statement.setDouble(index++, product.getHeight());
		return index;
	}
	
}
