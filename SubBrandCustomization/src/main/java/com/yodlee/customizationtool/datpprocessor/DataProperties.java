/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.yodlee.customizationtool.datpprocessor;

import java.util.HashMap;

public class DataProperties {

    private HashMap<String, DataProperty> dataProperties;

    public HashMap<String, DataProperty> getDataProperties() {
        return dataProperties;
    }

    public void setDataProperties(HashMap<String, DataProperty> dataProperties) {
        this.dataProperties = dataProperties;
    }
}
