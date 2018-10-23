package selenium_API;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class Topic_00_Common {
	
	WebDriver driver;
	WebDriverWait wait;
	
	//Topic 05
	public int PickNumberRandom(int limit) {
		Random rd = new Random();
		return rd.nextInt(limit);
	}
	
	public String[] PickMonthRandom() {
		String[] arrReturn = new String[3];
		
		// avoid case no = 0
		int no1 = PickNumberRandom(11) + 1;
		int no2 = PickNumberRandom(11) + 1;
		int no3 = PickNumberRandom(11) + 1;
		
		System.out.println(no1);
		System.out.println(no2);
		System.out.println(no3);
		Assert.assertTrue(no1!=no2);
		Assert.assertTrue(no2!=no3);
		Assert.assertTrue(no1!=no3);
		
		arrReturn[0] = ConvertNumberToTextMonth(no1);
		arrReturn[1] = ConvertNumberToTextMonth(no2);
		arrReturn[2] = ConvertNumberToTextMonth(no3);
		
		return arrReturn;
	}
	
	public String ConvertNumberToTextMonth(int no) {
		String month = "";
		switch (no)
		{
			case 1:
				month = "January";
				break;
			case 2:
				month = "February";
				break;
			case 3:
				month = "March";
				break;
			case 4:
				month = "April";
				break;
			case 5:
				month = "May";
				break;
			case 6:
				month = "June";
				break;
			case 7:
				month = "July";
				break;
			case 8:
				month = "August";
				break;
			case 9:
				month = "September";
				break;
			case 10:
				month = "October";
				break;
			case 11:
				month = "November";
				break;
			case 12:
				month = "December";
				break;
		}
		return month;
	}

	public void SelectManyItemsInDropDown(String parentLocator, String childLocator, List<String> expectedValue) throws InterruptedException{		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(parentLocator));

		js.executeScript("arguments[0].scrollIntoView(true);", element);	
		element.click();
		Thread.sleep(1200);
		List <WebElement> lstAllItems = driver.findElements(By.xpath(childLocator));	
		wait.until(ExpectedConditions.visibilityOfAllElements(lstAllItems));
		for(WebElement e: lstAllItems) 
		{			
			int index = expectedValue.indexOf(e.getText().trim());	
			if (index>-1)
			{
				js.executeScript("arguments[0].scrollIntoView(true);", e);			
				e.click();
			}
		}
		Thread.sleep(1200);	
	}
	
	public void SelectOneItemInDropDown(String parentLocator, String childLocator, String expectedValue) throws InterruptedException{		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(parentLocator));
		//js.executeScript("arguments[0].click();", element);	
		//or
		js.executeScript("arguments[0].scrollIntoView(true);", element);	
		element.click();
		Thread.sleep(1200);
		List <WebElement> lstAllItems = driver.findElements(By.xpath(childLocator));	
		wait.until(ExpectedConditions.visibilityOfAllElements(lstAllItems));
		for(WebElement e: lstAllItems) 
		{	
			if (e.getText().trim().equals(expectedValue))
			{
				js.executeScript("arguments[0].scrollIntoView(true);", e);			
				e.click();
				break;
			}
		}
		Thread.sleep(1200);	
	}
	
	//Topic 06
	public void clickElementByJavascript(WebElement element) {
	    JavascriptExecutor je = (JavascriptExecutor) driver;
	    je.executeScript("arguments[0].click();", element);
	}
	
	//Topic 05, 07
	public void CustomSort(List<String> expectedValue) {
		Collections.sort(expectedValue, new Comparator<String>() {
	        @Override
	        public int compare(String p1, String p2) {
	            return p1.compareTo(p2); 
	        }
	    });
		
	}
	
	// topic 09

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
	
	//Nhin
	public static void OpenXLSXFile_NHin() throws IOException {
		
		//open the exiting excel file
		String fileName = "Test.xlsx";
		String filePath = "E:";
		String sheetName = "Sheet1";
		
		File file = new File(filePath + "//"+ fileName);
		FileInputStream inputStream = new FileInputStream(file);
		
		Workbook guru99Workbook = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if(fileExtensionName.equals(".xlsx")){
			guru99Workbook = new XSSFWorkbook(inputStream);
		}
		Sheet guru99Sheet = guru99Workbook.getSheet(sheetName);
		
		int rowCount = guru99Sheet.getLastRowNum()-guru99Sheet.getFirstRowNum();
		
		for (int i = 0; i < rowCount+1; i++) {
	        Row row = guru99Sheet.getRow(i);
	        for (int j = 0; j < row.getLastCellNum(); j++) {

	            System.out.print(row.getCell(j).getStringCellValue()+"|| ");
	        }

	        System.out.println();

	    }
		
		
	}
	
    public static void writeAppendXLSXFile_NHin() throws IOException {
		
		//open the exiting excel file
		String fileName = "Test.xlsx";
		String filePath = "E:";
		String sheetName = "Sheet1";
		String[] dataToWrite = {"Mr. E","Noida", "hu", "ha"};
		
		File file = new File(filePath + "//"+ fileName);
		FileInputStream inputStream = new FileInputStream(file);		
		Workbook guru99Workbook = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if(fileExtensionName.equals(".xlsx")){
			guru99Workbook = new XSSFWorkbook(inputStream);
		}		
		Sheet sheet = guru99Workbook.getSheet(sheetName);	
		int rowCount = sheet.getLastRowNum()- sheet.getFirstRowNum();		
		Row row = sheet.getRow(0);
		Row newRow = sheet.createRow(rowCount+1);	
				
		for (int j = 0; j < row.getLastCellNum(); j++) {
	        Cell cell = newRow.createCell(j);
	        if (j < dataToWrite.length)
	        	cell.setCellValue(dataToWrite[j]);
	        else
	        	break;
	    }
				
		inputStream.close();
		FileOutputStream outputStream = new FileOutputStream(file);
	    guru99Workbook.write(outputStream);
	    outputStream.close();
		
	}


	public static void openXLSXFile() throws IOException {
		File file = new File("E://Test.xlsx");
	      FileInputStream fIP = new FileInputStream(file);
	      
	      //Get the workbook instance for XLSX file 
	      Workbook workbook = new XSSFWorkbook(fIP);
	      
	      if(file.isFile() && file.exists()) {
	         System.out.println("openworkbook.xlsx file open successfully.");
	      } else {
	         System.out.println("Error to open openworkbook.xlsx file.");
	      }
	      
		
	}
	public static void writeNewXLSXFile() throws IOException {
		
		String excelFileName = "E://Test.xlsx";//name of excel file

		String sheetName = "Sheet1";//name of sheet

		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet(sheetName) ;

		//iterating r number of rows
		for (int r=0;r < 5; r++ )
		{
			Row row = sheet.createRow(r);

			//iterating c number of columns
			for (int c=0;c < 5; c++ )
			{
				Cell cell = row.createCell(c);
	
				cell.setCellValue("Cell "+r+" "+c);
			}
		}

		FileOutputStream fileOut = new FileOutputStream(excelFileName);

		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}

}
