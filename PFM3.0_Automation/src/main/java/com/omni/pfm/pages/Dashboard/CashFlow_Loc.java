/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Dashboard;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.pages.Dashboard.CashFlow_Loc;
import com.omni.pfm.utility.SeleniumUtil;

public class CashFlow_Loc {
	
	

	private static final Logger logger = LoggerFactory.getLogger(CashFlow_Loc.class);
	public static WebDriver d=null;
	
	 public CashFlow_Loc(WebDriver d) {
		 this.d=d;
	}

	
	 public WebElement CashFlowHeader() {
			return SeleniumUtil.getWebElement(d, "Header_CF", "DashboardPage", null);
		}
	 public WebElement CashFlowWidgetCard() {
			return SeleniumUtil.getWebElement(d, "WidgetCard_CF", "DashboardPage", null);
		}	
	 public WebElement CashFlowFinappHeader() {
			return SeleniumUtil.getWebElement(d, "CFheader_CF", "DashboardPage", null);
		}
	 public WebElement CashFlowBacktoDashboard() {
			return SeleniumUtil.getWebElement(d, "BacktoDashboard_CF", "DashboardPage", null);
		}
	 public WebElement CashFlowDefaultTimefilter() {
			return SeleniumUtil.getWebElement(d, "DefaultTimeFilter_CF", "DashboardPage", null);
		} 
	 
	 public List<WebElement> CashFlowInflowbar() {
			return SeleniumUtil.getWebElements(d, "Cashinflowbar_CF", "DashboardPage", null);
		} 
	 
	 public WebElement CFIncomeAnalysisLink() {
			return SeleniumUtil.getWebElement(d, "CFIncomeAnalysisLnk_CF", "DashboardPage", null);
		} 
	 
	 public WebElement CFIncomeAnalysisHeader() {
			return SeleniumUtil.getWebElement(d, "CFIAHeader_CF", "DashboardPage", null);
		} 
	 public WebElement CFIABacktoCashFlow() {
			return SeleniumUtil.getWebElement(d, "CFIABacktoCashFlow_CF", "DashboardPage", null);
		} 
	 
	 public List<WebElement> CashFlowOutflowbar() {
			return SeleniumUtil.getWebElements(d, "Cashoutflowbar_CF", "DashboardPage", null);
		} 
	 
	 
	 public WebElement CFExpenseAnalysisLink() {
			return SeleniumUtil.getWebElement(d, "CFExpenseAnalysisLnk_CF", "DashboardPage", null);
		} 
	 
	 public WebElement CFExpenseAnalysisHeader() {
			return SeleniumUtil.getWebElement(d, "CFEAHeader_CF", "DashboardPage", null);
		} 
	 
	 public WebElement CFFTUEFirstPopup() {
			return SeleniumUtil.getWebElement(d, "FTUEpopup1_CF", "DashboardPage", null);
		} 
	 public WebElement CFFTUEFirstPopupBtn() {
			return SeleniumUtil.getWebElement(d, "FTUEpopupbtn1_CF", "DashboardPage", null);
		} 
	 public WebElement CFFTUESecondPopup() {
			return SeleniumUtil.getWebElement(d, "FTUEpopup2_CF", "DashboardPage", null);
		} 
	 public WebElement CFFTUESecondPopupBtn() {
			return SeleniumUtil.getWebElement(d, "FTUEpopupbtn2_CF", "DashboardPage", null);
		} 
		 
	 public WebElement CFafterSFGwidget_CF() {
			return SeleniumUtil.getWebElement(d, "CFafterSFGwidget_CF", "DashboardPage", null);
		}
	 
	 public List<WebElement> WidgetList_CF() {
			return SeleniumUtil.getWebElements(d, "WidgetList_CF", "DashboardPage", null);
		}
	 
	public List<WebElement> MonthListinGraph_CF() {
			return SeleniumUtil.getWebElements(d, "MonthListinGraph_CF", "DashboardPage", null);
		} 
	 
	public WebElement MonthListPointsinGraph_CF() {
		return SeleniumUtil.getWebElement(d, "MonthListPointsinGraph_CF", "DashboardPage", null);
	} 
	
	 public List<WebElement> Last6MonthsPointsinGraph_CF() {
		return SeleniumUtil.getWebElements(d, "Last6MonthsPointsinGraph_CF", "DashboardPage", null);
	}
	 
	 public List<WebElement> Last6MonthsPointsinGraph_CFforMobile() {
			return SeleniumUtil.getWebElements(d, "Last6MonthsPointsinGraph_CFforMobile", "DashboardPage", null);
		}
	 
	 public WebElement CashInFlowKey_CF() {
			return SeleniumUtil.getWebElement(d, "CashInFlowKey_CF", "DashboardPage", null);
		} 
	 
	 public WebElement CashOutFlowKey_CF() {
			return SeleniumUtil.getWebElement(d, "CashOutFlowKey_CF", "DashboardPage", null);
		} 
	 public List<WebElement> CurMonth_CF() {
			return SeleniumUtil.getWebElements(d, "CurMonth_CF", "DashboardPage", null);
		} 
	 
