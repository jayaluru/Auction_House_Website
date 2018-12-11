package db.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;

import db.dao.AdminTimingsDao;
import db.dao.DaoException;
import db.dao.ProductDao;
import domain.product.Product;
import domain.product.ProductBid;
import domain.user.AuctionTimings;

public class ProductDaoImpl implements ProductDao {
	private static final String placeBid = 
			"INSERT INTO "
			+ "PRODUCTBID (prodId, userId, bid) "
			+ "VALUES (?, ?, ?)";
	
	private static final String findHighestBid = 
			"select p.prodId, p.userId, p.bid from ProductBid p where "
			+ "p.bid = (select max(p1.bid) from ProductBid p1 where p1.prodId = p.prodId) "
			+ "and p.prodId = ?";

	private static final String createQuery = 
			"INSERT INTO "
			+ "PRODUCT (NAME, DESCRIPTION, PRICE, ISSOLD,PHOTO,bidStartTime,bidEndTime,bidDate) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
	private static final String retrieveQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD,p.bidDate,p.PHOTO "
			+ "FROM PRODUCT p "
			+ "WHERE p.PRODID = ? ";

	private static final String retrieveAllQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD,p.bidDate,p.PHOTO "
			+ "FROM PRODUCT p ";
	
	private static final String retrieveByTransactionQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, p.bidDate, p.PHOTO "
			+ "FROM PRODUCT p "
			+ "JOIN TRANSACTIONPRODUCT tp ON p.PRODID = tp.PRODID "
			+ "WHERE tp.TRXNID = ? ";
	
	private static final String retrieveBySellerQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, p.bidDate, p.PHOTO "
			+ "FROM PRODUCT p "
			+ "JOIN INVENTORYPRODUCT ip ON p.PRODID = ip.PRODID "
			+ "JOIN INVENTORY i ON ip.INVNID = i.INVNID "
			+ "WHERE i.USERID = ? ";

	private static final String retrieveByCartQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, p.bidDate, p.PHOTO "
			+ "FROM PRODUCT p "
			+ "JOIN CARTPRODUCT cp ON p.PRODID = cp.PRODID "
			+ "WHERE cp.CARTID = ? ";

	private static final String retrieveByInventoryQuery = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, p.bidDate, p.PHOTO "
			+ "FROM PRODUCT p "
			+ "JOIN INVENTORYPRODUCT ip ON p.PRODID = ip.PRODID "
			+ "WHERE ip.INVNID = ? ";
	
	private static final String updateQuery = 
			"UPDATE PRODUCT "
			+ "SET NAME = ?, "
			+ "DESCRIPTION = ?, "
			+ "PRICE = ?, "
			+ "ISSOLD = ?, "
			+ "bidDate = ?, "
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
	
	private static final String retrieveProductByDate = 
			"SELECT "
			+ "p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD, p.bidDate, p.PHOTO, "
			+ "p.bidStartTime, p.bidEndTime FROM PRODUCT p "
			+ "WHERE p.bidDate = ?";
	
