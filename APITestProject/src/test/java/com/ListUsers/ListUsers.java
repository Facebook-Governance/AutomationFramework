package com.ListUsers;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.Utils.ApiUtils;
import com.Utils.BaseClass;
import com.Utils.DataUtils;
import com.Utils.EnvironmentUtils;
import com.Utils.HTTPStatus;
import com.Utils.Headers;

public class ListUsers extends BaseClass {
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

	@Test(priority = 0, description = "Verify get information about Single List User")
	public void listSingleUserInformation() {
		apiUtils.setHeaders(headers.getHeaders());
		apiUtils.get("api/users/" + DataUtils.getTestData("listUser_listSingleUserId"));
		if (!(apiUtils.getStatusCode() == HTTPStatus.OK)) {
			Assert.assertEquals(apiUtils.getStatusCode(), HTTPStatus.OK, "Status code is not as expected");
		}

		Assert.assertEquals(apiUtils.getIDValues("data.id"),
				Integer.parseInt(DataUtils.getTestData("listUser_listSingleUserId")),
				"ID is not coming as expected after creating a user");

	}

	@Test(priority = 1, description = "Verify get information about Single List all users for different Pages, :: here Page 1")
	public void listAllUsersInformaitonPageOne() {
		apiUtils.setHeaders(headers.getHeaders());
		apiUtils.get("api/users/" + DataUtils.getTestData("listAllUsers_Page1"));
		if (!(apiUtils.getStatusCode() == HTTPStatus.OK)) {
			Assert.assertEquals(apiUtils.getStatusCode(), HTTPStatus.OK, "Status code is not as expected");
		}

		Assert.assertEquals(apiUtils.getIDValues("data.id"),
				Integer.parseInt(DataUtils.getTestData("listAllUsers_Page1")),
				"ID is not coming as expected after creating a user");

	}

	@Test(priority = 2, description = "Verify get information about Single List all users for different Pages, :: here Page 1")
	public void listAllUsersInformaitonPageTwo() {
		apiUtils.setHeaders(headers.getHeaders());
		apiUtils.get("api/users/" + DataUtils.getTestData("listAllUsers_Page2"));

		if (!(apiUtils.getStatusCode() == HTTPStatus.OK)) {
			Assert.assertEquals(apiUtils.getStatusCode(), HTTPStatus.OK, "Status code is not as expected");
		}

		Assert.assertEquals(apiUtils.getArrayOfIDValues("data.id").toString(),
				(DataUtils.getTestData("listAllUsers_PageObjectsID")),
				"ID is not coming as expected after creating a user");

	}

}
