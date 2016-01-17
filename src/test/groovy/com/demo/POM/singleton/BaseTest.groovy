package com.demo.POM.singleton

import com.demo.POM.singleton.driver.WebDriverFactory
import org.testng.annotations.AfterClass
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod

class BaseTest {

	protected static def driver
	protected def config = FrameworkConfig.instance.config

	@BeforeClass
	public void beforeClass() throws Exception {
		// create a WebDriver instance on the basis of the settings
		// provided in Config.groovy class
		driver = WebDriverFactory.instance.getDriver("local")
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
		loadApplication()
	}

	protected def loadApplication() {
		driver.manage().window().maximize();
		driver.get(config.url)
	}

	@AfterMethod(alwaysRun = true)
	public void deleteAllCookies() {
		driver.manage().deleteAllCookies()
	}

	@AfterClass(alwaysRun=true)
	public void afterClass() {
		driver.quit()
	}

}
