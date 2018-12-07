package db.dao;

import java.sql.Connection;
import java.sql.SQLException;

import domain.user.CreditCard;

public interface CreditCardDao {

	void create(Connection connection, CreditCard creditCard, Integer userId) throws SQLException, DaoException;

	CreditCard retrieve(Connection connection, Integer cardId) throws SQLException, DaoException;

	CreditCard retrieveByUser(Connection connection, Integer userId) throws SQLException, DaoException;

}
