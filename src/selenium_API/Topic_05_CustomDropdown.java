package selenium_API;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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


public class Topic_05_CustomDropdown {
	WebDriver driver; 
	WebDriverWait wait;
	
	String WebUrl1 = "https://daominhdam.github.io/basic-form/"; 
	String WebUrl2 = "http://jqueryui.com/resources/demos/selectmenu/default.html";
	String WebUrl3 = "https://material.angular.io/components/select/examples";
	String WebUrl4 = "https://demos.telerik.com/kendo-ui/dropdownlist/index";
	String WebUrl5 = "https://mikerodham.github.io/vue-dropdowns/";

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
	public void TC_01_WebElement() throws InterruptedException {
		// textbox, checkbox, textare, dropdownlist...
	}
	
	@Test(enabled = false)
	public void TC_02_Html_Dropdown() throws InterruptedException {
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
	
	@Test(enabled = false)
	public void TC_03_CustomDropdown() throws Exception {
		//JQUERY
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
		
		//VUE
		driver.get(WebUrl5);
		SelectOneItemInDropDown("//li [@class='dropdown-toggle']", "//a [@data-v-3ec2ada6='']", "Third Option");	
		Assert.assertTrue(driver.findElement(By.xpath("//li [@class='dropdown-toggle']")).getText().equals("Third Option"));	
		
	}
	
	@Test(enabled = false)
	public void TC_04_CustomDropdown_Editableselect() throws Exception {	
		//JQuery plugin
		driver.get("http://indrimuska.github.io/jquery-editable-select/");
		
		String expectedValue = "R";
		WebElement elementParent = driver.findElement(By.xpath("//div[@id='default-place']/input"));
		elementParent.click();
		elementParent.sendKeys("R");
		Thread.sleep(1200);
		
		/*Verify: all texts contain "R" or "r"*/
		List<WebElement> allitems = driver.findElements(By.xpath("//div[@id='default-place']/ul/li[@class='es-visible']"));
		for(WebElement e:allitems) {
			System.out.println(e.getText());
			Assert.assertTrue(e.getText().toUpperCase().contains(expectedValue));
		}	
		/*Get index and select that item*/
		int indexRandom = PickNumberRandom(allitems.size());
		allitems.get(indexRandom).click();
		String selectedValue = allitems.get(indexRandom).getText();
		Thread.sleep(1200);
		
		/*Move to top and move to element*/
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		js.executeScript("window.scrollBy(0,-document.body.scrollHeight || -document.documentElement.scrollHeight)", "");		
		js.executeScript("arguments[0].scrollIntoView(true);", elementParent);
		elementParent.click();	
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='default-place']/ul/li[text()='"+ selectedValue + "']")).isDisplayed());
		
	}
	
