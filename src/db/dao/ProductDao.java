package db.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import domain.product.Product;

public interface ProductDao extends ProductCategoryDao<Product> {

	public List<Product> retrieveByTransaction(Connection connection, Integer trxnId) throws SQLException, DaoException;

	public List<Product> retrieveBySeller(Connection connection, Integer sellerId) throws SQLException, DaoException;

	public List<Product> retrieveByCart(Connection connection, Integer cartId) throws SQLException, DaoException;

	public List<Product> retrieveByInventory(Connection connection, Integer invnId) throws SQLException, DaoException;
	
	public Integer retrieveInventoryId(Connection connection, Product prod) throws SQLException, DaoException;
	
	public Integer retrieveSellerId(Connection connection, Product prod) throws SQLException, DaoException;
}
