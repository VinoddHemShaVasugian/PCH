package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

public class TestReportZipformatter {
	// private static TestReportZipformatter zippy = new
	// TestReportZipformatter();
	private static TestReportZipformatter zip_instance;
	String srcFolder = "./CustomHTMLReport/HTMLReports/";
	String destFolder = "./CustomHTMLReport/";
	String zip_name = "Reports";
	Logger zip_log = Logger.getLogger("IWE");

	// public static void main(String a[]) {
	// zippy.createZip();
	// }

	public void createZip() {
		System.out.println("::::::::::::::::::::::::Inside Reports Zip Creation.::::::::::::::::::::::::::::::::");
		File files = new File(srcFolder);
		// Zip it.; If files exist.
		if (files.exists()) {
			try {
				zipReports(srcFolder, zip_name + ".zip");
			} catch (IOException e2) {
				zip_log.error("Error on Zip file creation:", e2);
			}
		}
	}

	/**
	 * Zip the report files. Includes the HTML and Screenshot files.
	 * 
	 * @param dirName
	 * @param nameZipFile
	 * @throws IOException
	 * 
	 * @author mperumal
	 * 
	 */
	public void zipReports(String dirName, String nameZipFile) throws IOException {
		ZipOutputStream zip = null;
		FileOutputStream fW = null;
		fW = new FileOutputStream(destFolder + "//" + nameZipFile);
		zip = new ZipOutputStream(fW);

		addFolderToZip("", dirName, zip);
		System.out.println(":::::::::::::::::::::::Sucessfully created zip file::::::::::::::::::::::::::::");
		zip.close();
		fW.close();
	}

	/**
	 * Iterate each file and Calling the method to Zip it.
	 * 
	 * @param path
	 * @param srcFolder
	 * @param zip
	 * @throws IOException
	 */
	private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws IOException {
		File folder = new File(srcFolder);
		if (folder.list().length != 0) {
			for (String fileName : folder.list()) {
				if (path.equals("")) {
					// For files which present in the given source directory
					addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
				} else {
					// For files which present inside the folder of the given
					// source directory.
					addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
				}
			}
		}
	}

	/**
	 * Zipping the actual file.
	 * 
	 * @param path
	 * @param srcFile
	 * @param zip
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	private void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws IOException {
		File folder = new File(srcFile);
		// This is a recursive method it will be called if there is a folder
		// inside a folder
		if (folder.isDirectory()) {
			addFolderToZip(path, srcFile, zip);
		} else {
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = new FileInputStream(srcFile);
			zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
			while ((len = in.read(buf)) > 0) {
				zip.write(buf, 0, len);
			}
		}
	}

	/**
	 * SingleTon method to return the TestReportZipformatter instance
	 * 
	 * @return
	 * @author amahapatra
	 */
	public static TestReportZipformatter getInstance() {
		if (zip_instance == null) {
			synchronized (TestReportZipformatter.class) {
				zip_instance = new TestReportZipformatter();
			}
		}
		return zip_instance;
	}

}