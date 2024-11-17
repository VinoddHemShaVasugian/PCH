package com.pch.quiz.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.pch.quiz.utilities.AppConfigLoader;

public class DatabaseHelper {

	/** The Constant dbDriverName. */
	public final static String dbDriverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	/** The Constant queryTimeout. */
	public final static int queryTimeout = 30;

	/** The database connection. */
	public static Connection databaseConnection = null;
	private final AppConfigLoader configLoaderInstance = AppConfigLoader.getInstance();
	private static DatabaseHelper dbInstance;

	/**
	 * Gets the database connection.
	 *
	 * @param connectionString the connection string
	 * @return the database connection
	 * @throws SQLException the SQL exception
	 */
	// Create Database Connection
	public static Connection getDatabaseConnection(String connectionString) throws SQLException {
		closedDBConnection();
		try {
			Class.forName(dbDriverName);
			databaseConnection = DriverManager.getConnection(connectionString);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Failed to make Database Connection!");
			e.printStackTrace();
		}
		return databaseConnection;
	}

	public Connection getDatabaseConnection() throws SQLException {
		closedDBConnection();
		try {
			Class.forName(dbDriverName);
			databaseConnection = DriverManager.getConnection(
					configLoaderInstance.getEnvironmentProperty("DBConnectionString"),
					configLoaderInstance.getEnvironmentProperty("DBUsername"),
					configLoaderInstance.getEnvironmentProperty("DBPassword"));
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Failed to make Database Connection!");
			e.printStackTrace();
		}
		return databaseConnection;
	}

	/**
	 * Closed DB connection.
	 *
	 * @throws SQLException the SQL exception
	 */
	// Close the DB Connection
	public static void closedDBConnection() throws SQLException {
		if (databaseConnection != null && !databaseConnection.isClosed())
			try {
				databaseConnection.close();
				System.out.println("Is Connection Closed : " + databaseConnection.isClosed());
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Will execute the query and return the respective column value
	 * 
	 * @param connectionString
	 * @param storedProcString
	 * @return
	 * @throws SQLException
	 */
	public String executeQuery(String queryString) throws SQLException {
		ResultSet result = null;
		Statement stmt = null;
		System.out.println("Select Query String: " + queryString);
		try {
			stmt = getDatabaseConnection().createStatement();
			stmt.setQueryTimeout(queryTimeout);
			result = stmt.executeQuery(queryString);
			if (result.next()) {
				return result.getString(1);
			} else {
				return "";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closedDBConnection();
		}
		return "";
	}

	/**
	 * Will execute the update query
	 * 
	 * @param queryString
	 * @return
	 * @throws SQLException
	 */
	public int updateQuery(String queryString) throws SQLException {
		Statement stmt = null;
		System.out.println("Update Query String: " + queryString);
		try {
			stmt = getDatabaseConnection().createStatement();
			stmt.setQueryTimeout(queryTimeout);
			return stmt.executeUpdate(queryString);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closedDBConnection();
		}
		return 0;
	}

	/**
	 * Execute the SQL query and return the result set value of all the column as a
	 * Map for all the rows
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public LinkedList<LinkedHashMap<String, String>> getMulitpleRowsAndColumnValues(String query) throws SQLException {
		Statement stmt = null;
		LinkedList<LinkedHashMap<String, String>> resultsetMultipleRow = new LinkedList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> columnValueMap = null;
		try {
			// Execute the SQL Query. Store results in ResultSet
			stmt = getDatabaseConnection().createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnCount = rsmd.getColumnCount();
			String columnName;
			String columnValue;
			while (resultSet.next()) {
				columnValueMap = new LinkedHashMap<String, String>();
				for (int i = 1; i <= columnCount; i++) {
					columnName = rsmd.getColumnName(i).trim();
					columnValue = resultSet.getString(i);
					columnValue = columnValue == null ? "" : columnValue;
					columnValueMap.put(columnName, columnValue);
				}
				resultsetMultipleRow.add(columnValueMap);
			}
		} catch (SQLException SQL) {
			System.out.println("Error in retreiving the mulitple row vlaues for the user:" + SQL.getSQLState());
		} finally {
			System.out.println("Closing the DB connection");
			closedDBConnection();
		}
		return resultsetMultipleRow;
	}

	/**
	 * Execute the SQL query and return the result set value of all the column as a
	 * Map for a row
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public LinkedHashMap<String, String> getMulitpleColumnValues(String query) throws SQLException {
		Statement stmt = null;
		LinkedHashMap<String, String> columnValueMap = new LinkedHashMap<String, String>();
		try {
			stmt = getDatabaseConnection().createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnCount = rsmd.getColumnCount();
			String columnName;
			String columnValue;
			while (resultSet.next()) {
				for (int i = 1; i <= columnCount; i++) {
					columnName = rsmd.getColumnName(i).trim();
					columnValue = resultSet.getString(i);
					columnValue = columnValue == null ? "" : columnValue;
					columnValueMap.put(columnName, columnValue);
				}
			}
		} catch (SQLException SQL) {
			System.out.println("Error in retreiving the mulitple column vlaues for the user:" + SQL.getSQLState());
		} finally {
			System.out.println("Closing the DB connection");
			closedDBConnection();
		}
		return columnValueMap;
	}

	/**
	 * Singleton method to instantiate one instance for the run
	 * 
	 * @return DatabaseHelper instance
	 */
	public static DatabaseHelper getInstance() {
		if (dbInstance == null) {
			dbInstance = new DatabaseHelper();
		}
		return dbInstance;
	}

}
