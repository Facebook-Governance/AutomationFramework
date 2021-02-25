package com.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DataUtils {
	static String projectDir = System.getProperty("user.dir");
	static Properties dataProperties = new Properties();

	public static void LoadData(String environment){

		FileInputStream fis=null;
		File dataFile = new File(projectDir + "/src/main/resources/Data/Data_" + environment + ".properties");
		try {
		fis = new FileInputStream(dataFile);
			dataProperties.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		finally {
			try {
				fis.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

	public static String getTestData(String key) {
	return dataProperties.getProperty(key);

	}

}
