# AutomationDesignPatternsDemo

The project was started as self-learning about the various design patterns - singleton for this particular one. Since then it has evolved to include a few more patterns - Strategy and Factory method in particular.

The project uses Selenium WebDriver with Java, TestNG, Gradle/Maven with intellij.

It is an extension of the page-objects github project by Iain Rose (https://github.com/iainrose/page-objects). The project differs from the original project in the following ways:
- All the config options in the Config.groovy class in the src/main/resources,
- The page locators use the @FindBy annotation instead of the By locator,
- The project now supports running the same set of tests on a mobile platform(s) and, on saucelabs, and
- groups are no longer supported during test execution.

## Create Page Object Classes
To create Page Objects classes extend from BasePageObject class. While extending from BasePageObject you'll need to override getUniqueElement method which returns the By class reference of the unique element on the page.

Also, while creating a page object, don't forget to include the call to the base class constructor passing the WebDriver instance.

## Create a test
Creating a test entails extending from the BaseTestNGTest class. And, now you are ready to start writing tests.

## Various system properties supported by the framework
1. BROWSER - to specify the browser the tests are to be run against. For tests to be run on mobile platform, specify 'Browser' & 'Safari' for Android & iOS, respectively.
2. SELENIUM_HOST - specify the ip of the grid/appium server
3. SELENIUM_PORT - specify the port of the grid/appium server
4. DRIVERTYPE - specify the type of WebDriver to be initialized - 'local' (firefox, chrome or IE - default), remote (Selenium Grid), sauce or mobile (default type for mobile is Android)
5. MOBILE_PLATFORM - used in conjunction with DRIVERTYPE to specify if the tests are to be run against 'android' - default option, or 'ios'. 'android' is the default type if the system property is not provided
6. DEVICETYPE - Used in conjunction with MOBILE_PLATFORM to specify if the device being targeted is 'iphone' or 'ipad'. It defaults to iphone
7. SAUCE_USERNAME - saucelabs username
8. SAUCE_ACCESS_KEY - saucelabs access key
9. SELENIUM_PLATFORM - used when specifying the platform for selenium grid or saucelabs - defaults to Windows 8
10. SELENIUM_VERSION - used when specifying the browser version the tests have to be run against
11. LOGROOTLEVEL - specifies the logging level - defaults to 'DEBUG'

## Running the tests
###To run all tests (default local machine with firefox)
./gradlew clean test

###To run all tests (local machine with chrome/IE)
./gradlew clean test -DBROWSER=chrome

./gradlew clean test -DBROWSER=internetexplorer

###To run tests on sauce
./gradlew clean test -DDRIVERTYPE=sauce -DSAUCE_USERNAME=xxx -DSAUCE_ACCESS_KEY=xxxxxx

You can also set the default key in the build.gradle file and not specify the same at the commandline. Commandline arguments will help in overriding the default sauce credentials, if so required

###To run tests on Selenium Grid
./gradlew clean test -DDRIVERTYPE=remote -DSELENIUM_HOST=xxx.xxx.xxx.xxx -DSELENIUM_PORT=xxxx -DSELENIUM_PLATFORM=xxx -DBROWSER=chrome/internetexplorer -DSELENIUM_VERSION=xx
- SELENIUM_HOST defaults to localhost
- SELENIUM_PORT defaults to 4444
- SELENIUM_PLATFORM defaults to ANY
- BROWSER defaults to firefox
- SELENIUM_VERSION defaults to 42

The above five properties are set in the Config.groovy file to the default values and can be overriden at run time with the appropriate commandline arguments.

### Running tests on appium
./gradlew clean test -DDRIVERTYPE=mobile -DSELENIUM_HOST=xxx.xxx.xxx.xxx -DSELENIUM_PORT=xxxx -DBROWSER=Browser/Safari -DMOBILE_PLATFORM=android/ios -DDEVICETYPE=iphone/ipad
- SELENIUM_HOST defaults to localhost
- SELENIUM_PORT defaults to 4444 (has to be specified for mobile test run)
- MOBILE_PLATFORM defaults to android
- BROWSER defaults to firefox (has to be specified for mobile test run)
- DEVICETYPE defaults to iphone. Is relevant for test execution on iOS only.


Any and all feedback welcomed :)
