package db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.dao.CategoryDao;
import db.dao.DaoException;
import db.services.impl.CategoryPersistenceServiceImpl;
import domain.product.Category;

public class CategoryDaoImpl implements CategoryDao {

	private static final String retrieveQuery = 
			"SELECT "
			+ "CATID, NAME, DESCRIPTION "
			+ "FROM CATEGORY "
			+ "WHERE CATID = ? ";
	
	private static final String retrieveByProductQuery = 
			"SELECT "
			+ "c.CATID, c.NAME, c.DESCRIPTION "
			+ "FROM CATEGORY c "
			+ "JOIN PRODUCT p "
			+ "ON c.CATID = p.CATID "
			+ "WHERE p.PRODID = ? ";
	
	private static final String retrieveAllQuery = 
			"SELECT "
			+ "CATID, NAME, DESCRIPTION "
			+ "FROM CATEGORY ";
	
	private static CategoryDao instance;
	
	private CategoryDaoImpl() {
		
	}

	public static CategoryDao getInstance() {
		if (instance == null) {
			instance = new CategoryDaoImpl();
		}
		return instance;
	}
	
	@Override
	public Category retrieve(Connection connection, Integer catId) throws SQLException, DaoException {
		if (catId == null) {
			throw new DaoException("CatId cannot be null!");
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveQuery);
			statement.setInt(1, catId);
			rs = statement.executeQuery();
			boolean found = rs.next();
			if (!found) {
				return null;
			}
			Category cat = buildCategory(rs);
			return cat;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}

	@Override
	public Category retrieveByProduct(Connection connection, Integer prodId) throws SQLException, DaoException {
		if (prodId == null) {
			throw new DaoException("ProdId cannot be null!");
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveByProductQuery);
			statement.setInt(1, prodId);
			rs = statement.executeQuery();
			boolean found = rs.next();
			if (!found) {
				return null;
			}
			Category cat = buildCategory(rs);
			return cat;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}
	
	@Override
	public List<Category> retrieveAll(Connection connection) throws SQLException, DaoException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveAllQuery);
			rs = statement.executeQuery();
			ArrayList<Category> cats = new ArrayList<Category>();

			while (rs.next()) {
				Category cat = buildCategory(rs);
				cats.add(cat);
			}
			return cats;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}

	private Category buildCategory(ResultSet rs) throws SQLException {
		Category cat = CategoryPersistenceServiceImpl.getInstance().getCategory();
		cat.setCatId(rs.getInt(1));
		cat.setName(rs.getString(2));
		cat.setDescription(rs.getString(3));
		return cat;
	}

}
