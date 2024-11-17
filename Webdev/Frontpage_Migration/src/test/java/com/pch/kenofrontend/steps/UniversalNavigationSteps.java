package com.pch.kenofrontend.steps;

import com.pch.kenofrontend.pages.HomePage;
import net.thucydides.core.annotations.Step;

public class UniversalNavigationSteps {
	
	HomePage homePage;
	
	@Step
	public void univnav()
	{
		homePage.univnavpresence();
		
    }
	
	@Step
	public void tabsinunivnav()
	{
		homePage.validatetabnavigation();
		
    }
	@Step
	public void scrollunivnav()
	{
		
		homePage.scrollnavigation();
    }

}
