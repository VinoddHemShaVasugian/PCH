/** 
 * 
 */
package com.pch.search.utilities;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.WritableFont;

import org.testng.IInvokedMethod;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.xml.XmlSuite;
/**
 * @author PUjjwal
 *
 */
enum MethodType{BEFORE_SUITE,BEFORE_TEST,BEFORE_CLASS,BEFORE_METHOD,TEST_METHOD,AFTER_METHOD,AFTER_CLASS,AFTER_TEST,AFTER_SUITE};

public class CustomListener extends TestListenerAdapter implements IReporter  {

	private List<TestNG_MethodRunMetadata> func1(String test, ISuite suite,IResultMap tests){
		List<TestNG_MethodRunMetadata> list = new ArrayList<TestNG_MethodRunMetadata>();
		List<IInvokedMethod> invokedMethods = suite.getAllInvokedMethods();		
		boolean isInvoked=false;
		for(ITestNGMethod test1:tests.getAllMethods()){
			for(IInvokedMethod invM:invokedMethods){
				if(invM.getTestMethod().equals(test1)){
					list.add(new TestNG_MethodRunMetadata(invM,suite,test ));					
					isInvoked=true;
					break;
				}
			}
			if(!isInvoked)
				list.add(new TestNG_MethodRunMetadata(test1, suite,test));
		}
		return list;
	}

