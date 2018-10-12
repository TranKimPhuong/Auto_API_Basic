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


public class Topic_04_CustomDropdown {
	WebDriver driver; 
	WebDriverWait wait;
	
	String WebUrl1 = "https://daominhdam.github.io/basic-form/"; 
	String WebUrl2 = "http://jqueryui.com/resources/demos/selectmenu/default.html";
	String WebUrl3 = "https://material.angular.io/components/select/examples";
	String WebUrl4 = "https://demos.telerik.com/kendo-ui/dropdownlist/index";
	
	String WebUrl5 = "https://mikerodham.github.io/vue-dropdowns/";
	String WebUrl6 = "http://indrimuska.github.io/jquery-editable-select/";
	
	//advance
	String WebUrl7 = "http://wenzhixin.net.cn/p/multiple-select/docs/";
	String WebUrl8 = "https://semantic-ui.com/modules/dropdown.html";

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
	public void TC_01_Html_Dropdown() throws InterruptedException {
		driver.get(WebUrl1);
		Select selector = new Select(driver.findElement(By.id("job1")));
		Assert.assertTrue(selector.isMultiple());
		
		selector.selectByVisibleText("Automation Tester");
		Assert.assertTrue(selector.getFirstSelectedOption().getText().compareTo("Automation Tester") == 0);
		
		selector.selectByValue("Manual Tester");
		Assert.assertTrue(selector.getFirstSelectedOption().getText().contentEquals("Manual Tester"));
		
		selector.selectByIndex(3);
		Assert.assertTrue(selector.getFirstSelectedOption().getText().contains("Mobile Tester"));
				
		//Assert.assertTrue(selector.getAllSelectedOptions().size() == 5); => sai
		Assert.assertTrue(selector.getOptions().size() == 5);
	}
	
	@Test
	public void TC_02_Custom_Dropdown() throws Exception {
		/*//JQUERY
		driver.get(WebUrl2);
		SelectOneItemInDropDown("//span[@id='number-button']", "//ul[@id='number-menu']/li[@class='ui-menu-item']/div", "19");
		Assert.assertTrue(driver.findElement(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text']")).getText().compareTo("19") == 0);
		
		//ANGULAR
		driver.get(WebUrl3);
		SelectOneItemInDropDown("//select-reset-example//div[@class='mat-form-field-infix']", "//mat-option/span", "Delaware");
		Assert.assertTrue(driver.findElement(By.xpath("//select-reset-example//div[@class='mat-form-field-infix']//span[@class ='ng-tns-c21-14 ng-star-inserted']")).getText().compareTo("Delaware") == 0);
		
		//TELERIK
		driver.get(WebUrl4);
		List<String> expectedValue = Stream.of("Orange").collect(Collectors.toList());	
		SelectManyItemsInDropDown("//span[@aria-owns='color_listbox']//span[@class='k-input']", "//ul[@id='color_listbox']/li", expectedValue);			
		Set<String> actualValue = Stream.of(driver.findElement(By.xpath("//span[@aria-owns='color_listbox']//span[@class='k-input']")).getText()).collect(Collectors.toSet());
		Assert.assertEquals(actualValue, new HashSet<String>(expectedValue));	

		String WebUrl5 = "https://mikerodham.github.io/vue-dropdowns/";
		String WebUrl6 = "http://indrimuska.github.io/jquery-editable-select/";
		
		//advance
		String WebUrl7 = "http://wenzhixin.net.cn/p/multiple-select/docs/";
		String WebUrl8 = "https://semantic-ui.com/modules/dropdown.html";
		*/
		//VUE
/*		driver.get(WebUrl5);
		SelectOneItemInDropDown("//li [@class='dropdown-toggle']", "//a [@data-v-3ec2ada6='']", "Third Option");	
		Assert.assertTrue(driver.findElement(By.xpath("//li [@class='dropdown-toggle']")).getText().equals("Third Option"));	
	*/
		//filter
		driver.get(WebUrl6);
		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		//WebElement element = driver.findElement(By.xpath("//div [@id='default-place']//input [@class='form-control es-input']"));	
		//js.executeScript("$('#default-place').click();");

		
		List <WebElement> lstAllItems = driver.findElements(By.xpath("//div[@id='default-place']/ul/li"));	
		wait.until(ExpectedConditions.visibilityOfAllElements(lstAllItems));
		for(WebElement e: lstAllItems) 
		{	//		System.out.println(e.getText());
			if (e.getText().trim().contains("A"))
			{
				js.executeScript("arguments[0].scrollIntoView();", e);			
				//e.click();
				break;
			}
		}
		Thread.sleep(1200);	
		Assert.assertTrue(driver.findElement(By.xpath("//li [@class='dropdown-toggle']")).getText().equals("Third Option"));	
	
	}
	
	
	@Test(enabled = false)
	public void TC_03_Custom_Dropdown() throws Exception {
		
	}
	@AfterClass
	public void afterClass() {	
		driver.quit();
	}
	
	public void SelectManyItemsInDropDown(String parentLocator, String childLocator, List<String> expectedValue) throws InterruptedException{		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(parentLocator));
		js.executeScript("arguments[0].click();", element);	
	
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
	
	public void SelectOneItemInDropDown(String parentLocator, String childLocator, String expectedValue) throws InterruptedException{		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(parentLocator));
		js.executeScript("arguments[0].click();", element);	
		//or
		/*js.executeScript("arguments[0].scrollIntoView(false);", element);	
		element.click();*/
	
		List <WebElement> lstAllItems = driver.findElements(By.xpath(childLocator));	
		wait.until(ExpectedConditions.visibilityOfAllElements(lstAllItems));
		for(WebElement e: lstAllItems) 
		{	//		System.out.println(e.getText());
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
