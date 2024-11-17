/*
 * @Author pvadivelu
 * PCH Search and Win and Front Page
 */
package com.pch.automation.database;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.pch.automation.utilities.AppConfigLoader;
import com.pch.automation.utilities.WebServiceClient;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabaseHelper.
 */
public class DatabaseHelper {

	/** The Constant dbDriverName. */
	public final static String dbDriverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	/** The Constant queryTimeout. */
	public final static int queryTimeout = 30;

	/** The database connection. */
	public static Connection databaseConnection = null;
	private final AppConfigLoader configLoaderInstance = AppConfigLoader.getInstance();
	private static DatabaseHelper dbInstance;

	WebServiceClient webServiceClientInstance = WebServiceClient.getInstance();

	/**
	 * Gets the database connection.
	 *
	 * @param connectionString
	 *            the connection string
	 * @return the database connection
	 * @throws SQLException
	 *             the SQL exception
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
	 * @throws SQLException
	 *             the SQL exception
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
	 * Execute query and return more result set from DB.
	 *
	 * @param connectionString
	 *            the connection string
	 * @param storedProcString
	 *            the stored proc string
	 * @return the result set
	 */
	/*
	 * Execute Query in Database and Return Result Set
	 */
	public ResultSet executeQueryandReturnMoreResultSetfromDB(String storedProcString) {
		ResultSet result = null;
		CallableStatement stmt = null;
		try {
			stmt = getDatabaseConnection().prepareCall(storedProcString, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.setQueryTimeout(queryTimeout);
			boolean isContainsResultSet = stmt.execute();
			if (isContainsResultSet)
				result = stmt.executeQuery();
			System.out.println(stmt.getMoreResults());
			result = stmt.getResultSet();
			System.out.println(result);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
		} finally {
			// closedDBConnection();
		}
		return result;
	}

	/**
	 * Execute queryand return result setfrom DB.
	 *
	 * @param connectionString
	 *            the connection string
	 * @param storedProcString
	 *            the stored proc string
	 * @return the result set
	 */
	public ResultSet executeQueryandReturnResultSetfromDB(String connectionString, String storedProcString) {
		ResultSet result = null;
		CallableStatement stmt = null;
		try {
			stmt = getDatabaseConnection(connectionString).prepareCall(storedProcString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setQueryTimeout(queryTimeout);
			boolean isContainsResultSet = stmt.execute();
			if (isContainsResultSet)
				result = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
		} finally {
			// closedDBConnection();
		}
		return result;
	}

	/**
	 * Execute queryand return single result setfrom DB.
	 *
	 * @param connectionString
	 *            the connection string
	 * @param storedProcString
	 *            the stored proc string
	 * @return the result set
	 */
	public ResultSet executeQueryandReturnSingleResultSetfromDB(String connectionString, String storedProcString) {
		ResultSet result = null;
		CallableStatement stmt = null;
		try {
			stmt = getDatabaseConnection(connectionString).prepareCall(storedProcString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setQueryTimeout(queryTimeout);
			stmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
		} finally {
			// closedDBConnection();
		}
		return result;
	}

	/**
	 * Execute queryfrom DB.
	 *
	 * @param connectionString
	 *            the connection string
	 * @param storedProcString
	 *            the stored proc string
	 */
	/*
	 * Execute Query in Database
	 */
	public void executeQueryfromDB(String connectionString, String storedProcString) {
		CallableStatement Stmt = null;
		try {
			Stmt = getDatabaseConnection(connectionString).prepareCall(storedProcString);
			Stmt.setQueryTimeout(queryTimeout);
			Stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// closedDBConnection();
		}
	}

	/**
	 * Execute queryfrom DB.
	 *
	 * @param connectionString
	 *            the connection string
	 * @param storedProcString
	 *            the stored proc string
	 * @param variables
	 *            the variables
	 */
	/*
	 * Execute Query with parameters in Database
	 */
	public void executeQueryfromDB(String connectionString, String storedProcString, List<String> variables) {
		CallableStatement Stmt = null;
		try {

			Stmt = getDatabaseConnection(connectionString).prepareCall(storedProcString);
			for (int i = 0; i < variables.size(); i++) {
				Stmt.setObject(i + 1, variables.get(i));
			}
			Stmt.setQueryTimeout(queryTimeout);
			Stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// closedDBConnection();
		}
	}

	/**
	 * Execute queryand return result setfrom DB.
	 *
	 * @param connectionString
	 *            the connection string
	 * @param storedProcString
	 *            the stored proc string
	 * @param variables
	 *            the variables
	 * @return the result set
	 */
	/*
	 * Execute Query with parameters in Database and Return Result Set
	 */
	public ResultSet executeQueryandReturnResultSetfromDB(String connectionString, String storedProcString,
			List<String> variables) {
		ResultSet result = null;
		CallableStatement stmt = null;
		try {
			stmt = getDatabaseConnection(connectionString).prepareCall(storedProcString);
			for (int i = 0; i < variables.size(); i++) {
				stmt.setObject(i + 1, variables.get(i));
			}
			stmt.setQueryTimeout(queryTimeout);
			result = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// closedDBConnection();
		}
		return result;
	}

	/**
	 * Execute query and return count from DB.
	 *
	 * @param connectionString
	 *            the connection string
	 * @param storedProcString
	 *            the stored proc string
	 * @return the int
	 * @throws SQLException
	 *             the SQL exception
	 */
	public int executeQueryandReturnCountfromDB(String storedProcString) throws SQLException {
		ResultSet result = null;
		Statement stmt = null;
		int count = 0;
		try {
			stmt = getDatabaseConnection().createStatement();
			stmt.setQueryTimeout(queryTimeout);
			result = stmt.executeQuery(storedProcString);
			while (result.next()) {
				count = result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closedDBConnection();
		}
		return count;
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
	 * Update the Daily search count to given parameter value
	 * 
	 * @param userEmail
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public int updateDailySearchCount(String userEmail, String searchCount) throws SQLException, IOException {

		// To get the User Id of the user via OAT and Email by User API.
		String userId = webServiceClientInstance.getUserIdFromEmail(userEmail);
		String query = "update sso_user_data set value = '" + searchCount + "' where user = "+userId+" and item like '%count';";
		return updateQuery(query);
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
		LinkedList<LinkedHashMap<String, String>> resultset_multiple_row = new LinkedList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> column_value_map = null;
		try {
			// Execute the SQL Query. Store results in ResultSet
			stmt = getDatabaseConnection().createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int column_count = rsmd.getColumnCount();
			String column_name;
			String column_value;
			while (resultSet.next()) {
				column_value_map = new LinkedHashMap<String, String>();
				for (int i = 1; i <= column_count; i++) {
					column_name = rsmd.getColumnName(i).trim();
					column_value = resultSet.getString(i);
					column_value = column_value == null ? "" : column_value;
					column_value_map.put(column_name, column_value);
				}
				resultset_multiple_row.add(column_value_map);
			}
		} catch (SQLException SQL) {
			System.out.println("Error in retreiving the mulitple row vlaues for the user:" + SQL.getSQLState());
		} finally {
			System.out.println("Closing the DB connection");
			closedDBConnection();
		}
		return resultset_multiple_row;
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
		LinkedHashMap<String, String> column_value_map = new LinkedHashMap<String, String>();
		try {
			stmt = getDatabaseConnection().createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int column_count = rsmd.getColumnCount();
			String column_name;
			String column_value;
			while (resultSet.next()) {
				for (int i = 1; i <= column_count; i++) {
					column_name = rsmd.getColumnName(i).trim();
					column_value = resultSet.getString(i);
					column_value = column_value == null ? "" : column_value;
					column_value_map.put(column_name, column_value);
				}
			}
		} catch (SQLException SQL) {
			System.out.println("Error in retreiving the mulitple column vlaues for the user:" + SQL.getSQLState());
		} finally {
			System.out.println("Closing the DB connection");
			closedDBConnection();
		}
		return column_value_map;
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
