package runners;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import commonMethods.PageReusable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import runners.RunCukes;

public class TestRunner {
	
	

	public static void main(String[] args) {
		
				
		/*TestListenerAdapter tla = new TestListenerAdapter();*/
		TestNG testNG = new TestNG();
		testNG.setTestClasses(new Class[] {RunCukes.class});
		/*testNG.addListener(tla);*/
		testNG.run();
		
	}
}