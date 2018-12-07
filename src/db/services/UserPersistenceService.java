package db.services;

import java.sql.SQLException;

import db.dao.DaoException;
import domain.user.User;

public interface UserPersistenceService {

	public void create(User user) throws SQLException, DaoException;

	public User retrieve(Integer id) throws SQLException, DaoException;

	public User retrieveByUsername(String username) throws SQLException, DaoException;
	
	public User retrieveByProduct(Integer prodId) throws SQLException, DaoException;
	
	public int update(User user) throws SQLException, DaoException;
	
	public User getUser();

}
