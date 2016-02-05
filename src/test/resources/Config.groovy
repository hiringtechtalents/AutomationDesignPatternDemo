url = 'http://stackoverflow.com'

// the setting is useful if we need to take a screenshot after every action
take_screenshot = false

// timeout for implicit and webdriverwait
WEBDRIVERWAIT_TIMEOUT = 30
IMPLICITWAIT_TIMEOUT = 10
// webdriverwait polling timeout in milliseconds
WEBDRIVERWAIT_POLL = 10


seleniumConfigs {
	local {
		browser = System.getProperty("BROWSER",'firefox')
	}
	remote {
		ip = System.getProperty("SELENIUM_HOST", "localhost")
		port = Integer.valueOf(System.getProperty("SELENIUM_PORT", "4444"))
		browser = System.getProperty("BROWSER",'firefox')
		version = '42'
		platform = 'ANY'
	}
	mobile {
		ip = System.getProperty("SELENIUM_HOST", "localhost")
		port = Integer.valueOf(System.getProperty("SELENIUM_PORT", "4723"))
		browser = System.getProperty("BROWSER",'Browser')
		platform = 'Android'
		deviceName = 'Android Emulator'
		platformVersion = '4.4.2'
	}
	sauceLabs {
		// the various Sauce properties are introduced by the SauceLabs
		// onDemand jenkins plugin. Using these to set the desired properties/capabilities
		// Set default values if the properties are not supplied.
		userName = System.getProperty('SAUCE_USERNAME', 'sandeep-singh-79')
		accessKey = System.getProperty('SAUCE_ACCESS_KEY', 'hIngLzBN7tzK4q+8mRTFt5vucVIGISGkfgJm/BTm8XY=')
		os = System.getProperty('SELENIUM_PLATFORM', 'Windows 8')
		browser = System.getProperty("BROWSER",'firefox')
		browserVersion = System.getProperty('SELENIUM_VERSION', '42')
		onDemand {
			server = 'ondemand.saucelabs.com'
			port = '80'
		}
	}
}