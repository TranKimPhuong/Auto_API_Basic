package TestNG_Demo;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

public class TestNG_03_Loop {
	WebDriver driver;
	WebDriverWait wait;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 30);
	}

	@Test(invocationCount = 2)
	public void TC_01_Login() {
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
		driver.quit();
	}

}
