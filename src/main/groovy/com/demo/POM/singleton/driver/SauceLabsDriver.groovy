package com.demo.POM.singleton.driver

import com.demo.POM.singleton.exceptions.UnsupportedDriverTypeException
import groovy.util.logging.Slf4j
import org.openqa.selenium.Platform
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
        browser = config.seleniumConfigs.sauceLabs.browser
		def userName = config.seleniumConfigs.sauceLabs.userName
		def accessKey = config.seleniumConfigs.sauceLabs.accessKey
        platform = config.seleniumConfigs.sauceLabs.os
        version = config.seleniumConfigs.sauceLabs.browserVersion
		def server = config.seleniumConfigs.sauceLabs.onDemand.server
		def port = config.seleniumConfigs.sauceLabs.onDemand.port
		
		if(driver == null) {
            log.info("Requesting ${browser} instance")
            caps = createCapabilities(browser)
		} else {
			return driver
		}

        caps.version = version
        caps.platform = Platform.fromString(platform)

        log.info("creating SauceLabsDriver instance with url: http://${userName}:${accessKey}@${server}:${port}/wd/hub")
        log.info("and capabilities: ${caps.getVersion()}, ${caps.getPlatform()}")
		driver = new RemoteWebDriver(
                new URL("http://${userName}:${accessKey}@${server}:${port}/wd/hub"), caps)

        driver
    }

    @Override
    protected DesiredCapabilities createCapabilities(Object browser) {
        def capabilities
        if (browser.equalsIgnoreCase('firefox')) {
            capabilities = DesiredCapabilities.firefox()
        } else if (browser.equalsIgnoreCase('chrome')) {
            capabilities = DesiredCapabilities.chrome()
        } else if (browser.contains('internet')) {
            capabilities = DesiredCapabilities.internetExplorer()
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true)
        } else if (browser.equalsIgnoreCase('safari')) {
            capabilities = DesiredCapabilities.safari()
        } else {
            log.error("Unsupported browser type: ${browser}. Throwing exception")
            throw new UnsupportedDriverTypeException("unsupported browser type: ${browser}")
        }
        capabilities
	}

}
