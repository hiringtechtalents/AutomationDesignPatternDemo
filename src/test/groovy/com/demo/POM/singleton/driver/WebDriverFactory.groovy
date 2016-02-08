package com.demo.POM.singleton.driver

import groovy.util.logging.Slf4j
import org.openqa.selenium.WebDriver

@Slf4j
public final class WebDriverFactory {
    private static WebDriverFactory instance

    private ThreadLocal<WebDriver> driver

    private WebDriverFactory() {}

	public static WebDriverFactory getInstance() {
		if (instance == null) {
			synchronized(WebDriverFactory.class) {
                if (instance == null) instance = new WebDriverFactory();
			}
		}
        return instance;
	}

    public WebDriver getDriver(String driverType) throws Exception {
        log.info("Entering getDriver method with the param driverType: ${driverType}")
        driver = new ThreadLocal<WebDriver>() {
            @Override
            protected WebDriver initialValue() {
                switch (driverType) {
                    case "local":
                        log.info("In local driver type case block")
                        return new LocalDriver().createDriver()
                    case "remote":
                        log.info("In remote driver type case block")
                        return new RemoteDriver().createDriver()
                    case "mobile":
                        log.info("In mobile driver type case block")
                        return new MobileDriver().createDriver()
                    case "saucelabs":
                        log.info("In saucelabs driver type case block")
                        return new SauceLabsDriver().createDriver()
                    default:
                        log.info("UnSupported driver type requested: ${driverType}")
                        throw new RuntimeException("UnSupported driver type requested: ${driverType}")
                }
            }
        }

        log.info("exiting getDriver method of ${this.class.simpleName} class")
        return driver.get()
	}

    public void closeDriver() {
        log.info("Entering closeDriver method of ${this.class.simpleName} class")
        log.info("quitting the current active WebDriver instance")
        driver.get().quit()

        log.info("De-registering the WebDriver instance from ThreadLocal instance")
        driver.remove()

        log.info("Exiting closeDriver method of ${this.class.simpleName} class")
    }
}