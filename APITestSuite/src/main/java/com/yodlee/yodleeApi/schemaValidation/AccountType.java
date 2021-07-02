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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.everit.json.schema.FormatValidator;

/**
 * @author Rahul Kumar
 *
 */
public class AccountType implements FormatValidator {
	
    static List<String> accountType = new ArrayList<String>();
    static String type = "";

    static {
        File file = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\TestData\\schemaValidation\\accountType.txt");
        try {
            type = FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        accountType= Arrays.asList(type.split("\\r?\\n"));
    }

    @Override
    public Optional<String> validate(final String subject) {


        if (!accountType.contains(subject)) {
            return Optional.of(String.format("Invalid Account Type " + subject, subject));
        } else {
            return Optional.empty();
        }


    }
}
