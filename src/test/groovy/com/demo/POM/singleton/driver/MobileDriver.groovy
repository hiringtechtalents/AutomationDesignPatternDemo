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

		def host = config.seleniumConfigs.mobile.ip
		def port = config.seleniumConfigs.mobile.port
		def browser = config.seleniumConfigs.mobile.browser
		def device_name = config.seleniumConfigs.mobile.deviceName
		def platform = config.seleniumConfigs.mobile.platform
		def platform_version = config.seleniumConfigs.mobile.platformVersion

		if(config.seleniumConfigs.mobile.platform.equalsIgnoreCase('android')) {
            log.info("creating AndroidDriver instance ...")
			def caps = DesiredCapabilities.android()

			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium")
			caps.setCapability(MobileCapabilityType.BROWSER_NAME, browser)
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, device_name)
			caps.setCapability(MobileCapabilityType.PLATFORM, platform)
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platform_version)
			def strCaps = "The following capabilities set for AndroidDriver:" +
                    "${caps.getCapability(MobileCapabilityType.AUTOMATION_NAME)}, ${caps.getBrowserName()}, " +
                    "${caps.getCapability(MobileCapabilityType.DEVICE_NAME)}, ${caps.getPlatform()}, " +
                    "${caps.getVersion()}."
            log.info(strCaps)
			log.info("AndroidDriver created with url: http://${host}:${port}/wd/hub")
			return (new AndroidDriver(new URL("http://${host}:${port}/wd/hub"),
					caps))
        } else {
			log.info("Unsupported driver type: ${platform}")
        }

        // TODO: code to create IOSDriver instance.
	}

}
