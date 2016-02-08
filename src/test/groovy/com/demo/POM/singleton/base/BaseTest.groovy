package com.demo.POM.singleton.base

import com.demo.POM.singleton.driver.WebDriverFactory
import groovy.util.logging.Slf4j
import org.testng.annotations.AfterClass
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod

@Slf4j
class BaseTest {

	protected static def driver
	protected def config = FrameworkConfig.instance.config
	//protected Eyes eyes

	@BeforeClass
	public void beforeClass() throws Exception {
        log.info(String.format("Entered %s constructor", this.class.simpleName))
		// create a WebDriver instance on the basis of the settings
		// provided in Config.groovy class
        log.info("Instantiating WebDriver instance for ${System.getProperty("DRIVERTYPE", "local")} driver type")
		driver = WebDriverFactory.instance.getDriver(System.getProperty("DRIVERTYPE", "local"))

		/*eyes = new Eyes()
		eyes.apiKey = "m1OxswNrSwo6Z72bqXzcaRs5TacSeQ8RcmbXlclUYFY110"
		eyes.forceFullPageScreenshot = true
        eyes.setMatchLevel(MatchLevel.LAYOUT2)*/

        log.info(String.format("Exiting %s constructor", this.class.simpleName))
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
        log.info("setting up test ...")
        loadApplication()
	}

	protected def loadApplication() {
        log.info("Entering loadApplication")
		//driver.manage().window().maximize();

        log.info("navigating to ${config.url}")
		driver.get(config.url)
	}

	@AfterMethod(alwaysRun = true)
	public void deleteAllCookies() {
        log.info("deleting all cookies to get session ready for next test")
        driver.manage().deleteAllCookies()
	}

	@AfterClass(alwaysRun=true)
	public void afterClass() {
        log.info("closing webdriver instance")
        //eyes.abortIfNotClosed()
		WebDriverFactory.instance.closeDriver()
	}

}
