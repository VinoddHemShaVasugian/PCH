package com.pch.kenofrontend.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import com.pch.kenofrontend.utilities.PropertiesReader;

/**
 * @author pvadivelu Dec 16, 2016
 *
 */
public class DatabaseHelper {

	private static PropertiesReader dbConnection = PropertiesReader.getInstance();
	public static Connection lottobackendDatabaseConnection = null;
	int queryTimeout = 10;

	// Create Lotto Backend Database connection
	/**
	 * @return
	 * @throws SQLException
	 */
	public static Connection getlottoBackEndDatabaseConnection() throws SQLException {

		if (lottobackendDatabaseConnection == null) {
			/*
			try {
				Class.forName(dbConnection.lottoContestAdminDbDriverName);

				if (lottobackendDatabaseConnection == null) {
					lottobackendDatabaseConnection = DriverManager
							.getConnection(
									dbConnection.lottoContestAdminDbConnectionString,
									dbConnection.lottoContestAdminDbUserName,
									dbConnection.lottoContestAdminDbPassword);
				}
			} catch (SQLException e) {
				System.out
				.println("Failed to make Lotto Back end Database Connection!");
				e.printStackTrace();
			}
			catch(ClassNotFoundException e){
				System.out
				.println("Failed to make Lotto Back end Database Connection!");
				e.printStackTrace();
			}*/
		}
		return lottobackendDatabaseConnection;
	}


	/*
	 * Execute SP with parameters in LottoBackend DB
	 */
	public ResultSet getQueryResultFromLottoBackendDB(String storedProcString) {

		ResultSet resultSet = null;
		CallableStatement callableStatement = null;

		try {

			callableStatement = getlottoBackEndDatabaseConnection().prepareCall(storedProcString);
			callableStatement.setQueryTimeout(queryTimeout);

			resultSet = callableStatement.executeQuery();

			SQLWarning warning = callableStatement.getWarnings();

			while (warning != null)
			{
				System.out.println(warning.getMessage());
				warning = warning.getNextWarning();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}


	//Update Query
	public int updateQueryinLottoBackendDB(String updateQuery){
		Statement statement = null;
		int rowsUpdated = 0;
		try {

			statement = getlottoBackEndDatabaseConnection().createStatement();
			statement.setQueryTimeout(queryTimeout);
			rowsUpdated = statement.executeUpdate(updateQuery);

		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return rowsUpdated;		
	}



}
