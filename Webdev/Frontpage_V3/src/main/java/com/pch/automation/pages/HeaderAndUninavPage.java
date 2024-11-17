package com.pch.automation.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.pch.automation.pages.fp.HomePage;
import com.pch.automation.utilities.AppConfigLoader;
import com.pch.automation.utilities.RandomGenerator;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

/**
 * Page objects and methods for Header and UniNav section
 *
 * @author vsankar
 */

public class HeaderAndUninavPage extends PageObject {

	/**
	 * Instantiates a HeaderAndUninavPage page.
	 *
	 * @param driver
	 */
	public HeaderAndUninavPage(WebDriver driver) {
		super(driver);
	}

	HomePage homePageInstance;
	AppConfigLoader appConfigLoader;
	LightboxPage lbPage;

	private final By myAccount = By.linkText("My Account");
	private final By signOut = By.linkText("Sign Out");
	private final By Register = By.linkText("Register");
	private final By signIn = By.linkText("Sign In");
	private final By completeRegistration = By.xpath("//a[text()='Complete Registration']");
	private final By homeLogo = By.cssSelector("a.uninav__logo");
	private final By headerMenu = By.cssSelector("ul.uninav__top-bar__links>li");
	private final By latestActivityMessage = By.cssSelector("p.uninav__msg__desc");
	private final By latestActivityAmount = By.cssSelector("span.uninav__msg__tokens-text");
	private final By latestEntryActivityMessage = By.cssSelector("div.uninav__msg__message-content");
	private final By totalTokenValue = By.cssSelector("p.uninav__token-center-alltime__tokens-amount");
	private final By tokenHistoryLink = By.linkText("Token History");
	private final By vipMsg = By.cssSelector("div.vip-message__content");
	private final By vipMsgBody = By.cssSelector("div.vip-message__content>p:nth-of-type(2)");
	private final By vipMsgHeader = By.cssSelector("div.vip-message__content>p:nth-of-type(1)");
	private final By vipBadgeLogo = By.cssSelector("span.vip-badge__name");
	private final By vipShelf = By.cssSelector("div.vip-message--mode-push-down-condensed");
	private final By uniNavList = By.cssSelector("li.uninav__carousel__item>a");
	private final By uninavNextArrow = By.cssSelector("span.uninav__carousel__nxt");
	private final By uninavNextArrowDisabledStatus = By.cssSelector("span.uninav__carousel__nxt--disabled");
	private final By uninavPreviousArrow = By.cssSelector("span.uninav__carousel__prv");
	private final By uninavPreviousArrowDisabledStatus = By.cssSelector("span.uninav__carousel__prv--disabled");
	private final By fancyWelcomeMsg = By.cssSelector("a.fancybox-item.fancybox-close");
	private final By levelGemIcon = By.cssSelector("div.uninav__my-status.uninav__my-status-toggle");
	private final By levelUpShelf = By.cssSelector("div.uninav__levelup[aria-hidden=false]");
	private final By redeemTokens = By.cssSelector("a.uninav__token-center-alltime__redeem uninav__redeem-toggle");
	private final By dropDownRedeemTokensbutton = By.linkText("REDEEM TOKENS");
	private final By levelupPlaynowButton = By.linkText("PLAY NOW!");
	private final By uninavSearch = By.cssSelector("a.uninav__carousel__link uninav__carousel__link--search");
	private final By learnMoreLink = By.linkText("Learn More");
	private final By aboutPchFP = By.linkText("About Frontpage");
	private final By howToSearch = By.xpath("//div[@class='ip_sidebar_textHolder'][contains(.,'How To Search')]");
	private final By dosAnddont = By.xpath("//div[@class='ip_sidebar_textHolder'][contains(.,'Do’s and Don’ts')]");
	private final By earntokens = By.xpath("//div[@class='ip_sidebar_textHolder'][contains(.,'Earn Tokens')]");
	private final By waysToWin = By.xpath("//div[@class='ip_sidebar_textHolder'][contains(.,'Ways To Win')]");
	private final By infoPageHeaderTitle = By.cssSelector("div#left span.active");

	/**
	 * Close the Level up shelf
	 */
	public void closeLevelUpShelf() {
		if (isElementVisible(levelUpShelf)) {
			clickOn(element(levelGemIcon));
			waitForRenderedElementsToDisappear(levelUpShelf);
		}
	}

	/**
	 * Close the Level up shelf
	 */
	public void openLevelUpShelf() {
		if (!isElementVisible(levelUpShelf)) {
			clickOn(element(levelGemIcon));
			waitForRenderedElements(levelUpShelf);
		}
	}

