package selenium_API;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_04_WebElement_WebBrowser {
	
	WebDriver driver;
	WebDriverWait wait ;
	
	String WebUrl1 = "https://daominhdam.github.io/basic-form/"; 
	String WebUrl2 = "http://jqueryui.com/resources/demos/selectmenu/default.html";
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 20);
	}
	
	@Test(enabled = false)
	public void TC_00_WebBrowser() throws Exception {
		driver.close();
		driver.quit();
		driver.notify();
		driver.notifyAll();
		
		//String title = driver.getTitle();
		//String Url = driver.getCurrentUrl();
		//String pageSource = driver.getPageSource();
		
	}
	@Test(enabled = false)
	public void TC_00_WebElement() throws Exception {
		// textbox, checkbox, textare, dropdownlist...
	}
	
	@Test
	public void TC_01_Html_Dropdownlist() throws Exception {
		driver.get(WebUrl1);
		Select selector = new Select(driver.findElement(By.id("job1")));
		Assert.assertTrue(selector.isMultiple());
		
		selector.deselectByVisibleText("Automation Tester");
		Assert.assertTrue(selector.getFirstSelectedOption().getText().compareTo("Automation Tester") == 0);
		
		selector.deselectByValue("Manual Tester");
		Assert.assertTrue(selector.getFirstSelectedOption().getText().contentEquals("Manual Tester"));
		
		selector.deselectByIndex(3);
		Assert.assertTrue(selector.getFirstSelectedOption().getText().contains("Mobile Tester"));
		
		Assert.assertTrue(selector.getAllSelectedOptions().size() == 5);
	}
	
	@Test
	public void TC_01_Custom_Dropdownlist() throws Exception {
		String expectedValue = "19";
		driver.get(WebUrl2);
		driver.findElement(By.id("number-button")).click();
		
		List <WebElement> lstAllItems = driver.findElements(By.xpath("//ul[@id='number-menu']/li[@class='ui-menu-item']/div"));
		wait.until(ExpectedConditions.invisibilityOfAllElements(lstAllItems));

		for(WebElement e: lstAllItems)
		{
			if (e.getText() == expectedValue)
			{
				e.click();
				break;
			}
		}
		
		Assert.assertTrue(driver.findElement(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text']")).getText().compareTo(expectedValue) == 0);
	}
	@AfterClass
	public void afterClass() {
		
		driver.quit();
	}

}
