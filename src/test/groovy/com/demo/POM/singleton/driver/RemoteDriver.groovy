/**
 * The class is responsible for creating a concrete RemoteWebDriver instance
 * 
 */
package com.demo.POM.singleton.driver

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
		def browser = config.seleniumConfigs.remote.browser
		def hostAddress = config.seleniumConfigs.remote.ip
		def hostPort = config.seleniuConfigs.remote.port
		def platform = config.seleniumConfigs.remote.platform
		def version = config.seleniumConfigs.remote.version
		DesiredCapabilities capabilities
		
		if(driver == null) {
            log.info("Requesting ${browser} instance")
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
                log.info("Unsupported browser type: ${browser}. Throwing RuntimeException")
				throw new RuntimeException("Browser type unsupported")
			}
		} else { return driver }

		capabilities.version = version
		capabilities.platform = Platform.fromString(platform)

		log.info("creating RemoteWebDriver instance with url: http://${hostAddress}:${hostPort}/wd/hub")
        log.info("and capabilities: ${capabilities.getVersion()}, ${capabilities.getPlatform()}")
		return (new RemoteWebDriver(
				new URL("http://${hostAddress}:${hostPort}/wd/hub"), capabilities))
	}

}
