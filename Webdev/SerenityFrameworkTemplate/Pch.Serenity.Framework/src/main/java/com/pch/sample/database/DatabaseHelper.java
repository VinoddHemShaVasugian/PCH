/*
 * @Author pvadivelu
 * @Version 1.0.0
 * PCH Serenity Framework Template
 */
package com.pch.sample.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


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

	/**
	 * Gets the database connection.
	 *
	 * @param connectionString the connection string
	 * @return the database connection
	 * @throws SQLException the SQL exception
	 */
	//Create Database Connection
	public static Connection getDatabaseConnection(String connectionString) throws SQLException{		
		closedDBConnection();		
		try {
			Class.forName(dbDriverName);
			databaseConnection = DriverManager
					.getConnection(
							connectionString);
		}
		catch (SQLException | ClassNotFoundException e) {
			System.out
			.println("Failed to make DataDatabaseConnection!");
			e.printStackTrace();
		}
		return databaseConnection;
	}


	/**
	 * Closed DB connection.
	 *
	 * @throws SQLException the SQL exception
	 */
	//Close the DB Connection
	public static void closedDBConnection() throws SQLException{
		if(databaseConnection != null && !databaseConnection.isClosed())
			try {
				databaseConnection.close();
				System.out.println("Is Connection Closed : "+databaseConnection.isClosed());
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}


	/**
	 * Execute queryand return more result setfrom DB.
	 *
	 * @param connectionString the connection string
	 * @param storedProcString the stored proc string
	 * @return the result set
	 */
	/*
	 * Execute Query in Database and Return Result Set
	 */
	public ResultSet executeQueryandReturnMoreResultSetfromDB(String connectionString, String storedProcString) {
		ResultSet result = null;
		CallableStatement stmt = null;
		try {
			stmt = getDatabaseConnection(connectionString).prepareCall(storedProcString,ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY);
			stmt.setQueryTimeout(queryTimeout);
			boolean isContainsResultSet=stmt.execute();
			if(isContainsResultSet)
			result = stmt.executeQuery();
			System.out.println(stmt.getMoreResults());
			result=stmt.getResultSet();
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
		}finally {
			//closedDBConnection();
		}
		return result;
	}
	
	/**
	 * Execute queryand return result setfrom DB.
	 *
	 * @param connectionString the connection string
	 * @param storedProcString the stored proc string
	 * @return the result set
	 */
	public ResultSet executeQueryandReturnResultSetfromDB(String connectionString, String storedProcString) {
		ResultSet result = null;
		CallableStatement stmt = null;
		try {
			stmt = getDatabaseConnection(connectionString).prepareCall(storedProcString,ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY);
			stmt.setQueryTimeout(queryTimeout);
			boolean isContainsResultSet=stmt.execute();
			if(isContainsResultSet)
			result = stmt.executeQuery();
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
		}finally {
			//closedDBConnection();
		}
		return result;
	}
	
	/**
	 * Execute queryand return single result setfrom DB.
	 *
	 * @param connectionString the connection string
	 * @param storedProcString the stored proc string
	 * @return the result set
	 */
	public ResultSet executeQueryandReturnSingleResultSetfromDB(String connectionString, String storedProcString) {
		ResultSet result = null;
		CallableStatement stmt = null;
		try {
			stmt = getDatabaseConnection(connectionString).prepareCall(storedProcString,ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY);
			stmt.setQueryTimeout(queryTimeout);
			stmt.execute();
			
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
		}finally {
			//closedDBConnection();
		}
		return result;
	}

	/**
	 * Execute queryfrom DB.
	 *
	 * @param connectionString the connection string
	 * @param storedProcString the stored proc string
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
		}finally {
			//closedDBConnection();
		}
	}



	/**
	 * Execute queryfrom DB.
	 *
	 * @param connectionString the connection string
	 * @param storedProcString the stored proc string
	 * @param variables the variables
	 */
	/*
	 * Execute Query with parameters in Database
	 */
	public void executeQueryfromDB(
			String connectionString, String storedProcString, List<String> variables) {
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
		}finally {
			//closedDBConnection();
		}
	}

	/**
	 * Execute queryand return result setfrom DB.
	 *
	 * @param connectionString the connection string
	 * @param storedProcString the stored proc string
	 * @param variables the variables
	 * @return the result set
	 */
	/*
	 * Execute Query with parameters in Database and Return Result Set
	 */
	public ResultSet executeQueryandReturnResultSetfromDB(
			String connectionString, String storedProcString, List<String> variables) {
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
		}finally {
			//closedDBConnection();
		}
		return result;
	}


	/**
	 * Execute queryand return countfrom DB.
	 *
	 * @param connectionString the connection string
	 * @param storedProcString the stored proc string
	 * @return the int
	 * @throws SQLException the SQL exception
	 */
	/*
	 * Execute Query in Database and Return Result Set Count
	 */
	public int executeQueryandReturnCountfromDB(
			String connectionString, String storedProcString) throws SQLException {
		ResultSet result = null;
		Statement stmt = null;
		int count = 0;
		try {
			stmt = getDatabaseConnection(connectionString).createStatement();
			stmt.setQueryTimeout(queryTimeout);
			result = stmt.executeQuery(storedProcString);
			while(result.next())			{			
				count = result.getInt(1);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//closedDBConnection();
		}
		return count;
	}


	
}
