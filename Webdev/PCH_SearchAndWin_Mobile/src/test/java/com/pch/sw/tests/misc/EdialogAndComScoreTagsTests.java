package com.pch.sw.tests.misc;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class EdialogAndComScoreTagsTests extends BaseTest {
	
	private HomePage homePage;
	private SearchResultsPage searchResultsPage;
	private User randomUser_1, randomUser_2;
	public WebHeaderPage webHeaderPage;
	public RegistrationPage registrationPage;
	private SearchResultsPage searchResultspage;
	
	@Test(groups={GroupNames.Regression,GroupNames.Mobile}, enabled=false)
	public void eDialogTags(){
		
		String homeURL = Common.getAppUrl(Environment.getAppName());
		String tagToBeFired = "https://pd.ed10.net/pd/0G/";
		String mobileTag = "mobile=0";
		
		homePage.load();
		searchResultsPage.searchAndGetCount("Hai");
		String pageSource = homePage.getPageSource();
		//CustomLogger.log(pageSource);
		CustomLogger.log("Verifying a tag which need to fire and mobile ");
		Assert.assertTrue(pageSource.contains(tagToBeFired));
		Assert.assertTrue(pageSource.contains(mobileTag));
		CustomLogger.log("Verified - ConversionTrack Code tag is fired & mobile is set to 0.");
		
		homePage.load(homeURL+"/search?q=TV&edid=&email=abcde@pch.com");
		String currentPgeSource = homePage.getPageSource();
		Assert.assertTrue(currentPgeSource.contains("mobile=0"));
		CustomLogger.log("Verified - ConversionTrack Code tag is fired & mobile is set to 0.");
		
	}
	
	@Test(groups={GroupNames.Regression,GroupNames.Mobile}, enabled=false)
	public void comScoreTags(){
		
		String comScore = "  var _comscore = _comscore || [];";
		String comScorePush = "_comscore.push({ c1: \"2\", c2: \"6036336\" });";
		String imgSrc = "http://b.scorecardresearch.com/p?c1=2&amp;c2=6036336&amp;cv=2.0&amp;cj=1";
		
		homePage.load();
		CustomLogger.log("Verifying comscore tags");
		String currentPgeSource = homePage.getPageSource();
		CustomLogger.log(currentPgeSource);
		Assert.assertTrue(currentPgeSource.contains(comScore));
		Assert.assertTrue(currentPgeSource.contains(comScorePush));
		Assert.assertTrue(currentPgeSource.contains(imgSrc));
		
	}
	
	@Test(groups={GroupNames.Regression,GroupNames.Mobile},description = "[1]Evergage_tags - 19089")
	public void evergageTags(){
		
		List<String> tagsToFireForEvergage = new ArrayList<String>();
		String evergageProvider ="window.EvergageUserProvider";
		String user = "userID: PCHUSER.gmt,";
		String email = "email: PCHUSER.emailHash,";
		String propertyMembership = "propertyMembership: PCHUSER.reglist,";
		String segmentMembership = "segmentMembership: PCHUSER.seglist,";
		
		
		tagsToFireForEvergage.add(evergageProvider);
		tagsToFireForEvergage.add(user);
		tagsToFireForEvergage.add(email);
		tagsToFireForEvergage.add(propertyMembership);
		tagsToFireForEvergage.add(segmentMembership);
		
		homePage.load();	
		
		boolean isLogIn = false;
		for(int cycle=0; cycle<2; cycle++){
			if(isLogIn){
				CustomLogger.log("looking for tags in pagesource for Recognized user..");
				loginToSearch(randomUser_1);			
				searchResultspage.searchAndGetCount("hai");
				homePage.closeUserLevelLightBox();
			}else{
				CustomLogger.log("looking for tags in pagesource for Guest user..");
			}
			
			String currentPgeSource = homePage.getPageSource();
			
			
			for(int i = 0;i<tagsToFireForEvergage.size();i++){
				Assert.assertTrue(currentPgeSource.contains(tagsToFireForEvergage.get(i)));
				CustomLogger.log("Tag fired - "+tagsToFireForEvergage.get(i));
			}
			isLogIn = true;
		}
		
	}
	
	@Test(groups={GroupNames.Regression,GroupNames.Mobile},description = "[1]UnRecohnizedUser-Evergage support with GMT - 22649")
	public void evergageUnrecognizedUser(){
		
		List<String> actualPageSource = new ArrayList<String>();
		String envi ="PCH.env = \"stg\"";
		String search = "PCH.sitecode = \"SEARCH\"";
		String segList = "PCHUSER.seglist = []";
		String regList = "PCHUSER.reglist = []";
		String isMiniReg = "PCHUSER.isMiniReg = false";
		String isSocialReg =  "PCHUSER.isSocialReg = false";
		String isFullyreg = "PCHUSER.isFullyRegistered = false";
		
		actualPageSource.add(envi);
		actualPageSource.add(search);
		actualPageSource.add(segList);
		actualPageSource.add(regList);
		actualPageSource.add(isMiniReg);
		actualPageSource.add(isSocialReg);
		actualPageSource.add(isFullyreg);
		
		homePage.load();
		String pageSource = homePage.getPageSource();
		CustomLogger.log("Looking for basic details of unrec user in Page source");
		
		for(int i=0; i<actualPageSource.size(); i++){
			Assert.assertTrue(pageSource.contains(actualPageSource.get(i)));
		}
		
		CustomLogger.log("");
		
	}
	
	@Test(groups={GroupNames.Regression,GroupNames.Mobile},description = "[1]RecognizedUser-Evergage support with GMT - 22648")
	public void evergageRecognizedUser(){
		
		loginToSearch(randomUser_2);
		
		List<String> actualPageSource = new ArrayList<String>();
		
		//String email= randomUser_2.getEmail().toLowerCase();
		//actualPageSource.add(email);
		actualPageSource.add(randomUser_2.getTitle());
		actualPageSource.add(randomUser_2.getFirstname());
		actualPageSource.add(randomUser_2.getLastname());
		//actualPageSource.add("PCHUSER.state = \"NY\";");
		//actualPageSource.add(randomUser_2.getZip());
		actualPageSource.add("PCH.sitecode = \"SEARCH\"");
		actualPageSource.add("PCH.env = \"stg\";");
		
		String pageSource = homePage.getPageSource();
		System.out.println(pageSource);
		CustomLogger.log("looking for user datails in Page Source");
		
		for(int i=0; i<actualPageSource.size(); i++){
			System.out.println(actualPageSource.get(i));
			Assert.assertTrue(pageSource.contains(actualPageSource.get(i)));
			
		}
	}

}