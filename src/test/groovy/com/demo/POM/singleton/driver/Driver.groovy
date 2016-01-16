package com.demo.POM.singleton.driver
/**
 * The base class for creating a WebDriver instance based on whether the
 * requested driver is local, remote or mobile
 */

/**
 * @author SANDEEP
 *
 */

import org.openqa.selenium.WebDriver

import com.demo.POM.singleton.FrameworkConfig


abstract class Driver {
	
	protected def driver
	protected def config
	
	public Driver() {
		config = FrameworkConfig.getInstance().getConfig()
	}
	
	protected abstract WebDriver createDriver();
}
