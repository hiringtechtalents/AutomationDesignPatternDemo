package com.demo.POM.singleton.driver

import groovy.transform.PackageScope
import groovy.transform.PackageScopeTarget
import groovy.util.logging.Slf4j
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.remote.MobileCapabilityType
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.DesiredCapabilities
/**
 * Created by SANDEEP on 3/24/2016.
 */
@Slf4j
@PackageScope(PackageScopeTarget.CLASS)
class IOSMobileDriver extends MobileDriver {
    private def deviceType

    public IOSMobileDriver(String serverAddress, int serverPort, String browser, String platform,
                           String deviceName, String version, String deviceType) {
        super(serverAddress, serverPort)

        this.browser = browser
        device_name = deviceName
        this.platform = platform
        this.version = version
        this.deviceType = deviceType
    }

    @Override
    WebDriver createDriver() {
        log.info("entering createDriver method of %s class", this.class.simpleName)
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
            caps = DesiredCapabilities.ipad()
        }

        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium")
        caps.setCapability(MobileCapabilityType.BROWSER_NAME, browser)
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, device_name)
        caps.setCapability(MobileCapabilityType.PLATFORM, platform)
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, version)
    }
}
