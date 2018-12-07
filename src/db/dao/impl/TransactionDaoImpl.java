package db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.dao.DaoException;
import db.dao.TransactionDao;
import db.services.impl.TransactionPersistenceServiceImpl;
import domain.product.Product;
import domain.transaction.Transaction;

public class TransactionDaoImpl implements TransactionDao {

	private static final String createQuery = 
			"INSERT INTO "
			+ "TRANSACTION (USERID, DATE, PRICE) "
			+ "VALUES (?, ?, ?) ";
	
	private static final String addProductQuery = 
			"INSERT INTO "
			+ "TRANSACTIONPRODUCT (TRXNID, PRODID) "
			+ "VALUES (?, ?) ";
	
	private static final String retrieveQuery = 
			"SELECT "
			+ "TRXNID, DATE, PRICE "
			+ "FROM TRANSACTION "
			+ "WHERE TRXNID = ? ";
	
	private static final String retrieveByUserQuery = 
			"SELECT "
			+ "TRXNID, DATE, PRICE "
			+ "FROM TRANSACTION "
			+ "WHERE USERID = ? ";

	private static TransactionDao instance;
	
	private TransactionDaoImpl() {
		
	}

	public static TransactionDao getInstance() {
		if (instance == null) {
			instance = new TransactionDaoImpl();
		}
		return instance;
	}
	
	@Override
	public void create(Connection connection, Transaction transaction, Integer userId)
			throws SQLException, DaoException {
		if (transaction.getTrxnId() != null) {
			throw new DaoException("TrxnId must be null!");
		}
		if (userId == null) {
			throw new DaoException("UserId cannot be null!");
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, userId);
			statement.setDate(2, transaction.getDate());
			statement.setDouble(3, transaction.getPrice());
			statement.executeUpdate();
			rs = statement.getGeneratedKeys();
			rs.next();
			transaction.setTrxnId(rs.getInt(1));
		} finally {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
		}
		for (Product prod : transaction.getProducts()) {
			statement = null;
			try {
				statement = connection.prepareStatement(addProductQuery);
				statement.setInt(1, transaction.getTrxnId());
				statement.setInt(2, prod.getProdId());
				statement.executeUpdate();
			} finally {
				if (statement != null && !statement.isClosed()) {
					statement.close();
				}
			}
		}

	}

	@Override
	public Transaction retrieve(Connection connection, Integer trxnId) throws SQLException, DaoException {
		if (trxnId == null) {
			throw new DaoException("TrxnId cannot be null!");
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveQuery);
			statement.setInt(1, trxnId);
			rs = statement.executeQuery();
			boolean found = rs.next();
			if (!found) {
				return null;
			}
			Transaction trxn = buildTransaction(rs);
			return trxn;
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
	public List<Transaction> retrieveByUser(Connection connection, Integer userId) throws SQLException, DaoException {
		if (userId == null) {
			throw new DaoException("UserId cannot be null!");
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveByUserQuery);
			statement.setInt(1, userId);
			rs = statement.executeQuery();
			ArrayList<Transaction> transactions = new ArrayList<Transaction>();
			while (rs.next()) {
				Transaction trxn = buildTransaction(rs);
				transactions.add(trxn);
			}
			return transactions;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}

	}

	private Transaction buildTransaction(ResultSet rs) throws SQLException {
		Transaction trxn = TransactionPersistenceServiceImpl.getInstance().getTransaction();
		trxn.setTrxnId(rs.getInt(1));
		trxn.setDate(rs.getDate(2));
		trxn.setPrice(rs.getDouble(3));
		return trxn;
	}

}
