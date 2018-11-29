package Java_Basic;

public class Java_SystemProperties {

	/**
	jdk: java development kit 
		development tool , khi build/devlop app
	jre: java runner environment 
		runner tool, khi run app
	IDEs: intergrated development environment, cho build/develop and run app
		java: Eclipse -> newbie
			  IntelliJ -> pro
		C#: MS VS (nặng)
			Visual studio Code -> only for back-end
		Python: Eclipse
		 		PyCharm (JetBrain)
	*/
	public static void main(String[] args) {
		// set variable environment at running time
		
		String rootFolder = System.getProperty("user.dir");
		
		//System.setProperty("webdriver.chrome.driver", ".\\Browsers\\chromedriver.exe");		
		System.setProperty("webdriver.chrome.driver", rootFolder + "\\Browsers\\chromedriver.exe");
		
		//https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html
		System.out.println(System.getProperty("java.home"));
		System.out.println(System.getProperty("java.version"));
		System.out.println(System.getProperty("os.name"));
		System.out.println(System.getProperty("os.version"));
		
		System.out.println(System.getProperty("user.name"));
	}

}
