package selenium_API;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeClass;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.AfterClass;


public class Topic_06_Button_CheckBox_Radio_Alert {
	
	WebDriver driver;
	WebDriverWait wait;
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 30 /*timeout in seconds*/);
	}
	
	@Test(enabled = false)
	public void TC_01_OnGuru() throws Exception
	{		
		driver.get("http://live.guru99.com/");
		
		WebElement myAccount = driver.findElement(By.xpath("//div[@class = 'footer']//a[text() = \"My Account\"]"));
		clickElementByJavascript(myAccount);
		Assert.assertTrue(driver.getCurrentUrl().equals("http://live.guru99.com/index.php/customer/account/login/"));
		
		WebElement createNewAccount = driver.findElement(By.xpath("//span[text() = \"Create an Account\"]"));
		clickElementByJavascript(createNewAccount);
		Assert.assertTrue(driver.getCurrentUrl().equals("http://live.guru99.com/index.php/customer/account/create/"));
		
	}
	
	@Test(enabled = false)
	public void TC_02_OnTelerik_Checkbox() throws Exception {	
		driver.get("http://demos.telerik.com/kendo-ui/styling/checkboxes");
		
		WebElement dualZone = driver.findElement(By.xpath("//label[text() = \"Dual-zone air conditioning\"]/preceding-sibling::input"));
		clickElementByJavascript(dualZone);
		Assert.assertTrue(dualZone.isSelected());// vi Selected chi dung cho the input
		
		clickElementByJavascript(dualZone);
		Assert.assertFalse(dualZone.isSelected());
	}
	
	@Test(enabled = false)
	public void TC_03_OnTelerik_Radio() throws Exception {	
		driver.get("http://demos.telerik.com/kendo-ui/styling/radios");
		
		WebElement petroRadio = driver.findElement(By.xpath("//label[text()= \"2.0 Petrol, 147kW\"]/preceding-sibling::input"));
		clickElementByJavascript(petroRadio);
		Assert.assertTrue(petroRadio.isSelected());

	}

	@Test(enabled = false)
	public void TC_04_AcceptAlert() throws Exception {	
		driver.get("https://daominhdam.github.io/basic-form/");
		
		WebElement alertWindow = driver.findElement(By.xpath("//button[text() = \"Click for JS Alert\"]"));
		alertWindow.click();
		wait.until(ExpectedConditions.alertIsPresent());
		
		Alert alert = driver.switchTo().alert();
		Assert.assertTrue(alert.getText().equals("I am a JS Alert"));
		Thread.sleep(1200);
		alert.accept();
		Assert.assertTrue(driver.findElement(By.xpath("//p[@id='result']")).getText().equals("You clicked an alert successfully"));
	}
	
	@Test(enabled = false)
	public void TC_05_CancelAlert() throws Exception {	
		driver.get("https://daominhdam.github.io/basic-form/");
		
		WebElement alertWindow = driver.findElement(By.xpath("//button[text() = \"Click for JS Confirm\"]"));
		alertWindow.click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		Assert.assertTrue(alert.getText().equals("I am a JS Confirm"));
		
		Thread.sleep(1200);
		alert.accept();
		Assert.assertTrue(driver.findElement(By.xpath("//p[@id='result']")).getText().equals("You clicked: Cancel"));
	}
	
	@Test(enabled = false)
	public void TC_06_FillTextforAlert() throws Exception {	
		driver.get("https://daominhdam.github.io/basic-form/");
		
		String strAlert = "huhu hoho khakha";
		WebElement alertWindow = driver.findElement(By.xpath("//button[text() = \"Click for JS Prompt\"]"));
		alertWindow.click();
		wait.until(ExpectedConditions.alertIsPresent());
		
		Alert alert = driver.switchTo().alert();
		Assert.assertTrue(alert.getText().equals("I am a JS prompt"));
		Thread.sleep(1200);
		alert.sendKeys(strAlert);
		alert.accept();
		Assert.assertTrue(driver.findElement(By.xpath("//p[@id='result']")).getText().equals("You entered: " + strAlert));
	}
	
	@Test
	public void TC_07_Herokuapp_AuthenticationAlert() throws Exception {	
		
//		driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
//		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='content']/div/p")).getText().trim().equals("Congratulations! You must have the proper credentials."));
		
		//AutoIT
//		try {
//			Runtime.getRuntime().exec("E:\\KP\\AutoIT\\HandleAuthentication.exe");
//		} catch (IOException e) {
//		   e.printStackTrace();
//		}
//		driver.get("http://the-internet.herokuapp.com/basic_auth");
//		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='content']/div/p")).getText().trim().equals("Congratulations! You must have the proper credentials."));
		
		//sikuli :  ko bit sao ko chay
		 Screen screen = new Screen();

		 driver.get("http://the-internet.herokuapp.com/basic_auth");
		 Pattern username = new Pattern("E:\\KP\\Sikuli\\Login\\Username.PNG");
		 Pattern password = new Pattern("E:\\KP\\Sikuli\\Login\\Password.PNG");
		 Pattern login = new Pattern("E:\\KP\\Sikuli\\Login\\OkButton.PNG");	 

		 screen.wait(username, 10); 
		 screen.type(username, "admin");
		 screen.type(password, "admin");
		 screen.click(login);
	}
	
	public void clickElementByJavascript(WebElement element) {
	    JavascriptExecutor je = (JavascriptExecutor) driver;
	    je.executeScript("arguments[0].click();", element);
	}
	
	@AfterClass
	public void afterClass() {
		driver.close();	
	}

}
