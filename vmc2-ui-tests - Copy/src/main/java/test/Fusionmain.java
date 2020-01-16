package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Fusionmain implements ElementByXPathIdInf {
	static WebDriver driver = null; 					// Driver object to run specific browser.
	static WebDriverWait wait = null; 					// Browser wait time.
	static List<ArrayList<String>> excelData = null; 	// List to automate test cases from excel sheet.
	static String SITEURL; 								// Site End point.
	
	/**
	 * @param args
	 * @throws InterruptedException Main method to run automation across all
	 *                              browsers.
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		PropertyConfigurator.configure(Propertiesfile.getFile("LOGPROP"));
		System.out.println(System.getProperty("os.name"));
		try {
			FusionInf Fusionmain = new FusionImpl();
			ReadExcelSheet readExcelsheet = new ReadExcelSheet();
			List<List<ArrayList<String>>> excelappend = new ArrayList<>();
			List<ArrayList<String>> URL = readExcelsheet.getExcelData("VMC2Info");
			SITEURL = URL.get(0).get(0);
			List<ArrayList<String>> scenarioList = readExcelsheet.getExcelData("TestScope");
			System.out.println(scenarioList);
			List<String> arrList = new ArrayList<String>();
			scenarioList.forEach(list -> arrList.addAll(list));
			for (String sheetName : arrList) {
				excelData = readExcelsheet.getExcelData(sheetName);
				System.out.println(sheetName);
				if ("firefox".equalsIgnoreCase(excelData.get(1).get(0))) {
					setDriverConfiguration("firefox");
				} else if ("Chrome".equalsIgnoreCase(excelData.get(1).get(0))) {
					setDriverConfiguration("Chrome");
				} else if ("IE".equalsIgnoreCase(excelData.get(1).get(0))) {
					setDriverConfiguration("IE");
				}
				driver = (WebDriver) Class.forName(Fusionmain.getDriverFullPath(excelData.get(1).get(0))).newInstance();
				driver.get(SITEURL);
				driver.manage().deleteAllCookies();
				driver.manage().window().maximize();
				excelData.stream().forEach(cellValue -> {
					Fusionmain.process(driver, cellValue);
				});
				driver.close();
				Thread.sleep(6500);
				excelappend.add(excelData);
			}
			generateReport(excelappend);
		} catch (UnhandledAlertException ee) {
			try {
				Alert alert = driver.switchTo().alert();
				alert.accept();
			} catch (NoAlertPresentException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates report based on excelSheet.
	 * 
	 * @param excelappend
	 */
	private static void generateReport(List<List<ArrayList<String>>> excelappend) {
		List<ArrayList<String>> combineSheets = new ArrayList<>();
		for (int i = 0; i < excelappend.size(); i++) {
			excelData = excelappend.get(i);
			excelData.remove(0);
			combineSheets.addAll(excelData);
		}
		ReportGenerator.writeReport(combineSheets);
		System.out.println("output file written");
	}

	/**
	 * Sets driver properties based on browser. Initialize the path where driver is
	 * present.
	 *
	 * @param browserType
	 * @throws IOException
	 */
	private static void setDriverConfiguration(String browserType) throws IOException {
		switch (browserType) {
		case "firefox": {
			
			System.setProperty("webdriver.gecko.driver", getDriver("GECKO"));
			break;
		}
		case "Chrome": {
			System.setProperty("webdriver.chrome.driver", getDriver("CHROME"));
			break;
		}
		case "IE": {
			System.setProperty("webdriver.ie.driver",  getDriver("IE"));
			break;
		}
		default:
			break;
		}
	}
	/**
	 * Execution based on OperatingSystem
	 *
	 * @param browserType
	 * @throws IOException
	 */
	
	private static String getDriver(String driverName) throws IOException {
		String osName = System.getProperty("os.name");
		switch(driverName) {
		case "GECKO":
			if(osName.contains("Windows")) {
				return Propertiesfile.getFilePath("GECKODRIVER","drivers");
			}else if(osName.contains("Linux")) {
				return Propertiesfile.getFilePath("LINUX_GECKODRIVER", "drivers");
			}
		case "CHROME":
			if(osName.contains("Windows")) {
				return Propertiesfile.getFilePath("CHROMEDRIVER","drivers");
			}else if(osName.contains("Linux")) {
				return Propertiesfile.getFilePath("LINUX_CHROMEDRIVER", "drivers");
			}
		case "IE":
			if(osName.contains("Windows")) {
				return Propertiesfile.getFilePath("IEDRIVER","drivers");
			}	
			
		}
		return null;
	}
	
}
