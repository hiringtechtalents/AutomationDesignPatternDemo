/**
 * 
 */
package com.demo.POM.singleton.driver

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.MobileCapabilityType
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.DesiredCapabilities

/**
 * @author SANDEEP
 *
 */
class MobileDriver extends Driver {
	//DesiredCapabilities caps
	
	public MobileDriver() {
		super()
	}

	/* (non-Javadoc)
	 * @see com.test.driver.Driver#createDriver(java.lang.String)
	 */
	@Override
	WebDriver createDriver() {
		if(config.seleniumConfigs.mobile.platform.equalsIgnoreCase('android')) {
			def caps = DesiredCapabilities.android()
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium")
			caps.setCapability(MobileCapabilityType.BROWSER_NAME, config.seleniumConfigs.mobile.browser)
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, config.seleniumConfigs.mobile.deviceName)
			caps.setCapability(MobileCapabilityType.PLATFORM, config.seleniumConfigs.mobile.platform)
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, config.seleniumConfigs.mobile.platformVersion)
			return (new AndroidDriver(new URL("http://${config.seleniumConfigs.mobile.ip}:${config.seleniumConfigs.mobile.port}/wd/hub"),
					caps))
		}
	}

}
