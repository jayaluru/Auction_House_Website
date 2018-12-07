package db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.dao.DaoException;
import db.dao.InventoryDao;
import db.services.impl.InventoryPersistenceServiceImpl;
import domain.user.Inventory;

public class InventoryDaoImpl implements InventoryDao {

	private static final String createQuery = 
			"INSERT INTO "
			+ "INVENTORY (USERID) "
			+ "VALUES (?) ";
	
	private static final String retrieveQuery = 
			"SELECT "
			+ "INVNID, USERID "
			+ "FROM INVENTORY "
			+ "WHERE USERID = ? ";
		
	private static final String addProductQuery = 
			"INSERT INTO "
			+ "INVENTORYPRODUCT (INVNID, PRODID) "
			+ "VALUES (?, ?) ";

	private static final String removeProductQuery = 
			"DELETE FROM "
			+ "INVENTORYPRODUCT "
			+ "WHERE INVNID = ? AND PRODID = ? ";

	private static InventoryDao instance;
	
	private InventoryDaoImpl() {
		
	}

	public static InventoryDao getInstance() {
		if (instance == null) {
			instance = new InventoryDaoImpl();
		}
		return instance;
	}
	
	@Override
	public void create(Connection connection, Inventory inv, Integer userId) throws SQLException, DaoException {
		if (inv.getInvnId() != null) {
			throw new DaoException("InvnId must be null!");
		}
		if (userId == null) {
			throw new DaoException("UserId cannot be null!");
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, userId);
			statement.executeUpdate();
			rs = statement.getGeneratedKeys();
			rs.next();
			inv.setInvnId(rs.getInt(1));
		} finally {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
		}
	}

	@Override
	public Inventory retrieveByUser(Connection connection, Integer userId) throws SQLException, DaoException {
		if (userId == null) {
			throw new DaoException("UserId cannot be null!");
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveQuery);
			statement.setInt(1, userId);
			rs = statement.executeQuery();
			boolean found = rs.next();
			if (!found) {
				return null;
			}
			Inventory invn = InventoryPersistenceServiceImpl.getInstance().getInventory();
			invn.setInvnId(rs.getInt(1));
			return invn;
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
	public void addProduct(Connection connection, Integer prodId, Integer invnId) throws SQLException, DaoException {
		if (prodId == null) {
			throw new DaoException("ProdId cannot be null!");
		}
		if (invnId == null) {
			throw new DaoException("InvnId cannot be null!");
		}
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(addProductQuery);
			statement.setInt(1, invnId);
			statement.setInt(2, prodId);
			statement.executeUpdate();
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
		}
	}

	@Override
	public void removeProduct(Connection connection, Integer prodId, Integer invnId) throws SQLException, DaoException {
		if (prodId == null) {
			throw new DaoException("ProdId cannot be null!");
		}
		if (invnId == null) {
			throw new DaoException("InvnId cannot be null!");
		}
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(removeProductQuery);
			statement.setInt(1, invnId);
			statement.setInt(2, prodId);
			statement.executeUpdate();
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
		}
	}

}
