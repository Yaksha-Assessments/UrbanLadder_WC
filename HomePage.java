package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends StartupPage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	private By homePageLogo = By.className("header__topBar_logo");

	public boolean verifyHomePageLogo() {
		boolean isHomePageLogoDisplayed = false;
		try {
			WebElement homePage = driver.findElement(homePageLogo);
			isHomePageLogoDisplayed = homePage.isDisplayed();
			System.out.println("Is homepage logo dislpayed --->> " + isHomePageLogoDisplayed);
		} catch (Exception e) {
			System.out.println("Home Page logo is not dispayed");
		}
		return isHomePageLogoDisplayed;
	}

	public String getTitleOfHomePage() {
		String title = "";
		try {
			title = driver.getTitle();
			System.out.println("Title of the page ---> " + title);
		} catch (Exception e) {
			System.out.println("Title can not be captured");
		}
		return title;
	}

}
