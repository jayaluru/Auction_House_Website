package db.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import db.DbManager;
import db.dao.CartDao;
import db.dao.CategoryDao;
import db.dao.DaoException;
import db.dao.ProductDao;
import db.dao.impl.CartDaoImpl;
import db.dao.impl.CategoryDaoImpl;
import db.dao.impl.ProductDaoImpl;
import db.services.CartPersistenceService;
import domain.product.Category;
import domain.product.Product;
import domain.user.Cart;

public class CartPersistenceServiceImpl implements CartPersistenceService {

	private DbManager db = DbManager.getInstance();
	private CartDao cartDao = CartDaoImpl.getInstance();
	private ProductDao prodDao = ProductDaoImpl.getInstance();
	private CategoryDao catDao = CategoryDaoImpl.getInstance();

	private static CartPersistenceService instance;
	
	private CartPersistenceServiceImpl() {
		
	}
	
	public static CartPersistenceService getInstance() {
		if (instance == null) {
			instance = new CartPersistenceServiceImpl();
		}
		return instance;
	}
	
	@Override
	public Cart retrieve(Integer userId) throws SQLException, DaoException {
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);
			Cart cart = cartDao.retrieveByUser(connection, userId);
			List<Product> prods = prodDao.retrieveByCart(connection, cart.getCartId());
			for (Product prod : prods) {
				Category prodCat = catDao.retrieveByProduct(connection, prod.getProdId());
				prod.setCategory(prodCat);
			}
			cart.setProducts(prods);
			connection.commit();
			return cart;
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
	public int update(Cart cart) throws SQLException, DaoException {
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);
			int updatedCount = cartDao.update(connection, cart);
			connection.commit();
			return updatedCount;
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
	public int removeProductFromAllCarts(Product prod) throws SQLException, DaoException {
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);
			int updatedCount = cartDao.removeProductFromAllCarts(connection, prod.getProdId());
			connection.commit();
			return updatedCount;
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
	public Cart getCart() {
		return new Cart();
	}

}
