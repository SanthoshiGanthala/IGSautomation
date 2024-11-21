package stepDefinitions;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;

import commonMethods.PageReusable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.appium.java_client.AppiumDriver;

public class InitializationStepDef extends PageReusable{
	
	public static int k;
	public static int i;
	public static String feature = "";
	public static String toolName; 
	public static RemoteWebDriver remoteDriver;
	public static AppiumDriver mDriver;
	public static PageReusable rm; 
	static String strBrowser = "";
	static List<String> lstExec = new ArrayList<String>();
//	Scenario scenario;
	
	@Before
	public void driverStart(Scenario scenario) throws Exception{
		
		
			try{	
																																									
				if (feature.equalsIgnoreCase(scenario.getId().split(";")[0])){
					System.out.println("Browser to launch "+lstExec.get(1));
					if(toolName.equalsIgnoreCase("Appium"))
						rm.launchApp();
					
					else if (toolName.equalsIgnoreCase("Selenium"))
						rm.launchBrowser(strBrowser);
					
					System.out.println(scenario.getName());
				}
				else{
				for (i = k; i < getFeatures().size();) {
					lstExec = inputlist().get(i);
					strBrowser = lstExec.get(1);
					toolName = lstExec.get(2).trim();
					if(toolName.equalsIgnoreCase("Appium")){
						rm = new PageReusable(mDriver, toolName);
						rm.launchApp();
					}
					else{
					 rm	= new PageReusable(remoteDriver, toolName);
					 rm.launchBrowser(strBrowser);
					}
					feature = scenario.getId().split(";")[0];
					System.out.println(feature);
					System.out.println(scenario.getName());
					k++;
					break;
				}
			}
			
			}
			catch (Exception e){
				Reporter.log(e.toString());
			
			}
	}
	
	@After
	public void teardown(){
//		System.out.println("We are in after hook method");
		closeDriver(toolName);
	}
	
	
	
	public List<List<String>> inputlist() throws Exception{
		List<List<String>> lstExecutables = new ArrayList<List<String>>();
		List<String> lstFeatbrowser = new ArrayList<String>();
		
		try{
			for(int i=0; i<getFeatures().size();i++){
				lstFeatbrowser.add(getFeatures().get(i));
				if(i<=getBrowsers().size())
					lstFeatbrowser.add(getBrowsers().get(i));
				else
					lstFeatbrowser.add(readProperty("Browser"));
				lstFeatbrowser.add(getDrivers().get(i));
				lstExecutables.add(lstFeatbrowser);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		return lstExecutables;
	}

}
