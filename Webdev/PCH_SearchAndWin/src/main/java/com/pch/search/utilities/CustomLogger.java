package com.pch.search.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomLogger {

	private static Map<Long,LogFile> threadNFileMap = new ConcurrentHashMap<Long, LogFile>();
	private static String logsDirectory = System.getProperty("user.dir")+"\\Reports\\Logs\\";
	
	public static void createContext(){

		try{
			long threadId = Thread.currentThread().getId();
			if(!threadNFileMap.containsKey(threadId)){
				//System.out.println("thread id "+threadId+" is not found in map..creating new");
				File f = new File(logsDirectory+new SimpleDateFormat("yyyy_MM_dd_hh.mm.ss").
						format(new Date())+"_"+threadId+".log");
				
				/*File f = new File(System.getProperty("user.dir")+"\\Reports\\Logs\\"+System.currentTimeMillis()+".log");*/

				//System.out.println("Printwriter for file "+threadId+" is created.");
				threadNFileMap.put(threadId, new LogFile(f));
				
			}else{
				throw new Error(String.format("New Context requested for thread Id - %d while a context is already running.",threadId));
			}
		}catch(FileNotFoundException fnf){
			fnf.printStackTrace();
			throw new Error();
		}
	}

	protected static String resetContext(){
		long threadId = Thread.currentThread().getId();
		String logFilePath=null;
		if(threadNFileMap.containsKey(threadId)){
			logFilePath= threadNFileMap.get(threadId).f.getAbsolutePath();
			threadNFileMap.get(threadId).pw.close();
			//System.out.println("Printwriter for file "+threadId+" is closed.");
			threadNFileMap.remove(threadId);

		}
		return logFilePath;
	}

	public static void log(String log){
		long threadId = Thread.currentThread().getId();
		/*Logs written by bots activated by thread should be logged in same File.
		 * Format of bot name = #ParentThreadId#
		 */
		String threadName = Thread.currentThread().getName();
		long parsedThreadIdFromBot;
		try{
			parsedThreadIdFromBot= Long.parseLong(Common.subString(threadName, "((?<=#))(.+)(?=#)"));
		}catch(NumberFormatException nfe){
			parsedThreadIdFromBot=Long.MIN_VALUE;
		}
		
		threadId=threadNFileMap.containsKey(threadId)?threadId:parsedThreadIdFromBot;
		
		if(threadNFileMap.containsKey(threadId)){			
			log="["+new Date()+"]    "+log;
			System.out.println(log);
			threadNFileMap.get(threadId).pw.println(log);
		}
	}
	
	public static void logException(Throwable e){
		StringWriter exceptionTrace = new StringWriter();
		e.printStackTrace(new PrintWriter(exceptionTrace));
		CustomLogger.log(exceptionTrace.toString());
	}


}

class LogFile{
	File f ;
	PrintWriter pw;
	public LogFile(File f) throws FileNotFoundException{
		this.f=f;
		pw = new PrintWriter(f);
	}
}
