package com.pch.search.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.testng.Assert;

public class DBUtils {

	private static Connection con = null;
	private static ResultSet MYSQLresultSet = null;

	private static Connection getMySqlJDBCConnection() {
		if (Environment.getEnvironment().equals("stg"))
			if (Environment.getAppName().equals("FrontPage")) {
				return getMySqlJDBCConnection("frontpage_stg");
			} else {
				return getMySqlJDBCConnection("search_stg");
			}

		else {
			if (Environment.getAppName().equals("FrontPage")) {
				return getMySqlJDBCConnection("frontpage_qa");
			} else {
				return getMySqlJDBCConnection("search_qa");
			}
		}
	}

	private static Connection getMySqlJDBCConnection(String dataBaseName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}

		try {
			con = DriverManager.getConnection(Environment.getMySqlJdbc() + dataBaseName + "?user="
					+ Environment.getMySqlUsername() + "&password=" + Environment.getMySqlPassword());
			con.setAutoCommit(false);
			CustomLogger.log("Got MySql Connection.");
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

		return con;
	}

	private static Connection getSqlServerJDBCConnection(String dataBaseName) {
		try {
			CustomLogger.log("Connecting to sql server");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}
		try {
			if (Environment.getEnvironment().equalsIgnoreCase("QA")) {
				con = DriverManager.getConnection(
						Environment.getSqlServerJdbc() + dataBaseName + ";user=" + Environment.getSqlServerUsername()
								+ ";password=" + Environment.getSqlServerPassword() + ";integratedSecurity=true;");
				con.setAutoCommit(false);
				CustomLogger.log("Got Connected to Sql Server.");
			} else {
				con = DriverManager.getConnection(Environment.getSqlServerJdbc() + dataBaseName + ";user="
						+ Environment.getSqlServerUsername() + ";password=" + Environment.getSqlServerPassword() + ";");
				con.setAutoCommit(false);
				CustomLogger.log("Got Connected to Sql Server.");
			}
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

		return con;
	}

	private static Connection getSqlServerJDBCConnection() {
		return getSqlServerJDBCConnection(Environment.getParam("SqlDataBase"));
	}

	public static Connection getconnected() {
		return getSqlServerJDBCConnection();
	}

	public static ResultSet executeSQLQuery(String query) throws SQLException {
		// Connection con=getMySqlJDBCConnection();
		Connection con = getconnected();
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		CustomLogger.log("Executing Query : " + query);
		ResultSet rs = stmt.executeQuery(query);
		return rs;
	}

	/**
	 * Executes the following commands <br>
	 * UPDATE sandw_sso_user_data SET value=0 WHERE user='newtester@pchmail.com'
	 * and property like '%count'; <br>
	 * UPDATE sandw_sso_user_data set expires = 1417018729 where
	 * user='newtester@pchmail.com';
	 * 
	 * @param userName
	 * @throws SQLException
	 */
	public static void expireDailySearch(String userEmail) throws SQLException {
		CustomLogger.log("Updating DB: Expire Daily search for: " + userEmail);

		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(userEmail);
		Long timeStamp = new java.util.Date().getTime() / 1000L;
		String query = "update sandw_sso_user_data set value = 0, expires = " + timeStamp + " where user = '" + user_id
				+ "' and item like '%count'";
		executeQuery(query);
		CustomLogger.log(String.format("Daily Search expired for %s.", user_id));
	}

	/**
	 * Executes the following commands <br>
	 * update sandw_sso_user_data set value='2014-06-07' where
	 * user='newtester@pchmail.com' and property='registration_date'; <br>
	 * UPDATE sandw_sso_user_data set expires = 0 where
	 * user='newtester@pchmail.com' and property='registration_date'
	 * 
	 * @param userName
	 * @throws SQLException
	 */
	public static void updateRegistrationDate(String userEmail, String dateStr) throws SQLException {

		CustomLogger.log("Updating DB: updating Registration Date for: " + userEmail);
		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(userEmail);
		String query = "update sandw_sso_user_data set value = '" + dateStr + "', expires = 0 where user = '" + user_id
				+ "' and item = 'registration_date'";
		executeQuery(query);
		CustomLogger.log(String.format("Registration date updated for %s to %s", userEmail, dateStr));
	}

	public static void updateLastVisitDate(String userEmail, String dateStr) throws SQLException {

		CustomLogger.log("Updating DB: updating Lst Visit Date for: " + userEmail);
		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(userEmail);
		String query = "update sandw_sso_user_data set value = '" + dateStr + "', expires = 0 where user = '" + user_id
				+ "' and item = 'consecutivevisits'";
		executeQuery(query);
		CustomLogger.log(String.format("ConsecutiveVisits date updated for %s to %s", userEmail, dateStr));
	}

	public static void expirePasswordResetLink(String userEmail) throws SQLException {

		getSqlServerJDBCConnection();
		// Further steps
	}

	public static void updateSearchCount(String userEmail, int count) throws SQLException {
		CustomLogger.log("Updating DB: updating Search Count for: " + userEmail);
		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(userEmail);
		String query = "update sandw_sso_user_data set value = '" + count + "' where user = '" + user_id
				+ "' and item = 'webdailyhrsearchcount'";

		// String query = String.format(
		// "update sandw_sso_properties set value=%d where user='%s' and
		// property = 'webdailyhrsearchcount' ",
		// count, userEmail);
		executeQuery(query);
		CustomLogger.log(String.format("webdailyhrsearchcount date updated for %s to %s", userEmail, count));
	}

	public static void updateSearchCount_GuestUser(String userEmail, int count) throws SQLException {
		CustomLogger.log("Updating DB: updating Search Count for: " + userEmail);
		String query = String.format(
				"update sandw_sso_properties_unrec set value=%d where user='%s' and property = 'webdailyhrsearchcount' ",
				count, userEmail);
		executeQuery(query);
		CustomLogger.log(String.format("webdailyhrsearchcount date updated for %s to %s", userEmail, count));
	}

	/**
	 * This function will update property = 'dailysearchcount' property of
	 * 'sandw_sso_user_data' table,
	 */
	public static void updateDailySearchCount(String userEmail, int count) throws SQLException {
		CustomLogger.log("Updating DB: updating Daily Search Count for: " + userEmail);
		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(userEmail);
		String query = "update sandw_sso_user_data set value = '" + count + "' where user = '" + user_id
				+ "' and item = 'dailysearchcount'";
		executeQuery(query);
		CustomLogger.log(String.format("dailysearchcount date updated for %s to %s", userEmail, count));
	}

	/**
	 * This function will update property = 'firstsearch' property of
	 * 'sandw_sso_properties' table,
	 */
	public static void updateFirstSearchCount(String userEmail, String value) throws SQLException {
		CustomLogger.log("Updating DB: updating First Search Count for: " + userEmail);
		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(userEmail);
		String query = "update sandw_sso_user_data set value = '" + value + "' where user = '" + user_id
				+ "' and item = 'firstsearch'";
		executeQuery(query);
		CustomLogger.log(String.format("firstsearch count updated for %s to %s", userEmail, value));
	}

	/**
	 * This function will validate the search counts for user from
	 * 'sandw_sso_properties' table,
	 */
	public static void validateSearchCount(String userEmail, String searchcount, int expectedSearchCount)
			throws SQLException {
		Connection con = null;
		Statement stmt = null;
		try {
			con = getMySqlJDBCConnection();
			stmt = con.createStatement();

			// To get the User Id of the user via OAT and Email by User API.
			String user_id = getUserIdFromEmail(userEmail);
			String sql = "select * from sandw_sso_user_data where user='" + user_id + "' and item='" + searchcount
					+ "'";
			// String sql = "select * from sandw_sso_properties where user='" +
			// userEmail + "' and property='"
			// + searchcount + "'";
			CustomLogger.log("Executing query - " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int actualCount = rs.getInt("value");

				// Display values
				Assert.assertEquals(actualCount, expectedSearchCount);
				CustomLogger.log(searchcount + " value from table: " + actualCount + " is as expected.");

			}
			rs.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					con.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (con != null)
					con.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		CustomLogger.log("Database connection closed successfully!");
	}

	public static String executeQueryAndVerifyValue(String Query, String Field){
		String fieldvalue = "";
		try {
			ExecuteMYSQLQuery(Query);
			fieldvalue = GetFieldValueAsString(MYSQLresultSet, Field);
			CustomLogger.log("Field value in DB is ::" + Field);
			return fieldvalue;

		} catch (Exception e) {
			e.printStackTrace();
		}
	 finally {
		// finally block used to close resources
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // end try
	CustomLogger.log("Database connection closed successfully!");
		return "";
		
	}
	
	// Method to get the Filed value of Table column in String
		private static String GetFieldValueAsString(ResultSet resultSet, String ColumnName) throws SQLException {
			if (resultSet.next()) {
				// System.out.println("Field Value is: " +
				// resultSet.getString(ColumnName));
				return resultSet.getString(ColumnName);
			} else {
				return "";
			}
		}
	
	// Method to Run the Query after database connection
		private static ResultSet ExecuteMYSQLQuery(String Query) throws SQLException {
	
			con = getMySqlJDBCConnection();
			
			System.out.println("Select Query to be Executed is: " + Query);
			MYSQLresultSet = con.createStatement().executeQuery(Query);
			return MYSQLresultSet;
		}

	
	/**
	 * This function will validate the search counts for user from
	 * 'sandw_sso_properties_unrec' table,
	 */
	public static void validateSearchCountGuestUser(String userEmail, String searchcount, int expectedSearchCount)
			throws SQLException {
		Connection con = null;
		Statement stmt = null;
		try {
			con = getMySqlJDBCConnection();
			stmt = con.createStatement();

			String sql = "select * from sandw_sso_properties_unrec where user='" + userEmail + "' and property='"
					+ searchcount + "'";
			CustomLogger.log("Executing query - " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int actualCount = rs.getInt("value");

				// Display values
				Assert.assertEquals(actualCount, expectedSearchCount);
				CustomLogger.log(searchcount + " value from table: " + actualCount + " is as expected.");

			}
			rs.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					con.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (con != null)
					con.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		CustomLogger.log("Database connection closed successfully!");
	}

	public static void updateLastLogin(String userEmail, long value) throws SQLException {
		CustomLogger.log("Updating DB: updating Last Login Date for: " + userEmail);
		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(userEmail);
		String query = "update sandw_sso_user_data set value = '" + value + "' where user = '" + user_id
				+ "' and item = 'lastlogin'";
		executeQuery(query);
		CustomLogger.log(String.format("Last login updated for %s to %s", userEmail, value));
	}

	private static void executeQuery(String query) throws SQLException {
		PreparedStatement ps = null;
		// try{
		Connection con = getMySqlJDBCConnection();
		con.setAutoCommit(false);
		ps = con.prepareStatement(query);
		CustomLogger.log("Executing query - " + ps);
		ps.executeUpdate();
		con.commit();
		// }catch (SQLException sqe){
		// CustomLogger.logException(sqe);
		// con.rollback();
		// }finally{
		if (ps != null) {
			ps.close();
		}
		con.setAutoCommit(true);
		con.close();
	}

	public static ResultSet executeQueryAndReturnResultSet(String query) throws SQLException {
		Connection con = getMySqlJDBCConnection();
		Statement stmnt = con.createStatement();
		CustomLogger.log("Executing query - " + query);
		ResultSet rs = stmnt.executeQuery(query);
		return rs;
	}

	/**
	 * This function will update property = 'Segments' property of
	 * 'sandw_sso_properties' table,
	 */
	public static void updateSegmentsProperty(String userEmail, String value) throws SQLException {
		CustomLogger.log("Updating DB: updating Segments for: " + userEmail);
		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(userEmail);
		String query = "update sandw_sso_user_data set value = '" + value + "' where user = '" + user_id
				+ "' and item = 'segments'";

		// String query = String.format(
		// "UPDATE sandw_sso_properties SET value='%s' WHERE user='%s' AND
		// property='segments';", value,
		// userEmail);
		executeQuery(query);
		CustomLogger.log(String.format("Segments updated for %s to %s", userEmail, value));
	}

	/**
	 * The below method is to update property = 'videoplayer_37' of
	 * 'sandw_sso_properties' table
	 **/
	public static void updateVideoPlayerProperty(String userEmail, String value) throws SQLException {
		CustomLogger.log("Updating DB: updating Video Player property for: " + userEmail);
		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(userEmail);
		String query = "update sandw_sso_user_data set value = '" + value + "' where user = '" + user_id
				+ "' and item = 'videoplayer_37'";

		// String query = String.format(
		// "UPDATE sandw_sso_properties SET value = '%s' WHERE user = '%s' AND
		// property = 'videoplayer_37';",
		// value, userEmail);
		executeQuery(query);
		CustomLogger.log("value field updated for user " + userEmail + " to " + value);
	}

	/**
	 * This function will delete property = 'lottooptin' of
	 * 'sandw_sso_properties' table,
	 */
	public static void deleteLottoOptinProperty(String userEmail) throws SQLException {
		CustomLogger.log("Updating DB: updating Video Player property for: " + userEmail);
		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(userEmail);
		String query = "delete from sandw_sso_user_data where user = '" + user_id + "' and item = 'lottooptin'";

		// String query = String.format("DELETE FROM sandw_sso_properties WHERE
		// user='%s' AND property='lottooptin';",
		// userEmail);
		executeQuery(query);
		CustomLogger.log(String.format("Deleted Lotto Optin property for %s", userEmail));
	}

	public static void executeSqlQuery(String query) throws SQLException {
		PreparedStatement ps = null;
		// try{
		Connection con = getSqlServerJDBCConnection();
		con.setAutoCommit(false);
		ps = con.prepareStatement(query);
		CustomLogger.log("Executing query - " + ps);
		ps.executeUpdate();
		con.commit();
		// }catch (SQLException sqe){
		// CustomLogger.logException(sqe);
		// con.rollback();s
		// }finally{
		if (ps != null) {
			ps.close();
		}
		con.setAutoCommit(true);
		con.close();
	}

	public static void updateTokensForWatchingVideos(String userEmail, String property) throws SQLException {
		CustomLogger.log("Updating DB: updating Video Player property for: " + userEmail);
		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(userEmail);
		String query = "update sandw_sso_user_data set value = 0 where user = '" + user_id + "' and item = '" + property
				+ "'";
		// String query = String.format("update sandw_sso_properties set
		// value='0' where user='%s' and property = '%s' ",
		// userEmail, property);
		executeQuery(query);
		CustomLogger.log(String.format("sandw_sso_properties updated for %s to %s", userEmail, property));
	}

	/**
	 * This function will validate tc & v values for user from
	 * 'sandw_search_activity_log' table,
	 */
	public static void validateSearchActivity(String userEmail, String column, String expectedValue)
			throws SQLException {
		Connection con = null;
		Statement stmt = null;
		try {
			con = getMySqlJDBCConnection();
			stmt = con.createStatement();

			String sql = "select * from sandw_search_activity_log where email='" + userEmail
					+ "' ORDER BY date_time DESC LIMIT 0 , 1";
			CustomLogger.log("Executing query - " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				String actualValue = rs.getString(column);

				// Display values
				Assert.assertEquals(actualValue, expectedValue);
				CustomLogger.log(column + " value from table: " + actualValue + " is as expected.");

			}
			rs.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					con.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (con != null)
					con.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		CustomLogger.log("Database connection closed successfully!");
	}

	/**
	 * Retrieve the User Id of the userby using Email via Central Service and
	 * User API's.
	 * 
	 * @return User Id
	 * @author mperumal
	 */
	public static String getUserIdFromEmail(String user_email) {

		String env = Environment.getEnvironment();
		String to_get_user_id_from_user_api;
		String user_id;
		if (env.equalsIgnoreCase("STG")) {
			// To get the User Id of the user via OAT and Email by User API's.
			to_get_user_id_from_user_api = "http://userapi.stg.pch.com/api/user/" + user_email + "/"
					+ getUserGMTOATFromEmail(user_email, "OAT");
			user_id = Common.getResponseParamFromGETCall(to_get_user_id_from_user_api, "id");
		} else {
			// To get the User Id of the user via OAT and Email by User API's.
			to_get_user_id_from_user_api = "http://userapi.qa.pch.com/api/user/" + user_email + "/"
					+ getUserGMTOATFromEmail(user_email, "OAT");
			user_id = Common.getResponseParamFromGETCall(to_get_user_id_from_user_api, "id");
		}
		return user_id;
	}

	/**
	 * Retrieve the GMT of the user by using Email via Central Service and User
	 * API's.
	 * 
	 * @return User Id
	 * @author mperumal
	 */
	public static String getUserGMTOATFromEmail(String user_email, String key) {

		String env = Environment.getEnvironment();
		String to_get_oat_gmt_from_email;
		String oat_gmt_of_given_email;
		if (env.equalsIgnoreCase("STG")) {
			// To get OAT from the given Email via the Central Service API
			to_get_oat_gmt_from_email = "http://centralservices.stg.pch.com/rfprofileapi/Svc/securedprofile.svc/json/api/memberlookup/gmtbyemail";
			oat_gmt_of_given_email = Common.getResponseParamFromPOSTCall(to_get_oat_gmt_from_email,
					"{\"Email\": \"" + user_email + "\", \"LoginName\": \"CustomerService\"}", null, key.toUpperCase());
		} else {
			// To get OAT from the given Email via the Central Service API
			to_get_oat_gmt_from_email = "http://centralservices.qa.pch.com/rfprofileapi/Svc/securedprofile.svc/json/api/memberlookup/gmtbyemail";
			oat_gmt_of_given_email = Common.getResponseParamFromPOSTCall(to_get_oat_gmt_from_email,
					"{\"Email\": \"" + user_email + "\", \"LoginName\": \"CustomerService\"}", null, key.toUpperCase());
		}
		return oat_gmt_of_given_email.toLowerCase();
	}
}