	private static final String retrieveProductBids = 
			"SELECT "
			+ "PRODID, USERID, BID, "
			+ "FROM PRODUCTBID"
			+ "WHERE PRODID = ?";
	
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
	public void createBid(Connection connection, ProductBid productBid) throws SQLException, DaoException {
		if (productBid.getProdId() == null) {
			throw new DaoException("ProdId must not be null!");
		}

		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(placeBid, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, productBid.getProdId());
			statement.setInt(2, productBid.getUserId());
			statement.setDouble(3, productBid.getPrice());
			statement.executeUpdate();
		} finally {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
		}
	}
	
	
	@Override
	public ProductBid getHighestBid(Connection connection, Integer prodId) throws SQLException, DaoException {
		if (prodId == null) {
			throw new DaoException("ProdId must not be null!");
		}
		
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(findHighestBid);
			statement.setInt(1, prodId);
			rs = statement.executeQuery();
			boolean found = rs.next();
			if (!found) {
				return null;
			}
			ProductBid productBid = buildProductBid(rs);
			return productBid;
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
	public List<ProductBid> getAllBids(Connection connection, Integer prodId) throws SQLException, DaoException {
		if (prodId == null) {
			throw new DaoException("ProdId must not be null!");
		}
		
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveProductBids);
			statement.setInt(1, prodId);
			rs = statement.executeQuery();
			
			ArrayList<ProductBid> productBids = new ArrayList<ProductBid>();

			while (rs.next()) {
				ProductBid productbid = buildProductBid(rs);
				productBids.add(productbid);
			}
			return productBids;
			
			//boolean found = rs.next();
			//if (!found) {
			//	return null;
			//}
			//ProductBid productBid = buildProductBid(rs);
			//return productBids;
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
	public void create(Connection connection, Product product) throws SQLException, DaoException {
		if (product.getProdId() != null) {
			throw new DaoException("ProdId must be null!");
		}

		PreparedStatement statement = null;
		ResultSet rs = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, product.getName());
			statement.setString(2, product.getDescription());
			statement.setDouble(3, product.getPrice());
			statement.setBoolean(4, product.isSold());
			statement.setString(6, product.getBidStartTime());
			statement.setString(7, product.getBidEndTime());
			statement.setDate(8, product.getBidDate());
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
			statement.setBlob(5, blob);
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
			statement.setDate(5, prod.getBidDate());
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
			statement.setBlob(6, blob);
			statement.setInt(7, prod.getProdId());
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

	private static Product buildProduct1(ResultSet rs) throws SQLException {
		// p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD
		Product product = new Product();
		product.setProdId(rs.getInt(1));
		product.setName(rs.getString(2));
		product.setDescription(rs.getString(3));
		product.setPrice(rs.getDouble(4));
		product.setSold(rs.getBoolean(5));
		product.setBidStartTime(rs.getString(7));
		product.setBidEndTime(rs.getString(8));
		try {
			product.setImage(ImageIO.read(rs.getBlob(6).getBinaryStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return product;
	}
	
	private static Product buildProduct(ResultSet rs) throws SQLException {
		// p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD
		Product product = new Product();
		product.setProdId(rs.getInt(1));
		product.setName(rs.getString(2));
		product.setDescription(rs.getString(3));
		product.setPrice(rs.getDouble(4));
		product.setSold(rs.getBoolean(5));
		Date date = rs.getDate(6);
		product.setBidDate(date);
		try {
			product.setImage(ImageIO.read(rs.getBlob(7).getBinaryStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return product;
	}
	
	private static ProductBid buildProductBid(ResultSet rs) throws SQLException {
		// p.PRODID, p.NAME, p.DESCRIPTION, p.PRICE, p.ISSOLD
		ProductBid productBid = new ProductBid();
		productBid.setProdId(rs.getInt(1));
		productBid.setUserId(rs.getInt(2));
		productBid.setPrice(rs.getDouble(3));
		
		return productBid;
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
	
	@SuppressWarnings("resource")
	@Override
	public Product retrieveCurrrentAuctionProduct(Connection connection) throws SQLException, DaoException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveProductByDate);
			Date date = new Date(System.currentTimeMillis());
			statement.setDate(1, date);
			rs = statement.executeQuery();
			
			boolean flag = false;
			while (rs.next()) {
				Product product = buildProduct1(rs);
				if(product.getBidStartTime().equals("")) {
					flag = true;
				}
				break;
			}
			
			if(flag) {
				setBidTimings(connection);
			}
			
			statement = connection.prepareStatement(retrieveProductByDate);
			date = new Date(System.currentTimeMillis());
			statement.setDate(1, date);
			rs = statement.executeQuery();
			
			Calendar rightNow = Calendar.getInstance();
			String h = rightNow.get(Calendar.HOUR_OF_DAY)+"";
			String m = rightNow.get(Calendar.MINUTE)+"";
			
			if(h.length()==1) {
				h="0"+h;
			}
			
			if(m.length()==1) {
				m="0"+m;
			}
			
			String h_m = h + ":" + m;
			
			while (rs.next()) {
				Product product = buildProduct1(rs);
				System.out.println();
				if(h_m.compareTo(product.getBidStartTime())>=0 && h_m.compareTo(product.getBidEndTime())<0) {
					return product;
				}
			}
			
			return null;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}

	private void setBidTimings(Connection connection) throws SQLException, DaoException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveProductByDate);
			Date date = new Date(System.currentTimeMillis());
			statement.setDate(1, date);
			rs = statement.executeQuery();
			
			
			AdminTimingsDao adminDao = AdminTimingsDaoImpl.getInstance();
			AuctionTimings auctionTimings = adminDao.getTimings(connection, date);
			
			String start = auctionTimings.getStartTime();
			String end = auctionTimings.getEndTime();
			while (rs.next()) {
				Product product = buildProduct1(rs);
				
				product.setBidStartTime(start);
				product.setBidEndTime(end);
				update(connection, product);
				
				start = end;
				end = incrementTime(end);
			}
			
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}
	
	private String incrementTime(String end) {
		int hour = Integer.parseInt(end.split(":")[0]);
		int min = Integer.parseInt(end.split(":")[1]);
		
		min = min+15;
		if(min>15) {
			min=min-60;
			hour+=1;
		}
		
		String m = min + "";
		String h = hour + "";
		if(m.length()==1) {
			m='0'+m;
		}
		if(h.length()==1) {
			h='0'+h;
		}
		
		return h + ":" + m;
	}

}
