package selenium_API;

import org.testng.annotations.Test;

import sun.applet.resources.MsgAppletViewer_sv;

import org.testng.annotations.BeforeClass;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

// Inject mã javascript vào trong browser
// Back-end developer
// và 
// Font-end developer: angular, react, vueJS
// fullstack dev : back-end + font-end, dev + operation
// js trên IE trường hợp element click ko stable


public class Topic_10_JavaScriptExecutor {
	
	WebDriver driver;
	JavascriptExecutor js;
	WebDriverWait wait;
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		driver.manage().window().maximize();
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, 60);
	}
	
	@Test(enabled = false)
	public void TC_01_JavaScriptExcutor() throws Exception
	{		
		driver.get("http://live.guru99.com/");
		
		/* Step 02 - Sử dụng JE để get domain của page */
		System.out.println("------ STEP 02--------");
		String domain = (String) executeForBrowser("return document.domain;");
		Assert.assertTrue(domain.equals("live.guru99.com"));
		
		/* Step 03 - Sử dụng JE để get URL của page */
		System.out.println("------ STEP 03--------");
		String URL = (String) executeForBrowser("return document.URL;");
		Assert.assertTrue(URL.equals("http://live.guru99.com/"));
		
		/* Step 04 - Open MOBILE page */
		System.out.println("------ STEP 04--------");
		WebElement mobileLink = driver.findElement(By.xpath("//a[text() = 'Mobile']"));
		clickElementByJs(mobileLink);
		
		String title = (String) executeForBrowser("return document.title;");
		Assert.assertTrue(title.equals("Mobile"));
		
		/* Step 05 - Add sản phẩm SAMSUNG GALAXY vào Cart */
		System.out.println("------ STEP 05--------");
		//WebElement samsungLink = driver.findElement(By.xpath("//h2[a[text() = 'Samsung Galaxy']]/following-sibling::div/button")); //viết ngắn hơn 
		//WebElement samsungLink = driver.findElement(By.xpath("//h2/a[text() = 'Samsung Galaxy']/../following-sibling::div/button")); // ko nên dùng vì performace nó chậm
		//WebElement samsungLink = driver.findElement(By.xpath("//div[h2[a[text() = 'Samsung Galaxy']]]//button")); // quét div, h2 => thêm thẻ cũng chậm => ko nên
		WebElement samsungLink = driver.findElement(By.xpath("//a[text() = 'Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']/button"));// viết hơi dài
		clickElementByJs(samsungLink);
		
		/* Step 06 - Verify message được hiển thị:  Samsung Galaxy was added to your shopping cart */
		System.out.println("------ STEP 06--------");
		String msgSuccess = (String) executeForBrowser("return document.documentElement.innerText;");
		Assert.assertTrue(msgSuccess.contains("Samsung Galaxy was added to your shopping cart."));
		
		/* Step 07 - Open PRIVACY POLICY page*/
		System.out.println("------ STEP 07--------");
		WebElement privacyLink = driver.findElement(By.xpath("//a[text() = \"Privacy Policy\"]"));
		clickElementByJs(privacyLink);
		
		/* Step 08 - Scroll xuống cuối page*/
		System.out.println("------ STEP 08--------");
		executeForBrowser("window.scrollTo(0,document.body.scrollHeight)");
		
		/* Step 09 - Verify dữ liệu có hiển thị với chỉ 1 xpath:*/
		System.out.println("------ STEP 09--------");
		Assert.assertTrue(driver.findElement(By.xpath("//th[text() = 'WISHLIST_CNT']/following-sibling::td[text() ='The number of items in your Wishlist.']")).isDisplayed());
		
		/* Step 10 - Navigate tới domain: http://demo.guru99.com/v4/  */
		System.out.println("------ STEP 10--------");
		navigateToUrlByJs("http://demo.guru99.com/v4/");
		domain = (String) executeForBrowser("return document.domain;");
		Assert.assertTrue(domain.equals("demo.guru99.com"));
	}

	@Test(enabled = true)
	public void TC_02_RemoveAttributes() throws Exception
	{	
		driver.get("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_input_disabled");
		
		/* Step 02 - Remove thuộc tính disabled của field Last name  */
		System.out.println("------ STEP 02--------");
		driver.switchTo().frame(driver.findElement(By.id("iframeResult")));
		
		WebElement lastName = driver.findElement(By.xpath("//input[@name = 'lname']"));
		removeAttributeInDOMByJs(lastName, "disabled");
		
		/* Step 03 - Sendkey vào field Last name  */
		System.out.println("------ STEP 03--------");
		String value = "Automation testing";
		sendKeyByJs(lastName, value);
		
		/* Step 04 - Click Submit button  */
		System.out.println("------ STEP 04--------");
		WebElement btnSubmit = driver.findElement(By.xpath("//input[@value = 'Submit']"));
		clickElementByJs(btnSubmit);
		
		/* Step 05 - Verify dữ liệu sau khi submit chứa đoạn text đã fill trong field Lastname (Automation Testing)  */
		System.out.println("------ STEP 05--------");		
		String textResult = driver.findElement(By.xpath("//div[@class = 'w3-container w3-large w3-border']")).getText();
		Assert.assertTrue(textResult.contains(value));
		
	}
	
	@Test(enabled = false)
	public void TC_03_CreateAnAccount() throws Exception
	{	
		driver.get("http://live.guru99.com/");
		Assert.assertTrue(driver.getTitle().equals("Home page"));
		
		/* Step 02 - Click vào link "My Account" để tới trang đăng nhập  */
		System.out.println("------ STEP 01--------");
		clickElementByJs(driver.findElement(By.xpath("//div[@class = 'footer']//a[text() = 'My Account']")));
		Assert.assertTrue(driver.getTitle().equals("Customer Login"));
		
		/* Step 03 - Click CREATE AN ACCOUNT button để tới trang đăng kí tài khoản  */
		System.out.println("------ STEP 03--------");
		clickElementByJs(driver.findElement(By.xpath("//a[@title = 'Create an Account']")));
		Assert.assertTrue(driver.getTitle().equals("Create New Customer Account"));
		
		/* Step 04 - Nhập thông tin hợp lệ vào tất cả các field: First Name/ Last Name/ Email Address/ Password/ Confirm Password  */
		System.out.println("------ STEP 04--------");
		sendKeyByJs(driver.findElement(By.xpath("//input[@name='firstname']")), "Phuonghuhu" + PickNumberRandom(100));
		sendKeyByJs(driver.findElement(By.xpath("//input[@name='middlename']")), "Thi");
		sendKeyByJs(driver.findElement(By.xpath("//input[@name='lastname']")), "Tran");
		sendKeyByJs(driver.findElement(By.xpath("//input[@name='email']")), "Phuong" + PickNumberRandom(100) + "@gmail.com");
		sendKeyByJs(driver.findElement(By.xpath("//input[@name='password']")), "Phuong123");
		sendKeyByJs(driver.findElement(By.xpath("//input[@name='confirmation']")), "Phuong123");
		
		/* Step 05 - Click REGISTER button  */
		System.out.println("------ STEP 05--------");		
		clickElementByJs(driver.findElement(By.xpath("//button[@title = 'Register']")));
		String msgSuccess = (String) executeForBrowser("return document.documentElement.innerText;");
		Assert.assertTrue(msgSuccess.contains("Thank you for registering with Main Website Store."));
		
		/* Step 06 - Logout khỏi hệ thống   */
		System.out.println("------ STEP 06--------");
		clickElementByJs(driver.findElement(By.xpath("//div[@class ='skip-links']//span[text() = 'Account']")));		
		clickElementByJs(driver.findElement(By.xpath("//div[@class = 'links']//li/a[text() = 'Log Out']")));
		
		/* Step 07 - Kiểm tra hệ thống navigate về Home page sau khi logout thành công  */
		System.out.println("------ STEP 07--------");	
		String allText =(String) executeForBrowser("return document.documentElement.innerText;");
		Assert.assertTrue(allText.contains("YOU ARE NOW LOGGED OUT"));
		// dòng này chỉ trang home mới có, ko nên lấy title trong trừng hợp này
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(),'This is demo site for')]")).isDisplayed());
	}
	
	public int PickNumberRandom(int limit) {
		Random rd = new Random();
		return rd.nextInt(limit);
	}
	
	public Object clickElementByJs(WebElement element) {
	    try{
	    	JavascriptExecutor js = (JavascriptExecutor) driver;
	    	return js.executeScript("arguments[0].click();", element);
	    }catch(Exception e){
	    	e.getMessage();
	    	return null;
	    }
	}
	
	public Object sendKeyByJs(WebElement element, String value) {
	    try{
	    	JavascriptExecutor js = (JavascriptExecutor) driver;
	    	return js.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
	    }catch(Exception e){
	    	e.getMessage();
	    	return null;
	    }
	}
	
	public Object removeAttributeInDOMByJs(WebElement element, String attribute) {
	    try{
	    	JavascriptExecutor js = (JavascriptExecutor) driver;
	    	return js.executeScript("arguments[0].removeAttribute('" + attribute +"');", element);
	    }catch(Exception e){
	    	e.getMessage();
	    	return null;
	    }
	}
	
	public Object navigateToUrlByJs(String url) {
	    try{
	    	JavascriptExecutor js = (JavascriptExecutor) driver;
	    	return js.executeScript("window.location='" + url + "'");
	    }catch(Exception e){
	    	e.getMessage();
	    	return null;
	    }
	}
	
	public Object executeForBrowser(String javaSript) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return js.executeScript(javaSript);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
	
	@AfterClass
	public void afterClass() {
		driver.close();	
	}

}
