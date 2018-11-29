package selenium_API;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class Topic_13_EZLicense {
	
	@Test
	public void TC_01_ActivateOflline() throws InterruptedException {
		try{

			String cmdStartEZLIC = "C:\\Program Files (x86)\\ACOM Solutions\\EZLicense\\EZLicense.exe";
			Runtime.getRuntime().exec(cmdStartEZLIC);
			
			String cmdActiveEZLic = "E:\\KP\\AutoIT\\EZLicense_x64.exe";
			Process process = Runtime.getRuntime().exec(cmdActiveEZLic +  " wdmatQQquVBmGHaJqMq0DLYf1AXFCM502T2gn6u9Z+c");
			if (process.waitFor() == -1)
				System.out.println("Failed");
			else
				System.out.println("Success");
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}
