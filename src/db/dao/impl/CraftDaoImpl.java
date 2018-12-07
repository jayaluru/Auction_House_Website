package db.dao.impl;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import db.dao.CraftDao;
import db.services.impl.CraftPersistenceServiceImpl;
import domain.product.Craft;

public class CraftDaoImpl extends AbstractProductCategoryDao<Craft> implements CraftDao {
	
	// Need to escape the word "USAGE" in MySQL
	private static final String createQuery = 
			"INSERT INTO "
			+ "CRAFT (PRODID, `USAGE`, LENGTH, WIDTH, HEIGHT) "
			+ "VALUES (?, ?, ?, ?, ?) ";

	private static final String retrieveQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, p.PHOTO, i.`USAGE`, i.LENGTH, i.WIDTH, i.HEIGHT "
			+ "FROM PRODUCT p "
			+ "JOIN CRAFT i ON p.PRODID = i.PRODID "
			+ "WHERE p.PRODID = ? ";

	private static final String retrieveAllQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, p.PHOTO, i.`USAGE`, i.LENGTH, i.WIDTH, i.HEIGHT "
			+ "FROM PRODUCT p "
			+ "JOIN CRAFT i ON p.PRODID = i.PRODID ";

	private static final String updateQuery = 
			"UPDATE "
			+ "CRAFT "
			+ "SET `USAGE` = ?, LENGTH = ?, WIDTH = ?, HEIGHT = ? "
			+ "WHERE PRODID = ? ";

	private static final String deleteQuery = 
			"DELETE FROM "
			+ "CRAFT "
			+ "WHERE PRODID = ? ";

	private static CraftDao instance;
	
	private CraftDaoImpl() {
		super(createQuery, retrieveQuery, retrieveAllQuery, updateQuery, deleteQuery);
	}

	public static CraftDao getInstance() {
		if (instance == null) {
			instance = new CraftDaoImpl();
		}
		return instance;
	}
	
	@Override
	protected Craft build(ResultSet rs) throws SQLException {
		Craft craft = CraftPersistenceServiceImpl.getInstance().getProd();
		craft.setProdId(rs.getInt(1));
		craft.setName(rs.getString(2));
		craft.setDescription(rs.getString(3));
		craft.setPrice(rs.getDouble(4));
		craft.setSold(rs.getBoolean(5));
		try {
			craft.setImage(ImageIO.read(rs.getBlob(6).getBinaryStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		craft.setUsage(rs.getString(7));
		craft.setLength(rs.getDouble(8));
		craft.setWidth(rs.getDouble(9));
		craft.setHeight(rs.getDouble(10));
		return craft;
	}

	@Override
	protected int createStatement(int index, PreparedStatement statement, Craft product) throws SQLException {
		statement.setString(index++, product.getUsage());
		statement.setDouble(index++, product.getLength());
		statement.setDouble(index++, product.getWidth());
		statement.setDouble(index++, product.getHeight());
		return index;
	}
	
}
