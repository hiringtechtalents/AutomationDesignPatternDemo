package com.demo.POM.singleton.test

import com.demo.POM.singleton.base.BaseTest
import com.demo.POM.singleton.pages.HomePage
import com.demo.POM.singleton.pages.QuestionsPage
import groovy.util.logging.Slf4j
import org.openqa.selenium.support.PageFactory
import org.testng.Assert
import org.testng.annotations.Test

@Slf4j
class ExampleTest extends BaseTest{

    @Test
    void clickQuestionsTest() {
        log.info("Entering clickQuestionsTest test method")

        // initialize Eyes instance to the WebDriver instance
        // the parameters required are WebDriver instance, Application name, Test name, viewport size (optional).
        //eyes.open(driver, "Stackoverflow", "NavigateToQuestionsPage")

        log.info("Initializing HomePage object via the PageFactory.initElements()")
        HomePage homePage = PageFactory.initElements(driver, HomePage.class)

        //checkWindow with a tag associated
        //eyes.checkWindow("Stackoverflow homepage")

        log.info("This initializes QuestionsPage class.")
        QuestionsPage questionsPage = homePage.clickQuestionsTab()

        //eyes.checkRegion(By.cssSelector(".youarehere #nav-questions"))

        Assert.assertTrue((boolean)questionsPage.isUsersTabDisplayed())

        // close Eyes instance.
        //TestResults testResult = eyes.close()

        log.info("Exiting clickQuestionsTest test method")
    }

    @Test
    void isLogoDisplayedTest() {
        log.info("Entering isLogodisplayedTest test method")

        // initialize Eyes instance to the WebDriver instance
        // the parameters required are WebDriver instance, Application name, Test name, viewport size (optional).
        //eyes.open(driver, "Stackoverflow", "isLogoDisplayedTest")

        log.info("Initializing HomePage object via the PageFactory.initElements()")
        HomePage homePage = PageFactory.initElements(driver, HomePage.class)

        // checkRegion using applitools eyes
        //eyes.checkRegion(By.id("hlogo"), "stackoverflow tag")

        Assert.assertTrue(homePage.isQuestionsTabDisplayed())

        // close Eyes instance.
        //TestResults testResult = eyes.close()

        log.info("Exiting isLogoDisplayedTest test method")
    }
}
