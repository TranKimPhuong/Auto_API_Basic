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

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;


public class Topic_07_UserInteractions {
	
	WebDriver driver;
	Actions action;
	
//	Gộp các actions of steps lại 
//	action.build();
//	
//	nhả chuột khi drag drop
//	action.release();
//	
//	thực thi all action
//	action.perfom();
	
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
		
		// select separated items
		action.keyDown(Keys.CONTROL).build().perform(); 
		elements.get(0).click();
		elements.get(2).click();
		elements.get(4).click();
		elements.get(6).click();
		action.keyUp(Keys.CONTROL).build().perform();			
		List<String> expectedValues= Stream.of("Item 0", "Item 2", "Item 4", "Item 6").collect(Collectors.toList());
		
		// Or select all constantly items
//		action.clickAndHold(elements.get(0)).perform();
//		action.moveToElement(elements.get(elements.size() -1)).release().perform();
//		List<String> expectedValues= Stream.of("Item 0", "Item 1", "Item 2", "Item 3", "Item 4","Item 5", "Item 6").collect(Collectors.toList());
		
		//verify		
		List<String> actualValues= new ArrayList<String>();
		List<WebElement> actalItems = driver.findElements(By.xpath("//ol[@id='selectable']/li[@class='ui-widget-content ui-selectee ui-selected']"));
		
		Assert.assertTrue(actalItems.size() == expectedValues.size());
		for(WebElement e : actalItems){
			actualValues.add(e.getText());
		}
		CustomSort(actualValues);
		Assert.assertEquals(new HashSet<String>(actualValues), new HashSet<String>(expectedValues));
	}
		
	@Test(enabled=false)
	public void TC_03_HoverMouse() throws Exception{				
		driver.get("https://www.myntra.com/");		
		
		WebElement element = driver.findElement(By.xpath("//div[@class = 'desktop-userIconsContainer']"));
		action.moveToElement(element).perform();
		
		driver.findElement(By.xpath("//a[text() = \"login\"]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class = 'login-box']//p[text() = \"Login to Myntra\"]")).isDisplayed());
	}
	
	@Test(enabled=false)
	public void TC_04_ContextClick_RightClick() throws Exception{	
		//right click
		driver.get("http://only-testing-blog.blogspot.com/2014/09/selectable.html");
		WebElement element = driver.findElement(By.xpath("//img[@border='0']"));	
		action.contextClick(element).perform();	
		
		//save as an image
		SaveAsbyRobot();
	}
	
	@Test(enabled=false)
	public void TC_05_RightClick() throws Exception{	
		//right click
		driver.get("https://swisnl.github.io/jQuery-contextMenu/demo.html");
		WebElement element = driver.findElement(By.xpath("//span[text() = \"right click me\"]"));	
		action.contextClick(element).perform();	
		
		// verify Quit is visible and not hover			
		WebElement  quitElement = driver.findElement(By.xpath("//li[contains(@class, 'context-menu-icon-quit') and not(contains(@class, 'context-menu-hover'))]"));	
		Assert.assertTrue(quitElement.isDisplayed());	
		action.moveToElement(quitElement).perform();
		// issue với chuột khi chạy song song nhiều test cases, hoặc user đụng
		// MAKE SURE CHUOT ko bị phân tán
		// verify Quit is visible and hover	
		Assert.assertTrue(driver.findElement(By.xpath("//li[contains(@class, 'context-menu-icon-quit') and contains(@class, 'context-menu-hover')]")).isDisplayed());	
		quitElement.click();
			
		Alert alert = driver.switchTo().alert();
		Assert.assertTrue(alert.getText().equals("clicked: quit"));
		alert.accept();
		
	}
	
	@Test (enabled=true)
	public void TC_06_DrapAndDrop() throws Exception{				
		driver.get("http://only-testing-blog.blogspot.com/2014/09/drag-and-drop.html");
		WebElement dragElementFrom = driver.findElement(By.xpath("//div[@id='dragdiv']"));
		WebElement dropElementTo = driver.findElement(By.xpath("//div[@id='dropdiv']/p"));
		Assert.assertTrue(dropElementTo.getText().equals("Drop here"));
		//way #1: i love this way
		action.clickAndHold(dragElementFrom).perform();
		action.release(dropElementTo).perform();
		
		//way #2
//		Actions builder = new Actions(driver);
//        Action dragAndDrop  = builder.clickAndHold(dragElementFrom)
//            .moveToElement(dropElementTo)
//            .release(dropElementTo)
//            .build();
//        dragAndDrop.perform();    
        
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
	
	public void SaveAsbyRobot() throws Exception{	
	
		//Select "Save Image As" option
		//To perform press Ctrl + v keyboard button action.
		  action.sendKeys(Keys.SHIFT, "v").build().perform();

		  Thread.sleep(3000);
		  Robot robot = new Robot();
		  // To press D key.
		  robot.keyPress(KeyEvent.VK_E);
		  // To press : key.
		  robot.keyPress(KeyEvent.VK_SHIFT);
		  robot.keyPress(KeyEvent.VK_SEMICOLON);
		  robot.keyRelease(KeyEvent.VK_SHIFT);
		  // To press \ key.
		  robot.keyPress(KeyEvent.VK_BACK_SLASH);		  
		  robot.keyPress(KeyEvent.VK_K);
		  robot.keyPress(KeyEvent.VK_P);  
		  robot.keyRelease(KeyEvent.VK_SHIFT);
		  robot.keyPress(KeyEvent.VK_BACK_SLASH);		  
		  robot.keyPress(KeyEvent.VK_D);
		  robot.keyPress(KeyEvent.VK_O);
		  robot.keyPress(KeyEvent.VK_W);
		  robot.keyPress(KeyEvent.VK_N);
		  robot.keyPress(KeyEvent.VK_L);
		  robot.keyPress(KeyEvent.VK_O);
		  robot.keyPress(KeyEvent.VK_A);
		  robot.keyPress(KeyEvent.VK_D);
		  robot.keyRelease(KeyEvent.VK_SHIFT);
		  robot.keyPress(KeyEvent.VK_BACK_SLASH);	
		  robot.keyPress(KeyEvent.VK_ENTER);  
		  
		  Thread.sleep(3000);
		  robot.keyPress(KeyEvent.VK_ENTER);  
	}
}
