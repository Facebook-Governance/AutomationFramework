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
 * This class validates status of transactions i.e. if it is MERGED, POSTED,
 * PENDING,FAILED etc.
 * 
 * @author Rahul Kumar
 *
 */
public class EnumTransactionStatus implements FormatValidator {

	static List<String> transactionStatus = new ArrayList<String>();
	static {
		transactionStatus.add("MERGED");
		transactionStatus.add("POSTED");
		transactionStatus.add("PENDING");
		transactionStatus.add("SCHEDULED");
		transactionStatus.add("IN_PROGRESS");
		transactionStatus.add("FAILED");
		transactionStatus.add("CLEARED");
		transactionStatus.add("DISBURSED");

	}

	@Override
	public Optional<String> validate(final String subject) {

		if (!transactionStatus.contains(subject)) {
			return Optional.of(String.format("Invalid Transaction Status " + subject, subject));
		} else {
			return Optional.empty();
		}

	}

}
