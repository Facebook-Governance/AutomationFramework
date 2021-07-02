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

import com.omni.pfm.Exceptions.FrameAttributeNotFoundExecption;

/**
 * @author Suhaib
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "iframe")
public class IFrame {

    @XmlAttribute(name = "name")
    String name;

    @XmlAttribute(name = "title")
    String title;

    @XmlAttribute(name = "locator")
    String locator;

    @XmlAttribute
    String value;

    @XmlElement(name = "element", type = Element.class)
    List<Element> elements;

    /**
     * @return the name
     */
    public String getName() {
	if (name == null) {
	    throw new FrameAttributeNotFoundExecption("Name attribute not found for the frame");
	}
	return name.trim();
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @return the locator
     */
    public String getLocator() {
	if (locator == null) {
	    throw new FrameAttributeNotFoundExecption("Locator attribute not found for the frame");
	}
	return locator.trim();
    }

    /**
     * @return the value
     */
    public String getValue() {
	if (value == null) {
	    throw new FrameAttributeNotFoundExecption("Value attribute not found for the frame");
	}
	return value;
    }

    /**
     * @return the elements
     */
    public List<Element> getElements() {
	return elements;
    }

}
