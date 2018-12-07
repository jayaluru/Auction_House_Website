package db.services;

import java.sql.SQLException;
import java.util.List;

import db.dao.DaoException;
import domain.product.Product;

public interface ProductCategoryPersistenceService<T extends Product> {

	public void create(T product, Integer invnId) throws SQLException, DaoException;

	public List<T> retrieveAll() throws SQLException, DaoException;

	public T retrieve(Integer prodId) throws SQLException, DaoException;
	
	public int update(T product) throws SQLException, DaoException;
	
	public int delete(T product) throws SQLException, DaoException;
	
	public T getProd();

}
