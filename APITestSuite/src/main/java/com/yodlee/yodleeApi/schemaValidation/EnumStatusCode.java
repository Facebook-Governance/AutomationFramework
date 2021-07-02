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
 * This class validates status code values i.e. if it is 801,802,401,403 etc.
 * 
 * @author Rahul Kumar
 *
 */
public class EnumStatusCode implements FormatValidator {

	static List<Integer> statusCode = new ArrayList<Integer>();

	static {
		statusCode.add(0);
		statusCode.add(801);
		statusCode.add(802);
		statusCode.add(504);
		statusCode.add(811);
		statusCode.add(401);
		statusCode.add(403);
		statusCode.add(404);
		statusCode.add(408);
		statusCode.add(413);
		statusCode.add(415);
		statusCode.add(419);
		statusCode.add(475);
		statusCode.add(507);
		statusCode.add(508);
		statusCode.add(509);
		statusCode.add(517);
		statusCode.add(525);
		statusCode.add(709);
		statusCode.add(601);
		statusCode.add(409);
		statusCode.add(411);
		statusCode.add(412);
		statusCode.add(416);
		statusCode.add(418);
		statusCode.add(423);
		statusCode.add(424);
		statusCode.add(425);
		statusCode.add(426);
		statusCode.add(402);
		statusCode.add(505);
		statusCode.add(406);
		statusCode.add(407);
		statusCode.add(414);
		statusCode.add(417);
		statusCode.add(420);
		statusCode.add(421);
		statusCode.add(422);
		statusCode.add(427);
		statusCode.add(428);
		statusCode.add(429);
		statusCode.add(430);
		statusCode.add(506);
		statusCode.add(518);
		statusCode.add(519);
		statusCode.add(520);
		statusCode.add(521);
		statusCode.add(522);
		statusCode.add(523);
		statusCode.add(524);
		statusCode.add(526);

	}

	@Override
	public Optional<String> validate(final String subject) {
		if (!statusCode.contains(Integer.parseInt(subject))) {
			return Optional.of(String.format("Invalid Provider Account Status " + subject, subject));
		} else {
			return Optional.empty();
		}
	}
}
