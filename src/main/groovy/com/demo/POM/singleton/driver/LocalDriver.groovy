package com.demo.POM.singleton.driver

import com.demo.POM.singleton.exceptions.UnsupportedDriverTypeException
import groovy.transform.PackageScope
import groovy.transform.PackageScopeTarget
import groovy.util.logging.Slf4j
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.DesiredCapabilities

/**
 * The class extends @see com.sand.base.DriverType abstract class to provide driver creation specifically for local
 * webdriver instances.
 */

/**
 * @author SANDEEP
 *
 */

@Slf4j
@PackageScope(PackageScopeTarget.CLASS)
class LocalDriver extends DriverType {

	LocalDriver(String browser) {
		this.browser = browser
	}

	/**
     * @see com.demo.POM.singleton.singleton.driver.DriverType#createDriver()
	 * Creates an instance of a browser webdriver based on
	 * the browser name stored in the Config.groovy file.
	 * The WebDriver exes are stored in the lib folder relative
	 * to the source root folder.
	 */
	@Override
	WebDriver createDriver() {
        log.info("entering createDriver of %s class", this.class.simpleName)

		if(driver == null) {
            String path = null
			if(browser.toLowerCase().contains("firefox")) {
                log.info("browser firefox requested. Creating and returning an instance ...")
				// using webdrivermanager library to create geckodriver instance
				WebDriverManager.firefoxdriver().setup()
				return new FirefoxDriver()
			} else if (browser.toLowerCase().contains("chrome")) {
                log.info("browser chrome requested.")
                log.info("creating chrome driver instance for ${System.getProperty("os.name")} OS")

				// using webdrivermanager library to create chromedriver instance
				WebDriverManager.chromedriver().setup()
				return new ChromeDriver()
            } else if (browser.toLowerCase().contains("internet") && System.getProperty("os.name").contains("Windows")) {
                log.info("browser IE requested. creating instance ...")

                // using webdrivermanager library to create IEDriver instance
				WebDriverManager.iedriver().setup()
				return new InternetExplorerDriver(caps)
			} else if(browser.toLowerCase().contains("safari")) {
				// TODO: yet to be implemented.
				log.warn("driver creation yet to be implemented for browser: safari")
			} else {
				log.error("Unknown browser type: ${browser}. Unable to create browser instance.")
				throw new UnsupportedDriverTypeException("Unknown Browser ${browser} Type. Unable to create browser instance.")
			}
		} else {
            log.info("the requested driver type already exists. Returning an instance of the same.")
			return driver
		}
	}

	/**
	 * empty method implementation of the @DriverType#createCapabilities method
	 */
	@Override
	protected def createCapabilities() {}
}
