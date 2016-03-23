package com.demo.POM.singleton.driver

import com.demo.POM.singleton.base.FrameworkConfig
import org.openqa.selenium.WebDriver
/**
 * The base class for creating a WebDriver instance based on whether the
 * requested driver is local, remote or mobile
 */

/**
 * @author SANDEEP
 *
 */
abstract class DriverType {

	// common settings required by child classes
	protected def caps = null
	protected def driver = null
    protected def config = FrameworkConfig.instance.config
	protected def browser = null
	protected def platform = null
	protected def version = null
	protected def serverAddress = null
	protected def serverPort = null

    /*public DriverType() {
        config = FrameworkConfig.instance.getConfig()
    }*/
	
	protected abstract WebDriver createDriver();

	protected abstract def createCapabilities();
}
