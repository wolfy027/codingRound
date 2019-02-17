import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.util.DriverUtil;

public class FlightBookingTest {

	WebDriver driver = null;
	WebDriverWait waitObject = null;

	@BeforeTest
	public void setUp() throws Exception {
		driver = DriverUtil.initDriver();
		waitObject = new WebDriverWait(driver, 10);
	}

	@Test
	public void testThatResultsAppearForAOneWayJourney() {
		driver.get("https://www.cleartrip.com/");
		waitObject.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.id("OneWay"), 0));
		driver.findElement(By.id("OneWay")).click();

		DriverUtil.hybridSendKeys(driver.findElement(By.id("FromTag")), "Bangalore");
		// wait for the auto complete options to appear for the origin
		DriverUtil.waitUntilElementIsPresent(waitObject, new ByChained(By.id("ui-id-1"), By.tagName("li")));
		List<WebElement> originOptions = driver.findElements(new ByChained(By.id("ui-id-1"), By.tagName("li")));
		originOptions.get(0).click();

		DriverUtil.hybridSendKeys(driver.findElement(By.id("ToTag")), "Delhi");
		// wait for the auto complete options to appear for the destination
		DriverUtil.waitUntilElementIsPresent(waitObject, new ByChained(By.id("ui-id-2"), By.tagName("li")));
		// select the first item from the destination auto complete list
		List<WebElement> destinationOptions = driver.findElements(new ByChained(By.id("ui-id-2"), By.tagName("li")));
		destinationOptions.get(0).click();

		driver.findElement(By.id("DepartDate")).click();
		DriverUtil.waitUntilElementIsPresent(waitObject, By.xpath("//td[@data-month='1']/a"));
		driver.findElement(By.xpath("//td[@data-month='1']/a")).click();
		// all fields filled in. Now click on search
		driver.findElement(By.id("SearchBtn")).click();

		DriverUtil.waitUntilElementIsPresent(waitObject, By.className("searchSummary"));
		// verify that result appears for the provided journey search
		Assert.assertTrue(DriverUtil.isElementPresent(driver, By.className("searchSummary")));
		Assert.assertTrue(
				DriverUtil.isElementPresent(driver, new ByChained(By.className("listView"), By.className("listItem"))));

	}

	@AfterTest
	public void teardown() {
		driver.quit();
	}
}
