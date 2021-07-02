package com.omni.pfm.utility;
/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.  
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlurryUtil {
	public static final Logger logger = LoggerFactory.getLogger(FlurryUtil.class);

	/**
	 * Utility method to open a new flurry tracker tab and clear the tags
	 * 
	 * @param d
	 * @throws Exception
	 * @author sbhat1
	 */
	public static void setFlurryTracker(WebDriver d) throws Exception {
		((JavascriptExecutor) d).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(d.getWindowHandles());
		d.switchTo().window(tabs.get(1));
		d.get("https://finappwellnessl1.corp.yodlee.com/10000004-17CBE222A42161A3FF450E47CF4C1A00/track-events.html");
		SeleniumUtil.click(d.findElement(By.id("clear-all")));
		d.switchTo().window(tabs.get(0));

	}

	/**
	 * Generic method to navigate to 2nd tab opened in browser
	 * 
	 * @param d
	 */
	public static void navigateToFlurryTool(WebDriver d) {
		ArrayList<String> tabs = new ArrayList<String>(d.getWindowHandles());
		d.switchTo().window(tabs.get(1));
	}

	/**
	 * Generic method to navigate to 1st tab opened in browser
	 * 
	 * @param d
	 */
	public static void navigateToPFMPage(WebDriver d) {
		ArrayList<String> tabs = new ArrayList<String>(d.getWindowHandles());
		d.switchTo().window(tabs.get(0));
	}

	public ArrayList<String> readTestData(int sheetNumber) throws IOException {
		String filePath = "./src/main/resources/excel/flurry.xlsx";
		File file = new File(filePath);
		FileInputStream inputStream = new FileInputStream(file);
		Workbook wb = new XSSFWorkbook(inputStream);
		Sheet sheet = wb.getSheetAt(sheetNumber);

		ArrayList<String> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Row row = sheet.getRow(i);
			Cell cell = row.getCell(0);
			String cellValue = cell.getStringCellValue();
			list.add(cellValue);
		}
		return list;
	}
}
