package selenium_API;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeClass;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.AfterClass;


public class Topic_08_UploadAndDownload {
	
	WebDriver driver;
	Actions action;
	WebDriverWait wait;
	
	String relativePath = "";
	String sBrowser = "chrome";
	
	@BeforeClass
	public void beforeClass() {
		switch(sBrowser.toLowerCase()) {
			case "chrome":
				GetChromeBrowser();
				sBrowser = "Chrome";
				break;
			case "firefox":
				GetFireFoxBrowser();
				sBrowser = "Firefox";
				break;
			case "ie":
				GetIEBrowser();
				sBrowser = "IE";
				break;
		}
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
		
		action = new Actions(driver);
		wait = new WebDriverWait(driver, 30);
		
		relativePath = System.getProperty("user.dir");
	}
	// Robot tuong tác với window dialog 
	@Test(enabled = false)
	public void TC_01_SaveAsByRobot() throws Exception{	
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
	
	/**
	 * NÊN dùng cách này
	 * @throws Exception
	 */
	@Test(enabled = false)
	public void TC_02_DownLoadByWGet() throws Exception{	

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
	public void TC_03_DownloadByAutoIT() throws InterruptedException{
		driver.get("https://daominhdam.github.io/basic-form/");
		driver.findElement(By.linkText("download.txt")).click();
		// ko bắt đc object
	}
	
	@Test(enabled = false)
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
	
	/**
	 * NÊN dùng cách này, SendKey của selenium work with webapp và trên máy nào cũng run dc
	 * @throws Exception
	 */
	@Test(enabled = false)
	public void TC_05_UploadBySendKeys() throws Exception{		
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		System.out.println("---Upload 3 images----");
		WebElement btnAddFiles = driver.findElement(By.xpath("//input[@type ='file']"));
		
		if (sBrowser == "Chrome")// và firefox mới + IE mới
			btnAddFiles.sendKeys(getStringFileNames(relativePath + "\\Upload", "\n", "", true)); 
		else
		{		
			List<String> filePaths = getFileNamesList(relativePath + "\\Upload", true);	
			for(String s:filePaths){
				btnAddFiles = driver.findElement(By.xpath("//input[@type ='file']"));
				btnAddFiles.sendKeys(s);
			}
		}
		
		System.out.println("---3 images are selected correctly----");
		List<String> expectedValues = getFileNamesList(relativePath + "\\Upload", false);		
		CustomSort(expectedValues);
		List<WebElement> choseImages = driver.findElements(By.xpath("//p[@class='name']"));
		List<String> actualValues = new ArrayList<String>();		
		for(WebElement e: choseImages) {
			actualValues.add(e.getText().trim());
		}		
		CustomSort(actualValues);
		Assert.assertEquals(new HashSet<String>(actualValues), new HashSet<String>(expectedValues));
		
		System.out.println("---Click Start button to upload 3 files----");
		List<WebElement> btnStarts = driver.findElements(By.xpath("//button[@class ='btn btn-primary start' and not(@type = 'submit')]"));
		for(WebElement e: btnStarts ) {
			e.click();
		}	
		
		System.out.println("---Verify 3 images uploaded successfully---");
		List<WebElement> linkedImages =  driver.findElements(By.xpath("//table[@class='table table-striped']/img"));
		for(WebElement e: linkedImages) {
			Assert.assertTrue(verifyRealImageLoaded(e));
		}
	}
	
	/**
	 * Ko khuyen dùng cách này, AutoIT tương tác với window dialog
	 * @throws Exception
	 */
	@Test(enabled = true)
	public void TC_06_UploadByAutoIT() throws Exception{
		String windowTitle = "Open";
		String filePath = relativePath + "\\Upload";
		String exePath = "E:\\KP\\AutoIT\\FileUpload.exe"; // them "-" thay space de ko bi autoIT split	
		String manyFiles = getStringFileNames(filePath, "|", "", false);
		
		if (sBrowser != "Chrome")
			windowTitle = "File-Upload"; // them "-" thay space de ko bi autoIT split
		
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");		
		driver.findElement(By.xpath("//span [@class='btn btn-success fileinput-button']")).click();		
		/* chạy ko ổn định do máy chậm => load chậm
		 * 		$CmdLine[1]: window name
		 * 		$CmdLine[2]: Path
		 * 		$CmdLine[3]: File names, delimiter is "|" instead of " " because AutoIT  will split by space
		 * 	
		 */
		Runtime.getRuntime().exec(exePath + " " + windowTitle + " " + filePath  + " " + manyFiles);	
		
		System.out.println("---Verify 3 images uploaded successfully---");
		List<WebElement> linkedImages =  driver.findElements(By.xpath("//table[@class='table table-striped']/img"));
		for(WebElement e: linkedImages) {
			Assert.assertTrue(verifyRealImageLoaded(e));
		}
	}
	
	/**
	 * Ko khuyen dùng cách này, Robot tương tác với win dialog
	 * @throws Exception
	 */
	@Test(enabled = true)
	public void TC_07_UploadByRobot() throws Exception{
	
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		driver.findElement(By.xpath("//span [@class='btn btn-success fileinput-button']")).click();

		uploadFile(relativePath + "\\Upload");
			
		System.out.println("---Verify 3 images uploaded successfully---");
		List<WebElement> linkedImages =  driver.findElements(By.xpath("//table[@class='table table-striped']/img"));
		for(WebElement e: linkedImages) {
			Assert.assertTrue(verifyRealImageLoaded(e));
		}
	}
	
	@Test(enabled = true)
	public void TC_08_IntergrateUploadFiles() throws Exception{
		driver.get("https://encodable.com/uploaddemo/");

		System.out.println("----STep01: SendKey to upload files-----");	
		WebElement btnUpload = driver.findElement(By.xpath("//input[@id='uploadname1']"));
		btnUpload.sendKeys(getStringFileNames(relativePath + "\\Upload", "\n", "", true));
		
		System.out.println("----STep02: Select dropdown box-----");
		driver.findElement(By.xpath("//select[@name='subdir1']")).click();
		Select select = new Select(driver.findElement(By.xpath("//select[@name='subdir1']")));
		select.selectByVisibleText("/uploaddemo/files/");
		
		System.out.println("----STep03: enter data into textbox-----");
		String subFolder = "KPfolder" + PickNumberRandom(100);
		String email = "KP@gmail.com";
		String firstname = "KP";
		
		driver.findElement(By.xpath("//input[@id='newsubdir1']")).sendKeys(subFolder);	
		driver.findElement(By.xpath("//input[@id='formfield-email_address']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@id='formfield-first_name']")).sendKeys(firstname);
		
		driver.findElement(By.xpath("//input[@id='uploadbutton']")).click();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		
		System.out.println("----STep04: Verify data-----");
		WebElement eEmails = driver.findElement(By.xpath("//dd[starts-with(text(), 'Email Address:')]"));
		wait.until(ExpectedConditions.visibilityOf(eEmails));
		String actualValue = eEmails.getText().split(":")[1].trim();	
		Assert.assertEquals(actualValue, email);
		
		actualValue = driver.findElement(By.xpath("//dd[starts-with(text(), 'First Name:')]")).getText().split(":")[1].trim();
		Assert.assertEquals(actualValue, firstname);
		List<WebElement> actualValues = driver.findElements(By.xpath("//dt[starts-with(text(), 'File ')]/a"));
		List<String> expectedValues = getFileNamesList(relativePath + "\\Upload", false);		
		for(WebElement e: actualValues)
		{
			Assert.assertTrue(expectedValues.contains(e.getText()));
		}
		
		System.out.println("----STep05: Verify corrected images uploaded-----");
		driver.findElement(By.xpath("//a[text() ='View Uploaded Files']")).click();
		List<WebElement> fileList = driver.findElements(By.xpath("//td[@class ='dname diricon']/a"));
		for(WebElement e: fileList) {
			if (e.getText().equals(subFolder))
			{
				e.click();
				break;
			}
		}
	
		//control cho case image file and flat file without attached image
		actualValues = driver.findElements(By.xpath("//td[starts-with(@class , 'fname')]/a[not(@class = 'thumb')]"));
		for(WebElement e: actualValues)
		{
			Assert.assertTrue(expectedValues.contains(e.getText()));
		}
		
//		List<WebElement> linkedImages =  driver.findElements(By.xpath("//table[@class='table table-striped']/img"));
//		for(WebElement e: linkedImages) {
//			Assert.assertTrue(verifyRealImageLoaded(e));
//		}
		
	}
	
	public boolean verifyRealImageLoaded(WebElement image) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (boolean) js.executeScript("return arguments[0].complete && " + "typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", image);
	}
	
	public int PickNumberRandom(int limit) {
		Random rd = new Random();
		return rd.nextInt(limit);
	}
	
	public void uploadFile(String fileLocation) {
        try {
            //native key strokes for CTRL, V and ENTER keys
            Robot robot = new Robot();
            
        	//Setting clipboard with file location
        	setClipboardData_forPath(fileLocation);
            
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);  
            robot.delay(1000); // move di cho khac la chay sai lien
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            
            setClipboardDat_forFileName(fileLocation);
                        
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V); 
            robot.delay(1000);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            
            
        } catch (Exception exp) {
        	exp.printStackTrace();
        }
    }
	
	/**
     * This method will set any parameter string to the system's clipboard.
     */
	public void setClipboardData_forPath(String path) {
	    StringSelection stringSelection = new StringSelection(path);	    	    
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}
	
	public void setClipboardDat_forFileName(String path) {
        StringSelection stringSelection = new StringSelection(getStringFileNames(path, " ", "\"", false));	    	    
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}
	
	public String getStringFileNames(String location, String delimiter, String quote, boolean absolutePath ) {
		File folder = new File(location);
	    File[] files = folder.listFiles();
	    String sFilenames = "";
	    String path = "";
	    	    	
	    for(int i = 0; i < files.length; i++){
		    if (absolutePath == true)
		    	path = files[i].getAbsolutePath();
		    else
		    	path = files[i].getName();
		    
	    	if (files[i].isFile())
	    		sFilenames += (i != 0? quote + delimiter + quote: quote) + path;
	    }
	    sFilenames += quote;  
	    
	    return  sFilenames;
	}
	
	public List<String> getFileNamesList(String location, boolean absolutePath) {
		File folder = new File(location);
		File[] files = folder.listFiles(); 
	    List<String> filenamesList = new ArrayList<String>();
	    String path = "";
	    
	    for(int i = 0; i < files.length; i++){
	    	 if (absolutePath == true)
			    	path = files[i].getAbsolutePath();
			    else
			    	path = files[i].getName();
	    	if (files[i].isFile())
	    		filenamesList.add(path);
	    }	    
	    return  filenamesList;
	}
	
	public void CustomSort(List<String> expectedValue) {
		Collections.sort(expectedValue, new Comparator<String>() {
	        @Override
	        public int compare(String p1, String p2) {
	            return p1.compareTo(p2); 
	        }
	    });
		
	}
	
	public void GetChromeBrowser() {
		System.setProperty("webdriver.chrome.driver", "E://KP//Software//chromedriver_win32//chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	public void GetFireFoxBrowser() {
		driver = new FirefoxDriver();
	}

	public void GetIEBrowser() {
		/*
		 *  1. Open internet explorer.
			2. Navigate to Tools->Option
			3. Navigate to Security Tab
			4. Click on "Reset All Zones to Default level" button
			5. Now for all option like Internet,Intranet,Trusted Sites and Restricted Site enable "Enable Protected" mode check-box.
			6. Set IE zoom level to 100%
			   then write below code in a java file and run*
		 */
		System.setProperty("webdriver.ie.driver","E:\\KP\\Software\\IEDriverServer_Win32_2.53.0\\IEDriverServer.exe");
		driver = new InternetExplorerDriver();
	}
	
	@AfterClass
	public void afterClass() {
		driver.close();	
	}

}
