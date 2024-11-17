package com.awardtokens;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class testtt {
	  @Test
	  public void full_reg() {
		  System.out.println("full reg ");
	  }
  @BeforeMethod
  public void beforeMethod() {
	  System.out.println("Getting contest entry from admin");
  }

  @AfterMethod
  public void afterMethod() {
	  System.out.println("Close");
  }
  @Test
  public void Silver() {
	  System.out.println("Silver reg ");
  }
  @Test
  public void Mini() {
	  System.out.println("mini reg ");
  }
 
}
