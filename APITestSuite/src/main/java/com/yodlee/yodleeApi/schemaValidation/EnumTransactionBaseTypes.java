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
 * This class validates Transactions base Types i.e if it is DEBIT, CREDIT,
 * UNKNOWN and OTHER.
 * 
 * @author Rahul Kumar
 *
 */
public class EnumTransactionBaseTypes implements FormatValidator {

	static List<String> transactionBaseType = new ArrayList<String>();
	static {
		transactionBaseType.add("DEBIT");
		transactionBaseType.add("CREDIT");
		transactionBaseType.add("UNKNOWN");
		transactionBaseType.add("OTHER");

	}

	@Override
	public Optional<String> validate(final String subject) {

		if (!transactionBaseType.contains(subject)) {
			return Optional.of(String.format("Invalid Transaction Base TYpe " + subject, subject));
		} else {
			return Optional.empty();
		}

	}

}
