package selenium_API;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeClass;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.annotations.AfterClass;


public class Topic_03_TextArea {
	
	WebDriver driver;
	int no = GetNoRandom();
	String inputName = "PhuongTruong";
	String inputAddress = "Tinh lo 10";
	String customerID = "";

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		driver.manage().window().maximize();

	}
	
	@Test
	public void TC_00_Login() throws Exception
	{			
		driver.get("http://demo.guru99.com/v4");

		driver.findElement(By.name("uid")).sendKeys("mngr155533");
		driver.findElement(By.name("password")).sendKeys("aqAtAda");
		driver.findElement(By.name("btnLogin")).click();	
		Assert.assertTrue(driver.getTitle().trim().equals("Guru99 Bank Manager HomePage"));
	}
	@Test
	public void TC_01_AddNewCustomer() throws Exception
	{
		driver.findElement(By.linkText("New Customer")).click();	
		driver.findElement(By.name("name")).sendKeys(inputName);
		driver.findElement(By.xpath("//input[@value = 'f']")).click();
		driver.findElement(By.name("dob")).sendKeys("1983-12-16");
	
		driver.findElement(By.name("addr")).sendKeys(inputAddress);
		driver.findElement(By.name("city")).sendKeys("HCM");
		driver.findElement(By.name("state")).sendKeys("Binh tan");
		driver.findElement(By.name("pinno")).sendKeys("123456");
		driver.findElement(By.name("telephoneno")).sendKeys("090616128888");
		driver.findElement(By.name("emailid")).sendKeys("aaa" + no + "@gmail.com");
		driver.findElement(By.name("password")).sendKeys("123456");		
		driver.findElement(By.name("sub")).click();
		Thread.sleep(1200);
		
		//Get the Customer ID of customer has been created
		customerID  = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
		System.out.println(customerID);
	}
	@Test
	public void TC_02_EditCustomer() throws Exception
	{
		driver.findElement(By.linkText("Edit Customer")).click();
		driver.findElement(By.name("cusid")).sendKeys(customerID);
		Thread.sleep(1200);
		driver.findElement(By.name("AccSubmit")).click();
			
		/*Verify Customer Name và Address*/
		Assert.assertTrue(driver.findElement(By.name("name")).getAttribute("value").equals(inputName));
		Assert.assertTrue(driver.findElement(By.name("addr")).getText().trim().equals(inputAddress));
		
		//Enter new value for all enabled fields
		String newAdd = "Nguyen Van dau";
		String newCity = "HCMCity";
		String newState = "Binh thanh";
		String newPIN = "456789";
		String newPhone = "0907520000";
		String newEmail = "eee" + no + "@gmail.com";
		
		driver.findElement(By.name("addr")).clear();
		driver.findElement(By.name("addr")).sendKeys(newAdd);
		driver.findElement(By.name("city")).clear();
		driver.findElement(By.name("city")).sendKeys(newCity);
		driver.findElement(By.name("state")).clear();
		driver.findElement(By.name("state")).sendKeys(newState);
		driver.findElement(By.name("pinno")).clear();
		driver.findElement(By.name("pinno")).sendKeys(newPIN);
		driver.findElement(By.name("telephoneno")).clear();
		driver.findElement(By.name("telephoneno")).sendKeys(newPhone);
		driver.findElement(By.name("emailid")).clear();
		driver.findElement(By.name("emailid")).sendKeys(newEmail);	
		
		driver.findElement(By.name("sub")).click();
		Thread.sleep(1200);
		
		//Verify 
		Assert.assertTrue(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText().equals(newAdd));
		Assert.assertTrue(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText().equals(newCity));
		Assert.assertTrue(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText().equals(newState));
		Assert.assertTrue(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText().equals(newPIN));
		Assert.assertTrue(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText().equals(newPhone));
		Assert.assertTrue(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText().equals(newEmail));
	}
	
	public int GetNoRandom() {
		Random rd = new Random();
		return rd.nextInt(50);
	}
	
	@AfterClass
	public void afterClass() {
		driver.close();	
	}

}
