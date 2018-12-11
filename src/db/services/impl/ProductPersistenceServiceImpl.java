package db.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import db.DbManager;
import db.dao.DaoException;
import db.dao.InventoryDao;
import db.dao.ProductDao;
import db.dao.impl.InventoryDaoImpl;
import db.dao.impl.ProductDaoImpl;
import db.services.ProductPersistenceService;
import domain.product.Product;
import domain.product.ProductBid;

public class ProductPersistenceServiceImpl implements ProductPersistenceService {

	private DbManager db = DbManager.getInstance();
	private ProductDao prodDao = ProductDaoImpl.getInstance();
	private InventoryDao invDao = InventoryDaoImpl.getInstance();
	
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
	public ProductBid getHighestBid(Integer prodId) throws SQLException, DaoException{
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);

			ProductBid bid = prodDao.getHighestBid(connection, prodId);
			
			return bid;
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
	public ProductBid placeBid(ProductBid productBid) throws SQLException, DaoException{
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);

			ProductBid bid = prodDao.getHighestBid(connection, productBid.getProdId());
			
			if(bid == null) {
				bid = new ProductBid();
				bid.setPrice(prodDao.retrieve(connection, productBid.getProdId()).getPrice());
			}
			
			if(bid.getPrice()>productBid.getPrice()) {
				return null;
			}
			
			prodDao.createBid(connection, productBid);
			
			connection.commit();
			
			return productBid;
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
	public List<ProductBid> getAllBids(Integer prodId) throws SQLException, DaoException{
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);

			List<ProductBid> bid = prodDao.getAllBids(connection, prodId);
			
			return bid;
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
	public Product retrieveCurrrentAuctionProduct() throws SQLException, DaoException{
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);

			Product prod = prodDao.retrieveCurrrentAuctionProduct(connection);
			
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
	public void create(Product prod, Integer invnId) throws SQLException, DaoException {
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);

			prodDao.create(connection, prod);
			invDao.addProduct(connection, prod.getProdId(), invnId);
			
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
	public void delete(Product prod, Integer invnId) throws SQLException, DaoException {
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);
			
			invDao.removeProduct(connection, prod.getProdId(), invnId);
			prodDao.delete(connection, prod);
			
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
	public Product retrieve(Integer prodId) throws SQLException, DaoException {
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);

			Product prod = prodDao.retrieve(connection, prodId);
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
