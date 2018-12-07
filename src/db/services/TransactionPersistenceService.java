package db.services;

import java.sql.SQLException;
import java.util.List;

import db.dao.DaoException;
import domain.transaction.Transaction;

public interface TransactionPersistenceService {

	public void create(Transaction trxn, Integer userId) throws SQLException, DaoException;

	public Transaction retrieve(Integer trxnId) throws SQLException, DaoException;

	public List<Transaction> retrieveByUser(Integer userId) throws SQLException, DaoException;

	public Transaction getTransaction();
	
}
