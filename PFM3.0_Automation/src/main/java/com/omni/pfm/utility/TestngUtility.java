/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.utility;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.omni.pfm.factory.PageFactory;

/**
 * @author Suhaib And Mohit
 *
 */
public class TestngUtility {

	private static Logger logger = LoggerFactory.getLogger(TestngUtility.class);

	List<XmlSuite> xmlSuites = new ArrayList<XmlSuite>();
private XmlTest test=null;
	void generateTestNgXml() throws NoSuchMethodException, SecurityException, ClassNotFoundException {
		XmlSuite suite = new XmlSuite();
		suite.setName("Smoke2");
		test = new XmlTest(suite);
		test.setName("Temp");
		List<Map<String, String>> dataList = ExcelUtil.readExcel("smok.xlsx");
		Map<String, String> suiteParams = dataList.get(0);
		System.out.println(suiteParams);
		String testCls = suiteParams.get("Test Class");
		System.out.println(testCls);
		suiteParams.remove("Test Class");
		suite.setParameters(suiteParams);
		List<XmlClass> classes = new ArrayList<XmlClass>();
		//XmlClass testClass = new XmlClass(testCls);
		XmlClass testClass = new XmlClass(testCls);//"com.omni.pfm.suites.Smoke"
		
		
		
		
		
		List<String> excludedMethods = new ArrayList<String>();
		Set<XmlInclude> includedMethods = new LinkedHashSet<XmlInclude>();
		for (Map.Entry<String, String> entry : dataList.get(1).entrySet()) {
			if (entry.getValue().equalsIgnoreCase("n")) {
				excludedMethods.add(entry.getKey());
			} else if (entry.getValue().equalsIgnoreCase("y")) {
				getDependentMethod(testCls,entry.getKey(),includedMethods);
				if(dependentMethodName!=null)
				{
					
					if(count!=0)
						Collections.reverse(dependentMethodName);
					for(int j=0;j<dependentMethodName.size();j++){
						includedMethods.add(new XmlInclude(dependentMethodName.get(j)));
					}
					includedMethods.clear();
					count++;
				}
				getDependentMethodByGroupByMethod(testCls,entry.getKey(),includedMethods);
				includedMethods.add(new XmlInclude(entry.getKey()));
			}
		}
		List<XmlInclude> includeMethodToTestNn = new ArrayList<XmlInclude>(includedMethods);
		testClass.setIncludedMethods(includeMethodToTestNn);
		testClass.setExcludedMethods(excludedMethods);
		classes.add(testClass);
		test.setClasses(classes);
		xmlSuites.add(suite);
	}
	public static int count=0;
	public static void getDependentMethodByGroupByMethod(String className,String MethodName,Set<XmlInclude> includedMethods) throws NoSuchMethodException, SecurityException, ClassNotFoundException
	{
		List<String[]> al=PageFactory.test(Class.forName(className), MethodName);
		//int i=0;
		if(al.get(1)==null)
			return;
		if(al.get(0)!=null && al.get(1).length>0)
		{
			String[] methodName=al.get(1);
			for(int i=0;i<al.get(1).length;i++)
			{
				if(includedMethods!=null)
					includedMethods.add(new XmlInclude(methodName[i]));
			}
		}
		//i++;
	}
	public static List<String> dependentMethodName=null;
	public static void getDependentMethod(String className,String MethodName,Set<XmlInclude> includedMethods) throws NoSuchMethodException, SecurityException, ClassNotFoundException
	{
		List<String[]> al=PageFactory.test(Class.forName(className), MethodName);
		
		if(al.get(0)==null){
			return;
		}
		if(al.get(0)!=null && al.get(0).length>0)
		{
			String[] dependsOnGroup=al.get(0);
			dependentMethodName=new ArrayList<String>();
			for(int i=0;i<dependsOnGroup.length;i++)
			{
				String method=getMethod(Class.forName(className),dependsOnGroup[i],includedMethods);
				dependentMethodName.add(method);
				if(method!=null)
				{
					getDependentMethod(className,method,includedMethods);
				}
			}
			
			
		}else
		{
			logger.info("For Class [{}], Depends on group for method [{}] is null",className,MethodName);
		}
	}
	
	public static String getMethod(Class c,String groupName,Set<XmlInclude> includedMethods) throws NoSuchMethodException, SecurityException
	{
		if(c==null || groupName==null){
			System.out.print("No data");
			logger.error("The argument should not be null");
		}
		String methodName=null;
		Annotation ann = null;
		System.out.println("TotalMethods=="+c.getDeclaredMethods().length);
		for(Method z : c.getDeclaredMethods()){
			 ann = z.getAnnotation(Test.class);
			 if(ann!=null)
			 {
				 System.out.println(z.getName());
				String[] grpName = ((Test)ann).groups();
				if(grpName.length>0){
					ArrayList<String> grpList = new ArrayList<String>(Arrays.asList(grpName));
					if(grpList.contains(groupName))
					{
						methodName=z.getName();
						valk.add(methodName);
						logger.info("The Dependent MethodName is [{}]",methodName);
						if(includedMethods!=null)
							//includedMethods.add(new XmlInclude(methodName));
						break;
					}
				}
			 }
		}
		return methodName;
	}
	
	public static List<String> valk=new LinkedList<String>();
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, ClassNotFoundException {
		TestngUtility util = new TestngUtility();
		util.generateTestNgXml();
		TestNG tng = new TestNG();
		tng.setXmlSuites(util.xmlSuites);
		tng.run();
		//System.out.println("Val=="+getMethod(YCOM_AssetsAllocationFinapp.class,"AssetsDrag"));
		/*List<XmlInclude> includedMethods = new ArrayList<XmlInclude>();
		getDependentMethod("com.omni.pfm.suites.MyTestClass","a",includedMethods);
		System.out.print("Final val="+valk);*/
		
	}
}
