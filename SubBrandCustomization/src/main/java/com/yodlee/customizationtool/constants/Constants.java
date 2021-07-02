/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.yodlee.customizationtool.constants;

import java.io.File;

public class Constants {

    public static final String SCREENSHOT_DIR = System.getProperty("user.dir") + File.separator + "Screenshots";
    public static final String FAILED_SCREENSHOT_LOC = ".." + File.separator + "Screenshots";
    public static final String PAGE_HEADER_SUB_BRAND_MANAGEMENT = "Sub-brand Management";
    public static final String GENERAL_CONFIGURATION = "General Configuration";
    public static final String PUBLIC_AUTHENTICATION_SETUP = "Public Authentication Setup";
    public static final String PRIVATE_AUTHENTICATION_SETUP = "Private Authentication Setup";
    public static final String PRODUCTS_AND_APPS_SELECTION = "Select Products for ";
    public static final String CONTACT_DETAILS = "Contact Details";
    public static final String DEPLOYMENT_STATUS_PUBLISHED_IN_PUBLIC = "Published in Public";
    public static final String DEPLOYMENT_STATUS_PUBLISHED_IN_PRIVATE = "Published in Private";
    public static final String DEPLOYMENT_STATUS_PUBLISH_FAILED_IN_PUBLIC = "Publish failed in Public";
    public static final String DEPLOYMENT_STATUS_PUBLISH_FAILED_IN_PRIVATE = "Publish failed in Private";
    public static final String DEPLOYMENT_STATUS_PUBLISH_INITIATED_IN_PUBLIC = "Publish initiated in Public";
    public static final String DEPLOYMENT_STATUS_PUBLISH_INITIATED_IN_PRIVATE = "Publish initiated in Private";
    public static final String CUSTOMER_TYPE_CHANNEL_PARTNER = "Channel Partner";
    public static final String CUSTOMER_TYPE_SUB_BRAND = "Sub-brand";
    public static final String ENVIRONMENT_STATUS_PRIVATE = "Private";
    public static final String ENVIRONMENT_STATUS_PUBLIC = "Public";
    public static final String ACTIVE_STEP_GENERAL_CONFIGURATION = "General Configuration";
    public static final String ACTIVE_STEP_PUBLIC_SETUP = "Public Setup";
    public static final String ACTIVE_STEP_PRIVATE_SETUP = "Private Setup";
    public static final String ACTIVE_STEP_PRODUCTS_AND_APPS_SELECTION = "Products & Apps Selection";
    public static final String ACTIVE_STEP_CONTACT_DETAILS = "Contact Details";
    public static final String ACTIVE_STEP_REVIEW = "Review";
    public static final String EDIT_SUB_BRAND_HEADER = "Edit Sub-brand";
    public static final String CREATE_SUB_BRAND_HEADER = "Create Sub-brand";
    public static final String SELECT_APPLICATIONS_YODLEE_FASTLINK = "Yodlee FastLink";
    public static final String SELECT_APPLICATIONS_YODLEE_FINANCIAL_WELLNESS_FINAPPS = "Yodlee Financial Wellness FinApps";

}
