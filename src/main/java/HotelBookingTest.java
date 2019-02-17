import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.sun.javafx.PlatformUtil;

public class HotelBookingTest {

	WebDriver driver = null;

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
		setDriverPath();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		PageFactory.initElements(driver, this);
	}

	@Test
	public void shouldBeAbleToSearchForHotels() {
		driver.get("https://www.cleartrip.com/");
		hotelLink.click();
		String urlBeforeSearchOperation = driver.getCurrentUrl();
		String testData = "Indiranagar, Bangalore";
		localityTextBox.click();
		localityTextBox.clear();
		localityTextBox.sendKeys(testData);
		localityTextBox.click();
		waitFor(2000);
		localityTextBox.sendKeys(Keys.ENTER);
		new Select(travellerSelection).selectByVisibleText("1 room, 2 adults");
		checkInDate.click();
		checkInDateElement.click();
		checkOutDate.click();
		checkOutDateElement.click();
		searchButton.click();
		waitFor(3000);
		String urlAfterSearchOperation = driver.getCurrentUrl();
		Assert.assertNotEquals(urlBeforeSearchOperation, urlAfterSearchOperation);
	}

	private void setDriverPath() {
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

	private void waitFor(int durationInMilliSeconds) {
		try {
			Thread.sleep(durationInMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace(); // To change body of catch statement use File | Settings | File Templates.
		}
	}

	@AfterTest
	public void teardown() {
		driver.quit();
	}

}
