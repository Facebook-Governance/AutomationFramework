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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Suhaib
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Pages")
public class Pages {

	@XmlElement(name = "page", type = Page.class)
	List<Page> pages;

	public List<Page> getPages() {
		return pages;
	}
}