package commonMethods;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.testng.Assert;
import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class TestReusables {

	 Connection con;

	/* ###########################################################################
	 * Description: API Methods - This method used to send a post request
	 * @apiName: Name of the API, @cookie: cookie name, @payload: payload parameters
	 * @return: string
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	public String sendPostRequest(String apiUrl, String apiName, String cookie, String payload) {
		StringBuffer jsonString = new StringBuffer();

		try {
			URL url = new URL(
					apiUrl + apiName);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Cookie", cookie);
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			writer.write(payload);
			writer.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				jsonString.append(line);
			}
			br.close();
			connection.disconnect();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return jsonString.toString();
	}

	
	/* ###########################################################################
	 * Description: API Methods - This method used to get the response
	 * @serviceUrl: API service URL
	 * @return stringbuffer
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	
	@SuppressWarnings("null")
	public StringBuffer getServiceResponse(String serviceUrl) throws Exception {
		String output = null;
		StringBuffer outputResponse = null;
		try {
			Thread.sleep(5000);
			URL url = new URL(serviceUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("source-appl-id", "6");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Accept-Resolution", "thumb, medium, high");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			} else {

			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			// String output;
			
			PrintWriter out = new PrintWriter("Response/" + "response.txt");
			while ((output = br.readLine()) != null) {
				
				outputResponse.append(output);
			}

			conn.disconnect();
		} catch (Exception ex) {
		}

		return outputResponse;

	}
	
	
	
	/* ###########################################################################
	 * Description: API Methods - This method used to get authentication token
	 * @return string (Authentication Token)
	 * Configure baseURI, X-client, Username, Password and post Auth string of application
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	
	public String getAuthToken() {
		String auth_Token ="";
		try{
		RestAssured.baseURI="https://cgg.gov.in/api/v1.0/";  //Example url provided here. you have to add your API base URL
		RequestSpecification httpRequest = RestAssured.given();
	        httpRequest.header("X-Client", "testuser")  // Example X-client parameter provided here. you have to add you app parameter
	        .header("Content-Type", "application/json");
	        JsonObject loginCredentials = new JsonObject();
	        loginCredentials.addProperty("client", "testuser");  // Example user name provided. Add actual user name
	        loginCredentials.addProperty("pwd", "12345678");	// Example password added. Add actual password
	        httpRequest.body(loginCredentials.toString());
	        Response response = httpRequest.post("/auth/login"); //Example get string. Add your apps url string
	        JsonPath jsonPathEvaluator = response.jsonPath();
	        auth_Token= jsonPathEvaluator.get("auth_token").toString();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return auth_Token;
	}
	
	
	/* ###########################################################################
	 * Description: API Methods - This method used to get authentication token
	 * @return string (Authentication Token)
	 * @ baseURI: API Base URL, @X-client: X-Client parameter value, 
	 * @Username: Authentication Username,
	 * @Password: Authentication Password, @postString: Post String of API Url
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	
	public String getAuthToken(String url, String xclient, String userName, String password, String postString) {
		String auth_Token="";
		try{
		RestAssured.baseURI=url; 
		RequestSpecification httpRequest = RestAssured.given();
	        httpRequest.header("X-Client", xclient)
	        .header("Content-Type", "application/json");
	        JsonObject loginCredentials = new JsonObject();
	        loginCredentials.addProperty("client", userName);
	        loginCredentials.addProperty("pwd", password);
	        httpRequest.body(loginCredentials.toString());
	        Response response = httpRequest.post(postString);
	        JsonPath jsonPathEvaluator = response.jsonPath();
	        auth_Token= jsonPathEvaluator.get("auth_token").toString();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return auth_Token;
	}
	
	
	
	
	/* ###########################################################################
	 * Description: API Methods - This method used to get API response
	 * @return string (JSON Response as String)
	 * @ authtoken: Authentication token
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	public String getResponse(String authToken) {
		String ResString="";
		try{
		RestAssured.baseURI="https://cgg.gov.in/api/v1.0/";  //Example url provided here. you have to add your API base URL
		RequestSpecification httpRequest = RestAssured.given();
	        httpRequest.header("X-Client", "testuser") // Example X-client parameter provided here. you have to add you app parameter
	        .header("Authorization","Bearer "+authToken+"")
	        .header("Content-Type", "application/json");
	    Response response = httpRequest.get("/ticket/id="); //Example get string. Add your apps url string
	    ResString= response.asString();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
        return ResString;
        
        
	}
	
	/* ###########################################################################
	 * Description: API Methods - This method used to get API response
	 * @return string (JSON Response)
	 * @ baseURL: API Base URL, @X-client: X-Client parameter value, 
	 * @authToken: Authentication token value
	 * @getAPIURL: URL string
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	public String getResponse(String baseURL, String authToken,String xclientparam, String getAPIURL) {
		String ResString="";
		try{
		RestAssured.baseURI=baseURL;  
		RequestSpecification httpRequest = RestAssured.given();
	        httpRequest.header("X-Client", xclientparam) 
	        .header("Authorization","Bearer "+authToken+"")
	        .header("Content-Type", "application/json");
	    Response response = httpRequest.get(getAPIURL); 
	    ResString= response.asString();
		}
		catch(Exception e){
			e.printStackTrace();
		}
        return ResString;
        
	}
	
	
	/* ###########################################################################
	 * Description: API Methods - This method used to get specific JSON node value
	 * @return string (node value)
	 * @ baseURL: API Base URL, @X-client: X-Client parameter value, 
	 * @authToken: Authentication token value
	 * @URL: URL string, @nodePath: JSON hierarchy path
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	public String getNodevalue(String baseURL, String xclient, String authToken, String url, String nodePath) {
		String nodeValue="";
		try{
		RestAssured.baseURI=baseURL; 
		RequestSpecification httpRequest = RestAssured.given();
	        httpRequest.header("X-Client", xclient)
	        .header("Authorization","Bearer "+authToken+"")
	        .header("Content-Type", "application/json");
	    Response response = httpRequest.get(url);
	    JsonPath jsonPathEvaluator = response.jsonPath();
	    nodeValue=jsonPathEvaluator.get(nodePath).toString();
	      //e.g. nodepath : result.release.hits._source.additional_metadata.websitetemplate
	   	   
		}
		catch(Exception e){
			e.printStackTrace();
		}
    
	    return nodeValue;
        
	}
	
	
	/* ###########################################################################
	 * Description: DataBase Methods - This method used to get result set of an oracle database table
	 * @return result set
	 * @ userName: Database Username, @Password: Database Password, @query: Database query
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	public ResultSet getOracleResultset(String userName, String passWord, String query) throws Exception{
		
		ResultSet rs = null;
		try{  
			 
			Class.forName("oracle.jdbc.driver.OracleDriver");  
			// Configure connection URL as per your project database connection
			//format: jdbc:oracle:thin:@<host name>:<port>:<Database Name>
			/*Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",userName,passWord); */ 
			con=DriverManager.getConnection("jdbc:oracle:thin:@10.2.28.17:1521:ghmctest",userName,passWord); 
			Statement stmt=con.createStatement();  
			rs=stmt.executeQuery(query);  
			/*
			 * To get desired result we can use below code
			 * while(rs.next())  
			 * System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)); 
		    */
					  
			}
		catch(Exception e){ 
			System.out.println(e);
			e.printStackTrace();
			}  
			  
			return rs;  
	}
	
	
	
	/* ###########################################################################
	 * Description: DataBase Methods - This method used to get result set of an SQL database table
	 * @return result set
	 * @ userName: Database Username, @Password: Database Password, @query: Database query
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	public ResultSet getSQLResultset(String userName, String passWord, String query) throws Exception{
		
		ResultSet rs = null;
		try{  
			 
			Class.forName("com.mysql.jdbc.Driver");  
			// Configure connection URL as per your project database connection
			//format: jdbc:mysql://<host name>:<port>/<Database Name>
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/cggtest",userName,passWord);  
			Statement stmt=con.createStatement();  
			rs=stmt.executeQuery(query);  
			/*
			 * To get desired result we can use below code
			 * while(rs.next())  
			 * System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)); 
		    */
		  
			}
		catch(Exception e){ 
			System.out.println(e);
			e.printStackTrace();
			}  
			  
			return rs;  
	}
	
	/* ###########################################################################
	 * Description: DataBase Methods - This method used to get result set of an Postgre database table
	 * @return result set
	 * @ userName: Database Username, @Password: Database Password, @query: Database query
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	public ResultSet getPostgreResultset(String userName, String passWord, String query) throws Exception{
		
		ResultSet rs = null;
		try{  
			Class.forName("org.postgresql.driver");
			// Configure connection URL as per your project database connection
			//format: jdbc:postgresql://<host name>:<port>/<Database Name> 
//			Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/bietest",userName,passWord);  
			
			Connection con=DriverManager.getConnection("jdbc:postgresql://10.2.28.36:6432/biets_services",userName,passWord);
			Statement stmt=con.createStatement();  
			rs=stmt.executeQuery(query);  
			  
			}
		catch(Exception e){ 
			System.out.println(e);
			e.printStackTrace();
			}  
			  
			return rs;  
	}
	
	
	/* ###########################################################################
	 * Description: DataBase Methods - This method used to close the database connection
	 * Created : 17-04-2020
	 * ###########################################################################
	*/
	
	public void closeConnection(){
		try{
			con.close();
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail("Failed in closing database connection");
		}
	}
}
