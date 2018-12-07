package db.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import db.dao.DaoException;
import db.dao.ProductDao;
import domain.product.Product;

public class ProductDaoImpl implements ProductDao {

	private static final String createQuery = 
			"INSERT INTO "
			+ "PRODUCT (CATID, NAME, DESCRIPTION, PRICE, ISSOLD,PHOTO) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
		
	private static final String retrieveQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD,p.PHOTO "
			+ "FROM PRODUCT p "
			+ "WHERE p.PRODID = ? ";

	private static final String retrieveAllQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD,p.PHOTO "
			+ "FROM PRODUCT p ";
	
	private static final String retrieveByTransactionQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, p.PHOTO "
			+ "FROM PRODUCT p "
			+ "JOIN TRANSACTIONPRODUCT tp ON p.PRODID = tp.PRODID "
			+ "WHERE tp.TRXNID = ? ";
	
	private static final String retrieveBySellerQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, p.PHOTO "
			+ "FROM PRODUCT p "
			+ "JOIN INVENTORYPRODUCT ip ON p.PRODID = ip.PRODID "
			+ "JOIN INVENTORY i ON ip.INVNID = i.INVNID "
			+ "WHERE i.USERID = ? ";

	private static final String retrieveByCartQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, p.PHOTO "
			+ "FROM PRODUCT p "
			+ "JOIN CARTPRODUCT cp ON p.PRODID = cp.PRODID "
			+ "WHERE cp.CARTID = ? ";

	private static final String retrieveByInventoryQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, p.PHOTO "
			+ "FROM PRODUCT p "
			+ "JOIN INVENTORYPRODUCT ip ON p.PRODID = ip.PRODID "
			+ "WHERE ip.INVNID = ? ";
	
	private static final String updateQuery = 
			"UPDATE PRODUCT "
			+ "SET NAME = ?, "
			+ "DESCRIPTION = ?, "
			+ "PRICE = ?, "
			+ "ISSOLD = ?, "
			+ "PHOTO = ? "
			+ "WHERE PRODID = ? ";
	
	private static final String deleteQuery = 
			"DELETE FROM "
			+ "PRODUCT "
			+ "WHERE PRODID = ? ";
	
	private static final String retrieveInventoryIdQuery = 
			"SELECT "
			+ "ip.INVNID "
			+ "FROM INVENTORYPRODUCT ip "
			+ "WHERE ip.PRODID = ? ";

	private static final String retrieveSellerIdQuery = 
			"SELECT "
			+ "i.USERID "
			+ "FROM INVENTORYPRODUCT ip "
			+ "JOIN INVENTORY i ON ip.INVNID = i.INVNID "
			+ "WHERE ip.PRODID = ? ";

	private static ProductDao instance;
	
	private ProductDaoImpl() {
		
	}

	public static ProductDao getInstance() {
		if (instance == null) {
			instance = new ProductDaoImpl();
		}
		return instance;
	}
	