	@Test(enabled = false)
	public void TC_05_CustomDropdown_MultiSelect() throws Exception {		
		////JQuery plugin, checkbox and text
		//---co tagname = select, ma lay roi chon no ko hieu 
		
		driver.get("http://wenzhixin.net.cn/p/multiple-select/docs/");		
		/*--chọn 1 trg 12 cái => hiện đúng cái chọn--*/
				SelectOneItemInDropDown("//p[@id='e1_t']/div/button", "//p[@id='e1_t']/div/div/ul/li/label/span", "November");
				Assert.assertTrue(driver.findElement(By.xpath("//p[@id='e1_t']/div/button/span")).getText().equals("November"));
				driver.navigate().refresh();
				Thread.sleep(1200);
				
		/*--chọn 3 cai random => hiện đúng cái chọn--*/	
				List<String> expectedValue = Arrays.asList(PickMonthRandom());	
				SelectManyItemsInDropDown("//p[@id='e1_t']/div/button","//p[@id='e1_t']/div/div/ul/li/label/span", expectedValue );
				
				CustomSort(expectedValue);				
				String[] temp = driver.findElement(By.xpath("//p[@id='e1_t']/div/button/span")).getText().split(", ");	
				List<String> actualValue = Arrays.asList(temp);
				CustomSort(actualValue);
				Assert.assertEquals(new HashSet<String>(actualValue), new HashSet<String>(expectedValue));
				driver.navigate().refresh();
				Thread.sleep(1200);
				
		/*--- chọn 5 cái  => hiện 5/12--*/
				expectedValue = Stream.of("January", "September", "October", "November", "December").collect(Collectors.toList());
				SelectManyItemsInDropDown("//p[@id='e1_t']/div/button","//p[@id='e1_t']/div/div/ul/li/label/span", expectedValue );

				//Verify : display "5 of 12 selected"
				String newExpectedValue = "";
				if (expectedValue.size() > 3 && expectedValue.size() < 12)
					newExpectedValue = String.valueOf(expectedValue.size()) + " of 12 selected";
				Thread.sleep(1200);
				Assert.assertEquals(driver.findElement(By.xpath("//p[@id='e1_t']/div/button/span")).getText(),newExpectedValue);
				driver.navigate().refresh();
				Thread.sleep(1200);
				
		/*--- chọn select all => 	Verify : Display "All selected"--*/
				SelectOneItemInDropDown("//p[@id='e1_t']/div/button", "//p[@id='e1_t']/div/div/ul/li/label", "[Select all]");
				Assert.assertTrue(driver.findElement(By.xpath("//p[@id='e1_t']/div/button/span")).getText().equals("All selected"));

	}
	
	@Test(enabled = false)
	public void TC_06_CustomDropdown_MaximunSelections() throws Exception {
	
		driver.get("https://semantic-ui.com/modules/dropdown.html");		
		Thread.sleep(1200);
		List<String> expectedValue = Stream.of("Mechanical Engineering", "React", "UI Design", "Meteor", "Ember").collect(Collectors.toList());
		SelectManyItemsInDropDown("//div[@class= 'ui fluid dropdown selection multiple']", "//div[@class='menu transition visible']/div", expectedValue);
		CustomSort(expectedValue);
		Thread.sleep(1200);
		
		// move up to top and move to element
		driver.findElement(By.xpath("//div [@class='article']")).click();	
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath("//div [@class='ui fluid dropdown selection multiple']"));
		js.executeScript("arguments[0].scrollIntoView(true);", element);			
		Thread.sleep(1200);
		
		List<String> actualValue = new ArrayList<String>(); 
		List<WebElement> allSelectedItems = driver.findElements(By.xpath("//div [@class='ui fluid dropdown selection multiple']/a"));	
		for(WebElement e: allSelectedItems) 
		{
			//System.out.println(e.getText());
			actualValue.add(e.getText());
		}
		
