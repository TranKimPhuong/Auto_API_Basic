package selenium_API;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;


public class Topic_00_template {
	
	WebDriver driver;
	String WebUrl = "https://daominhdam.github.io/basic-form/"; 
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
	}
	
	@Test
	public void TC_01_TextBoxIsDisable(WebDriver driver, WebElement webe) throws Exception
	{		
		driver.get(WebUrl);
	}

	@AfterClass
	public void afterClass() {
		driver.close();	
	}

}
