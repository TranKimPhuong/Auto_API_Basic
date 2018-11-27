package selenium_API;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class Topic_12_Tomcat_SQLConnection {
	
	WebDriver driver;

	
	@Test(enabled = false)
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
     public void TC_03_ConnectionToDBSQL()  throws SQLException{

		 try {
		   openConnectionToDBSQL();
           
           System.out.println("Result RESTORE: " + restoreFullDBtoSQL("test5", "E:\\KP\\DBBackup\\EZPS840_empty.bak", "E:\\KP\\DBBackup"));
           
           getDataFromDBSQL("select * from test5.Security.[User]");
           
           System.out.println("Result BACKUP: " + backupDBFromSQL("test5", "E:\\KP\\DBBackup\\test5_bk.bak"));
           
           closeConnection();
		 }
		 catch(Exception ex) {
				System.out.println("Error: " + ex.getMessage());
		}
	 }
	 
	 public void openConnectionToDBSQL()  throws SQLException{

		 try {
		   //Loading the required JDBC Driver class
		   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
           conn = DriverManager.getConnection("jdbc:sqlserver://192.168.74.129\\sql2008R2;databasename=master;IntergratedSecurity=true","sa","sa123");
		   
           //Executing SQL query and fetching the result
           st = conn.createStatement(); 
		 }
		 catch(Exception ex) {
				System.out.println("Error: " + ex.getMessage());
		}
	 }
	 
     public boolean restoreFullDBtoSQL(String restoreDBName, String localBackupDevicePath, String restorePath) throws ClassNotFoundException, SQLException {

    	 String executeCmd = getCmdQuery(restoreDBName, localBackupDevicePath, restorePath );  	  	 
     	 try {    	
    		    st.execute(executeCmd); 
    		    return true;
		    } catch (Exception ex) {
		    	System.out.println("Error: " + ex.getMessage());
		    }
		return false;
     }
     
     
     public String getCmdQuery(String restoreDBName, String localBackupDevicePath, String restorePath) throws SQLException, ClassNotFoundException {
    	 String executeCmdFileList = "RESTORE FILELISTONLY FROM DISK ='" + localBackupDevicePath + "'";
    	 String executeCmd = "RESTORE DATABASE " + restoreDBName + 
	             " FROM DISK ='" + localBackupDevicePath + "'";
    	 
    	 try {
	    	 rs = st.executeQuery(executeCmdFileList); 
	    	 while (rs.next()) {
	    		 if (!rs.getString("Logicalname").endsWith("_log"))
	    			 executeCmd = executeCmd + " WITH MOVE '" + rs.getString("Logicalname") + "' TO '" + restorePath + "\\"+ restoreDBName +".mdf'," ;
	    		 else
	    			 executeCmd = executeCmd + " MOVE '" + rs.getString("Logicalname") + "' TO '" + restorePath + "\\" + restoreDBName + "_log.ldf';" ;
	    	 } 
	    	 return executeCmd;
    	 }catch(SQLException ex) {
    		 return "SQL Error: " + ex.getMessage();
    	 }
     }
     
     public void getDataFromDBSQL(String sqlQueryWithDBName)  throws SQLException{
         
         rs = st.executeQuery(sqlQueryWithDBName); 
         
         while (rs.next()) {
             System.out.println(rs.getString("Loginid"));
             System.out.println("Record Count : " + rs.getRow()); 
         } 
	 }
	 
	 public boolean backupDBFromSQL(String sourceDBName, String backupAdsolutedPath) {
	    String executeCmd = "BACKUP DATABASE "+ sourceDBName +" TO DISK = '"+ backupAdsolutedPath +"' ";
     	 try {    	
    		    st.execute(executeCmd); 
    		    return true;
		    } catch (Exception ex) {
		    	System.out.println("Error: " + ex.getMessage());
		    }
		return false;
	 }
	 

     public void closeConnection()  throws SQLException{
		 if (rs != null) rs.close();
		 if (st != null)  st.close();
		 if (conn != null) conn.close();
	 }


}
