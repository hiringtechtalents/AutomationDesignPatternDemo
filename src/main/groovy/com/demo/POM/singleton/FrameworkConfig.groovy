package com.demo.POM.singleton

class FrameworkConfig implements Cloneable {
	
	private static final def instance = new FrameworkConfig()
	private static def config
		
	private FrameworkConfig() {
		config = new ConfigSlurper().
				parse(new File("src/test/resources/Config.groovy")
						.toURI().toURL())
	}

	static def getInstance() {
		return instance
	}
	
	def getConfig() {
		return config
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

}
