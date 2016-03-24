package com.demo.POM.singleton.base

import groovy.util.logging.Slf4j

@Slf4j
@Singleton
class FrameworkConfig {

    private def config = new ConfigSlurper().
            parse(new File("src/main/resources/Config.groovy")
                    .toURI().toURL())
	
	def getConfig() {
        log.info("Entering getConfig. Returning the config object")
        return config
	}
	
	public Object clone() throws CloneNotSupportedException {
        log.info("Not allowed to clone the current class")
        log.info("throwing CloneNotSupportedException for your pains ...")
        throw new CloneNotSupportedException("Cloning not allowed for ${this.class.simpleName} class");
	}

}
