package db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.dao.CreditCardDao;
import db.dao.DaoException;
import domain.user.CreditCard;

public class CreditCardDaoImpl implements CreditCardDao {

	private static final String createQuery = 
			"INSERT INTO "
			+ "CREDITCARD (USERID, NUMBER, EXPDATE, CVV) "
			+ "VALUES (?, ?, ?, ?) ";

	private static final String retrieveQuery = 
			"SELECT "
			+ "CARDID, NUMBER, EXPDATE, CVV "
			+ "FROM CREDITCARD "
			+ "WHERE CARDID = ? ";
	
	private static final String retrieveByUserQuery = 
			"SELECT "
			+ "CARDID, NUMBER, EXPDATE, CVV "
			+ "FROM CREDITCARD "
			+ "WHERE USERID = ? ";
	
	private static CreditCardDao instance;
	
	private CreditCardDaoImpl() {
		
	}

	public static CreditCardDao getInstance() {
		if (instance == null) {
			instance = new CreditCardDaoImpl();
		}
		return instance;
	}
	
	@Override
	public void create(Connection connection, CreditCard creditCard, Integer userId) throws SQLException, DaoException {
		if (creditCard.getCardId() != null) {
			throw new DaoException("CardId must be null!");
		}

		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, userId);
			statement.setString(2, creditCard.getNumber());
			statement.setDate(3, creditCard.getExpDate());
			statement.setString(4, creditCard.getCvv());
			statement.executeUpdate();
			rs = statement.getGeneratedKeys();
			rs.next();
			creditCard.setCardId(rs.getInt(1));
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
	public CreditCard retrieve(Connection connection, Integer cardId) throws SQLException, DaoException {
		if (cardId == null) {
			throw new DaoException("CardId cannot be null!");
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveQuery);
			statement.setInt(1, cardId);
			rs = statement.executeQuery();
			boolean found = rs.next();
			if (!found) {
				return null;
			}
			CreditCard card = buildCreditCard(rs);
			return card;
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
	public CreditCard retrieveByUser(Connection connection, Integer userId) throws SQLException, DaoException {
		if (userId == null) {
			throw new DaoException("UserId cannot be null!");
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveByUserQuery);
			statement.setInt(1, userId);
			rs = statement.executeQuery();
			boolean found = rs.next();
			if (!found) {
				return null;
			}
			CreditCard card = buildCreditCard(rs);
			return card;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}

	private CreditCard buildCreditCard(ResultSet rs) throws SQLException {
		CreditCard card = new CreditCard();
		card.setCardId(rs.getInt(1));
		card.setNumber(rs.getString(2));
		card.setExpDate(rs.getDate(3));
		card.setCvv(rs.getString(4));
		return card;
	}

}
