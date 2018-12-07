package db.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import db.DbManager;
import db.dao.CategoryDao;
import db.dao.DaoException;
import db.dao.impl.CategoryDaoImpl;
import db.services.CategoryPersistenceService;
import domain.product.Category;

public class CategoryPersistenceServiceImpl implements CategoryPersistenceService {

	private DbManager db = DbManager.getInstance();
	private CategoryDao catDao = CategoryDaoImpl.getInstance();

	private static CategoryPersistenceService instance;
	
	private CategoryPersistenceServiceImpl() {
		
	}
	
	public static CategoryPersistenceService getInstance() {
		if (instance == null) {
			instance = new CategoryPersistenceServiceImpl();
		}
		return instance;
	}
	
	@Override
	public Category retrieve(Integer catId) throws SQLException, DaoException {
		Connection connection = db.getConnection();
		try {
			connection.setAutoCommit(false);
			
			Category cat = catDao.retrieve(connection, catId);
			
			connection.commit();
			return cat;
		} catch (Exception ex) {
			connection.rollback();
			throw ex;
		} finally {
			if (connection != null) {
				connection.setAutoCommit(true);
				if (!connection.isClosed()) {
					connection.close();
				}
			}
		}
	}

	@Override
	public Category retrieveByProduct(Integer prodId) throws SQLException, DaoException {
		Connection connection = db.getConnection();
		try {
			connection.setAutoCommit(false);
			
			Category cat = catDao.retrieveByProduct(connection, prodId);
			
			connection.commit();
			return cat;
		} catch (Exception ex) {
			connection.rollback();
			throw ex;
		} finally {
			if (connection != null) {
				connection.setAutoCommit(true);
				if (!connection.isClosed()) {
					connection.close();
				}
			}
		}
	}

	@Override
	public List<Category> retrieveAll() throws SQLException, DaoException {
		Connection connection = db.getConnection();
		try {
			connection.setAutoCommit(false);
			
			List<Category> cats = catDao.retrieveAll(connection);
			
			connection.commit();
			return cats;
		} catch (Exception ex) {
			connection.rollback();
			throw ex;
		} finally {
			if (connection != null) {
				connection.setAutoCommit(true);
				if (!connection.isClosed()) {
					connection.close();
				}
			}
		}
	}

	@Override
	public Category getCategory() {
		return new Category();
	}
	
	
}
