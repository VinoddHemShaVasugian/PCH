package com.pch.kenofrontend.utilities;

/**
 * @author lsharma Nov 28, 2018
 *
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessUtil {
	static Process prs = null;
	static String filesourcelocation = System.getProperty("user.dir")
			+ "/Executables/";

	public static void executeCommand(String command) throws IOException,
			InterruptedException {
		System.out.println("**Executing command on command prompt as-> "
				+ command);
		prs = Runtime.getRuntime().exec(filesourcelocation + command, null,
				new File(filesourcelocation));
		new Thread(new Runnable() {
			public void run() {
				BufferedReader input = new BufferedReader(
						new InputStreamReader(prs.getInputStream()));
				String line = null;
				try {
					while ((line = input.readLine()) != null)
						System.out.println(line);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		System.out
				.println("Executed command on command prompt as-> " + command);

	}

	public static void killProcess() {
		if(prs!=null){
			System.out.println("***Killing the Process which we initiated earlier in code.***");
			prs.destroy();
		}
	}

}
