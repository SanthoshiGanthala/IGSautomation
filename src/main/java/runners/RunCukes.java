package runners;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.testng.annotations.BeforeClass;

import com.vimalselvam.cucumber.listener.ExtentProperties;
import com.vimalselvam.cucumber.listener.Reporter;

import commonMethods.PageReusable;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@SuppressWarnings("unchecked")
@RunWith(Cucumber.class)
@CucumberOptions(
//		features = "F:\\Automation\\Framework\\src\\main\\java\\features",
		glue = "stepDefinitions",
//		tags = "@tag1",
		strict = true,
		/*dryRun = true,*/
		monochrome = true,
		plugin={"com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:"}
		)

public class RunCukes extends AbstractTestNGCucumberTests {
	
	public static String resultFolder;
	
	@BeforeClass
	public static void setup(){
		ExtentProperties extentProperties = ExtentProperties.INSTANCE;
		extentProperties.setReportPath(resultFolder + "/TestReport.html");
		System.clearProperty("hudson.model.DirectoryBrowserSupport.CSP");
		System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", "sandbox allow-scripts; default-src 'self'; script-src * 'unsafe-eval'; img-src *; style-src * 'unsafe-inline'; font-src *");
	}
	
	
	
	@AfterClass
	public static void teardown()
	{
	String aDir = System.getProperty("user.dir");
	System.out.println(aDir);
	Reporter.loadXMLConfig(new File(aDir+"//extent-config.xml"));
	//Reporter.setSystemInfo("Test User", System.getProperty("user.name"));
	Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
	Reporter.setSystemInfo("Application Name", "Google App ");
	Reporter.setSystemInfo("Operating System Type", System.getProperty("os.name").toString());
	Reporter.setSystemInfo("Environment", "Test Environment");
	Reporter.setTestRunnerOutput("Test Execution Report");
	}
	
	public static String[] featureList;
	static{
		
		try {
			final CucumberOptions old = (CucumberOptions) RunCukes.class.getAnnotation(CucumberOptions.class);

			Object handler = Proxy.getInvocationHandler(old);

			Field field = null;
			try {
				field = handler.getClass().getDeclaredField("memberValues");

			} catch (Exception e) {
				e.printStackTrace();
			}
			field.setAccessible(true);
			Map<String, String[]> memberValues;
			try {
				memberValues = (Map<String, String[]>) field.get(handler);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new IllegalStateException(e);
			}

			SimpleDateFormat sdfDateReport = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			Date now = new Date();

			resultFolder = "ReportGenerator/" + "BDD_HtmlReport_" + sdfDateReport.format(now);
			File file = new File(resultFolder);
			file.mkdirs();

			Map systemInfo = new HashMap();
			systemInfo.put("Cucumber version", "v1.2.5");

			List<String> featureLists = new ArrayList<String>();
			String tagsLists = "";
			try {
				featureLists = PageReusable.getFeatures();
				tagsLists = PageReusable.getTags();

			} catch (Exception e1) {
				// TODO Auto-generate catch block
				e1.printStackTrace();
			}

			int x = featureLists.size();
			featureList = new String[x];
			for (int i = 0; i < featureLists.size(); i++) {
				featureList[i] = featureLists.get(i);
			}

			String[] tags = { tagsLists };

			memberValues.put("features", featureList);
			// memberValues.put("format", format);
			if (!tagsLists.isEmpty()) {
				memberValues.put("tags", tags);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

