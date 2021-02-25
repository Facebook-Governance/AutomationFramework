package com.org.WebAppAutomation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.org.Util.BaseClass;
import com.org.Util.DataUtils;
import com.org.Util.LocatorUtils;
import com.org.pages.ApplyFiltersToSelectProducts;
import com.org.pages.LandingPage;

public class PurchaseProduct extends BaseClass {
	protected Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	LandingPage landingPage;
	ApplyFiltersToSelectProducts filters;

	@Test(priority = 1, description = "Load the URL with WebPage info")
	public void verifyLandingPage() {
		logger.info("Loading the environment URL and Lanching the driver to load URL");
		landingPage = new LandingPage(driver);
		loadEnvironment();
	}

	@Test(priority = 2, description = "Go to Women Category")
	public void SelectCategory() {
		logger.info("Selecting the Women Category");
		landingPage.selectWomenCategory();
	}

	@Test(priority = 3, description = "Apply Three Filters")
	public void ApplyFiltersOnProducts() {
		/*
		WebPage is not loading after applying filter hence this TC is expected failure
		*/
		logger.info(
				"Applying filters on the Women Category, This TC is expected to Fail as Applying filter is not working.");

		filters.applyConditionFilter();
		filters.applyManufacturerFilter();
		filters.applyStockFilter();
	}

	@Test(priority = 4, description = "Click on Add to cart for a product with 27$ value")
	public void selectProductWithSpecificPrice() {
		logger.info("Capturing all the displayed product and selecting the one with Specific Price - $27.00 ");
		landingPage.selectProductFromTheListWithPrice(DataUtils.getTestData("PriceForProductSelection"));
	}

	@Test(priority = 5, description = "Change color if more than one color is available")
	public void changeColorOfProductIfOptionsAvailable() {
		logger.info("Switching to iFrame and selecting color is color option is avaialble ");
		landingPage.switchToFrame(LocatorUtils.getLocator("ProductPage.ChangeiFrame"));
		landingPage.changeColorOfProductIfAvailable();
	}

	@Test(priority = 6, description = "Go to Product Page and change quantity")
	public void changeQuantityOfProduct() {
		logger.info("Updating the quantity");

		landingPage.updateQuantity();
	}

	@Test(priority = 7, description = "Go to Product Page and change size")
	public void selectSizeOfProduct() {
		logger.info("Changing the size from default selected size");
		landingPage.selectSize();
	}

	@Test(priority = 8, description = "Add to Cart")
	public void submitTheProduct() {
		logger.info("Submitting the product");
		landingPage.submitTheProduct();
	}

	@Test(priority = 9, description = "Go to Cart Page")
	public void proceedToCheckOut() {
		logger.info("Proceeding to CheckOut");
		landingPage.proceedToCheckOut();
	}

	@Test(priority = 10, description = "Proceed to checkout")
	public void reviewAndProceedToCheckOutProduct() {
		logger.info("Reviewing the details on Review Page, and Proceeding for Shipping Info");
		landingPage.switchToDefaultContent();
		landingPage.reviewAndProceedToCheckOutProduct();
	}

	@Test(priority = 11, description = "Enter username / password")
	public void loginToPortal() {
		logger.info("Providing the login information and and login to Portal");
		landingPage.loginToPortal();
	}

	@Test(priority = 12, description = "Enter Address")
	public void updateOrAddAddress() {
		logger.info("Adding new address on which Product needs to be shipped");
		landingPage.addNewAddress();
	}

	@Test(priority = 13, description = "Shipping details")
	public void shippingPageAcceptTnC() {
		logger.info("Accepting TnC for the product Purchase");
		landingPage.shippingPageAcceptTnC();
	}

	@Test(priority = 14, description = "On the Payment page, add incorrect data and read the error message.")
	public void paymentPageForOder() {
		/*
		 * Here in the payment page, none of the options were available where input
		 * fields were there. hence Selected one of the Payment Method
		 */
		logger.info("Making Payment for Order");
		landingPage.paymentPageForOder();
	}

	@Test(priority = 15, description = "Order Confirmation Page")
	public void orderConfirmationsPage() {
		logger.info("Verifying the oder confirmation Page ");
		landingPage.orderConfirmation();
	}

}
