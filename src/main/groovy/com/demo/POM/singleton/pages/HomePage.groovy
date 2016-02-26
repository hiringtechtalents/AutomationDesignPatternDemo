package com.demo.POM.singleton.pages

import com.demo.POM.singleton.base.BasePageObject
import groovy.util.logging.Slf4j
import org.openqa.selenium.By
import org.openqa.selenium.InvalidSelectorException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

@Slf4j
class HomePage extends BasePageObject {

    @FindBy(css = ".nav > ul > li")
	WebElement questionLink

    @FindBy(css = ".nav > ul > li")
	List<WebElement> questionsTab

    HomePage(WebDriver driver) {
		super(driver)
	}

	@Override
    protected By getUniqueElement() {
        log.info("Look for unique element div.subheader for page ${this.class.simpleName}")
        try {
            By.cssSelector("div.subheader")
        } catch (InvalidSelectorException ise) {
            log.error("Malformed selector: #questions", ise)
            throw ise
        } catch (Exception e) {
            log.error("Exception encountered on page ${this.class.simpleName}", e);
            throw e;
        }
    }

    def clickQuestionsTab() {
        log.info("Entering clickQuestionsTab method")
        log.info("clicking on questionLink")
        questionLink.click()

        log.info("return value: QuestionsPage")

        PageFactory.initElements(this.driver, QuestionsPage.class)
    }

    def isQuestionsTabDisplayed() {
        log.info("Entering isQuestionsTabDisplayed method")
        log.info("return value: questionsTab.size() > 0: ${questionsTab.size() > 0}")

        questionsTab.size() > 0
    }

}
