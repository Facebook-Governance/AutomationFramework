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
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.Xsoup;


public class XMLParser {

	final static int  DTOCOL=4;
	final static int  EXTRACOL=5;

	/**
	 * Purpose: To read all the links in a given html content
	 *  
	 * @param sHtml
	 * @throws IOException
	 */
	static String filepath = System.getProperty("user.dir") + File.separator
			+ "src" + File.separator + "test" + File.separator + "java"
			+ File.separator + "com" + File.separator + "snapdeal"
			+ File.separator + "data" + File.separator
			+ "Pipeline.xlsx";
	
	public static void readAllLinks(String sHtml) throws IOException{
		org.jsoup.nodes.Document doc = Jsoup.parse(sHtml);
		Elements links = doc.select("a[href]");
		Elements images = doc.select("img");

		for(org.jsoup.nodes.Element link : links){
			checkValidLink(link.attr("abs:href"));
		}
		for(org.jsoup.nodes.Element image : images){
			checkValidLink(image.attr("abs:src"));
		}
	}

	/**
	 * Purpose: To read text content within a give html code. Extracts data within <p></p> tags.
	 * 
	 * @param sHtml
	 * @throws IOException
	 */
	public static String readTextContent(String sHtml) throws IOException{

		org.jsoup.nodes.Document doc = Jsoup.parse(sHtml); 
		Elements paraContents = doc.select("div");
		String textContent = "";
		for(org.jsoup.nodes.Element para:paraContents)
			textContent = textContent + "\n" + para.text();
		return textContent;
	
	}
	
	/**
	 * Purpose: To retrieve content using xpath
	 * 
	 * @param sHtml
	 * @throws IOException
	 */
	public static String retrieveTextContent(String sHtml,String xpath,String searchContent) throws IOException{

		org.jsoup.nodes.Document doc = Jsoup.parse(sHtml);
		Elements result = Xsoup.compile(xpath).evaluate(doc).getElements();
		
		//return result.get(0).attr("href"); // Old method
		//return result.get(0).text(); //New 
		
		if (searchContent.equalsIgnoreCase("OTP")) {
			return result.get(0).text(); // For OTP
		} else if(searchContent.equalsIgnoreCase("LINK")) {
			return result.get(0).attr("href").trim(); //Added by Ashwin PM, as the new emails does not contain link!
		}else {
			return null;
		}
	}

	/**
	 * Purpose: returns the HTTP reponse code for the provided link. (To verify whether link is active).
	 * @param sLink
	 * @return
	 * @throws IOException
	 */
	public static int checkValidLink(String sLink) throws IOException{
		int iResponseCode = 0;
		URL url = new URL(sLink);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.connect();
		iResponseCode = con.getResponseCode();
		System.out.println("Respons Code" + iResponseCode + " " + sLink);
		con.disconnect();
		return iResponseCode;
	}


	public static String replaceInTemplate(Map<String, String> map, String target){

		Set<String> keySet= map.keySet();
		for (String key : keySet) {

			String pattern3="[$][{][a-zA-Z]{1,}?.("+key+")[}]";
			String pattern4="[$][{]("+key+")[}]";
			target=target.replaceAll(pattern3, map.get(key));
			target=target.replaceAll(pattern4, map.get(key));
		}
		return target;
	}
}