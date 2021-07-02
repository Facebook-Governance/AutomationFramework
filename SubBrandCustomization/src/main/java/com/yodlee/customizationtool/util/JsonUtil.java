/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.yodlee.customizationtool.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yodlee.customizationtool.envprocessor.Environments;
import com.yodlee.customizationtool.pageprocessor.Config;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtil {

    public static <T> T getObject(Class<T> classType, String path){

        T obj = null;
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;
        InputStream in = Config.class.getClassLoader().getResourceAsStream(path);

        try {
            obj = mapper.readValue(in,classType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public static void main(String[] args) {
       Environments envs = getObject(Environments.class,".\\environment\\Environment.json");
        System.out.println(envs.getEnvironments().get("L1").getBaseUrl());
    }
}
