/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.yodlee.customizationtool;

import com.yodlee.customizationtool.basetest.BaseTest;
import com.yodlee.customizationtool.page.LoginPage;
import com.yodlee.customizationtool.page.ViewListOfSubBrandsPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import java.util.*;

public class ViewListOfSubBrands extends BaseTest {

    private Logger log = LoggerFactory.getLogger(ViewListOfSubBrands.class);

    LoginPage loginPage;
    ViewListOfSubBrandsPage viewListOfSubBrandsPage;

    @BeforeClass
    public void init() throws InterruptedException {
        loginPage.loginToSubBrandManagementPortal(
                dataProperty.getLoginUsername(), dataProperty.getLoginPassword());
        Thread.sleep(2000);
    }

    @AfterClass
    public  void logout(){
        viewListOfSubBrandsPage.logout();
    }

    @BeforeTest
    public void setup() {
        loginPage = PageFactory.initElements(getWebdriver(), LoginPage.class);
        viewListOfSubBrandsPage = PageFactory.initElements(getWebdriver(), ViewListOfSubBrandsPage.class);
    }

    @Test(priority = 0, enabled = true)
    public void testValidateViewListOfSubBrandPageContents() {

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(viewListOfSubBrandsPage.isPresentCreateSubBrandButton(),
                "Home Page Not Displayed/Create Sub-Brand Button Not Found. Please Check!");
        log.info("Successfully Logged in to Sub-Brand Management Portal Page.");
        log.info("Create Sub-Brand Button Validated Succcessfully.");

        softAssert.assertTrue(viewListOfSubBrandsPage.isEnabledCreateSubBrandButton(),
                "Create Sub-Brand Button Not Enabled. Please Check!");
        log.info("View List Of Sub Brand Page - Create Sub-Brand Button is Enabled By Default.");

        softAssert.assertTrue(viewListOfSubBrandsPage.isPresentSearchFilterTxtBx(),
                "Search Filer Text Box Not Present. Please Check!");
        log.info("View List Of Sub Brand Page - Search By Sub-Brand Text Box Is Present.");

        softAssert.assertTrue(viewListOfSubBrandsPage.colHeaderSubBrandName().isDisplayed(),
                "Column Header Sub Brand Name Not Present");
        log.info("View List Of Sub Brand Page - Column Header Sub Brand Name Is Present.");

        softAssert.assertTrue(viewListOfSubBrandsPage.colHeaderDeploymentStatus().isDisplayed(),
                "Column Header Deployment Status Not Present");
        log.info("View List Of Sub Brand Page - Column Header Deployment Status Is Present.");

        softAssert.assertTrue(viewListOfSubBrandsPage.colHeaderEnvironmentStatus().isDisplayed(),
                "Column Header Environment Status Not Present");
        log.info("View List Of Sub Brand Page - Column Header Environment Status Is Present.");

        softAssert.assertTrue(viewListOfSubBrandsPage.colHeaderLastPublishedStatus().isDisplayed(),
                "Column Header Last Published Not Present");
        log.info("View List Of Sub Brand Page - Column Header Last Published Is Present.");

        softAssert.assertTrue(viewListOfSubBrandsPage.sortBySubBrandNameDD().isDisplayed(),
                "Sorting Option By Sub Brand Name Not Present");
        log.info("View List Of Sub Brand Page - Sorting Option By Sub Brand Name Is Present.");

        softAssert.assertTrue(viewListOfSubBrandsPage.sortByDeploymentStatusDD().isDisplayed(),
                "Sorting Option By Deployment Status Not Present");
        log.info("View List Of Sub Brand Page - Sorting Option By Deployment Status Is Present.");

        softAssert.assertTrue(viewListOfSubBrandsPage.sortByEnvironmentStatusDD().isDisplayed(),
                "Sorting Option By Environment Status Not Present");
        log.info("View List Of Sub Brand Page - Sorting Option By Environment Status Is Present.");

        softAssert.assertTrue(viewListOfSubBrandsPage.sortByLastPublishedDD().isDisplayed(),
                "Sorting Option By Last Published Not Present");
        log.info("View List Of Sub Brand Page - Sorting Option By Last Published Is Present.");

        softAssert.assertTrue(viewListOfSubBrandsPage.pageNumberTextBox().isDisplayed(),
                "Page Number Text Box Not Present");
        log.info("View List Of Sub Brand Page - Pagination, Page Number Text Box Is Present.");

        softAssert.assertAll();
    }

