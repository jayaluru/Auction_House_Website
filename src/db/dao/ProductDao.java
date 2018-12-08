package db.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import domain.product.Product;
import domain.product.ProductBid;

public interface ProductDao extends ProductCategoryDao<Product> {
	
	public void createBid(Connection connection, ProductBid productBid) throws SQLException, DaoException;
	
	public ProductBid getHighestBid(Connection connection, Integer prodId) throws SQLException, DaoException;

	public List<Product> retrieveByTransaction(Connection connection, Integer trxnId) throws SQLException, DaoException;

	public List<Product> retrieveBySeller(Connection connection, Integer sellerId) throws SQLException, DaoException;

	public List<Product> retrieveByInventory(Connection connection, Integer invnId) throws SQLException, DaoException;
	
	public Integer retrieveInventoryId(Connection connection, Product prod) throws SQLException, DaoException;
	
	public Integer retrieveSellerId(Connection connection, Product prod) throws SQLException, DaoException;
	
	public Product retrieveCurrrentAuctionProduct(Connection connection) throws SQLException, DaoException;
}
