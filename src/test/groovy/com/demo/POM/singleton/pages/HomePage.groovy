package com.demo.POM.singleton.pages

import com.demo.POM.singleton.base.BasePageObject
import groovy.util.logging.Slf4j
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

@Slf4j
class HomePage extends BasePageObject {
	@FindBy(css="div#menus")
	List<WebElement> menuBar

	@FindBy(id="nav-questions")
	WebElement questionLink
	
	@FindBy(id="nav-questions")
	List<WebElement> questionsTab

    HomePage(WebDriver driver) {
		super(driver)
	}

	@Override
    protected By getUniqueElement() {
        log.info("Look for unique element div#hmenus for page ${this.class.simpleName}")
        return By.cssSelector("div#hmenus")
    }

    def clickQuestionsTab() {
        log.info("Entering clickQuestionsTab method")
        log.info("clicking on questionLink")
        questionLink.click()

        log.info("return value: QuestionsPage")
        return PageFactory.initElements(this.driver, QuestionsPage.class)
    }

    def isQuestionsTabDisplayed() {
        log.info("Entering isQuestionsTabDisplayed method")
        log.info("return value: questionsTab.size() > 0: ${questionsTab.size() > 0}")
        return questionsTab.size() > 0
    }

}
