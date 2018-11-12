package TestNG_Demo;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class TestNG_01_Annotations {
	/**
	 * The order of annotations
	 * beforeSuite => for all testcases
	 * 		beforeTest => for all testcases
	 * 			beforeClass => for all testcases
	 * 				beforeMethod => for each test case
	 * 					TC_01_Test1
	 * 				afterMethod
	 * 
	 * 				beforeMethod
	 * 					TC_01_Test2
	 * 				afterMethod
	 * 
	 * 				beforeMethod
	 * 					TC_01_Test3
	 * 				afterMethod
	 * 
	 * 				beforeMethod
	 * 					TC_01_Test4
	 * 				afterMethod
	 * 			afterClass
	 * 		afterTest
	 * afterSuite
	 */
	
  @Test
  public void TC_01_Test1() {
	  System.out.println("TC_01_Test1");
  }
  @Test
  public void TC_02_Test2() {
	  System.out.println("TC_01_Test2");
  }
  @Test
  public void TC_03_Test3() {
	  System.out.println("TC_01_Test3");
  }
  @Test
  public void TC_04_Test4() {
	  System.out.println("TC_01_Test4");
  }

  @BeforeMethod
  public void beforeMethod() {
	  System.out.println("beforeMethod");
  }

  @AfterMethod
  public void afterMethod() {
	  System.out.println("afterMethod");
  }

  @BeforeClass
  public void beforeClass() {
	  System.out.println("beforeClass");
  }

  @AfterClass
  public void afterClass() {
	  System.out.println("afterClass");
  }

  @BeforeTest
  public void beforeTest() {
	  System.out.println("beforeTest");
  }

  @AfterTest
  public void afterTest() {
	  System.out.println("afterTest");
  }

  @BeforeSuite
  public void beforeSuite() {
	  System.out.println("beforeSuite");
  }

  @AfterSuite
  public void afterSuite() {
	  System.out.println("afterSuite");
  }

}
