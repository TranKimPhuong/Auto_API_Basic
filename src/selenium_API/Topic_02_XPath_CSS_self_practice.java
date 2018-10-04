package selenium_API;

import org.testng.annotations.Test;

import com.sun.glass.events.KeyEvent;
import com.sun.glass.ui.Robot;
import com.sun.org.apache.bcel.internal.generic.Select;

import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.server.DriverSessions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

//http://daominhdam.890m.com/
public class Topic_02_XPath_CSS_self_practice {
	
	WebDriver driver;
	String WebUrl = "https://daominhdam.github.io/basic-form/"; 
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
	}
	
	@Test
	public void TC_01_XPath() throws InterruptedException {
		driver.navigate().to(WebUrl);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//driver.findElement(By.xpath("//input[@id='mail']")).sendKeys("kimphuong.tran1983@gmail.com");
		//driver.findElement(By.cssSelector("#email")).sendKeys("kimphuong.tran1983@gmail.com");;
		driver.findElement(By.id("mail")).sendKeys("kimphuong.tran1983@gmail.com");
		
		//kiem tra va bao textbox bi disable
		
		if (driver.findElement(By.xpath("//input[@id='under_18' and @type='radio']")).isEnabled())
			driver.findElement(By.xpath("//input[@id='under_18' and @type='radio']")).click();
		
		
		driver.findElement(By.id("edu")).sendKeys("BMA");
		
		//kiem tra text area bi disable
		
		driver.findElement(By.xpath("//select[@id='job1']/option[@value='mobile']")).click();
		//kiem tra coi element display dung chua
		
		if (driver.findElement(By.id("job2")).isEnabled())
			driver.findElement(By.xpath("//select[@id='job2']/option[@value='automation']")).click();
		//kiem tra is disable
		
		if (!driver.findElement(By.id("development")).isSelected())
		{	
			driver.findElement(By.id("development")).click();
			if (driver.findElement(By.id("design")).isSelected())
				driver.findElement(By.id("design")).click();
		}
		// kiem tra lai coi da chon dung chua
		
		//---------Slide object
		driver.findElement(By.id("slider-1")).click();
		double max = Double.parseDouble(driver.findElement(By.id("slider-1")).getAttribute("max"));
		double min = Double.parseDouble(driver.findElement(By.id("slider-1")).getAttribute("min"));
		double value = 90;
		
		Actions action = new Actions(driver);
		WebElement slider = driver.findElement(By.id("slider-1"));	
		if (value < max && value > min)
		{	
			action.dragAndDropBy(slider, (int) value, 0).build().perform();
		}else
		{
			System.out.println("The value is out of range:" + min + " to " + max);
		}	
		
		//kiem tra slider 2 bi disable
		
		//+++++++++++++++++++++++ table
		//driver.findElement(By.xpath("//a[@href='https://daominhdam.wordpress.com/2015/12/13/java-webdriver-08-kiem-tra-phan-tu-ton-tai-tren-page/']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Element is display')]")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		Assert.assertEquals("[Java – Webdriver 08] – Kiểm tra phần tử hiển thị trên page", "[Java – Webdriver 08] – Kiểm tra phần tử hiển thị trên page");
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//a[contains(text(),'Element is disable')]")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Assert.assertEquals("[Java – Webdriver 09] – Kiểm tra phần tử bị disable","[Java – Webdriver 09] – Kiểm tra phần tử bị disable");
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//a[contains(text(),'Element is select')]")).click();
		Assert.assertEquals("[Java – Webdriver 10] – Kiểm tra phần tử đã được chọn (selected)", "[Java – Webdriver 10] – Kiểm tra phần tử đã được chọn (selected)");
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		
		// multiple browser windows in different window browsers
		driver.findElement(By.linkText("Click Here")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		String originalWindowHandle = driver.getWindowHandle();
		String strMainTitle = driver.getTitle();
		Set<String> allWindowHandles = driver.getWindowHandles(); 
		
		for(String nwindow : allWindowHandles)
		{
			//way#1
			/*
			driver.switchTo().window(nwindow);
			String strNextTitle = driver.getTitle();	
			if (!strNextTitle.equalsIgnoreCase(strMainTitle))
			{
				System.out.println("title: " + driver.getTitle());
				driver.findElement(By.id("gs_htif0")).isDisplayed();			
				Assert.assertEquals(driver.getTitle(), "Google");			
			}*/
			
			//way#2
			if (!nwindow.equals(originalWindowHandle))
			{
				driver.switchTo().window(nwindow);
				System.out.println("title: " + driver.getTitle());
				driver.findElement(By.id("gs_htif0")).isDisplayed();			
				Assert.assertEquals(driver.getTitle(), "Google");	
				driver.close();
			}

		}
		driver.switchTo().window(originalWindowHandle); 
		System.out.println("title: " + driver.getTitle());
		driver.findElement(By.id("mail")).isDisplayed();
		Assert.assertEquals(driver.getTitle(), "SELENIUM WEBDRIVER FORM DEMO");
		
		// download window
		
		
        DesiredCapabilities dc = DesiredCapabilities.firefox();     
		//Create FireFox Profile object
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		
		firefoxProfile.setAcceptUntrustedCertificates(true);
		
		//Set Location to store files after downloading
		firefoxProfile.setPreference("browser.download.folderList", 2);//When set to 0, Firefox will save all files on the user’s desktop. 1 saves the files in the Downloads folder and 2 saves file at the location specified for the most recent download.
		firefoxProfile.setPreference("browser.download.dir","c:\\downloads");
		
		//Set Preference to not show file download confirmation dialogue using MIME types Of different file extension types.
		firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", 
			    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;"); 	// full type of MINE	
		
		firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
		firefoxProfile.setPreference("browser.download.manager.focusWhenStarting", false);
		firefoxProfile.setPreference("browser.download.useDownloadDir", true);
		
		firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
		firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		firefoxProfile.setPreference("browser.download.manager.closeWhenDone", true);
		firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
		firefoxProfile.setPreference("browser.download.manager.useWindow", true);
		firefoxProfile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
		firefoxProfile.setPreference("pdfjs.disabled", true);

        dc.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
        driver = new FirefoxDriver(dc);
        driver.navigate().to("http://the-internet.herokuapp.com/download/some-file.txt");
        
		//driver.findElement(By.linkText("download.txt")).click();
      
        
        
		// ko click OK button dc
		//driver = new FirefoxDriver(firefoxProfile);
        
        
		/*for(String windowHandle: driver.getWindowHandles())
		{

			driver.switchTo().window(windowHandle);
			String winTitle=driver.getTitle();
			Assert.assertEquals(driver.getTitle(), "SELENIUM WEBDRIVER FORM DEMO");
				//perform the action inside the popup

		}*/
			  
		driver.close();		

		//+++++++++++++++++++++++
		
		//refresh page
		//driver.findElement(By.xpath("//input[contains(@id,'button') and @type='submit']")).click();
		
		
	}

	@AfterClass
	public void afterClass() {
		
		//driver.navigate().refresh();
	}

}
