/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.yodlee.customizationtool.pageprocessor;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageBean {

    @JsonProperty("elements")
    private HashMap<String,ElementLocatorBean> elements;

    public HashMap<String, ElementLocatorBean> getElements() {
        return elements;
    }


}