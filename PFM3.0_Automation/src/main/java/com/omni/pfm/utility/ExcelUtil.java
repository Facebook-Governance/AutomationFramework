/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Suhaib
 *
 */
public class ExcelUtil {

	private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	static List<Map<String, String>> readExcel(String fileName) {
		logger.info("==> Entering ExcelUtil.readExcel method.");
		Map<String, String> testMethod = new LinkedHashMap<String, String>();
		Map<String, String> parameters = new HashMap<String, String>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		InputStream in = null;
		Workbook workbook;
		Sheet sheet;
		logger.info("Excel File : {}",fileName);
		try {
			File f = new File("C:\\"+fileName);
			in = new FileInputStream(f);
			logger.info("Excel file path : {}",ExcelUtil.class.getClassLoader().getResource(fileName));
			workbook = new XSSFWorkbook(in);
			sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();

			boolean endOfParamsFlag = false;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
						continue;
					}
					else if("Test_Scenario".equalsIgnoreCase(cell.getStringCellValue())){
						endOfParamsFlag = true;
					}
					else if(!endOfParamsFlag){
						Cell paramVal = cellIterator.next();
						String value = "";
						if(paramVal.getCellType()==Cell.CELL_TYPE_BOOLEAN){
							value = String.valueOf(paramVal.getBooleanCellValue());
						}
						else if(paramVal.getCellType()==Cell.CELL_TYPE_NUMERIC){
							value = String.valueOf(paramVal.getNumericCellValue());
						}
						else if(paramVal.getCellType()==Cell.CELL_TYPE_STRING){
							value = paramVal.getStringCellValue();
						}
						else
							continue;
						parameters.put(cell.getStringCellValue(), value);
					}
				}
				if (endOfParamsFlag)
					break;
			}
			while(rowIterator.hasNext()){
				Row row = rowIterator.next();
				if(row.getCell(5).getStringCellValue().equalsIgnoreCase("y")){
					testMethod.put(row.getCell(2).getStringCellValue(), row
							.getCell(5).getStringCellValue());
				}
			}
		/*	for (int i = 5; i <= rowNum; i++) {
				if (sheet.getRow(i).getCell(5).getStringCellValue().equalsIgnoreCase("y")) {
					testMethod.put(sheet.getRow(i).getCell(2).getStringCellValue(), sheet.getRow(i)
							.getCell(5).getStringCellValue());
				}
				System.out.println(sheet.getRow(i).getCell(2).getStringCellValue() + "--"
						+ sheet.getRow(i).getCell(5).getStringCellValue());
			}*/
			System.out.println(testMethod);
			System.out.println(parameters);
			list.add(parameters);
			list.add(testMethod);
		} catch (FileNotFoundException e) {
			logger.error("File Not found - {}",fileName);
			logger.error("Full Stack trace - {}",e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Cannot open file : {}", fileName.toString());
			logger.error("Full Stack Trace - {}",e);
			e.printStackTrace();
		} finally {
			try {
				logger.info("Closing Excel file...");
				in.close();
				logger.info("Excel file closed successfully...");
			} catch (IOException e) {
				logger.error("Error closing excel file Stream : {}", e);
				e.printStackTrace();
			}
		}
		logger.info("<== Exiting readExcel method.");
		return list;
	}

	public static void main(String[] args) {
		// InputStream in =
		// ExcelUtil.class.getClassLoader().getResourceAsStream("smok.xlsx");
		List<Map<String, String>> data = readExcel("smok.xlsx");
		Map<String,String> params = data.get(0);
		Map<String,String> m = data.get(1);
		for(Map.Entry<String,String> entry : params.entrySet()){
			System.out.println(entry.getKey()+" = "+entry.getValue());
		}
		System.out.println("----------------------------------------------------");
		for(Map.Entry<String,String> entry : m.entrySet()){
			System.out.println(entry.getKey()+" = "+entry.getValue());
		}
	}
}
