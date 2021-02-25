package com.Update;

import java.text.ParseException;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.Utils.ApiUtils;
import com.Utils.BaseClass;
import com.Utils.DataUtils;
import com.Utils.DateUtils;
import com.Utils.EnvironmentUtils;
import com.Utils.HTTPStatus;
import com.Utils.Headers;

public class UpdateUser extends BaseClass {
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

	@Test(priority = 0, description = "Verify Create User Flow")
	public void updateUser() {
		apiUtils.setHeaders(headers.getHeaders());
		apiUtils.put("api/users/2", DataUtils.getTestData("putUpdateBody"));
		if (!(apiUtils.getStatusCode() == HTTPStatus.OK)) {
			Assert.assertEquals(apiUtils.getStatusCode(), HTTPStatus.OK, "Status code is not as expected");
		}
		Assert.assertEquals(apiUtils.getValues("name"), DataUtils.getTestData("updateUser_Name"),
				"Name is not coming as expected after creating a user");
		Assert.assertEquals(apiUtils.getValues("job"), DataUtils.getTestData("updateUser_Job"),
				"Job role is not coming as expected after creating a user");

	}

	@Test(priority = 1, description = "Verify Create User Flow")
	public void updateUserAndValidateUdpatedTime() throws ParseException {

		apiUtils.setHeaders(headers.getHeaders());
		Date d1Time = DateUtils.getThePresentUTCTime();

		apiUtils.put("api/users/2", DataUtils.getTestData("putUpdateBody"));

		String updatedDate = apiUtils.getValues("updatedAt");
		Date d2Time = DateUtils.getTheDateForGivenCreatedAtString(updatedDate);
		long differenceInMinutes = DateUtils.getTheDifferenceBetweenTheGivenDatesInMinutes(d2Time, d1Time);

		if (differenceInMinutes > 1) {
			Assert.fail("Created date is not coming properly. Called the API at :: " + d1Time
					+ " and created date coming in response is :: " + d2Time);
		}

	}

	@Test(priority = 2, description = "Verify negative use case when body of the API is wrong")
	public void updateUserNegativeUseCase() {

		apiUtils.setHeaders(headers.getHeaders());
		apiUtils.put("api/users/2", DataUtils.getTestData("updateUser_NegativeUseCase_WrongBody"));
		if (!(apiUtils.getStatusCode() == HTTPStatus.BAD_REQUEST)) {
			Assert.assertEquals(apiUtils.getStatusCode(), HTTPStatus.BAD_REQUEST, "Status code is not as expected");
		}
	}

	@Test(priority = 3, description = "Verify update user with only job update")
	public void updateOnlyJobInUserInfo() {

		apiUtils.setHeaders(headers.getHeaders());
		apiUtils.put("api/users/2", DataUtils.getTestData("updateUser_OnlyJobUpdated"));
		if (!(apiUtils.getStatusCode() == HTTPStatus.OK)) {
			Assert.assertEquals(apiUtils.getStatusCode(), HTTPStatus.OK, "Status code is not as expected");
		}

		Assert.assertTrue(apiUtils.getValues("name") == null);
		Assert.assertEquals(apiUtils.getValues("job"), DataUtils.getTestData("updateUser_Job"),
				"Job role is not coming as expected after creating a user");
	}

}
