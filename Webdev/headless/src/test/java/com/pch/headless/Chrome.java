package com.pch.headless;
/**
 * The class Chrome. This class describes Chrome browser instance.
 */
public class Chrome implements WebDriverProvider {
    /**
     * The constant LOG.
     */
    private static final Logger LOG = Logger.getLogger(Chrome.class.getName());

    /**
     * The method createDriver.
     */
    @Override
    public WebDriver createDriver(final DesiredCapabilities capabilities) {
        WebDriverManager.chromedriver().setup();
        capabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
        try {
            return new ChromeDriver(getChromeOptions());
        } catch (Exception ex) {
            if (LOG.isLoggable(Level.INFO)) {
                LOG.info(String.valueOf(ex));
            }
        }
        return null;
    }

    /**
     * Method getChromeOptions.
     *
     * @return the chrome options.
     */
    public static ChromeOptions getChromeOptions() {
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        chromeOptions.setExperimentalOption("useAutomationExtension", false);

        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--window-size=1580,1280");

        final HashMap<String, Object> prefs = new HashMap();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        chromeOptions.setExperimentalOption("prefs", prefs);

        return chromeOptions;
    }

	public WebDriver createDriver(DesiredCapabilities capabilities) {
		// TODO Auto-generated method stub
		return null;
	}
}