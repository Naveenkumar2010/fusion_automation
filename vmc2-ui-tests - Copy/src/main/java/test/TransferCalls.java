package test;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TransferCalls {
	WebDriver driver = null; 	// Web driver object.

	/**
	 * Utility method used for call transfer.
	 * 
	 * @param excelData
	 * @return
	 */
	public String callTransfer(ExcelDataPojo excelData) {
		this.driver = Fusionmain.driver;
		String status = "FAIL";
		List<String> data = Arrays.asList(excelData.getActionData().split(","));
		JavascriptExecutor jsDnDPG = (JavascriptExecutor) driver;
		WebElement ElementRef = getElement(excelData.getxPathIdType(), excelData.getXpathIdValue());
		List<WebElement> elements = ElementRef.findElements(By.className("call-no-call"));
		if (elements.isEmpty()) {
			String jsPGLibraryString = "!function(t){t.fn.simulateDragDrop=function(a){return this.each(function(){new t.simulateDragDrop(this,a)})},t.simulateDragDrop=function(t,a){this.options=a,this.simulateEvent(t,a)},t.extend(t.simulateDragDrop.prototype,{simulateEvent:function(a,e){var n=\"dragstart\",r=this.createEvent(n);this.dispatchEvent(a,n,r),n=\"drop\";var i=this.createEvent(n,{});i.dataTransfer=r.dataTransfer,this.dispatchEvent(t(e.dropTarget)[0],n,i),n=\"dragend\";var s=this.createEvent(n,{});s.dataTransfer=r.dataTransfer,this.dispatchEvent(a,n,s)},createEvent:function(t){var a=document.createEvent(\"CustomEvent\");return a.initCustomEvent(t,!0,!0,null),a.dataTransfer={data:{},setData:function(t,a){this.data[t]=a},getData:function(t){return this.data[t]}},a},dispatchEvent:function(t,a,e){t.dispatchEvent?t.dispatchEvent(e):t.fireEvent&&t.fireEvent(\"on\"+a,e)}})}(jQuery);";
			System.out.println(
					"CallTransfer" + "$(" + data.get(0) + ").simulateDragDrop({ dropTarget: " + data.get(1) + "});");
			jsDnDPG.executeScript(jsPGLibraryString + "$(" + data.get(0) + ").simulateDragDrop({ dropTarget: '"
					+ data.get(1) + "'});");
			status = "PASS";
		} else {
			System.out.println("No call is available to transfer between" + excelData.getAction());
		}

		return status;
	}

	/**
	 * Returns Web element based on path type and value.
	 * 
	 * @param pathType
	 * @param pathValue
	 * @return
	 */
	public WebElement getElement(String pathType, String pathValue) {
		WebElement element = null;
		switch (pathType.toUpperCase()) {
		case "XPATH":
			element = ElementByXPathIdInf.getByXPath(driver, (String) pathValue);
			break;
		case "ID":
			element = ElementByXPathIdInf.getByID(driver, (String) pathValue);
			break;
		case "CLASSNAME":
			element = ElementByXPathIdInf.getByClassName(driver, (String) pathValue);
			break;
		default:
			break;
		}
		return element;

	}
}
