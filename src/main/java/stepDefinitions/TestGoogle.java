package stepDefinitions;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.junit.runner.RunWith;

import com.vimalselvam.cucumber.listener.Reporter;

import commonMethods.TestReusables;
import commonMethods.PageReusable;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageObjects.TestGooglePO;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)

public class TestGoogle {

	PageReusable rm = new PageReusable();
	TestGooglePO tgp = new TestGooglePO();
	TestReusables apr = new TestReusables();
	
@Given("^I launch the application$")
public void i_launch_the_appication() throws Throwable {
   rm.navigate();
}

@When("^I search$")
public void i_search() throws Throwable {
	tgp.searchText(rm.readExcel("TestData", "Scenario2", "Search"));
    
}

@When("^I search \"([^\"]*)\"$")
public void i_search(String keyword) throws Throwable {
	tgp.searchText(keyword);
    
}

@Then("^Search result displayed$")
public void search_result_displayed() throws Throwable {
	/*System.out.println("Then Statement");
	System.out.println("Printing Oracle result set");
   ResultSet rs = apr.getOracleResultset("trade", "trade", "Select count(*) from BANK_MAST");
   while(rs.next())
   System.out.println(rs.getString(1));
   apr.closeConnection();
   System.out.println("Printing Postgre result");
   rs = apr.getOracleResultset("biets_services", "biets_services", "select count(*) from boi_barcode_details_others");
   while(rs.next())
   System.out.println(rs.getString(1));*/
	
	tgp.searchresult();
}


@Given("^I have to test Data table$")
public void i_have_to_test_Data_table() throws Throwable {
    System.out.println("I am in data table given Step");
}

@When("^I am passing table data$")
public void i_am_passing_table_data(DataTable table) throws Throwable {
    System.out.println("I Am Reading Data Table Values");
    List<List<String>> data = table.raw();
    for(int i=0; i<data.size(); i++) { 
		System.out.println(data.get(i).get(0) + "=" + data.get(i).get(1)); 
	}
    
}

@Then("^I can access all data values$")
public void i_can_access_all_data_values() throws Throwable {
    System.out.println("I Accessed all table data");
}


@When("^I am passing table data values$")
public void i_am_passing_table_data_values(DataTable table) throws Throwable {
    System.out.println("I Am Reading Data Table Values");
    List<Map<String, String>> data = table.asMaps(String.class, String.class);
    for(int i=0; i<data.size(); i++) { 
		System.out.println(data.get(i).get("First Name")); 
		System.out.println(data.get(i).get("Last Name"));
		System.out.println(data.get(i).get("e-Mail"));
	}
    
}

}
