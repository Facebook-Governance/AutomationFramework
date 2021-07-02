/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.testBase;



import java.io.FileNotFoundException;

import java.io.IOException;

import java.io.InputStream;

import java.util.Properties;



public class ReadProperty {



	public static Properties propFile=null;

    public static void loadPropertiesFile(String fileName) throws IOException{

        Properties prop=new Properties();

        InputStream inputStream=ReadProperty.class.getClassLoader().getResourceAsStream(fileName);

        if(inputStream !=null ){

            prop.load(inputStream);

        }

        else {

            throw new FileNotFoundException("property file " + fileName + " not found in the resource folder");

        }

        propFile=prop;

    }



    public static String getPropertyValue(String key){

        return propFile.getProperty(key);

    }

}

