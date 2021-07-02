/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.Util.pageprocessor;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ElementLocatorBean {

    @JsonProperty("Locator type")
    private String locatorType;

    @JsonProperty("value")
    private String value;

    @JsonProperty("Locator type")
    public String getLocatorType() {
        return locatorType;
    }

    @JsonProperty("Locator type")
    public void setLocatorType(String locatorType) {
        this.locatorType = locatorType;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

}

