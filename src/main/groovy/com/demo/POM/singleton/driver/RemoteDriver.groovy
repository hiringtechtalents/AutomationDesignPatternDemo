/**
 * The class is responsible for creating a concrete RemoteWebDriver instance
 * 
 */
package com.demo.POM.singleton.driver

import com.demo.POM.singleton.exceptions.UnsupportedDriverTypeException
import groovy.util.logging.Slf4j
import org.openqa.selenium.Platform
import org.openqa.selenium.WebDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

/**
 * @author SANDEEP
 *
 */

@Slf4j
class RemoteDriver extends DriverType {
	
	public RemoteDriver() {
		super()
	}

	/* (non-Javadoc)
	 * @see com.test.driver.Driver#createDriver(java.lang.String)
	 */
	@Override
	WebDriver createDriver() {
        log.info("entering createDriver of %s class", this.class.simpleName)
		
		if(driver == null) {
            log.info("Requesting ${browser} instance")
			caps = createCapabilities()
		} else { return driver }

		caps.version = version
		caps.platform = Platform.fromString(platform)

		log.info("creating RemoteWebDriver instance with url: http://${serverAddress}:${serverPort}/wd/hub")
		log.info("and capabilities: ${caps.getVersion()}, ${caps.getPlatform()}")
		return (new RemoteWebDriver(
				new URL("http://${serverAddress}:${serverPort}/wd/hub"), caps))
	}

	protected DesiredCapabilities createCapabilities() {
		browser = config.seleniumConfigs.remote.browser
		serverAddress = config.seleniumConfigs.remote.ip
		serverPort = config.seleniuConfigs.remote.port
		platform = config.seleniumConfigs.remote.platform
		version = config.seleniumConfigs.remote.version

		DesiredCapabilities capabilities
		if (browser.equalsIgnoreCase("firefox")) {
			capabilities = DesiredCapabilities.firefox()
		} else if (browser.equalsIgnoreCase("internetExplorer")) {
			capabilities = DesiredCapabilities.internetExplorer()
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true)
		} else if (browser.equalsIgnoreCase("chrome")) {
			capabilities = DesiredCapabilities.chrome()
		} else if (browser.equalsIgnoreCase('safari')) {
			capabilities = DesiredCapabilities.safari()
		} else {
			log.error("Unsupported browser type: ${browser}. Throwing RuntimeException")
			throw new UnsupportedDriverTypeException("Browser type unsupported for ${browser}")
		}
		capabilities
	}

}
