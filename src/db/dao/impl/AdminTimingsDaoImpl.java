package db.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.dao.AdminTimingsDao;
import db.dao.DaoException;
import domain.user.AuctionTimings;

public class AdminTimingsDaoImpl implements AdminTimingsDao {
	private static final String findTimings = 
			"SELECT a.auctionDate, a.startTime, a.endTime FROM AuctionTimings a "
			+ "where a.auctionDate = ?";
	
	private static final String findDefaultTimings = 
			"SELECT a.auctionDate, a.startTime, a.endTime FROM AuctionTimings a "
			+ "where a.auctionDate is null";
	
	private static AdminTimingsDao instance;
	
	private AdminTimingsDaoImpl() {
		
	}

	public static AdminTimingsDao getInstance() {
		if (instance == null) {
			instance = new AdminTimingsDaoImpl();
		}
		return instance;
	}
	
	@SuppressWarnings("resource")
	@Override
	public AuctionTimings getTimings(Connection connection, Date date) throws SQLException, DaoException{
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(findTimings);
			statement.setDate(1, date);
			rs = statement.executeQuery();
			
			boolean found = rs.next();
			if (!found) {
				statement = connection.prepareStatement(findDefaultTimings);
				rs = statement.executeQuery();
				found = rs.next();
			}
			
			AuctionTimings auctiontimings = findTime(rs);
			return auctiontimings;
			
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}
	
	private static AuctionTimings findTime(ResultSet rs) throws SQLException {
		AuctionTimings auctionTimings = new AuctionTimings();
		
		auctionTimings.setAuctionDate(rs.getDate(1));
		auctionTimings.setStartTime(rs.getString(2));
		
		auctionTimings.setEndTime(rs.getString(3));
		
		return auctionTimings;
	}
	
}