    @Test(priority = 1, enabled = true)
    public void testValidateSearchForNonExistantSubBrand() {

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(viewListOfSubBrandsPage.isPresentCreateSubBrandButton(),
                "Home Page Not Displayed/Create Sub-Brand Button Not Found. Please Check!");
        log.info("Successfully Logged in to Sub-Brand Management Portal Page.");
        log.info("Create Sub-Brand Button Validated Successfully.");

        String subBrandName= viewListOfSubBrandsPage.getFirstSubBrandFromHomePage();

        viewListOfSubBrandsPage.searchBySubBrand(subBrandName+"*&@^#@#");

        softAssert.assertEquals(viewListOfSubBrandsPage.noResultsFoundTextLabel().getText().trim(),
                dataProperty.getNoResultsFoundText(),"Actual And Expected Results Do Not Match. Actual Is: "
                        +viewListOfSubBrandsPage.noResultsFoundTextLabel().getText()+ " Expected Is: "
                        +dataProperty.getNoResultsFoundText());

        softAssert.assertTrue(viewListOfSubBrandsPage.closeSearchFilterLink().isDisplayed(),
                "Close Button Not Found In Search Filter After Adding Search Criteria.");
        if(viewListOfSubBrandsPage.closeSearchFilterLink().isDisplayed())
            viewListOfSubBrandsPage.closeSearchFilterLink().click();

        softAssert.assertAll();
    }

    @Test(priority = 2, enabled = true)
    public void testValidateSearchForExistingSubBrand() {

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(viewListOfSubBrandsPage.isPresentCreateSubBrandButton(),
                "Home Page Not Displayed/Create Sub-Brand Button Not Found. Please Check!");
        log.info("Successfully Logged in to Sub-Brand Management Portal Page.");
        log.info("Create Sub-Brand Button Validated Succcessfully.");

        String subBrandName= viewListOfSubBrandsPage.getFirstSubBrandFromHomePage();

        viewListOfSubBrandsPage.searchBySubBrand(subBrandName);
        List<WebElement> el = viewListOfSubBrandsPage.getSubBrandNames();

        log.info("Following Is The List Of Sub Brand Names Based On The Search Criteria ");
        if (el.size() > 0) {
            for (WebElement e : el) {
                softAssert.assertTrue(e.getText().contains(subBrandName));
                log.info("SubBrand Name Is: " + e.getText());
            }
        }

        if(viewListOfSubBrandsPage.closeSearchFilterLink().isDisplayed())
            viewListOfSubBrandsPage.closeSearchFilterLink().click();

        softAssert.assertAll();
    }

    @Test(priority = 3, enabled = true)
    public void testValidateSearchFilterWorksOnlyWhenMinimum3CharacterAreEnteredInSearchFilter() {

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(viewListOfSubBrandsPage.isPresentCreateSubBrandButton(),
                "View List Of Sub Brand Page Not Displayed/Create Sub-Brand Button Not Found. Please Check!");
        log.info("Successfully Logged in to Sub-Brand Management Portal Page.");
        log.info("Create Sub-Brand Button Validated Successfully.");

        String totalSubBrands = viewListOfSubBrandsPage.totalSubrands().getText();

        viewListOfSubBrandsPage.searchBySubBrand("We");

        String totalSubBrandAfterSearch = viewListOfSubBrandsPage.totalSubrands().getText();

        if(totalSubBrandAfterSearch.equalsIgnoreCase(totalSubBrands))
            softAssert.assertTrue(totalSubBrandAfterSearch.equalsIgnoreCase(totalSubBrands),
                    "View List Of Sub Brand Page: Total Sub Brand Count Before Search & After Search Are Not Matching. " +
                            "Search Filter Is Working When Less Than 3 Characters Are Specified In The Search Criteria.");

        if(viewListOfSubBrandsPage.closeSearchFilterLink().isDisplayed())
            viewListOfSubBrandsPage.closeSearchFilterLink().click();

        softAssert.assertAll();
    }

