package com.DeleteUser;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.Utils.ApiUtils;
import com.Utils.BaseClass;
import com.Utils.EnvironmentUtils;
import com.Utils.HTTPStatus;
import com.Utils.Headers;

public class DeleteUser extends BaseClass {
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

	@Test(priority = 0, description = "Verify Delete User Flow")
	public void deleteUser1() {
		apiUtils.setHeaders(headers.getHeaders());

		apiUtils.delete("api/users1");
		if (!(apiUtils.getStatusCode() == HTTPStatus.ACCEPTED)) {
			Assert.assertEquals(apiUtils.getStatusCode(), HTTPStatus.ACCEPTED, "Status code is not as expected");
		}

	}
	@Test(priority = 1, description = "Verify Delete User Flow")
	public void deleteUser2() {
		apiUtils.setHeaders(headers.getHeaders());

		apiUtils.delete("api/users2");
		if (!(apiUtils.getStatusCode() == HTTPStatus.ACCEPTED)) {
			Assert.assertEquals(apiUtils.getStatusCode(), HTTPStatus.ACCEPTED, "Status code is not as expected");
		}

	}

}
