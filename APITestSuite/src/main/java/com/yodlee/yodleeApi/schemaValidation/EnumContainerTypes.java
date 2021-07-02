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
 * This class validates type of containers i.e. for bank, bill,
 * investment,loan,creditCard,reward.
 * 
 * @author Rahul Kumar
 *
 */
public class EnumContainerTypes implements FormatValidator {

	static List<String> containerType = new ArrayList<String>();

	static {
		containerType.add("bank");
		containerType.add("bill");
		containerType.add("insurance");
		containerType.add("investment");
		containerType.add("loan");
		containerType.add("creditCard");
		containerType.add("reward");
	}

	@Override
	public Optional<String> validate(final String subject) {

		if (!containerType.contains(subject)) {
			return Optional.of(String.format("Invalid Container::" + subject, subject));
		} else {
			return Optional.empty();
		}
	}

}
