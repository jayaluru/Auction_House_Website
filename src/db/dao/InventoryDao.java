package db.dao;

import java.sql.Connection;
import java.sql.SQLException;

import domain.user.Inventory;

public interface InventoryDao {

	public void create(Connection connection, Inventory inv, Integer userId) throws SQLException, DaoException;

	public Inventory retrieveByUser(Connection connection, Integer userId) throws SQLException, DaoException;

	public void addProduct(Connection connection, Integer prodId, Integer invnId) throws SQLException, DaoException;

	public void removeProduct(Connection connection, Integer prodId, Integer invnId) throws SQLException, DaoException;
	
}
