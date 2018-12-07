package db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import db.dao.DaoException;
import db.dao.UserDao;
import db.services.impl.UserPersistenceServiceImpl;
import domain.user.User;

public class UserDaoImpl implements UserDao {

	private static final String registerQuery = 
			"INSERT INTO "
			+ "USER (USERNAME, PASSWORD, NAME, ADDRESS, DESCRIPTION, ACTIVE) "
			+ "VALUES (?, ?, ?, ?, ?, ?) ";

	private static final String retrieveQuery = 
			"SELECT "
			+ "USERID, USERNAME, PASSWORD, NAME, ADDRESS, DESCRIPTION, ACTIVE "
			+ "FROM USER "
			+ "WHERE USERID = ? ";
	
	private static final String retrieveByUsernameQuery = 
			"SELECT "
			+ "USERID, USERNAME, PASSWORD, NAME, ADDRESS, DESCRIPTION, ACTIVE "
			+ "FROM USER "
			+ "WHERE USERNAME = ? ";
	
	private static final String updateQuery = 
			"UPDATE USER "
			+ "SET USERNAME = ?, "
			+ "PASSWORD = ?, "
			+ "NAME = ?, "
			+ "ADDRESS = ?,"
			+ "DESCRIPTION = ?, "
			+ "ACTIVE = ? "
			+ "WHERE USERID = ?";
	
	private static final String retrieveByProductIdQuery = 
			"SELECT "
			+ "u.USERID, u.USERNAME, u.PASSWORD, u.NAME, u.ADDRESS, u.DESCRIPTION, u.ACTIVE "
			+ "FROM INVENTORYPRODUCT ip "
			+ "JOIN INVENTORY i "
			+ "ON i.INVNID = ip.INVNID "
			+ "JOIN USER u "
			+ "ON u.USERID = i.USERID "
			+ "WHERE ip.PRODID = ? ";

	private static UserDao instance;
	
	private UserDaoImpl() {
		
	}

	public static UserDao getInstance() {
		if (instance == null) {
			instance = new UserDaoImpl();
		}
		return instance;
	}
	
	@Override
	public void create(Connection conn, User user) throws SQLException, DaoException {
		if (user.getUserId() != null) {
			throw new DaoException("UserId must be null on register!");
		}

		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(registerQuery, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getName());
			statement.setString(4, user.getAddress());
			statement.setString(5, user.getDescription());
			statement.setBoolean(6, user.isActive());

			int result = statement.executeUpdate();
			if (result != 1) {
				throw new DaoException("User was unable to be created!");
			}

			ResultSet key = statement.getGeneratedKeys();
			key.next();
			user.setUserId(key.getInt(1));
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
		}
	}

	@Override
	public User retrieveByUsername(Connection conn, String username) throws SQLException, DaoException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = conn.prepareStatement(retrieveByUsernameQuery);
			statement.setString(1, username);
			rs = statement.executeQuery();
			boolean found = rs.next();
			if (!found) {
				return null;
			}
			User user = buildUser(rs);
			return user;
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
	public User retrieveByProduct(Connection conn, Integer prodId) throws SQLException, DaoException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = conn.prepareStatement(retrieveByProductIdQuery);
			statement.setInt(1, prodId);
			rs = statement.executeQuery();
			boolean found = rs.next();
			if (!found) {
				return null;
			}
			User user = buildUser(rs);
			return user;
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
	public User retrieve(Connection conn, Integer id) throws SQLException, DaoException {
		if (id == null) {
			throw new DaoException("UserId cannot be null!");
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = conn.prepareStatement(retrieveQuery);
			statement.setInt(1, id);
			rs = statement.executeQuery();
			boolean found = rs.next();
			if (!found) {
				return null;
			}
			User user = buildUser(rs);
			return user;
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
	public int update(Connection conn, User user) throws SQLException, DaoException {
		if (user.getUserId() == null) {
			throw new DaoException("UserId cannot be null!");
		}

		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(updateQuery);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getName());
			statement.setString(4, user.getAddress());
			statement.setString(5, user.getDescription());
			statement.setBoolean(6, user.isActive());
			statement.setInt(7, user.getUserId());
			int result = statement.executeUpdate();
			if (result != 1) {
				throw new DaoException("Unable to update user!");
			}
			return result;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
		}
	}

	private static User buildUser(ResultSet rs) throws SQLException {
		User user = UserPersistenceServiceImpl.getInstance().getUser();
		user.setUserId(rs.getInt(1));
		user.setUsername(rs.getString(2));
		user.setPassword(rs.getString(3));
		user.setName(rs.getString(4));
		user.setAddress(rs.getString(5));
		user.setDescription(rs.getString(6));
		user.setActive(rs.getBoolean(7));
		return user;
	}

}
