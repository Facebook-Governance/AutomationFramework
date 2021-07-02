/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.PageProcessor;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.Exceptions.FrameNotFoundExecption;
import com.omni.pfm.Exceptions.PageNotFoundException;
import com.omni.pfm.XmlBeans.Element;
import com.omni.pfm.XmlBeans.IFrame;
import com.omni.pfm.XmlBeans.Mobile;
import com.omni.pfm.XmlBeans.Page;
import com.omni.pfm.XmlBeans.Pages;
import com.omni.pfm.XmlBeans.PreCondition;
import com.omni.pfm.config.Config;
import com.omni.pfm.listeners.EReporter;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Suhaib
 *
 */
public class PageParser {
	private static final Logger logger = LoggerFactory.getLogger(PageParser.class);
	private static Config config = Config.getInstance();

	static List<Page> loadAllPages(String xmlFileName) {
		logger.info("Loading all pages from file '{}'", xmlFileName);
		List<Page> pages = null;
		try {
			InputStream in = PageParser.class.getClassLoader().getResourceAsStream(xmlFileName);
			JAXBContext jaxbContext = JAXBContext.newInstance(Pages.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Pages que = (Pages) jaxbUnmarshaller.unmarshal(in);
			pages = que.getPages();
			if (pages.size() < 0) {
				logger.error("Could not find any page defined in the xml file - {}", xmlFileName);
			}
			return pages;
		} catch (JAXBException e) {
			e.printStackTrace();
			logger.error("Uable to parse Pages XML file - {}", xmlFileName);
			logger.error("<== Exiting loadAllPages method with NULL");
			return null;
		}
	}

	/**
	 * @param pageName - Name of the page as defined in the Page.xml
	 * @param d        - WedDriver instance
	 */
	public static boolean navigateToPage(String pageName, WebDriver d) {
		logger.info("Navigating to page '{}'", pageName);
		logger.info("Navigate to : {}", pageName);
		boolean status = false;
		List<Page> allPages = config.pageRepo; // loadAllPages("ALKAMI_Pages.xml");
		boolean isMenuItem = false;
		String containerPage = "";
		Page currentPage = null;
		if (allPages == null || allPages.size() < 1) {
			logger.error("Could not get page info from the xml", allPages);
		} else if (pageName == null || pageName.trim().equals("")) {
			logger.error("PAGE NAME IS NULL OR EMPTY - {}", pageName);
		} else {
			boolean pageFound = false;
			for (Page page : allPages) {
				if (pageName.trim().equals(page.getName().trim())) {
					containerPage = page.getContainerPage();
					currentPage = page;
					pageFound = true;
					break;
				}
			}
			if (!pageFound) {
				logger.error("PAGE [{}]  NOT FOUND IN THE XML", pageName);
				EReporter.log(LogStatus.INFO, "PAGE [" + pageName + "]  NOT FOUND IN THE XML");
				return status;
			}
			List<PreCondition> list = getPreconditions(pageName);
			boolean isDeepLink = false;
			if (list != null && list.size() > 0) {
				if (isConditionPresent(list, "deep_link")) {
					isDeepLink = true;
					if (currentPage.getName().equalsIgnoreCase(config.getCurrentPage())) {
						logger.info("Current page == {}", currentPage.getName());
						return true;
					}
					config.setCurrentPage(pageName);
					ConditionProcessor.processPreconditions(d, list);
					// 10 Nov
					status = true;
				}
			}
			if (!isDeepLink) {
				logger.info("CurrentPage from config is ::::: {}", config.getCurrentPage());
				logger.info("Current Page is - {}", currentPage.getName());
				logger.info(
						"Current Page Details \nName : {}\nisMenuItem : {}\nparentMenu : {}\ncontainerPage : {}\nlocator : {}\nlocatorValue : {}",
						currentPage.getName(), currentPage.getIsMenuItem(), currentPage.getParentMenu(),
						currentPage.getContainerPage(), currentPage.getLocator(), currentPage.getLocatorValue());
				logger.info("IS_HOME_PAGE VALUE IS:::::::::::::::::::" + currentPage.getIsHomePage());
				// removed the check if the page to navigate is same as the current
				// page displayed. created bugs when navigation was done other
				// navigateToPage method..
				if (currentPage.getName().equalsIgnoreCase(config.getCurrentPage())) {
					logger.info("Current page == {}", currentPage.getName());
					return true;
				} else if (currentPage.getIsHomePage()
						&& !config.getCurrentPage().equalsIgnoreCase(currentPage.getName())) {
					gotoHomePage(d, currentPage);
					return true;
				} else {
					Element parentMenu = null;
					WebElement we;
					if (!(currentPage.getIsMenuItem() == null) && currentPage.getIsMenuItem()
							&& !(currentPage.getParentMenu() == null)
							&& !currentPage.getParentMenu().trim().equals("")) {
						logger.info("Page is Menu Item");
						isMenuItem = true;
						String parentPage;
						if (currentPage.getIsHomePage()) {
							parentPage = currentPage.getName();
						} else {
							parentPage = currentPage.getContainerPage();
						}
						parentMenu = getPageElement(parentPage, currentPage.getParentMenu());
					}
					if (isMenuItem && parentMenu != null) {
						we = getWebElement(d, parentMenu.getLocator(), parentMenu.getValue(), 30);
					} else {
						we = getWebElement(d, currentPage.getLocator(), currentPage.getLocatorValue(), 30);
					}
					logger.info("Element found in the current page.");
					if (we == null) {
						navigateToPage(containerPage, d);
					}
				}
				if (isMenuItem) {
					clickOnMenu(allPages, currentPage, d);
				}
				WebElement we = getWebElement(d, currentPage.getLocator(), currentPage.getLocatorValue(), 30);
				if (we != null) {
					logger.info("Clicking on element with locator = [{}] & value = [{}]", currentPage.getLocator(),
							currentPage.getLocatorValue());
					// change by Mohit
					SeleniumUtil.click(we);
					// END
					// ConditionProcessor.processPreconditions(d, pageName,"");
					config.setCurrentPage(pageName);
					status = true;
					d.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				} else {
					logger.error("Web Element is null - {}", we);
				}
			}
		}
		logger.info("<== Exiting from NavigateToPage method.");
		return status;
	}

	/**
	 * Click on the Menu
	 *
	 * @param allPages
	 * @param pageName
	 * @param MenuName
	 * @param d
	 */
	public static void clickOnMenu(List<Page> allPages, Page currentPage, WebDriver d) {
		logger.info("==>Entering clickOnMenu Method.");
		logger.info("Container Page = [{}] and Menu Name = [{}]", currentPage.getContainerPage(),
				currentPage.getParentMenu());
		if (currentPage.getIsHomePage()) {
			Element el = getPageElement(currentPage.getName(), currentPage.getParentMenu());
			if (el == null) {
				logger.error("Could not find Menu Element [{}] in page [{}]", currentPage.getParentMenu(),
						currentPage.getName());
			} else {
				logger.info("Found Menu Element");
				// Actions action = new Actions(d);
				WebElement we = getWebElement(d, el.getLocator(), el.getValue(), 60);
				logger.info("Clicking on Main Menu...");
				// Refactor By Mohit Firefox
				SeleniumUtil.click(we);
				// action.moveToElement(we).click().build().perform();
			}
		} else {
			Page containerPage = getPage(currentPage.getContainerPage());
			if (containerPage == null) {
				logger.error("Container Page is null - {}", currentPage.getContainerPage());
			} else {
				logger.info("Found Conatainer Page - {}", containerPage.getName());
				Element el = getPageElement(containerPage.getName(), currentPage.getParentMenu());
				if (el == null) {
					logger.error("Could not find Parent Menu [{}] in page [{}]", currentPage.getParentMenu(),
							containerPage.getName());
				} else {
					logger.info("Found parent menu item [{}] in page [{}]", currentPage.getParentMenu(),
							containerPage.getName());
					// firefox
					WebElement we = getWebElement(d, el.getLocator(), el.getValue(), 30);
					logger.info("Clicking on Main Menu...");
					SeleniumUtil.click(we);// .click();
					// action.moveToElement(we).click().build().perform();
				}
			}
		}
		logger.info("<== Exiting clickOnMenu Method.");
	}

	public static void gotoHomePage(WebDriver d, Page homePage) {
		logger.info("==> NAVIGATING to HOME PAGE");
		if (!(homePage.getIsMenuItem() == null) && homePage.getIsMenuItem() && !(homePage.getParentMenu() == null)
				&& !homePage.getParentMenu().trim().equals("")) {
			clickOnMenu(config.pageRepo, homePage, d);
		}
		WebElement we = getWebElement(d, homePage.getLocator(), homePage.getLocatorValue(), 30);
		if (we != null) {
			logger.info("Clicking on element with locator = [{}] & value = [{}]", homePage.getLocator(),
					homePage.getLocatorValue());
			SeleniumUtil.click(we);
			config.setCurrentPage(homePage.getName());
		} else {
			logger.error("Element [{} - {}] is null", homePage.getLocator(), homePage.getLocatorValue());
		}
		logger.info("==> NAVIGATION to HOME PAGE COMPLETED");
	}

	/**
	 * @param d            - WebDriver instance
	 * @param locator      - Type of locator
	 * @param locatorValue - Value of the locator
	 * @return - Returns the WebElement object
	 */
	public static WebElement getWebElement(WebDriver d, String locator, String locatorValue, int timeOut) {
		logger.info("==> Entering getWebElement method.");
		int timeOuts = 40;
		if (timeOut > 0) {
			timeOuts = timeOut;
		}
		if (d == null) {
			logger.error("Web Driver instance [d] is null. Exiting method", d);
			return null;
		} else if (locator == null || locator.trim().equals("")) {
			logger.error("Locator type is null or empty - {}", locator);
			logger.error("Returning from method with null");
			return null;
		} else if (locatorValue == null || locatorValue.trim().equals("")) {
			logger.error("Locator value is null or empty - {}", locatorValue);
			logger.error("Returning from method with null");
			return null;
		} else {
			return SeleniumUtil.waitUntilElementPresent(d, locator, locatorValue, timeOuts);
		}
	}

	@Deprecated
	public static String[] getElementValues(String label) {
		logger.info("==> Entering getElementValues() method.");
		logger.info("Label = [{}]", label);
		String values[] = new String[2];
		if (label.lastIndexOf(".") == -1) {
			logger.error("Label Format is wrong - [{}]", label);
			logger.error("Label Format is PageName.LabelName");
			return null;
		}
		Map<String, Page> repo = Config.getInstance().pageRepository;
		logger.info("Page repo value = [{}]", repo);
		values = label.split("\\.", 2);
		logger.info("Page = [{}] and Element = [{}]", values[0], values[1]);
		if (!repo.containsKey(values[0])) {
			logger.error("Page Map does not contain page - [{}] - Check page.xml file.", values[0]);
			logger.error("Returning from method with null.");
			return null;
		} else {
			Page page = repo.get(values[0]);
			logger.info("Page Found : {}", page.getName());
			List<Element> elements = page.getElements();
			if (elements == null || elements.size() < 1) {
				logger.error("No Elements found in the Page - {}", label.split("\\.", 2)[0]);
				logger.error("Returning from the method with null");
				return null;
			} else {
				for (Element e : elements) {
					if (label.trim().equalsIgnoreCase(e.getName().trim())) {
						logger.info("Found Element - [{}] in the page xml file", label);
						logger.info("Locator Type = [{}] and Locator Value = [{}]", e.getLocator(), e.getValue());
						values = new String[2];
						values[0] = e.getLocator();
						values[1] = e.getValue();
						logger.info("Returning from getElementValues method.");
						return values;
					}
				}
				logger.error("Element [{}] not found in page [{}]", label, page.getName());
				logger.error("Returning from method getElementValues() with null");
				return null;
			}
		}
	}

	public static Boolean loadFrame(WebDriver d, String parentPageName, String frameName) {
		logger.info("==> Entering loadFrame() method");
		boolean loadSuccess = false;
		if (d == null) {
			logger.info("WebDriver instance is null : {}", d);
			logger.info("<== Exiting method loadFrame()");
		} else if (frameName == null || frameName.trim().equals("")) {
			logger.info("Frame Name is null or empty : {}", frameName);
			logger.info("<== Exiting method loadFrame()");
		} else if (parentPageName == null || parentPageName.trim().equals("")) {
			logger.warn("Parent Page Name is null or empty : {}", parentPageName);
			logger.warn("Frame [{}] will be loaded from the current page ", frameName);
			logger.info("Current Page is : {}", config.getCurrentPage());
			loadSuccess = switchToFrame(d, config.getCurrentPage(), frameName);
		} else {
			navigateToPage(parentPageName, d);
			loadSuccess = switchToFrame(d, parentPageName, frameName);
		}
		logger.info("Frame Load Status is : {}", String.valueOf(loadSuccess).toUpperCase());
		logger.info("Exiting loadFrame() method.");
		return loadSuccess;
	}

	/**
	 * @param locator
	 * @param value
	 */
	public static boolean switchToFrame(WebDriver d, String parentPage, String frameName) {
		List<Page> pages = config.pageRepo;
		boolean frameFound = false;
		boolean flag = false;
		if (frameName == null)
			return flag;
		for (Page page : pages) {
			if (parentPage.trim().equals(page.getName())) {
				List<IFrame> frames = page.getFrames();
				if (frames == null || frames.size() < 1) {
					logger.error("No Frame present in the XML for page : {}", page.getName());
					logger.error("Could not find Frame [{}] in Page [{}]", frameName, page.getName());
				} else {
					for (IFrame f : frames) {
						if (frameName.trim().equalsIgnoreCase(f.getName().trim())) {
							frameFound = true;
							logger.info("Switching to frame : {}", frameName);
							SeleniumUtil.scrollElementIntoView(d, getWebElement(d, f.getLocator(), f.getValue(), 20),
									true);
							d.switchTo().frame(getWebElement(d, f.getLocator(), f.getValue(), 20));
							flag = true;
							break;
						}
					}
					if (frameFound) {
						break;
					} else {
						logger.error("Could not find frame [{}] in page [{}]", frameName, parentPage);
					}
				}
			}
		}
		return flag;
	}

	/**
	 * @param pageName       - Name of the page where element is present
	 * @param isFrameElement - True if element is present within the frame, else
	 *                       false
	 * @param frameName      - Name of the frame if isFrameElement is true, else
	 *                       null
	 * @param label          - Label the element
	 * @return - Returns the String array of length 3, first item containing the
	 *         locator type, second the locator value and third the timeOut period
	 *         defined.
	 */
	public static String[] getElementValues(String pageName, boolean isFrameElement, String frameName, String label) {
		List<Element> elements = null;
		String[] values = null;
		if (pageName == null || pageName.trim().equals("")) {
			logger.error("pageName is null or empty : ", pageName);
		} else if (label == null || label.trim().equals("")) {
			logger.error("Label value is null or empty : {}", label);
		} else {
			Map<String, Page> repo = Config.getInstance().pageRepository;
			logger.info("Page = [{}] and Element = [{}]", pageName, label);
			if (!repo.containsKey(pageName)) {
				logger.error("Page Map does not contain page - [{}] - Check page.xml file.", pageName);
				throw new PageNotFoundException(pageName + " not found in the xml.");
			} else {
				Page page = repo.get(pageName);
				if (!isFrameElement) {
					elements = page.getElements();
				} else {
					if (frameName == null || frameName.trim().equals("")) {
						logger.error("Frame Name is null or Empty : {}", frameName);
					} else {
						List<IFrame> frames = page.getFrames();
						if (frames == null || frames.size() < 1) {
							logger.error("Frame List is null or empty - {}", frames);
						} else {
							boolean frameFound = false;
							for (IFrame f : frames) {
								if (f.getName().trim().equals(frameName.trim())) {
									logger.info("Found Frame - [{}] in page - [{}]", frameName, page.getName());
									elements = f.getElements();
									frameFound = true;
									break;
								}
							}
							if (!frameFound) {
								logger.error("Could not find the frame {} in page {}", frameName, pageName);
								throw new FrameNotFoundExecption(frameName + " frame not found in Page " + pageName);
							}
						}
					}
				}
			}
			if (elements == null || elements.size() < 1) {
				logger.error("Could not find any elements - List is null or empty - {}", elements);
			} else {
				values = getElementValue(elements, label);
			}
		}
		return values;
	}

	private static String[] getElementValue(List<Element> elements, String label) {
		String values[] = null;
		boolean eFound = false;
		for (Element e : elements) {
			if (label.trim().equalsIgnoreCase(e.getName().trim())) {
				logger.info("Element - [{}] is found with Locator Type = [{}] and Locator Value = [{}]", label,
						e.getLocator(), e.getValue());
				values = new String[3];
				values[0] = e.getLocator();
				values[1] = e.getValue();
				values[2] = String.valueOf(e.getTimeOut());
				eFound = true;
				break;
			}
		}
		if (!eFound) {
			logger.error("Element [{}] not found.", label);
		}
		return values;
	}

	public static Page getPage(String pageName) {
		Page page = null;
		if (GenericUtil.isNull(pageName)) {
			logger.error("Page name is null or empty : {}", pageName);
		} else {
			if (config.pageRepository.containsKey(pageName)) {
				logger.info("Page Found : {}", pageName);
				page = config.pageRepository.get(pageName);
			} else {
				logger.error("Page [{}] not found in page xml.", pageName);
				EReporter.log(LogStatus.INFO, "Page [{" + pageName + "}] not found in page xml.");
			}
		}
		return page;
	}

	public static IFrame getFrame(String pageName, String frameName) {
		IFrame f = null;
		Page p = getPage(pageName);
		if (!GenericUtil.isNull(frameName)) {
			if (p != null) {
				List<IFrame> frames = p.getFrames();
				if (frames != null && frames.size() > 0) {
					for (IFrame frame : frames) {
						if (frameName.trim().equals(frame.getName())) {
							f = frame;
							break;
						}
					}
				}
				if (f == null) {
					logger.error("Could not find the frame {} in page {}.", frameName, pageName);
					EReporter.log(LogStatus.INFO, "Could not find the frame " + frameName + " in page " + pageName);
				}
			}
		} else {
			logger.error("Frame Name null or empty.");
			EReporter.log(LogStatus.INFO, "Frame name is empty or null.");
		}
		return f;
	}

	public static Element getPageElement(String pageName, String elementName) {
		logger.info("==> Entering getPageElement method.");
		Page page = getPage(pageName);
		Element el = null;
		if (page == null) {
			logger.error("Could not get the page with the name : {}", pageName);
		} else {
			if (GenericUtil.isNull(elementName)) {
				logger.error("Element name is null or empty : {}", elementName);
			} else {
				List<Element> elements = page.getElements();
				boolean eFound = false;
				for (Element e : elements) {
					if (e.getName().trim().equalsIgnoreCase(elementName)) {
						el = e;
						eFound = true;
						logger.info("Found Element : {}", elementName);
						break;
					}
				}
				if (!eFound) {
					logger.error("Could not find the element [{}] in page [{}] inside the xml.", elementName, pageName);
					logger.error("Could not find the element [" + elementName + "] in page [" + pageName
							+ "] inside the xml.", elementName, pageName);
				}
			}
		}
		logger.info("<== Exiting getPageElement method.");
		return el;
	}

	/**
	 * Get the element object
	 * 
	 * @param pageName    - page name
	 * @param frameName   - frame name
	 * @param elementName - element label
	 * @return Element object if element is found, else null
	 */
	public static Element getPageElement(String pageName, String frameName, String elementName) {
		Page page = getPage(pageName);
		Element el = null;
		if (page != null) {
			List<Element> elements = null;
			if (!GenericUtil.isNull(frameName)) {
				List<IFrame> frames = page.getFrames();
				for (IFrame frame : frames) {
					if (frameName.equals(frame.getName())) {
						elements = frame.getElements();
						break;
					}
				}
			} else {
				elements = page.getElements();
			}
			boolean elementFound = false;
			if (!GenericUtil.isNull(elementName) && elements != null) {
				for (Element e : elements) {
					if (elementName.equals(e.getName())) {
						el = e;
						elementFound = true;
						break;
					}
				}
			}
			if (!elementFound) {
				logger.error("Could not find the element [{}] in page [{}] and frame [{}] in the page xml.",
						elementName, pageName, frameName);
				EReporter.log(LogStatus.INFO, "Could not find the element [" + elementName + "] in page [" + pageName
						+ "] and frame [" + frameName + "] in the page xml.");
			}
		}
		return el;
	}

	static List<PreCondition> getPreconditions(String pageName) {
		logger.info("==> Entering getPreconditions method.");
		Page page = getPage(pageName);
		return page.getPreconditions();
	}

	public static Mobile getMobileTag(String pageName) {
		logger.info("==> Entering getPreconditions method.");
		Page page = getPage(pageName);
		return page.getMobileTag();
	}

	static boolean isConditionPresent(List<PreCondition> pcList, String conditionName) {
		for (PreCondition pc : pcList) {
			if (pc.getType().equalsIgnoreCase(conditionName)) {
				return true;
			}
		}
		return false;
	}

	static String[] getPostconditions(String pageName) {
		logger.info("==> Entering getPreconditions method.");
		Page page = getPage(pageName);
		String[] postconditions = null;
		if (page == null) {
			logger.error("Could not get the page value...");
		} else {
			String s = page.getPostconditions();
			if (!GenericUtil.isNull(s)) {
				logger.info("No preconditions defined for the page...");
			} else {
				logger.info("Preconditions for the page are - {}", s);
				postconditions = s.split(";");
			}
		}
		logger.info("<== Exiting getPreconditions method.");
		return postconditions;
	}

	/**
	 * Same as navigateToPage method but navigation will happen even if driver is on
	 * the same page as the page to be navigated.
	 * 
	 * @param pageName
	 * @param d
	 * @return
	 */
	public static boolean forceNavigate(String pageName, WebDriver d) {
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		logger.info("==> Entering force navigateToPage method.");
		logger.info("Navigate to : {}", pageName);
		boolean status = false;
		List<Page> allPages = config.pageRepo; // loadAllPages("ALKAMI_Pages.xml");
		boolean isMenuItem = false;
		String containerPage = "";
		Page currentPage = null;
		if (allPages == null || allPages.size() < 1) {
			logger.error("Could not get page info from the xml", allPages);
		} else if (pageName == null || pageName.trim().equals("")) {
			logger.error("PAGE NAME IS NULL OR EMPTY - {}", pageName);
		} else {
			boolean pageFound = false;
			for (Page page : allPages) {
				if (pageName.trim().equals(page.getName().trim())) {
					containerPage = page.getContainerPage();
					currentPage = page;
					pageFound = true;
					break;
				}
			}
			if (!pageFound) {
				logger.error("PAGE [{}]  NOT FOUND IN THE XML", pageName);
				logger.error("RETURNING FROM THE navigate MEHTOD.");
				return status;
			}
			List<PreCondition> list = getPreconditions(pageName);
			boolean isDeepLink = false;
			if (list != null && list.size() > 0) {
				if (isConditionPresent(list, "deep_link")) {
					isDeepLink = true;
					ConditionProcessor.processPreconditions(d, list);
					// 10 Nov
					status = true;
					config.setCurrentPage(pageName);
				}
			}
			if (!isDeepLink) {
				logger.info("CurrentPage from config is ::::: {}", config.getCurrentPage());
				logger.info("Current Page is - {}", currentPage.getName());
				logger.info(
						"Current Page Details \nName : {}\nisMenuItem : {}\nparentMenu : {}\ncontainerPage : {}\nlocator : {}\nlocatorValue : {}",
						currentPage.getName(), currentPage.getIsMenuItem(), currentPage.getParentMenu(),
						currentPage.getContainerPage(), currentPage.getLocator(), currentPage.getLocatorValue());
				logger.info("IS_HOME_PAGE VALUE IS:::::::::::::::::::" + currentPage.getIsHomePage());
				// removed the check if the page to navigate is same as the current
				// page displayed. created bugs when navigation was done other
				// navigateToPage method..
				/*
				 * if (currentPage.getName().equalsIgnoreCase(config.getCurrentPage())) {
				 * logger.info("Current page == config.currentPage ");
				 * logger.info("<==Exiting navigateToPage method."); return true; } else
				 */
				if (currentPage.getIsHomePage() && !config.getCurrentPage().equalsIgnoreCase(currentPage.getName())) {
					gotoHomePage(d, currentPage);
					logger.info("<==Exiting navigate method - navigation to home page");
					return true;
				} else {
					Element parentMenu = null;
					WebElement we;
					if (!(currentPage.getIsMenuItem() == null) && currentPage.getIsMenuItem()
							&& !(currentPage.getParentMenu() == null)
							&& !currentPage.getParentMenu().trim().equals("")) {
						logger.info("Page is Menu Item");
						isMenuItem = true;
						String parentPage;
						if (currentPage.getIsHomePage()) {
							parentPage = currentPage.getName();
						} else {
							parentPage = currentPage.getContainerPage();
						}
						parentMenu = getPageElement(parentPage, currentPage.getParentMenu());
					}
					if (isMenuItem && parentMenu != null) {
						we = getWebElement(d, parentMenu.getLocator(), parentMenu.getValue(), 30);
					} else {
						we = getWebElement(d, currentPage.getLocator(), currentPage.getLocatorValue(), 30);
					}
					logger.info("Element found in the current page.");
					if (we == null) {
						navigateToPage(containerPage, d);
					}
				}
				if (isMenuItem) {
					clickOnMenu(allPages, currentPage, d);
				}
				WebElement we = getWebElement(d, currentPage.getLocator(), currentPage.getLocatorValue(), 30);
				if (we != null) {
					logger.info("Clicking on element with locator = [{}] & value = [{}]", currentPage.getLocator(),
							currentPage.getLocatorValue());
					// we.click();
					SeleniumUtil.click(we);
					// ConditionProcessor.processPreconditions(d, pageName,"");
					config.setCurrentPage(pageName);
					status = true;
				} else {
					logger.error("Web Element is null - {}", we);
				}
			}
		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible(100);
		SeleniumUtil.waitForPageToLoad(2000);
		logger.info("<== Exiting force NavigateToPage method.");
		return status;
	}

	/**
	 * Check is the element is present in the cobrand. If the element is not present
	 * in the cobrand, 'locator' attribute value should be 'null' or 'value'
	 * attribute value should be 'null'.
	 * 
	 * @param elementName - element name as defined in the page xml.
	 * @param pageName    - name of the page as defined in the page xml.
	 * @param frameName   - frame name as defined in the page xml if the element is
	 *                    present inside the frame.
	 * @return - true if both the locator and value attributes have the value other
	 *         than 'null' or empty string, else false if both or any one has the
	 *         value as 'null', or the element is not present in the page xml.
	 */
	public static boolean isElementPresent(String elementName, String pageName, String frameName) {
		Element el = getPageElement(pageName, frameName, elementName);
		if (el == null) {
			EReporter.log(LogStatus.INFO, "The Element [" + elementName + "] is not present in the Co-brand. ");
			return false;
		} else {
			String locator = el.getLocator();
			String locatorValue = el.getValue();
			if (GenericUtil.isNull(locator) || "null".equalsIgnoreCase(locator.trim())
					|| GenericUtil.isNull(locatorValue) || "null".equalsIgnoreCase(locatorValue.trim())) {
				EReporter.log(LogStatus.INFO, "The Element [" + elementName + "] is not present in the Co-brand. ");
				return false;
			} else
				return true;
		}
	}

	// For Testing purpose
	/*
	 * public static void main(String[] args) { Config.createInstance("BAC"); config
	 * = Config.getInstance();
	 * System.out.println(isElementPresent("CautionDeleteOK", "AccountsPage",
	 * "CautionFloater")); }
	 */

}