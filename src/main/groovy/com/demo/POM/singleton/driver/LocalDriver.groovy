package com.demo.POM.singleton.driver

import groovy.util.logging.Slf4j
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
class LocalDriver extends DriverType {
	
	public LocalDriver() {
		super()
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
		def browser = config.seleniumConfigs.local.browser

		if(driver == null) {
			String path
			if(browser.toLowerCase().contains("firefox")) {
                log.info("browser firefox requested. Creating and returning an instance ...")
				return new FirefoxDriver()
			} else if (browser.toLowerCase().contains("chrome")) {
                log.info("browser chrome requested.")
                log.info("creating chrome driver instance for ${System.getProperty("os.name")} OS")
				// if the OS the test is being run on is Windows then
				// use the chromedriver.exe file for driver initialization
				if (System.getProperty("os.name").contains("Windows")) {
					path = createDriverIfDriverFileExists('chromedriver.exe')
                    log.info("path of chromedriver executable: ${path}")
                } else {
                    // if the OS is not Windows
                    // then set the path of the chrome driver to chromedriver
                    path = createDriverIfDriverFileExists('chromedriver')
                    log.info("path of chromedriver executable: ${path}")
				}

				System.setProperty("webdriver.chrome.driver", path)
				return new ChromeDriver()
            } else if (browser.toLowerCase().contains("internet") && System.getProperty("os.name").contains("Windows")) {
                log.info("browser IE requested. creating instance ...")

                path = createDriverIfDriverFileExists("IEDriverServer.exe")
                log.info("path of IEDriver executable: ${path}")

                System.setProperty("webdriver.ie.driver", path)
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer()
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true)
				return new InternetExplorerDriver(capabilities)
			} else if(browser.toLowerCase().contains("safari")) {
				// TODO: yet to be implemented.
                log.info("driver creation yet to be implemented for browser: safari")
                println "driver creation yet to be implemented for browser: safari"
			} else {
                log.info("Unknown browser type: ${browser}. Unable to create browser instance.")
				throw new RuntimeException("Unknown Browser ${browser} Type. Unable to create browser instance.")
			}
		} else {
            log.info("the requested driver type already exists. Returning an instance of the same.")
			return driver
		}
	}

    private createDriverIfDriverFileExists = { String driverFileName ->
        def path = new File("${System.getProperty('user.dir')}/lib/${driverFileName}")
        if (path.exists()) {
			return path.toString()
		} else {
            log.info("${driverFileName} file could not be found at location: lib")
            println "${driverFileName} file could not be found at location: lib"
		}
	}

}
