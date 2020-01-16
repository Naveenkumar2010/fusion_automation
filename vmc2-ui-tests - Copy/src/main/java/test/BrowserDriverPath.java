package test;

public enum BrowserDriverPath {

	FIREFOX("org.openqa.selenium.firefox.FirefoxDriver"), CHROME("org.openqa.selenium.chrome.ChromeDriver"),
	IE("org.openqa.selenium.ie.InternetExplorerDriver");

	private String driver;

	BrowserDriverPath(String driver) {
		this.driver = driver;
	}

	public String getDriver() {
		return driver;
	}
}
