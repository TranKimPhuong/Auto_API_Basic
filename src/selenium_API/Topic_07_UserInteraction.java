package selenium_API;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeClass;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;


public class Topic_07_UserInteraction {
	
	WebDriver driver;
	Actions action;
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
		action = new Actions(driver);
	}
	
	@Test(enabled=false)
	public void TC_01_DoubleClick() throws Exception{		
		driver.get("http://only-testing-blog.blogspot.com/2014/09/selectable.html");
		WebElement element = driver.findElement(By.xpath("//button[text() = \"Double-Click Me To See Alert\"]"));	
		action.doubleClick(element).perform();		
		
		Assert.assertTrue(driver.switchTo().alert().getText().equals("You double clicked me.. Thank You.."));
		driver.switchTo().alert().accept();
	}
	
	@Test(enabled=false)
	public void TC_02_ClickAndHold_SelectMulti() throws Exception{				
		driver.get("http://only-testing-blog.blogspot.com/2014/09/selectable.html");
		List<WebElement> elements = driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		
		action.keyDown(Keys.CONTROL).build().perform();
		elements.get(0).click();
		elements.get(2).click();
		elements.get(4).click();
		elements.get(6).click();
		action.keyUp(Keys.CONTROL).build().perform();			
		
		//verify
		List<String> expectedValues= Stream.of("Item 0", "Item 2", "Item 4", "Item 6").collect(Collectors.toList());
		
		List<String> actualValues= new ArrayList<String>();
		List<WebElement> actalItems = driver.findElements(By.xpath("//ol[@id='selectable']/li[@class='ui-widget-content ui-selectee ui-selected']"));
		
		for(WebElement e : actalItems){
			actualValues.add(e.getText());
		}
		CustomSort(actualValues);
		Assert.assertEquals(new HashSet<String>(actualValues), new HashSet<String>(expectedValues));
	}
		
	@Test(enabled=false)
	public void TC_03_HoverMouse() throws Exception{				
		driver.get("https://daominhdam.github.io/basic-form/");		
		WebElement element = driver.findElement(By.xpath("//a[text()=\"Hover over me\"]"));
		action.moveToElement(element).perform();
		Thread.sleep(2000);

	}
	
	@Test(enabled=false)
	public void TC_04_ContextClick_RightClick() throws Exception{	
		//right click
		driver.get("http://only-testing-blog.blogspot.com/2014/09/selectable.html");
		WebElement element = driver.findElement(By.xpath("//img[@border='0']"));	
		action.contextClick(element).perform();	
		
		//verify??
	}
		
	@Test (enabled=true)
	public void TC_05_DrapAndDrop() throws Exception{				
		driver.get("http://only-testing-blog.blogspot.com/2014/09/drag-and-drop.html");
		WebElement dragElementFrom = driver.findElement(By.xpath("//div[@id='dragdiv']"));
		WebElement dropElementTo = driver.findElement(By.xpath("//div[@id='dropdiv']/p"));
		
		//way #1
//		action.clickAndHold(dragElementFrom).perform();
//		action.release(dropElementTo).perform();
		
		//way #2
		Actions builder = new Actions(driver);
        Action dragAndDrop  = builder.clickAndHold(dragElementFrom)
            .moveToElement(dropElementTo)
            .release(dropElementTo)
            .build();
        dragAndDrop.perform();    
        
		Assert.assertTrue(dropElementTo.getText().equals("Dropped!"));	
	}
	
	@AfterClass
	public void afterClass() {
		driver.close();	
	}
	
	public void CustomSort(List<String> expectedValue) {
		Collections.sort(expectedValue, new Comparator<String>() {
	        @Override
	        public int compare(String p1, String p2) {
	            return p1.compareTo(p2); 
	        }
	    });
		
	}
	
}
