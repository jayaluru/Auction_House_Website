package db.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import db.DbManager;
import db.dao.CategoryDao;
import db.dao.DaoException;
import db.dao.ProductDao;
import db.dao.impl.CategoryDaoImpl;
import db.dao.impl.ProductDaoImpl;
import db.services.ProductPersistenceService;
import domain.product.Category;
import domain.product.Product;

public class ProductPersistenceServiceImpl implements ProductPersistenceService {

	private DbManager db = DbManager.getInstance();
	private ProductDao prodDao = ProductDaoImpl.getInstance();
	private CategoryDao catDao = CategoryDaoImpl.getInstance();
	
	private static ProductPersistenceService instance;

	private ProductPersistenceServiceImpl() {
		
	}
	
	public static ProductPersistenceService getInstance() {
		if (instance == null) {
			instance = new ProductPersistenceServiceImpl();
		}
		return instance;
	}
	
	@Override
	public Product retrieve(Integer prodId) throws SQLException, DaoException {
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);

			Product prod = prodDao.retrieve(connection, prodId);
			Category prodCat = catDao.retrieveByProduct(connection, prod.getProdId());
			prod.setCategory(prodCat);
			connection.commit();
			return prod;
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
	public List<Product> retrieveAll() throws SQLException, DaoException {
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);

			List<Product> pcs = prodDao.retrieveAll(connection);
			for (Product prod : pcs) {
				Category prodCat = catDao.retrieveByProduct(connection, prod.getProdId());
				prod.setCategory(prodCat);
			}
			connection.commit();
			return pcs;
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

}
