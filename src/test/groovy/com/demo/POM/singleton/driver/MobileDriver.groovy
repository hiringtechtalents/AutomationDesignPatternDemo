/**
 * 
 */
package com.demo.POM.singleton.driver

import groovy.util.logging.Slf4j
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.MobileCapabilityType
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.DesiredCapabilities

/**
 * @author SANDEEP
 *
 */

@Slf4j
class MobileDriver extends DriverType {
	//DesiredCapabilities caps
	
	public MobileDriver() {
		super()
	}

	/* (non-Javadoc)
	 * @see com.test.driver.Driver#createDriver(java.lang.String)
	 *
	 * Only driver creation for androidDriver supported for now.
	 */
	@Override
	WebDriver createDriver() {
        log.info("entering createDriver of %s class", this.class.simpleName)
		if(config.seleniumConfigs.mobile.platform.equalsIgnoreCase('android')) {
            log.info("creating AndroidDriver instance ...")
			def caps = DesiredCapabilities.android()

			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium")
			caps.setCapability(MobileCapabilityType.BROWSER_NAME, config.seleniumConfigs.mobile.browser)
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, config.seleniumConfigs.mobile.deviceName)
			caps.setCapability(MobileCapabilityType.PLATFORM, config.seleniumConfigs.mobile.platform)
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, config.seleniumConfigs.mobile.platformVersion)
			def strCaps = "The following capabilities set for AndroidDriver:" +
                    "${caps.getCapability(MobileCapabilityType.AUTOMATION_NAME)}, ${caps.getBrowserName()}, " +
                    "${caps.getCapability(MobileCapabilityType.DEVICE_NAME)}, ${caps.getPlatform()}, " +
                    "${caps.getVersion()}."
            log.info(strCaps)
            log.info("AndroidDriver created with url: http://${config.seleniumConfigs.mobile.ip}:${config.seleniumConfigs.mobile.port}/wd/hub")
            return (new AndroidDriver(new URL("http://${config.seleniumConfigs.mobile.ip}:${config.seleniumConfigs.mobile.port}/wd/hub"),
					caps))
        } else {
            log.info("Unsupported driver type: ${config.seleniumConfigs.mobile.platform}")
        }

        // TODO: code to create IOSDriver instance.
	}

}