	public void generateReport(List<XmlSuite> arg0, List<ISuite> arg1,String arg2) {	
		ISuite suite = arg1.get(0);
		Map<String,ISuiteResult> suiteResults =  suite.getResults();
		List<TestNG_MethodRunMetadata> listOfAllMethod = new ArrayList<TestNG_MethodRunMetadata>();
		for(ISuiteResult suiteResult:suiteResults.values()){
			ITestContext context=suiteResult.getTestContext();
			listOfAllMethod.addAll(func1(context.getName(),suite,context.getFailedTests()));
			listOfAllMethod.addAll(func1(context.getName(),suite,context.getPassedTests()));
			listOfAllMethod.addAll(func1(context.getName(),suite,context.getSkippedTests()));

			listOfAllMethod.addAll(func1(context.getName(),suite,context.getFailedConfigurations()));
			listOfAllMethod.addAll(func1(context.getName(),suite,context.getPassedConfigurations()));
		}	
		Collections.sort(listOfAllMethod);

		List<TestNG_Suite> suits = new ArrayList<TestNG_Suite>();
		for(TestNG_MethodRunMetadata method:listOfAllMethod){
			if(method.methodType==MethodType.TEST_METHOD){
				TestNG_Suite newSuite = suits.get(TestNG_Suite.getSuite(method.suiteName, suits));
				List<TestNG_Test> tests = newSuite.testNG_tests_inSuite;
				TestNG_Test newTest = tests.get(TestNG_Test.getTest(method.testName, tests));
				List<TestNG_Class> klasses = newTest.testNG_classes_inTest;
				TestNG_Class newCLass = klasses.get(TestNG_Class.getClass(method.className, klasses));
				List<TestNG_MethodRunMetadata> methods  = newCLass.testNG_methods_inClass;
				TestNG_MethodRunMetadata.getMethod(method, methods);	
			}
			else if(method.methodType==MethodType.AFTER_METHOD){
				if(method.executionResult==ITestResult.SKIP){
					TestNG_Suite newSuite = suits.get(TestNG_Suite.getSuite(method.suiteName, suits));
					List<TestNG_Test> tests = newSuite.testNG_tests_inSuite;
					TestNG_Test newTest = tests.get(TestNG_Test.getTest(method.testName, tests));
					List<TestNG_Class> klasses = newTest.testNG_classes_inTest;
					TestNG_Class newCLass = klasses.get(TestNG_Class.getClass(method.className, klasses));
					List<TestNG_MethodRunMetadata> methods  = newCLass.testNG_methods_inClass;
					TestNG_MethodRunMetadata.getMethod(method, methods);
					methods.get(TestNG_MethodRunMetadata.getMethod(method.configurationOfMethodName, methods)).logFile=method.logFile;					
				}
			}
		}
		try{
			ExcelUtil eu =new ExcelUtil(new File(System.getProperty("user.dir")+"//Reports//Report.xls"));
			List<Counter> counters = new ArrayList<Counter>();
			int sheetCount=1;
			int col=0;
			int row=0;
			for(TestNG_Suite testsuite:suits){				
				System.out.println("..."+testsuite.suiteName);				
				for(TestNG_Test te_st:testsuite.testNG_tests_inSuite){
					Counter counter = new Counter(te_st.testName);
					col=0;
					row=0;
					eu.createSheet(te_st.testName, sheetCount++);
					eu.writeText("Result of "+te_st.testName,col, row, 22, WritableFont.TIMES, true, Alignment.LEFT, VerticalAlignment.CENTRE, Colour.BLACK, Colour.WHITE);	
					eu.mergeCell(col, row, col+5, row);
					row++;
					for(TestNG_Class test_klass:te_st.testNG_classes_inTest){
						row=row+1;
						col=1;
						eu.writeText(test_klass.clasName, col, row, 12, WritableFont.ARIAL, true, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.GRAY_25);
						eu.mergeCell(col, row, col+3, row);
						row++;
						System.out.println("..............."+test_klass.clasName);
						for(TestNG_MethodRunMetadata test_method:test_klass.testNG_methods_inClass){
							col=1;
							eu.writeText(test_method.thisMethodname, col, row, 11, WritableFont.TIMES, false, Alignment.LEFT, VerticalAlignment.CENTRE, Colour.BLACK, Colour.WHITE);
							eu.addComment(test_method.description,col++,row);
							if(test_method.executionResult==ITestResult.FAILURE){
								++counter.failed;
								eu.writeText("FAILED", col++, row, 10, WritableFont.ARIAL, false, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.WHITE, Colour.RED);
								eu.writeLink("Screenshot",new File(test_method.screenShotFile),col++,row);
								eu.writeLink("Logs",new File(test_method.logFile),col,row);
							}
							else if(test_method.executionResult==ITestResult.SUCCESS){
								++counter.passed;
								eu.writeText("PASSED", col++, row, 10, WritableFont.ARIAL, false, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.WHITE, Colour.GREEN);
								eu.writeText("", col++, row, 10, WritableFont.ARIAL, false, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.WHITE);
								//col=col+2;
								eu.writeLink("Logs",new File(test_method.logFile),col,row);
							}
							else if(test_method.executionResult==ITestResult.SKIP){
								++counter.skipped;
								eu.writeText("SKIPPED", col++, row, 10, WritableFont.ARIAL, false, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.YELLOW);
								eu.writeText("", col++, row, 10, WritableFont.ARIAL, false, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.WHITE);
								eu.writeText("", col, row, 10, WritableFont.ARIAL, false, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.WHITE);
								if(test_method.logFile!=null)
									eu.writeLink("Logs",new File(test_method.logFile),col,row);
								else
									eu.writeText("", col, row, 10, WritableFont.ARIAL, false, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.WHITE);
							}														
							System.out.println(".................."+test_method.thisMethodname);
							row++;
						}						
					}
					counters.add(counter);
					eu.setColumnSize(1, 45);
					eu.setColumnSize(3, 20);
					eu.setColumnSize(4, 10);
				}	
				row=3;
				col=5;
				eu.createSheet("Overall Results", 0);							
				eu.writeText(suite.getName(), col, row, 12, WritableFont.ARIAL, true, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.GREY_40_PERCENT);
				eu.mergeCell(col, row, col+5, row+1);
				row=row+2;				
				col=col+2;
				eu.writeText("Passed", col++, row, 11, WritableFont.TIMES, false, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.WHITE);
				eu.writeText("Failed", col++, row, 11, WritableFont.TIMES, false, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.WHITE);
				eu.writeText("Skipped", col++, row, 11, WritableFont.TIMES, false, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.WHITE);
				eu.writeText("Pass Rate", col++, row, 11, WritableFont.TIMES, false, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.WHITE);
				int passed =0;
				int failed=0;
				int skipped=0;
				for(Counter cnt:counters){
					row++;
					col=5;
					eu.writeLink(String.format("HYPERLINK(\"#'%s'!A1\",\"%s\")",cnt.counterFor,cnt.counterFor), col, row);
					col=col+2;
					passed +=cnt.passed;
					failed+=cnt.failed;
					skipped+=cnt.skipped;
					eu.writeText(cnt.passed, col++, row, 11, WritableFont.TIMES, false, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.WHITE);
					eu.writeText(cnt.failed, col++, row, 11, WritableFont.TIMES, false, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.WHITE);
					eu.writeText(cnt.skipped, col++, row, 11, WritableFont.TIMES, false, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.WHITE);
					eu.writeText((cnt.passed*100)/(cnt.passed+cnt.failed+cnt.skipped)+"%", col++, row, 11, WritableFont.TIMES, false, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.WHITE);
				}
				row=row+2;
				col=5;
				eu.writeText("Total", col+1, row, 11, WritableFont.ARIAL, true, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.WHITE);
				col=col+2;
				if(passed>0)
					eu.writeText(passed, col++, row, 11, WritableFont.TIMES, true, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.WHITE, Colour.GREEN);
				else
					eu.writeText(passed, col++, row, 11, WritableFont.TIMES, true, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.WHITE);				
				if (failed>0)
					eu.writeText(failed, col++, row, 11, WritableFont.TIMES, true, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.WHITE, Colour.RED);
				else
					eu.writeText(failed, col++, row, 11, WritableFont.TIMES, true, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.WHITE);
				if (skipped>0)
					eu.writeText(skipped, col++, row, 11, WritableFont.TIMES, true, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.YELLOW);
				else
					eu.writeText(skipped, col++, row, 11, WritableFont.TIMES, true, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.WHITE);
				eu.writeText((passed*100)/(passed+failed+skipped)+"%", col++, row, 11, WritableFont.TIMES, true, Alignment.CENTRE, VerticalAlignment.CENTRE, Colour.BLACK, Colour.GRAY_25);

				eu.setColumnSize(5, 30);
				eu.setColumnSize(7, 10);
				eu.setColumnSize(8, 10);
				eu.setColumnSize(9, 10);
				eu.setColumnSize(10, 10);

			}
			eu.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	static int searchTestNGElement(List<?> testNGelementList,String element){
		int i=0;
		String subjectString = null;
		for(Object obj:testNGelementList){
			if(obj instanceof TestNG_MethodRunMetadata){
				subjectString=((TestNG_MethodRunMetadata)(obj)).thisMethodname;				
			}
			else{
				subjectString = obj.toString();
			}
			if(subjectString.equals(element)){
				return i;
			}
			i++;
		}
		return -1;
	}
}


class TestNG_MethodRunMetadata implements Comparable<TestNG_MethodRunMetadata>{
	public String screenShotFile;
	public String logFile;	
	public String className;
	public String testName;
	public String suiteName;
	public long runTime;
	public int executionResult;
	public MethodType methodType;
	public Throwable exception_whileRuning;
	public String thisMethodname;
	public String description;
	public String configurationOfMethodName;
	public TestNG_MethodRunMetadata(String name){
		this.thisMethodname=name;
	}

	/*
	 * Parsing TestNG resuts and testNg method metadata.
	 */

	public TestNG_MethodRunMetadata(IInvokedMethod im,ISuite suite,String test){		
		this.thisMethodname=im.getTestMethod().getMethodName();
		this.suiteName=suite.getName();
		this.runTime=im.getDate();
		this.executionResult=im.getTestResult().getStatus();
		this.description=im.getTestMethod().getDescription();
		ITestNGMethod testNgmethod =im.getTestMethod(); 
		if(testNgmethod.isTest())
		{
			methodType=MethodType.TEST_METHOD;			
			testName=test;
			String[] classNameWithPackage=testNgmethod.getTestClass().getName().split("\\.");
			className=classNameWithPackage[classNameWithPackage.length-1];
			if(this.executionResult==ITestResult.FAILURE){
				Object obj = im.getTestResult().getAttribute("screenshotFile");
				if(obj!=null){
					String ssFile = obj.toString();
					ssFile=ssFile.substring(ssFile.lastIndexOf("\\")+1);
					ssFile=".//ScreenShots//"+ssFile;
					this.screenShotFile=ssFile;
				}else{
					this.screenShotFile=null;
				}
				
			}
			Object logFileObj = im.getTestResult().getAttribute("logfilePath");
			if(logFileObj!=null){
				String logFile=logFileObj.toString();
				logFile=logFile.substring(logFile.lastIndexOf("\\")+1);
				logFile=".//Logs//"+logFile;
				//this.logFile = logFileObj.toString();
				this.logFile = logFile;
			}
			else
				this.logFile = System.getProperty("user.dir")+"//src//test//resources//ScreenshotError.png";
		}

		if(testNgmethod.isAfterMethodConfiguration()){
			methodType=MethodType.AFTER_METHOD;
			testName=test;
			String[] classNameWithPackage=testNgmethod.getTestClass().getName().split("\\.");
			className=classNameWithPackage[classNameWithPackage.length-1];
			Object logFileObj = im.getTestResult().getAttribute("logfilePath");
			this.configurationOfMethodName = ((Method)(im.getTestResult().getParameters()[0])).getName();			
			if(logFileObj!=null){
				String logFile=logFileObj.toString();
				logFile=logFile.substring(logFile.lastIndexOf("\\")+1);
				logFile=".//Logs//"+logFile;
				//this.logFile = logFileObj.toString();
				this.logFile = logFile;
			}
			/*else
				this.logFile = System.getProperty("user.dir")+"//src//test//resources//ScreenshotError.png";*/
		}
	}


	public TestNG_MethodRunMetadata(ITestNGMethod tm, ISuite suite,String test){
		this.thisMethodname=tm.getMethodName();
		this.suiteName=suite.getName();
		this.description=tm.getDescription();
		this.executionResult=ITestResult.SKIP;
		if(tm.isTest())
		{
			methodType=MethodType.TEST_METHOD;			
			String[] classNameWithPackage=tm.getTestClass().getName().split("\\.");
			className=classNameWithPackage[classNameWithPackage.length-1];
			testName=test;			
		}
	}
	public String toString(){		
		return String.format("%s %s %s %s %s %s %s", thisMethodname,methodType,executionResult,runTime,suiteName,className,testName);		
	}
	public static int getMethod(String testMethodName,List<TestNG_MethodRunMetadata> lst){
		int index=CustomListener.searchTestNGElement(lst, testMethodName);		
		if(index<0){
			TestNG_MethodRunMetadata t_method = new TestNG_MethodRunMetadata(testMethodName);
			lst.add(t_method);
			return lst.size()-1;
		}else{
			return index;
		}
	}
	public static void getMethod(TestNG_MethodRunMetadata testMethod,List<TestNG_MethodRunMetadata> lst){
		boolean isMatchingMethodFound = false;
		int i=0;
		for(TestNG_MethodRunMetadata tm:lst){
			if(tm.thisMethodname.equals(testMethod.thisMethodname)){
				isMatchingMethodFound=true;				
				lst.set(i, testMethod);
			}
			i++;
		}
		if(!isMatchingMethodFound){
			lst.add(testMethod);
		}
	}
	public int compareTo(TestNG_MethodRunMetadata arg0) {
		if (this.runTime>arg0.runTime)
			return 1;
		else if(this.runTime<arg0.runTime)
			return -1;
		else
			return 0;
	}	
}
class TestNG_Suite{
	public TestNG_Suite(String suiteName){
		this.suiteName=suiteName;
	}
	public List<TestNG_Test> testNG_tests_inSuite;
	public String suiteName;
	public String toString(){
		return suiteName;
	}


	/*
	 * This method will return the index 
	 * at which the suite element is present. 
	 */
	public static int getSuite(String suiteName,List<TestNG_Suite> lst){
		int index=CustomListener.searchTestNGElement(lst, suiteName);
		if(index<0){
			TestNG_Suite t_suite = new TestNG_Suite(suiteName);
			t_suite.testNG_tests_inSuite = new ArrayList<TestNG_Test>();
			lst.add(t_suite);
			return lst.size()-1;
		}	
		return index;
	}
}
class TestNG_Test{
	public TestNG_Test(String testName){
		this.testName=testName;
	}
	public List<TestNG_Class> testNG_classes_inTest;
	public String testName;
	public String suiteName;
	public String toString(){
		return testName;
	}
	public static int getTest(String testName,List<TestNG_Test> lst){
		int index=CustomListener.searchTestNGElement(lst, testName);
		if(index<0){
			TestNG_Test t_test = new TestNG_Test(testName);
			t_test.testNG_classes_inTest = new ArrayList<TestNG_Class>();
			lst.add(t_test);
			return lst.size()-1;
		}else{
			return index;
		}
	}
}
class TestNG_Class{	
	public TestNG_Class(String clasName){
		this.clasName=clasName;
	}
	public List<TestNG_MethodRunMetadata> testNG_methods_inClass;
	public String clasName;
	public String testName;
	public String suiteName;
	public String toString(){
		return clasName;
	}
	public static int getClass(String className,List<TestNG_Class> lst){
		int index=CustomListener.searchTestNGElement(lst, className);
		if(index<0){
			TestNG_Class t_class = new TestNG_Class(className);
			t_class.testNG_methods_inClass = new ArrayList<TestNG_MethodRunMetadata>();
			lst.add(t_class);
			return lst.size()-1;
		}else{
			return index;
		}
	}
}
class Counter{
	String counterFor;
	int passed;
	int failed;
	int skipped;
	public Counter(String counterFor){
		this.counterFor=counterFor;
	}
}
