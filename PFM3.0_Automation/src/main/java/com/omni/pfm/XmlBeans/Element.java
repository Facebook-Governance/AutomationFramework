/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.XmlBeans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.Exceptions.ElementAttributeNotFoundExecption;
import com.omni.pfm.utility.GenericUtil;

/**
 * @author Suhaib
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "element")
public class Element {

    private static final Logger logger = LoggerFactory.getLogger(Element.class);

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "locator")
    private String locator;

    @XmlAttribute(name = "value")
    private String value;

    @XmlAttribute(name = "timeOut")
    private String timeOut;

    @XmlValue
    private String elementValue;

    public String getElementValue() {
	return elementValue.trim();
    }

    public String getName() {
	if (name == null) {
	    throw new ElementAttributeNotFoundExecption("Name attribute not found for the element");
	}
	return name.trim();
    }

    public String getLocator() {
	if (locator == null) {
	    throw new ElementAttributeNotFoundExecption("Locator attribute not found for the element");
	}
	return locator.trim();
    }

    public String getValue() {
	if (value == null) {
	    throw new ElementAttributeNotFoundExecption("Value attribute not found for the element");
	}
	return value;
    }

    public void setElementValue(String value) {
	this.elementValue = value;
    }

    public int getTimeOut() {
	if (GenericUtil.isNull(timeOut)) {
	    return 0;
	} else {
	    try {
		System.out.println("Time Out period for the element = " + timeOut);
		return Integer.parseInt(timeOut);
	    } catch (NumberFormatException e) {
		logger.error("Time Out defined is not proper : {}", timeOut);
		return 0;
	    }
	}
    }
}
