package com.demo.POM.singleton.Listeners

import com.demo.POM.singleton.base.FrameworkConfig
import com.demo.POM.singleton.driver.SauceLabsDriver
import com.demo.POM.singleton.driver.WebDriverFactory
import com.saucelabs.saucerest.SauceREST
import groovy.util.logging.Slf4j
import org.apache.commons.io.FileUtils
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.remote.RemoteWebDriver
import org.testng.ITestContext
import org.testng.ITestListener
import org.testng.ITestResult

import java.text.SimpleDateFormat

/**
 * Created by SANDEEP on 2/5/2016.
 */

@Slf4j
class TestEventsListener implements ITestListener {

    private def driver = WebDriverFactory.instance.getDriver(System.getProperty("DRIVERTYPE", "local"))
    private def filePath = "${System.getProperty('user.dir')}/screenshots/${getDateTime()}/"
    private def config = FrameworkConfig.instance.config

    /**
     * Invoked each time before a test will be invoked.
     * The <code>ITestResult</code> is only partially filled with the references to
     * class, method, start millis and status.
     *
     * @param result the partially filled <code>ITestResult</code>
     * @see ITestResult#STARTED
     */
    @Override
    void onTestStart(ITestResult result) {}

    /**
     * Invoked each time a test succeeds.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#SUCCESS
     */
    @Override
    void onTestSuccess(ITestResult result) {
        log.info("test Successful ...")
        log.info("test (System.getProperty('DRIVERTYPE').equals('sauce')) is: ${System.getProperty('DRIVERTYPE').equals('sauce')}")
        if (System.getProperty('DRIVERTYPE').equals('sauce')) {
            updateSauceResults(result)
        }
    }

    private updateSauceResults(ITestResult result) {
        log.info("Entering updateSauceResults method with ITestResult parameter from the calling method")

        def userName = config.seleniumConfigs.sauceLabs.userName
        def accessKey = config.seleniumConfigs.sauceLabs.accessKey

        def jobID = ((RemoteWebDriver) driver).getSessionId().toString()
        log.info("SauceLabs jobID: ${jobID}")
        def client = new SauceREST(userName, accessKey)
        log.info("created SauceREST client with SauceLabs username and access key")
        def sauceJob = ["name": "Test method: ${result.getMethod().getMethodName()}"]

        log.info("result.isSuccess: ${result.isSuccess()}")
        if (result.isSuccess()) {
            log.info("setting SauceLabs job ${jobID} as passed ...")
            client.jobPassed(jobID)
        } else {
            log.info("setting SauceLabs job ${jobID} as failed ...")
            client.jobFailed(jobID)
        }

        log.info("Exiting updateSauceResults method ...")
    }

    /**
     * Invoked each time a test fails. Responsible for taking screenshots every time the test fails
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#FAILURE
     */
    @Override
    void onTestFailure(ITestResult result) {
        log.info("Test failed ...")
        log.info("In onTestFailure() method of the listener %s", this.class.simpleName)

        if (config.take_screenshot) {
            log.info("config.take_screenshot set to ${config.take_screenshot}")
            log.info("^^^^^^ Error: ${result.name} test has failed!!! ^^^^^^")

            takeScreenShot(result.name.trim())

            log.info("^^^^^^ Placed screenshot in ${filePath} directory ^^^^^^")
        }

        // mark the test as failed in saucelabs provided the driver is instance of SauceLabsDriver
        log.info("test driver instanceof SauceLabsDriver is: ${driver instanceof SauceLabsDriver}")
        if (driver instanceof SauceLabsDriver) {
            log.info("updating SauceLabs result for test failure ...")
            updateSauceResults(result)
        }
    }
/**
 * Invoked each time a test is skipped.
 *
 * @param result <code>ITestResult</code> containing information about the run test
 * @see ITestResult#SKIP
 */
    @Override
    void onTestSkipped(ITestResult result) {}

    /**
     * Invoked each time a method fails but has been annotated with
     * successPercentage and this failure still keeps it within the
     * success percentage requested.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#SUCCESS_PERCENTAGE_FAILURE
     */
    @Override
    void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

    /**
     * Invoked after the test class is instantiated and before
     * any configuration method is called.
     */
    @Override
    void onStart(ITestContext context) {}

    /**
     * Invoked after all the tests have run and all their
     * Configuration methods have been called.
     */
    @Override
    void onFinish(ITestContext context) {}

    private void takeScreenShot(String methodName) {
        log.info("Entering takeScreenShot method with the parameter methodName: ${methodName}")

        def scrShotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)

        FileUtils.copyFile(scrShotFile, new File("${filePath}${methodName}${scrShotFile.name}"))

        log.info("Exiting method takeScreenShot")
    }

    private def getDateTime() {
        log.info("Entering method getDateTime")
        def now = new Date();
        def strDate = new SimpleDateFormat("dd-MM-yyyy").format(now)
        /*def strTime = new SimpleDateFormat("HH:mm:ss").format(now);
        strTime = strTime.replace(":", "-");*/

        log.info("formatted date value: ${strDate}")
        log.info("return value: D${strDate}")
        "D${strDate}"
    }
}
