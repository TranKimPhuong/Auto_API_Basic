package selenium_API;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Common {
	public void Inital(WebDriver driver, WebDriverWait wait) {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 30);
	}
	
	public void QuitorCloseWebDriver(WebDriver driver,String strDedicate) {
		switch(strDedicate.toUpperCase()) {
			case "Quit":
				driver.quit();
				break;
			case "Close":
				driver.close();
				break;
		}
	}	
	public void SelectElementinDropDownList(WebDriver driver, WebDriverWait wait,String parentLocator, String childLocator, List<String> expectedValue) throws InterruptedException{
		
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
			//String a = expectedValue.Where(e => e.equals(e.getText().trim())).toString();
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
