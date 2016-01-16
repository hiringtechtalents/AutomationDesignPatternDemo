package com.demo.POM.singleton;

import groovy.util.ConfigObject;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.demo.POM.singleton.FrameworkConfig;
import com.demo.POM.singleton.driver.WebDriverFactory;

public class BaseTest {

	protected static WebDriver driver;
    protected ConfigObject config = FrameworkConfig.getInstance().getConfig();
  
	@BeforeClass
	public void beforeClass() throws Exception {
		// create a WebDriver instance on the basis of the settings
		// provided in Config.groovy class
		driver = WebDriverFactory.getInstance().getDriver("local");
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
		loadApplication();
	}
	
	protected void loadApplication() {
		driver.manage().window().maximize();
		driver.get((String) config.get("url"));
	}

	@AfterMethod(alwaysRun = true)
	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}
	
	@AfterClass(alwaysRun=true)
	public void afterClass() {
		driver.quit();
	}

}
