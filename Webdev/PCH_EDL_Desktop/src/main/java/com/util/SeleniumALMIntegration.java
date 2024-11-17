package com.util;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.jacob.com.LibraryLoader;

import atu.alm.wrapper.ALMServiceWrapper;
import atu.alm.wrapper.enums.StatusAs;
/**
 * 
 * @author Automation Tester http://automationtestingutilities.blogspot.in/
 */
public class SeleniumALMIntegration extends WrapperFunctions {

	private static final SeleniumALMIntegration alm_instance = new SeleniumALMIntegration();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final LinkedHashMap<String, String> alm_tc = new LinkedHashMap<String, String>();

	public static SeleniumALMIntegration getInstance() {
		return alm_instance;
	}

	protected void updateResults(String status, String test_id, String test_case_name) {
		// Retrieve ALM details
		String alm_mapping = env_property_file_reader("ALM_Mapping");
		String alm_endpoint = env_property_file_reader("ALM_URL");
		String test_regression_dir = env_property_file_reader("Test_regression_dir");
		int test_set_id = Integer.parseInt(env_property_file_reader("Test_set_id"));
		String test_set_name = env_property_file_reader("Test_set_name");
		String alm_username = env_property_file_reader("Alm_Username");
		// String alm_password =env_property_file_reader( "Alm_Password");
		String test_domain_name = env_property_file_reader("Domain_name");
		String test_project_name = env_property_file_reader("Project_name");

		if (alm_mapping.equalsIgnoreCase("Yes")) {
			try {
				System.setProperty("jacob.dll.path",
						System.getProperty("user.dir") + "\\src\\main\\resources\\lib\\jacob-1.18-x86.dll");
				LibraryLoader.loadJacobLibrary();
				ALMServiceWrapper wrapper = new ALMServiceWrapper(alm_endpoint);
				wrapper.connect(alm_username, "", test_domain_name, test_project_name);
				if (test_case_name != null && !test_case_name.isEmpty()) {
					for (String tc_name : test_case_name.split(",")) {
						if (alm_tc.get(tc_name) != null) {
							status = status.toUpperCase().equalsIgnoreCase(alm_tc.get(tc_name)) ? status.toUpperCase()
									: "FAIL";
						}
						switch (status.toUpperCase()) {
						case "PASS":
							wrapper.updateResult(test_regression_dir, test_set_name, test_set_id, tc_name,
									StatusAs.PASSED);
							break;
						case "FAIL":
							wrapper.updateResult(test_regression_dir, test_set_name, test_set_id, tc_name,
									StatusAs.FAILED);
							break;
						case "NO_RUN":
							wrapper.updateResult(test_regression_dir, test_set_name, test_set_id, tc_name,
									StatusAs.NO_RUN);
							break;
						}
						alm_tc.put(tc_name, status.toUpperCase());
					}
				}
				wrapper.close();
			} catch (Exception alm) {
				log.error("Error in the ALM integration: " + alm.getMessage());
			}
		}
	}
}