	/**
	 * To click on Sign out link and sign out from the site
	 */
	public void clickSignOut() {
		clickOn(element(signOut));
	}

	/**
	 * To click on My Account link
	 */
	public void clickMyAccount() {
		clickOn(element(myAccount));
	}

	/**
	 * To click on total earned tokens
	 */
	public void clickTokensEarned() {
		clickOn(element(totalTokenValue));
	}

	/**
	 * To verify the presence of Signout Link
	 * 
	 * @return True
	 */
	public boolean verifySignout() {
		return isElementVisible(signOut);
	}

	/**
	 * To verify the presence of Sign in Link
	 * 
	 * @return True
	 */
	public boolean verifyUnRecHome() {
		return isElementVisible(signIn);
	}

	/**
	 * To click on Home Menu
	 */
	public void clickHome() {
		clickOn(element(homeLogo));
	}

	/**
	 * To get latest activity message
	 * 
	 * @return Latest activity message
	 */
	public String getLatestActivityMessage() {
		clickTokensEarned();
		return element(latestActivityMessage).getAttribute("innerHTML");
	}

	/**
	 * To get earned token amount for recent activity
	 * 
	 * @return Token amount of recent activity
	 */
	public String getLatestActivityTokenAmount() {
		clickTokensEarned();
		return element(latestActivityAmount).getAttribute("innerHTML").replace(",", "").split(" ")[0];
	}

	/**
	 * To get recent entry activity message
	 * 
	 * @return Latest entry activity Message
	 */
	public String getLatestEntryActivityMessage() {
		clickTokensEarned();
		return element(latestEntryActivityMessage).getAttribute("innerHTML").replace(",", "");
	}

	/**
	 * To click on Sign In link on home page for unrecognized user.
	 */
	public void clickSignIn() {
		clickOn(element(signIn));
	}

	/**
	 * To click on Register link on home page for unrecognized user.
	 */
	public void clickRegister() {
		clickOn(element(Register));
	}

	/**
	 * Verify the Register link on home page for unrecognized user.
	 * 
	 * @return True
	 */
	public boolean verifyRegister() {
		return isElementVisible(Register);
	}

	/**
	 * Verify the Register link on home page for unrecognized user.
	 * 
	 * @return True
	 */
	public boolean verifySignin() {
		return isElementVisible(signIn);
	}

	/**
	 * To verify Complete Registration button for silver/mini/social users
	 * 
	 * @return True
	 */
	public boolean verifyCompleteRegistration() {
		return isElementVisible(completeRegistration);
	}

	/**
	 * To click Complete Registration button
	 * 
	 * @return True
	 */
	public void clickCompleteRegistration() {
		if (isElementVisible(fancyWelcomeMsg)) {
			clickOn(element(fancyWelcomeMsg));
		}
		clickOn(element(completeRegistration));
	}

	/**
	 * To verify the my account link on home page to ensure user redirected on Home
	 * page
	 * 
	 * @return True
	 */
	public boolean verifyHome() {
		return isElementVisible(myAccount);
	}

	/**
	 * To get total Token value
	 * 
	 * @return TokenValue
	 */
	public int getTokens() {
		element(totalTokenValue).waitUntilVisible();
		String tokenValue = element(totalTokenValue).getText();
		if (tokenValue.contentEquals("Loading...")) {
			getDriver().navigate().refresh();
		}
		tokenValue = tokenValue.replace(",", "");
		int tokens = Integer.parseInt(tokenValue);
		return tokens;
	}

	/**
	 * To click on Token History link from page header
	 */
	public void clickTokenHistory() {
		lbPage.closeEvergagePopup();
		moveTo(tokenHistoryLink);
		clickOn(element(tokenHistoryLink).waitUntilClickable());
	}

	/**
	 * Get the Header Menu list names
	 * 
	 * @return List of headers
	 */
	public List<WebElementFacade> getHeaderMenuList() {
		element(headerMenu).waitUntilVisible();
		return findAll(headerMenu);
	}

	/**
	 * To Get other properties displayed on UniNav
	 * 
	 * @return List of properties in UniNav
	 */
	public List<WebElementFacade> getUninavList() {
		element(uniNavList).waitUntilVisible();
		return findAll(uniNavList);
	}

	/**
	 * Verify the UniNav Next arrow disabled status
	 * 
	 * @return True
	 */
	public boolean verifyUninavNextArrowDisabledStatus() {
		return isElementVisible(uninavNextArrowDisabledStatus);
	}

	/**
	 * Verify the UniNav Next arrow enabled status
	 * 
	 * @return True
	 */
	public boolean verifyUninavNextArrowEnabledStatus() {
		return isElementVisible(uninavNextArrow);
	}

