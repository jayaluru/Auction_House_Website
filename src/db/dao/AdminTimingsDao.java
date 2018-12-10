package db.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import domain.user.AuctionTimings;
import domain.user.User;

public interface AdminTimingsDao {
	public AuctionTimings getTimings(Connection connection, Date date) throws SQLException, DaoException;
	
	public String retrieveByUsername(Connection conn, String username) throws SQLException, DaoException;
	
	public int update(Connection conn, AuctionTimings auctionTimings) throws SQLException, DaoException;
}
