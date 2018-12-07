package db.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import db.DbManager;
import db.dao.CategoryDao;
import db.dao.DaoException;
import db.dao.ProductDao;
import db.dao.TransactionDao;
import db.dao.impl.CategoryDaoImpl;
import db.dao.impl.ProductDaoImpl;
import db.dao.impl.TransactionDaoImpl;
import db.services.TransactionPersistenceService;
import domain.product.Category;
import domain.product.Product;
import domain.transaction.Transaction;

public class TransactionPersistenceServiceImpl implements TransactionPersistenceService {

	private DbManager db = DbManager.getInstance();
	private TransactionDao trxnDao = TransactionDaoImpl.getInstance();
	private ProductDao prodDao = ProductDaoImpl.getInstance();
	private CategoryDao catDao = CategoryDaoImpl.getInstance();

	private static TransactionPersistenceService instance;
	
	private TransactionPersistenceServiceImpl() {
		
	}
	
	public static TransactionPersistenceService getInstance() {
		if (instance == null) {
			instance = new TransactionPersistenceServiceImpl();
		}
		return instance;
	}
	
	@Override
	public void create(Transaction trxn, Integer userId) throws SQLException, DaoException {
		Connection connection = db.getConnection();
		try {
			connection.setAutoCommit(false);
			trxnDao.create(connection, trxn, userId);
			for (Product prod : trxn.getProducts()) {
				prodDao.update(connection, prod);
			}

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
	public Transaction retrieve(Integer trxnId) throws SQLException, DaoException {
		Connection connection = db.getConnection();
		try {
			connection.setAutoCommit(false);
			Transaction trxn = trxnDao.retrieve(connection, trxnId);

			List<Product> prods = prodDao.retrieveByTransaction(connection, trxnId);
			for (Product prod : prods) {
				Category prodCat = catDao.retrieveByProduct(connection, prod.getProdId());
				prod.setCategory(prodCat);
			}
			trxn.setProducts(prods);

			connection.commit();
			return trxn;
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
	public List<Transaction> retrieveByUser(Integer userId) throws SQLException, DaoException {
		Connection connection = db.getConnection();
		try {
			connection.setAutoCommit(false);
			List<Transaction> trxns = trxnDao.retrieveByUser(connection, userId);

			for (Transaction trxn : trxns) {
				List<Product> prods = prodDao.retrieveByTransaction(connection, trxn.getTrxnId());
				for (Product prod : prods) {
					Category prodCat = catDao.retrieveByProduct(connection, prod.getProdId());
					prod.setCategory(prodCat);
				}
				trxn.setProducts(prods);
			}

			connection.commit();
			return trxns;
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
	public Transaction getTransaction() {
		return new Transaction();
	}

}
