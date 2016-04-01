/**
 * 
 */
package com.demo.POM.singleton.driver

import groovy.transform.PackageScope
import groovy.transform.PackageScopeTarget
import groovy.util.logging.Slf4j
/**
 * @author SANDEEP
 *
 */

@Slf4j
@PackageScope(PackageScopeTarget.CLASS)
abstract class MobileDriver extends DriverType {
    protected def device_name

    public MobileDriver(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress
        this.serverPort = serverPort
	}
}
