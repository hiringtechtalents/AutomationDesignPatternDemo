package com.demo.POM.singleton.base

import groovy.util.logging.Slf4j

@Slf4j
class FrameworkConfig implements Cloneable {
	
	private static final def instance = new FrameworkConfig()
	private static def config
		
	private FrameworkConfig() {
        config = new ConfigSlurper().
				parse(new File("src/test/resources/Config.groovy")
						.toURI().toURL())
	}

	static def getInstance() {
        log.info("Entering getInstance. Returning the instance of the current class")
		return instance
	}
	
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
