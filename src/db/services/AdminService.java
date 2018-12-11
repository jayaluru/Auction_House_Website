package db.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import db.dao.DaoException;
import domain.user.AuctionTimings;

public interface AdminService {
	
	public AuctionTimings getTimings(Date date) throws SQLException, DaoException;
	
	public String retrieveByUsername(String username) throws SQLException, DaoException;
	
	public int update(AuctionTimings auctionTimings) throws SQLException, DaoException;
}
