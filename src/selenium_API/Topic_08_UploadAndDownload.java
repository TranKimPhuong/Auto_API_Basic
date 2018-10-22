package selenium_API;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeClass;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.AfterClass;


public class Topic_08_UploadAndDownload {
	
	WebDriver driver;
	Actions action;
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
		action = new Actions(driver);
	}
	
	@Test(enabled = false)
	public void TC_01_SaveAsbyRobot() throws Exception{	
		//right click
		driver.get("http://only-testing-blog.blogspot.com/2014/09/selectable.html");
		WebElement element = driver.findElement(By.xpath("//img[@border='0']"));	
		action.contextClick(element).perform();	
		
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
	
	@Test(enabled = false)
	public void TC_02_DownLoadbyWGet() throws Exception{	

			// using wget
			driver.get("https://daominhdam.github.io/basic-form/");
			
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
	}
	
	@Test(enabled = false)
	public void TC_03_DownloadbyAutoIT() throws InterruptedException{
		
	}
	
	@Test(enabled = true)
	public void TC_04_Herokuapp_AuthenticationAlert() throws Exception {	
		
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
	
	@AfterClass
	public void afterClass() {
		//driver.close();	
	}

}
