package appModules;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Common {
	
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
		
		System.out.println("toi day roi na");
		System.out.println(lstAllItems.size());
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
}
