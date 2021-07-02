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
 * This class validates locale like en_DE,en_CA,en_PG etc.
 * 
 * @author Rahul Kumar
 *
 */
public class EnumLocale implements FormatValidator {
	static List<String> locale = new ArrayList<String>();

	static {
		locale.add("en_DE");
		locale.add("en_PT");
		locale.add("en_GU");
		locale.add("fr_CA");
		locale.add("en_ZA");
		locale.add("en_AN");
		locale.add("en_BE");
		locale.add("zh_CN");
		locale.add("en_FI");
		locale.add("es_MX");
		locale.add("en_CA");
		locale.add("en_KR");
		locale.add("en_PG");
		locale.add("nl_AN");
		locale.add("en_US");
		locale.add("de_DE");
		locale.add("en_ID");
		locale.add("en_SE");
		locale.add("pt_BR");
		locale.add("en_FJ");
		locale.add("en_HK");
		locale.add("en_JP");
		locale.add("en_KH");
		locale.add("en_TO");
		locale.add("nl_NL");
		locale.add("en_BM");
		locale.add("en_CO");
		locale.add("en_IN");
		locale.add("en_VN");
		locale.add("en_VU");
		locale.add("es_ES");
		locale.add("en_SZ");
		locale.add("en_BR");
		locale.add("en_IE");
		locale.add("en_IT");
		locale.add("en_SG");
		locale.add("en_MW");
		locale.add("en_KE");
		locale.add("en_NL");
		locale.add("en_ES");
		locale.add("en_AE");
		locale.add("en_SB");
		locale.add("en_TH");
		locale.add("fr_FR");
		locale.add("en_CN");
		locale.add("en_JE");
		locale.add("en_BW");
		locale.add("en_AU");
		locale.add("en_CK");
		locale.add("de_AT");
		locale.add("de_RU");
		locale.add("en_MX");
		locale.add("en_DK");
		locale.add("en_NZ");
		locale.add("en_KP");
		locale.add("en_PH");
		locale.add("en_GB");
		locale.add("en_AT");
		locale.add("en_GLOBAL");
		locale.add("en_IL");
		locale.add("en_FR");
		locale.add("en_MY");
		locale.add("en_WS");
		locale.add("en_CH");
		locale.add("en_ZW");
		locale.add("en_BG");

	}

	@Override
	public Optional<String> validate(final String subject) {
		if (!locale.contains(subject)) {
			return Optional.of(String.format("Invalid locale Type " + subject, subject));
		} else {
			return Optional.empty();
		}

	}
}
