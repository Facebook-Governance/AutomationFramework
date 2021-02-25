package com.org.pages;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.org.Util.DataUtils;
import com.org.Util.LocatorUtils;
import com.org.Util.SeleniumUtils;

public class LandingPage extends SeleniumUtils {

	public LandingPage(WebDriver driver) {
		super(driver);
	}

	public void selectWomenCategory() {
		click(LocatorUtils.getLocator("LandingPage.Women.Tab"));
	}

	public void applyThreeFilters() {
		click(LocatorUtils.getLocator("LandingPage.Women.Tab"));
	}

	public void selectProductFromTheListWithPrice(String price) {
		List<WebElement> products = getWebElements(LocatorUtils.getLocator("ProductList"));
		List<WebElement> productPrice = getWebElements(LocatorUtils.getLocator("ProductListWithPrice"));
		List<WebElement> productImage = getWebElements(LocatorUtils.getLocator("ProductImage"));
		for (int i = 0; i < products.size(); i++) {
			moveToElement(products.get(i));
			waitFor(1);
			if (productPrice.get(i).getText().trim().equals(price)) {
				if (i != 0) {
					moveToElement(products.get(i - 1));
				}
				productImage.get(i).click();
				break;
			}
		}
	}

	public void switchToFrame(By element) {

		driver.switchTo().frame(driver.findElement(element));

	}

	public void switchToDefaultContent() {

		driver.switchTo().defaultContent();
	}

	public void changeColorOfProductIfAvailable() {
		List<WebElement> findElements = driver
				.findElements(LocatorUtils.getLocator("CheckOutPage.ColorSelector.PickColor"));
		if (findElements.size() > 1) {
			for (WebElement element : findElements) {
				if (element.getAttribute("class") != null || !element.getAttribute("class").equals("selected")) {
					element.click();
					break;
				}
			}
		}
	}

	public void updateQuantity() {

		sendkeys(LocatorUtils.getLocator("ProductPage.Update.Quantity"), DataUtils.getTestData("QuantityToBeUpdated"));
	}

	public void selectSize() {

		Select ProductSize = new Select(driver.findElement(LocatorUtils.getLocator("ProductPage.SelectSize")));

		ProductSize.selectByVisibleText(DataUtils.getTestData("ProductSizeToBeSelected"));

	}

	public void submitTheProduct() {
		click(LocatorUtils.getLocator("CheckOutPage.AddProduct.Button"));

	}

	public void proceedToCheckOut() {
		click(LocatorUtils.getLocator("CheckOutPage.ProceedToCheckOut.Button"));
	}

	public void reviewAndProceedToCheckOutProduct() {
		click(LocatorUtils.getLocator("ShoppingCart.ProceedToCheckOut.Button"));
	}

	public void loginToPortal() {
		sendkeys(LocatorUtils.getLocator("CheckOutPage.Login.Email"), DataUtils.getTestData("LoginEmail"));
		sendkeys(LocatorUtils.getLocator("CheckOutPage.Login.Password"), DataUtils.getTestData("LoginPassword"));
		click(LocatorUtils.getLocator("CheckOutPage.Login.SubmitButton"));

	}

	public void addNewAddress() {
		click(LocatorUtils.getLocator("AddressPage.AddNewAddress.Button"));
		System.out.println("Add New Address Now");
		sendkeys(LocatorUtils.getLocator("AddressPage.AddressInput.AddressTextField"),
				DataUtils.getTestData("AddressLine"));
		sendkeys(LocatorUtils.getLocator("AddressPage.AddressInput.CityInput"), DataUtils.getTestData("City"));

		Select state = new Select(driver.findElement(LocatorUtils.getLocator("AddressPage.AddressInput.StateInput")));
		state.selectByVisibleText(DataUtils.getTestData("State"));

		sendkeys(LocatorUtils.getLocator("AddressPage.AddressInput.ZipCodeInput"), DataUtils.getTestData("ZipCode"));
		sendkeys(LocatorUtils.getLocator("AddressPage.AddressInput.PhoneInput"),
				DataUtils.getTestData("HomePhoneNumber"));
		sendkeys(LocatorUtils.getLocator("AddressPage.AddressInput.MobilePhoneInput"),
				DataUtils.getTestData("MobilePhone"));
		sendkeys(LocatorUtils.getLocator("AddressPage.AddressInput.AddressAlias"),
				"My Address" + new Random().nextInt(1000000000));
		click(LocatorUtils.getLocator("AddressPage.AddressInput.SaveAddressButton"));
		click(LocatorUtils.getLocator("AddressPage.ContnueButton"));

	}

	public void shippingPageAcceptTnC() {
		click(LocatorUtils.getLocator("ShippingPage.enableCheckBox"));
		click(LocatorUtils.getLocator("ShippingPage.SubmitButton"));

	}

	public void paymentPageForOder() {
		click(LocatorUtils.getLocator("PaymentPage.OptionBankWire.Tab"));
		click(LocatorUtils.getLocator("OrderConfirmationPage.ConfirmOrderButton"));

	}
	
	public void orderConfirmation() {
		Assert.assertEquals(getText(LocatorUtils.getLocator("OrderVerificationPage.TextVerification")), DataUtils.getTestData("OrderConfirmationText"));
		Assert.assertTrue(isDisplayed(LocatorUtils.getLocator("OrderVerificationPage.TextVerification")), "Oder has not been placed !!");
		
		
	}
}