	@Override
	public void create(Connection connection, Product product) throws SQLException, DaoException {
		if (product.getProdId() != null) {
			throw new DaoException("ProdId must be null!");
		}

		PreparedStatement statement = null;
		ResultSet rs = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, product.getCategory().getCatId());
			statement.setString(2, product.getName());
			statement.setString(3, product.getDescription());
			statement.setDouble(4, product.getPrice());
			statement.setBoolean(5, product.isSold());
			try {
				ImageIO.write(product.getImage(), "jpg", baos);
				baos.flush();
			} catch (IOException e) {
				e.printStackTrace();
				throw new DaoException("Unable to save image to db.");
			}
			byte[] imageBytes = baos.toByteArray();
			Blob blob = connection.createBlob();
			blob.setBytes(1, imageBytes);
			statement.setBlob(6, blob);
			statement.executeUpdate();
			rs = statement.getGeneratedKeys();
			rs.next();
			product.setProdId(rs.getInt(1));
		} finally {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Product retrieve(Connection connection, Integer prodId) throws SQLException, DaoException {
		if (prodId == null) {
			throw new DaoException("ProdId cannot be null!");
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveQuery);
			statement.setInt(1, prodId);
			rs = statement.executeQuery();
			boolean found = rs.next();
			if (!found) {
				return null;
			}
			Product product = buildProduct(rs);
			return product;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}

	@Override
	public List<Product> retrieveAll(Connection connection) throws SQLException, DaoException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveAllQuery);
			rs = statement.executeQuery();
			ArrayList<Product> products = new ArrayList<Product>();

			while (rs.next()) {
				Product product = buildProduct(rs);
				products.add(product);
			}
			return products;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}

	@Override
	public List<Product> retrieveByTransaction(Connection connection, Integer trxnId)
			throws SQLException, DaoException {
		if (trxnId == null) {
			throw new DaoException("TrxnId cannot be null!");
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveByTransactionQuery);
			statement.setInt(1, trxnId);
			rs = statement.executeQuery();
			ArrayList<Product> products = new ArrayList<Product>();
			while (rs.next()) {
				Product product = buildProduct(rs);
				products.add(product);
			}
			return products;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}

	@Override
	public List<Product> retrieveBySeller(Connection connection, Integer sellerId) throws SQLException, DaoException {
		if (sellerId == null) {
			throw new DaoException("SellerId cannot be null!");
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveBySellerQuery);
			statement.setInt(1, sellerId);
			rs = statement.executeQuery();
			ArrayList<Product> products = new ArrayList<Product>();
			while (rs.next()) {
				Product product = buildProduct(rs);
				products.add(product);
			}
			return products;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}

	@Override
	public List<Product> retrieveByCart(Connection connection, Integer cartId) throws SQLException, DaoException {
		if (cartId == null) {
			throw new DaoException("CartId cannot be null!");
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveByCartQuery);
			statement.setInt(1, cartId);
			rs = statement.executeQuery();
			ArrayList<Product> products = new ArrayList<Product>();
			while (rs.next()) {
				Product product = buildProduct(rs);
				products.add(product);
			}
			return products;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}

	@Override
	public List<Product> retrieveByInventory(Connection connection, Integer invnId) throws SQLException, DaoException {
		if (invnId == null) {
			throw new DaoException("InvnId cannot be null!");
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveByInventoryQuery);
			statement.setInt(1, invnId);
			rs = statement.executeQuery();
			ArrayList<Product> products = new ArrayList<Product>();
			while (rs.next()) {
				Product product = buildProduct(rs);
				products.add(product);
			}
			return products;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}

	@Override
	public int update(Connection conn, Product prod) throws SQLException, DaoException {
		if (prod.getProdId() == null) {
			throw new DaoException("ProdId cannot be null!");
		}

		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(updateQuery);
			statement.setString(1, prod.getName());
			statement.setString(2, prod.getDescription());
			statement.setDouble(3, prod.getPrice());
			statement.setBoolean(4, prod.isSold());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(prod.getImage(), "jpg", baos);
			} catch (IOException e) {
				e.printStackTrace();
				throw new DaoException("Unable to save image to db.");
			}
			byte[] imageBytes = baos.toByteArray();
			Blob blob = conn.createBlob();
			blob.setBytes(1, imageBytes);
			statement.setBlob(5, blob);
			statement.setInt(6, prod.getProdId());
			int result = statement.executeUpdate();
			if (result != 1) {
				throw new DaoException("Unable to update product: " + result + " rows changed!");
			}
			return result;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
		}
	}
	
	@Override
	public int delete(Connection connection, Product product) throws SQLException, DaoException {
		if (product.getProdId() == null) {
			throw new DaoException("ProdId cannot be null!");
		}

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(deleteQuery);
			statement.setInt(1, product.getProdId());
			int result = statement.executeUpdate();
			if (result != 1) {
				throw new DaoException("Unable to delete product!");
			}
			return result;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
		}
	}

	private static Product buildProduct(ResultSet rs) throws SQLException {
		// p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD
		Product product = new Product();
		product.setProdId(rs.getInt(1));
		product.setName(rs.getString(2));
		product.setDescription(rs.getString(3));
		product.setPrice(rs.getDouble(4));
		product.setSold(rs.getBoolean(5));
		try {
			product.setImage(ImageIO.read(rs.getBlob(6).getBinaryStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return product;
	}

	@Override
	public Integer retrieveInventoryId(Connection connection, Product prod) throws SQLException, DaoException {
		if (prod.getProdId() == null) {
			throw new DaoException("ProdId cannot be null!");
		}

		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveInventoryIdQuery);
			statement.setInt(1, prod.getProdId());
			rs = statement.executeQuery();
			boolean found = rs.next();
			if (!found) {
				return null;
			}
			Integer invnId = rs.getInt(1);
			return invnId;
			
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}

	@Override
	public Integer retrieveSellerId(Connection connection, Product prod) throws SQLException, DaoException {
		if (prod.getProdId() == null) {
			throw new DaoException("ProdId cannot be null!");
		}

		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveSellerIdQuery);
			statement.setInt(1, prod.getProdId());
			rs = statement.executeQuery();
			boolean found = rs.next();
			if (!found) {
				return null;
			}
			Integer sellerId = rs.getInt(1);
			return sellerId;
			
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}

}
