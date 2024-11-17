package com.miscellaneous;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.HomePage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SubCategoryPage;
import com.pageobjects.VideoLandingPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class VideoTests extends BaseClass {

	private final AccountsSignInPage sign_in_instance = AccountsSignInPage.getInstance();
	private final AccountsRegisterPage register = AccountsRegisterPage.getInstance();
	private final HomePage homepage_instance = HomePage.getInstance();
	private final VideoLandingPage videopage_instace = VideoLandingPage.getInstance();
	private final CentralServices_Page centralpage = CentralServices_Page.getInstance();
	private final SubCategoryPage sub_category_instance = SubCategoryPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private static String env_to_execute = "env_to_execute";
/*
	@SuppressWarnings("unused")
	@testId(test_id = "")
	@testCaseName(test_case_name = "")
	@Test(priority = 1, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the Landing page of the videos when plays it ", testName = "")
	public void video_landing_page_categories() throws Exception {
		// Step 1
		test_step_details(1, "Navigate to frontpage and sign-in wiith valid credentials");
		homepage_instance.click_SignIn();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());
		// Step 2
		test_step_details(2, "Click on Videos and verify the landing page");
		homepage_instance.click_vidoes_menu();
		assertIsStringContains(DriverManager.getDriver().getCurrentUrl(),
				xmlReader(ENVIRONMENT, "BaseURL") + "video/featured");
		assertTrue(videopage_instace.verify_fa_videosection());
		assertTrue(videopage_instace.verify_back_to_home_link());
		assertTrue(videopage_instace.verify_video_landing_page());
		assertIsStringContains(videopage_instace.get_video_title(),
				videopage_instace.get_video_title_on_bottom_playlist().substring(0,
						videopage_instace.get_video_title_on_bottom_playlist().length() - 3));

		// Step 3
		test_step_details(3, "Verify the category playlists");
		assertTrue(videopage_instace.verify_categoryPlaylist());
		// Step 4
		test_step_details(4, "Verify the number of videos displayed in the playlist on the page");
		LinkedList<String> menu_list = homepage_instance.get_main_catagory_menu_name();
		for (String menu_name : menu_list) {
			assertEqualsInt(videopage_instace.get_playlist_video_count(menu_name), 3);
		}
		// Step 5
		test_step_details(5, "Verify the Playlist and the video count of Category sections on featured video pages");
		menu_list = homepage_instance.get_main_catagory_menu_name();
		for (String menu_name : menu_list) {
			// Step 6
			test_step_details(6, "Verify the display of Next and Previous arrows");
			// assertTrue(videopage_instace.verify_next_arrow_enable_status(menu_name));
			// assertTrue(videopage_instace.verify_previous_arrow_disable_status(menu_name));
			// Step 7
			test_step_details(7, "Click the Play list and verify the video by playing it");
			videopage_instace.navigate_sub_category_page_from_label(menu_name);
			assertTrue(videopage_instace.verify_fa_videosection());
			assertIsStringContains(videopage_instace.get_video_title(),
					videopage_instace.get_video_title_on_bottom_playlist().substring(0,
							videopage_instace.get_video_title_on_bottom_playlist().length() - 3));
			assertTrue(videopage_instace.verify_back_to_home_link());
			homepage_instance.click_vidoes_menu();
			menu_list = homepage_instance.get_main_catagory_menu_name();
			// Break statement will stop the execution by verify the first
			// category section details of the menu. Added to
			// save the execution time.
			break;
		}

		// Step 8
		test_step_details(8,
				"Verify the Playlist and the video count of Sub-Category sections on category video pages");
		LinkedList<String> sub_category_menu_url_list = homepage_instance.get_sub_catagory_menu_url_list();
		LinkedList<String> sub_category_menu_name_list = homepage_instance.get_sub_catagory_menu_list();
		menu_list = homepage_instance.get_main_catagory_menu_name();
		for (int count = 0; count < sub_category_menu_url_list.size(); count++) {
			if (!sub_category_menu_url_list.get(count).endsWith("business")
					&& !sub_category_menu_url_list.get(count).endsWith("sports")) {
				// Step 9
				test_step_details(9,
						"Navigate to Category page Click the Play list and verify the video by playing it");
				invokeBrowser(sub_category_menu_url_list.get(count));
				sub_category_instance.click_first_video_link();
				assertTrue(videopage_instace.verify_fa_videosection());
				assertIsStringContains(videopage_instace.get_video_title(),
						videopage_instace.get_video_title_on_bottom_playlist().substring(0,
								videopage_instace.get_video_title_on_bottom_playlist().length() - 3));
				assertTrue(videopage_instace.verify_back_to_home_link());
				// Step 10
				test_step_details(10, "Verify the Main Category section in the Sub-Category video page");
				assertTrue(videopage_instace.verify_video_playlist(menu_list.get(count)));
				// Step 11
				test_step_details(11, "Verify the other Sub-Category section in the Sub-Category video page");
				for (int i = count + 1; i < sub_category_menu_name_list.size(); count++) {
					assertTrue(videopage_instace.verify_video_playlist(sub_category_menu_name_list.get(i)));
					// assertTrue(videopage_instace.verify_next_arrow_enable_status(sub_category_menu_name_list.get(i)));
					// assertTrue(
					// videopage_instace.verify_previous_arrow_disable_status(sub_category_menu_name_list.get(i)));
					break;
				}
				sub_category_menu_url_list = homepage_instance.get_sub_catagory_menu_url_list();
				sub_category_menu_name_list = homepage_instance.get_sub_catagory_menu_list();

				// Break statement will stop the execution by verify the first
				// sub category section details of the menu. Added to
				// save the execution time.
				break;
			}
		}
	}

	@testId(test_id = "")
	@testCaseName(test_case_name = "")
	@Test(priority = 2, groups = { "DESKTOP",
			"TABLET" }, testName = "", description = "Verify the Video player for all types of users")
	public void verify_video_player_for_all_users() throws Exception {
		test_Method_details(2, "Verify the Video player for all types of users");
		// Step 1
		test_step_details(1, "Navigate to frontpage click on videos and verify");
		homepage_instance.click_vidoes_menu();
		assertTrue(videopage_instace.verify_video_landing_page());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the video player information");
		assertTrue(videopage_instace.verify_video_player());
		step_validator(2, true);

		// Step 3
		test_step_details(3,
				"Verify all over the site when try to open a video, video should open with a videoplayer page.");
		LinkedList<String> menu_list = homepage_instance.get_main_catagory_menu_name();
		for (String menu_name : menu_list) {
			if (!menu_name.endsWith("more")) {
				videopage_instace.navigate_sub_category_page_from_label(menu_name);
				assertTrue(videopage_instace.verify_video_player());
				homepage_instance.click_vidoes_menu();
			}
		}
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify Next Video info for Guest users");
		homepage_instance.click_vidoes_menu();
		videopage_instace.verify_play_circle();
		assertTrue(videopage_instace.verify_next_video_unrec_user());
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Verify Login and Register links on the Next video info page of Guest user");
		assertIsStringContains(videopage_instace.get_login_url(),
				"https://accounts." + env_property_file_reader("env_to_execute").toLowerCase() + ".pch.com/login");
		assertIsStringContains(videopage_instace.get_register_url(),
				"https://accounts." + env_property_file_reader("env_to_execute").toLowerCase() + ".pch.com/register");
		step_validator(5, true);

		// Step 6
		test_step_details(6, "Verify Next Video info for Social user");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		navigateTo(centralpage.createSocialUser()[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.click_vidoes_menu();
		lb_instance.close_lb();
		assertTrue(videopage_instace.verify_video_player());
		videopage_instace.click_entertainment_videos();
		videopage_instace.verify_play_circle();
		assertTrue(videopage_instace.verify_videoendscreen_completeReg());
		step_validator(6, true);

		// Step 7
		test_step_details(7, "Verify Next Video info for Mini Reg user");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		navigateTo(centralpage.createMiniReguser()[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.click_vidoes_menu();
		lb_instance.close_lb();
		assertTrue(videopage_instace.verify_video_player());
		videopage_instace.click_entertainment_videos();
		videopage_instace.verify_play_circle();
		assertTrue(videopage_instace.verify_videoendscreen_completeReg());
		step_validator(7, true);

		// Step 8
		test_step_details(8, "Verify Next Video info for Silver user");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		navigateTo(centralpage.createSilverUser()[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.click_vidoes_menu();
		lb_instance.close_lb();
		assertTrue(videopage_instace.verify_video_player());
		videopage_instace.click_entertainment_videos();
		videopage_instace.verify_play_circle();
		assertTrue(videopage_instace.verify_videoendscreen_completeReg());
		step_validator(8, true);

		// Step 9
		test_step_details(9, "Verify Next Video info for Gold user and verify the tokens");
		navigateTo(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_Register();
		register.register_FullUser();
		lb_instance.close_welcome_optin_lb();
		lb_instance.close_bronze_level_up_lb();
		homepage_instance.close_openx_banner();
		homepage_instance.click_vidoes_menu();
		assertTrue(videopage_instace.verify_video_player());
		videopage_instace.click_entertainment_videos();
		step_validator(9, true);

		// Step 10
		test_step_details(10, "Verify Next Video play button for Gold user and verify the tokens");
		assertTrue(
				homepage_instance.verify_token_claim_status(msg_property_file_reader("video_player_default_message")));
		videopage_instace.verify_play_circle();
		videopage_instace.click_play_circle();
		assertTrue(
				homepage_instance.verify_token_claim_status(msg_property_file_reader("video_player_default_message")));
		assertEquals(homepage_instance.get_latest_activity_message(),
				msg_property_file_reader("video_token_activity_message"));
		step_validator(10, true);
	}*/

	@testId(test_id = "RT-04233")
	@testCaseName(test_case_name = "Create Local Tables for Video and Story Logs [D/T/M]")
	@Test(priority = 3, groups = { "DESKTOP", "TABLET",
			"SANITY" }, description = "Verify the Video details on Video log table after plays the Video for Full Reg user", testName = "RT-04233:Create Local Tables for Video and Story Logs [D/T/M]")
	public void verify_video_details_on_video_log_for_full_reg() throws Exception {
		test_Method_details(3, "Verify the Video details on Video log table after plays the Video for Full Reg user");
		// Step 1
		test_step_details(1, "Create a Full Reg user");
		navigateTo(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_Register();
		String user_email = register.register_FullUser();
		lb_instance.close_welcome_optin_lb();
		lb_instance.close_bronze_level_up_lb();
		homepage_instance.close_openx_banner();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the Video log by watch a Featured Video and Verify VIP activity");
		homepage_instance.click_first_video_link();
		String video_title = videopage_instace.get_video_title();
		String token_amount = videopage_instace.get_claimed_token_amount();
		String category_type = videopage_instace.get_category_type();
		LinkedHashMap<String, String> log_details = db_instance.get_video_log_details(user_email);
		assertEquals(log_details.get("video_title"), video_title);
		assertEquals(log_details.get("tokens"), token_amount);
		assertEquals(log_details.get("claimed"), "1");
		if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), category_type);
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the Video log by watch a Video from all the Category pages");
		LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("more")) {
				invokeBrowser(url);
				homepage_instance.click_first_video_link();
				video_title = videopage_instace.get_video_title();
				token_amount = videopage_instace.get_claimed_token_amount();
				category_type = videopage_instace.get_category_type();
				log_details = db_instance.get_video_log_details(user_email);
				assertEquals(log_details.get("video_title"), video_title);
				assertEquals(log_details.get("tokens"), token_amount);
				assertEquals(log_details.get("claimed"), "1");
				if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
					assertEquals(log_details.get("device"), "D");
				} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
					assertEquals(log_details.get("device"), "T");
				}
				assertEquals(log_details.get("category"), category_type);
				// Break statement will stop the execution by verify the Video
				// log details for the first category page of the menu. Added to
				// save the execution time.
				break;
			}
		}
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the Video log by watch a Video from all the Sub-Category pages");
		url_list = homepage_instance.get_sub_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("business") && !url.endsWith("sports")) {
				invokeBrowser(url);
				sub_category_instance.click_first_video_link();
				video_title = videopage_instace.get_video_title();
				token_amount = videopage_instace.get_claimed_token_amount();
				category_type = videopage_instace.get_category_type();
				log_details = db_instance.get_video_log_details(user_email);
				assertEquals(log_details.get("video_title"), video_title);
				assertEquals(log_details.get("tokens"), token_amount);
				assertEquals(log_details.get("claimed"), "1");
				if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
					assertEquals(log_details.get("device"), "D");
				} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
					assertEquals(log_details.get("device"), "T");
				}
				assertEquals(log_details.get("category"), category_type);
				// Break statement will stop the execution by verify the Video
				// log details for the first category page of the menu. Added to
				// save the execution time.
				break;
			}
		}
		step_validator(4, true);
	}

	@testId(test_id = "RT-04233")
	@testCaseName(test_case_name = "Create Local Tables for Video and Story Logs [D/T/M]")
	@Test(priority = 4, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the Video details on Video log table after plays the Video for Mini  Reg user", testName = "RT-04233:Create Local Tables for Video and Story Logs [D/T/M]")
	public void verify_video_details_on_video_log_for_mini_reg() throws Exception {
		// Step 1
		test_step_details(1, "Create a Mini Reg user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = centralpage.createMiniReguser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		lb_instance.close_bronze_level_up_lb();
		homepage_instance.close_openx_banner();

		// Step 2
		test_step_details(2, "Verify the Video log by watch a Featured Video");
		homepage_instance.click_first_video_link();
		videopage_instace.verify_play_circle();
		String video_title = videopage_instace.get_video_title();
		String category_type = videopage_instace.get_category_type();
		LinkedHashMap<String, String> log_details = db_instance.get_video_log_details(user_details[0]);
		assertEquals(log_details.get("video_title"), video_title);
		assertTrue(homepage_instance
				.verify_default_token_icon_msg(msg_property_file_reader("video_player_default_message")));
		assertEquals(log_details.get("claimed"), "0");
		if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), category_type);

		// Step 3
		test_step_details(3, "Verify the Video log by watch a Video from all the Category pages");
		LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("more")) {
				invokeBrowser(url);
				homepage_instance.click_first_video_link();
				videopage_instace.verify_play_circle();
				video_title = videopage_instace.get_video_title();
				category_type = videopage_instace.get_category_type();
				log_details = db_instance.get_video_log_details(user_details[0]);
				assertEquals(log_details.get("video_title"), video_title);
				assertTrue(homepage_instance
						.verify_default_token_icon_msg(msg_property_file_reader("video_player_default_message")));
				assertEquals(log_details.get("claimed"), "0");
				if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
					assertEquals(log_details.get("device"), "D");
				} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
					assertEquals(log_details.get("device"), "T");
				}
				assertEquals(log_details.get("category"), category_type);
				// Break statement will stop the execution by verify the Video
				// log details for the first category page of the menu. Added to
				// save the execution time.
				break;
			}
		}

		// Step 4
		test_step_details(4, "Verify the Video log by watch a Video from all the Sub-Category pages");
		url_list = homepage_instance.get_sub_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("business") && !url.endsWith("sports")) {
				invokeBrowser(url);
				sub_category_instance.click_first_video_link();
				videopage_instace.verify_play_circle();
				video_title = videopage_instace.get_video_title();
				category_type = videopage_instace.get_category_type();
				log_details = db_instance.get_video_log_details(user_details[0]);
				assertEquals(log_details.get("video_title"), video_title);
				assertTrue(homepage_instance
						.verify_default_token_icon_msg(msg_property_file_reader("video_player_default_message")));
				assertEquals(log_details.get("claimed"), "0");
				if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
					assertEquals(log_details.get("device"), "D");
				} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
					assertEquals(log_details.get("device"), "T");
				}
				assertEquals(log_details.get("category"), category_type);
				// Break statement will stop the execution by verify the Video
				// log details for the first category page of the menu. Added to
				// save the execution time.
				break;
			}
		}

		// Step 5
		test_step_details(5, "Complete the Registration");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_complete_registration();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register.complete_RegistrationForMiniRegUser();
		lb_instance.close_bronze_level_up_lb();

		// Step 6
		test_step_details(6,
				"Verify the Video log by watch a Featured Video after complete the registration and Verify VIP activity");
		homepage_instance.click_first_video_link();
		videopage_instace.verify_tokens_claimed_button();
		video_title = videopage_instace.get_video_title();
		category_type = videopage_instace.get_category_type();
		String token_amount_value = videopage_instace.get_claimed_token_amount();
		log_details = db_instance.get_video_log_details(user_details[0], video_title);
		assertEquals(log_details.get("tokens"), token_amount_value);
		assertEquals(log_details.get("claimed"), "1");
		if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), category_type);
	}

	@testId(test_id = "RT-04233")
	@testCaseName(test_case_name = "Create Local Tables for Video and Story Logs [D/T/M]")
	@Test(priority = 5, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the Video details on Video log table after plays the Video for Silver User", testName = "RT-04233:Create Local Tables for Video and Story Logs [D/T/M]")
	public void verify_video_details_on_video_log_for_silver_user() throws Exception {
		// Step 1
		test_step_details(1, "Create a Silver user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = centralpage.createSilverUser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		lb_instance.close_bronze_level_up_lb();
		homepage_instance.close_openx_banner();

		// Step 2
		test_step_details(2, "Verify the Video log by watch a Featured Video");
		homepage_instance.click_first_video_link();
		fast_forward_jw_video_player();
		videopage_instace.verify_play_circle();
		String video_title = videopage_instace.get_video_title();
		assertTrue(homepage_instance
				.verify_default_token_icon_msg(msg_property_file_reader("video_player_default_message")));
		String category_type = videopage_instace.get_category_type();
		LinkedHashMap<String, String> log_details = db_instance.get_video_log_details(user_details[0]);
		assertEquals(log_details.get("video_title"), video_title);
		assertTrue(homepage_instance
				.verify_default_token_icon_msg(msg_property_file_reader("video_player_default_message")));
		assertEquals(log_details.get("claimed"), "0");
		if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), category_type);

		// Step 3
		test_step_details(2, "Verify the Video log by watch a Video from all the Category pages");
		LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("more")) {
				invokeBrowser(url);
				homepage_instance.click_first_video_link();
				video_title = videopage_instace.get_video_title();
				videopage_instace.verify_play_circle();
				category_type = videopage_instace.get_category_type();
				log_details = db_instance.get_video_log_details(user_details[0]);
				assertEquals(log_details.get("video_title"), video_title);
				assertTrue(homepage_instance
						.verify_default_token_icon_msg(msg_property_file_reader("video_player_default_message")));
				assertEquals(log_details.get("claimed"), "0");
				if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
					assertEquals(log_details.get("device"), "D");
				} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
					assertEquals(log_details.get("device"), "T");
				}
				assertEquals(log_details.get("category"), category_type);
				// Break statement will stop the execution by verify the Video
				// log details for the first category page of the menu. Added to
				// save the execution time.
				break;
			}
		}

		// Step 4
		test_step_details(4, "Verify the Video log by watch a Video from all the Sub-Category pages");
		url_list = homepage_instance.get_sub_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("business") && !url.endsWith("sports")) {
				invokeBrowser(url);
				sub_category_instance.click_first_video_link();
				video_title = videopage_instace.get_video_title();
				videopage_instace.verify_play_circle();
				category_type = videopage_instace.get_category_type();
				log_details = db_instance.get_video_log_details(user_details[0]);
				assertEquals(log_details.get("video_title"), video_title);
				assertTrue(homepage_instance
						.verify_default_token_icon_msg(msg_property_file_reader("video_player_default_message")));
				assertEquals(log_details.get("claimed"), "0");
				if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
					assertEquals(log_details.get("device"), "D");
				} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
					assertEquals(log_details.get("device"), "T");
				}
				assertEquals(log_details.get("category"), category_type);
				// Break statement will stop the execution by verify the Video
				// log details for the first category page of the menu. Added to
				// save the execution time.
				break;
			}
		}

		// Step 5
		test_step_details(5, "Complete the Registration");
		homepage_instance.click_complete_registration();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register.completer_RegistrationSilveruser();
		lb_instance.close_bronze_level_up_lb();

		// Step 6
		test_step_details(6,
				"Verify the Video log by watch a Featured Video after complete the registration and Verify VIP activity");
		videopage_instace.verify_tokens_claimed_button();
		video_title = videopage_instace.get_video_title();
		category_type = videopage_instace.get_category_type();
		String token_amount_value = videopage_instace.get_claimed_token_amount();
		log_details = db_instance.get_video_log_details(user_details[0], video_title);
		assertEquals(log_details.get("tokens"), token_amount_value);
		assertEquals(log_details.get("claimed"), "1");
		if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), category_type);
	}

	@testId(test_id = "RT-04233")
	@testCaseName(test_case_name = "Create Local Tables for Video and Story Logs [D/T/M]")
	@Test(priority = 6, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the Video details on Video log table after plays the Video for Social user", testName = "RT-04233:Create Local Tables for Video and Story Logs [D/T/M]")
	public void verify_video_details_on_video_log_for_social_user() throws Exception {
		// Step 1
		test_step_details(1, "Create a Social user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = centralpage.createSocialUser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		lb_instance.close_bronze_level_up_lb();
		homepage_instance.close_openx_banner();

		// Step 2
		test_step_details(2, "Verify the Video log by watch a Featured Video");
		homepage_instance.click_first_video_link();
		videopage_instace.verify_play_circle();
		String video_title = videopage_instace.get_video_title();
		String category_type = videopage_instace.get_category_type();
		LinkedHashMap<String, String> log_details = db_instance.get_video_log_details(user_details[0]);
		assertEquals(log_details.get("video_title"), video_title);
		assertTrue(homepage_instance
				.verify_default_token_icon_msg(msg_property_file_reader("video_player_default_message")));
		assertEquals(log_details.get("claimed"), "0");
		if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), category_type);

		// Step 3
		test_step_details(2, "Verify the Video log by watch a Video from all the Category pages");
		LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("more")) {
				invokeBrowser(url);
				homepage_instance.click_first_video_link();
				video_title = videopage_instace.get_video_title();
				videopage_instace.verify_play_circle();
				category_type = videopage_instace.get_category_type();
				log_details = db_instance.get_video_log_details(user_details[0]);
				assertEquals(log_details.get("video_title"), video_title);
				assertTrue(homepage_instance
						.verify_default_token_icon_msg(msg_property_file_reader("video_player_default_message")));
				assertEquals(log_details.get("claimed"), "0");
				if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
					assertEquals(log_details.get("device"), "D");
				} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
					assertEquals(log_details.get("device"), "T");
				}
				assertEquals(log_details.get("category"), category_type);
				break;
			}
		}

		// Step 4
		test_step_details(4, "Verify the Video log by watch a Video from all the Sub-Category pages");
		url_list = homepage_instance.get_sub_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("business") && !url.endsWith("sports")) {
				invokeBrowser(url);
				sub_category_instance.click_first_video_link();
				video_title = videopage_instace.get_video_title();
				videopage_instace.verify_play_circle();
				category_type = videopage_instace.get_category_type();
				log_details = db_instance.get_video_log_details(user_details[0]);
				assertEquals(log_details.get("video_title"), video_title);
				assertTrue(homepage_instance
						.verify_default_token_icon_msg(msg_property_file_reader("video_player_default_message")));
				assertEquals(log_details.get("claimed"), "0");
				if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
					assertEquals(log_details.get("device"), "D");
				} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
					assertEquals(log_details.get("device"), "T");
				}
				assertEquals(log_details.get("category"), category_type);
				break;
			}
		}

		// Step 5
		test_step_details(5, "Complete the Registration");
		homepage_instance.click_complete_registration();
		register.complete_RegistrationForSocialUser();

		// Step 6
		test_step_details(6,
				"Verify the Video log by watch a Featured Video after complete the registration and Verify VIP activity");
		videopage_instace.verify_tokens_claimed_button();
		video_title = videopage_instace.get_video_title();
		category_type = videopage_instace.get_category_type();
		String token_amount_value = videopage_instace.get_claimed_token_amount();
		log_details = db_instance.get_video_log_details(user_details[0], video_title);
		assertEquals(log_details.get("tokens"), token_amount_value);
		assertEquals(log_details.get("claimed"), "1");
		if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), category_type);
	}
