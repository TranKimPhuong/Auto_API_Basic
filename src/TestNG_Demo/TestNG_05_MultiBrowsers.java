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

public class TestNG_05_MultiBrowsers {
	WebDriver driver;
	WebDriverWait wait;
	
	  @Parameters("browser")
	  @BeforeClass
	  public void beforeClass(String browserName) {
		  switch (browserName)
		  {
		  	case "firefox":
			  	driver = new FirefoxDriver();
			  	break;
			  	
		  	case "chrome":
				System.setProperty("webdriver.chrome.driver", ".//Browsers//chromedriver.exe");
				driver = new ChromeDriver();
			  	break;
			  	
		  	case "headless":
		  	    System.setProperty("webdriver.chrome.driver", ".//Browsers//chromedriver.exe");

  	    	    ChromeOptions chromeOptions = new ChromeOptions();
  	    	    chromeOptions.addArguments("headless");
  	    	    chromeOptions.addArguments("window-size=1212x911");

  	    	    driver = new ChromeDriver(chromeOptions);
			  	break;
			  	
		  	/*case "IE":
				System.setProperty("webdriver.ie.driver",".//Browsers//IEDriverServer.exe");
				driver = new InternetExplorerDriver();
		  		break;*/
		  }
		  
		  driver.manage().window().maximize();
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  wait = new WebDriverWait(driver,30);
	  }
	  
	  @Parameters({"user", "pass"})
	  @Test
	  public void TC_01_Login(String username, String password) {
		  driver.get("http://live.guru99.com");
		  
		  driver.findElement(By.xpath("//div[@class = 'footer']//a[text() = 'My Account']")).click();
			
		  driver.findElement(By.xpath("//input[@id = 'email']")).sendKeys("User1@domain.com");
		  driver.findElement(By.id("pass")).sendKeys("1@3456");
		  driver.findElement(By.id("send2")).click();	  
		  wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("send2")));
		  Assert.assertTrue(driver.findElement(By.xpath("//div[@class = 'page-title']/h1")).getText().equals("MY DASHBOARD"));

		  driver.findElement(By.xpath("//a/span[text() = 'Account']")).click();
		  driver.findElement(By.xpath("//a[text() = 'Log Out']")).click();
		  
		  wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class = 'page-title']/h2[contains(text() , 'This is demo site for')]")));
		  Assert.assertTrue(driver.findElement(By.xpath("//div[@class = 'page-title']/h2[contains(text() , 'This is demo site for')]")).isDisplayed());
	  }
	
	  @AfterClass
	  public void afterClass() {
		  driver.close();
	  }

}
