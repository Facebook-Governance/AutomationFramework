/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.pages.DemoSite;

import java.util.List;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.omni.pfm.utility.SeleniumUtil;

public class CreateServiceRequest_Loc {
	
	WebDriver d;
	String pageName = "DemoSite";
	
	
public CreateServiceRequest_Loc(WebDriver d) {
	this.d=d;
}
	

public WebElement backText() {

	return SeleniumUtil.getVisibileWebElement(d, "BackText", pageName, null);

}

public WebElement headerText() {

	return SeleniumUtil.getVisibileWebElement(d, "HeaderText", pageName, null);

}

public WebElement text1() {

	return SeleniumUtil.getVisibileWebElement(d, "Text1", pageName, null);

}

public WebElement text2() {

	return SeleniumUtil.getVisibileWebElement(d, "Text2", pageName, null);

}

public WebElement labelSubject() {

	return SeleniumUtil.getVisibileWebElement(d, "LabelSubject", pageName, null);

}

public WebElement textBoxSubject() {

	return SeleniumUtil.getVisibileWebElement(d, "TextBoxSubject", pageName, null);

}

public WebElement errorEmptySubject() {

	return SeleniumUtil.getVisibileWebElement(d, "ErrorEmptySubject", pageName, null);

}

public WebElement labelDescription() {

	return SeleniumUtil.getVisibileWebElement(d, "LabelDescription", pageName, null);

}

public WebElement textBoxDescription() {

	return SeleniumUtil.getVisibileWebElement(d, "TextBoxDescription", pageName, null);

}

public WebElement errorEmptyDescription() {

	return SeleniumUtil.getVisibileWebElement(d, "ErrorEmptyDescription", pageName, null);

}

public WebElement characterCount() {

	return SeleniumUtil.getVisibileWebElement(d, "CharacterCount", pageName, null);

}

public WebElement labelSystemDetails() {

	return SeleniumUtil.getVisibileWebElement(d, "LabelSystemDetails", pageName, null);

}

public WebElement dropdownSystemDetails() {

	return SeleniumUtil.getVisibileWebElement(d, "DropdownSystemDetails", pageName, null);

}

public WebElement browserText() {

	return SeleniumUtil.getVisibileWebElement(d, "BrowserText", pageName, null);

}

public WebElement browserVersion() {

	return SeleniumUtil.getVisibileWebElement(d, "BrowserVersion", pageName, null);

}

public WebElement oSText() {

	return SeleniumUtil.getVisibileWebElement(d, "OSText", pageName, null);

}

public WebElement oSType() {

	return SeleniumUtil.getVisibileWebElement(d, "OSType", pageName, null);
	
}
public String getBrowserVersion() {

	Capabilities caps = ((RemoteWebDriver) d).getCapabilities();

	String browserVersion = caps.getBrowserName() + " " + caps.getVersion();

	return browserVersion.toLowerCase();

}

public String getOSType() {

	String os = System.getProperty("os.name");

	return os.toLowerCase();

}




public WebElement submitButton() {

	return SeleniumUtil.getVisibileWebElement(d, "SubmitButton", pageName, null);

}
public WebElement supportButton() {

	return SeleniumUtil.getVisibileWebElement(d, "supportLinkXpath", pageName, null);

}

public WebElement serviceRequests() {

	return SeleniumUtil.getVisibileWebElement(d, "serviceRequestsXpath", pageName, null);

}public WebElement attachmentTitle() {

	return SeleniumUtil.getVisibileWebElement(d, "attachmentHdr", pageName, null);

}

public WebElement addAttachmentLink() {

	return SeleniumUtil.getVisibileWebElement(d, "addAttachmentTxt", pageName, null);

}
public WebElement addAttachmentIcon() {

	return SeleniumUtil.getVisibileWebElement(d, "addAttachmentIcn", pageName, null);

}

public WebElement fileFormatText() {

	return SeleniumUtil.getVisibileWebElement(d, "fileFormatTxt", pageName, null);

}

public WebElement fileFormatText2() {

	return SeleniumUtil.getVisibileWebElement(d, "fileFormatTxt2", pageName, null);

}

public WebElement fileFormatText1() {

	return SeleniumUtil.getVisibileWebElement(d, "fileFormatTxt1", pageName, null);

}

public WebElement indicatorIcon() {

	return SeleniumUtil.getVisibileWebElement(d, "IndicatorIcon", pageName, null);

}

public WebElement fileAttachment() {

	return SeleniumUtil.getWebElement(d, "fileAttach", pageName, null);

}

public WebElement deleteIcon1() {

	return SeleniumUtil.getVisibileWebElement(d, "deleteIcn1", pageName, null);


}
public WebElement issueSubTextBox() {

	return SeleniumUtil.getVisibileWebElement(d, "issueSubTxtBox", pageName, null);


}public WebElement descriptionTextBox() {

	return SeleniumUtil.getVisibileWebElement(d, "descriptionTxtBox", pageName, null);


}public List<WebElement> fileName() {

	return SeleniumUtil.getWebElements(d, "fileNam", pageName, null);


}public List<WebElement> screenShotIcon() {

	return SeleniumUtil.getWebElements(d, "screenShotIcn", pageName, null);


}
public List <WebElement> deleteIcon() {

	return SeleniumUtil.getWebElements(d, "deleteIcn", pageName, null);
}
	
	public void deleteAttachment()
	{
		try {
			deleteIcon().get(0).click();
			SeleniumUtil.waitForPageToLoad();
			deleteAttachment();
		}
		catch(Exception e)
		{
			
		}
	}
	


public WebElement screenshotErrorIcon() {

	return SeleniumUtil.getVisibileWebElement(d, "screenshotErrorIcn", pageName, null);


}public WebElement screenshotErrorText() {

	return SeleniumUtil.getVisibileWebElement(d, "screenshotErrorTxt", pageName, null);


}public WebElement windowCloseBtn() {

	return SeleniumUtil.getVisibileWebElement(d, "WindowCloseBtn", pageName, null);


}public WebElement successToastMsg() {

	return SeleniumUtil.getVisibileWebElement(d, "SuccessToastMsg", pageName, null);


}

public WebElement myProfile() {

	return SeleniumUtil.getVisibileWebElement(d, "myProfileXpath", pageName, null);
}

	public void navigateToServiceRequests() {
		this.supportButton().click();
		SeleniumUtil.waitForPageToLoad();
		this.serviceRequests().click();
		SeleniumUtil.waitForPageToLoad();


}

	public void navigateToMyProfile() {
		this.myProfile().click();
		SeleniumUtil.waitForPageToLoad();
	}

	 public String getAttachementTitle() {
		return attachmentTitle().getText().trim();
	}

	public boolean addAttachmentIconIsDisplyed() {
		return addAttachmentIcon().isDisplayed();
	}

	public void typeTextField(WebElement element, String value) {
		SeleniumUtil.click(element);
		element.clear();
		element.sendKeys(value);
	}
	
	/**//**
	* Method to check if a screen shot is attached successfully. Starting from 1
	* 
		 * @param i
		 * @return
		 *//*
	*/	public boolean isScreenShotDisplayed(int i) {
			i = i - 1;
			return screenShotIcon().size() >= i ? screenShotIcon().get(i).isDisplayed() : false;
		}
}
