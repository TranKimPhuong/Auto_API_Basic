package selenium_API;

import org.testng.annotations.Test;

import com.google.common.base.Function;

import org.testng.annotations.BeforeClass;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;


public class Topic_11_VerifyAssertAndWait {
	
	WebDriver driver;
	WebDriverWait wait;
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver,30);
	}
	
	/**
	 * driver.implicitWait (ngầm định)
	 * wait to findElement(s) presented at DOM
	 * driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);	
	 * Set 1 lần và apply cho toàn bộ testscripts
	 * @throws Exception
	 */
	@Test(enabled = false)
	public void TC_01_ImplicitWait() throws Exception
	{		
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		driver.findElement(By.xpath("//div[@id='start']/button")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='finish']/h4")).isDisplayed());
	}

	/**
	 * Using WebDriverWait lib (Rõ ràng)
	 * wait a visible element, clickabled element, ..., not present at DOM ==> behaviors
	 * wait = new WebDriverWait(driver,10);
	 * @throws Exception
	 */
	@Test(enabled = true)
	public void TC_02_ExplicitWait() throws Exception
	{		
		driver.get("http://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='ctl00_ContentPlaceholder1_ctl00_ContentPlaceholder1_RadCalendar1Panel']")));
		String msg = driver.findElement(By.xpath("//div[@id='ctl00_ContentPlaceholder1_ctl00_ContentPlaceholder1_Label1Panel']/span")).getText();
		Assert.assertTrue(msg.equals("No Selected Dates to display."));
		
		// chọn current date
		Date currentDate = new Date();
		String sDay = myDateFormat(currentDate,"yyyy/MM/dd").split("/")[2].replaceFirst("^0+(?!$)", "");		
		WebElement selectedDateBefore = driver.findElement(By.xpath("//tbody/tr/td/a[text()='" + sDay + "']")); 
		selectedDateBefore.click();
		Assert.assertTrue(driver.findElement(By.xpath("//td[contains(@class, 'rcSelected')]")).isDisplayed());
		
		// chờ Ajax loading xong
		By ajaxIcon = By.xpath("//div[@class='raDiv']");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(ajaxIcon));	
		
		// find lại nó mới hiểu
		WebElement selectedDateAfter = driver.findElement(By.xpath("//a[text()='" + sDay + "']")); 		
		wait.until(ExpectedConditions.visibilityOf(selectedDateAfter));
		
		String sDate = myDateFormat(currentDate,"EEEEE, MMMMM dd, yyyy");
		msg = driver.findElement(By.xpath("//div[@id='ctl00_ContentPlaceholder1_ctl00_ContentPlaceholder1_Label1Panel']/span")).getText();
		Assert.assertTrue(sDate.equals(msg));
	}
	
	/**
	 * using Wait/FluentWait lib (chu kì/ tần số nhất định)
	 * wait a element with condition
	 * @throws Exception
	 */
	@Test(enabled = false)
	public void TC_03_FluentWait() throws Exception
	{		
		driver.get("https://daominhdam.github.io/fluent-wait/");
		
		WebElement countdown = driver.findElement(By.xpath("//div[@id='javascript_countdown_time']"));
		wait.until(ExpectedConditions.visibilityOf(countdown));
		
		new FluentWait<WebElement> (countdown)
				.withTimeout(15, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
		 		.until(new Function<WebElement, Boolean>() {
					public Boolean apply(WebElement element) {
						boolean flag = element.getText().endsWith("00");
						System.out.println("Time: " + element.getText());
						
						return flag;
					}
		 		});

	}
	
	@Test
	public void TC_03_FluentWaitAsExplicitWait() throws Exception
	{		
		driver.get("https://daominhdam.github.io/fluent-wait/");
		
		// First - set the wait parameters
		Wait<WebDriver> waitGlobal = new FluentWait<WebDriver>(driver)
		    .withTimeout(30, TimeUnit.SECONDS) // set the timeout
		    .pollingEvery(5, TimeUnit.SECONDS); // set the interval between every 2 tries     
		   // .ignoring(NoSuchElementException.class); // don't throw this exception
		
		// Then - declare the webElement and use a function to find it
		 WebElement waitingElement = wait.until(new Function<WebDriver, WebElement>() {
		                 public WebElement apply(WebDriver driver) {
		                     return driver.findElement(By.xpath("//div[@id='javascript_countdown_time']"));
		                   }
		                 });
		 //waitingElement.click();
		 System.out.println("Time: " + waitingElement.getText());
	}
	
	/**
	 * Mình tự viết pattern cho weekday theo ý mình 
	 */
	public String myDateFormat(Date dt, String pattern) {
		Locale locale = new Locale("en", "UK");
		
		//my style
//		DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
//		dateFormatSymbols.setWeekdays(new String[]{
//		        "Unused",
//		        "Sad Sunday",
//		        "Manic Monday",
//		        "Thriving Tuesday",
//		        "Wet Wednesday",
//		        "Total Thursday",
//		        "Fat Friday",
//		        "Super Saturday",
//		});				
		
		// default way
		SimpleDateFormat simpleDateFormat =
		        new SimpleDateFormat(pattern, locale);
		return simpleDateFormat.format(dt);
	}
	
	@AfterClass
	public void afterClass() {
		driver.close();	
	}

}
