package com.demo.POM.singleton.driver

import com.demo.POM.singleton.base.FrameworkConfig
import com.demo.POM.singleton.exceptions.UnsupportedDriverTypeException
import groovy.util.logging.Slf4j
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.UnreachableBrowserException

@Slf4j
@Singleton
public final class WebDriverFactory {
    private def localDriverInstance, mobileDriverInstance, remoteDriverInstance, sauceLabsDriverInstance

    private final config = FrameworkConfig.instance.config

    private def browser = null
    private def platform = null
    private def version = null
    private def serverAddress = null
    private def serverPort = null

    private ThreadLocal<WebDriver> driver

    public getDriver(String driverType) {
        log.info("Entering getDriver method with the param driverType: ${driverType}")
        driver = new ThreadLocal<WebDriver>() {
            @Override
            protected WebDriver initialValue() {
                switch (driverType) {
                    case "local":
                        log.info("In local driver type case block")
                        return getLocalDriverInstance() as WebDriver
                    case "remote":
                        log.info("In remote driver type case block")
                        return getRemoteDriverInstance() as WebDriver
                    case "mobile":
                        log.info("In mobile driver type case block")
                        return getMobileDriverInstance() as WebDriver
                    case "sauce":
                        log.info("In saucelabs driver type case block")
                        return getSauceLabsDriverInstance() as WebDriver
                    default:
                        log.error("UnSupported driver type requested: ${driverType}")
                        throw new UnsupportedDriverTypeException("UnSupported driver type requested: ${driverType}")
                }
            }
        }

        log.info("exiting getDriver method of ${this.class.simpleName} class")

        driver.get()
    }

    private def getLocalDriverInstance() {
        log.info("creating a singleton local driver instance ...")
        this.browser = config.seleniumConfigs.local.browser
        if (localDriverInstance == null) {
            synchronized (WebDriverFactory.class) {
                if (localDriverInstance == null) localDriverInstance = new LocalDriver(browser).createDriver()
            }
        }

        localDriverInstance
    }

    private def getRemoteDriverInstance() {
        log.info("creating a singleton remote driver instance ...")

        this.browser = config.seleniumConfigs.remote.browser
        this.serverAddress = config.seleniumConfigs.remote.ip
        this.serverPort = config.seleniumConfigs.remote.port
        this.version = config.seleniumConfigs.remote.version
        this.platform = config.seleniumConfigs.remote.platform

        if (remoteDriverInstance == null) {
            synchronized (WebDriverFactory.class) {
                if (remoteDriverInstance == null) {
                    remoteDriverInstance = new RemoteDriver(browser, serverAddress, serverPort as int, platform, version)
                            .createDriver()
                }
            }
        }

        remoteDriverInstance
    }

    private def getMobileDriverInstance() {
        log.info("creating a singleton mobile driver instance ...")
        if (mobileDriverInstance == null) {
            synchronized (WebDriverFactory.class) {
                if (mobileDriverInstance == null) {
                    try {
                        mobileDriverInstance = new MobileDriver().createDriver()
                    } catch (UnreachableBrowserException e) {
                        log.error("We were unable to contact the mobile device and/or launch the browser. Exception encountered: ${e}")
                        log.error("Please check if the correct appium port, host & browser were supplied ...")
                        throw e
                    }
                }
            }
        }

        mobileDriverInstance
    }

    private def getSauceLabsDriverInstance() {
        log.info("creating a singleton Sauce driver instance ...")

        browser = config.seleniumConfigs.sauceLabs.browser
        def userName = config.seleniumConfigs.sauceLabs.userName
        def accessKey = config.seleniumConfigs.sauceLabs.accessKey
        platform = config.seleniumConfigs.sauceLabs.os
        version = config.seleniumConfigs.sauceLabs.browserVersion
        serverAddress = config.seleniumConfigs.sauceLabs.onDemand.server
        serverPort = config.seleniumConfigs.sauceLabs.onDemand.port

        if (sauceLabsDriverInstance == null) {
            synchronized (WebDriverFactory.class) {
                if (sauceLabsDriverInstance == null) sauceLabsDriverInstance = new SauceLabsDriver().createDriver()
            }
        }

        sauceLabsDriverInstance
    }

    public void closeDriver() {
        log.info("Entering closeDriver method of ${this.class.simpleName} class")
        log.info("quitting the current active WebDriver instance")
        driver.get().quit()

        log.info("De-registering the WebDriver instance from ThreadLocal instance")
        driver.remove()

        log.info("Exiting closeDriver method of ${this.class.simpleName} class")
    }
}