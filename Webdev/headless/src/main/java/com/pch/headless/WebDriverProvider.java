package com.pch.headless;
/**
 * The interface Web driver provider.
 */
public interface WebDriverProvider {

    /**
     * Create new {@link WebDriver} instance.
     *
     * @param capabilities set of desired capabilities
     *                     as suggested by Selenium framework;
     * @return new {@link WebDriver} instance.
     */

    WebDriver createDriver(DesiredCapabilities capabilities);

}