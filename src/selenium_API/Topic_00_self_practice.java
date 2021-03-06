package selenium_API;

import org.testng.annotations.Test;

import com.sun.prism.paint.Stop;

import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.annotations.AfterClass;
import org.testng.Assert;
import org.openqa.selenium.interactions.Actions;
	
import org.testng.annotations.AfterClass;

public class Topic_00_self_practice {
	
	WebDriver driver;
	String WebUrl = "https://daominhdam.github.io/basic-form/"; 
	String WebUrl1 = "https://www.guru99.com/become-an-instructor.html";
	
	@BeforeClass
	public void beforeClass() {

	}
	
	@Test(enabled = false)
	public void TC_00_AutoIT_Test() throws InterruptedException{
		
		driver.get(WebUrl1);
		
		Random ran = new Random();
		int index = ran.nextInt(50);
		
		driver.switchTo().frame(driver.findElement(By.id("JotFormIFrame-71343386835462")));
		driver.findElement(By.id("input_1")).sendKeys("p" + index);
		driver.findElement(By.id("input_2")).sendKeys("p" + index+ "@gmail.com");
		driver.findElement(By.id("input_3")).sendKeys("dddddp");
		
		driver.findElement(By.id("cid_7")).click();
		// tai sao no ko run vay choi oi choi oi choi choi oi 
		try
		{
			Process exec = Runtime.getRuntime().exec("F:\\KP\\AutoIT\\FileUpload.exe");
			int exitVal = exec.waitFor();
			System.out.println("Exit value: " + exitVal);
		}
		catch (IOException e) {	e.printStackTrace();}
		
		Thread.sleep(10000);
		if (driver.findElement(By.id("input_3")).isDisplayed())
		{
			System.out.println("tim dc roi tim dc roi");
			driver.findElement(By.id("input_5")).click();
		Thread.sleep(10000);
		}
		Assert.assertEquals("Your submission has been received.", driver.findElement(By.xpath("//div[@id='stage']/div/p")).getText());
	}

	@Test (enabled = false)
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
		//Actions action = new Actions(driver);
		WebElement slider = driver.findElement(By.id("slider-1"));	
		if (value < max && value > min)
			action.dragAndDropBy(slider, (int) value, 0).build().perform();
		else
			System.out.println("The value is out of range:" + min + " to " + max);
		
		//kiem tra slider 2 bi disable
		
		//+++++++++++++++++++++++ table
		//driver.findElement(By.xpath("//a[@href='https://daominhdam.wordpress.com/2015/12/13/java-webdriver-08-kiem-tra-phan-tu-ton-tai-tren-page/']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Element is display')]")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		Assert.assertEquals("[Java â€“ Webdriver 08] â€“ Kiá»ƒm tra pháº§n tá»­ hiá»ƒn thá»‹ trÃªn page", "[Java â€“ Webdriver 08] â€“ Kiá»ƒm tra pháº§n tá»­ hiá»ƒn thá»‹ trÃªn page");
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//a[contains(text(),'Element is disable')]")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Assert.assertEquals("[Java â€“ Webdriver 09] â€“ Kiá»ƒm tra pháº§n tá»­ bá»‹ disable","[Java â€“ Webdriver 09] â€“ Kiá»ƒm tra pháº§n tá»­ bá»‹ disable");
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//a[contains(text(),'Element is select')]")).click();
		Assert.assertEquals("[Java â€“ Webdriver 10] â€“ Kiá»ƒm tra pháº§n tá»­ Ä‘Ã£ Ä‘Æ°á»£c chá»�n (selected)", "[Java â€“ Webdriver 10] â€“ Kiá»ƒm tra pháº§n tá»­ Ä‘Ã£ Ä‘Æ°á»£c chá»�n (selected)");
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		// multiple browser windows in different window browsers
		driver.findElement(By.linkText("Click Here")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);		
		String originalWindowHandle = driver.getWindowHandle();
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
		// using wget
		driver.findElement(By.linkText("download.txt")).click();
		WebElement downloadBtn = driver.findElement(By.linkText("download.txt"));
		String sourceLocation = downloadBtn.getAttribute("href");
        String wget_cmd = "cmd /c E:\\KP\\Software\\Wget\\wget.exe -P E:\\KP\\Download --no-check-certificate " + sourceLocation;
		
        try {
			Process exec = Runtime.getRuntime().exec(wget_cmd);
			int exitVal = exec.waitFor();
			System.out.println("Exit value: " + exitVal);
		}catch (InterruptedException | IOException ex){
			System.out.println(ex.toString());
		}
		

		//+++++++++++++++++++++++
		
		//refresh page
		//driver.findElement(By.xpath("//input[contains(@id,'button') and @type='submit']")).click();
		
		
	}
	
	@Test(enabled = false)
	public void TC_02_test_SuiteCRM() throws InterruptedException {
		driver.navigate().to("http://27.74.255.96:8070/suitecrm/index.php");
		
		driver.findElement(By.id("user_name")).clear();
		 driver.findElement(By.id("user_name")).sendKeys("admin");

		 driver.findElement(By.id("user_password")).clear();
		 driver.findElement(By.id("user_password")).sendKeys("RAPtor@1234");

		 driver.findElement(By.name("Login")).click();
		 driver.quit();
		 
	}
	 
	
	@AfterClass	
	public void afterClass() {
		
		driver.quit();

	}

}
