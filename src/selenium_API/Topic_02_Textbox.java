package selenium_API;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeClass;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_02_Textbox {
	
	int TIMEOUT = 20;
	WebDriver driver;
	String WebUrl = "http://live.guru99.com"; 
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
		driver.get(WebUrl);
	}
	
	@Test(enabled=false)
	public void TC_01_Verify_URL_and_title() throws InterruptedException {

		driver.findElement(By.xpath("//div[@class='footer-container']//a[text()='My Account']")).click();
		driver.findElement(By.xpath("//a[@title = 'Create an Account']")).click();		
		driver.navigate().back();
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/customer/account/login/");
		driver.navigate().forward();
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/customer/account/create/");
	}
	
	@Test(enabled=false)
	public void TC_02_Login_Empty() throws InterruptedException {

		driver.findElement(By.xpath("//div[@class='footer-container']//a[@title='My Account']")).click();
		Assert.assertTrue(driver.findElement(By.id("send2")).isEnabled());
		driver.findElement(By.id("send2")).click();		
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(), "This is a required field.");
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(), "This is a required field.");
	}
	
	@Test(enabled=false)
	public void TC_03_Login_with_Email_invalid() throws InterruptedException {

		driver.findElement(By.xpath("//div[@class='footer-container']//a[@title='My Account']")).click();	
		driver.findElement(By.id("email")).sendKeys("123434234@12312.123123");
		driver.findElement(By.id("send2")).click();		
		Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(), "Please enter a valid email address. For example johndoe@domain.com.");
	}
	
	@Test(enabled=false)
	public void TC_04_Login_with_Password_invalid() throws InterruptedException {

		driver.findElement(By.xpath("//div[@class='footer-container']//a[@title='My Account']")).click();		
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123");
		driver.findElement(By.id("send2")).click();		
		Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(), "Please enter 6 or more characters without leading or trailing spaces.");
	}
	
	@Test(enabled=false)
	public void TC_05_Create_an_account() throws InterruptedException {

		driver.findElement(By.xpath("//div[@class='footer-container']//a[@title='My Account']")).click();
		Assert.assertTrue(driver.getTitle().contains("Customer Login"));
		driver.findElement(By.xpath("//a[@title = 'Create an Account']")).click();		
		Assert.assertTrue(driver.getTitle().contains("Create New Customer Account"));
		
		Random ran = new Random();
		int index = ran.nextInt(50);
		driver.findElement(By.id("firstname")).sendKeys("UserNew" + index);
		driver.findElement(By.id("middlename")).sendKeys("F");
		driver.findElement(By.id("lastname")).sendKeys("Bitshop");
		driver.findElement(By.id("email_address")).sendKeys("UserNew"+ index + "@domain.com");
		driver.findElement(By.id("password")).sendKeys("1@3456");
		driver.findElement(By.id("confirmation")).sendKeys("1@3456");
		driver.findElement(By.id("middlename")).sendKeys("123");
		driver.findElement(By.xpath("//button[@title = 'Register']")).click();		
		String text = driver.findElement(By.xpath("//li[@class='success-msg']//descendant::span")).getText();
		Assert.assertTrue(text.equalsIgnoreCase("Thank you for registering with Main Website Store."));
		
		driver.findElement(By.xpath("//a[@class='skip-link skip-account']")).click();
		driver.findElement(By.xpath("//a[@title='Log Out']")).click();		
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(),'This is demo site for')]")).isDisplayed());	
		//cai nay no chi dai khai thoi, chu no chua kiem nghiem, chac con thieu cai gi
		//Assert.assertTrue(driver.getTitle().contains("Home page"));		
	}
	
	@AfterClass
	public void afterClass() {
		
		driver.quit();
	}

}
