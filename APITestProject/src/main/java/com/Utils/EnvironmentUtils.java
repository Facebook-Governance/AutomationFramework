package com.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;

public class EnvironmentUtils {
	static String projectDir = System.getProperty("user.dir");
	static Properties environmentProperties = new Properties();

	public static void LoadEnvironment(String environment) {

		File envFile = new File(
				projectDir + "/src/main/resources/Environment/Environment_" + environment + ".properties");
		try {
			FileInputStream fis = new FileInputStream(envFile);
			environmentProperties.load(fis);
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Assert.fail(e.getStackTrace().toString());
		}

	}

	public static String getEnvironmentData(String key) {
		return environmentProperties.getProperty(key);

	}

}
