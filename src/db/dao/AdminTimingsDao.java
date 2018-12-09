package db.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import domain.user.AuctionTimings;

public interface AdminTimingsDao {
	public AuctionTimings getTimings(Connection connection, Date date) throws SQLException, DaoException;
}
