package com.pch.kenofrontend.steps;

import java.util.List;

import net.thucydides.core.annotations.Step;
import com.pch.kenofrontend.pages.GameChoicesPage;


public class GameChoicesPageSteps{

	GameChoicesPage gamechoicesPage;
	
	@Step
	public void verifynumbers(List<String> numberstoselect)
	{
		gamechoicesPage.matchsubmittednumbers(numberstoselect);
	}	
	
}