		CustomSort(actualValue);
		Assert.assertEquals(new HashSet<String>(actualValue), new HashSet<String>(expectedValue));
	}
	
	@Test(enabled = false)
	public void TC_07_CustomDropdown_FlagsAndCountry() throws Exception {
		driver.get("https://semantic-ui.com/modules/dropdown.html");		
		Thread.sleep(1200);
		List<String> expectedValue = Stream.of("Bangladesh", "Cayman Islands", "Vatican City", "Uganda"
				, "Togo", "Sweden", "Solomon Islands", "Rwanda", "Palestine", "North Korea"
				, "Moldova", "Zimbabwe", "Wallis and Futuna", "Albania", "Vietnam").collect(Collectors.toList());
		SelectManyItemsInDropDown("//div [@class='another dropdown example']/div [@class='ui fluid multiple search selection dropdown']", "//div [@class='menu transition visible']/div", expectedValue);
		CustomSort(expectedValue);
		Thread.sleep(1200);
		
		// move up to top and then move to element
		driver.findElement(By.xpath("//div [@class='article']")).click();	
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath("//div [@class='another dropdown example']/div [@class='ui fluid multiple search selection dropdown']"));
		js.executeScript("arguments[0].scrollIntoView(true);", element);			
		Thread.sleep(1200);
		
		List<String> actualValue = new ArrayList<String>(); 
		List<WebElement> allSelectedItems = driver.findElements(By.xpath("//div [@class='another dropdown example']/div [@class='ui fluid multiple search selection dropdown']/a"));	
		for(WebElement e: allSelectedItems) 
		{
			//System.out.println(e.getText());
			actualValue.add(e.getText());
		}
		
		CustomSort(actualValue);
		Assert.assertEquals(new HashSet<String>(actualValue), new HashSet<String>(expectedValue));
	}
	
	@AfterClass
	public void afterClass() {	
		driver.quit();
	}
	
	public int PickNumberRandom(int limit) {
		Random rd = new Random();
		return rd.nextInt(limit);
	}
	
	public String[] PickMonthRandom() {
		String[] arrReturn = new String[3];
		
		// avoid case no = 0
		int no1 = PickNumberRandom(11) + 1;
		int no2 = PickNumberRandom(11) + 1;
		int no3 = PickNumberRandom(11) + 1;
		
		System.out.println(no1);
		System.out.println(no2);
		System.out.println(no3);
		Assert.assertTrue(no1!=no2);
		Assert.assertTrue(no2!=no3);
		Assert.assertTrue(no1!=no3);
		
		arrReturn[0] = ConvertNumberToTextMonth(no1);
		arrReturn[1] = ConvertNumberToTextMonth(no2);
		arrReturn[2] = ConvertNumberToTextMonth(no3);
		
		return arrReturn;
	}
	
	public String ConvertNumberToTextMonth(int no) {
		String month = "";
		switch (no)
		{
			case 1:
				month = "January";
				break;
			case 2:
				month = "February";
				break;
			case 3:
				month = "March";
				break;
			case 4:
				month = "April";
				break;
			case 5:
				month = "May";
				break;
			case 6:
				month = "June";
				break;
			case 7:
				month = "July";
				break;
			case 8:
				month = "August";
				break;
			case 9:
				month = "September";
				break;
			case 10:
				month = "October";
				break;
			case 11:
				month = "November";
				break;
			case 12:
				month = "December";
				break;
		}
		return month;
	}
	
	public void CustomSort(List<String> expectedValue) {
		Collections.sort(expectedValue, new Comparator<String>() {
	        @Override
	        public int compare(String p1, String p2) {
	            return p1.compareTo(p2); 
	        }
	    });
		
	}
	
	public void SelectManyItemsInDropDown(String parentLocator, String childLocator, List<String> expectedValue) throws InterruptedException{		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(parentLocator));
		//js.executeScript("arguments[0].click();", element);	
		js.executeScript("arguments[0].scrollIntoView(true);", element);	
		element.click();
		Thread.sleep(1200);
		List <WebElement> lstAllItems = driver.findElements(By.xpath(childLocator));	
		wait.until(ExpectedConditions.visibilityOfAllElements(lstAllItems));
		for(WebElement e: lstAllItems) 
		{			
			int index = expectedValue.indexOf(e.getText().trim());	
			if (index>-1)
			{
				js.executeScript("arguments[0].scrollIntoView(true);", e);			
				e.click();
			}
		}
		Thread.sleep(1200);	
	}
	
	public void SelectOneItemInDropDown(String parentLocator, String childLocator, String expectedValue) throws InterruptedException{		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(parentLocator));
		//js.executeScript("arguments[0].click();", element);	
		//or
		js.executeScript("arguments[0].scrollIntoView(true);", element);	
		element.click();
		Thread.sleep(1200);
		List <WebElement> lstAllItems = driver.findElements(By.xpath(childLocator));	
		wait.until(ExpectedConditions.visibilityOfAllElements(lstAllItems));
		for(WebElement e: lstAllItems) 
		{	//		System.out.println(e.getText());
			if (e.getText().trim().equals(expectedValue))
			{
				js.executeScript("arguments[0].scrollIntoView(true);", e);			
				e.click();
				break;
			}
		}
		Thread.sleep(1200);	
	}
}
