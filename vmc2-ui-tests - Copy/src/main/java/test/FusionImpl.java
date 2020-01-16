package test;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class FusionImpl implements FusionInf {
	TransferCalls calltransfer = new TransferCalls(); 			 // Transfer Call object.
	public Logger Log = Logger.getLogger(Logger.class.getName());// Logger object.
	WebDriver driver = null;									 // Web driver object.
	WebElement validateElement = null; 							 // Web element object.
	static String SITEURL; 										 // Site End point.
	ReadExcelSheet readExcelsheet = new ReadExcelSheet();

	/**
	 * Process the method based on stepwise.
	 */
	@Override
	public void process(WebDriver driver, List<String> cellValue) {
		ExcelDataPojo excelData = null;
		this.driver = driver;
		excelData = getValues(cellValue);
		if (!cellValue.contains("TYPE")) {
			waitTillElementLoad(excelData);
		}
		String status = performAction(excelData);
		cellValue.add(cellValue.size() - 1, status);
	}

	/**
	 * GetElement using this method
	 */
	@Override
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
		}
		return element;

	}

	@Override
	public String getDriverFullPath(String browser) {
		return browser.isEmpty() ? BrowserDriverPath.FIREFOX.getDriver()
				: BrowserDriverPath.valueOf(browser.toUpperCase()).getDriver();
	}

	/**
	 * Performing Action based on VMC Application
	 */
	@Override
	public String performAction(ExcelDataPojo excelData) {
		String status = "FAIL";
		String compareStatus = "";

		try {
			switch (excelData.getAction().toUpperCase()) {
			case "VERIFY":
				compareStatus = value(excelData);
				break;
			case "CLICK":
				try {
					Thread.sleep(3000);
					System.out.println("click excelData:" + excelData);
					getElement(excelData.getxPathIdType(), excelData.getXpathIdValue()).click();
				} catch (Exception e) {
					compareStatus = "FAIL";
					e.printStackTrace();
					Log.info("Click is not performed");
				}
				break;
			case "LOGIN":
				compareStatus = Login(excelData);
				break;
			case "CLEAR":
				try {
					getElement(excelData.getxPathIdType(), excelData.getXpathIdValue()).clear();
					Thread.sleep(2000);
				} catch (Exception e) {
					compareStatus = "FAIL";
					Log.info("Clear is not performed");
				}
				break;
			case "KEYS":
				try {
					getElement(excelData.getxPathIdType(), excelData.getXpathIdValue())
							.sendKeys(excelData.getActionData());
					System.out.println("Keys is performed");
				} catch (Exception e) {
					compareStatus = "FAIL";
					Log.info("Keys is not performed");
				}
				break;
			case "PAGINATION":
				Thread.sleep(5000);
				pagination(excelData);
				break;
			case "DRAGDROP":
				Thread.sleep(7000);
				compareStatus = dragdrop(excelData);
				Thread.sleep(7000);
				break;
			case "MOVEFOLDER":
				Thread.sleep(2000);
				movefolder(excelData);
				Thread.sleep(5000);
				break;
			case "UNPROVISIONED":
				unprovisioned(excelData);
				Thread.sleep(10000);
				break;
			case "UPLOADAVATAR":
				try {
					getElement(excelData.getxPathIdType(), excelData.getXpathIdValue());
					Thread.sleep(3000);
					Robot r = new Robot();
					Thread.sleep(1000);
					StringSelection stringselection = new StringSelection(
							Propertiesfile.getFilePath("AVATAR", "avatars"));
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
					Thread.sleep(3000);
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_V);
					r.keyRelease(KeyEvent.VK_CONTROL);
					r.keyRelease(KeyEvent.VK_V);
					Thread.sleep(2000);
					r.keyPress(KeyEvent.VK_ENTER);
					r.keyRelease(KeyEvent.VK_ENTER);
					Thread.sleep(2000);
				}

				catch (Exception e) {
					compareStatus = "FAIL";
					e.printStackTrace();
					Log.info("Avatar is not uploaded");
				}
				break;
			case "DROPDOWN":
				Select dropdownlist = new Select(getElement(excelData.getxPathIdType(), excelData.getXpathIdValue()));
				dropdownlist.selectByValue(excelData.getActionData());
				break;
			case "GETIMAGE":
				compareStatus = getimage(excelData,
						getElement(excelData.getxPathIdType(), excelData.getXpathIdValue()).getText());
				break;
			case "GETCOLOR":
				getcolor(excelData);
				break;
			case "RESIZE":
				Thread.sleep(2000);
				resize(excelData);
				break;
			case"PHONEGROUPRESIZE":
			     Thread.sleep(2000);
				 phonegroupresize(excelData);
				 break;
			case "SCREENSHOTCAPT":
				ScreenShotCapt(driver);
				break;
			case "KEYDOWNEVENT":
				Thread.sleep(5000);
				keydownevent(excelData);
				Thread.sleep(5000);
				break;
			case "SENDKEYSEVENT":
				Thread.sleep(5000);
				sendkeysevent(excelData);
				break;
			case "CHECKMESSAGECOUNT":
				compareStatus = checkmessagecount(excelData);
				break;
			case "REFRESHBROWSER":
				refreshbrowser(driver);
				break;
			case "BARGEMONITORWHISPER":
				Bargemonitorwhisper(excelData);
				break;
			case "CLICKTODIAL":
				clickToDial(excelData);
				break;
			case "MPLTOPO":
				calltransfer.callTransfer(excelData);
				break;
			case "MPLTOCQ":
				calltransfer.callTransfer(excelData);
				break;
			case "MPLTOPG":
				calltransfer.callTransfer(excelData);
				break;
			case "MPLTOPL":
				calltransfer.callTransfer(excelData);
				break;
			case "PGTOMPL":
				calltransfer.callTransfer(excelData);
				break;
			case "PGTOPO":
				calltransfer.callTransfer(excelData);
				break;
			case "PGTOPL":
				calltransfer.callTransfer(excelData);
				break;
			case "PGTOPG":
				calltransfer.callTransfer(excelData);
				break;
			case "PGTOCQ":
				calltransfer.callTransfer(excelData);
				break;
			case "POTOMPL":
				calltransfer.callTransfer(excelData);
				break;
			case "POTOPO":
				calltransfer.callTransfer(excelData);
				break;
			case "POTOPG":
				calltransfer.callTransfer(excelData);
				break;
			case "POTOPL":
				calltransfer.callTransfer(excelData);
				break;
			case "POTOCQ":
				calltransfer.callTransfer(excelData);
				break;
			case "CQTOPO":
				calltransfer.callTransfer(excelData);
				break;
			case "CQTOMPL":
				calltransfer.callTransfer(excelData);
				break;
			case "CQTOPG":
				calltransfer.callTransfer(excelData);
				break;
			case "PLTOPG":
				calltransfer.callTransfer(excelData);
				break;
			case "PLTOPO":
				calltransfer.callTransfer(excelData);
				break;
			case "PLTOCQ":
				calltransfer.callTransfer(excelData);
				break;
			case "PLTOPL":
				calltransfer.callTransfer(excelData);
				break;
			case "PLTOMPP":
				calltransfer.callTransfer(excelData);
				break;
			default:
				break;
			}

			if (compareStatus.isEmpty()) {
				status = "PASS";
			} else {
				status = compareStatus;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Waiting for Element to Load.
	 */
	public String value(ExcelDataPojo excelData) {
		String status = "FAIL";
		try {
			WebElement element = getElement(excelData.getxPathIdType(), excelData.getXpathIdValue());
			Thread.sleep(2000);
			String text = element.getText();
			System.out.println(text);
			if (element.getText().contentEquals(excelData.getActionData())) {
				System.out.println("Both the text matches");
				status = "PASS";
			} else {
				System.out.println("Different data");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}

	/**
	 * Waiting for Element to Load
	 */
	@Override
	public void waitTillElementLoad(ExcelDataPojo excelData) {
		WebElement element = getElement(excelData.getPreRequestType(), excelData.getPreRequestData());
		if (element != null && element.getText().isEmpty()) {
			System.out.println("text data:" + element.getText());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Verification of the text in each modules.
	 */
	@Override
	public String verify(ExcelDataPojo excelData, String text) {

		String status = "FAIL";
		try {
			List<String> actiontextdata = Arrays.asList(excelData.getActionData().split(","));
			System.out.println("text" + text);
			System.out.println("text of actiontextdata" + actiontextdata.get(0));
			if (actiontextdata.get(0).contains(text.trim())) {
				status = "PASS";
			} else {
				status = "FAIL";
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("Element is not verified");
		}
		return status;

	}

	/**
	 * To perform Login Click
	 * 
	 * @throws InterruptedException
	 * 
	 */
	@Override
	public String Login(ExcelDataPojo excelData) throws InterruptedException {
		String status = "FAIL";
		try {
			WebElement element = getElement(excelData.getxPathIdType(), excelData.getXpathIdValue());
			element.click();
			Thread.sleep(8000);
			String currentUrl = driver.getCurrentUrl();
			List<ArrayList<String>> URL1 = readExcelsheet.getExcelData("VMC2Info");
			SITEURL = URL1.get(0).get(0);
			String invalidCredentials = SITEURL + "/login";
			String homepage = SITEURL + "/home";
			if (currentUrl.equals(invalidCredentials)) {
				System.out.println("Check crendentials");
				System.exit(0);
			} else if (currentUrl.equals(homepage)) {
				System.out.println("Successfully Logged IN");
				status = "PASS";
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("Login failed");
		}
		return status;

	}

	/**
	 * To Verify Image display in each modules.
	 */
	@Override
	public String getimage(ExcelDataPojo excelData, String imageValue) {

		String status = "FAIL";
		try {

			WebElement getimage = null;
			getimage = getElement(excelData.getxPathIdType(), excelData.getXpathIdValue());
			if (getimage.isDisplayed()) {
				System.out.println("Image displayed");

				status = "PASS";
			} else {
				System.out.println("Image is not displayed");

				status = "FAIL";
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("Getimage is not displayed");
		}
		return status;
	}

	/**
	 * To Verify Color Highlights in modules.
	 */
	@Override
	public String getcolor(ExcelDataPojo excelData) {
		String status = "FAIL";
		try {
			@SuppressWarnings("unused")
			WebElement element = getElement(excelData.getxPathIdType(), excelData.getXpathIdValue());
			List<WebElement> elements = driver.findElements(By.className("call-no-call"));
			if (elements.isEmpty()) {
				WebElement value = driver.findElement(By.className("th1-exceeded"));
				String threshold_color = value.getCssValue("background-color");
				Thread.sleep(5000);
				if (threshold_color.equals(excelData.getActionData())) {
					System.out.println("ColorHighlights is performed");
				}
			} else {
				System.out.println("No active call to perform Color Highlights ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("ColorHighlights is not displayed");
		}
		return status;
	}

	/**
	 * Pagination for Call log & ActiveViewPanel
	 */

	public String pagination(ExcelDataPojo excelData) {
		String status = "FAIL";
		try {
			WebElement Pagination = getElement(excelData.getxPathIdType(), excelData.getXpathIdValue());
			List<WebElement> elements = Pagination.findElements(By.tagName("button"));
			System.out.println("size:" + elements.size());
			if (elements.size() > 0) {
				WebElement ele = elements.get(2);
				System.out.println(ele.getText());
				ele.click();
				System.out.println("PageNumber" + " " + ele.getText() + "is clicked");
				status = "PASS";
			} else {
				System.out.println("Pagination is not present");
			}
		} catch (Exception e) {
			Log.info("Pagination occurs when there are more than 25 records");
		}
		return status;
	}

	/**
	 * To take Screenshot in each modules.
	 */
	@Override
	public void ScreenShotCapt(WebDriver driver) {
		try {
			this.driver = driver;
			Date d = new Date();
			System.out.println(d.toString());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			File scrFile1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile1,
					new File(Propertiesfile.getAbsoultePath("SCREENSHOTS") + File.separator + sdf.format(d) + ".jpeg"));
			System.out.println("Screenshot is captured");
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("Screenshot is not Captured");
		}
	}

	/**
	 * To take Update Result.
	 */
	@Override
	public String ResultStatus(boolean resultstatus) {
		String flag = null;
		if (resultstatus) {
			flag = "FAIL";
		} else {
			flag = "FAIL";
		}
		return flag;
	}

	/**
	 * To do Drag and drop Action
	 */
	@Override
	public String dragdrop(ExcelDataPojo excelData) {
		String status = "FAIL";
		try {
			System.out.println("DND - BEGIN");
			List<String> data = Arrays.asList(excelData.getActionData().split(","));
			System.out.println(data);
			JavascriptExecutor jsDnD = (JavascriptExecutor) driver;
			String jsString = "!function(t){t.fn.simulateDragDrop=function(a){return this.each(function(){new t.simulateDragDrop(this,a)})},t.simulateDragDrop=function(t,a){this.options=a,this.simulateEvent(t,a)},t.extend(t.simulateDragDrop.prototype,{simulateEvent:function(a,e){var n=\"dragstart\",r=this.createEvent(n);this.dispatchEvent(a,n,r),n=\"drop\";var i=this.createEvent(n,{});i.dataTransfer=r.dataTransfer,this.dispatchEvent(t(e.dropTarget)[0],n,i),n=\"dragend\";var s=this.createEvent(n,{});s.dataTransfer=r.dataTransfer,this.dispatchEvent(a,n,s)},createEvent:function(t){var a=document.createEvent(\"CustomEvent\");return a.initCustomEvent(t,!0,!0,null),a.dataTransfer={data:{},setData:function(t,a){this.data[t]=a},getData:function(t){return this.data[t]}},a},dispatchEvent:function(t,a,e){t.dispatchEvent?t.dispatchEvent(e):t.fireEvent&&t.fireEvent(\"on\"+a,e)}})}(jQuery);";
			System.out.println(
					"DragDrop" + "$(" + data.get(0) + ").simulateDragDrop({ dropTarget: " + data.get(1) + "});");
			jsDnD.executeScript(
					jsString + "$(" + data.get(0) + ").simulateDragDrop({ dropTarget: '" + data.get(1) + "'});");
			System.out.println("DND - END");
			status = "PASS";
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("DragandDrop is not occured");
		}
		return status;
	}

	/**
	 * 
	 * Move folder in Voice mail.
	 * 
	 * @return
	 * 
	 */
	public String movefolder(ExcelDataPojo excelData) {
		String status = "FAIL";

		try {
			List<String> data = Arrays.asList(excelData.getActionData().split(","));
			JavascriptExecutor jsDnD = (JavascriptExecutor) driver;
			WebElement ElementRef = getElement(excelData.getxPathIdType(), excelData.getXpathIdValue());
			List<WebElement> elements = ElementRef.findElements(By.id("voicemailWindow_email"));
			if (elements.isEmpty()) {
				System.out.println("Voicemail is not present to move between folders");
			} else {
				String jsString = "!function(t){t.fn.simulateDragDrop=function(a){return this.each(function(){new t.simulateDragDrop(this,a)})},t.simulateDragDrop=function(t,a){this.options=a,this.simulateEvent(t,a)},t.extend(t.simulateDragDrop.prototype,{simulateEvent:function(a,e){var n=\"dragstart\",r=this.createEvent(n);this.dispatchEvent(a,n,r),n=\"drop\";var i=this.createEvent(n,{});i.dataTransfer=r.dataTransfer,this.dispatchEvent(t(e.dropTarget)[0],n,i),n=\"dragend\";var s=this.createEvent(n,{});s.dataTransfer=r.dataTransfer,this.dispatchEvent(a,n,s)},createEvent:function(t){var a=document.createEvent(\"CustomEvent\");return a.initCustomEvent(t,!0,!0,null),a.dataTransfer={data:{},setData:function(t,a){this.data[t]=a},getData:function(t){return this.data[t]}},a},dispatchEvent:function(t,a,e){t.dispatchEvent?t.dispatchEvent(e):t.fireEvent&&t.fireEvent(\"on\"+a,e)}})}(jQuery);";
				System.out.println(
						"DragDrop" + "$(" + data.get(0) + ").simulateDragDrop({ dropTarget: " + data.get(1) + "});");
				jsDnD.executeScript(
						jsString + "$(" + data.get(0) + ").simulateDragDrop({ dropTarget: " + data.get(1) + "});");
				System.out.println("Voicemail is moved from one Folder to other");
			}
		} catch (Exception e) {
			status = "FAIL";
			e.printStackTrace();
			Log.info("MoveFolder is not occured");
		}
		return status;
	}

	/**
	 * Voice mail Count
	 */
	@Override
	public String checkmessagecount(ExcelDataPojo excelData) {
		String status = "FAIL";
		try {
			WebElement id = getElement(excelData.getxPathIdType(), excelData.getXpathIdValue());
			WebElement value = driver.findElement(
					By.xpath("//td/button[contains(@id,'" + excelData.getXpathIdValue() + "')]/parent::td"));
			if (!value.getText().contains("0")) {
				id.click();
				status = "PASS";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * KeyDownEvent
	 */
	@Override
	public String keydownevent(ExcelDataPojo excelData) {
		String status = "FAIL";
		try {
			WebElement keydown = getElement(excelData.getxPathIdType(), excelData.getXpathIdValue());
			if (keydown.isDisplayed()) {
				Actions builder = new Actions(driver);
				Action seriesofActions = builder.keyDown(keydown, Keys.CONTROL).click().build();
				seriesofActions.perform();
				System.out.println("KeyDown Event performed ");
			} else {
				System.out.println("No Voicemail");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("KeydownEvent is not occured");
		}
		return status;
	}

	/**
	 * SendKeys Event
	 */
	@Override
	public String sendkeysevent(ExcelDataPojo excelData) {
		String status = "FAIL";
		try {
			WebElement sendkeys = getElement(excelData.getxPathIdType(), excelData.getXpathIdValue());
			Actions builder = new Actions(driver);
			builder.sendKeys(sendkeys, Keys.DELETE).build().perform();
			System.out.println("voicemail select to be deleted ");
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("sendkeysevent is not occured");
		}
		return status;
	}

	/**
	 * Refresh Browser
	 */
	@Override
	public void refreshbrowser(WebDriver driver) {
		try {
			driver.navigate().refresh();
			System.out.println("Browser is refreshed ");
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("Refreshbrowser is not occured");
		}
	}

	/**
	 * To check UnAuthorized Users @throws
	 * 
	 * @return
	 */
	@Override
	public String unprovisioned(ExcelDataPojo excelData) {
		String status = "FAIL";
		try {
			String expectedUrl = SITEURL + "/unprovisioned";
			String actualUrl = driver.getCurrentUrl();
			if (actualUrl.equals(expectedUrl)) {
				WebElement popup = driver.findElement(By.id("unprovisionedUsers_popup"));
				WebElement dismiss = driver.findElement(By.className("btn-default"));
				if (popup.isDisplayed()) {
					dismiss.click();
				}
			} else {
				System.out.println("No Unprovisioned Users");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("UnprovisionedPopup is not occured");
		}
		return status;
	}

	/**
	 * To do Resize @throws
	 */
	@Override
	public String resize(ExcelDataPojo excelData) {
		String status = "FAIL";
		try {
			WebElement resizeElement = getElement(excelData.getxPathIdType(), excelData.getXpathIdValue());
			new Actions(driver).dragAndDropBy(resizeElement, 250, 250).build().perform();
			Thread.sleep(2000);
			new Actions(driver).dragAndDropBy(resizeElement, 250, 250).build().perform();
			Thread.sleep(2000);
			new Actions(driver).dragAndDropBy(resizeElement, -200, -200).build().perform();
			Thread.sleep(2000);
			new Actions(driver).dragAndDropBy(resizeElement, -200, -200).build().perform();
			Thread.sleep(2000);
			new Actions(driver).dragAndDropBy(resizeElement, -200, -200).build().perform();
			System.out.println("Resizing is done");
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("Resize is not occured");
		}

		return status;
	}
	/**
	 * To do Resize in Phonegroup @throws
	 */
	@Override
	public String phonegroupresize(ExcelDataPojo excelData) {
		String status = "FAIL";
		try {
			WebElement resizeElement = getElement(excelData.getxPathIdType(), excelData.getXpathIdValue());
			new Actions(driver).dragAndDropBy(resizeElement, 250, 250).build().perform();
			Thread.sleep(2000);
			new Actions(driver).dragAndDropBy(resizeElement, 250, 250).build().perform();
			Thread.sleep(2000);
		}
		catch (Exception e) {
			e.printStackTrace();
			Log.info("Resize is not occured");
		}
		return status;
	}
	

	/**
	 * To perform Barge,Monitor,Whisper @throws
	 */
	@Override
	public String Bargemonitorwhisper(ExcelDataPojo excelData) {
		String status = "FAIL";
		try {
			WebElement BMW = getElement(excelData.getxPathIdType(), excelData.getXpathIdValue());
			List<WebElement> elements = BMW.findElements(By.id("myPhonePanel_callStatus"));
			if (!elements.isEmpty()) {
				Thread.sleep(2000);
				WebElement element = driver.findElement(By.className("monitor-gray"));
				if (element.isDisplayed()) {
					element.click();
					status = "PASS";
				}
			} else {
				System.out.println(" No Active Call to perform Barge,Monitor,Whisper");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("Barge,Monitor,Whisper Icon is not displayed");
		}
		return status;
	}

	/**
	 * To perform ClickToDial @throws
	 */
	@Override
	public String clickToDial(ExcelDataPojo excelData) {
		String status = "FAIL";
		try {
			WebElement Elementref = getElement(excelData.getxPathIdType(), excelData.getXpathIdValue());
			Thread.sleep(2000);
			List<WebElement> elements = driver.findElements(By.className("call-no-call"));
			if (elements.isEmpty()) {
				WebElement status1 = driver.findElement(By.id("myPhonePanel_callStatus"));
				Thread.sleep(1000);
				status1.click();
				Thread.sleep(3000);
				WebElement clickToDial = driver.findElement(By.className("reject"));
				clickToDial.click();
				status="PASS";
			} else {
				System.out.println("No Active Call");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("ClickToDial is not performed");
		}
		return status;
	}

	/**
	 * Getting list of values.
	 */
	@Override
	public ExcelDataPojo getValues(List<String> cellValue) {
		System.out.println(cellValue);
		return new ExcelDataPojo(cellValue.get(1), cellValue.get(2), cellValue.get(3), cellValue.get(4),
				cellValue.get(5), cellValue.get(6), cellValue.get(7), cellValue.get(8), cellValue.get(9),
				cellValue.get(10));
	}

}
