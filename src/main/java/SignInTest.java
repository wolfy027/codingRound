import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.sun.javafx.PlatformUtil;

@SuppressWarnings("restriction")
public class SignInTest {

	@BeforeTest
	public void setUp() throws Exception {
		setDriverPath();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	}

	WebDriver driver = null;

	@Test
	public void shouldThrowAnErrorIfSignInDetailsAreMissing() {
		driver.get("https://www.cleartrip.com/");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		driver.findElement(By.linkText("Your trips")).click();
		driver.findElement(By.id("SignIn")).click();
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.id("modal_window"), 0));
		driver.switchTo().frame("modal_window");
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.id("signInButton"), 0));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("signInButton")));
		driver.findElement(By.id("signInButton")).click();
		String errors1 = driver.findElement(By.id("errors1")).getText();
		Assert.assertTrue(errors1.contains("There were errors in your submission"));
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

	@AfterTest
	public void teardown() {
		driver.quit();
	}

}
