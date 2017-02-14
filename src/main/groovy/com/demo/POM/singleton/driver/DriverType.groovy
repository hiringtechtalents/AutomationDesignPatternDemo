package com.demo.POM.singleton.driver

import groovy.transform.PackageScope
import org.openqa.selenium.WebDriver
/**
 * The base class for creating a WebDriver instance based on whether the
 * requested driver is local, remote or mobile
 */

/**
 * @author SANDEEP
 *
 */
@PackageScope
abstract class DriverType {

	// common settings required by child classes
	protected def caps = null
	protected def driver = null
	protected def browser = null
	protected def platform = null
	protected def version = null
	protected def serverAddress = null
	protected def serverPort = null

    protected abstract WebDriver createDriver();

    protected abstract def createCapabilities();
    
    protected void killDriver() {
	driver.close()
	driver.quit()
	driver = null
    }
}
