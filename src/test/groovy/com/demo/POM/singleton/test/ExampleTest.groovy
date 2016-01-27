package com.demo.POM.singleton.test

import com.demo.POM.singleton.BaseTest
import com.demo.POM.singleton.pages.HomePage
import com.demo.POM.singleton.pages.QuestionsPage
import org.openqa.selenium.support.PageFactory
import org.testng.Assert
import org.testng.annotations.Test

class ExampleTest extends BaseTest{

    @Test
    void clickQuestionsTest() {
        /*
        // initialize Eyes instance to the WebDriver instance
        // the parameters required are WebDriver instance, Application name, Test name, viewport size (optional).
        eyes.open(driver, "Stackoverflow", "NavigateToQuestionsPage")*/

        HomePage homePage = PageFactory.initElements(driver, HomePage.class)

        //checkWindow with a tag associated
        //eyes.checkWindow("Stackoverflow homepage")

        QuestionsPage questionsPage = homePage.clickQuestionsTab()

        //eyes.checkRegion(By.cssSelector(".youarehere #nav-questions"))

        Assert.assertTrue((boolean)questionsPage.isUsersTabDisplayed())

        // close Eyes instance.
        //TestResults testResult = eyes.close()
    }

    @Test
    void isLogoDisplayedTest() {
        /*
        // initialize Eyes instance to the WebDriver instance
        // the parameters required are WebDriver instance, Application name, Test name, viewport size (optional).
        eyes.open(driver, "Stackoverflow", "isLogoDisplayedTest")*/

        HomePage homePage = PageFactory.initElements(driver, HomePage.class)

        // checkRegion using applitools eyes
        //eyes.checkRegion(By.id("hlogo"), "stackoverflow tag")

        Assert.assertTrue(homePage.isQuestionsTabDisplayed())

        // close Eyes instance.
        //TestResults testResult = eyes.close()
    }
}
