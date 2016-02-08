package com.demo.POM.singleton.base

import groovy.util.logging.Slf4j
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.Assert

@Slf4j
abstract class BasePageObject {
	protected def driver
	protected def wait
	
	private def config = FrameworkConfig.instance.config

	public BasePageObject(WebDriver driver) {
        log.info(String.format("Entered %s constructor", this.class.simpleName))
		this.driver = driver;
		wait = new WebDriverWait(this.driver, config.WEBDRIVERWAIT_TIMEOUT, config.WEBDRIVERWAIT_POLL)

        log.info("Calling isLoaded method to validate if the page is loaded.")
		isLoaded();
        log.info(String.format("Exiting %s constructor", this.class.simpleName))
	}
	
	/**
	 * Each page object must implement this method to return the identifier of a unique WebElement on that page.
	 * The presence of this unique element will be used to assert that the expected page has finished loading
	 * @return the By locator of unique element on the page
	 */
	protected abstract By getUniqueElement();
	
	protected void isLoaded() throws Error{
        log.info("Entering isLoaded method")
		//Define a list of WebElements that match the unique element locator for the page
        List<WebElement> uniqueElement = driver.findElements(getUniqueElement())

        // Assert that the unique element is present in the DOM
        Assert.assertTrue((uniqueElement.size() > 0),
				"Unique Element \'${getUniqueElement().toString()}\' not found for ${this.class.simpleName}")

        // Wait until the unique element is visible in the browser and ready to use. This helps make sure the page is
        // loaded before the next step of the tests continue.
        wait.until(ExpectedConditions.visibilityOfElementLocated(getUniqueElement()))
        log.info("Exiting isLoaded method.")
	}

}
