package com.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Test {
	public static void main(String[] args) {
		try {
			System.out.println(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2021-02-23T08:00:38.081Z"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 
		}
	}
}
