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
 * This class validates types of Life insurances like if it is
 * TERM_LIFE_INSURANCE,ULIP,OTHER etc.
 * 
 * @author Rahul Kumar
 *
 */
public class LifeInsuranceType implements FormatValidator {
	static List<String> lifeInsuranceTypes = new ArrayList<String>();
	static {
		lifeInsuranceTypes.add("UNKNOWN");
		lifeInsuranceTypes.add("OTHER");
		lifeInsuranceTypes.add("TERM_LIFE_INSURANCE");
		lifeInsuranceTypes.add("UNIVERSAL_LIFE_INSURANCE");
		lifeInsuranceTypes.add("WHOLE_LIFE_INSURANCE");
		lifeInsuranceTypes.add("VARIABLE_LIFE_INSURANCE");
		lifeInsuranceTypes.add("ULIP");
	}

	@Override
	public Optional<String> validate(final String subject) {

		if (!lifeInsuranceTypes.contains(subject)) {
			return Optional.of(String.format("Invalid LifeInsurance Type " + subject, subject));
		} else {
			return Optional.empty();
		}

	}
}
