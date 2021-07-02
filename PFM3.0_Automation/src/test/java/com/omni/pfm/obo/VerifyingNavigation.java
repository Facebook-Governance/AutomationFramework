/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.obo;

import java.util.ArrayList;

import org.testng.annotations.Test;

import com.omni.pfm.testBase.TestBase;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.opencsv.CSVReader;

public class VerifyingNavigation extends TestBase{
	Logger logger=LoggerFactory.getLogger(TransactionsAndNetworth.class);
	OBO_Loc oboLoc;
	String fileName=System.getProperty("user.dir")+File.separator+"downloads"+File.separator+"download.csv";
	
	@BeforeClass()
	public void init() throws Exception
	{
		doInitialization("OBO");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		oboLoc=new OBO_Loc(d);
	}
	

	@Test(description="Verifying Navigations expected and actual.", priority = 11, enabled = true)
	public void verifyingNavigations() throws Exception
	{
		
		oboLoc.deletePreviousdownloadCSV(fileName);
		SeleniumUtil.SwitchToTab(d, "YCCTab");
		String customerName=oboLoc.customerName().getText().trim();
		SeleniumUtil.click(oboLoc.auditLog());

		oboLoc.customerNameTxtBox().sendKeys(customerName);
		SeleniumUtil.click(oboLoc.searchButton());
		SeleniumUtil.click(oboLoc.exportListTab()); 
		SeleniumUtil.waitForPageToLoad(3000);
		
		ArrayList<String> actual = new ArrayList<String>();    

		try {

			String fileName2=System.getProperty("user.dir")+File.separator+"downloads"+File.separator+"download.csv";
			CSVReader cs= new CSVReader(new FileReader(fileName2),',');
			List<String[]> readAll = cs.readAll();
			for(int i=1; i<readAll.size();i++)
			{
				String[] strings = readAll.get(i);
				actual.add(strings[1]);
			}
			System.out.println(actual.size());

		} catch (IOException e) {
			e.printStackTrace();
		}

		String expected[]=PropsUtil.getDataPropertyValue("ExpectedNavigations").split(",");
		for(int i=0;i<expected.length;i++) {
		System.out.println(expected[i]);
		System.out.println(actual);
		Assert.assertTrue(actual.contains(expected[i]));
		}
		
	}
}
