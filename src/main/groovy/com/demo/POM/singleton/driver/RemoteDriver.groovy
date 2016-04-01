/**
 * The class is responsible for creating a concrete RemoteWebDriver instance
 * 
 */
package com.demo.POM.singleton.driver

import com.demo.POM.singleton.exceptions.UnsupportedDriverTypeException
import groovy.transform.PackageScope
import groovy.transform.PackageScopeTarget
import groovy.util.logging.Slf4j
import org.openqa.selenium.Platform
import org.openqa.selenium.WebDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

/**
 * @author SANDEEP
 *
 */

@Slf4j
@PackageScope(PackageScopeTarget.CLASS)
class RemoteDriver extends DriverType {
    private static def seleniumServer

    private static String gridNodeLaunchCommand

    private static String windowsGridNodeLaunchCommand
	
	public RemoteDriver() {
		super()

		browser = config.seleniumConfigs.remote.browser
		serverAddress = config.seleniumConfigs.remote.ip
        serverPort = config.seleniumConfigs.remote.port
		platform = config.seleniumConfigs.remote.platform
		version = config.seleniumConfigs.remote.version

        try {
            def seleniumServerDir = "${System.getProperty('user.dir')}/lib"
            def filePattern = /selenium-server*/

            seleniumServer = new FileNameByRegexFinder().getFileNames(seleniumServerDir, filePattern)

            log.info("number of matching selenium server files: ${seleniumServer.size()}")

        } catch (final IllegalArgumentException e) {
            log.error("Issues observed locating the selenium-server-standalone jar file")
            log.error(e)
            throw e
        } catch (Exception e) {
            log.error("Issues observed locating the selenium-server-standalone jar file")
            log.error(e)
            throw e
        }

        gridNodeLaunchCommand = "java -jar ${seleniumServer.get(0)}"
        windowsGridNodeLaunchCommand = "cmd.exe /c start $gridNodeLaunchCommand"

        launchGridNode(serverAddress, serverPort, browser, platform, version)
	}

    private void launchGridNode(serverAddress, serverPort, browser, platform, version) {
        Process p

        if (System.getProperty("os.name").contains("Windows")) {
            windowsGridNodeLaunchCommand += " -role node -hub http://${serverAddress}:${serverPort}/grid/register"
            p = windowsGridNodeLaunchCommand.execute()
        } else {
            gridNodeLaunchCommand += " -role node -hub http://${serverAddress}:${serverPort}/grid/register"
            p = gridNodeLaunchCommand.execute()
        }

        if (!p.exitValue()) {
            log.error("grid node launch process exited with value: ${p.exitValue()}")
            log.error("Unable to launch the grid node for the supplied parameters:")
            log.error("browser: ${browser}")
            log.error("Server Address: ${serverAddress}")
            log.error("port: ${serverPort}")
            log.error("platform: ${platform}")
            log.error("browser version: ${version}")
            throw new RuntimeException("Unable to successfully launch a ")
        }
    }

    /* (non-Javadoc)
     * @see com.test.driver.Driver#createDriver(java.lang.String)
     */
	@Override
	WebDriver createDriver() {
        log.info("entering createDriver of %s class", this.class.simpleName)
		
		if(driver == null) {
            log.info("Requesting ${browser} instance")
			createCapabilities()
		} else { return driver }

		log.info("creating RemoteWebDriver instance with url: http://${serverAddress}:${serverPort}/wd/hub")
		log.info("and capabilities: ${caps.getVersion()}, ${caps.getPlatform()}")
		return (new RemoteWebDriver(
				new URL("http://${serverAddress}:${serverPort}/wd/hub"), caps))
	}

	@Override
	protected def createCapabilities() {
		if (browser.equalsIgnoreCase("firefox")) {
			caps = DesiredCapabilities.firefox()
		} else if (browser.equalsIgnoreCase("internetExplorer")) {
			caps = DesiredCapabilities.internetExplorer()
			caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true)
		} else if (browser.equalsIgnoreCase("chrome")) {
			caps = DesiredCapabilities.chrome()
		} else if (browser.equalsIgnoreCase('safari')) {
			caps = DesiredCapabilities.safari()
		} else {
			log.error("Unsupported browser type: ${browser}. Throwing RuntimeException")
			throw new UnsupportedDriverTypeException("Browser type unsupported for ${browser}")
		}

		caps.version = version
		caps.platform = Platform.fromString(platform)
	}
}
