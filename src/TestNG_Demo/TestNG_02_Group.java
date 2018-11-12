package TestNG_Demo;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;


public class TestNG_02_Group {
  @BeforeClass
  public void beforeClass() {
	  System.out.println("beforeClass");
  }
	
  @Test(groups = "account", priority = 5)
  public void TC_01_Test1() {
	  System.out.println("TC_01_Test1_account");
  }
  @Test(groups = "payment", enabled = false, priority = 2)
  public void TC_02_Test2() {
	  System.out.println("TC_01_Test2_payment");
  }
  @Test(groups = "account", priority = 6)
  public void TC_03_Test3() {
	  System.out.println("TC_01_Test3_account");
  }
  @Test(groups = "payment" , priority = 4)
  public void TC_04_Test4() {
	  System.out.println("TC_01_Test4_payment");
  }
  @Test(groups = "account", priority = 7)
  public void TC_05_Test5() {
	  System.out.println("TC_01_Test3_account");
  }
  @Test(groups = "account", enabled = false, priority = 3)
  public void TC_06_Test6() {
	  System.out.println("TC_01_Test3_account");
  }
  @Test(groups = "account", priority = 1)
  public void TC_07_Test7() {
	  System.out.println("TC_01_Test3_account");
  }

  @AfterClass
  public void afterClass() {
	  System.out.println("afterClass");
  }
}
