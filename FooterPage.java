package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FooterPage extends StartupPage {
	
	public FooterPage(WebDriver driver) {
		super(driver);
	}

	private By contactUsButton = By.xpath("//a[text()='Contact Us']");
	private By contactUsHeading = By.xpath("(//div[@class='section-title-container']/span)[2]");

	public void clickOnContactUsLink() {
		driver.findElement(contactUsButton).click();
	}

	public boolean verifyContactUsHeading() {
		if (driver.findElement(contactUsHeading).isDisplayed()) {
			return true;
		} else
			return false;
	}

}
