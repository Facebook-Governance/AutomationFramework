/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.XmlBeans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.omni.pfm.Exceptions.PageAttributeNotFoundExecption;

/**
 * @author Suhaib
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "page")
public class Page {

    @XmlElement(name = "pre-condition", type = PreCondition.class)
    List<PreCondition> preConditions;
    
    @XmlElement(name = "mobile", type = Mobile.class)
    Mobile mobile;

    @XmlElement(name = "post-condition")
    String postconditions;

    @XmlAttribute(name = "name")
    String name;

    @XmlAttribute(name = "isMenuItem")
    String isMenuItem;

    @XmlAttribute(name = "parentMenu")
    String ParentMenu;

    @XmlAttribute(name = "containerPage")
    String containerPage;

    @XmlAttribute(name = "locator")
    String locator;

    @XmlAttribute(name = "locatorValue")
    String locatorValue;

    @XmlElement(name = "element", type = Element.class)
    List<Element> elements;

    @XmlElement(name = "iframe", type = IFrame.class)
    List<IFrame> frames;

    @XmlAttribute(name = "isHomePage")
    String isHomePage;

    public boolean getIsHomePage() {
	if (isHomePage != null && isHomePage.trim().equals("true")) {
	    return true;
	} else
	    return false;
    }

    /**
     * @return the name
     */

    public String getName() {
	if (name == null) {
	    throw new PageAttributeNotFoundExecption("Name attribute not found for the page...");
	}
	return name.trim();
    }

    public List<PreCondition> getPreconditions() {
	return preConditions;
    }
    
    public Mobile getMobileTag() {
    	return mobile;
    }

    public String getPostconditions() {
	if (postconditions == null) {
	    return "";
	} else
	    return postconditions.trim();
    }

    /**
     * @return the isMenuItem
     */

    public Boolean getIsMenuItem() {
	if (isMenuItem != null && isMenuItem.trim().equals("true")) {
	    return true;
	} else
	    return false;
    }

    /**
     * @return the parentMenu
     */

    public String getParentMenu() {
	if (ParentMenu == null) {
	    return "";
	} else
	    return ParentMenu.trim();
    }

    /**
     * @return the containerPage
     */

    public String getContainerPage() {
	if (containerPage != null) {
	    return containerPage.trim();
	} else
	    return "";
    }

    /**
     * @return the locator
     */

    public String getLocator() {
	if (locator == null) {
	    throw new PageAttributeNotFoundExecption("Locator attribute not found for the page...");
	}
	return locator.trim();
    }

    /**
     * @return the locatorValue
     */

    public String getLocatorValue() {
	if (locatorValue == null) {
	    throw new PageAttributeNotFoundExecption("LocatorValue attribute not found for the page...");
	}
	return locatorValue.trim();
    }

    /**
     * @return the elements
     */
    public List<Element> getElements() {
	return elements;
    }
    
    public void setElement(List<Element> ele)
    {
    	this.elements=ele;
    }

    /**
     * @return the frames
     */
    public List<IFrame> getFrames() {
	return frames;
    }

}
