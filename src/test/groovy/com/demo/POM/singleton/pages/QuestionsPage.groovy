package com.demo.POM.singleton.pages

import com.demo.POM.singleton.base.BasePageObject
import groovy.util.logging.Slf4j
import org.openqa.selenium.By
import org.openqa.selenium.InvalidSelectorException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

@Slf4j
class QuestionsPage extends BasePageObject {

	@FindBy(xpath = "//*[text()='Users']")
	List<WebElement> usersTab

	QuestionsPage(WebDriver driver) {
		super(driver)
	}

	@Override
	protected By getUniqueElement() {
        log.info("Look for unique element #questions for page ${this.class.simpleName}")

        try {
            By.cssSelector("#questions")
        } catch (InvalidSelectorException ise) {
            log.error("Malformed selector: #questions", ise)
            throw ise
        } catch (Exception e) {
            log.error("Exception encountered for page ${this.class.simpleName}: ", e);
            throw e;
        }
	}

	def isUsersTabDisplayed() {
        log.info("Entering isUsersTabDisplayed method")
        log.info("return value: usersTab.size() > 0: ${usersTab.size() > 0}")

        usersTab.size() > 0
	}

}
