package db.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DbManager;
import db.dao.CartDao;
import db.dao.CategoryDao;
import db.dao.CreditCardDao;
import db.dao.DaoException;
import db.dao.InventoryDao;
import db.dao.ProductDao;
import db.dao.TransactionDao;
import db.dao.UserDao;
import db.dao.impl.CartDaoImpl;
import db.dao.impl.CategoryDaoImpl;
import db.dao.impl.CreditCardDaoImpl;
import db.dao.impl.InventoryDaoImpl;
import db.dao.impl.ProductDaoImpl;
import db.dao.impl.TransactionDaoImpl;
import db.dao.impl.UserDaoImpl;
import db.services.UserPersistenceService;
import domain.product.Category;
import domain.product.Product;
import domain.transaction.Transaction;
import domain.user.Cart;
import domain.user.CreditCard;
import domain.user.Inventory;
import domain.user.User;

public class UserPersistenceServiceImpl implements UserPersistenceService {

	private DbManager db = DbManager.getInstance();
	private UserDao userDao = UserDaoImpl.getInstance();
	private CreditCardDao creditCardDao = CreditCardDaoImpl.getInstance();
	private InventoryDao inventoryDao = InventoryDaoImpl.getInstance();
	private CartDao cartDao = CartDaoImpl.getInstance();
	private TransactionDao trxnDao = TransactionDaoImpl.getInstance();
	private ProductDao prodDao = ProductDaoImpl.getInstance();
	private CategoryDao catDao = CategoryDaoImpl.getInstance();

	private static UserPersistenceService instance;
	
	private UserPersistenceServiceImpl() {
		
	}
	
	public static UserPersistenceService getInstance() {
		if (instance == null) {
			instance = new UserPersistenceServiceImpl();
		}
		return instance;
	}
	
	@Override
	public void create(User user) throws SQLException, DaoException {
		if (user.getAddress() == null) {
			throw new DaoException("Address cannot be null!");
		}
		if (user.getCreditCard() == null) {
			throw new DaoException("Credit Card cannot be null!");
		}
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);
			userDao.create(connection, user);
			Integer userId = user.getUserId();

			Inventory inventory = InventoryPersistenceServiceImpl.getInstance().getInventory();
			user.setInventory(inventory);
			inventoryDao.create(connection, inventory, userId);
			inventory.setProducts(new ArrayList<Product>());

			Cart cart = CartPersistenceServiceImpl.getInstance().getCart();
			user.setCart(cart);
			cartDao.create(connection, cart, userId);
			cart.setProducts(new ArrayList<Product>());

			CreditCard creditCard = user.getCreditCard();
			creditCardDao.create(connection, creditCard, userId);

			user.setTransactions(new ArrayList<Transaction>());

			connection.commit();
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
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
	public User retrieve(Integer id) throws SQLException, DaoException {

		Connection connection = db.getConnection();
		try {
			connection.setAutoCommit(false);
			User user = userDao.retrieve(connection, id);
			if (user != null) {
				buildUser(connection, user);
			}

			connection.commit();
			return user;
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
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

	public User retrieveByUsername(String username) throws SQLException, DaoException {
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);
			User user = userDao.retrieveByUsername(connection, username);
			if (user != null) {
				buildUser(connection, user);
			}
			
			connection.commit();
			return user;
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
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

	public User retrieveByProduct(Integer prodId) throws SQLException, DaoException {
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);
			User user = userDao.retrieveByProduct(connection, prodId);
			if (user != null) {
				buildUser(connection, user);
			}
			
			connection.commit();
			return user;
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
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
	private void buildUser(Connection connection, User user) throws SQLException, DaoException {
		int userId = user.getUserId();
		Inventory inventory = inventoryDao.retrieveByUser(connection, userId);
		List<Product> invnProds = prodDao.retrieveByInventory(connection, inventory.getInvnId());
		for (Product prod : invnProds) {
			Category prodCat = catDao.retrieveByProduct(connection, prod.getProdId());
			prod.setCategory(prodCat);
		}
		inventory.setProducts(invnProds);
		user.setInventory(inventory);

		Cart cart = cartDao.retrieveByUser(connection, userId);
		List<Product> cartProds = prodDao.retrieveByCart(connection, cart.getCartId());
		for (Product prod : cartProds) {
			Category prodCat = catDao.retrieveByProduct(connection, prod.getProdId());
			prod.setCategory(prodCat);
		}
		cart.setProducts(cartProds);
		user.setCart(cart);

		CreditCard creditCard = creditCardDao.retrieveByUser(connection, userId);
		user.setCreditCard(creditCard);

		List<Transaction> transactions = trxnDao.retrieveByUser(connection, userId);
		for (Transaction trxn : transactions) {
			List<Product> trxnProds = prodDao.retrieveByTransaction(connection, trxn.getTrxnId());
			for (Product prod : trxnProds) {
				Category prodCat = catDao.retrieveByProduct(connection, prod.getProdId());
				prod.setCategory(prodCat);
			}
			trxn.setProducts(trxnProds);
		}

		user.setTransactions(transactions);

	}

	@Override
	public int update(User user) throws SQLException, DaoException {
		
		Connection connection = db.getConnection();
		try {
			connection.setAutoCommit(false);

			int count = userDao.update(connection, user);
			
			connection.commit();
			return count;
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
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
	public User getUser() {
		return new User();
	}

}
