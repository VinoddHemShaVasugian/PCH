package com.pch.kenofrontend.utilities;

import com.browserstack.local.Local;
import java.util.Map;
import java.util.HashMap;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStory;

/**
 * Created by lsharma on 12/24/2018.
 */

public class BrowserStackSerenityTest {
	public static Local bsLocal;

	@BeforeStory
	public static void setUp() throws Exception {
		    if(PropertiesReader.getInstance().getBaseConfig("ExecutionPlatform").equalsIgnoreCase("REMOTE"))
		    {
	
	    
		bsLocal = new Local();
		Map<String, String> bsLocalArgs = new HashMap<String, String>();
		bsLocalArgs.put("key", "tyBx7JQpeWhqHCnB3kfd");
		bsLocalArgs.put("onlyAutomate", "true");
		bsLocalArgs.put("v", "true");
		bsLocalArgs.put("localIdentifier", "KenoFrontendTest");
		try {
			bsLocal.start(bsLocalArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out
				.println("***running the bs local execution with running status as: **** "
						+ bsLocal.isRunning());
	}

	}
	@AfterStory
	public static void tearDown() throws Exception {
		if (bsLocal != null) {
			System.out.println("***ending the bs local execution****");
			bsLocal.stop();
		}
	}
}