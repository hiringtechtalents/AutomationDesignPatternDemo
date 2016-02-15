package com.demo.POM.singleton.driver

import groovy.util.logging.Slf4j
import org.openqa.selenium.WebDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

@Slf4j
class SauceLabsDriver extends DriverType {
	
	public SauceLabsDriver() {
		super()
	}

	@Override
	WebDriver createDriver() {
        log.info("entering createDriver of %s class", this.class.simpleName)
		def browser = config.seleniumConfigs.sauceLabs.browser
		def userName = config.seleniumConfigs.sauceLabs.userName
		def accessKey = config.seleniumConfigs.sauceLabs.accessKey
		def platform = config.seleniumConfigs.sauceLabs.os
		def version = config.seleniumConfigs.sauceLabs.browserVersion
		def server = config.seleniumConfigs.sauceLabs.onDemand.server
		def port = config.seleniumConfigs.sauceLabs.onDemand.port
		def capabilities
		
		if(driver == null) {
            log.info("Requesting ${browser} instance")
			if(browser.equalsIgnoreCase('firefox')) {
				capabilities = DesiredCapabilities.firefox()
			} else if(browser.equalsIgnoreCase('chrome')) {
				capabilities = DesiredCapabilities.chrome()
			} else if (browser.contains('internet')) {
				capabilities = DesiredCapabilities.internetExplorer()
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true)
			} else if(browser.equalsIgnoreCase('safari')) {
				capabilities = DesiredCapabilities.safari()
            } else {
                log.info("Unsupported browser type: ${browser}. Throwing RuntimeException")
                throw new RuntimeException("unsupported browser type: ${browser}")
            }
		} else {
			return driver
		}
		capabilities.setCapability("platform", platform)
		capabilities.setCapability("version", version)

        log.info("creating SauceLabsDriver instance with url: http://${userName}:${accessKey}@${server}:${port}/wd/hub")
        log.info("and capabilities: ${capabilities.getVersion()}, ${capabilities.getPlatform()}")
		driver = new RemoteWebDriver(
                new URL("http://${userName}:${accessKey}@${server}:${port}/wd/hub"), capabilities)

		return driver
	}

}