    @Test(priority = 4, enabled = true)
    public void testValidateSortingByColumnNames() throws InterruptedException {

        getWebdriver().navigate().refresh();
        Thread.sleep(2000);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(viewListOfSubBrandsPage.isPresentCreateSubBrandButton(),
                "View List Of Sub Brand Page  Not Displayed/Create Sub-Brand Button Not Found. Please Check!");
        log.info("Successfully Logged in to Sub-Brand Management Portal Page.");
        log.info("Create Sub-Brand Button Validated Successfully.");

        log.info("***************************BEGIN: SORT BY SUB BRAND NAME********************************************");

        ArrayList<String> defaultOrderedListOfSubBrands = viewListOfSubBrandsPage.getSubBrandNamesColumnContents();

        viewListOfSubBrandsPage.sortBySubBrandNameDD().click();
        String sortTypeSubBrandName = viewListOfSubBrandsPage.sortTypeSubBrandName().getAttribute("aria-sort");
        softAssert.assertEquals(sortTypeSubBrandName,"ascending",
                "View List Of Sub Brand Page: Sub Brands Are Not Sorted In Ascending Order By Default.");

        ArrayList<String> ascendingOrderedListOfSubBrands = viewListOfSubBrandsPage.getSubBrandNamesColumnContents();
        Collections.sort(defaultOrderedListOfSubBrands);

        softAssert.assertEquals(ascendingOrderedListOfSubBrands.toString(), defaultOrderedListOfSubBrands.toString(),
                "View List Of Sub Brand Page: Sort Sub Brand Name Column In Ascending Order: Actual And Expected Sort Results Do Not Match. " +
                        "Actual Is " + ascendingOrderedListOfSubBrands.toString() + " Expected Is "+defaultOrderedListOfSubBrands.toString());

        viewListOfSubBrandsPage.sortBySubBrandNameDD().click();
        sortTypeSubBrandName = viewListOfSubBrandsPage.sortTypeSubBrandName().getAttribute("aria-sort");
        softAssert.assertEquals(sortTypeSubBrandName,"descending",
                "View List Of Sub Brand Page: Sub Brands Are Not Sorted In Descending Order Even Upon Clicking On Sort By Sub Brand Name Link.");

        ArrayList<String> descendingOrderedListOfSubBrands = viewListOfSubBrandsPage.getSubBrandNamesColumnContents();
        Collections.sort(defaultOrderedListOfSubBrands, Collections.reverseOrder());

        softAssert.assertEquals(descendingOrderedListOfSubBrands.toString(), defaultOrderedListOfSubBrands.toString(),
                "View List Of Sub Brand Page: Sort Sub Brand Name Column In Descending Order: Actual And Expected Sort Results Do Not Match. " +
                        "Actual Is " + descendingOrderedListOfSubBrands.toString() + " Expected Is "+defaultOrderedListOfSubBrands.toString());


        log.info("***************************END: SORT BY SUB BRAND NAME********************************************");

        getWebdriver().navigate().refresh();
        Thread.sleep(2000);

        log.info("***************************BEGIN: SORT BY DEPLOYMENT STATUS********************************************");

        ArrayList<String> fixedListOfDeploymentStatus = viewListOfSubBrandsPage.getFixedListOfDeploymentStatus();

        viewListOfSubBrandsPage.sortByDeploymentStatusDD().click();
        String sortTypeDeploymentStatus = viewListOfSubBrandsPage.sortTypeDeploymentStatus().getAttribute("aria-sort");
        softAssert.assertEquals(sortTypeDeploymentStatus,"ascending",
                "View List Of Sub Brand Page: Deployment Status Is Not Sorted In Ascending Order By Default.");

        ArrayList<String> ascendingOrderedListOfDeploymentStatus = viewListOfSubBrandsPage.getDeploymentStatusColumnContents();

        ArrayList<String> finalAscendingOrderSortedListOfDeploymentStatus =viewListOfSubBrandsPage.getAscendingSortedList(fixedListOfDeploymentStatus,ascendingOrderedListOfDeploymentStatus);

        softAssert.assertEquals(ascendingOrderedListOfDeploymentStatus.toString(), finalAscendingOrderSortedListOfDeploymentStatus.toString(),
                "View List Of Sub Brand Page: Sort Deployment Status Column In Ascending Order: Actual And Expected Sort Results Do Not Match. " +
                        "Actual Is " + ascendingOrderedListOfDeploymentStatus.toString() + " Expected Is "+finalAscendingOrderSortedListOfDeploymentStatus.toString());

        viewListOfSubBrandsPage.sortByDeploymentStatusDD().click();
        sortTypeDeploymentStatus = viewListOfSubBrandsPage.sortTypeDeploymentStatus().getAttribute("aria-sort");
        softAssert.assertEquals(sortTypeDeploymentStatus,"descending",
                "View List Of Sub Brand Page: Deployment Status Is Not Sorted In Descending Order Even Upon Clicking On Sort By Deployment Status Column DD.");

        ArrayList<String> descendingOrderedListOfDeploymentStatus = viewListOfSubBrandsPage.getDeploymentStatusColumnContents();

        ArrayList<String> finalDescendingOrderSortedListOfDeploymentStatus =viewListOfSubBrandsPage.getDescendingSortedList(fixedListOfDeploymentStatus,descendingOrderedListOfDeploymentStatus);

        softAssert.assertEquals(descendingOrderedListOfDeploymentStatus.toString(), finalDescendingOrderSortedListOfDeploymentStatus.toString(),
                "View List Of Sub Brand Page: Sort Deployment Status Column In Descending Order: Actual And Expected Sort Results Do Not Match. " +
                        "Actual Is " + descendingOrderedListOfDeploymentStatus.toString() + " Expected Is "+finalDescendingOrderSortedListOfDeploymentStatus.toString());

        log.info("***************************END: SORT BY DEPLOYMENT STATUS********************************************");

        getWebdriver().navigate().refresh();
        Thread.sleep(2000);

        log.info("***************************BEGIN: SORT BY ENVIRONMENT STATUS********************************************");

        ArrayList<String> fixedListOfEnvironmentStatus = viewListOfSubBrandsPage.getFixedListOfEnvironmentStatus();

        viewListOfSubBrandsPage.sortByEnvironmentStatusDD().click();
        String sortTypeEnvironmentStatus = viewListOfSubBrandsPage.sortTypeEnvironmentStatus().getAttribute("aria-sort");
        softAssert.assertEquals(sortTypeEnvironmentStatus,"ascending",
                "View List Of Sub Brand Page: Environment Status Is Not Sorted In Ascending Order By Default.");

        ArrayList<String> ascendingOrderedListOfEnvironmentStatus = viewListOfSubBrandsPage.getEnvironmentStatusColumnContents();

        ArrayList<String> finalAscendingOrderSortedListOfEnvironmentStatus =viewListOfSubBrandsPage.getAscendingSortedList(fixedListOfEnvironmentStatus,ascendingOrderedListOfEnvironmentStatus);

        softAssert.assertEquals(ascendingOrderedListOfEnvironmentStatus.toString(), finalAscendingOrderSortedListOfEnvironmentStatus.toString(),
                "View List Of Sub Brand Page: Sort Environment Status Column In Ascending Order: Actual And Expected Sort Results Do Not Match. " +
                        "Actual Is " + ascendingOrderedListOfEnvironmentStatus.toString() + " Expected Is "+finalAscendingOrderSortedListOfEnvironmentStatus.toString());


        viewListOfSubBrandsPage.sortByEnvironmentStatusDD().click();
        sortTypeEnvironmentStatus = viewListOfSubBrandsPage.sortTypeEnvironmentStatus().getAttribute("aria-sort");
        softAssert.assertEquals(sortTypeEnvironmentStatus,"descending",
                "View List Of Sub Brand Page: Environment Status Is Not Sorted In Descending Order Even Upon Clicking On Sort By Environment Status Column DD.");

        ArrayList<String> descendingOrderedListOfEnvironmentStatus = viewListOfSubBrandsPage.getEnvironmentStatusColumnContents();

        ArrayList<String> finalDescendingOrderSortedListOfEnvironmentStatus =viewListOfSubBrandsPage.getDescendingSortedList(fixedListOfEnvironmentStatus,descendingOrderedListOfEnvironmentStatus);

        softAssert.assertEquals(descendingOrderedListOfEnvironmentStatus.toString(), finalDescendingOrderSortedListOfEnvironmentStatus.toString(),
                "View List Of Sub Brand Page: Sort Environment Status Column In Descending Order: Actual And Expected Sort Results Do Not Match. " +
                        "Actual Is " + descendingOrderedListOfEnvironmentStatus.toString() + " Expected Is "+finalDescendingOrderSortedListOfEnvironmentStatus.toString());

        log.info("***************************END: SORT BY ENVIRONMENT STATUS********************************************");

        /*getWebdriver().navigate().refresh();
        Thread.sleep(2000);

        log.info("***************************BEGIN: SORT BY LAST PUBLISHED STATUS********************************************");

        ArrayList<String> defaultOrderedListOfLastPublished = viewListOfSubBrandsPage.getLastPublishedColumnContents();

        viewListOfSubBrandsPage.sortByLastPublishedDD().click();
        String sortTypeLastPublished = viewListOfSubBrandsPage.sortTypeLastPublished().getAttribute("aria-sort");
        softAssert.assertEquals(sortTypeLastPublished,"ascending",
                "Last Published Status Is Not Sorted In Ascending Order By Default.");

        ArrayList<String> ascendingOrderedListOfLastPublished = viewListOfSubBrandsPage.getLastPublishedColumnContents();
        Collections.sort(defaultOrderedListOfLastPublished);

        softAssert.assertEquals(ascendingOrderedListOfLastPublished.toString(), defaultOrderedListOfLastPublished.toString(),
                "Sort Last Published Column In Ascending Order: Actual And Expected Sort Results Do Not Match. " +
                        "Actual Is " + ascendingOrderedListOfLastPublished.toString() + " Expected Is "+defaultOrderedListOfLastPublished.toString());

        viewListOfSubBrandsPage.sortByLastPublishedDD().click();
        sortTypeLastPublished = viewListOfSubBrandsPage.sortTypeLastPublished().getAttribute("aria-sort");
        softAssert.assertEquals(sortTypeLastPublished,"descending",
                "Last Published Status Is Not Sorted In Descending Order Even Upon Clicking On Sort By Last Published Status Column DD.");

        ArrayList<String> descendingOrderedListOfLastPublished = viewListOfSubBrandsPage.getLastPublishedColumnContents();
        Collections.sort(defaultOrderedListOfLastPublished, Collections.reverseOrder());

        softAssert.assertEquals(ascendingOrderedListOfLastPublished.toString(), defaultOrderedListOfLastPublished.toString(),
                "Sort Last Published Column In Descending Order: Actual And Expected Sort Results Do Not Match. " +
                        "Actual Is " + descendingOrderedListOfLastPublished.toString() + " Expected Is "+descendingOrderedListOfLastPublished.toString());

        log.info("***************************END: SORT BY LAST PUBLISHED********************************************");*/

        softAssert.assertAll();
    }
}