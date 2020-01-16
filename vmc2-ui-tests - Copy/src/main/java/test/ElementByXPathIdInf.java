package test;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface ElementByXPathIdInf {

	public Logger Log = Logger.getLogger(Logger.class.getName());	//	Logger object.
	static WebElement element = null;								// Web Element.

	/**
	 * Returns web element based on given x-path.
	 * @param driver
	 * @param xpathValue
	 * @return WebElement
	 */
	static WebElement getByXPath(WebDriver driver, String xpathValue) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathValue)));
			List<WebElement> elements = driver.findElements(By.xpath(xpathValue));
			if (elements.size() == 1)
				return driver.findElement(By.xpath(xpathValue));

			for (WebElement ele : elements) {
				try {
					if (ele.isDisplayed())
						return ele;
				} catch (StaleElementReferenceException e) {
					System.out.println("Attempting to recover from StaleElementReferenceException ...");
					getByXPath(driver, xpathValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("XPATH is not Found");
		}

		return element;
	}

	/**
	 * Returns web element based on given id.
	 * @param driver
	 * @param idValue
	 * @return WebElement
	 */
	static WebElement getByID(WebDriver driver, String idValue) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(idValue)));
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("ID is not Found");
		}
		return driver.findElement(By.id(idValue));

	}

	/**
	 * Returns web element based on given class name.
	 * @param driver
	 * @param classnameValue
	 * @return WebElement
	 */
	static WebElement getByClassName(WebDriver driver, String classnameValue) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className(classnameValue)));
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("Classname is not Found");
		}
		return driver.findElement(By.className(classnameValue));
	}

}
