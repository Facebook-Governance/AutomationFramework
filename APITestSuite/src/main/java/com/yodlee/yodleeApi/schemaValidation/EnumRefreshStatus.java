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
 * This class validates Refresh status i.e. if it is
 * SUCCESS,UPDATE_IN_PROGRESS,DATA_EXPECTED, MULTIPLE_ERROR etc.
 * 
 * @author Rahul Kumar
 *
 */
public class EnumRefreshStatus implements FormatValidator {

	static List<String> refreshStatusMessage = new ArrayList<String>();
	static {
		refreshStatusMessage.add("SUCCESS");
		refreshStatusMessage.add("ADD_IN_PROGRESS");
		refreshStatusMessage.add("UPDATE_IN_PROGRESS");
		refreshStatusMessage.add("MULTIPLE_ERROR");
		refreshStatusMessage.add("PARTIAL_SUCCESS");
		refreshStatusMessage.add("NO_CONNECTION");
		refreshStatusMessage.add("INTERNAL_ERROR");
		refreshStatusMessage.add("LOST_REQUEST");
		refreshStatusMessage.add("DATA_EXPECTED");
		refreshStatusMessage.add("REQUIRED_FIELD_UNAVAILABLE");
		refreshStatusMessage.add("STATUS_SITE_TERMINATED_SESSION");
		refreshStatusMessage.add("LOGIN_NOT_COMPLETED");
		refreshStatusMessage.add("UNIQUEID_FROM_DATA_SOURCE_ERROR");
		refreshStatusMessage.add("BETA_SITE_WORK_IN_PROGRESS");
		refreshStatusMessage.add("INSTANT_REQUEST_TIMEDOUT");
		refreshStatusMessage.add("TOKEN_ID_INVALID");
		refreshStatusMessage.add("GENERAL_EXCEPTION_WHILE_GATHERING_MFA_DATA");
		refreshStatusMessage.add("MFA_INFO_NOT_PROVIDED_IN_REAL_TIME_BY_GATHERER");
		refreshStatusMessage.add("STATUS_FIELD_NOT_AVAILABLE");
		refreshStatusMessage.add("DBFILER_SUMMARY_SAVE_ERROR");
		refreshStatusMessage.add("STATUS_SITE_UNAVAILABLE");
		refreshStatusMessage.add("STATUS_SITE_OUT_OF_BUSINESS");
		refreshStatusMessage.add("STATUS_SITE_APPLICATION_ERROR");
		refreshStatusMessage.add("STATUS_SITE_SESSION_ALREADY_ESTABLISHED");
		refreshStatusMessage.add("STATUS_HTTP_DNS_ERROR_EXCEPTION");
		refreshStatusMessage.add("STATUS_ACCT_INFO_UNAVAILABLE");
		refreshStatusMessage.add("STATUS_SITE_DOWN_FOR_MAINTENANCE");
		refreshStatusMessage.add("STATUS_CERTIFICATE_ERROR");
		refreshStatusMessage.add("STATUS_SITE_BLOCKING");
		refreshStatusMessage.add("STATUS_SITE_CURRENTLY_NOT_SUPPORTED");
		refreshStatusMessage.add("STATUS_LOGIN_FAILED");
		refreshStatusMessage.add("STATUS_PASSWORD_EXPIRED");
		refreshStatusMessage.add("STATUS_ACCOUNT_LOCKED");
		refreshStatusMessage.add("STATUS_NO_ACCT_FOUND");
		refreshStatusMessage.add("STATUS_DATA_MODEL_NO_SUPPORT");
		refreshStatusMessage.add("STATUS_SITE_MERGED_ERROR");
		refreshStatusMessage.add("STATUS_UNSUPPORTED_LANGUAGE_ERROR");
		refreshStatusMessage.add("STATUS_ACCOUNT_CANCELED");
		refreshStatusMessage.add("STATUS_SPLASH_PAGE_EXCEPTION");
		refreshStatusMessage.add("STATUS_TERMS_AND_CONDITIONS_EXCEPTION");
		refreshStatusMessage.add("STATUS_UPDATE_INFORMATION_EXCEPTION");
		refreshStatusMessage.add("STATUS_SITE_NOT_SUPPORTED");
		refreshStatusMessage.add("NEW_LOGIN_INFO_REQUIRED_FOR_SITE");
		refreshStatusMessage.add("NEW_MFA_INFO_REQUIRED_FOR_AGENTS");
		refreshStatusMessage.add("MFA_INFO_NOT_PROVIDED_TO_YODLEE_BY_USER_FOR_AGENTS");
		refreshStatusMessage.add("MFA_INFO_MISMATCH_FOR_AGENTS");
		refreshStatusMessage.add("ENROLL_IN_MFA_AT_SITE");
		refreshStatusMessage.add("MFA_INFO_NOT_PROVIDED_IN_REAL_TIME_BY_USER_VIA_APP");
		refreshStatusMessage.add("INVALID_MFA_INFO_IN_REAL_TIME_BY_USER_VIA_APP");
		refreshStatusMessage.add("USER_PROVIDED_REAL_TIME_MFA_DATA_EXPIRED");
		refreshStatusMessage.add("OK");
	}

	@Override
	public Optional<String> validate(final String subject) {

		if (!refreshStatusMessage.contains(subject)) {
			return Optional.of(String.format("Invalid Provider refreshStatusMessage" + subject, subject));
		} else {
			return Optional.empty();
		}
	}
}
