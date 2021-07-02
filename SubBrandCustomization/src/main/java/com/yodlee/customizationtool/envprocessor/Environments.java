/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.yodlee.customizationtool.envprocessor;

import java.util.HashMap;

public class Environments {

    private HashMap<String,Environment> environments;

    public HashMap<String, Environment> getEnvironments() {
        return environments;
    }

    public void setEnvironments(HashMap<String, Environment> environments) {
        this.environments = environments;
    }
}
