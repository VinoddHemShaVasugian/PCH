package com.pch.sw.tests.misc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.GroupNames;

public class SearchActivityLogTests extends BaseTest {

	private HomePage homePage;
	RegistrationPage regPage;
	String keyword = "books";
	AdminBasePage joomlaPage;
	String QueryString;

	/*
	 * @Test(description=
	 * "TestID='17297', Verify Activity log for Guest user from browser's console"
	 * ,groups={GroupNames.Mobile,GroupNames.Regression},
	 * testName="DifferentiateGuidedSearchOnActivityLog_Desktop_Tablet") public
	 * void testActivityLogForGuestUserAndRegisteredUser() throws SQLException{
	 * String Url, email, tc, v; tc= ""; v="";
	 * 
	 * 
	 * if(Environment.getEnvironment().equalsIgnoreCase("STG")){ email =
	 * "drtest90@pchmail.com"; tc ="206WS7743P"; v= "20153111"; Url =
	 * "http://search.stg.pch.com/search/?q=shoes&src1=swemail&src2=13S2505&mailid=pch13S2505&tc=204SX4880K&v=20073489&e=03320A92-F11F-4EAD-825D-231E24563E39&email=drtest90@pchmail.com"
	 * ; }else{ email = "drtest90@pchmail.com"; tc = "206WS7743P"; v=
	 * "20153111"; Url =
	 * "http://search.stg.pch.com/search/?q=shoes&src1=swemail&src2=13S2505&mailid=pch13S2505&tc=204SX4880K&v=20073489&e=03320A92-F11F-4EAD-825D-231E24563E39&email=drtest90@pchmail.com"
	 * ; }
	 * 
	 * 
	 * homePage.load(); homePage.loadSearchWithEmailLink(Url);
	 * 
	 * // Validate TC & V values and agent ID in search activity table
	 * DBUtils.validateSearchActivity(email, "tc", tc);
	 * DBUtils.validateSearchActivity(email, "v", v); CustomLogger.log(
	 * "Validated the record successfully in Search_Activity_Log table.");
	 * 
	 * 
	 * }
	 */
	
	@Test
	public void superPrizeQueueProcess() throws SQLException {
		joomlaPage.goToArticle("Contest Keys / Search / FirstSearch");
		String TcValue = joomlaPage.mobileTcValue().getAttribute("value");
		String VValue = joomlaPage.mobileVValue().getAttribute("value");
		joomlaPage.goToArticle("Contest Keys / Search / FirstSearch");
		joomlaPage.mobileTcValue().clear();
		joomlaPage.mobileTcValue().sendKeys("sdf");
		joomlaPage.mobileVValue().clear();
		joomlaPage.mobileVValue().sendKeys("ashdfkj");
		joomlaPage.saveCloseAndClearCache();
		QueryString = "http://search.stg.pch.com/search/?q=shoes&src1=swemail&src2=13S2505&mailid=pch13S2505&tc=sdf&v=ashdfkj&e=4da0bd7f-cba9-4b26-af05-99b11e50e94a&email=np12@pchmail.com";
		homePage.loadSearchWithEmailLink(QueryString);
		DBUtils.executeQueryAndReturnResultSet("select * from sandw_superprize_queue where email='np12@pchmail.com' and pdate='getdate()'");
		joomlaPage.goToArticle("Contest Keys / Search / FirstSearch");
		joomlaPage.mobileTcValue().clear();
		joomlaPage.mobileTcValue().sendKeys(TcValue);
		joomlaPage.mobileVValue().clear();
		joomlaPage.mobileVValue().sendKeys(VValue);
		joomlaPage.saveCloseAndClearCache();
	}
}