/*
	@testId(test_id = "")
	@testCaseName(test_case_name = "")
	@Test(priority = 7, groups = { "DESKTOP",
			"TABLET" }, testName = "", description = "Verify the Video title on below and rightrail of hte video player")
	public void verify_video_title_below_and_rightrail_of_player() throws Exception {
		// Step 1
		test_step_details(1, "Navigate to frontpage and sign-in wiith valid credentials");
		homepage_instance.click_SignIn();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());
		// Step 2
		test_step_details(2, "Verify the title of video");
		homepage_instance.click_vidoes_menu();
		videopage_instace.click_entertainment_videos();
		assertTrue(homepage_instance.verify_startVideoText());
		// Step 3
		test_step_details(3, "Compare the title in all locations");
		assertIsStringContains(homepage_instance.getStartVideoText(),
				homepage_instance.getfirstvideo_List().substring(0, 20));
	}

	@testId(test_id = "")
	@testCaseName(test_case_name = "")
	@Test(priority = 8, groups = { "DESKTOP",
			"TABLET" }, testName = "", description = "Verify the display of Token Icon on Brightcove player and the Token award messages")
	public void verify_token_icon_on_jw_player() throws Exception {
		// Step 1
		test_step_details(1, "Navigate to frontpage and verify Token_Icon below Bright cove player for guest user");
		homepage_instance.click_vidoes_menu();
		videopage_instace.click_entertainment_videos();
		assertTrue(homepage_instance
				.verify_default_token_icon_msg(msg_property_file_reader("video_player_default_message")));
		// Step 2
		test_step_details(2,
				"Create a Mini reg user from reg foundation and verify Token_Icon below Bright cove player");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		navigateTo(centralpage.createMiniReguser()[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.click_vidoes_menu();
		lb_instance.close_lb();
		assertTrue(homepage_instance
				.verify_default_token_icon_msg(msg_property_file_reader("video_player_default_message")));
		// Step 3
		test_step_details(3, "Create a Silver user from reg foundation and verify Token_Icon below Bright cove player");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		navigateTo(centralpage.createSilverUser()[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.click_vidoes_menu();
		lb_instance.close_lb();
		assertTrue(homepage_instance
				.verify_default_token_icon_msg(msg_property_file_reader("video_player_default_message")));
		// Step 4
		test_step_details(4, "Create a Social user from reg foundation and verify Token_Icon below Bright cove player");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		navigateTo(centralpage.createSilverUser()[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.click_vidoes_menu();
		lb_instance.close_lb();
		assertTrue(homepage_instance
				.verify_default_token_icon_msg(msg_property_file_reader("video_player_default_message")));
		// Step 5
		test_step_details(5, "Create a Gold user from reg foundation and verify Token_Icon below Bright cove player");
		navigateTo(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_Register();
		register.register_FullUser();
		lb_instance.close_welcome_optin_lb();
		lb_instance.close_bronze_level_up_lb();
		homepage_instance.click_vidoes_menu();
		assertTrue(homepage_instance
				.verify_default_token_icon_msg(msg_property_file_reader("video_player_default_message")));
		assertTrue(
				homepage_instance.verify_token_claim_status(msg_property_file_reader("video_player_default_message")));
		doRefresh();
		assertTrue(homepage_instance
				.verify_token_already_claim_status(msg_property_file_reader("video_player_default_message")));
	}*/

	@SuppressWarnings("unused")
	@testId(test_id = "RT-04391")
	@testCaseName(test_case_name = "[D/T] FP: Verify ads in across the site")
	@Test(priority = 10, description = "Verify the Ad refresh on all video's page", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04391:[D/T] FP: Verify ads in across the site")
	public void verify_ad_refresh_on_video_page() throws Exception {

		// Step 1
		test_step_details(1, "Login to the FP with valid user");
		homepage_instance.click_SignIn();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Verify the GPT ad's are refreshed when next video plays on Featured Videos page");
		homepage_instance.click_vidoes_menu();
		String page_ad_google_ids[] = videopage_instace.get_page_ad_google_query_id();
		videopage_instace.verify_play_circle();
		videopage_instace.click_play_circle();
		sleepFor(3);
		assertNotEquals(page_ad_google_ids[0], videopage_instace.get_inline_ad_google_query_id());
		assertNotEquals(page_ad_google_ids[1], videopage_instace.get_right_rail_ad_1_google_query_id());
		assertNotEquals(page_ad_google_ids[2], videopage_instace.get_right_rail_ad_2_google_query_id());

		// Step 3
		test_step_details(3, "Verify the GPT ad's are refreshed when next video plays on Category Videos page");
		LinkedList<String> menu_name_list = homepage_instance.get_main_catagory_menu_name();
		for (int count = 0; count < menu_name_list.size(); count++) {
			videopage_instace.navigate_sub_category_page_from_label(menu_name_list.get(count));
			page_ad_google_ids = videopage_instace.get_page_ad_google_query_id();
			videopage_instace.verify_play_circle();
			videopage_instace.click_play_circle();
			sleepFor(3);
			assertNotEquals(page_ad_google_ids[0], videopage_instace.get_inline_ad_google_query_id());
			assertNotEquals(page_ad_google_ids[1], videopage_instace.get_right_rail_ad_1_google_query_id());
			assertNotEquals(page_ad_google_ids[2], videopage_instace.get_right_rail_ad_2_google_query_id());
			homepage_instance.click_vidoes_menu();
			menu_name_list = homepage_instance.get_main_catagory_menu_name();
			// Break statement will stop the execution by verify the first
			// category section details of the menu. Added to
			// save the execution time.
			break;
		}

		// Step 4
		test_step_details(4, "Verify the GPT ad's are refreshed when next video plays on Sub-Category Videos page");
		LinkedList<String> sub_category_menu_url_list = homepage_instance.get_sub_catagory_menu_url_list();
		for (String sub_menu_url : sub_category_menu_url_list) {
			if (!sub_menu_url.endsWith("business") && !sub_menu_url.endsWith("sports")) {
				invokeBrowser(sub_menu_url);
				sub_category_instance.click_first_video_link();
				page_ad_google_ids = videopage_instace.get_page_ad_google_query_id();
				videopage_instace.verify_play_circle();
				videopage_instace.click_play_circle();
				sleepFor(3);
				assertNotEquals(page_ad_google_ids[0], videopage_instace.get_inline_ad_google_query_id());
				assertNotEquals(page_ad_google_ids[1], videopage_instace.get_right_rail_ad_1_google_query_id());
				assertNotEquals(page_ad_google_ids[2], videopage_instace.get_right_rail_ad_2_google_query_id());
				// Break statement will stop the execution by verify the first
				// sub category section details of the menu. Added to
				// save the execution time.
				break;
			}
		}
	}

	@testId(test_id = "RT-04391")
	@testCaseName(test_case_name = "[D/T] FP: Verify ads in across the site")
	@Test(priority = 11, description = "Verify display of Video page Ad's", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04391:[D/T] FP: Verify ads in across the site")
	public void verify_ads_on_video_page() throws IOException {
		test_Method_details(11, "Verify display of Video page Ad's");
		String right_rail_ad_1_width = "300";
		String right_rail_ad_1_height = "600";
		String right_rail_ad_2_width = "300";
		String right_rail_ad_2_height = "250";
		String inline_tile_ad_width = "728";
		String inline_tile_ad_height = "90";

		// Step 1
		test_step_details(1, "Login as a Recognised user");
		homepage_instance.click_SignIn();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the display of Inline GPT Tile Ad's and the Size");
		homepage_instance.click_vidoes_menu();
		assertTrue(homepage_instance.verify_inline_gpt_tile_ad());
		assertEquals(homepage_instance.get_size_of_inline_gpt_tile_ad()[0], inline_tile_ad_width);
		assertEquals(homepage_instance.get_size_of_inline_gpt_tile_ad()[1], inline_tile_ad_height);
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the display of Right Rail First Ad and the Size");
		assertTrue(homepage_instance.verify_right_rail_gpt_ad_1());
		assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_1()[0], right_rail_ad_1_width);
		if (homepage_instance.get_size_of_right_rail_gpt_ad_1()[1].equals(right_rail_ad_1_height)) {
			assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_1()[1], right_rail_ad_1_height);
		} else if (homepage_instance.get_size_of_right_rail_gpt_ad_1()[1].equals(right_rail_ad_2_height)) {
			assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_1()[1], right_rail_ad_2_height);
		} else {
			assertTrue(false, "Right Rail First ad is mismatched in the height");
		}
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the display of Right Rail Second Ad and the Size");
		assertTrue(homepage_instance.verify_right_rail_gpt_ad_2());
		assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_2()[0], right_rail_ad_2_width);
		assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_2()[1], right_rail_ad_2_height);
		step_validator(4, true);
	}
}
