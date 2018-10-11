package selenium_API;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.HashSet;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
	WebDriverWait wait;
	
	String WebUrl1 = "https://daominhdam.github.io/basic-form/"; 
	String WebUrl2 = "http://jqueryui.com/resources/demos/selectmenu/default.html";
	String WebUrl3 = "https://material.angular.io/components/select/examples";
	String WebUrl4 = "https://demos.telerik.com/kendo-ui/dropdownlist/index";

	@BeforeClass
	public void beforeClass() {	
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 30);
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
	public void TC_00_WebElement() throws InterruptedException {
		// textbox, checkbox, textare, dropdownlist...
	}
	
	@Test(enabled = false)
	public void TC_01_Html_Dropdownlist() throws InterruptedException {
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
	
	@Test(enabled = false)
	public void TC_02_Custom_Dropdownlist() throws Exception {
		String expectedValue = "19";	
		String parentLocator = "//span[@id='number-button']";
		String childLocator = "//ul[@id='number-menu']/li[@class='ui-menu-item']/div";
		driver.get(WebUrl2);	
		SelectOneElementinDropDown(parentLocator, childLocator, expectedValue);
		Assert.assertTrue(driver.findElement(By.xpath(parentLocator + "/span[@class='ui-selectmenu-text']")).getText().compareTo(expectedValue) == 0);
	}
	
	@Test
	public void TC_03_Custom_Dropdownlist_otherPages() throws Exception {
		
		String expectedValue = "Delaware";	
		String parentLocator = "//select-reset-example//div[@class='mat-form-field-infix']";
		String childLocator = "//mat-option/span";
		driver.get(WebUrl3);	
		SelectOneElementinDropDown(parentLocator, childLocator, expectedValue);
		Assert.assertTrue(driver.findElement(By.xpath(parentLocator + "//span[@class ='ng-tns-c21-14 ng-star-inserted']")).getText().compareTo(expectedValue) == 0);
	

		driver.get(WebUrl4);
		String parentLocator1 = "//span[@aria-owns='color_listbox']//span[@class='k-input']";
		String childLocator1 = "//ul[@id='color_listbox']/li";
		List<String> expectedValue1 = Stream.of("Orange").collect(Collectors.toList());	
		SelectManyElementinDropDown(parentLocator1, childLocator1, expectedValue1);	
		
		Set<String> actualValue = Stream.of(driver.findElement(By.xpath(parentLocator1)).getText()).collect(Collectors.toSet());
		Set<String> expectedValueSet = new HashSet<String>(expectedValue1);	
		Assert.assertEquals(actualValue, expectedValueSet);	
	}
	
	@AfterClass
	public void afterClass() {	
		driver.quit();
	}
	
	public void SelectManyElementinDropDown(String parentLocator, String childLocator, List<String> expectedValue) throws InterruptedException{		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(parentLocator));
		//js.executeScript("arguments[0].click();", element);	
		//or
		js.executeScript("arguments[0].scrollIntoView(false);", element);	
		element.click();
		Thread.sleep(1200);
	
		List <WebElement> lstAllItems = driver.findElements(By.xpath(childLocator));	
		wait.until(ExpectedConditions.visibilityOfAllElements(lstAllItems));
		for(WebElement e: lstAllItems) 
		{			
			int index = expectedValue.indexOf(e.getText().trim());	
			if (index>-1)
			{
				js.executeScript("arguments[0].scrollIntoView();", e);			
				e.click();
				break;
			}
		}
		Thread.sleep(1200);	
	}
	
	public void SelectOneElementinDropDown(String parentLocator, String childLocator, String expectedValue) throws InterruptedException{		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(parentLocator));
		//js.executeScript("arguments[0].click();", element);	
		//or
		js.executeScript("arguments[0].scrollIntoView(false);", element);	
		element.click();
		Thread.sleep(1200);
	
		List <WebElement> lstAllItems = driver.findElements(By.xpath(childLocator));	
		wait.until(ExpectedConditions.visibilityOfAllElements(lstAllItems));
		for(WebElement e: lstAllItems) 
		{			
			if (e.getText().trim().equals(expectedValue))
			{
				js.executeScript("arguments[0].scrollIntoView();", e);			
				e.click();
				break;
			}
		}
		Thread.sleep(1200);	
	}
}
