package TestNG_Demo;

import org.testng.annotations.Test;

import com.beust.jcommander.Parameter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

public class TestNG_04_DataProvider {
	WebDriver driver;
	WebDriverWait wait;
	
	  @BeforeClass
	  public void beforeClass() {
		  driver = new FirefoxDriver();
		  driver.manage().window().maximize();
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  wait = new WebDriverWait(driver,30);
	  }
		
	  @Test(dataProvider = "User/Pass")
	  public void TC_01_Login(String username, String password) {
		  driver.get("http://live.guru99.com");
		  driver.findElement(By.xpath("//div[@class = 'footer']//a[text() = 'My Account']")).click();
		  
		  driver.findElement(By.id("email")).sendKeys(username);
		  driver.findElement(By.id("pass")).sendKeys(password);
		  driver.findElement(By.id("send2")).click();
		  
		  driver.findElement(By.xpath("//a/span[text() = 'Account']")).click();
		  driver.findElement(By.xpath("//a[text() = 'Log Out']")).click();
		  
		  wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class = 'page-title']/h2[contains(text() , \"This is demo site for\")]")));
		  Assert.assertTrue(driver.findElement(By.xpath("//div[@class = 'page-title']/h2[contains(text() , \"This is demo site for\")]")).isDisplayed());
	  }
	
	  @DataProvider(name = "User/Pass")
	  public Object[][] UserAndPassword() {
	    return new Object[][] {
	      new Object[] { "UserNew2@domain.com", "1@3456" },
	      new Object[] { "User1@domain.com", "1@3456" }
	    };
	  }
	
	  @AfterClass
	  public void afterClass() {
		  driver.close();
	  }

}
