package com.RegisterUser;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.Utils.ApiUtils;
import com.Utils.BaseClass;
import com.Utils.DataUtils;
import com.Utils.EnvironmentUtils;
import com.Utils.HTTPStatus;
import com.Utils.Headers;

public class RegisterUser extends BaseClass {
	Headers headers;
	ApiUtils apiUtils;

	@BeforeTest
	public void beforeTest() {
		try {
			headers = new Headers();
			headers.setContentTypeAsJSON();
			apiUtils = new ApiUtils();
			apiUtils.setTheBaseURI(EnvironmentUtils.getEnvironmentData("EnvironmentURL"));
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test(priority = 0, description = "Verify successful User Registration")
	public void registerUserSuccessfully() {
		apiUtils.setHeaders(headers.getHeaders());
		apiUtils.post("api/register", DataUtils.getTestData("registerUser_SuccessfulBody"));
		if (!(apiUtils.getStatusCode() == HTTPStatus.OK)) {
			Assert.assertEquals(apiUtils.getStatusCode(), HTTPStatus.OK, "Status code is not as expected");
		}
		Assert.assertEquals(apiUtils.getReponse(), DataUtils.getTestData("registerUser_SuccessfulResponse"),
				"API Response body is not coming as expected");

	}

	@Test(priority = 1, description = "Verify Un-successful User Registration when password is not provided")
	public void registerUserUnSuccessfully_PasswordNotProvided() {
		apiUtils.setHeaders(headers.getHeaders());
		apiUtils.post("api/register", DataUtils.getTestData("registerUser_UnSuccessfulBody_WithoutPassword"));
		if (!(apiUtils.getStatusCode() == HTTPStatus.BAD_REQUEST)) {
			Assert.assertEquals(apiUtils.getStatusCode(), HTTPStatus.BAD_REQUEST, "Status code is not as expected");
		}
		Assert.assertEquals(apiUtils.getValues("error"), DataUtils.getTestData("registerUser_MissingPasswordError"),
				"API Response Body is not coming as expected");
	}

	@Test(priority = 2, description = "Verify Un-successful User Registration when email is not provided")
	public void registerUserUnSuccessfully_EmailNotProvided() {
		apiUtils.setHeaders(headers.getHeaders());
		apiUtils.post("api/register", DataUtils.getTestData("registerUser_UnSuccessfulBody_WithoutEmail"));
		if (!(apiUtils.getStatusCode() == HTTPStatus.BAD_REQUEST)) {
			Assert.assertEquals(apiUtils.getStatusCode(), HTTPStatus.BAD_REQUEST, "Status code is not as expected");
		}
		Assert.assertEquals(apiUtils.getValues("error"), DataUtils.getTestData("registerUser_MissingEmailError"),
				"API Response Body is not coming as expected");
	}

	@Test(priority = 3, description = "Verify User Registration when wrong body is provided")
	public void registerUserUnSuccessfully_WrongBody() {
		apiUtils.setHeaders(headers.getHeaders());
		apiUtils.post("api/register",
				DataUtils.getTestData("registerUser_UnSuccessfulBody_WithoutBody_NegativeUseCase"));
		if (!(apiUtils.getStatusCode() == HTTPStatus.BAD_REQUEST)) {
			Assert.assertEquals(apiUtils.getStatusCode(), HTTPStatus.BAD_REQUEST, "Status code is not as expected");
		}

	}
}
