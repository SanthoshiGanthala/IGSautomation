package pageObjects;

import java.io.IOException;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import commonMethods.PageReusable;


public class TestGooglePO extends PageReusable{
	
	/*public TestGooglePO(RemoteWebDriver driver) {
		super(driver);
		
	}*/
	
	public TestGooglePO(){
		super();
	}

	@FindBy(xpath=".//input[@name='q']")
	WebElement txtSearch;
	
	@FindBy(xpath=".//div[@id='result-stats']")
	WebElement txtresult;
	
	
	public void searchText(String sText) throws Exception{
		
		enterText(txtSearch, sText);
		keyBoardEnter(txtSearch);
		
	}
	
	public void searchresult() throws Exception{
		elementIsDisplayed(txtresult);
	}

}