	 public WebElement CashFlowFinVerify_CF() {
			return SeleniumUtil.getWebElement(d, "CashFlowFinVerify_CF", "DashboardPage", null);
		} 
	 
	 public WebElement ForecastBarColor_CF() {
			return SeleniumUtil.getWebElement(d, "ForecastBarColor_CF", "DashboardPage", null);
		}
	 
	 public WebElement ForecastChkBoxColor_CF() {
			return SeleniumUtil.getWebElement(d, "ForecastChkBoxColor_CF", "DashboardPage", null);
		}
	 
	 public WebElement CashInFlowBarColor_CF() {
			return SeleniumUtil.getWebElement(d, "CashInFlowBarColor_CF", "DashboardPage", null);
		}
	 public WebElement CashInChkBoxcolor_CF() {
			return SeleniumUtil.getWebElement(d, "CashInChkBoxcolor_CF", "DashboardPage", null);
		}
	 public WebElement CashOutFlowBarColor_CF() {
			return SeleniumUtil.getWebElement(d, "CashOutFlowBarColor_CF", "DashboardPage", null);
		} 
	 public WebElement CashOutChkBoxcolor_CF() {
			return SeleniumUtil.getWebElement(d, "CashOutChkBoxcolor_CF", "DashboardPage", null);
		} 
	 public WebElement MonthsinXaxis_CF() {
			return SeleniumUtil.getWebElement(d, "MonthsinXaxis_CF", "DashboardPage", null);
		} 
	 public WebElement AmountinYaxis_CF() {
			return SeleniumUtil.getWebElement(d, "AmountinYaxis_CF", "DashboardPage", null);
		}  
	 public WebElement ChartImage_CF() {
			return SeleniumUtil.getWebElement(d, "ChartImage_CF", "DashboardPage", null);
		}
	 
	 public WebElement Legends_CF() {
			return SeleniumUtil.getWebElement(d, "Legends_CF", "DashboardPage", null);
		}
	 public WebElement BelowCFWtext_CF() {
			return SeleniumUtil.getWebElement(d, "BelowCFWtext_CF", "DashboardPage", null);
		}
	 
	 public WebElement NoDataMsgCFwidget_CF() {
			return SeleniumUtil.getWebElement(d, "NoDataMsgCFwidget_CF", "DashboardPage", null);
		} 
	 public WebElement NoAccsinCFW_CF() {
			return SeleniumUtil.getWebElement(d, "NoAccsinCFW_CF", "DashboardPage", null);
		}
	 
	 public WebElement LinkAccbtn_CF() {
			return SeleniumUtil.getWebElement(d, "LinkAccbtn_CF", "DashboardPage", null);
		}
	 
	 public WebElement VisttheAppBtn_CF() {
			return SeleniumUtil.getWebElement(d, "VisttheAppBtn_CF", "DashboardPage", null);
		}
	 
	 public List<WebElement> OneMonthData_CF() {
			return SeleniumUtil.getWebElements(d, "OneMonthData_CF", "DashboardPage", null);
		}
	 public WebElement ThreeMonthsData_CF() {
			return SeleniumUtil.getWebElement(d, "ThreeMonthsData_CF", "DashboardPage", null);
		}
	 public WebElement FiltersContainer_CF() {
			return SeleniumUtil.getWebElement(d, "FiltersContainer_CF", "DashboardPage", null);
		}
	 public List<WebElement> CashInflowColumninCFfin_CF() {
			return SeleniumUtil.getWebElements(d, "CashInflowColumninCFfin_CF", "DashboardPage", null);
		} 
	 public WebElement BackToDashinTranFin_CF() {
			return SeleniumUtil.getWebElement(d, "BackToDashinTranFin_CF", "DashboardPage", null);
		}
	 
	 public WebElement BacktoCashFlowBtninTran_CF() {
			return SeleniumUtil.getWebElement(d, "BacktoCashFlowBtninTran_CF", "DashboardPage", null);
		}
	 public WebElement BackToDashinCashFlowFin_CF() {
			return SeleniumUtil.getWebElement(d, "BackToDashinCashFlowFin_CF", "DashboardPage", null);
		}
	 
	 
	 public WebElement BacktoCashFlowBtninSpenFin_CF() {
			return SeleniumUtil.getWebElement(d, "BacktoCashFlowBtninSpenFin_CF", "DashboardPage", null);
		} 
	 
	 public WebElement IncomeLink_CF() {
		 WebElement el=null;
		 boolean f=false;
		 List<WebElement> element=SeleniumUtil.getWebElements(d, "IncomeLink_CF", "DashboardPage", null);
		 for(WebElement elem:element)
		 {
			 try{
				f=elem.isDisplayed();
				if(f==true){
					el=elem;
					break;
				}
			 }catch(Exception ex){
				 el=null;
				 f=false;
			 }
		 }
		 
			return el;//SeleniumUtil.getVisibileWebElement(d, "IncomeLink_CF", "DashboardPage", null);
		}
	 
	 public WebElement verifyCashFlowBacktoDashboard_Mobile() {
			return SeleniumUtil.getWebElement(d, "BacktoDashboard_CF_mobile", "DashboardPage", null);
		}
}
