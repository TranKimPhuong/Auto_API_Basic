package selenium_API;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class Topic_12_Tomcat_SQLConnection {
	
	WebDriver driver;
	@BeforeClass
	public void beforeClass() {
	}
	
	@Test(enabled = true)
	public void TC_01_StartandStopTomCat() throws InterruptedException {
		try{
			// setup at 
			// \\EZITWK028\Thien_app_error
			// goi ham run nè a
			String command = "E:\\Dung\\Tomcat 9.0\\bin\\startup.bat";
			Runtime.getRuntime().exec(command);

			// wrok voi browser
			
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
			driver.manage().window().maximize();
			driver.get("http://localhost:8080");
			
			driver.quit();		
			// phải QUIT driver rồi goi tắt Tomcat
			String commandStop = "E:\\Dung\\Tomcat 9.0\\bin\\stopTomcat.bat";
			Runtime.getRuntime().exec(commandStop);
			
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	 private Connection conn = null;
	 private Statement st = null;
	 private ResultSet rs = null;
	 
	 @Test
     public void TC_03_openConnectionToDBSQL()  throws SQLException{

		 try {
		   //Loading the required JDBC Driver class
		   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
           conn = DriverManager.getConnection("jdbc:sqlserver://10.1.1.104\\sql2008R2;databasename=EZpaysuite;IntergratedSecurity=true","sa","sa123");
           //Executing SQL query and fetching the result
           st = conn.createStatement();
           
           String sqlStr = "select * from document.document where id < 38825";
           rs = st.executeQuery(sqlStr);     
          
		 }
		 catch(Exception ex) {
				System.out.println(ex.getMessage());
		}
	 }
	 
	 @Test(dependsOnMethods = "TC_03_openConnectionToDBSQL")
     public void TC_04_getDataFromDBSQL()  throws SQLException{
         while (rs.next()) {
             System.out.println(rs.getString("id"));
             System.out.println("Record Count : " + rs.getRow()); 
         } 
	 }
	 
	 @Test(dependsOnMethods = "TC_03_openConnectionToDBSQL")
     public void TC_05_closeConnectionToDBSQL()  throws SQLException{
         rs.close();
         st.close();
         conn.close();
	 }

	 @AfterClass
		public void afterClass() {
		}
}
