package db.services;

import java.sql.SQLException;
import java.util.List;

import db.dao.DaoException;
import domain.product.Product;

public interface ProductPersistenceService {

	public Product retrieve(Integer prodId) throws SQLException, DaoException;

	public List<Product> retrieveAll() throws SQLException, DaoException;
	
}
