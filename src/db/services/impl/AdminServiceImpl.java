package db.services.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import db.DbManager;
import db.dao.AdminTimingsDao;
import db.dao.DaoException;
import db.dao.impl.AdminTimingsDaoImpl;
import db.services.AdminService;
import domain.user.AuctionTimings;
import domain.user.User;

public class AdminServiceImpl implements AdminService{
	private DbManager db = DbManager.getInstance();
	private AdminTimingsDao adminDao = AdminTimingsDaoImpl.getInstance();
	
	private static AdminService instance;
	
	private AdminServiceImpl() {
		
	}
	
	public static AdminService getInstance() {
		if (instance == null) {
			instance = new AdminServiceImpl();
		}
		return instance;
	}
	
	@Override
	public String retrieveByUsername(String username) throws SQLException, DaoException {
		Connection connection = db.getConnection();

		try {
			connection.setAutoCommit(false);
			String password  = adminDao.retrieveByUsername(connection, username);
			
			connection.commit();
			return password;
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			connection.rollback();
			throw ex;
		} finally {
			if (connection != null) {
				connection.setAutoCommit(true);
				if (!connection.isClosed()) {
					connection.close();
				}
			}
		}
	}
	
	@Override
	public int update(AuctionTimings auctionTimings) throws SQLException, DaoException {
		
		Connection connection = db.getConnection();
		try {
			connection.setAutoCommit(false);

			int count = adminDao.update(connection, auctionTimings);
			
			connection.commit();
			return count;
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			connection.rollback();
			throw ex;
		} finally {
			if (connection != null) {
				connection.setAutoCommit(true);
				if (!connection.isClosed()) {
					connection.close();
				}
			}
		}
	}
	
	
	@Override
	public AuctionTimings getTimings(Date date) throws SQLException, DaoException {
		
		Connection connection = db.getConnection();
		try {
			connection.setAutoCommit(false);

			AuctionTimings auctionTimings = adminDao.getTimings(connection, date);
			
			connection.commit();
			return auctionTimings;
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			connection.rollback();
			throw ex;
		} finally {
			if (connection != null) {
				connection.setAutoCommit(true);
				if (!connection.isClosed()) {
					connection.close();
				}
			}
		}
	}
}
