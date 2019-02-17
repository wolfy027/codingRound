import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.util.DriverUtil;

public class HotelBookingTest {

	WebDriver driver = null;
	WebDriverWait waitObject = null;

	@FindBy(xpath = "//li[@class='hotelApp ']")
	private WebElement hotelLink;

	@FindBy(id = "Tags")
	private WebElement localityTextBox;

	@FindBy(id = "SearchHotelsButton")
	private WebElement searchButton;

	@FindBy(id = "travellersOnhome")
	private WebElement travellerSelection;

	@FindBy(id = "wgSelectChoosen")
	private WebElement citySelection;

	@FindBy(id = "CheckInDate")
	private WebElement checkInDate;

	@FindBy(xpath = "//td[@data-month='1']")
	private WebElement checkInDateElement;

	@FindBy(id = "CheckOutDate")
	private WebElement checkOutDate;

	@FindBy(xpath = "//td[@data-month='2']")
	private WebElement checkOutDateElement;

	@BeforeTest
	public void setUp() throws Exception {
		driver = DriverUtil.initDriver();
		PageFactory.initElements(driver, this);
		waitObject = new WebDriverWait(driver, 10);
	}

	@Test
	public void shouldBeAbleToSearchForHotels() {
		driver.get("https://www.cleartrip.com/");
		DriverUtil.waitUntilElementIsPresent(waitObject, By.xpath("//li[@class='hotelApp ']"));
		hotelLink.click();
		String urlBeforeSearchOperation = driver.getCurrentUrl();
		String testData = "Indiranagar, Bangalore";
		localityTextBox.click();
		localityTextBox.clear();
		localityTextBox.sendKeys(testData);
		localityTextBox.click();

		DriverUtil.waitUntilElementIsPresent(waitObject, new ByChained(By.id("ui-id-1"), By.tagName("li")));
		// select the first item from the destination auto complete list
		List<WebElement> destinationOptions = driver
				.findElements(new ByChained(By.id("ui-id-1"), By.tagName("li"), By.tagName("a")));
		destinationOptions.get(0).click();

		new Select(travellerSelection).selectByVisibleText("1 room, 2 adults");
		checkInDate.click();
		checkInDateElement.click();
		checkOutDate.click();
		checkOutDateElement.click();
		searchButton.click();
		String urlAfterSearchOperation = driver.getCurrentUrl();
		Assert.assertNotEquals(urlBeforeSearchOperation, urlAfterSearchOperation);
	}

	@AfterTest
	public void teardown() {
		driver.quit();
	}

}
