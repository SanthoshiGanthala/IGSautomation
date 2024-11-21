package pageObjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import commonMethods.PageReusable;

public class loginPo extends PageReusable {

	public loginPo() {
		super();
	}

	public WebDriver driver;

	String reportFileName = initializeReportFileName();

	ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportFileName);
	ExtentReports extent = new ExtentReports();

	private static String initializeReportFileName() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date date = new Date();
		return "IGS Subcategory Report" + dateFormat.format(date) + ".html";
	}

	@FindBy(xpath = "//*[@id='txt_UserName']")
	WebElement username;

	@FindBy(xpath = "//*[@id='txt_Password']")
	WebElement pwd;

	@FindBy(xpath = "//span[@id='lblCaptcha']")
	WebElement captcha;

	@FindBy(xpath = "//*[@id='txtCaptcha']")
	WebElement captchainput;

	@FindBy(id = "btnLogin")
	WebElement login;

	@FindBy(xpath = "//*[@id='ContentPlaceHolder1_txtFromdate']")
	WebElement fromdate;

	@FindBy(xpath = "//th[@class='datepicker-switch' and text()='March 2024']")
	WebElement months;

	@FindBy(xpath = "//th[@class='datepicker-switch' and text()='2024']")
	WebElement years;

	@FindBy(xpath = "//th[@class='prev']")
	WebElement prev;

	@FindBy(xpath = "//*[@id='ContentPlaceHolder1_txtTodate']")
	WebElement todate;

	@FindBy(xpath = "//*[@id='ContentPlaceHolder1_btnSubmit']")
	WebElement submit;

	@FindBy(xpath = "//a[@href='SubCategoryWiseReportNew.aspx']")
	WebElement subcat_element;
	
	@FindBy(xpath="//a[@href='GrievanceCategoryWiseReport.aspx']")
	WebElement cat_element;

	@FindBy(xpath = "//a[@href='#' and text()='1']")
	WebElement onebutton;

	@FindBy(xpath = "//*[@class='dataTables_info']")
	WebElement text;

	@FindBy(xpath = "//*[@id='ContentPlaceHolder1_btn_Back1']")
	WebElement back;

	@FindBy(xpath = "//i[@class='fa fa-chevron-right']")
	WebElement nxtpage;

	@FindBy(xpath = "//input[@id='btnLogout']")
	WebElement logout;

	@FindBy(xpath = "//a[@href='javascript:;']")
	WebElement img;

	@FindBy(xpath = "//*[@id='ContentPlaceHolder1_ddlZone']")
	WebElement zone;

	@FindBy(xpath = "//a[@class='nav-link' and text()='Home']")
	WebElement home;

	@FindBy(xpath = "//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails']/tfoot/tr/td[%d]")
	List<WebElement> webElements;

	@FindBy(xpath = "//*[@id='ContentPlaceHolder1_ddlSource']")
	WebElement source;
	
	@FindBy(xpath="//input[@class='form-control input-sm']")
	WebElement search;
	
	int total_reg_sum, total1_reg_sum, total_pen_sum, total1_pen_sum, open_count, open1_count, under_process,
			under1_process;
	int res_by_off_sum, res1_by_off_sum, clo_by_cit_sum, clo1_by_cit_sum, rec_from_other_ward, rec1_from_other_ward;
	int sub_for_sanction_sum, sub1_for_sanction_sum, non_ghmc, non1_ghmc, out_of_ghmc, out1_of_ghmc, oth_status_sum,
			oth1_status_sum;

	public void processElements(int flag) throws Exception {

		int TdValues = 13;
		for (int i = 3; i <= TdValues; i++) {

			String xpath = String.format("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails']/tfoot/tr/td[%d]", i);
			WebElement element = findElementByXpath(xpath);
			String value = element.getText().replace(",", "");
			int intValue = Integer.parseInt(value);

			switch (i) {
			case 3:
				if (flag == 0) {
					total_reg_sum = intValue;
				} else {
					total1_reg_sum = intValue;
				}
				break;
			case 4:
				if (flag == 0) {
					total_pen_sum = intValue;
				} else {
					total1_pen_sum = intValue;
				}
				break;
			case 5:
				if (flag == 0) {
					open_count = intValue;
				} else {
					open1_count = intValue;
				}
				break;
			case 6:
				if (flag == 0) {
					under_process = intValue;
				} else {
					under1_process = intValue;
				}
				break;
			case 7:
				if (flag == 0) {
					res_by_off_sum = intValue;
				} else {
					res1_by_off_sum = intValue;
				}
				break;
			case 8:
				if (flag == 0) {
					clo_by_cit_sum = intValue;
				} else {
					clo1_by_cit_sum = intValue;
				}
				break;
			case 9:
				if (flag == 0) {
					rec_from_other_ward = intValue;
				} else {
					rec1_from_other_ward = intValue;
				}
				break;
			case 10:
				if (flag == 0) {
					sub_for_sanction_sum = intValue;
				} else {
					sub1_for_sanction_sum = intValue;
				}
				break;
			case 11:
				if (flag == 0) {
					non_ghmc = intValue;
				} else {
					non1_ghmc = intValue;
				}
				break;
			case 12:
				if (flag == 0) {
					out_of_ghmc = intValue;
				} else {
					out1_of_ghmc = intValue;
				}
				break;
			case 13:
				if (flag == 0) {
					oth_status_sum = intValue;
				} else {
					oth1_status_sum = intValue;
				}
				break;
			default:
				break;
			}
		}
	}

	enum Month {
		Jan(1), Feb(2), Mar(3), Apr(4), May(5), Jun(6), Jul(7), Aug(8), Sep(9), Oct(10), Nov(11), Dec(12);

		private int value;

		Month(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static String getMonth(int number) {
			for (Month month : Month.values()) {
				if (month.getValue() == number) {
					return String.valueOf(month);
				}
			}
			return null; // Return null if the input number doesn't match any month.
		}
	}

	public void date_click() throws Exception {

		fromdate.click();
		Thread.sleep(1000);

		months.click();
		years.click();

		String from = readExcel("dates", "1", "fromdate");
		String[] dateparts = from.split("-");

		String year_val = dateparts[2];
		String xpath_year = "//span[text()='" + year_val + "']";
		WebElement yearsele = findElementByXpath(xpath_year);
		int ye = Integer.parseInt(year_val);

		if (ye >= 2020 && ye <= 2029) {
			yearsele.click();
		}

		else if (ye >= 2010 && ye <= 2019) {
			prev.click();
			WebElement yearsele0 = findElementByXpath(xpath_year);
			yearsele0.click();
		}

		String month_val = dateparts[1];
		int mn = Integer.parseInt(month_val);
		String m = Month.getMonth(mn);

		String xpath_month = "//span[text()='" + m + "']";
		WebElement monele = findElementByXpath(xpath_month);
		monele.click();

		String day_val = dateparts[0];
		String xpath_day = "//td[@class='day'][text()='" + day_val + "']";
		WebElement date = findElementByXpath(xpath_day);
		date.click();

		todate.click();
		months.click();
		years.click();

		String to = readExcel("dates", "1", "todate");
		String[] dateparts1 = to.split("-");

		String year_val1 = dateparts1[2];

		String xpath_year1 = "//span[text()='" + year_val1 + "']";
		WebElement yearsele1 = findElementByXpath(xpath_year1);
		int ye1 = Integer.parseInt(year_val1);

		if (ye1 >= 2020 && ye1 <= 2029) {
			yearsele1.click();
		}

		else if (ye1 >= 2010 && ye1 <= 2019) {
			prev.click();
			WebElement yearsele01 = findElementByXpath(xpath_year1);
			yearsele01.click();
		}

		String month_val1 = dateparts1[1];
		int tmn = Integer.parseInt(month_val1);
		String tm = Month.getMonth(tmn);

		String xpath_month1 = "//span[text()='" + tm + "']";
		WebElement monele1 = findElementByXpath(xpath_month1);
		monele1.click();

		String day_val1 = dateparts1[0];
		String xpath_day1 = "//td[@class='day'][text()='" + day_val1 + "']";
		WebElement date1 = findElementByXpath(xpath_day1);
		date1.click();

		Thread.sleep(3000);

	}

	public void captcha_inp() throws Exception {

		String c = captcha.getText();
		String[] parts = c.split("\\s+");

		int operand1 = Integer.parseInt(parts[0]);
		String operator = parts[1];
		int operand2 = Integer.parseInt(parts[2]);

		int result;
		switch (operator) {

		case "+":
			result = operand1 + operand2;
			break;

		case "-":
			result = operand1 - operand2;
			break;

		default:
			throw new IllegalArgumentException("Unsupported operator: " + operator);
		}

		String resultString = String.valueOf(result);
		captchainput.sendKeys(resultString);
		Thread.sleep(1000);

	}

	public void selecting_details() throws Exception {

		username.sendKeys("admin");
		pwd.sendKeys("ghmc123");
		captcha_inp();
		login.click();
		Thread.sleep(500);

	}

	public void checkColumnSum(ExtentTest testCol, int sum, int totalCount, String columnName) {

		if (sum == totalCount) {
			testCol.pass("Test passed for column " + columnName
					+ " as sum of all values in column is equal to total count " + sum);
		} else {
			testCol.fail("Test failed for column " + columnName
					+ " as sum of all values in column is not equal to total count " + sum);
		}

	}

	// Column Sum of grievance subcategory
	public void column_sum() throws Exception {

		extent.attachReporter(htmlReporter);
		ExtentTest test1 = extent.createTest("TEST CASE 1 - Column sum validation ");

		subcat_element.click();
		// Thread.sleep(3000);

		date_click();
		submit.click();

//		WebDriverWait wait = new WebDriverWait(driver, 800); // 10 seconds timeout
//	    wait.until(ExpectedConditions.visibilityOf(text));

		Thread.sleep(3000);
		int sum1 = 0;
		int sum2 = 0;
		int sum3 = 0;
		int sum4 = 0;
		int sum5 = 0;
		int sum6 = 0;
		int sum7 = 0;
		int sum8 = 0;
		int sum9 = 0;
		int sum10 = 0;
		int sum11 = 0;

		String text01 = text.getText();
		String parts1[] = text01.split("of");
		String entriesNumber1 = parts1[1].trim().split(" ")[0]; // Extract the number part
		int totalEntries1 = Integer.parseInt(entriesNumber1.trim());

		processElements(0);

		for (int j = 0; j < totalEntries1; j++) {
			if (j > 0 && j % 10 == 0) {
				nxtpage.click();
			}

			int rowtext01 = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lblTotalReg_" + j + "']")
							.getText());
			sum1 = sum1 + rowtext01;
			int rowc02t = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lblTotalPending_" + j + "']")
							.getText());
			sum2 = sum2 + rowc02t;
			int rowc03t = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lbtnOpen_" + j + "']")
							.getText());
			sum3 = sum3 + rowc03t;
			int rowc04t = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lbtnUNDERPROCESS_" + j + "']")
							.getText());
			sum4 = sum4 + rowc04t;
			int rowc05t = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lbtnRes_" + j + "']")
							.getText());
			sum5 = sum5 + rowc05t;
			int rowc06t = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lbtnClosedbyCit_" + j + "']")
							.getText());
			sum6 = sum6 + rowc06t;
			int rowc07t = Integer.parseInt(findElementByXpath(
					"//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lbtnFORWARD_TO_ANOTHER_WARD_" + j + "']")
					.getText());
			sum7 = sum7 + rowc07t;
			int rowc08t = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lbtnFunds_" + j + "']")
							.getText());
			sum8 = sum8 + rowc08t;
			int rowc09t = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lbtnNonGHMC_" + j + "']")
							.getText());
			sum9 = sum9 + rowc09t;
			int rowc10t = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lbtnOutofGHMC_" + j + "']")
							.getText());
			sum10 = sum10 + rowc10t;
			int rowc11t = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lbtnOtherStatus_" + j + "']")
							.getText());
			sum11 = sum11 + rowc11t;

		}

		checkColumnSum(test1, sum1, total_reg_sum, "Total_registered");
		checkColumnSum(test1, sum2, total_pen_sum, "Total_pending");
		checkColumnSum(test1, sum3, open_count, "Open");
		checkColumnSum(test1, sum4, under_process, "Under_process");
		checkColumnSum(test1, sum5, res_by_off_sum, "Resolved_by_officer");
		checkColumnSum(test1, sum6, clo_by_cit_sum, "Closed_by_citizen");
		checkColumnSum(test1, sum7, rec_from_other_ward, "Received_from_other_ward");
		checkColumnSum(test1, sum8, sub_for_sanction_sum, "Submitted_for_sanction");
		checkColumnSum(test1, sum9, non_ghmc, "Non_GHMC");
		checkColumnSum(test1, sum10, out_of_ghmc, "Out_of_GHMC");
		checkColumnSum(test1, sum11, oth_status_sum, "Other_status");

		extent.flush();

	}

	// Row fields validation of grievance subcategory
	public void row_validation() throws Exception {

		ExtentTest testcase = extent.createTest("TEST CASE 2 - Rows Validation");

		onebutton.click();

		String text1 = text.getText();
		String parts[] = text1.split("of");
		String entriesNumber = parts[1].trim().split(" ")[0]; // Extract the number part
		int totalEntries = Integer.parseInt(entriesNumber.trim());
		System.out.println(totalEntries);
		int i = 0;

		for (int j = 0; j < totalEntries; j++) {// Total registered values

			ExtentTest test = testcase.createNode("Test case for row " + (j + 1));

			String row_val = "//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lblTotalReg_" + j + "']";
			WebElement row1 = findElementByXpath(row_val);
			int rowtext1 = Integer.parseInt(row1.getText());

			String catElement = "//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lblSubCategory_" + j + "']";
			WebElement subCatEle = findElementByXpath(catElement);
			String SubCatElement = subCatEle.getText();

			int rowc2t = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lblTotalPending_" + j + "']")
							.getText());
			int rowc3t = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lbtnOpen_" + j + "']")
							.getText());
			int rowc4t = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lbtnUNDERPROCESS_" + j + "']")
							.getText());
			int rowc5t = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lbtnRes_" + j + "']")
							.getText());
			int rowc6t = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lbtnClosedbyCit_" + j + "']")
							.getText());
			int rowc7t = Integer.parseInt(findElementByXpath(
					"//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lbtnFORWARD_TO_ANOTHER_WARD_" + j + "']")
					.getText());
			int rowc8t = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lbtnFunds_" + j + "']")
							.getText());
			int rowc9t = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lbtnNonGHMC_" + j + "']")
							.getText());
			int rowc10t = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lbtnOutofGHMC_" + j + "']")
							.getText());
			int rowc11t = Integer.parseInt(
					findElementByXpath("//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lbtnOtherStatus_" + j + "']")
							.getText());

			int sum = rowc3t + rowc4t + rowc5t + rowc6t + rowc7t + rowc8t + rowc9t + rowc10t + rowc11t;
			int total_pending_count = rowc3t + rowc4t + rowc7t;

			if (rowtext1 == sum) {
				test.pass("Test case passed at subcategory " + (SubCatElement) + " as expected sum " + sum
						+ " matches the total registered number");
			} else {
				test.fail("Test case failed at subcategory " + (SubCatElement) + " as expected sum is " + rowtext1
						+ " but we got the sum " + sum);
			}

			if (rowc2t == total_pending_count) {
				test.pass("Test case passed at subcategory " + (SubCatElement) + " as expected total pending value "
						+ rowc2t + " matches the sum of open, under process and received from another ward "
						+ total_pending_count);
			} else {
				test.fail("Test case failed at subcategory " + (SubCatElement) + " as expected total pending value "
						+ rowc2t + " did not match the sum of open, under process and received from another ward "
						+ total_pending_count);
			}

			row1.click();
			Thread.sleep(1000);

			String info = "//*[@id='ContentPlaceHolder1_GrdTotal_info']";
			String p = pageSource();

			if (p.contains("No Records Found")) {

				Thread.sleep(3000);
				if (i == 0) {
					back.click();
					Thread.sleep(1000);
				} else {
					back.click();
					Thread.sleep(1000);
					for (int x = 0; x < i; x++) {
						nxtpage.click();
						Thread.sleep(1000);
					}
				}
				if ((j % 10) == 9) {
					nxtpage.click();
					i++;

				}

			} else {

				WebElement showingText = findElementByXpath(info);
				String text_2 = showingText.getText();
				String parts_1[] = text_2.split("of");
				String entriesNumber_1 = parts_1[1].trim().split(" ")[0];
				entriesNumber_1 = entriesNumber_1.replace(",", "");
				int number = Integer.parseInt(entriesNumber_1.trim());

				if (rowtext1 == number) {
					test.pass("Test passed for " + (j + 1) + " row"
							+ " as total registered entries matched the sub entries");
				} else {
					test.fail("Test failed for " + (j + 1) + " row"
							+ " as total registered entries did not matched the sub entries");
				}

				if (i == 0) {
					back.click();
					Thread.sleep(1000);
				} else {
					back.click();
					Thread.sleep(1000);
					for (int x = 0; x < i; x++) {
						nxtpage.click();
						Thread.sleep(1000);
					}
				}
				if ((j % 10) == 9) {
					nxtpage.click();
					i++;

				}

			}

		} // for loop ends here

		extent.flush();
	}

	public void logOut() throws Exception {

		home.click();
		moveToElement(img);
		Thread.sleep(8000);
		clickUsingJS(img);
		Thread.sleep(2000);
		clickUsingJS(img);
		Thread.sleep(2000);
		clickUsingJS(logout);
		Thread.sleep(2000);

	}

	// Different zones login
	public void zonal_reports() throws Exception {

		extent.attachReporter(htmlReporter);
		ExtentTest test2 = extent.createTest("TEST CASE 3 - Validating zonal logins values");
		ExtentTest test3 = extent.createTest("TEST CASE 4 - Total registered count validation in every zone");

		String str = "//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails']/tfoot/tr/td[3]";
		WebElement t = findElementByXpath(str);
		String total_reg_admin = t.getText().replace(",", "");
		int admin_total_reg = Integer.parseInt(total_reg_admin);

		int sum = 0;

		for (int i = 1; i < 7; i++) {

			subcat_element.click();
			date_click();
			if (zone.isDisplayed()) {
				zone.click();
				Select dropdown = new Select(zone);
				dropdown.selectByIndex(i);
				submit.click();
			} else {
				submit.click();
			}

			processElements(0);

			logOut();

			String z = readExcel("zones", String.valueOf(i), "Zone");
			String p = readExcel("zones", String.valueOf(i), "pwd");
			username.sendKeys(z);
			pwd.sendKeys(p);

			captcha_inp();
			login.click();
			subcat_element.click();
			date_click();
			submit.click();

			processElements(1);

			sum = sum + total1_reg_sum;

			logOut();

			selecting_details();

			Thread.sleep(2000);

			if (total_reg_sum == total1_reg_sum && total_pen_sum == total1_pen_sum && open_count == open1_count
					&& under_process == under1_process && res_by_off_sum == res1_by_off_sum
					&& clo_by_cit_sum == clo1_by_cit_sum && rec_from_other_ward == rec1_from_other_ward
					&& sub_for_sanction_sum == sub1_for_sanction_sum && non_ghmc == non1_ghmc
					&& out_of_ghmc == out1_of_ghmc && oth_status_sum == oth1_status_sum) {

				test2.pass("Values of every column in " + z
						+ " zone is matched with the values when respective zone is selected in admin login");

			} else {
				test2.fail("Values of every column in " + z
						+ " zone did not match with the values when respective zone is selected on admin login");
			}

		}

		if (sum == admin_total_reg) {
			test3.pass("Sum of total registered value in every zonal login " + sum
					+ " is equal to the total registered value in admin login " + admin_total_reg);
		} else {
			test3.fail("Sum of total registered value in every zonal login " + sum
					+ " is not equal to the total registered value in admin login " + admin_total_reg);
		}
		extent.flush();

	}

	public void source_reports() throws Exception {

//		extent.attachReporter(htmlReporter);
//		ExtentTest test2 = extent.createTest("TEST CASE 3 - Validating source logins values");
		
		int sum = 0;
		
		subcat_element.click();
		date_click();
		submit.click();
		
		String str="//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails']/tfoot/tr/td[3]";
		WebElement total_reg =findElementByXpath(str);
		int total_r = Integer.parseInt(total_reg.getText().replace(",", ""));
		System.out.println("Total count for admin login is " + total_r);

		for (int i = 1; i < 14; i++) {

			subcat_element.click();
			date_click();

			source.click();
			Select source_dropdown = new Select(source);
			source_dropdown.selectByIndex(i);
			submit.click();
			Thread.sleep(5000);
			
			try{
				WebDriverWait wait;    
				wait = new WebDriverWait(getDriver(), 20);
				wait.until(ExpectedConditions.alertIsPresent());
				getDriver().switchTo().alert();
				acceptAlert();  
			
			}
			catch (Exception e) {
				String s="//*[@id='ContentPlaceHolder1_grdSubCatWiseDetails']/tfoot/tr/td[3]";
				WebElement total =findElementByXpath(s);
				int t = Integer.parseInt(total.getText().replace(",", ""));
				System.out.println("Total count for source is " + t);
				System.out.println("....");
				sum = sum + t;
				System.out.println(sum);
				
			}
			
			
		}
		
		if(total_r==sum) {
			System.out.println("Total count in admin login "+total_r+" is matched with sources sum "+sum);
		}
		else {
			System.out.println("Total count in admin login "+total_r+" is not matched with sources sum "+sum);
		}

	}
	
	public void csz_reports() throws Exception{
		
		extent.attachReporter(htmlReporter);
		ExtentTest test = extent.createTest("TEST CASE - Category and Subcategory values");
		
		String catText;
		int CategoryCount;
		
		cat_element.click();
		date_click();
		submit.click();
		
		String text1 = text.getText();
		String parts[] = text1.split("of");
		String entriesNumber = parts[1].trim().split(" ")[0]; // Extract the number part
		int totalEntries = Integer.parseInt(entriesNumber.trim());
		System.out.println("Total entries are: "+totalEntries);
		
		for(int k=0; k<totalEntries;k++) {
			
			cat_element.click();
			date_click();
			submit.click();
			Thread.sleep(1000);
			
			if (k>9) {
				nxtpage.click();
			}
			if(k>19) {
				nxtpage.click();
				nxtpage.click();
			}
			
	
			String row_val = "//*[@id='ContentPlaceHolder1_grdGrvCat_lblTotalReg_" + k + "']";
			WebElement total_reg = findElementByXpath(row_val);
			int totalRegText = Integer.parseInt(total_reg.getText());
			System.out.println("*******************************");
			System.out.println("Total registered value of row " + (k+1)+ " is "+totalRegText);
			System.out.println("*******************************");
			
			total_reg.click();
			
			String info = "//*[@id='ContentPlaceHolder1_GrdTotal_info']";
			WebElement sub_entry = findElementByXpath(info);
			String text = sub_entry.getText();
			String p[] = text.split("of");
			String entriesNumber_1 = p[1].trim().split(" ")[0];
			entriesNumber_1 = entriesNumber_1.replace(",", "");
			int number = Integer.parseInt(entriesNumber_1.trim());
			System.out.println("Sub entries count of row "+(k+1)+" is "+number);
			System.out.println("--------------------");
			
			Map<String, Integer> grCatCount = new HashMap<>();
			
			for(int c=0;c<number;c++) {
				
				if (c > 0 && c % 10 == 0) {
					nxtpage.click();
				}
				
				String greivance = "//span[@id='ContentPlaceHolder1_GrdTotal_lblCategory_"+c+"']";
				WebElement gr_cat = findElementByXpath(greivance);
				String grCatText = gr_cat.getText();
				System.out.println("Text at subentry row "+(c+1)+" is "+grCatText);
				
				grCatCount.put(grCatText, grCatCount.getOrDefault(grCatText, 0) + 1);
				
			}
			
			System.out.println("Category Counts: ");
			
			for (Map.Entry<String, Integer> entry : grCatCount.entrySet()) {
				catText = entry.getKey();
				CategoryCount = entry.getValue();
			    System.out.println(catText + ": " + CategoryCount);
			    	if(totalRegText==1) {
			    		back.click();
			    	}
					subcat_element.click();
					date_click();
					submit.click();
					search.sendKeys(catText);
					search.sendKeys(Keys.ENTER);
					Thread.sleep(2000);
					
					String num = "//td[@class='sorting_1']";
					WebElement sort = findElementByXpath(num);
					int grid_val = Integer.parseInt(sort.getText());
					
					String ele = "//span[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lblSubCategory_"+(grid_val-1)+"']";
					WebElement element = findElementByXpath(ele);
					
					if(catText.equals(element.getText())) {
						
						String total = "//a[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lblTotalReg_"+(grid_val-1)+"']";
						WebElement total_count = findElementByXpath(total);
						Integer integ = Integer.parseInt(total_count.getText().replace(",", ""));
						
						if(integ==CategoryCount) {
							System.out.println("Total count of category "+catText+" : "+CategoryCount+" is matched with subcategory total count "+integ);
							test.pass("Total count of category "+catText+" : "+CategoryCount+" is matched with subcategory total count "+integ);
						}
					
						else {
							System.out.println("Total count of category "+catText+" : "+CategoryCount+" is not matched with subcategory total count "+integ);
							test.fail("Total count of category "+catText+" : "+CategoryCount+" is not matched with subcategory total count "+integ);
						}
						
					}
					
					else {
						
						String text_ele = "//span[text()='"+catText+"']";
						WebElement elem = findElementByXpath(text_ele);
						String element_text =elem.getText();
						
						String idAttribute = elem.getAttribute("id");
						String[] num_part = idAttribute.split("_");
						String numberPart = num_part[num_part.length - 1];
						int num_p = Integer.parseInt(numberPart);
						
						String t = "//a[@id='ContentPlaceHolder1_grdSubCatWiseDetails_lblTotalReg_"+num_p+"']";
						WebElement e = findElementByXpath(t);
						int tot = Integer.parseInt(e.getText());
						
						if(tot==CategoryCount) {
							System.out.println("Total count of category "+element_text+" : "+CategoryCount+" is matched with subcategory total count "+tot);
							test.pass("Total count of category "+element_text+" : "+CategoryCount+" is matched with subcategory total count "+tot);
						}
					
						else {
							System.out.println("Total count of category "+element_text+" : "+CategoryCount+" is not matched with subcategory total count "+tot);
							test.fail("Total count of category "+element_text+" : "+CategoryCount+" is not matched with subcategory total count "+tot);
						}
									
					}
					
					Thread.sleep(1000);
			}
			
	    }
		extent.flush();
	}
	
	
}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

