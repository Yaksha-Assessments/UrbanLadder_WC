package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class SearchPage extends StartupPage {
	
	public SearchPage(WebDriver driver) {
		super(driver);
	}

	private By searchBar = By.cssSelector("input#search");
	private By searchButton = By.cssSelector("button#search_button");
	private By searchedProductTitle = By.xpath("//div[contains(@class,'product-title')]/span[@class='name']");
	private By wishIcon = By.xpath("(//span[contains(@class,'wishicon')])[1]");
	private By wishlistCount = By.cssSelector("div#shortlist-badge span:nth-child(2)");
	private By viewProductButton = By.xpath("//a[text()='View Product']");
	private By productTitle = By.xpath("//div[contains(@id,'product-title-container')]/h1[@class='product-title']");
	private By priceFilter = By.xpath("(//li[@data-group=\"price\"])[1]");
	private By priceRange = By.xpath("(//label[@class=\"range\"])[1]");
	private By productPrice = By.cssSelector("div.price-number span.pricing");
	private By inStockOnlyCheckBox = By.cssSelector("div[data-option-key=\"In Stock Only\"] label");
	private By headerWishlistIcon = By.cssSelector("div#shortlist-badge ");
	private By wishlistPageProductName = By.cssSelector("a.product-title-block div span.name");
	private By addToCompareButton = By.xpath("(//a[text()='Add to Compare'])[1]");
	private By widgetProduct = By.xpath("//div[contains(@class,\"widget-product-card\") and @data-vid]");

	public void searchForProduct(String productName) {
		driver.findElement(searchBar).sendKeys(productName);
		driver.findElement(searchButton).click();
	}

	public boolean verifySearchedProduct(String expectedProductName) {
		try {
			List<WebElement> productTitle = driver.findElements(searchedProductTitle);
			for (int i = 0; i < 20; i++) {
				String actualProductName = productTitle.get(i).getText();
				System.out.println(" Actual Product Name -->> " + actualProductName);
				Assert.assertTrue(actualProductName.contains(expectedProductName));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	int beforeParsedItemWishlistedText;
	String getWishlistProductText;

	public void addItemToWishlist() {
		String beforeItemWishlistedText = driver.findElement(wishlistCount).getText();
		getWishlistProductText = driver.findElements(searchedProductTitle).get(0).getText();
		System.out.println("Wish list product Text ---> " + getWishlistProductText);
		beforeParsedItemWishlistedText = Integer.parseInt(beforeItemWishlistedText);
		System.out.println("before paresed text --> " + beforeParsedItemWishlistedText);
		driver.findElement(wishIcon).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean verifyProductAddedToWishlist() {
		String afterItemWishlistedText = driver.findElement(wishlistCount).getText();
		int afterParsedItemWishlistedText = Integer.parseInt(afterItemWishlistedText);
		System.out.println("After paresed text --> " + afterParsedItemWishlistedText);
		if (afterParsedItemWishlistedText > beforeParsedItemWishlistedText) {
			return true;
		} else
			return false;
	}

	String actualProductName = "";

	public void clickOnViewProduct() {
		actualProductName = driver.findElements(searchedProductTitle).get(0).getText();
		System.out.println("Product Name ===> " + actualProductName);
		new Actions(driver).moveToElement(driver.findElements(searchedProductTitle).get(0)).build().perform();
		driver.findElements(viewProductButton).get(0).click();
	}

	public boolean verifyProductDetailsOnProductInfoPage() {
		String expectedProductName = driver.findElement(productTitle).getText();
		System.out.println("Expected product Name ---->> " + expectedProductName);
		if (expectedProductName.trim().equals(actualProductName.trim())) {
			return true;
		} else
			return false;
	}

	public void applyPriceFilter() {
		new Actions(driver).moveToElement(driver.findElement(priceFilter)).build().perform();
		driver.findElement(priceFilter).click();
		driver.findElement(priceRange).click();
	}

	public boolean verifyProductPriceLiesInRange() throws InterruptedException {
		boolean areProductPricesFallInRange = false;
		String getPriceRangeInString = driver.findElement(priceRange).getText().trim();

		// Extract min and max price
		String[] priceRange = getPriceRangeInString.replace("₹", "").replace(",", "").split("-");
		int minPrice = Integer.parseInt(priceRange[0].trim());
		int maxPrice = Integer.parseInt(priceRange[1].trim());
		System.out.println("Min price --> " + minPrice);
		System.out.println("Max price --> " + maxPrice);

		Thread.sleep(2000);
		driver.findElement(inStockOnlyCheckBox).click();

		List<WebElement> productPriceWebElement = driver.findElements(productPrice);
		System.out.println("Size -->" + productPriceWebElement.size());

		for (int i = 0; i < productPriceWebElement.size(); i++) {
			String productPriceText = productPriceWebElement.get(i).getText().trim();
			int productPrice = Integer
					.parseInt(productPriceText.replace("Starting From ₹", "").replace(",", "").trim());
			if (productPrice >= minPrice && productPrice <= maxPrice) {
				areProductPricesFallInRange = true;
			} else {
				areProductPricesFallInRange = false;
				break;
			}
		}
		return areProductPricesFallInRange;
	}

	public boolean verifyWishlistedProductDetails() {
		driver.findElement(headerWishlistIcon).click();
		String expectedProductName = driver.findElement(wishlistPageProductName).getText();
		if (expectedProductName.trim().equals(getWishlistProductText.trim())) {
			return true;
		} else
			return false;
	}

	public void clickOnAddToCompareButton() {
		new Actions(driver).moveToElement(driver.findElements(searchedProductTitle).get(0)).build().perform();
		driver.findElement(addToCompareButton).click();
	}

	public boolean verifyProductIsPresentInCompareList() {
		if (driver.findElement(widgetProduct).isDisplayed()) {
			return true;
		} else
			return false;
	}

}
