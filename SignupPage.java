package pages;

import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class SignupPage extends StartupPage {
	
	public SignupPage(WebDriver driver) {
		super(driver);
	}

	private By profileIcon = By.xpath("//span[contains(@class,\"user-profile-icon\")]");
	private By signUpicon = By.cssSelector("a#header-icon-signup");
	private By emailAddress = By.cssSelector("input[placeholder='Email Address*']");
	private By userPassword = By.xpath("(//div[@class='password']/input[@type=\"password\"])[1]");
	private By signUpButton = By.cssSelector("input[value='Sign Up']");
	private By headerProfileIcon = By.cssSelector("a#header-icon-profile");
	private By logoutButton = By.cssSelector("a#logout");

	public void hoverOnProfileButton() {
		new Actions(driver).moveToElement(driver.findElement(profileIcon)).click().build().perform();
	}

	public void clickOnSignupButton() {
		driver.findElement(signUpicon).click();
	}

	public void doSignup(String password) throws InterruptedException {
		String randomEmailAddress = generateRandomUsername();
		System.out.println("Email Address --> " + randomEmailAddress);
		System.out.println("Password --> " + password);
		driver.findElement(emailAddress).sendKeys(randomEmailAddress);
		driver.findElement(userPassword).click();
		driver.findElement(userPassword).sendKeys(password);
		driver.findElement(signUpButton).click();
	}

	public static String generateRandomUsername() {
		// Generate a random unique ID
		String randomId = UUID.randomUUID().toString().substring(0, 8); // Take the first 8 characters
		return "user" + randomId + "@mailinator.com";
	}

	public boolean isuserLoggedIn() {
		boolean isMyAccountVisible = false;
		try {
			new Actions(driver).moveToElement(driver.findElement(profileIcon)).click().build().perform();
			isMyAccountVisible = driver.findElement(headerProfileIcon).isDisplayed();
			System.out.println("Is Profile displayed ?? " + isMyAccountVisible);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isMyAccountVisible;
	}

	public void clickLogoutButton() {
		driver.findElement(logoutButton).click();
	}

	public boolean isUserLoggedOut() {
		new Actions(driver).moveToElement(driver.findElement(profileIcon)).click().build().perform();
		if (driver.findElement(signUpicon).isDisplayed()) {
			return true;
		} else
			return false;
	}

}
