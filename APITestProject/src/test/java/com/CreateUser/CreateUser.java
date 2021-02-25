package com.CreateUser;

import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class CreateUser extends BaseClass {

	protected Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	Headers headers;
	ApiUtils apiUtils;

	@BeforeTest
	public void beforeTest() {
		logger.info("In Create User class, Setting headers and calling Base URI from Environment.properties");

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
	public void createUserSuccessfully() throws ParseException {
		logger.info("------ Execution Started for Create User----------");
		apiUtils.setHeaders(headers.getHeaders());
		Date d1Time = DateUtils.getThePresentUTCTime();

		apiUtils.post("api/users", DataUtils.getTestData("createUser_APIBody"));
		if (!(apiUtils.getStatusCode() == HTTPStatus.CREATED)) {
			Assert.assertEquals(apiUtils.getStatusCode(), HTTPStatus.CREATED, "Status code is not as expected");
		}
		Assert.assertEquals(apiUtils.getValues("name"), DataUtils.getTestData("createUser_UserName"),
				"Name is not coming as expected after creating a user");
		Assert.assertEquals(apiUtils.getValues("job"), DataUtils.getTestData("createUser_Job"),
				"Job role is not coming as expected after creating a user");
		String id = apiUtils.getValues("id");
		if (id.isEmpty() || id == null) {
			Assert.fail("Id is not present in the API respone body");
		} else {
			try {
				int idvalue = Integer.parseInt(id);
				if (idvalue == 0) {
					Assert.fail("value of ID is coming as 0 (Zero)");
				}
			} catch (Exception e) {
				Assert.fail("Id is not an integer value");
			}
		}
		String createdDate = apiUtils.getValues("createdAt");
		Date d2Time = DateUtils.getTheDateForGivenCreatedAtString(createdDate);
		long differenceInMinutes = DateUtils.getTheDifferenceBetweenTheGivenDatesInMinutes(d2Time, d1Time);

		if (differenceInMinutes > 1) {
			Assert.fail("Created date is not coming properly. Called the API at :: " + d1Time
					+ " and created date coming in response is :: " + d2Time);
		}
	}

	@Test(priority = 1, description = "Verify Un-successful create user flow when correct body is not provided")
	public void createUserUnSuccessfully_BodyNotProvided() {
		logger.info("Executing Unsuccessful request");
		apiUtils.setHeaders(headers.getHeaders());
		apiUtils.post("api/register", DataUtils.getTestData("createUser_UnSuccessfulBody_WrongBody_NegativeUseCase"));
		if (!(apiUtils.getStatusCode() == HTTPStatus.BAD_REQUEST)) {
			Assert.assertEquals(apiUtils.getStatusCode(), HTTPStatus.BAD_REQUEST, "Status code is not as expected");
		}
	}

}
