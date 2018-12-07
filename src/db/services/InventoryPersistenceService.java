package db.services;

import java.sql.SQLException;

import db.dao.DaoException;
import domain.user.Inventory;

public interface InventoryPersistenceService {

	public Inventory retrieve(Integer userId) throws SQLException, DaoException;

	public Inventory getInventory();
}
