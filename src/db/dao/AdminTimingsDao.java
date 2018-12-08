package db.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface AdminTimingsDao {
	public String[] getTimings(Connection connection) throws SQLException, DaoException;
}
