package com.pch.sw.tests.guidedSearch;

import org.testng.annotations.Test;

import com.pch.search.mobile.pages.GuidedSearchPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.GroupNames;

public class GuidedSearchSegmentationTests extends BaseTest {
	
	private HomePage homePage;
	private WebHeaderPage header;
	private GuidedSearchPage gs;
	
	@Test(groups={GroupNames.Mobile}, description="Un-recognized users GS validation tests for all layouts - TestID-29925")
	public void gsSegmentation_UnRecognizedUsers(){
		
		// load default GS for unrecognized users and validate 
		homePage.load();
		gs.validateGs("L1-UD-6E-1-DEFAULT");
		
		homePage.loadGuidedSearch("AUTOMATION-L1");
		gs.validateGs("L1-U-4E-3");
		
		homePage.loadGuidedSearch("AUTOMATION-L2");
		gs.validateGs("L2-U-4E-6");
		
		homePage.loadGuidedSearch("AUTOMATION-L3");
		gs.validateGs("L3-U-6E-9");
		
		homePage.loadGuidedSearch("AUTOMATION-L4");
		gs.validateGs("L4-U-6E-22");
		
		homePage.loadGuidedSearch("AUTOMATION-L5");
		gs.validateGs("L5-U-5E-25");
		
	}
	
	@Test(groups={GroupNames.Mobile}, description="Recognized users - Included segment - GS validation tests for all layouts - TestID-29925")
	public void gsSegmentation_RecognizedUsersIncludeSegment(){
		
		//Included segment User 'SEARCH_VIP - SV' - kcheguri06@pchmail.com / testing
		homePage.load();
		header.loginToSearch("kcheguri06@pchmail.com", "testing");
		gs.validateGs("L1-RD-5E-2-DEFAULT");
		
		// load GS for recognized users and validate 
		homePage.loadGuidedSearch("AUTOMATION-L1");
		gs.validateGs("L1-R-3E-4");
		
		homePage.loadGuidedSearch("AUTOMATION-L2");
		gs.validateGs("L2-R-3E-7");
		
		homePage.loadGuidedSearch("AUTOMATION-L3");
		gs.validateGs("L3-R-6E-10");
		
		homePage.loadGuidedSearch("AUTOMATION-L4");
		gs.validateGs("L4-R-6E-23");
		
		homePage.loadGuidedSearch("AUTOMATION-L5");
		gs.validateGs("L5-R-5E-26");
		
	}
	
	@Test(groups={GroupNames.Mobile}, description="Recognized users - Excluded segment - GS validation tests for all layouts - TestID-29925")
	public void gsSegmentation_RecognizedUsersExcludeSegment(){
		
		//Excluded Segment User 'ACQ_CommerceVIP_MAILABLE - ACM' - kcheguri07@pchmail.com / testing
		homePage.load();
		header.loginToSearch("kcheguri07@pchmail.com", "testing");
		gs.validateGs("L1-RD-5E-2-DEFAULT");
		
		// load GS for recognized users and validate 
		homePage.loadGuidedSearch("AUTOMATION-L1");
		gs.validateGs("L1-RD-2E-5");
		
		homePage.loadGuidedSearch("AUTOMATION-L2");
		gs.validateGs("L2-RD-6E-8");
		
		homePage.loadGuidedSearch("AUTOMATION-L3");
		gs.validateGs("L3-RD-6E-11");
		
		homePage.loadGuidedSearch("AUTOMATION-L4");
		gs.validateGs("L4-RD-6E-24");
		
		homePage.loadGuidedSearch("AUTOMATION-L5");
		gs.validateGs("L5-RD-5E-27");
		
	}
	
	@Test(groups={GroupNames.Mobile}, description="Recognized users - No segment - GS validation tests for all layouts - TestID-29925")
	public void gsSegmentation_RecognizedUsersNoSegment(){
		
		//No Segment User - kcheguri03@pchmail.com / testing
		homePage.load();
		header.loginToSearch("kcheguri03@pchmail.com", "testing");
		gs.validateGs("L1-RD-5E-2-DEFAULT");
		
		// load GS for recognized users and validate 
		homePage.loadGuidedSearch("AUTOMATION-L1");
		gs.validateGs("L1-RD-2E-5");
		
		homePage.loadGuidedSearch("AUTOMATION-L2");
		gs.validateGs("L2-RD-6E-8");
		
		homePage.loadGuidedSearch("AUTOMATION-L3");
		gs.validateGs("L3-RD-6E-11");
		
		homePage.loadGuidedSearch("AUTOMATION-L4");
		gs.validateGs("L4-RD-6E-24");
		
		homePage.loadGuidedSearch("AUTOMATION-L5");
		gs.validateGs("L5-RD-5E-27");
		
	}
	
	
}
