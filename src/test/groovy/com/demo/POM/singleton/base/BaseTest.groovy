package com.demo.POM.singleton.base

import com.demo.POM.singleton.driver.WebDriverFactory
import org.testng.annotations.AfterClass
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod

class BaseTest {

	protected static def driver
	protected def config = FrameworkConfig.instance.config
	//protected Eyes eyes

	@BeforeClass
	public void beforeClass() throws Exception {
		// create a WebDriver instance on the basis of the settings
		// provided in Config.groovy class
		driver = WebDriverFactory.instance.getDriver(System.getProperty("DRIVERTYPE", "local"))

		/*eyes = new Eyes()
		eyes.apiKey = "m1OxswNrSwo6Z72bqXzcaRs5TacSeQ8RcmbXlclUYFY110"
		eyes.forceFullPageScreenshot = true
        eyes.setMatchLevel(MatchLevel.LAYOUT2)*/
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
        loadApplication()
	}

	protected def loadApplication() {
		//driver.manage().window().maximize();
		driver.get(config.url)
	}

	@AfterMethod(alwaysRun = true)
	public void deleteAllCookies() {
        driver.manage().deleteAllCookies()
	}

	@AfterClass(alwaysRun=true)
	public void afterClass() {
        //eyes.abortIfNotClosed()
		WebDriverFactory.instance.closeDriver()
	}

}
