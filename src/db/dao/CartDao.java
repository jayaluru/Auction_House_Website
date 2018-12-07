package db.dao;

import java.sql.Connection;
import java.sql.SQLException;

import domain.user.Cart;

public interface CartDao {

	public void create(Connection connection, Cart cart, Integer userId) throws SQLException, DaoException;

	public Cart retrieveByUser(Connection connection, Integer userId) throws SQLException, DaoException;

	public int update(Connection connection, Cart cart) throws SQLException, DaoException;

	public int removeProductFromAllCarts(Connection connection, Integer prodId) throws SQLException, DaoException;
	
}
