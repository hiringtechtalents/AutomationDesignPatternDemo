/**
 * 
 */
package com.demo.POM.singleton.driver

import groovy.transform.PackageScope
import groovy.transform.PackageScopeTarget
import groovy.util.logging.Slf4j
import org.openqa.selenium.WebDriver
/**
 * @author SANDEEP
 *
 */

@Slf4j
@PackageScope(PackageScopeTarget.CLASS)
class MobileDriver extends DriverType {
    protected def device_name
    private def mobile_platform
	
	public MobileDriver() {
        serverAddress = config.seleniumConfigs.mobile.ip
        serverPort = config.seleniumConfigs.mobile.port
        mobile_platform = config.seleniumConfigs.mobile.mobile_platform
	}

	/* (non-Javadoc)
	 * @see com.test.driver.Driver#createDriver(java.lang.String)
	 *
	 * Only driver creation for androidDriver supported for now.
	 */
	@Override
	WebDriver createDriver() {
        log.info("entering createDriver of %s class", this.class.simpleName)
        log.info("creating driver of $mobile_platform type")

        if (mobile_platform.equalsIgnoreCase('android')) {
            return createAndroidDriver()
        } else if (mobile_platform.equalsIgnoreCase("iOS")) {
            return createIOSDriver()
        }
    }

    private createIOSDriver() {
        log.info("entering createIOSDriver of ${this.class.simpleName} class")
        // log.error("Unsupported driver type: ${platform}", new UnsupportedDriverTypeException())

        return new IOSMobileDriver().createIOSDriver()
    }

    private createAndroidDriver() {
        log.info("creating AndroidDriver instance ...")

        return new AndroidMobileDriver().createAndroidDriver()
    }

    @Override
    protected createCapabilities() {}
}
