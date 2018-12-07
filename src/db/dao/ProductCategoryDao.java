package db.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import domain.product.Product;

public interface ProductCategoryDao<T extends Product> {

	public void create(Connection connection, T product) throws SQLException, DaoException;

	public T retrieve(Connection connection, Integer prodId) throws SQLException, DaoException;

	public List<T> retrieveAll(Connection connection) throws SQLException, DaoException;
	
	public int update(Connection connection, T product) throws SQLException, DaoException;
	
	public int delete(Connection connection, T product) throws SQLException, DaoException;

}
