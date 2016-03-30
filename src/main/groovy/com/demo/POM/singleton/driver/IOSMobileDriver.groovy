package com.demo.POM.singleton.driver

import groovy.transform.PackageScope
import groovy.transform.PackageScopeTarget
import groovy.util.logging.Slf4j
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.remote.MobileCapabilityType
import org.openqa.selenium.remote.DesiredCapabilities

/**
 * Created by SANDEEP on 3/24/2016.
 */
@Slf4j
@PackageScope(PackageScopeTarget.CLASS)
class IOSMobileDriver extends MobileDriver {
    private def deviceType

    public IOSMobileDriver() {
        super()

        browser = config.seleniumConfigs.mobile.ios.browser
        device_name = config.seleniumConfigs.mobile.ios.deviceName
        platform = config.seleniumConfigs.mobile.ios.platform
        version = config.seleniumConfigs.mobile.ios.platformVersion
        deviceType = config.seleniumConfigs.mobile.ios.deviceType
    }

    def createIOSDriver() {
        log.info("entering createAndroidDriver method of %s class", this.class.simpleName)
        createCapabilities()
        def strCaps = "The following capabilities set for IOSDriver:" +
                "${caps.getCapability(MobileCapabilityType.AUTOMATION_NAME)}, ${caps.getBrowserName()}, " +
                "${caps.getCapability(MobileCapabilityType.DEVICE_NAME)}, ${caps.getPlatform()}, " +
                "${caps.getVersion()}."
        log.info(strCaps)
        log.info("AndroidDriver created with url: http://${serverAddress}:${serverPort}/wd/hub")
        return (new IOSDriver(new URL("http://${serverAddress}:${serverPort}/wd/hub"),
                caps))
    }

    @Override
    protected createCapabilities() {
        log.info("entering createCapabilities method of ${this.class.simpleName}")

        if (deviceType.equalsIgnoreCase('iphone')) {
            caps = DesiredCapabilities.iphone()
        } else if (deviceType.equalsIgnoreCase('ipad')) {
            caps = DesiredCapabilities.iphone()
        }

        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium")
        caps.setCapability(MobileCapabilityType.BROWSER_NAME, browser)
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, device_name)
        caps.setCapability(MobileCapabilityType.PLATFORM, platform)
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, version)
    }
}
