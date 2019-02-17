package com.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sun.javafx.PlatformUtil;

public class DriverUtil {

	public static WebDriver initDriver() {
		setDriverPath();
		WebDriver driver;
		Map<String, Object> prefs = new HashMap<String, Object>();
		// Set the notification setting it will override the default setting
		prefs.put("profile.default_content_setting_values.notifications", 2);
		// Create object of ChromeOption class
		ChromeOptions options = new ChromeOptions();
		// Set the experimental option
		options.setExperimentalOption("prefs", prefs);
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		return driver;
	}

	public static List<WebElement> waitUntilElementIsPresent(WebDriverWait waitObject, By by) {
		return waitObject.until(ExpectedConditions.numberOfElementsToBeMoreThan(by, 0));
	}

	public static void setDriverPath() {
		if (PlatformUtil.isMac()) {
			System.setProperty("webdriver.chrome.driver", "chromedriver");
		}
		if (PlatformUtil.isWindows()) {
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		}
		if (PlatformUtil.isLinux()) {
			System.setProperty("webdriver.chrome.driver", "chromedriver_linux");
		}
	}

	public static void hybridSendKeys(WebElement sourceLocation, String text) {
		sourceLocation.click();
		sourceLocation.clear();
		sourceLocation.sendKeys(text);
	}

	public static boolean isElementPresent(WebDriver driver, By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static void waitFor(int durationInMilliSeconds) {
		try {
			Thread.sleep(durationInMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace(); // To change body of catch statement use File | Settings | File Templates.
		}
	}

}
