package db.services;

import java.sql.SQLException;
import java.util.List;

import db.dao.DaoException;
import domain.product.Category;

public interface CategoryPersistenceService {

	public Category retrieve(Integer catId) throws SQLException, DaoException;
	
	public Category retrieveByProduct(Integer prodId) throws SQLException, DaoException;
	
	public List<Category> retrieveAll() throws SQLException, DaoException;
	
	public Category getCategory();
	
}
