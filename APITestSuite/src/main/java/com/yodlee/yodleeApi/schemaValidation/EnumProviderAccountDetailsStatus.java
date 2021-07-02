/*******************************************************************************
 *
 * * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/

package com.yodlee.yodleeApi.schemaValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.everit.json.schema.FormatValidator;

/**
 * This class validates Additional Status of a Provider account i.e. if it is
 * LOGIN_IN_PROGRESS, LOGIN_SUCCESS, REQUEST_TIME_OUT etc.
 * 
 * @author Rahul Kumar
 *
 */
public class EnumProviderAccountDetailsStatus implements FormatValidator {

	static List<String> providerAccAdditionalStatus = new ArrayList<String>();

	static {
		providerAccAdditionalStatus.add("LOGIN_IN_PROGRESS");
		providerAccAdditionalStatus.add("USER_INPUT_REQUIRED");
		providerAccAdditionalStatus.add("LOGIN_SUCCESS");
		providerAccAdditionalStatus.add("ACCOUNT_SUMMARY_RETRIEVED");
		providerAccAdditionalStatus.add("NEVER_INITIATED");
		providerAccAdditionalStatus.add("LOGIN_FAILED");
		providerAccAdditionalStatus.add("REQUEST_TIME_OUT");
		providerAccAdditionalStatus.add("PARTIAL_DATA_RETRIEVED");
		providerAccAdditionalStatus.add("PARTIAL_DATA_RETRIEVED_REM_SCHED");

	}

	@Override
	public Optional<String> validate(final String subject) {

		if (!providerAccAdditionalStatus.contains(subject)) {
			return Optional.of(String.format("Invalid Provider Account Additional Status " + subject, subject));
		} else {
			return Optional.empty();
		}
	}

}
