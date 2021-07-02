	/*******************************************************************************
	 *
	 * * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
	 *
	 * *
	 *
	 * * This software is the confidential and proprietary information of Yodlee, Inc.
	 *
	 * * Use is subject to license terms.
	 *
	 *******************************************************************************/

package com.yodlee.yodleeApi.helper;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author bsivateja This helper class is used to get the request from YSL logs
 */

public class YslLogAnalysisHelper {

	

	public static String getYslRequestxml(String inputfile, String userid)
			throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(inputfile));

		String xml = "";
		String line = null;
		while ((line = in.readLine()) != null) {
			int idx = line.indexOf("<request>");

			if (idx > 0) {
				xml = line;

				if (xml.contains(userid)) {
					xml = line.substring(idx);

					int endIdx = xml.lastIndexOf("</request>");
					xml = xml.substring(0, endIdx) + "</request>";

					break;
				} else {
					xml = "";
				}
			}

		}
		in.close();

		xml = xml.trim();
		System.out.println(xml);

		return xml;
	}


	public static boolean verifyRequestXMLExists(String inputfile, String userid)
			throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(inputfile));
		boolean value = false;
		String xml = "";
		String line = null;
		while ((line = in.readLine()) != null) {

			if (line.contains("<request>")) {
				xml = line;

				if (xml.contains(userid)) {
					value = true;
					break;
				} else {
					value = false;
				}
			}

		}
		in.close();
		return value;
	}


		public static boolean createActXml(String str, String path) {
			BufferedWriter writer;
			try {

				writer = new BufferedWriter(new FileWriter(path));
				writer.write(str);
				writer.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				;
				return false;
			}

			return true;
		}

	}


