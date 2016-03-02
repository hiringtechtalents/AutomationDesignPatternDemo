/**
 * 
 */
package com.demo.POM.singleton.driver

import com.demo.POM.singleton.exceptions.UnsupportedDriverTypeException
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
    def device_name
	
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
        browser = config.seleniumConfigs.mobile.browser
        device_name = config.seleniumConfigs.mobile.deviceName
        platform = config.seleniumConfigs.mobile.platform
        version = config.seleniumConfigs.mobile.platformVersion

        if (platform.equalsIgnoreCase('android')) {
            log.info("creating AndroidDriver instance ...")
            caps = createCapabilities()
			def strCaps = "The following capabilities set for AndroidDriver:" +
                    "${caps.getCapability(MobileCapabilityType.AUTOMATION_NAME)}, ${caps.getBrowserName()}, " +
                    "${caps.getCapability(MobileCapabilityType.DEVICE_NAME)}, ${caps.getPlatform()}, " +
                    "${caps.getVersion()}."
            log.info(strCaps)
			log.info("AndroidDriver created with url: http://${host}:${port}/wd/hub")
			return (new AndroidDriver(new URL("http://${host}:${port}/wd/hub"),
					caps))
        } else {
			log.error("Unsupported driver type: ${platform}", new UnsupportedDriverTypeException())
        }

        // TODO: code to create IOSDriver instance.
    }

    @Override
    protected DesiredCapabilities createCapabilities() {
        def caps
        if (browser.equalsIgnoreCase('android')) {
            caps = DesiredCapabilities.android()

            caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium")
            caps.setCapability(MobileCapabilityType.BROWSER_NAME, browser)
            caps.setCapability(MobileCapabilityType.DEVICE_NAME, device_name)
            caps.setCapability(MobileCapabilityType.PLATFORM, platform)
            caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, version)
        }

        caps
	}

}
