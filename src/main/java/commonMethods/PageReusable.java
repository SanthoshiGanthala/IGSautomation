package commonMethods;

import runners.RunCukes;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.vimalselvam.cucumber.listener.Reporter;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class PageReusable {
	
	static String dirPath = System.getProperty("user.dir");
    static RemoteWebDriver driver;
	@SuppressWarnings("rawtypes")
	static AppiumDriver mDriver;
	static String toolName;
	static WebDriverWait wait;
	Map<String,String> mobileEmulation = new HashMap<>();
	
	
	@SuppressWarnings("static-access")
	public PageReusable(RemoteWebDriver driver, String tool){
		
		this.setDriver(driver);
		toolName = tool;
		PageFactory.initElements(driver, this);
	}
	
	@SuppressWarnings({ "rawtypes", "static-access" })
	public PageReusable(AppiumDriver mdriver, String tool){
		
		this.mDriver = mdriver;
		toolName = tool;
		PageFactory.initElements(mDriver, this);
	}
	
	public PageReusable(){
		PageFactory.initElements(getDriver(), this);
	}
	/* ###########################################################################
	 * Description: This method used to read properties from property file
	 * @prop: Property to fetch
	 * Created : 30-03-2020
	 * ###########################################################################
	*/
	
	public static String readProperty(String prop) throws IOException{
	  String property="";
		try{ 
		FileReader reader=new FileReader("Properties.properties");  
	    Properties p=new Properties();  
	    p.load(reader);  
	    property=p.getProperty(prop);
	    
	   }
	   catch (Exception e){
		   e.printStackTrace();
		  Reporter.addScenarioLog("Error in reading property: ");
		  Assert.fail("Error reading property");
	   }
	   
	   return property;
	}
	

	/* ###########################################################################
	 * Description: This method used to read data from excel
	 * @sheet: excel sheet, @featureName: feature file name, @colName: column heading for reference
	 * Created : 30-03-2020
	 * ###########################################################################
	*/
	
	@SuppressWarnings("resource")
	public String readExcel(String sheet, String featureName, String colName) throws IOException{
		
		int setrow=0,setcol=0;
		File fls = new File(dirPath+"//TestData.xlsx");
		FileInputStream fis = new FileInputStream(fls);
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sh = wb.getSheet(sheet);
		for(int i=0; i<=sh.getLastRowNum(); i++){
			Row row = sh.getRow(i);
			if(featureName.equalsIgnoreCase(row.getCell(0).getStringCellValue())){
				setrow =i;
				break;
			}
		}
		for(int j=0; j<=sh.getRow(0).getLastCellNum(); j++){
			if(colName.equalsIgnoreCase(sh.getRow(0).getCell(j).getStringCellValue())){
				setcol = j;
				break;
			}
		}
	return sh.getRow(setrow).getCell(setcol).getStringCellValue();
	}
	
	/* ###########################################################################
	 * Description: This method used to get list of features to be executed
	 * Created : 30-03-2020
	 * ###########################################################################
	*/
	
	@SuppressWarnings("resource")
	public static List<String> getFeatures() throws IOException{
		ArrayList<String> features = new ArrayList<String>();
		String featurepath = dirPath + "\\src\\main\\java\\features\\";
		File fls = new File(dirPath+"//Features.xlsx");
		FileInputStream fis = new FileInputStream(fls);
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sh = wb.getSheet("Features");
		for(int i=0; i<=sh.getLastRowNum(); i++){
			if(sh.getRow(i).getCell(1).getStringCellValue().equalsIgnoreCase("Yes"))
				features.add(featurepath + sh.getRow(i).getCell(0).getStringCellValue());
		}
		
		return features;
	}
	
	/* ###########################################################################
	 * Description: This method used to get list of tags to be executed
	 * Created : 30-03-2020
	 * ###########################################################################
	*/
	
	@SuppressWarnings("resource")
	public static String getTags() throws IOException{
		String tags = "";
		File fls = new File(dirPath+"//Features.xlsx");
		FileInputStream fis = new FileInputStream(fls);
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sh = wb.getSheet("Tags");
		for(int i=0; i<=sh.getLastRowNum(); i++){
			if(sh.getRow(i).getCell(1).getStringCellValue().equalsIgnoreCase("Yes"))
				
				tags = tags+(sh.getRow(i).getCell(0).getStringCellValue()+",");
		}
		
		return tags;
	}
	
	/* ###########################################################################
	 * Description: This method used to get list of browsers that each scenario file to be executed on
	 * Created : 30-03-2020
	 * ###########################################################################
	*/
	
	@SuppressWarnings("resource")
	public static List<String> getBrowsers() throws IOException{
		ArrayList<String> browsers = new ArrayList<String>();
		File fls = new File(dirPath+"//Features.xlsx");
		FileInputStream fis = new FileInputStream(fls);
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sh = wb.getSheet("Features");
		for(int i=0; i<=sh.getLastRowNum(); i++){
			if(sh.getRow(i).getCell(1).getStringCellValue().equalsIgnoreCase("Yes"))
				browsers.add(sh.getRow(i).getCell(2).getStringCellValue());
		}
		
		return browsers;
	}
	
	
	/* ###########################################################################
	 * Description: This method used to get list of Drivers (Selenium / Appium) that each scenario file to be executed on
	 * Created : 30-03-2020
	 * ###########################################################################
	*/
	
	@SuppressWarnings("resource")
	public static List<String> getDrivers() throws IOException{
		ArrayList<String> browsers = new ArrayList<String>();
		File fls = new File(dirPath+"//Features.xlsx");
		FileInputStream fis = new FileInputStream(fls);
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sh = wb.getSheet("Features");
		for(int i=0; i<=sh.getLastRowNum(); i++){
			if(sh.getRow(i).getCell(1).getStringCellValue().equalsIgnoreCase("Yes"))
				browsers.add(sh.getRow(i).getCell(3).getStringCellValue());
		}
		
		return browsers;
	}
	
	
	/* ###########################################################################
	 * Description: This method used to launch the browser and navigate to the application URL
	 * @browser: Browser to be launched
	 * Created : 06-04-2020
	 * ###########################################################################
	*/
	public void launchBrowser( String browser){
		
		if(browser.equalsIgnoreCase("Chrome")){
			WebDriverManager.chromedriver().setup();
			setDriver(new ChromeDriver());
			getDriver().manage().window().maximize();
		}
		else if(browser.equalsIgnoreCase("Firefox")){
			WebDriverManager.firefoxdriver().setup();
			setDriver(new FirefoxDriver());
			getDriver().manage().window().maximize();
		}
		
		else if(browser.equalsIgnoreCase("IE")){
			WebDriverManager.iedriver().setup();
			setDriver(new InternetExplorerDriver());
			getDriver().manage().window().maximize();
		}
		
		else if(browser.equalsIgnoreCase("Edge")){
			WebDriverManager.edgedriver().setup();
			setDriver(new EdgeDriver());
			getDriver().manage().window().maximize();
		}
		else if(browser.equalsIgnoreCase("Safari")){
			WebDriverManager.safaridriver().setup();
			setDriver(new SafariDriver());
			getDriver().manage().window().maximize();
		}
		else if(browser.equalsIgnoreCase("ChromeiPhone")){
			WebDriverManager.chromedriver().setup();
			mobileEmulation.put("deviceName", "iPhone 8");
//			mobileEmulation.put("userAgent","Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
			setDriver(new ChromeDriver(chromeOptions));
			getDriver().manage().window().maximize();
		}
		else if(browser.equalsIgnoreCase("ChromeAndroid")){
			WebDriverManager.chromedriver().setup();
			mobileEmulation.put("deviceName", "Galaxy S5");
//			mobileEmulation.put("userAgent","Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
			setDriver(new ChromeDriver(chromeOptions));
			getDriver().manage().window().maximize();
		}
		else{
			Reporter.addScenarioLog("Browser to be used not mentioned in properties file");
			Assert.fail("Browser has to be mentioned either in prop file or Fetures sheet ");
		}
		getDriver().manage().deleteAllCookies();
	}
	
	
	/* ###########################################################################
	 * Description: This method used to close all browser instances opened by webdriver
	 * Created : 06-04-2020
	 * ###########################################################################
	*/
	
	public static void closeDriver(String toolName){
		if(toolName.equalsIgnoreCase("Selenium")){
			driver.quit();}
		else if (toolName.equalsIgnoreCase("Appium")){
			mDriver.quit();
		}
	}
	
	 /* ###########################################################################
		 * Description: This method used to initiate mobile driver and launch mobile application
		 * Created : 07-05-2020
		 * ###########################################################################
	*/ 
	@SuppressWarnings("rawtypes")
	public void launchApp() throws IOException{
		
		try{
			String url = readProperty("ServerAddress");
			String port = readProperty("appiumport");
			String platform = readProperty("PlatformName");
			String platformversion = readProperty("Platformversion");
			String deviceName = readProperty("Devicename");
			String deviceUDID = readProperty("DeviceUDID");
			String deviceVersion = readProperty("Deviceversion");
			String appName = readProperty("ApplicationName");
			String appPath = readProperty("APKpath");
			String appType = readProperty("AppType");
			String appPackage = readProperty("appPackage");
			String appActivity = readProperty("appActivity");
			String browser = readProperty("mBrowser");
			url = url+":"+port+"/wd/hub";
			if(platform.equalsIgnoreCase("Android") && appType.equalsIgnoreCase("Native")){
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability("platformName", platform);
				capabilities.setCapability("platformVersion", platformversion);
				capabilities.setCapability("deviceName", deviceName);
				capabilities.setCapability("app", appPath);
				capabilities.setCapability("appPackage", appPackage);
				capabilities.setCapability("appActivity", appActivity);
				if(mDriver == null)
				mDriver =  new AndroidDriver(new URL(url), capabilities);
				mDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			}
			
			else if(platform.equalsIgnoreCase("Android") && appType.equalsIgnoreCase("Web")){
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability("platformName", platform);
				capabilities.setCapability("platformVersion", platformversion);
				capabilities.setCapability("deviceName", deviceName);
				capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
				capabilities.setCapability("newCommandTimeout", "300");
				if(mDriver == null)
				mDriver =  new AndroidDriver(new URL(url), capabilities);
				mDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			}
			
			else if(platform.equalsIgnoreCase("iOS") && appType.equalsIgnoreCase("Native")){
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability("platformName", platform);
				capabilities.setCapability("platformVersion", platformversion);
				capabilities.setCapability("deviceName", deviceUDID);
				capabilities.setCapability("app", appPath);
				if(mDriver == null)
				mDriver =  new IOSDriver(new URL(url), capabilities);
				mDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			}
			
			else if(platform.equalsIgnoreCase("iOS") && appType.equalsIgnoreCase("Web")){
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability("platformName", platform);
				capabilities.setCapability("platformVersion", platformversion);
				capabilities.setCapability("deviceName", deviceName);
				capabilities.setCapability("udid", deviceUDID);
				capabilities.setCapability("--default-device", "true");
				capabilities.setBrowserName("safari");
				if(mDriver == null)
				mDriver =  new IOSDriver(new URL(url), capabilities);
				mDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			}
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	/* ###########################################################################
	 * Description: This method used to navigate to the application URL
	 * Created : 06-04-2020
	 * ###########################################################################
	*/
	public void navigate(){
		
		try{
			String URL = readProperty("URL");
			switch (toolName){
			case "Selenium":
				if(URL.length()>0)
					driver.get(URL);
				else
					Reporter.addStepLog("Application URL not  mentioned in properties file");
					
				break;
			case "Appium":
				if(URL.length()>0)
					mDriver.get(URL);
				else
					Reporter.addStepLog("Application URL not  mentioned in properties file");
				break;
			}
			
		}
		catch (Exception e){
			Reporter.addStepLog("Error navigating to url : "+e.toString());
			Assert.fail("error navigating to url");
		}
	}
	
	public void navigate1() {
	    try {
	        String URL = readProperty("URL");
	        if (driver != null) {
	            switch (toolName) {
	                case "Selenium":
	                    if (URL.length() > 0)
	                        getDriver().get(URL);
	                    else
	                        Reporter.addStepLog("Application URL not mentioned in properties file");

	                    break;
	                case "Appium":
	                    if (URL.length() > 0)
	                        mDriver.get(URL);
	                    else
	                        Reporter.addStepLog("Application URL not mentioned in properties file");
	                    break;
	            }
	        } else {
	            Reporter.addStepLog("WebDriver is not initialized.");
	            Assert.fail("WebDriver is not initialized.");
	        }
	    } catch (Exception e) {
	        Reporter.addStepLog("Error navigating to URL: " + e.toString());
	        Assert.fail("Error navigating to URL");
	    }
	}

	
	
	/* ###########################################################################
	 * Description: This method used to navigate to the application URL
	 * @url: URL to be navigated
	 * Created : 06-04-2020
	 * ###########################################################################
	*/
	public void navigate(String url){
		
		try{
			
			switch (toolName){
			case "Selenium":
				getDriver().get(url);
				break;
			case "Appium":
				mDriver.get(url);
				break;
			}
		}
		catch (Exception e){
			Reporter.addStepLog(e.toString());
			Assert.fail("error navigating to url");
		}
	}
	
	
	/* ###########################################################################
	 * Description: This method used to enter text in text field
	 * @ele : Act on element, @str: text to be entered
	 * Created : 06-04-2020
	 * ###########################################################################
	*/
	
	public void enterText(WebElement ele, String str) throws Exception{
		
	try{
		switch (toolName){
		case "Selenium":
			wait = new WebDriverWait(getDriver(), 60);
			break;
		case "Appium":
			wait = new WebDriverWait(mDriver, 60);
			break;
		}
		wait.until(ExpectedConditions.visibilityOf(ele));
		ele.sendKeys(str);
		Reporter.addStepLog("Entered text "+""+str+""+" in element ");
	}
	catch (Exception e){
		Reporter.addStepLog(e.toString());
		Reporter.addScreenCaptureFromPath(screenshot());
		Assert.fail("Exception on entering text in element: " + ele);
	}
		
	}
	
	
	/* ###########################################################################
	 * Description: This method used to navigate back on browser
	 * Created : 06-04-2020
	 * ###########################################################################
	*/
	
	public void navigateBack() throws Exception {
			switch(toolName){
			case "Selenium":
				getDriver().navigate().back();
				getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				break;
			case "Appium":
				mDriver.navigate().back();
				mDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				break;
			}
		}

	/* ###########################################################################
	 * Description: This method used to click Enter button on keyboard
	 * @ele: Act on element
	 * Created : 16-04-2020
	 * ###########################################################################
	*/
	
	public void keyBoardEnter(WebElement ele) throws Exception {
		
		try{
			switch (toolName){
			case "Selenium":
				wait = new WebDriverWait(getDriver(), 60);
				break;
			case "Appium":
				wait = new WebDriverWait(mDriver, 60);
				break;
			}
			wait.until(ExpectedConditions.visibilityOf(ele));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			ele.sendKeys(Keys.ENTER);
		}
		catch (Exception e){
			Reporter.addStepLog(e.toString());
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail("Exception  on pressing Enter on element: " + ele);
		}
		
	}
	
	/* ###########################################################################
	 * Description: This method used to click on element 
	 * @ele: Act on element
	 * Created : 16-04-2020
	 * ###########################################################################
	*/
	
	public void clickUsingJS(WebElement ele) throws Exception {
		try {
			
			switch (toolName){
			case "Selenium":
				wait = new WebDriverWait(getDriver(), 60);
				break;
			case "Appium":
				wait = new WebDriverWait(mDriver, 60);
				break;
			}
				wait.until(ExpectedConditions.visibilityOf(ele));
				wait.until(ExpectedConditions.elementToBeClickable(ele));
				((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", ele);
				Reporter.addStepLog("Click on element is successful");
				
		} 
		catch (Exception exc) {
			exc.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail("Exception on clicking webelement: " + ele);

			throw new Exception();
		}

	}
	
	/* ###########################################################################
	 * Description: This method used to take screen shot of the page 
	 * Created : 16-04-2020
	 * ###########################################################################
	*/
	public static String screenshot() throws Exception {
		String imgPath = null;
		Thread.sleep(2000);
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_MM_SS");
		Date date = new Date();
		String timeStamp = dateFormat.format(date);
		File scrFileSelenium = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		imgPath = RunCukes.resultFolder + "/screenshots" + "/" + timeStamp + ".png";
		FileUtils.copyFile(scrFileSelenium, new File(imgPath));
		return new File(imgPath).getAbsolutePath();
	}
	
	/* ###########################################################################
	 * Description: This method used to click on an element
	 * @ele: Act on element
	 * Created : 16-04-2020
	 * ###########################################################################
	*/
	public void click(WebElement ele) throws Exception{
		try{
			switch (toolName){
			case "Selenium":
				wait = new WebDriverWait(getDriver(), 60);
				break;
			case "Appium":
				wait = new WebDriverWait(mDriver, 60);
				break;
			}
		wait.until(ExpectedConditions.visibilityOf(ele));
		wait.until(ExpectedConditions.elementToBeClickable(ele));
		ele.click();
		Reporter.addStepLog("Click on element successful");
		}
		catch (Exception e){
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail("Exception on clicking webelement: " );
		}
	}
	
	
	/* ###########################################################################
	 * Description: This method used to click on an element from the list of similar Elements
	 * @ele: Act on element
	 * @String: Element value to be clicked
	 * Created : 23-08-2022
	 * ###########################################################################
	*/
	public void clickFromList(List<WebElement> ele, String value) throws Exception{
		try{
			switch (toolName){
			case "Selenium":
				wait = new WebDriverWait(getDriver(), 60);
				break;
			case "Appium":
				wait = new WebDriverWait(mDriver, 60);
				break;
			}
		wait.until(ExpectedConditions.visibilityOfAllElements(ele));
		for(WebElement e: ele) {
			if(e.getText().equalsIgnoreCase(value)) {
				Thread.sleep(8000);
				e.click();
				Reporter.addStepLog("Click on element from list of elements is successful");
				break;
			}
		}
		
		}
		catch (Exception e){
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail("Exception on clicking webelement: " );
		}
	}
	
	
	
	/* ###########################################################################
	 * Description: This method used to select the value from dropdown
	 * @ele: Act on element, @val: value to be selected
	 * Created : 16-04-2020
	 * ###########################################################################
	*/
	public void selectByValue(WebElement ele, String value) throws Exception{
		try{
			switch (toolName){
			case "Selenium":
				wait = new WebDriverWait(getDriver(), 60);
				break;
			case "Appium":
				wait = new WebDriverWait(mDriver, 60);
				break;
			}
			wait.until(ExpectedConditions.visibilityOf(ele));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			Select option = new Select(ele);
			option.selectByValue(value);
			Reporter.addStepLog("Option " + value + "" + " selected successful");
		}
		catch (Exception e){
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail( "Exception on selecting option " );
		}
	}
	
	
	/* ###########################################################################
	 * Description: This method used to select the value from dropdown
	 * @ele: Act on element, @val: value to be selected
	 * Created : 16-04-2020
	 * ###########################################################################
	*/
	public void selectByVisibleText(WebElement ele, String strText) throws Exception{
		
		System.out.println("selectByVisibleText");
		try{
			switch (toolName){
			case "Selenium":
				wait = new WebDriverWait(getDriver(), 60);
				break;
			case "Appium":
				wait = new WebDriverWait(mDriver, 60);
				break;
			}
			wait.until(ExpectedConditions.visibilityOf(ele));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			Select option = new Select(ele);
			option.selectByVisibleText(strText);
			Reporter.addStepLog("Option  " + strText + "" + " selected successful");
		}
		catch (Exception e){
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail( "Exception on selecting option " );
		}
	}
	
	/* ###########################################################################
	 * Description: This method used to select the value from dropdown
	 * @ele: Act on element, @val: value to be selected
	 * Created : 16-04-2020
	 * ###########################################################################
	*/
	public void selectByIndex(WebElement ele, int index) throws Exception{
		try{
			switch (toolName){
			case "Selenium":
				wait = new WebDriverWait(getDriver(), 60);
				break;
			case "Appium":
				wait = new WebDriverWait(mDriver, 60);
				break;
			}
			wait.until(ExpectedConditions.visibilityOf(ele));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			Select option = new Select(ele);
			option.selectByIndex(index);
			Reporter.addStepLog("Option at index " + index + "" + " selected successful");
		}
		catch (Exception e){
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail( "Exception on selecting option " );
		}
	}
	
	
	/* ###########################################################################
	 * Description: This method used to get the selected value from dropdown
	 * @ele: Act on element
	 * Created : 16-04-2020
	 * ###########################################################################
	*/
	public String getSelectedValue(WebElement ele) throws Exception{
		String selectedvalue="";
		try{
			switch (toolName){
			case "Selenium":
				wait = new WebDriverWait(getDriver(), 60);
				break;
			case "Appium":
				wait = new WebDriverWait(mDriver, 60);
				break;
			}
			wait.until(ExpectedConditions.visibilityOf(ele));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			Select option = new Select(ele);
			selectedvalue=option.getFirstSelectedOption().getText();
			Reporter.addStepLog("Option " + selectedvalue + "" + " selected successful");
		}
		
		catch (Exception e){
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail("Exception in getting selected value");
		}
		
		return selectedvalue;
	}
	
	/* ###########################################################################
	 * Description: This method used to drag and drop the element 
	 * @ele1: drag element, @ele2: Drop element
	 * Created : 16-04-2020
	 * ###########################################################################
	*/
	public void dragAndDrop(WebElement ele1, WebElement ele2) throws Exception {
		try{
		Actions action1 = new Actions(getDriver());
		action1.dragAndDrop(ele1, ele2).perform();
		}
		catch (Exception e){
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail("Exception on dargging and dropping webelement from to To: ");
		}
	}
	
	/* ###########################################################################
	 * Description: This method used to get the text from the element
	 * @ele: act on element,
	 * @return element text
	 * Created : 16-04-2020
	 * ###########################################################################
	*/
	public String getText(WebElement ele) throws Exception {
		String text = "";
		try{
			switch (toolName){
			case "Selenium":
				wait = new WebDriverWait(getDriver(), 60);
				break;
			case "Appium":
				wait = new WebDriverWait(mDriver, 60);
				break;
			}
			wait.until(ExpectedConditions.visibilityOf(ele));
			text = ele.getText();
			Reporter.addStepLog("The Element text is " + text + " ");
		}
		catch (Exception e){
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail("Exception on getting element text: " + ele);
		}
		return text;
	}
	
	/* ###########################################################################
	 * Description: This method used to compare two element text values
	 * @ele1: text element1, @ele2: text element2,
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	public void compreElementText(WebElement ele1, WebElement ele2) throws Exception{
		
		try{
			switch (toolName){
			case "Selenium":
				wait = new WebDriverWait(getDriver(), 60);
				break;
			case "Appium":
				wait = new WebDriverWait(mDriver, 60);
				break;
			}
			wait.until(ExpectedConditions.visibilityOf(ele1));
			wait.until(ExpectedConditions.visibilityOf(ele2));
			Assert.assertEquals(ele1.getText(), ele2.getText());
			
		}
		catch (Exception e){
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail("Exception in asserting two text");
		}
	}
	
	/* ###########################################################################
	 * Description: This method used to assert element text with given text
	 * @ele: text element, @str : text string to compare
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	public void assertText(String str, WebElement ele) throws Exception{
		
		try{
			switch (toolName){
			case "Selenium":
				wait = new WebDriverWait(getDriver(), 60);
				break;
			case "Appium":
				wait = new WebDriverWait(mDriver, 60);
				break;
			}
			wait.until(ExpectedConditions.visibilityOf(ele));
			Assert.assertEquals(str, ele.getText());
			
		}
		catch (Exception e){
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail("Exception in asserting text");
		}
	}
	
	/* ###########################################################################
	 * Description: This method used to scroll page down / up to get element visible
	 * @ele: act on element, 
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	public void scrollToElement(WebElement ele) throws Exception {
		try{
			switch (toolName){
			case "Selenium":
				wait = new WebDriverWait(getDriver(), 60);
				break;
			case "Appium":
				wait = new WebDriverWait(mDriver, 60);
				break;
			}
		wait.until(ExpectedConditions.visibilityOf(ele));
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("arguments[0].scrollIntoView();", ele);
		Reporter.addStepLog("Scrolled the webpage till element visible");
		}
		catch (Exception e){
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail("Exception in scrolling the page till webelement visible " );
		}
	}
	
	/* ###########################################################################
	 * Description: This method used to get whether the element is visible or not
	 * @ele: act on element, 
	 * @return boolean
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	public Boolean elementIsDisplayed(WebElement ele) throws Exception{
		try{
			switch (toolName){
			case "Selenium":
				wait = new WebDriverWait(getDriver(), 60);
				break;
			case "Appium":
				wait = new WebDriverWait(mDriver, 60);
				break;
			}
			wait.until(ExpectedConditions.visibilityOf(ele));
			if (ele.isDisplayed()) {
				Reporter.addStepLog("The Element is displayed");
				return true;

			} else
				Reporter.addStepLog("The Element is not displayed");
				Reporter.addScreenCaptureFromPath(screenshot());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail("Exception on locating the element ");
			return false;
		}
	}
	
	
	/* ###########################################################################
	 * Description: This method used to get whether the element is enabled or not
	 * @ele: act on element, 
	 * @return boolean
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	public boolean elementIsEnabled(WebElement ele) throws Exception {
		try {
			
				switch (toolName){
				case "Selenium":
					wait = new WebDriverWait(getDriver(), 60);
					break;
				case "Appium":
					wait = new WebDriverWait(mDriver, 60);
					break;
				}
				wait.until(ExpectedConditions.visibilityOf(ele));
				if (ele.isEnabled()) {
					return true;
				} else
					return false;
		} 
		catch (Exception e) {
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.assertTrue(false, "Element not enabled");
			return false;
		}
		
	}
	
	
	/* ###########################################################################
	 * Description: This method used to switch to default frame / window
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	
	public void switchToDefaultFrame() throws Exception {
		
		try{
			switch (toolName){
			case "Selenium":
				getDriver().switchTo().defaultContent();
				break;
			case "Appium":
				mDriver.switchTo().defaultContent();
				break;
			}
		}
		catch (Exception e){
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail("Exception while switching to default content ");
		}
		
	}
	
	
	/* ###########################################################################
	 * Description: This method used to switch driver to frame
	 * @ele: act on element, 
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	
	public void switchToFrame(WebElement ele) throws Exception {
		
		try{
			switch (toolName){
			case "Selenium":
				wait = new WebDriverWait(getDriver(), 60);
				wait.until(ExpectedConditions.visibilityOf(ele));
				getDriver().switchTo().frame(ele);
				break;
			case "Appium":
				wait = new WebDriverWait(mDriver, 60);
				wait.until(ExpectedConditions.visibilityOf(ele));
				mDriver.switchTo().frame(ele);
				break;
			}
			
		}
		catch (Exception e){
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail("Exception while switching to frame ");
		}
		
	}
	
	/* ###########################################################################
	 * Description: This method used to switch parent tab by closing child tab
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	public void switchToParentTab() throws Exception {
		try{
		ArrayList<String> tabs = new ArrayList<String>(getDriver().getWindowHandles());
		switch (toolName){
		case "Selenium":
			getDriver().switchTo().window(tabs.get(1));
			getDriver().close();
			getDriver().switchTo().window(tabs.get(0));
			break;
		case "Appium":
			mDriver.switchTo().window(tabs.get(1));
			mDriver.close();
			mDriver.switchTo().window(tabs.get(0));
			break;
		}
		}
		catch (Exception e){
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail( "Exception while switching to parent tab");
		}
	}

	
	
	/* ###########################################################################
	 * Description: This method used to switch child tab
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	public void switchToChildTab() throws Exception{
		try{
		ArrayList<String> tabs = new ArrayList<String>(getDriver().getWindowHandles());
		switch (toolName){
		case "Selenium":
			getDriver().switchTo().window(tabs.get(1));
			break;
		case "Appium":
			mDriver.switchTo().window(tabs.get(1));
			break;
		}
		}
		catch (Exception e){
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail("Exception while switching to child tab");
		}
	}
	
	
	/* ###########################################################################
	 * Description: This method used to move focus to the element
	 * @ele: act on element, 
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	
	public void moveToElement(WebElement ele) throws Exception {
		Actions actions;
		try{
			
			switch (toolName){
			case "Selenium":	
				wait = new WebDriverWait(getDriver(), 60);
				wait.until(ExpectedConditions.visibilityOf(ele));
				actions = new Actions(getDriver());
				actions.moveToElement(ele).build().perform();
				break;
			case "Appium":
				wait = new WebDriverWait(mDriver, 60);
				wait.until(ExpectedConditions.visibilityOf(ele));
				actions = new Actions(mDriver);
				actions.moveToElement(ele).build().perform();
				break;
			}
			
		}
		catch (Exception e){
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail("Exception while moving focus to element ");
		}
		
	}
	
	
	/* ###########################################################################
	 * Description: This method used to move focus to the element and click
	 * @ele: act on element, 
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	
	public void moveAndClick(WebElement ele) throws Exception {
		Actions actions;
		try{
			switch (toolName){
			case "Selenium":	
				wait = new WebDriverWait(getDriver(), 60);
				wait.until(ExpectedConditions.visibilityOf(ele));
				actions = new Actions(getDriver());
				actions.moveToElement(ele).build().perform();
				ele.click();
				break;
			case "Appium":
				wait = new WebDriverWait(mDriver, 60);
				wait.until(ExpectedConditions.visibilityOf(ele));
				actions = new Actions(mDriver);
				actions.moveToElement(ele).build().perform();
				ele.click();
				break;
			}
		}
		catch (Exception e){
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail("Exception while moving focus to element ");
		}
		
	}
	
	/* ###########################################################################
	 * Description: This method used to clear the text element
	 * @ele: act on element, 
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	
	public void clearTheElement(WebElement ele) throws Exception {
		
		try{
			switch (toolName){
			case "Selenium":
				wait = new WebDriverWait(getDriver(), 60);
				break;
			case "Appium":
				wait = new WebDriverWait(mDriver, 60);
				break;
			}
			wait.until(ExpectedConditions.visibilityOf(ele));
			ele.clear();
			Reporter.addStepLog("Text in the element removed");
		}
		catch (Exception e){
			e.printStackTrace();
			Reporter.addScreenCaptureFromPath(screenshot());
			Assert.fail("Exception while removing text from field element ");
		}
		
	}
	
	
	/* ###########################################################################
	 * Description: This method used to refresh the page
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	
	public void refresh() throws Exception {
		
		try{
			switch (toolName){
			case "Selenium":
				getDriver().navigate().refresh();
				break;
			case "Appium":
				wait = new WebDriverWait(mDriver, 60);
				mDriver.navigate().refresh();
				break;
			}
		}
		catch (Exception e){
			e.printStackTrace();
			
		}
		
	}
	
	
	public String pageSource() throws Exception{
    	String source="";
		 try{
			  source=driver.getPageSource();
		 }
		 catch (Exception e){
			 e.printStackTrace();
		 }
		 return source;
	 }
	
	/* ###########################################################################
	 * Description: This method used to delete all cookies
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	
	public void deleteAllCookies() throws Exception {
		
		try{
			switch (toolName){
			case "Selenium":
				getDriver().manage().deleteAllCookies();
				Reporter.addStepLog("Cookies are deleted");
				break;
			case "Appium":
				mDriver.manage().deleteAllCookies();
				Reporter.addStepLog("Cookies are deleted");
				break;
			}
			
		}
		catch (Exception e){
			e.printStackTrace();
			
		}
		
	}
	

	/* ###########################################################################
	 * Description: This method used to get current page URL
	 * @return String
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	
	public String getPageUrl() throws Exception {
		String Url="";
		try{
			switch (toolName){
			case "Selenium":
				Url = getDriver().getCurrentUrl();
				break;
			case "Appium":
				Url = mDriver.getCurrentUrl();
				break;
			}
		}
		catch (Exception e){
			e.printStackTrace();
			Assert.fail("Error fetching the URL");
		}
		return Url;
	}
	
	public void alertdisplayed() throws Exception {
		try{
			if(wait.until(ExpectedConditions.alertIsPresent()) != null)
			{
				acceptAlert();
			}
			else {
				
			}
		 }
		 catch (Exception e){
			 e.printStackTrace();
			 Reporter.addScreenCaptureFromPath(screenshot());
			 Assert.fail("Exception in accepting Alert");
		 }
	
	}
	
	
	/* ###########################################################################
	 * Description: This method used to wait until page gets load
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	 public void waitForPageToLoad() {
	        ExpectedCondition<Boolean> expectation = new
	                ExpectedCondition<Boolean>() {
	                    public Boolean apply(WebDriver driver) {
	                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
	                    }
	                };
	        try {
	            //Thread.sleep(1000);
	            WebDriverWait wait = new WebDriverWait(getDriver(), 60, 100);
	            wait.until(expectation);
	        } catch (Throwable error) {
	            Assert.fail("Timeout waiting for Page Load Request to complete.");
	        }
	    }
	
	
	 /* ###########################################################################
		 * Description: This method used to Accept the java script alert
		 * Created : 18-04-2020
		 * ###########################################################################
		*/ 
	 public void acceptAlert() throws Exception{
		 try{
			 Alert alert = getDriver().switchTo().alert();
			 alert.accept();
			 Reporter.addStepLog("Alert Acceppted");
		 }
		 catch (Exception e){
			 e.printStackTrace();
			 Reporter.addScreenCaptureFromPath(screenshot());
			 Assert.fail("Exception in accepting Alert");
		 }
	 }
	 
	 
	 public void alertpresent() throws Exception {
			
			try{
				switch (toolName){
				case "Selenium":
					wait = new WebDriverWait(getDriver(), 60);
					wait.until(ExpectedConditions.alertIsPresent());
					getDriver().switchTo().alert();
					acceptAlert();
					break;
				case "Appium":
					wait = new WebDriverWait(mDriver, 60);
					wait.until(ExpectedConditions.alertIsPresent());
					mDriver.switchTo().alert();
					acceptAlert();
					break;
				}
				
			}
			catch (Exception e){
				e.printStackTrace();
				Reporter.addScreenCaptureFromPath(screenshot());
				Assert.fail("Exception while switching to frame ");
			}
			
		}
	 
	 /* ###########################################################################
		 * Description: This method used to Dismiss the java script alert
		 * Created : 18-04-2020
		 * ###########################################################################
		*/ 
	 public void dismissAlert() throws Exception{
		 try{
			 Alert alert = getDriver().switchTo().alert();
			 alert.dismiss();
			 Reporter.addStepLog("Alert Dismissed");
		 }
		 catch (Exception e){
			 e.printStackTrace();
			 Reporter.addScreenCaptureFromPath(screenshot());
			 Assert.fail("Exception Dismissing the Alert");
		 }
	 }
	 
	 
	 /* ###########################################################################
		 * Description: This method used to Get Alert message
		 * Created : 18-04-2020
		 * ###########################################################################
		*/ 
	 public String getAlerrtMessage() throws Exception{
		 String message="";
		 try{
			 Alert alert = getDriver().switchTo().alert();
			 message = alert.getText();
			 Reporter.addStepLog("Alert message captured");
		 }
		 catch (Exception e){
			 e.printStackTrace();
			 Reporter.addScreenCaptureFromPath(screenshot());
			 Assert.fail("Exception in getting the Alert text");
		 }
		 return message;
	 }
	 
	 /* ###########################################################################
		 * Description: This method used to Enter text in Alert box
		 * Created : 18-04-2020
		 * ###########################################################################
		*/ 
	 public void enterTextInAlet(String str) throws Exception{
		 
		 try{
			 Alert alert = getDriver().switchTo().alert();
			 alert.sendKeys(str);
			 Reporter.addStepLog("Text entered in Alert box");
		 }
		 catch (Exception e){
			 e.printStackTrace();
			 Reporter.addScreenCaptureFromPath(screenshot());
			 Assert.fail("Exception entering text in the Alert");
		 }
		 
	 }
	 
	 public WebElement findElementByXpath(String ele) throws Exception{
		 WebElement element=null;
			try{
				switch (toolName){
				case "Selenium":
					wait = new WebDriverWait(getDriver(), 60);
					break;
				case "Appium":
					wait = new WebDriverWait(mDriver, 60);
					break;
				}
//				System.out.println(ele);
				element = getDriver().findElement(By.xpath(ele));
				Reporter.addStepLog("Element Identified ");
				
			}
			catch (Exception e){
				Reporter.addStepLog(e.toString());
				Reporter.addScreenCaptureFromPath(screenshot());
				Assert.fail("Element not identified ");
			}
				
			return element;
			}
	 
    public void passxycoordinate(WebElement ele) {
		 
		 int x=ele.getLocation().getX();
		 int y=ele.getLocation().getY();
			
		System.out.println("X card coordinate "  + x);
			System.out.println("Y card coordinate "  + y);	
		 Actions act = new Actions(getDriver());
		// act.moveByOffset(439, 22).click().build().perform();
		 act.moveByOffset(x, y).click();
		// act.build().perform();
	 }
    
    public void RobotKey() throws Exception{
		 
		 Robot Rbt = new Robot();
		 
		 Rbt.keyPress(KeyEvent.VK_SPACE);
		 Rbt.keyRelease(KeyEvent.VK_SPACE);
		 Rbt.keyPress(KeyEvent.VK_TAB);
		 Rbt.keyRelease(KeyEvent.VK_TAB);
		 Rbt.keyPress(KeyEvent.VK_TAB);
		 Rbt.keyRelease(KeyEvent.VK_TAB);
		 Rbt.keyPress(KeyEvent.VK_ENTER);
		 Rbt.keyRelease(KeyEvent.VK_ENTER);
	 }

	public static RemoteWebDriver getDriver() {
		return driver;
	}

	public static void setDriver(RemoteWebDriver driver) {
		PageReusable.driver = driver;
	}
	 
}

