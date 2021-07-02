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

import org.testng.Reporter;

/**
 * @author msuhaib
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "pre-condition")
public class PreCondition {

    @XmlAttribute(name = "type")
    String type;

    @XmlAttribute(name = "samlPage")
    String samlPage;

    @XmlElement(name = "element", type = Element.class)
    List<Element> elements;

    public String getType() {
	if (type == null || type.trim().equals("")) {
	    Reporter.log("Condition Type Not Specified");
	    return null;
	}
	return type;
    }

    public String getSamlPage() {
	return samlPage;
    }

    public List<Element> getElements() {
	return elements;
    }

}
