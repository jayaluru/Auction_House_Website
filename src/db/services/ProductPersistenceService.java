package db.services;

import java.sql.SQLException;
import java.util.List;

import db.dao.DaoException;
import domain.product.Product;
import domain.product.ProductBid;

public interface ProductPersistenceService {
	public ProductBid placeBid(ProductBid productBid) throws SQLException, DaoException;
	
	public void create(Product prod, Integer invnId) throws SQLException, DaoException;
	
	public void delete(Product prod, Integer invnId) throws SQLException, DaoException;

	public Product retrieve(Integer prodId) throws SQLException, DaoException;

	public List<Product> retrieveAll() throws SQLException, DaoException;
	
	public Product retrieveCurrrentAuctionProduct() throws SQLException, DaoException;
}
