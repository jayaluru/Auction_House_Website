package db.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import db.DbManager;
import db.dao.CartDao;
import db.dao.CategoryDao;
import db.dao.DaoException;
import db.dao.InventoryDao;
import db.dao.ProductCategoryDao;
import db.dao.ProductDao;
import db.dao.impl.CartDaoImpl;
import db.dao.impl.CategoryDaoImpl;
import db.dao.impl.InventoryDaoImpl;
import db.dao.impl.ProductDaoImpl;
import db.services.ProductCategoryPersistenceService;
import domain.product.Category;
import domain.product.Product;

public abstract class AbstractProductCategoryPersistenceService<T extends Product> implements ProductCategoryPersistenceService<T> {

	private DbManager db = DbManager.getInstance();
	private InventoryDao inventoryDao = InventoryDaoImpl.getInstance();
	private CartDao cartDao = CartDaoImpl.getInstance();
	private ProductDao prodDao = ProductDaoImpl.getInstance();
	private CategoryDao catDao = CategoryDaoImpl.getInstance();
	private ProductCategoryDao<T> prodCatDao;
	
	protected AbstractProductCategoryPersistenceService(ProductCategoryDao<T> prodCatDao) {
		this.prodCatDao = prodCatDao;
	}
	
	@Override
	public void create(T product, Integer invnId) throws SQLException, DaoException {
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);

			prodDao.create(connection, product);
			prodCatDao.create(connection, product);
			inventoryDao.addProduct(connection, product.getProdId(), invnId);

			connection.commit();
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
	public List<T> retrieveAll() throws SQLException, DaoException {
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);

			List<T> pcs = prodCatDao.retrieveAll(connection);
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

	@Override
	public T retrieve(Integer prodId) throws SQLException, DaoException {
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);

			T pc = prodCatDao.retrieve(connection, prodId);
			if (pc != null) {
				Category prodCat = catDao.retrieveByProduct(connection, pc.getProdId());
				pc.setCategory(prodCat);
			}
			
			connection.commit();
			return pc;
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
	public int update(T product) throws SQLException, DaoException {
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);

			int count = prodCatDao.update(connection, product);
			int prodCount = prodDao.update(connection, product);
			
			if (prodCount != count) {
				throw new DaoException("Unable to update Product: " + count + " prodCat rows and " + prodCount + " product rows updated!");
			}
			
			connection.commit();
			return count;
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
	public int delete(T product) throws SQLException, DaoException {
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);
			
			cartDao.removeProductFromAllCarts(connection, product.getProdId());
			// TODO consider moving this to inventoryDao from prodDao.
			Integer invnId = prodDao.retrieveInventoryId(connection, product);
			inventoryDao.removeProduct(connection, product.getProdId(), invnId);
			
			int count = prodCatDao.delete(connection, product);
			int prodCount = prodDao.delete(connection, product);
			
			if (prodCount != count) {
				throw new DaoException("Unable to delete Product!");
			}
			
			connection.commit();
			return count;
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
