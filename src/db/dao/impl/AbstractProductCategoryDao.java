package db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.dao.DaoException;
import db.dao.ProductCategoryDao;
import domain.product.Product;

public abstract class AbstractProductCategoryDao<T extends Product> implements ProductCategoryDao<T> {
	
	private final String createQuery;
	private final String retrieveQuery;
	private final String retrieveAllQuery;
	private final String updateQuery;
	private final String deleteQuery;
	
	protected AbstractProductCategoryDao(String createQuery, String retrieveQuery, String retrieveAllQuery, String updateQuery, String deleteQuery) {
		this.createQuery = createQuery;
		this.retrieveQuery = retrieveQuery;
		this.retrieveAllQuery = retrieveAllQuery;
		this.updateQuery = updateQuery;
		this.deleteQuery = deleteQuery;
	}
	
	@Override
	public void create(Connection connection, T product) throws SQLException, DaoException {
		if (product.getProdId() == null) {
			throw new DaoException("ProdId cannot be null!");
		}

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(createQuery);
			statement.setInt(1, product.getProdId());
			createStatement(2, statement, product);
			statement.executeUpdate();
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
		}
	}

	@Override
	public T retrieve(Connection connection, Integer prodId) throws SQLException, DaoException {
		if (prodId == null) {
			throw new DaoException("ProdId cannot be null!");
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveQuery);
			statement.setInt(1, prodId);
			rs = statement.executeQuery();
			boolean found = rs.next();
			if (!found) {
				return null;
			}
			T product = build(rs);
			return product;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}

	@Override
	public List<T> retrieveAll(Connection connection) throws SQLException, DaoException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(retrieveAllQuery);
			rs = statement.executeQuery();
			ArrayList<T> prods = new ArrayList<T>();

			while (rs.next()) {
				T prod = build(rs);
				prods.add(prod);
			}
			return prods;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		}
	}

	@Override
	public int update(Connection connection, T product) throws SQLException, DaoException {
		if (product.getProdId() == null) {
			throw new DaoException("ProdId cannot be null!");
		}

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(updateQuery);
			int index = createStatement(1, statement, product);
			statement.setInt(index, product.getProdId());
			int result = statement.executeUpdate();
			if (result != 1) {
				throw new DaoException("Unable to update product: " + result + " rows updated!");
			}
			return result;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
		}
	}

	@Override
	public int delete(Connection connection, T product) throws SQLException, DaoException {
		if (product.getProdId() == null) {
			throw new DaoException("ProdId cannot be null!");
		}

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(deleteQuery);
			statement.setInt(1, product.getProdId());
			int result = statement.executeUpdate();
			if (result != 1) {
				throw new DaoException("Unable to delete product!");
			}
			return result;
		} finally {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
		}
	}

	protected abstract T build(ResultSet rs) throws SQLException;
	
	protected abstract int createStatement(int index, PreparedStatement statement, T product) throws SQLException;
	
}
