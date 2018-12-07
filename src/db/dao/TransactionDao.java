package db.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import domain.transaction.Transaction;

public interface TransactionDao {

	public void create(Connection connection, Transaction transaction, Integer userId)
			throws SQLException, DaoException;

	public Transaction retrieve(Connection connection, Integer trxnId) throws SQLException, DaoException;

	public List<Transaction> retrieveByUser(Connection connection, Integer userId) throws SQLException, DaoException;

}
