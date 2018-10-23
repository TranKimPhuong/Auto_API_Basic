package selenium_API;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeClass;

import java.util.HashSet;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;



import org.testng.annotations.AfterClass;


public class Topic_09_IFrame_WindowPopUp {
	
	WebDriver driver;
	WebDriverWait wait;
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", "E://KP//Software//chromedriver_win32//chromedriver.exe");
		driver = new ChromeDriver();	
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 30);
	}
	
	@Test(enabled = false)
	public void TC_01_HDFCBank_IFrame() throws Exception
	{		
		driver.get("http://www.hdfcbank.com/");
		driver.navigate().refresh();
		driver.navigate().refresh();
		driver.navigate().refresh();
		driver.navigate().refresh();
		Thread.sleep(5000);
		driver.navigate().refresh();
		Thread.sleep(5000);
		
		CloseAdvertisement();
		
		//Step 3:
		driver.switchTo().frame(driver.findElement(By.xpath("//div [@class='flipBannerWrap']//iframe")));
		String msg = driver.findElement(By.xpath("//div [@id='messageContainer']/span[@id ='messageText']")).getText();
		System.out.println(msg);
		Assert.assertTrue(msg.equals("What are you looking for?"));	
		driver.switchTo().defaultContent();

		//Step 4:
		driver.switchTo().frame(driver.findElement(By.xpath("//div [@class='slidingbanners']//iframe")));
		List<WebElement> slideImg = driver.findElements(By.xpath("//div[starts-with(@id,'prd-item-list')]//img"));
		System.out.println("Cai con lai ne:" + slideImg.size());
		Assert.assertTrue(slideImg.size() == 6);
		driver.switchTo().defaultContent();
		
		//Step 5:
		List<WebElement> frontIcons = driver.findElements(By.xpath("//div[@class='flipBanner']//img[@class='front icon']"));
		System.out.println("Cai cuoi ne:" + frontIcons.size());
		Assert.assertTrue(frontIcons.size() == 8);
	}
	
	@Test(enabled = false)
	public void TC_02_SwitchToWindows() throws Exception
	{	
		driver.get("https://daominhdam.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//a[text() = 'Click Here']")).click();
		
		String parentWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		for(String w: allWindows) {
			if(!w.equalsIgnoreCase(parentWindow))
			{
				driver.switchTo().window(w);
				Assert.assertTrue(driver.getTitle().equals("Google"));
				driver.close();
				break;
			}
		}
		driver.switchTo().window(parentWindow);
		Assert.assertTrue(driver.getTitle().equals("SELENIUM WEBDRIVER FORM DEMO"));
	}
	
	@Test(enabled = true)
	public void TC_01_HDFCBank_SwitchToWindows() throws Exception
	{	
		driver.get("http://www.hdfcbank.com/");
		CloseAdvertisement();			
		String originWindow = driver.getWindowHandle();
		Set<String> parentWindows = new HashSet<String>();
		parentWindows.add(originWindow);
		
		//Step 03 - Click Angri link -> Mở ra tab/window mới -> Switch qua tab mới
		WebElement linkAgri = driver.findElement(By.xpath("//a[text() = \"Agri\"]"));
		SwitchToWindow(linkAgri, parentWindows,"HDFC Bank Kisan Dhan Vikas e-Kendra " );			
		parentWindows.add(driver.getWindowHandle());
		Thread.sleep(3000);
		
		//Step 04 - Click Account Details link -> Mở ra tab/window mới -> Switch qua tab mới
		WebElement linkAcctDetails  = driver.findElement(By.xpath("//ul [@class='grid_list clearfix']//p[text() = \"Account Details\"]"));
		SwitchToWindow(linkAcctDetails, parentWindows,"Welcome to HDFC Bank NetBanking");
		parentWindows.add(driver.getWindowHandle());
		Thread.sleep(3000);
		
		//Step 05- Click Privacy Policy link (nằm trong frame) -> Mở ra tab/window mới -> Switch qua tab mới
		driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='footer']")));
		WebElement linkPrivacy = driver.findElement(By.xpath("//form//a[text() =\"Privacy Policy\"]"));
		SwitchToWindow(linkPrivacy, parentWindows,"HDFC Bank - Leading Bank in India, Banking Services, Private Banking, Personal Loan, Car Loan" );
		driver.switchTo().defaultContent();	
		parentWindows.add(driver.getWindowHandle());
		Thread.sleep(3000);
		
		//Step 06- Click CSR link on Privacy Policy page
		WebElement linkCSR = driver.findElement(By.xpath("//a[text() = \"CSR\"]"));
		SwitchToWindow(linkCSR, parentWindows," HDFC BANK - CSR - Homepage " );		
		Thread.sleep(3000);

		
		//Step 07 - Close tất cả popup khác - chỉ giữ lại parent window
		IsCloseAllExceptOne(originWindow);
		driver.switchTo().window(originWindow);
		Assert.assertTrue(driver.getTitle().trim().equals("HDFC Bank: Personal Banking Services ".trim()));
	}

	@AfterClass
	public void afterClass() {
		driver.close();	
	}
	
	public void CloseAdvertisement() throws Exception
	{	
		List<WebElement> lstFrames =  driver.findElements(By.tagName("iframe"));
		for(WebElement e: lstFrames)
		{
			if (e.getAttribute("id").equals("vizury-notification-template"))
			{
				// go div-close ben chrome la ra
				//Step 2:
				WebElement popup = driver.findElement(By.xpath("//iframe[@id='vizury-notification-template']"));
				driver.switchTo().frame(popup); 			
				driver.findElement(By.xpath("//div[@id='div-close']")).click();
				break;
			}			
		}
		driver.switchTo().defaultContent();	
	}

	public void SwitchToWindow(WebElement element, Set<String> parentWindows, String newTitle) throws Exception
	{	
		element.click();
		Set<String> allWindows = driver.getWindowHandles();
		for(String w: allWindows) {		
			if(!parentWindows.contains(w))
			{
				driver.switchTo().window(w);
				if (driver.getTitle().trim().equals(newTitle.trim()))
					break;
			}
		}
	}
	
	public void IsCloseAllExceptOne(String parentWindow) throws Exception
	{	
		Set<String> allWindows = driver.getWindowHandles();
		for(String w: allWindows) {
			if(!w.equalsIgnoreCase(parentWindow))
			{
				driver.switchTo().window(w);
				driver.close();
			}
		}		
	}
}
