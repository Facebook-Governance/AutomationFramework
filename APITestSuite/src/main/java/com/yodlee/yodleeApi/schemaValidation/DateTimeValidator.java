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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yodlee.yodleeApi.schemaValidation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.everit.json.schema.FormatValidator;

/**
 * This class validates format for both Date and Time.
 * 
 * @author Minu and Rahul Kumar
 */
public class DateTimeValidator implements FormatValidator {
	String datePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	@Override
	public Optional<String> validate(final String subject) {
		System.out.println("Entering the validator  " + subject);
		SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

		try {
			dateFormat.parse(subject);
		} catch (ParseException e) {
			System.out.println("Exception is:" + e.getMessage());
			return Optional.of(String.format("Invalid date format", subject));
		}
		return Optional.empty();
	}

}