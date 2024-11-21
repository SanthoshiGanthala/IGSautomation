package stepDefinitions;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import commonMethods.PageReusable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import pageObjects.loginPo;

public class loginIGRS {
	
	static RemoteWebDriver driver;
	PageReusable rm = new PageReusable();
	loginPo lp = new loginPo();
	
	
	@Given("launch the igrs application in browser")
	public void i_launch_application() throws Throwable{
		//driver.navigate().to("https://igs.ghmc.gov.in/PerProdIGSDashboard/Login.aspx");
		rm.navigate();
		Thread.sleep(5000);
	}
	
	@Then("clicking on the necessary fields")
	public void details() throws Throwable{
		//lp.selecting_details();
		//lp.column_sum();
		//lp.row_validation();
		//lp.zonal_reports();
		//lp.source_reports();
		lp.csz_reports();
		
	}

}	


