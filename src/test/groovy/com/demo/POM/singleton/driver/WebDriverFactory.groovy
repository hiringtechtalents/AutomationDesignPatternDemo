package com.demo.POM.singleton.driver

import org.openqa.selenium.WebDriver


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
        driver = new ThreadLocal<WebDriver>() {
            @Override
            protected WebDriver initialValue() {
                switch (driverType) {
                    case "local":
                        return new LocalDriver().createDriver()
                    case "remote":
                        return new RemoteDriver().createDriver()
                    case "mobile":
                        return new MobileDriver().createDriver()
                    case "saucelabs":
                        return new SauceLabsDriver().createDriver()
                    default: throw new Exception("UnSupported driver type requested: ${driverType}")
                }
            }
        }

        return driver.get()
	}

    public void closeDriver() {
        driver.get().quit()

        driver.remove()
    }
}