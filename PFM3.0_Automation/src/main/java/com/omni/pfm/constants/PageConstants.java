/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.constants;

/**
 * @author Suhaib
 *
 */

enum PageConstant {
    HomePage, AccountsPage, TransactionPage, BudgetPage, NetworthPage, PortfolioManagerPage, RewardsPage, ExpenseAnalysisPage, CreditCardPage, CashFlowAnalysisPage, AccountsAndSitesPage, AccountGroupsPage, AlertsPage
}
enum PageConstant_LoginType {
	SAML1, SAML2, OBO, DeepLink;
	
}
public class PageConstants {
 
    public static final String SAML1 = "SAML1";
    public static final String SAML2 = "SAML2";
    public static final String OBO = "OBO";
    public static final String DeepLink = "DEEPLINK";
    public static final String L1NODE = "L1NODE";
    public static final String MOBILE = "MOBILE";
    public static final String L1IFRAME = "L1IFRAME";
    public static final String L1OLB = "L1OLB";
 
    
    
    /**************** Page Names *****************************/
    public static String NEW_USER_FOR_SAML="NO";
   	public static final String FIDELITY_COBRAND = "FIDILITY";
   	public static final String coBrandName = "FIDILITY";
       public static final String HomePage = "HomePage";
       public static final String AccountsPage = "AccountsPage";
       public static final String TransactionPage = "TransactionPage";
       public static final String BudgetPage = "BudgetPage";
       public static final String NetworthPage = "NetworthPage";
       public static final String PortfolioManagerPage = "PortfolioManagerPage";
       public static final String RewardsPage = "RewardsPage";
       public static final String ExpenseAnalysisPage = "ExpenseAnalysisPage";
       public static final String CreditCardPage = "CreditCardPage";
       public static final String CashFlowAnalysisPage = "CashFlowAnalysisPage";
       public static final String AccountsAndSitesPage = "AccountsAndSitesPage";
       public static final String AccountGroupsPage = "AccountGroupsPage";
       public static final String AlertsPage = "AlertsPage";
       public static final String DashboardPage = "DashboardPage";
       public static final String BillReminderPage = "BillReminderPage";
       public static final String RealEstatePage = "RealEstatePage";
       public static final String SaveForGoalpage = "SaveForGoalPage";
       public static final String FIN_CALENDERPAGE = "CalendarPage";
       public static String LiabilityAllocationPage="DashboardPage";
       public static String NetWorthPageDropArea="AccountsPage";
       /*********************** Finapp Names ******************************/
       public static String FINAPP_DROP_AREA = null;
       public static String TRAN_FINAPP_DROP_AREA = "AccountSummaryBankAccount";
       public static final String FINANCIAL_CALENDAR_FINAPP = "FinancialCalendarFinApp";
       /********** Account Summary Page Constants ************/
       public static final String ACCOUNT_VISIBILITY_HIDDEN = "Hidden";
       public static final String ACCOUNT_VISIBILITY_VISIBLE = "Visible";
       /********** Account Summary Page Constants Ends ************/
       /********** Anindita - Expense Analysis Page Constants ************/
       public static final String INCREASING = "INCREASING";
       public static final String DECREASING = "DECREASING";
   	public static final Integer ManualAccount_CreditIndex = 5;
   	
   	
}