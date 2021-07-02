/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.XmlBeans.Element;
import com.omni.pfm.XmlBeans.Mobile;
import com.omni.pfm.XmlBeans.Page;
import com.omni.pfm.config.Config;
import com.omni.pfm.constants.PropertyConstants;

public class MobileUtil {
	 static Logger logger = LoggerFactory.getLogger(MobileUtil.class);
	private static boolean mobileFlag=false;

	public static boolean isMobileFlag() {
		return mobileFlag;
	}

	public static void setMobileFlag(boolean mobileFlag) {
		MobileUtil.mobileFlag = mobileFlag;
	}
	
	public static void setTypeOfMode(String value)
	{
		logger.info("The appFlag is [{}]. comparing getMode type",value);
		List<String> storedValues=Arrays.asList(PropertyConstants.MOBILE_LEBEL.split(","));
		if(storedValues.contains(value.toLowerCase()))
		{
			logger.info("The APPFlag is Mobile Supportive");
			setMobileFlag(true);
		}else{
			setMobileFlag(false);
			logger.info("The APPFlag is WEB Supportive");
		}
	}
	
	  public static void mobileSuiteParser()
	    {
	    	//if(PropsUtil.getEnvPropertyValue("appFlag").equalsIgnoreCase(PropertyConstants.MOBILE_LEBEL))
	    	if(MobileUtil.isMobileFlag())
	    	{
	    		logger.info("Parsing the XML for Mobile Device");
	    		for(String pageNames:Config.getInstance().pageRepository.keySet())
	    		{
	    			Page p=(Page)Config.getInstance().pageRepository.get(pageNames);
	    			Mobile mobile = PageParser.getMobileTag(pageNames);
	    			List<Element> elementToReplace=new ArrayList<Element>();
	    			if(mobile!=null){
	    				logger.info("<< Found mobile tag inside the page ["+pageNames+"]" );
		    			List<Element> mobileElements = mobile.getElements();
		    			List<Element> pageElements=p.getElements();
		    			for(Element mob:mobileElements)
		    			{
		    				String MobElementName=mob.getName();
		    				Iterator <Element> pageElementIterator=pageElements.iterator();
		    				while (pageElementIterator.hasNext()) {
		    				    Element pageEle = pageElementIterator.next();
		    				    if(MobElementName.equals(pageEle.getName()))
		    				    {
		    				    	elementToReplace.add(mob);
		    				    	pageElementIterator.remove();
		    				    	//pageElements.add(mob);
		    				    }else{
		    				    	elementToReplace.add(mob);
		    				    }
		    				}
		    			}
		    			
						List<Element> finalElement=ListUtils.union(pageElements, elementToReplace);
		    			p.setElement(finalElement);
		    			Config.getInstance().pageRepository.put(p.getName(), p);
		    			//Config.getInstance().pageRepository.put(pageNames, p);
	    			}else
	    			{
	    				logger.info("<< The page ["+pageNames+"] does not contains the mobile tag" );
	    			}
	    			
	    		}
	    	}
	    }
	
	
}
