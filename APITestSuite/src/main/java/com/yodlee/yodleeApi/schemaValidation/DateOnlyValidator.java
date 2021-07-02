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

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.Optional;

import org.everit.json.schema.FormatValidator;

/**
 * This class validates format for Date only.
 * 
 * @author Rahul Kumar
 *
 */
public class DateOnlyValidator implements FormatValidator {

	String dateOnlyPattern = "yyyy-MM-dd";

	@Override
	public Optional<String> validate(String subject) {
		System.out.println("Entering the validator  " + subject);
		DateTimeFormatter fmt = new DateTimeFormatterBuilder().appendValue(ChronoField.YEAR, 4, 4, SignStyle.NEVER)
				.appendPattern("-MM-dd").toFormatter();
		try {
			fmt.parse(subject);
		} catch (Exception e) {
			System.out.println("Exception jj is:" + e.getMessage());
			return Optional.of(String.format("Invalid date format", subject));
		}
		return Optional.empty();
	}

}
