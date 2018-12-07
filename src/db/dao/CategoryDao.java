package db.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import domain.product.Category;

public interface CategoryDao {

	Category retrieve(Connection connection, Integer catId) throws SQLException, DaoException;

	Category retrieveByProduct(Connection connection, Integer prodId) throws SQLException, DaoException;

	List<Category> retrieveAll(Connection connection) throws SQLException, DaoException;

}
