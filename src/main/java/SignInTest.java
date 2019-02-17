import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.util.DriverUtil;

public class SignInTest {
	WebDriver driver = null;
	WebDriverWait waitObject = null;

	@BeforeTest
	public void setUp() throws Exception {
		driver = DriverUtil.initDriver();
		waitObject = new WebDriverWait(driver, 10);
	}

	@Test
	public void shouldThrowAnErrorIfSignInDetailsAreMissing() {
		driver.get("https://www.cleartrip.com/");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		driver.findElement(By.linkText("Your trips")).click();
		driver.findElement(By.id("SignIn")).click();
		DriverUtil.waitUntilElementIsPresent(waitObject, By.id("modal_window"));
		driver.switchTo().frame("modal_window");
		DriverUtil.waitUntilElementIsPresent(waitObject, By.id("signInButton"));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("signInButton")));
		driver.findElement(By.id("signInButton")).click();
		String errors1 = driver.findElement(By.id("errors1")).getText();
		Assert.assertTrue(errors1.contains("There were errors in your submission"));
	}

	@AfterTest
	public void teardown() {
		driver.quit();
	}

}
