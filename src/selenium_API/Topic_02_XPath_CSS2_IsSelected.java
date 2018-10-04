package selenium_API;

import org.testng.annotations.Test;

import com.sun.org.apache.bcel.internal.generic.Select;

import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;

//http://daominhdam.890m.com/
public class Topic_02_XPath_CSS2_IsSelected {
	
	WebDriver driver;
	String WebUrl = "http://daominhdam.890m.com"; 
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	

	}
	@Test
	public void TC_01_1_TextBoxIsDisable(WebDriver driver, WebElement webe) throws Exception
	{
		if (webe.isEnabled())
		{
			System.out.println("TextBox is enable");
		}
		else
		{
			System.out.println("TextBox is disable");
		}
	}
	@Test
	public void TC_01_2_RadioBtnIsDisable(WebDriver driver, WebElement webe) throws Exception
	{
		if (webe.isEnabled())
		{
			System.out.println("Radio button is enable");
		}
		else
		{
			System.out.println("Radio button is disable");
		}
	}
	
	@Test
	public void TC_01_2_DropDownListIsDisable(WebDriver driver, WebElement webe) throws Exception
	{
		if (webe.isEnabled())
		{
			System.out.println("Dropdown List is enable");
		}
		else
		{
			System.out.println("Dropdown List is disable");
		}
	}
	@Test
	public void TC_01_XPath() {
		driver.navigate().to(WebUrl);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//input[@id='mail']")).sendKeys("kimphuong.tran1983@gmail.com");
		
		//kiem tra va bao textbox bi disable va ko nhap dc
		
		if (driver.findElement(By.xpath("//input[@id='under_18' and @type='radio']")).isEnabled())
			driver.findElement(By.xpath("//input[@id='under_18' and @type='radio']")).click();
		
		
		driver.findElement(By.xpath("//textarea[@id='edu']")).sendKeys("BMA");
		
		if (driver.findElement(By.xpath("//textarea[@id='bio']")).isEnabled())
			driver.findElement(By.xpath("//textarea[@id='bio']")).sendKeys("Tan Tao primary School \r\n mac Dinh chi high school");
		
		driver.findElement(By.xpath("//select[@id='job1']/option[@value='mobile']")).click();
		//kiem tra coi chon test dung chua
		
		if (driver.findElement(By.xpath("//select[@id='job2']")).isEnabled())
			driver.findElement(By.xpath("//select[@id='job2']/option[@value='automation']")).click();
		//kiem tra coi chon test dung chua
		
		if (!driver.findElement(By.xpath("//input[@id='development']")).isSelected())
		{	
			driver.findElement(By.xpath("//input[@id='development']")).click();
			if (driver.findElement(By.xpath("//input[@id='design']")).isSelected())
				driver.findElement(By.xpath("//input[@id='design']")).click();
		}
		// kiem tra lai coi da chon dung chua
		
		
		driver.findElement(By.xpath("//input[@id='slider-1']")).click();
		double max = Double.parseDouble(driver.findElement(By.id("slider-1")).getAttribute("max"));
		double min = Double.parseDouble(driver.findElement(By.id("slider-1")).getAttribute("min"));
		double value = 90;
		
		Actions action = new Actions(driver);
		WebElement slider = driver.findElement(By.xpath("//input[@id='slider-1']"));
		
		if (value < max && value > min)
		{	
			action.dragAndDropBy(slider, (int) value, 0).build().perform();
		}else
		{
			System.out.println("The value is out of range:" + min + " to " + max);
		}	
		
		if (driver.findElement(By.xpath("//input[@id='slider-2']")).isEnabled())
		{
			driver.findElement(By.xpath("//input[@id='slider-2']")).click();
			double max2 = Double.parseDouble(driver.findElement(By.id("slider-2")).getAttribute("max"));
			double min2 = Double.parseDouble(driver.findElement(By.id("slider-2")).getAttribute("min"));
			double value2 = 20;
			
			Actions action2 = new Actions(driver);
			WebElement slider2 = driver.findElement(By.xpath("//input[@id='slider-2']"));
			
			if (value2 < max2 && value2 > min2)
			{	
				action2.dragAndDropBy(slider2, (int) value2, 0).build().perform();
			}else
			{
				System.out.println("The value is out of range:" + min2 + " to " + max2);
			}			
		}
		
		driver.findElement(By.xpath("//a[]")).click();
		
		//refresh page
		driver.findElement(By.xpath("//input[contains(@id,'button') and @type='sumit']")).click();
		
		
	}

	@AfterClass
	public void afterClass() {
		
		//driver.navigate().refresh();
	}

}
