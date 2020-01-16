package test;

import java.util.List;
/**
 *  
 * List of methods
 * 
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import test.ExcelDataPojo;

public interface FusionInf {

	void process(WebDriver driver, List<String> cellValue);

	WebElement getElement(String pathType, String pathValue);

	String getDriverFullPath(String browser);

	String performAction(ExcelDataPojo excelData);

	void waitTillElementLoad(ExcelDataPojo excelData);

	String verify(ExcelDataPojo excelData, String text);

	String getimage(ExcelDataPojo excelData, String imageValue);

	String pagination(ExcelDataPojo excelData);

	String dragdrop(ExcelDataPojo excelData);

	String movefolder(ExcelDataPojo excelData);

	void refreshbrowser(WebDriver driver);

	void ScreenShotCapt(WebDriver driver);

	ExcelDataPojo getValues(List<String> cellValue);

	String ResultStatus(boolean flag);

	String resize(ExcelDataPojo excelData);
	
	String phonegroupresize(ExcelDataPojo excelData);

	String keydownevent(ExcelDataPojo excelData);

	String sendkeysevent(ExcelDataPojo excelData);

	String Bargemonitorwhisper(ExcelDataPojo excelData);

	String clickToDial(ExcelDataPojo excelData);

	String unprovisioned(ExcelDataPojo excelData);

	String Login(ExcelDataPojo excelData) throws InterruptedException;

	String getcolor(ExcelDataPojo excelData);

	String checkmessagecount(ExcelDataPojo excelData);

}
