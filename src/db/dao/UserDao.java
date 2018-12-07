package db.dao;

import java.sql.Connection;
import java.sql.SQLException;

import domain.user.User;

public interface UserDao {

	public void create(Connection conn, User user) throws SQLException, DaoException;

	public User retrieve(Connection conn, Integer id) throws SQLException, DaoException;
	
	public User retrieveByUsername(Connection conn, String username) throws SQLException, DaoException;
	
	public User retrieveByProduct(Connection conn, Integer prodId) throws SQLException, DaoException;

	public int update(Connection conn, User user) throws SQLException, DaoException;

}
