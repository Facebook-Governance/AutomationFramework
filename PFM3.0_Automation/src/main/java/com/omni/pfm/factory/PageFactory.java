/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.omni.pfm.annotations.Find;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class PageFactory {
	private static Logger logger = LoggerFactory.getLogger(PageFactory.class);

	@SuppressWarnings("unused")
	public static void initalize(WebDriver d, Class<?> c) {
		logger.info("==> Entering PageFactory.initialize()");
		logger.info("Initializing [{}] class", c.getName());
		if (d == null) {
			logger.error("WebDriver instance is null... Returning back from method");
		} else if (c == null) {
			logger.error("Class value is null : {}", c);
		}
		else{
		Class<?> o = c;

		for (Field f : o.getDeclaredFields()) {
			if (f.isAnnotationPresent(Find.class)) {
				Annotation anno = f.getAnnotation(Find.class);
				Find find = (Find) anno;
				logger.info("Found label = [{}] and page = [{}] on [{}] field", find.label(),
						find.page(), f.getName());
				WebElement value = SeleniumUtil.getWebElement(d, find.label(), find.page(), null);

				if (value != null) {
					try {
						f.setAccessible(true);
						f.set(o, value);
					} catch (IllegalAccessException e) {
						logger.error("Error while setting the value of object - {}", e);
						throw new RuntimeException(e);
					}
				}

				else {
					logger.error("Not able to get the element : Label = [{}] && Field Name = [{}]",
							find.label(), f.getName());
				}
			}
		}
		}
		logger.info("<== Exiting PageFactory.initialize()");
	}
	
	@SuppressWarnings("unused")
	public static void initalize(WebDriver d, Class<?> c, String pageName) {
		logger.info("==> Entering PageFactory.initialize()");
		logger.info("Initializing [{}] class", c.getName());
		if (d == null) {
			logger.error("WebDriver instance is null... Returning back from method");
		} else if (c == null) {
			logger.error("Class value is null : {}", c);
		}
		else if(GenericUtil.isNull(pageName)){
			logger.error("Page Name is null or empty.");
		}
		else{
		Class<?> o = c;

		for (Field f : o.getDeclaredFields()) {
			if (f.isAnnotationPresent(Find.class)) {
				Annotation anno = f.getAnnotation(Find.class);
				Find find = (Find) anno;
				logger.info("Found label = [{}] and page = [{}] on [{}] field", find.label(),
						find.page(), f.getName());
				WebElement value = SeleniumUtil.getWebElement(d, find.label(), pageName, null);

				if (value != null) {
					try {
						f.setAccessible(true);
						f.set(o, value);
					} catch (IllegalAccessException e) {
						logger.error("Error while setting the value of object - {}", e);
						throw new RuntimeException(e);
					}
				}

				else {
					logger.error("Not able to get the element : Label = [{}] && Field Name = [{}]",
							find.label(), f.getName());
				}
			}
		}
		}
		logger.info("<== Exiting PageFactory.initialize()");
	}
	
	public static List<String[]> test(Class c, String methodName) throws NoSuchMethodException, SecurityException{
		Method m = null;//c.getClass().getDeclaredMethod(methodName);
		List<String[]> al=new LinkedList<String[]>();
		for(Method z : c.getDeclaredMethods()){
			System.out.println(z.getName());
			if(methodName.equals(z.getName())){
				m = z;
			}
		}
		if(m==null)
		{
			logger.info("Did not find valid method name [{}] inside the class [{}]",methodName,c);
		}
		else if(m.isAnnotationPresent(Test.class)){
			Annotation ann = m.getAnnotation(Test.class);
			
			String[] dependsOngroups = ((Test)ann).dependsOnGroups();
			String[] dependsOnMethods = ((Test)ann).dependsOnMethods();
			al.add(dependsOngroups);
			al.add(dependsOnMethods);
			System.out.println("Dependent Groups");
			for(String s : dependsOngroups){
				
				System.out.println(s);
			}
			System.out.println("-------------------");
			System.out.println("Dependent Methods");
			for(String s : dependsOnMethods){
				System.out.println(s);
			}
			
		}
		return al;
	}
	public static String getDescription(Class c, String methodName) throws NoSuchMethodException, SecurityException{
		Method m = null;
		String description=null;
		for(Method z : c.getDeclaredMethods()){
			System.out.println(z.getName());
			if(methodName.equals(z.getName())){
				m = z;
			}
		}
		if(m.isAnnotationPresent(Test.class)){
			Annotation ann = m.getAnnotation(Test.class);
			
			 description = ((Test)ann).description();
			System.out.println("-------------------");
			
		}
		return description;
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, ClassNotFoundException {
		//test(Smoke.class,"addFinApps");
		//TestngUtility.getMethod(Smoke.class,"addFinApps");
	}
}
