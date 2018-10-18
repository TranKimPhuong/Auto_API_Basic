package selenium_API;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;

public class Topic_04_Selected_Displayed_Enabled {
	
	WebDriver driver;
	String WebUrl = "https://daominhdam.github.io/basic-form/"; 
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
		driver.get(WebUrl);
	}
	@Test
	public void TC_01_Element_IsDisplayed() throws Exception
	{
		if (driver.findElement(By.id("mail")).isDisplayed())
			driver.findElement(By.id("mail")).sendKeys("Automation Testing");			
		else
			System.out.println("Textbox email isnot displayed");
		
		if (driver.findElement(By.id("under_18")).isDisplayed())
			driver.findElement(By.id("under_18")).click();		
		else
			System.out.println("Age Under 18 isnot displayed");
		
		if (driver.findElement(By.id("edu")).isDisplayed())
			driver.findElement(By.id("edu")).sendKeys("Automation Testing");
		else
			System.out.println("Education area isnot displayed");			
	}
	@Test
	public void TC_02_Element_IsEnabledd() throws Exception	{
		
		if (driver.findElement(By.id("mail")).isEnabled())
			System.out.println("Email textbox is enabled");
		else
			System.out.println("Email textbox is disabled");
		
		if (driver.findElement(By.id("under_18")).isEnabled())
			System.out.println("Age Under 18 is enabled");
		else
			System.out.println("Age Under 18 is disabled");

		if (driver.findElement(By.id("edu")).isEnabled())
			System.out.println("Education area is enabled");
		else
			System.out.println("Education area is disabled");
		
		if (driver.findElement(By.id("job1")).isEnabled())
			System.out.println("Job dropbox is enabled");
		else
			System.out.println("Job dropbox is disabled");
		
		if (driver.findElement(By.id("development")).isEnabled())
			System.out.println("Development checkbox is enabled");
		else
			System.out.println("Development checkbox is disabled");
		
		if (driver.findElement(By.id("slider-1")).isEnabled())
			System.out.println("Slide bar is enabled");
		else
			System.out.println("Slide bar is disabled");
		
		if (driver.findElement(By.id("button-enabled")).isEnabled())
			System.out.println("Button is enabled");
		else
			System.out.println("Button is disabled");		
	}
	
	@Test
	public void TC_03_Element_IsDisabled() throws Exception
	{
		if (driver.findElement(By.id("password")).isEnabled())
			System.out.println("Password textbox is enabled");
		else
			System.out.println("Password textbox is disabled");
		
		if (driver.findElement(By.id("under_18")).isEnabled())
			System.out.println("Radio button is enabled");
		else
			System.out.println("Radio button is disabled");
		
		if (driver.findElement(By.id("bio")).isEnabled())
			System.out.println("Biography area is enabled");
		else
			System.out.println("Biography area is disabled");
		
		if (driver.findElement(By.id("job2")).isEnabled())
			System.out.println("Job2 dropbox is enabled");
		else
			System.out.println("Job2 dropbox is disabled");
		
		if (driver.findElement(By.id("check-disbaled")).isEnabled())
			System.out.println("checkbox is enabled");
		else
			System.out.println("checkbox is disabled");
		
		if (driver.findElement(By.id("slider-2")).isEnabled())
			System.out.println("Slide2 bar is enabled");
		else
			System.out.println("Slide2 bar is disabled");
		
		if (driver.findElement(By.id("button-disabled")).isEnabled())
			System.out.println("Button is enabled");
		else
			System.out.println("Button is disabled");	
	}
	
	@Test
	public void TC_04_Element_IsSelected() {
		
		driver.findElement(By.id("under_18")).click();
		driver.findElement(By.id("development")).click();
		
		if (driver.findElement(By.id("development")).isSelected())
			System.out.println("Development is selected");
		else
			driver.findElement(By.id("development")).click();
		
		if (driver.findElement(By.id("under_18")).isSelected())
			System.out.println("Age Under 18 is selected");
		else
			driver.findElement(By.id("under_18")).click();
	}
	
	public void isElementEnabled(WebElement element){
		
		if (element.isEnabled())
			System.out.println("Element is enabled");
		else
			System.out.println("Element is disabled");
	}

	@AfterClass
	public void afterClass() {
		
		driver.quit();
	}

}
