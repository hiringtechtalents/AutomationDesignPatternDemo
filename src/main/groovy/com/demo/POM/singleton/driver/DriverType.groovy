package com.demo.POM.singleton.driver

import com.demo.POM.singleton.base.FrameworkConfig
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.DesiredCapabilities

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

    protected def browser, platform, version, caps

	public DriverType() {
		config = FrameworkConfig.getInstance().getConfig()
	}
	
	protected abstract WebDriver createDriver();

    protected abstract DesiredCapabilities createCapabilities();
}
