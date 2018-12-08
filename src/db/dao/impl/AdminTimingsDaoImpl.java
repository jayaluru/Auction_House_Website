package db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.dao.AdminTimingsDao;
import db.dao.DaoException;

public class AdminTimingsDaoImpl implements AdminTimingsDao {
	private static final String findTimings = 
			"SELECT a.maxItems, a.startTime, a.endTime FROM AuctionTimings a";
	
	private static AdminTimingsDao instance;
	
	private AdminTimingsDaoImpl() {
		
	}

	public static AdminTimingsDao getInstance() {
		if (instance == null) {
			instance = new AdminTimingsDaoImpl();
		}
		return instance;
	}
	
	@Override
	public String[] getTimings(Connection connection) throws SQLException, DaoException{
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(findTimings);
			rs = statement.executeQuery();
			
			boolean found = rs.next();
			if (!found) {
				return null;
			}
			String[] auctiontimings = findTime(rs);
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
	
	private static String[] findTime(ResultSet rs) throws SQLException {
		String[] auctiontimings = new String[3];
		auctiontimings[0] = rs.getInt(1)+"";
		auctiontimings[1] = rs.getString(2);
		auctiontimings[2] = rs.getString(3);
		
		return auctiontimings;
	}
	
}
