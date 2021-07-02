/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.XmlBeans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.collections.ListUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.constants.PropertyConstants;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

/**
 * @author mkumar7
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mobile")
public class Mobile {

    @XmlAttribute(name = "mode")
    String mode;

    @XmlElement(name = "element", type = Element.class)
    List<Element> elements;
    
   

    public String getMode() {
	if (mode == null || mode.trim().equals("")) {
	    Reporter.log("Condition mode Not Specified");
	    return null;
	}
	return mode;
    }
    public List<Element> getElements() {
	return elements;
    }
   /* public static void main(String []agr){
    	Logger logger = LoggerFactory.getLogger(Mobile.class);
    	String pageName="AccountsPage",containerPage;
    	Page currentPage;
    	Config config = Config.createInstance("BBT");
    	
    	if(PropsUtil.getEnvPropertyValue("appFlag").equalsIgnoreCase(PropertyConstants.MOBILE_LEBEL))
    	{
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
	    			config.pageRepository.put(p.getName(), p);
	    			//Config.getInstance().pageRepository.put(pageNames, p);
    			}else
    			{
    				logger.info("<< The page ["+pageNames+"] does not contains the mobile tag" );
    			}
    			
    		}
    	}
    	
    	
    	
    	logger.info("mob Value=="+PropsUtil.getDataPropertyValue("TitleOfTabToClose"));
    	System.setProperty("webdriver.gecko.driver", "c:/geckodriver.exe");
    	DesiredCapabilities des=new DesiredCapabilities();
    	des.setCapability("acceptSslCerts", true);
    	
    	
		WebDriver d = new FirefoxDriver();
		d.get("https://www.google.co.in");
		//if web "click on IMAGE" and if mobile "click on gmail"
		WebElement link=SeleniumUtil.getWebElement(d, "LinkToClick", "DashboardPage", null);
		SeleniumUtil.click(link); 
		d.get("https://www.google.co.in");
		WebElement serarch=SeleniumUtil.getWebElement(d, "searchtextBox", pageName, null);
		SeleniumUtil.sendKeys(serarch, PropsUtil.getDataPropertyValue("valueToSearch"));
		//serarch.sendKeys(PropsUtil.getDataPropertyValue("valueToSearch"));
		SeleniumUtil.click(SeleniumUtil.getWebElement(d, "searchButton", pageName, null));
    	
    }*/

}
