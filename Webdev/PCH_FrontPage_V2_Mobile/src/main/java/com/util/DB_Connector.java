package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * @author Maniganda Perumal
 * @since June 21 - 2017
 * 
 */
public class DB_Connector extends WrapperFunctions {

	private Connection mysql_connect = null;
	private Statement mysql_statement = null;
	private ResultSet mysql_resultSet = null;

	private static final DB_Connector db_instance = new DB_Connector();

	private DB_Connector() {
	}

	public static DB_Connector getInstance() {
		return db_instance;
	}

	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	// Method to connect to database
	public void open_mysql_connection(String uri, String username, String password) throws SQLException {
		// Setup the connection with the DB
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// DriverManager.getConnection("jdbc:mysql://10.79.9.41/pch_stg?user=""
			// &password="" ");
			mysql_connect = DriverManager.getConnection(uri, username, password);
			log.info("DB Connection Established successfully!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method to terminate the DB connection after validation
	public void close_mysql_connection() {
		try {
			if (mysql_resultSet != null) {
				mysql_resultSet.close();
			}

			if (mysql_statement != null) {
				mysql_statement.close();
			}

			if (mysql_connect != null) {
				log.info("DB Connection is closed Successfully!!!");
				mysql_connect.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method to Run the Select Query after database connection
	public ResultSet executeMYSQLQuery(String Query) throws SQLException {
		log.info("Opening the DB connection");
		try {
			open_mysql_connection(xmlReader(ENVIRONMENT, "DB_uri"), xmlReader(ENVIRONMENT, "DB_username"),
					xmlReader(ENVIRONMENT, "DB_password"));
			log.info("Select Query to be Executed is: " + Query);
			mysql_resultSet = mysql_connect.createStatement().executeQuery(Query);
		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			log.info("Closing the DB connection");
			close_mysql_connection();
		}
		return mysql_resultSet;
	}

	// Method to Run the Update Query after database connection
	public int updateMYSQLQuery(String Query) throws SQLException {
		log.info("Opening the DB connection");
		int status = 0;
		try {
			open_mysql_connection(xmlReader(ENVIRONMENT, "DB_uri"), xmlReader(ENVIRONMENT, "DB_username"),
					xmlReader(ENVIRONMENT, "DB_password"));
			log.info("Update Query to be Executed is: " + Query);
			status = mysql_connect.createStatement().executeUpdate(Query);
		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			log.info("Closing the DB connection");
			close_mysql_connection();
		}
		return status;
	}

	// Method to get the Filed value of Table column in String
	public String getFieldValueAsString(ResultSet resultSet, String ColumnName) throws SQLException {
		if (resultSet.next()) {
			return resultSet.getString(ColumnName);
		} else {
			return "";
		}
	}

	// to execute a query and verify the specific field value
	public String executeQueryAndVerifyValue(String Query, String Field) {
		String fieldvalue = "";
		try {
			log.info("Opening the DB connection");
			open_mysql_connection(xmlReader(ENVIRONMENT, "DB_uri"), xmlReader(ENVIRONMENT, "DB_username"),
					xmlReader(ENVIRONMENT, "DB_password"));
			log.info("Select Query to be Executed is: " + Query);
			mysql_resultSet = mysql_connect.createStatement().executeQuery(Query);
			fieldvalue = getFieldValueAsString(mysql_resultSet, Field);
			return fieldvalue;
		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			log.info("Closing the DB connection");
			close_mysql_connection();
		}
		return "";
	}

	/**
	 * Update the Registration date for an user
	 * 
	 * @param userEmail
	 * @param dateStr
	 * @throws SQLException
	 */
	public void updateRegistrationDate(String userEmail, String dateStr) throws SQLException {

		log.info("Updating DB: updating Registration Date for: " + userEmail);
		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(userEmail);
		String query = "update sso_user_data set value = '" + dateStr + "', expires = 0 where user = '" + user_id
				+ "' and item = 'registration_date'";
		updateMYSQLQuery(query);
		log.info(String.format("Registration date updated for %s to %s", userEmail, dateStr));
	}

	/**
	 * Update the Daily search count to given parameter value
	 * 
	 * @param userEmail
	 * @throws SQLException
	 */
	public void updateDailySearchCount(String userEmail, int search_count) throws SQLException {
		log.info("Updating DB: Expire Daily search for: " + userEmail);

		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(userEmail);
		// Long timeStamp = new java.util.Date().getTime() / 1000L;
		String query = "update sso_user_data set value = '" + search_count + "' , expires = 0 where user = '" + user_id
				+ "' and (item like 'dailysearchcount' or item like 'dailywincount' or item like 'webdailyhrsearchcount' or "
				+ "item like 'webdailysearchcount' or item like 'webrealsearchcount');";
		updateMYSQLQuery(query);
		log.info(String.format("Daily Search expired for %s.", user_id));
	}

	/**
	 * Update the Searching Enabled status to 1 or 0.
	 * 
	 * @param userEmail
	 * @throws SQLException
	 */
	public void updateSearchingEnabledStatus(String userEmail, int enable_status) throws SQLException {
		log.info("Updating DB: Searching Enabled item for: " + userEmail);

		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(userEmail);
		Long timeStamp = new java.util.Date().getTime() / 1000L;
		String query = "update sso_user_data set value = '" + enable_status + "' , expires = " + timeStamp
				+ " where user = '" + user_id + "' and item like 'searchingenabled';";
		updateMYSQLQuery(query);
		log.info(String.format("Searching Enabled item for %s.", user_id));
	}

	/**
	 * Update the Birthday token expire time stamp to 0
	 * 
	 * @param userEmail
	 * @throws SQLException
	 */
	public void updateBirthDateExpireValue(String userEmail, long timestamp) throws SQLException {
		log.info("Updating DB: Expire Birthday Token Expire value for: " + userEmail);

		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(userEmail);
		// long expire_timetamp = modifyCurrentDateByOffset("year", -1);
		String query = "update sso_user_data set expires = " + timestamp + " where user = '" + user_id
				+ "' and item like 'tokenawardedbirthday';";
		updateMYSQLQuery(query);
		log.info(String.format("Updated Birth Date Expire value for %s.", user_id));
	}

	/**
	 * Update the Daily search count to given parameter value
	 * 
	 * @param userEmail
	 * @throws SQLException
	 */
	public void updateTokenAwardLinkPromo(String userEmail) throws SQLException {
		log.info("Updating DB: Expire Token Award link promo for: " + userEmail);

		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(userEmail);
		Long timeStamp = new java.util.Date().getTime() / 1000L;
		String query = "update sso_user_data set value = 0 , expires = " + timeStamp + " where user = '" + user_id
				+ "' and item like 'tokenawardedlinkpromotion%';";
		updateMYSQLQuery(query);
		log.info(String.format("Token Award link promo expired for %s.", user_id));
	}

	/**
	 * Update the Optin Showed count to given parameter value
	 * 
	 * @param userEmail
	 * @throws SQLException
	 */
	public void updateOptinShowedCount(String userEmail, int count) throws SQLException {
		log.info("Updating DB: Optin Showed count for: " + userEmail);

		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(userEmail);
		Long timeStamp = new java.util.Date().getTime() / 1000L;
		String query = "update sso_user_data set value = '" + count + "' , expires = " + timeStamp + " where user = '"
				+ user_id + "' and item like 'OptinShowed';";
		updateMYSQLQuery(query);
		log.info(String.format("Updated Optin showed and expired value for %s.", user_id));
	}

	/**
	 * Update the Last Login value
	 * 
	 * @param userEmail
	 * @throws SQLException
	 */
	public void updateLastLogin(String userEmail, Long value) throws SQLException {
		log.info("Updating DB: Last Login timestamp for: " + userEmail);
		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(userEmail);
		Long timeStamp = new java.util.Date().getTime() / 1000L;
		String query = "update sso_user_data set value = '" + value + "' , expires = " + timeStamp + " where user = '"
				+ user_id + "' and item like 'LastLogin';";
		updateMYSQLQuery(query);
		log.info(String.format("Updated Last Login value and expired value for %s.", user_id));
	}

	/**
	 * Retrieve the Video log details for the given user using the Video title
	 * 
	 * @param user_email
	 * @param video_title
	 * @return
	 */
	public LinkedHashMap<String, String> get_video_log_details(String user_email, String video_title) {
		log.info("Retrieving DB for Video log for user: " + user_email);
		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(user_email);
		String query = "select video_title, category, tokens, claimed, device, gmt, oat from video_log where user_id = "
				+ user_id + " and video_title like \"" + video_title + "\" order by `TIMESTAMP` desc limit 1;";
		LinkedHashMap<String, String> data = get_mulitple_columns_values_of_user(query);
		return data;
	}

	/**
	 * Retrieve the Video log details for the given user
	 * 
	 * @param user_email
	 * @return
	 */
	public LinkedHashMap<String, String> get_video_log_details(String user_email) {
		log.info("Retrieving DB for Video log for user: " + user_email);
		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(user_email);
		String query = "select video_title, category, tokens, claimed, device, gmt, oat from video_log where user_id = "
				+ user_id + " order by `TIMESTAMP` desc limit 1;";
		LinkedHashMap<String, String> data = get_mulitple_columns_values_of_user(query);
		return data;
	}

	/**
	 * Retrieve the Story log details for the given user
	 * 
	 * @param user_email
	 * @return
	 */
	public LinkedHashMap<String, String> get_story_log_details(String user_email) {
		log.info("Retrieving DB for Video log for user: " + user_email);
		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(user_email);
		String query = "select story_id, category, tokens, claimed, device, gmt, oat from story_log where user_id = "
				+ user_id + " order by `TIMESTAMP` desc limit 1;";
		LinkedHashMap<String, String> data = get_mulitple_columns_values_of_user(query);
		return data;
	}

	/**
	 * Retrieve the Search Activity log details for the given user
	 * 
	 * @param user_email
	 * @return
	 */
	public LinkedHashMap<String, String> get_search_activity_log_details(String user_email) {
		log.info("Retrieving DB for Search Activity log for user: " + user_email);
		String query = "select uid, useragenttype, nfsp, search_term from search_activity_log where email = '"
				+ user_email + "' order by date_time desc limit 1;";
		LinkedHashMap<String, String> data = get_mulitple_columns_values_of_user(query);
		return data;
	}

	/**
	 * Retrieve the Failed Contest Entry details for the given user
	 * 
	 * @param user_email
	 * @return
	 */
	public LinkedHashMap<String, String> get_failed_contest_details(String contest_key) {
		log.info("Retrieving failed contest key details for key: " + contest_key);
		String query = "select originating_url,app_code,gmt from contest_entries_failed where contest_key = '"
				+ contest_key + "' order by entry_date desc limit 1;";
		LinkedHashMap<String, String> data = get_mulitple_columns_values_of_user(query);
		return data;
	}

	/**
	 * Retrieve the Queued Contest Entry details for the given user
	 * 
	 * @return
	 */
	public LinkedHashMap<String, String> get_queued_contest_details() {
		log.info("Retrieving queued contest key details ");
		String query = "select app_code,status,error_message,request_data from contest_entries order by created_at desc limit 1;";
		LinkedHashMap<String, String> data = get_mulitple_columns_values_of_user(query);
		return data;
	}

	/**
	 * Retrieve the Queued Token Credit details for the given user
	 * 
	 * @return
	 */
	public LinkedHashMap<String, String> get_queued_token_details() {
		log.info("Retrieving queued contest key details ");
		String query = "select lob,status,request_data from tokens_credit_queue order by created_at desc limit 1;";
		LinkedHashMap<String, String> data = get_mulitple_columns_values_of_user(query);
		return data;
	}

	/**
	 * Retrieve the Queued Contest Entry details for the given user
	 * 
	 * @param user_email
	 * @return
	 */
	public LinkedHashMap<String, String> get_queued_bmh_details(String user_email) {
		log.info("Retrieving queued bmh details ");
		String query = "select tc,v,gmt,site_bmh,posted,rejected,bmh_error from billmehandler_queue where email = '"
				+ user_email + "';";
		LinkedHashMap<String, String> data = get_mulitple_columns_values_of_user(query);
		return data;
	}

	/**
	 * Retrieve the First Search Contest Entry details for the given user
	 * 
	 * @param user_email
	 * @return
	 */
	public String verify_first_search_contest_key_on_userdata_table(String user_email, String contest_key) {
		log.info("Retrieving First Search contest key details for user: " + user_email);
		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(user_email);
		String query = "select case when exists (select * from sso_user_data where user = " + user_id + " and item='"
				+ contest_key + "') THEN True ELSE False end as status;";
		return executeQueryAndVerifyValue(query, "status");
	}

	/**
	 * Retrieve the Daily Progress Bar details for the given user
	 * 
	 * @param user_email
	 * @return
	 */
	public String get_daily_progress_mission_details(String user_email) {
		log.info("Retrieving Daily Progress mission bar details for user: " + user_email);
		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(user_email);
		String query = "select * from sso_user_data where user = '" + user_id + "' and item like 'mobiledailygameplay'";
		return executeQueryAndVerifyValue(query, "value");
	}

	/**
	 * Retrieve the HealthiNation details for the given user
	 * 
	 * @param user_email
	 * @return
	 */
	public String get_healthination_details(String user_email) {
		log.info("Retrieving HealthiNation token details for user: " + user_email);
		// To get the User Id of the user via OAT and Email by User API.
		String user_id = getUserIdFromEmail(user_email);
		String query = "select value from frontv2_stg.sso_user_data where user = " + user_id
				+ " and item = 'HealthiNationTokens';";
		return executeQueryAndVerifyValue(query, "value");
	}

	/**
	 * Execute the SQL query and return the result set value of all the column
	 * as a Map of the first row
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public LinkedHashMap<String, String> get_mulitple_columns_values_of_user(String query) {

		LinkedHashMap<String, String> column_value_map = null;
		try {
			// Execute the SQL Query. Store results in ResultSet
			open_mysql_connection(xmlReader(ENVIRONMENT, "DB_uri"), xmlReader(ENVIRONMENT, "DB_username"),
					xmlReader(ENVIRONMENT, "DB_password"));
			log.info("Select query to be Executed is: " + query);
			mysql_resultSet = mysql_connect.createStatement().executeQuery(query);
			ResultSetMetaData rsmd = mysql_resultSet.getMetaData();
			int column_count = rsmd.getColumnCount();
			String column_name;
			String column_value;
			while (mysql_resultSet.next()) {
				column_value_map = new LinkedHashMap<String, String>();
				for (int i = 1; i <= column_count; i++) {
					column_name = rsmd.getColumnName(i).trim();
					column_value = mysql_resultSet.getString(i);
					column_value = column_value == null ? "" : column_value;
					column_value_map.put(column_name, column_value);
				}
			}
		} catch (SQLException SQL) {
			log.error("Error in retreiving the mulitple column vlaues for the user: " + SQL.getLocalizedMessage());
		} finally {
			log.info("Closing the DB connection");
			close_mysql_connection();
		}
		return column_value_map;
	}

	/**
	 * Execute the SQL query and return the result set value of all the column
	 * as a Map for all the rows
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public LinkedList<LinkedHashMap<String, String>> get_mulitple_rows_values_of_user(String query) {

		LinkedList<LinkedHashMap<String, String>> resultset_multiple_row = new LinkedList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> column_value_map = null;
		try {
			// Execute the SQL Query. Store results in ResultSet
			open_mysql_connection(xmlReader(ENVIRONMENT, "DB_uri"), xmlReader(ENVIRONMENT, "DB_username"),
					xmlReader(ENVIRONMENT, "DB_password"));
			log.info("Select query to be Executed is: " + query);
			mysql_resultSet = mysql_connect.createStatement().executeQuery(query);
			ResultSetMetaData rsmd = mysql_resultSet.getMetaData();
			int column_count = rsmd.getColumnCount();
			String column_name;
			String column_value;
			while (mysql_resultSet.next()) {
				column_value_map = new LinkedHashMap<String, String>();
				for (int i = 1; i <= column_count; i++) {
					column_name = rsmd.getColumnName(i).trim();
					column_value = mysql_resultSet.getString(i);
					column_value = column_value == null ? "" : column_value;
					column_value_map.put(column_name, column_value);
				}
				resultset_multiple_row.add(column_value_map);
			}
		} catch (SQLException SQL) {
			log.error("Error in retreiving the mulitple row vlaues for the user:" + SQL.getSQLState());
		} finally {
			log.info("Closing the DB connection");
			close_mysql_connection();
		}
		return resultset_multiple_row;
	}

	/**
	 * Retrieve the User Id of the userby using Email via Central Service and
	 * User API's.
	 * 
	 * @return User Id
	 * @author mperumal
	 */
	public String getUserIdFromEmail(String user_email) {

		String env = WrapperFunctions.ENVIRONMENT;
		String to_get_user_id_from_user_api;
		String user_id;
		if (env.equalsIgnoreCase("STG")) {
			// To get the User Id of the user via OAT and Email by User API's.
			to_get_user_id_from_user_api = "http://userapi.stg.pch.com/api/user/" + user_email + "/"
					+ getUserGMTOATFromEmail(user_email, "OAT");
			user_id = WrapperFunctions.getResponseParamFromGETCall(to_get_user_id_from_user_api, "id", "data");
		} else {
			// To get the User Id of the user via OAT and Email by User API's.
			to_get_user_id_from_user_api = "http://userapi.qa.pch.com/api/user/" + user_email + "/"
					+ getUserGMTOATFromEmail(user_email, "OAT");
			user_id = WrapperFunctions.getResponseParamFromGETCall(to_get_user_id_from_user_api, "id", "data");
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
	public String getUserGMTOATFromEmail(String user_email, String key) {

		String env = WrapperFunctions.ENVIRONMENT;
		String to_get_oat_gmt_from_email;
		String oat_gmt_of_given_email;
		if (env.equalsIgnoreCase("STG")) {
			// To get OAT from the given Email via the Central Service API
			to_get_oat_gmt_from_email = "http://centralservices.stg.pch.com/rfprofileapi/Svc/securedprofile.svc/json/api/memberlookup/gmtbyemail";
			oat_gmt_of_given_email = WrapperFunctions.getResponseParamFromPOSTCall(to_get_oat_gmt_from_email,
					"{\"Email\": \"" + user_email + "\", \"LoginName\": \"CustomerService\"}", null, key.toUpperCase());
		} else {
			// To get OAT from the given Email via the Central Service API
			to_get_oat_gmt_from_email = "http://centralservices.qa.pch.com/rfprofileapi/Svc/securedprofile.svc/json/api/memberlookup/gmtbyemail";
			oat_gmt_of_given_email = WrapperFunctions.getResponseParamFromPOSTCall(to_get_oat_gmt_from_email,
					"{\"Email\": \"" + user_email + "\", \"LoginName\": \"CustomerService\"}", null, key.toUpperCase());
		}
		return oat_gmt_of_given_email.toLowerCase();
	}
}