	/**
	 * Verify the UniNav Previous arrow disabled status
	 * 
	 * @return True
	 */
	public boolean verifyUninavPreviousArrowDisabledStatus() {
		return isElementVisible(uninavPreviousArrowDisabledStatus);
	}

	/**
	 * Verify the UniNav Previous arrow enabled status
	 * 
	 * @return True
	 */
	public boolean verifyUninavPreviousArrowEnabledStatus() {
		return element(uninavPreviousArrow).isVisible();
	}

	/**
	 * Verify the VIP badge on UniNav
	 * 
	 * @return True
	 */

	public boolean verifyVipBadge() {
		return isElementVisible(vipBadgeLogo);
	}

	/**
	 * Click VIP Shelf on UniNav
	 * 
	 * @return True
	 */

	public boolean verifyVipShelf() {
		return isElementVisible(vipShelf);
	}

	/**
	 * Click VIP logo
	 * 
	 */
	public void clickVIPLogo() {
		try {
		clickOn(element(vipBadgeLogo).waitUntilClickable());
		}catch(Exception e) {
			homePageInstance.jsClick(vipBadgeLogo);
		}
	}

	/**
	 * Common function to verify the VIP message before and after done any activity
	 * 
	 * @return True
	 */
	public boolean verifyVIPMsgBeforeAndAfterSearch() {
		try {
			String beforeSearch = getVipMsg();
			System.out.println("VIP message before search: " + beforeSearch);
			homePageInstance.search(RandomGenerator.randomAlphabetic(5));
			String afterSearch = getVipMsg();
			System.out.println("VIP message after search: " + afterSearch);
			return beforeSearch.contentEquals(afterSearch);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Verify VIP message on home
	 * 
	 * @return VIP message
	 */
	public String getVipMsg() {
		String msg;
		if (!verifyVipShelf()) {
			clickVIPLogo();
			element(vipMsgBody).waitUntilVisible();
			element(vipMsgHeader).waitUntilVisible();
			waitFor(30);
			msg = element(vipMsg).waitUntilPresent().getText();
			clickVIPLogo();
		} else {
			element(vipMsgBody).waitUntilVisible();
			element(vipMsgHeader).waitUntilVisible();
			msg = element(vipMsg).waitUntilPresent().getText();
			clickVIPLogo();
		}
		System.out.println("Actual VIP Msg: " + msg);
		return msg.trim();
	}

	//// deepika--update//
	/**
	 * verify the presence of redeemToken icon
	 * 
	 * @return True
	 */
	public boolean verifyReedemTokenButtonVisible() {

		return isElementVisible(redeemTokens);

	}

	/**
	 * verify the presence of redeemToken button in dropdownshelf
	 * 
	 * @return True
	 */
	public boolean verifyDropDownReedemTokenButtonVisible() {
		clickOn(element(redeemTokens));
		return isElementVisible(dropDownRedeemTokensbutton);
	}

	/**
	 * verify the presence of playnow button in dropdownshelf
	 * 
	 */

	public void verifyPlaynowButton() {
		if (isElementVisible(levelUpShelf)) {
			clickOn(element(levelGemIcon));
			clickOn(element(levelupPlaynowButton));

		}
	}

	public void verifyRedeemPagenavigation() {
		clickOn(element(redeemTokens).waitUntilVisible());
	}

	public boolean verifyAboutPage() {
		homePageInstance.jsClick(aboutPchFP);
		homePageInstance.switchToNewlyOpenedTab();
		return element(infoPageHeaderTitle).getText().equalsIgnoreCase("ABOUT FRONTPAGE");
	}

	public boolean verifyDosPage() {
		homePageInstance.jsClick(dosAnddont);
		return element(infoPageHeaderTitle).getText().equalsIgnoreCase("DO'S AND DON'TS");
	}

	public boolean verifyEarnTokensPage() {
		homePageInstance.jsClick(earntokens);
		return element(infoPageHeaderTitle).getText().equalsIgnoreCase("EARN TOKENS");
	}

	public boolean verifyHowToSearchPage() {
		homePageInstance.jsClick(howToSearch);
		return element(infoPageHeaderTitle).getText().equalsIgnoreCase("HOW TO SEARCH");
	}

	public boolean verifyWaysToWinPage() {
		homePageInstance.jsClick(waysToWin);
		return element(infoPageHeaderTitle).getText().equalsIgnoreCase("WAYS TO WIN");
	}

	public void Searchuninav() {
		clickOn(element(uninavSearch));
	}

	public void afterGamePlayLearnMore() {
		if (isElementVisible(levelUpShelf)) {
			clickOn(element(levelGemIcon));
			clickOn(element(learnMoreLink));
		}
	}

}
