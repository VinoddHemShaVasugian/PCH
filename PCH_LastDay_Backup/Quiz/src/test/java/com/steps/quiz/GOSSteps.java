package com.steps.quiz;

import java.util.TreeMap;

import com.pages.quiz.GosPage;
import com.pages.quiz.LightboxPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class GOSSteps extends ScenarioSteps {

	private static final long serialVersionUID = 1L;
	private static TreeMap<String, String> gosDetails = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);

	public static TreeMap<String, String> getGosDetails() {
		return gosDetails;
	}

	GosPage gos;
	LightboxPage lbPage;

	@Step
	public boolean verifyLegacyGosLayout() {
		return gos.verifyLegacyGosLayout();
	}

	@Step
	public boolean verifyCompleteRegButtonGos() {
		return gos.verifyNextQuizBtn();
	}

	@Step
	public boolean verifyVideoAdGos() {
		return gos.verifyVideoAdGos();
	}

	@Step
	public void clickCompleteRegButton() {
		gos.clickNextQuizBtn();
	}

	@Step
	public void clickPlayNextQuizLink() {
		gos.clickPlayNextQuizLink();
	}

	@Step
	public boolean verifyPlayNextQuizLink() {
		return gos.verifyPlayNextQuizLink();
	}

	@Step
	public void clickNextQuizBtn() {
		gos.clickNextQuizBtn();
	}

	@Step
	public boolean verifyNextQuizBtn() {
		return gos.verifyNextQuizBtn();
	}

	@Step
	public boolean verifyTokenAmtGos() {
		return gos.verifyTokenAmtGos();
	}

	@Step
	public boolean verifyCongratsMsg() {
		return gos.verifyCongratsMsg();
	}

	@Step
	public boolean verifyGuestUserLB() {
		return lbPage.verifyGuestUserLB();
	}

	@Step
	public boolean verifyAbandoningTokenLB() {
		return lbPage.verifyAbandonLB();
	}

	@Step
	public void getGosAttributes() {
		gosDetails.put("tokens", gos.getTokenAmtGos());
	}
}