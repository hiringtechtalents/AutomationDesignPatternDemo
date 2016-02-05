package com.demo.POM.singleton.driver

import com.demo.POM.singleton.FrameworkConfig
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
	
	protected def driver
	protected def config

	public DriverType() {
		config = FrameworkConfig.getInstance().getConfig()
	}
	
	protected abstract WebDriver createDriver();
}