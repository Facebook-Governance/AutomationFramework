/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.rest;
/*
 * Author: Ramesh Kumar N
 * 
 * 
 * 
 */
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;





import org.databene.feed4testng.FeedTest;
import org.testng.SkipException;




import com.omni.pfm.utility.PropsUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class rest extends FeedTest{
	
	private String restversion;
	public static String userSession;
	
	
	public rest (String restversion, boolean urlEncodingflag,String baseURI) {
		this.restversion= restversion;
		RestAssured.urlEncodingEnabled = urlEncodingflag;
		RestAssured.baseURI = baseURI;
	}
	
	public String getRestVersion () {
		return restversion;
	}
	
	/*public static void updateUserForm(String investor, String advisor,
		    String cobSession) throws JSONException {
			JSONObject loginForm = new JSONObject();
			JSONArray fieldArray = new JSONArray();
			
			JSONObject userDetails = new JSONObject();
			userDetails.put("loginName", advisor);
			userDetails.put("referenceId", "12334567891");
			userDetails.put("roleType", "ADVISOR");

			
			JSONObject associationType = new JSONObject();
			associationType.put("loginName", investor);
			associationType.put("associationType", "INVESTOR");
			fieldArray.put(userDetails);
			fieldArray.put(associationType);

			loginForm.put("user", fieldArray);
			System.out.println("the loginform is : " + loginForm.toString());
			String form = loginForm.toString();

			Response response = RestUtil.putWithBodyParam(form, "http://192.168.112.179:8680/ysl/ymc/v1/user",cobSession);
			response.then().log().all();
			// Assert.assertEquals(response.getStatusCode(), 204);

		}*/
	
	
	// Site based 
	//Method to call 'CobrandLogin' api and generate 'CobLoginSession Token'
		public String getCobrandSessionToken(String cobrandLogin, String cobrandPassword) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("cobrandLogin", cobrandLogin);
			params.put("cobrandPassword", cobrandPassword);
			Response response = RestUtil.post(params,restversion+"/authenticate/coblogin");
			response.then().log().all();
			return response.jsonPath().getString("cobrandConversationCredentials.sessionToken"); 
		}
	
	
	/*Method for register user*/
	public String getNewUserSession(String newUserLoginName, String newUserPassword, String coBrandSessionToken) throws InterruptedException {

		HashMap<String, String> params = new HashMap<String, String> ();
		params.put("cobSessionToken", coBrandSessionToken);
		params.put("userCredentials.loginName",newUserLoginName);
		params.put("userCredentials.password",newUserPassword);
		params.put("userCredentials.objectInstanceType","com.yodlee.ext.login.PasswordCredentials");
		params.put("userProfile.emailAddress","testautomation@yodlee.com");
		params.put("userPreferences[0]","PREFERRED_CURRENCY~USD");
		params.put("userPreferences[1]","PREFERRED_DATE_FORMAT~MM/dd/yyyy");
		params.put("userProfile.firstName","Sakshi");
		params.put("userProfile.lastName","Jain");
		params.put("userProfile.middleInitial","S");
		params.put("userProfile.objectInstanceType","com.yodlee.core.usermanagement.UserProfile");
		params.put("userProfile.address1","3600 Bridge Parkway");
		params.put("userProfile.address2","Suite 200");
		params.put("userProfile.city","Redwood City");
		params.put("userProfile.country","USA");
		Response response = RestUtil.post(params,restversion+"/jsonsdk/UserRegistration/register3");	
		response.then().log().all();
	    userSession= response.jsonPath().getString("userContext.conversationCredentials.sessionToken");
		return response.jsonPath().getString("loginName");
		
	}
	
	public String userSession()
	{
	
		return userSession;
	}
	
	
	/*Method for register user*/
	public String getNewUserSession1(String newUserLoginName, String newUserPassword, String coBrandSessionToken) throws InterruptedException {

		HashMap<String, String> params = new HashMap<String, String> ();
		params.put("cobSessionToken", coBrandSessionToken);
		params.put("userCredentials.loginName",newUserLoginName);
		params.put("userCredentials.password",newUserPassword);
		params.put("userCredentials.objectInstanceType","com.yodlee.ext.login.PasswordCredentials");
		params.put("userProfile.emailAddress","testautomation@yodlee.com");
		params.put("userPreferences[0]","PREFERRED_CURRENCY~USD");
		params.put("userPreferences[1]","PREFERRED_DATE_FORMAT~MM/dd/yyyy");
		params.put("userProfile.firstName","Pavithra");
		params.put("userProfile.lastName","Kiradi");
		params.put("userProfile.middleInitial","S");
		params.put("userProfile.objectInstanceType","com.yodlee.core.usermanagement.UserProfile");
		params.put("userProfile.address1","3600 Bridge Parkway");
		params.put("userProfile.address2","Suite 200");
		params.put("userProfile.city","Redwood City");
		params.put("userProfile.country","USA");
		Response response = RestUtil.post(params,restversion+"/jsonsdk/UserRegistration/register3");	
		response.then().log().all();
		return response.jsonPath().getString("userContext.conversationCredentials.sessionToken");
		
		
	}
	
	
	 private Response GetSiteLoginForm(String siteId, String coBrandSessionToken) {
		    
		    HashMap<String, String> params = new HashMap<String, String> ();
		    params.put("cobSessionToken",coBrandSessionToken);
		    params.put("siteId", siteId);
		    Response SiteLoginformResponse = RestUtil.post(params,restversion+"/jsonsdk/SiteAccountManagement/getSiteLoginForm");
		    SiteLoginformResponse.then().log().all();   
		    return SiteLoginformResponse;
		} 
		
		/*Method to add Site level Account*/
		public String AddSite(String siteId,String dagUserName,String dagPwd, String usersSessionToken, String coBrandSessionToken) throws Exception {            
	        
	        Response SiteLoginformResponse = GetSiteLoginForm(siteId, coBrandSessionToken);
	        String siteAccountId;
	        HashMap<String, String> params = new HashMap<String, String> ();
	        params.put("cobSessionToken", coBrandSessionToken);
	        params.put("userSessionToken", usersSessionToken);
	        params.put("siteId", siteId);
	        params.put("credentialFields.enclosedType", PropsUtil.getEnvPropertyValue("credentialFields.enclosedType"));
	        params.put("credentialFields[0].displayName",SiteLoginformResponse.jsonPath().getString("componentList[0].displayName"));
	        params.put("credentialFields[0].fieldType.typeName",SiteLoginformResponse.jsonPath().getString("componentList[0].fieldType.typeName"));
	        params.put("credentialFields[0].helpText",SiteLoginformResponse.jsonPath().getString("componentList[0].helpText"));
	        params.put("credentialFields[0].maxlength",SiteLoginformResponse.jsonPath().getString("componentList[0].maxlength"));
	        params.put("credentialFields[0].name",SiteLoginformResponse.jsonPath().getString("componentList[0].name"));
	        params.put("credentialFields[0].size",SiteLoginformResponse.jsonPath().getString("componentList[0].size"));
	        params.put("credentialFields[0].value",dagUserName);
	        params.put("credentialFields[0].valueIdentifier",SiteLoginformResponse.jsonPath().getString("componentList[0].valueIdentifier"));
	        params.put("credentialFields[0].valueMask",SiteLoginformResponse.jsonPath().getString("componentList[0].valueMask"));
	        params.put("credentialFields[0].isEditable",SiteLoginformResponse.jsonPath().getString("componentList[0].isEditable"));
	        params.put("credentialFields[1].displayName",SiteLoginformResponse.jsonPath().getString("componentList[1].displayName"));
	        params.put("credentialFields[1].fieldType.typeName",SiteLoginformResponse.jsonPath().getString("componentList[1].fieldType.typeName"));
	        params.put("credentialFields[1].helpText",SiteLoginformResponse.jsonPath().getString("componentList[1].helpText"));
	        params.put("credentialFields[1].maxlength",SiteLoginformResponse.jsonPath().getString("componentList[1].maxlength"));
	        params.put("credentialFields[1].name",SiteLoginformResponse.jsonPath().getString("componentList[1].name"));
	        params.put("credentialFields[1].size",SiteLoginformResponse.jsonPath().getString("componentList[1].size"));
	        params.put("credentialFields[1].value",dagPwd);
	        params.put("credentialFields[1].valueIdentifier",SiteLoginformResponse.jsonPath().getString("componentList[1].valueIdentifier"));
	        params.put("credentialFields[1].valueMask",SiteLoginformResponse.jsonPath().getString("componentList[1].valueMask"));
	        params.put("credentialFields[1].isEditable",SiteLoginformResponse.jsonPath().getString("componentList[1].isEditable"));
	        params.put("shareCredentialsWithinSite","true");
	        params.put("startRefreshItemOnAddition","true");                     
	                     
	        Response response = RestUtil.post(params,restversion+"/jsonsdk/SiteAccountManagement/addSiteAccount1");
	        response.then().log().all();
	        
	        siteAccountId = response.jsonPath().getString("siteAccountId");            
	        System.out.println("\nsiteAccountId = " +siteAccountId);
	        return siteAccountId;
	       }
		
	    /*  Method to call login form for Item */
		private  Response testGetLoginFormForContentService(String contentServiceId,String coBrandSessionToken ) {
			
			HashMap<String, String> params = new HashMap<String, String> ();
			params.put("cobSessionToken",coBrandSessionToken);
			params.put("contentServiceId",contentServiceId);
			 
			Response getLoginform = RestUtil.post(params,restversion+"/jsonsdk/ItemManagement/getLoginFormForContentService");
			getLoginform.then().log().all();
			return 	getLoginform;			
		}	
		
		/*Method to add addAccount for contanier*/
		public  String testAddItem(String contentServiceId,String dagUserName,String dagPwd,String usersSessionToken, String coBrandSessionToken) throws Exception {
			
		   Response getLoginformResponse= testGetLoginFormForContentService(contentServiceId,coBrandSessionToken);
			HashMap<String, String> params = new HashMap<String, String> ();
			params.put("cobSessionToken",coBrandSessionToken);
			params.put("userSessionToken", usersSessionToken);
			params.put("contentServiceId", contentServiceId);
			params.put("credentialFields.enclosedType",  PropsUtil.getEnvPropertyValue("credentialFields.enclosedType"));
			params.put("credentialFields[0].displayName",getLoginformResponse.jsonPath().getString("componentList[0].displayName"));
			params.put("credentialFields[0].fieldType.typeName",getLoginformResponse.jsonPath().getString("componentList[0].fieldType.typeName"));
			params.put("credentialFields[0].helpText",getLoginformResponse.jsonPath().getString("componentList[0].helpText"));
			params.put("credentialFields[0].maxlength",getLoginformResponse.jsonPath().getString("componentList[0].maxlength"));
			params.put("credentialFields[0].name",getLoginformResponse.jsonPath().getString("componentList[0].name"));
			params.put("credentialFields[0].size",getLoginformResponse.jsonPath().getString("componentList[0].size"));
			params.put("credentialFields[0].value",dagUserName);
			params.put("credentialFields[0].valueIdentifier",getLoginformResponse.jsonPath().getString("componentList[0].valueIdentifier"));
			params.put("credentialFields[0].valueMask",getLoginformResponse.jsonPath().getString("componentList[0].valueMask"));
			params.put("credentialFields[0].isEditable",getLoginformResponse.jsonPath().getString("componentList[0].isEditable"));
			params.put("credentialFields[1].displayName",getLoginformResponse.jsonPath().getString("componentList[1].displayName"));
			params.put("credentialFields[1].fieldType.typeName",getLoginformResponse.jsonPath().getString("componentList[1].fieldType.typeName"));
			params.put("credentialFields[1].helpText",getLoginformResponse.jsonPath().getString("componentList[1].helpText"));
			params.put("credentialFields[1].maxlength",getLoginformResponse.jsonPath().getString("componentList[1].maxlength"));
			params.put("credentialFields[1].name",getLoginformResponse.jsonPath().getString("componentList[1].name"));
			params.put("credentialFields[1].size",getLoginformResponse.jsonPath().getString("componentList[1].size"));
			params.put("credentialFields[1].value",dagPwd);
			params.put("credentialFields[1].valueIdentifier",getLoginformResponse.jsonPath().getString("componentList[1].valueIdentifier"));
			params.put("credentialFields[1].valueMask",getLoginformResponse.jsonPath().getString("componentList[1].valueMask"));
			params.put("credentialFields[1].isEditable",getLoginformResponse.jsonPath().getString("componentList[1].isEditable"));
			params.put("shareCredentialsWithinSite","true");
			params.put("startRefreshItemOnAddition","true");			
					
			Response response = RestUtil.post(params,restversion+"/jsonsdk/ItemManagement/addItemForContentService1");
			response.then().log().all();
			
			String itemId = response.jsonPath().getString("primitiveObj");		
	        return itemId;
			
		}	
		
		public  boolean pollRefresh(String itemId,String coBrandSessionToken,String usersSessionToken) throws Exception {
			System.out.println("\nChecking the refresh status...");

			try {
				Response response = getRefreshStatusCode(itemId,coBrandSessionToken,usersSessionToken);
				String statusCode = response.jsonPath().getString("statusCode");
				String status = response.jsonPath().getString("lastDataUpdateAttempt.status.name");
				System.out.println("\nstatusCode = " + statusCode + "\tstatus = "+ status);
				String successStatusCode = "[0]";

				if (statusCode.equalsIgnoreCase("[801]")
						&& status.equalsIgnoreCase("[IN_PROCESS]")) {
					pollRefresh(itemId,coBrandSessionToken,usersSessionToken);
				} else if (statusCode.equalsIgnoreCase(successStatusCode.trim())) {
					System.out.println("\nrefreshStatusCode = " + statusCode);
					System.out.println("\tThe refresh has completed successfully.");
					return true;
				}

			} catch (InterruptedException ex) {
				throw new RuntimeException("Refresh polling has been interrupted!");
			}
			return false;
		}
		private Response getRefreshStatusCode(String itemId,String coBrandSessionToken,String usersSessionToken) {

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("cobSessionToken",coBrandSessionToken);
			params.put("userSessionToken",usersSessionToken);
			params.put("itemIds[0]", itemId);
			Response response = RestUtil.post(params, "/v1.0/jsonsdk/Refresh/getRefreshInfo1");
			response.then().log().all();
			return response;
		}
		
		
		
		
		
		
		public boolean pollRefreshStatus(String cobSession,String userSession,String siteAccountId) throws Exception {          
	        System.out.println ("\nChecking the refresh status...");
	        boolean flag = false;
	        String refreshStatus = "REFRESH_TRIGGERED";
	        String addStatus ="";
	        while (refreshStatus.equals("REFRESH_TRIGGERED")|| refreshStatus.equals("LOGIN_SUCCESS") || refreshStatus.equals("PARTIAL_COMPLETE")) {
	  		 Response siteRefreshInfo = getSiteRefreshInfo(cobSession,userSession,siteAccountId);
	  	  	   refreshStatus = siteRefreshInfo.jsonPath().getString("siteRefreshStatus.siteRefreshStatus");
	  	  	   addStatus = siteRefreshInfo.jsonPath().getString("siteAddStatus.siteAddStatus");
	  	  	   System.out.println("-----------------"+addStatus+"----------------");
	        }
	    	if (refreshStatus.equals("REFRESH_COMPLETED")){
	            if(addStatus.equals("ADDED")) {
	           	 System.out.println("\nrefreshStatus = " + refreshStatus);
	           	 System.out.println("\tThe refresh has completed successfully.");
	           	 flag = true;
	            }
		   	}                   
	        else {
	              flag = false; 
	        }

	     return flag;
		}
		
		
		
		private Response getSiteRefreshInfo(String cobSession, String userSession, String siteaccountId)
		{

		      HashMap<String, String> siteparams = new HashMap<String, String> ();
		      
		      siteparams.put("cobSessionToken", cobSession);  
		      siteparams.put("userSessionToken", userSession);       

		      siteparams.put("memSiteAccId", siteaccountId);
		      
		      Response siteRefreshInfo = RestUtil.post(siteparams,restversion+"/jsonsdk/Refresh/getSiteRefreshInfo");
		      siteRefreshInfo.then().log().all();
		      
		      return siteRefreshInfo;
		}


		  public HashMap<String, String> registerandaddsite(String userName,  String cobrandlogin, String cobrandpassword, String id, String username, String password,String accounts,String enable) throws InterruptedException {
		      
			  
			  if ("FALSE".equalsIgnoreCase(enable))throw new SkipException("Testing skip.");
		        //Boolean flag =true;
		        System.out.println("username :"+ username);
		        //System.out.println("baseURi :"+ baseURi);
		        System.out.println("cobrandlogin :"+ cobrandlogin);
		        System.out.println("cobrandpassword :"+ cobrandpassword);
		        System.out.println("dagsitusername :"+ id);
		        System.out.println("dagsitepassword :"+ username);
		        System.out.println("SiteID :"+ password);
		        HashMap<String, String> params = new HashMap<String, String> ();
		         try {
		                    
		                    
		                    
		                    
		               /*
		                    if(accounts.equals("Site"))
		                    {
		                     String siteAcId=AddSite(id,username ,password, Account_InError_Test.usersSessionToken,  Account_InError_Test.cobrandSessionToken);
		                    pollRefreshStatus(Account_InError_Test.cobrandSessionToken, Account_InError_Test.usersSessionToken, siteAcId);
		                    }
		                    if(accounts.equals("Container"))
		                    {
		                    String itemID=testAddItem(id ,username,password,Account_InError_Test.usersSessionToken,Account_InError_Test.cobrandSessionToken);
		                    pollRefresh(itemID,Account_InError_Test.cobrandSessionToken,Account_InError_Test.usersSessionToken);	
		                		
		                    }
		               }          */
		         }
		              catch (Exception e) {
		                     System.out.println(e.getMessage());
		                     //flag = false;
		               }
		        return params;
		 }

		 public Boolean addAccount (String usersSessionToken, String cobrandSessionToken, String dagsitusername, String dagsitepassword, String SiteID) throws Exception {
		        String siteAcId=AddSite(SiteID,dagsitusername ,dagsitepassword, usersSessionToken, cobrandSessionToken);
		        Boolean addAccountStatus = pollRefreshStatus(cobrandSessionToken, usersSessionToken, siteAcId);
		   
		        return addAccountStatus;
		       
		 }
		
		
		
		
		
		
		
		
		/*
			
			
		  

		public Response getMFAResponseForSite(String cobSession,String userSession,String siteAccountId) throws FileNotFoundException
		
		{
		              HashMap<String, String> getmfaparams = new HashMap<String, String> ();
		              Response getMFAResponse = null;

		              getmfaparams.put("cobSessionToken", cobSession);       
		              getmfaparams.put("userSessionToken", userSession);            
		              getmfaparams.put("memSiteAccId", siteAccountId);
		              String retry = "true";
		              String isMessageAvailable = "false";
		              while (retry.equals("true") && isMessageAvailable.equals("false"))  {
		            	  getMFAResponse = RestUtil.post(getmfaparams,restversion+"/jsonsdk/Refresh/getMFAResponseForSite");
		            	  getMFAResponse.then().log().all();
		            	  retry = getMFAResponse.jsonPath().getString("retry");
		            	  isMessageAvailable = getMFAResponse.jsonPath().getString("isMessageAvailable");
		              }
		           return getMFAResponse;
		}
		
	
	
	
	
	// Method to call 'login' api with new user credential and generate 'userSessionToken'
	public String getNewUserSessionToken(String newUserLoginName, String newUserPassword, String coBrandSessionToken) {
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("login", newUserLoginName);
		params.put("password", newUserPassword);
		params.put("cobSessionToken", coBrandSessionToken);
		Response response = RestUtil.post(params,restversion+"/authenticate/login");
		response.then().log().all();
		return response.jsonPath().getString("userContext.conversationCredentials.sessionToken");			
	}
	   Method to call login form for site
      
	
	
	
	
	public void putMFATokenRequestForSite(String cobSession,String userSession,String siteAccountId,Response getMFAResponse)
	{       
	       HashMap<String, String> putmfaparams = new HashMap<String, String> ();
	       System.out.println(getMFAResponse.toString());
	       putmfaparams.put("cobSessionToken",cobSession); 
	       putmfaparams.put("userSessionToken", userSession);            
	       putmfaparams.put("memSiteAccId", siteAccountId);
	       //putmfaparams.put("userResponse.objectInstanceType","com.yodlee.core.mfarefresh.MFATokenResponse");
	       //putmfaparams.put("userResponse.objectInstanceType","com.yodlee.core.mfarefresh.MFAQuesAnsResponse");
	       putmfaparams.put("userResponse.objectInstanceType","com.yodlee.core.mfarefresh.MFAQuesAnsResponse");
	       putmfaparams.put("userResponse.quesAnsDetailArray[0].answerFieldType", getMFAResponse.jsonPath().getString("fieldInfo.questionAndAnswerValues[0].responseFieldType"));
	       putmfaparams.put("userResponse.quesAnsDetailArray[0].metaData", getMFAResponse.jsonPath().getString("fieldInfo.questionAndAnswerValues[0].metaData"));
	       putmfaparams.put("userResponse.quesAnsDetailArray[0].question", getMFAResponse.jsonPath().getString("fieldInfo.questionAndAnswerValues[0].question"));
	       putmfaparams.put("userResponse.quesAnsDetailArray[0].questionFieldType", getMFAResponse.jsonPath().getString("fieldInfo.questionAndAnswerValues[0].questionFieldType"));
	       putmfaparams.put("userResponse.quesAnsDetailArray[0].answer","Anjani");

	       //putmfaparams.put("userResponse.token","Anjani");
	       //putmfaparams.put("userResponse.captcha","mswm");
	       Response MFAResponse = RestUtil.post(putmfaparams,restversion+"/jsonsdk/Refresh/putMFARequestForSite");
	       MFAResponse.then().log().all();
	}
	
	

	
	public Response getSiteAccountMfaQuesAndAns(String siteaccountId, String usersSessionToken, String coBrandSessionToken) throws Exception {           
        
        HashMap<String, String> params = new HashMap<String, String> ();
        params.put("cobSessionToken",coBrandSessionToken);
        params.put("userSessionToken", usersSessionToken);
        params.put("memSiteAccId", siteaccountId);
       
        Response response = RestUtil.post(params,restversion+"/jsonsdk/SiteAccountManagement/getSiteAccountMfaQuestionsAndAnswers");
        response.then().log().all();
       
        return response;
       
    }
	
	
	
	public String updateSite(String siteid, String siteaccountId,String mfa, String dagUserName,String dagPwd, String usersSessionToken, String coBrandSessionToken) throws Exception {            
		    String siteAccountId;
		    HashMap<String, String> params = new HashMap<String, String> ();
		    Response SiteLoginformResponse = GetSiteLoginForm(siteid, coBrandSessionToken);
		    params.put("cobSessionToken",coBrandSessionToken);
		    params.put("userSessionToken", usersSessionToken);
		    params.put("memSiteAccId", siteaccountId);
		    params.put("credentialFields.enclosedType", PropertyLoader.getEnvProperty("credentialFields.enclosedType"));
		    params.put("credentialFields[0].displayName",SiteLoginformResponse.jsonPath().getString("componentList[0].displayName"));
		    params.put("credentialFields[0].fieldType.typeName",SiteLoginformResponse.jsonPath().getString("componentList[0].fieldType.typeName"));
		    params.put("credentialFields[0].helpText",SiteLoginformResponse.jsonPath().getString("componentList[0].helpText"));
		    params.put("credentialFields[0].maxlength",SiteLoginformResponse.jsonPath().getString("componentList[0].maxlength"));
		    params.put("credentialFields[0].name",SiteLoginformResponse.jsonPath().getString("componentList[0].name"));
		    params.put("credentialFields[0].size",SiteLoginformResponse.jsonPath().getString("componentList[0].size"));
		    params.put("credentialFields[0].value",dagUserName);
		    params.put("credentialFields[0].valueIdentifier",SiteLoginformResponse.jsonPath().getString("componentList[0].valueIdentifier"));
		    params.put("credentialFields[0].valueMask",SiteLoginformResponse.jsonPath().getString("componentList[0].valueMask"));
		    
		    params.put("credentialFields[0].isEditable",SiteLoginformResponse.jsonPath().getString("componentList[1].isEditable"));
		    params.put("credentialFields[1].displayName",SiteLoginformResponse.jsonPath().getString("componentList[1].displayName"));
		    params.put("credentialFields[1].fieldType.typeName",SiteLoginformResponse.jsonPath().getString("componentList[1].fieldType.typeName"));
		    params.put("credentialFields[1].helpText",SiteLoginformResponse.jsonPath().getString("componentList[1].helpText"));
		    params.put("credentialFields[1].maxlength",SiteLoginformResponse.jsonPath().getString("componentList[1].maxlength"));
		    params.put("credentialFields[1].name",SiteLoginformResponse.jsonPath().getString("componentList[1].name"));
		    params.put("credentialFields[1].size",SiteLoginformResponse.jsonPath().getString("componentList[1].size"));
		    params.put("credentialFields[1].value",dagPwd);
		    params.put("credentialFields[1].valueIdentifier",SiteLoginformResponse.jsonPath().getString("componentList[1].valueIdentifier"));
		    params.put("credentialFields[1].valueMask",SiteLoginformResponse.jsonPath().getString("componentList[1].valueMask"));
		    
		    params.put("credentialFields[1].isEditable",SiteLoginformResponse.jsonPath().getString("componentList[1].isEditable"));
		    //params.put("shareCredentialsWithinSite",TestNGBootStrap.properties.getProperty("shareCredentialsWithinSite"));
		    //params.put("startRefreshItemOnAddition",TestNGBootStrap.properties.getProperty("startRefreshItemOnAddition")); 
		    if (mfa.equalsIgnoreCase("mfa"))
            {
               Response GetSiteAccountMfaQuesAndAns = getSiteAccountMfaQuesAndAns(siteaccountId,usersSessionToken, coBrandSessionToken);
               GetSiteAccountMfaQuesAndAns.jsonPath().getString("mfaQuestionAnswerId");
               JsonArray entries = (JsonArray) new JsonParser().parse(GetSiteAccountMfaQuesAndAns.asString());
              
             params.put("mfaQuestionAnswers[0].mfaAnswer", ((JsonObject) entries.get(0)).get("mfaAnswer").getAsString());
             params.put("mfaQuestionAnswers[0].mfaQuestion", ((JsonObject) entries.get(0)).get("mfaQuestion").getAsString());
             params.put("mfaQuestionAnswers[0].mfaQuestionAnswerId", ((JsonObject) entries.get(0)).get("mfaQuestionAnswerId").getAsString());
            
             params.put("mfaQuestionAnswers[1].mfaAnswer", ((JsonObject) entries.get(1)).get("mfaAnswer").getAsString());
             params.put("mfaQuestionAnswers[1].mfaQuestion", ((JsonObject) entries.get(1)).get("mfaQuestion").getAsString());
             params.put("mfaQuestionAnswers[1].mfaQuestionAnswerId", ((JsonObject) entries.get(1)).get("mfaQuestionAnswerId").getAsString());
           
            }
		                 
		    Response response = RestUtil.post(params,restversion+"/jsonsdk/SiteAccountManagement/updateSiteAccountCredentials");
		    response.then().log().all();
		   
		    siteAccountId = response.jsonPath().getString("siteAccountId");           
		    System.out.println("\nsiteAccountId = " +siteAccountId);
		    
		    
		   
		   // pollRefreshStatus(TestNGBootStrap.coBrandSessionToken,usersSessionToken,siteAccountId);     
		    
		    return siteAccountId;
		
		
	}
		
	public void testLogout(String usersSessionToken, String coBrandSessionToken) {
        
        System.out.println("\ntestLogout");
        HashMap<String, String> params = new HashMap<String, String> ();
        params.put("cobSessionToken", coBrandSessionToken);
        params.put("userSessionToken",usersSessionToken);
        Response logout = RestUtil.post(params,restversion+"/jsonsdk/Login/logout");
        logout.then().log().all();						
    }
		
		
	
	
*/




/*

public void testGetLoginFormForContentService(String contentServiceId) {
	
	HashMap<String, String> params = new HashMap<String, String> ();
	params.put("cobSessionToken",TestNGBootStrap.coBrandSessionToken);
	params.put("contentServiceId", contentServiceId);
	Response getLoginformResponse = RestUtil.post(params,TestNGBootStrap.RestVersion+"/jsonsdk/ItemManagement/getLoginFormForContentService");
	getLoginformResponse.then().log().all();
					
}	

public void testGetSiteSummary(String siteId, String usersSessionToken)
{
	HashMap<String, String> params = new HashMap<String, String> ();
	params.put("cobSessionToken",TestNGBootStrap.coBrandSessionToken);
	params.put("userSessionToken", usersSessionToken);
	params.put("memSiteAccId", siteId);			
	Response response = RestUtil.post(params,TestNGBootStrap.RestVersion +"/jsonsdk/DataService/getItemSummariesForSite");
	response.then().log().all();
	
}
*/


	/*
	public Response getLoginFormForContentService(String contentServiceId, String coBrandSessionToken) {

		HashMap<String, String> params = new HashMap<String, String> ();
		params.put("cobSessionToken",coBrandSessionToken);
		params.put("contentServiceId", contentServiceId);
		Response loginformResponse = RestUtil.post(params,restversion+"/jsonsdk/ItemManagement/getLoginFormForContentService");
		loginformResponse.then().log().all();
		return loginformResponse;		
	}
	

	public String getContentServiceInfoByRN(String cobSessionToken, String routingNumber) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cobSessionToken", cobSessionToken);
		params.put("routingNumber", routingNumber);
		params.put("notrim", "true");
		Response response = RestUtil.post(params,restversion+"/jsonsdk/RoutingNumberService/getContentServiceInfoByRoutingNumber");
		response.then().log().all();
		return response.jsonPath().getString("contentServiceId");
	}
	
	// IAV REST Services
	public String addItemAndStartVerificationDataRequest(String coBrandSessionToken, String  userSessionToken, String accountNumber, String routingNumber, String siteName, String sitePassword) {
		 String contentServiceID = getContentServiceInfoByRN(coBrandSessionToken, routingNumber);
		 Response loginformResponse = getLoginFormForContentService(contentServiceID, coBrandSessionToken);
		 HashMap<String, String> params = new HashMap<String, String> ();
		 
		 params.put("iavRequest.contentServiceId",contentServiceID);
    	 params.put("iavRequest.dfiAccount.routingNumber",routingNumber);
    	 if (!accountNumber.isEmpty()) {
    		 params.put("iavRequest.dfiAccount.accountNumber",accountNumber);
    	 }
    	 params.put("iavRequest.isLoginResponseRequired","true");
    	 
    	 params.put("cobSessionToken", coBrandSessionToken);	
    	 params.put("userSessionToken", userSessionToken);

    	 //params.put("iavRequest.isAccountSummaryResponseEnabled","true");
    	 //params.put("iavRequest.isTransactionResponseEnabled","true");
    	 //params.put("iavRequest.transactionLimit","2");    	
    	 //params.put("accountNumber",properties.getProperty("accountNumber"));
    	      
         params.put("credentialFields.enclosedType",PropertyLoader.getEnvProperty("credentialFields.enclosedType"));        
         params.put("credentialFields[0].displayName",loginformResponse.jsonPath().getString("componentList[0].displayName"));
         params.put("credentialFields[0].fieldType.typeName",loginformResponse.jsonPath().getString("componentList[0].fieldType.typeName"));    	
         params.put("credentialFields[0].helpText",loginformResponse.jsonPath().getString("componentList[0].helpText"));    	
         params.put("credentialFields[0].isEditable",loginformResponse.jsonPath().getString("componentList[0].isEditable"));    	
         params.put("credentialFields[0].maxlength",loginformResponse.jsonPath().getString("componentList[0].maxlength"));    	
    	 params.put("credentialFields[0].name",loginformResponse.jsonPath().getString("componentList[0].name"));    	
    	 params.put("credentialFields[0].size",loginformResponse.jsonPath().getString("componentList[0].size"));    	
    	 params.put("credentialFields[0].value",siteName);    	
    	 params.put("credentialFields[0].valueIdentifier",loginformResponse.jsonPath().getString("componentList[0].valueIdentifier"));    	
    	 params.put("credentialFields[0].valueMask",loginformResponse.jsonPath().getString("componentList[0].valueMask"));    	
    	 params.put("credentialFields[1].displayName",loginformResponse.jsonPath().getString("componentList[1].displayName"));    	
    	 params.put("credentialFields[1].fieldType.typeName",loginformResponse.jsonPath().getString("componentList[1].fieldType.typeName"));    	
    	 params.put("credentialFields[1].helpText",loginformResponse.jsonPath().getString("componentList[1].helpText"));    	
    	 params.put("credentialFields[1].isEditable",loginformResponse.jsonPath().getString("componentList[1].isEditable"));   	
    	 params.put("credentialFields[1].maxlength",loginformResponse.jsonPath().getString("componentList[1].maxlength"));    	
    	 params.put("credentialFields[1].name",loginformResponse.jsonPath().getString("componentList[1].name"));    	
    	 params.put("credentialFields[1].size",loginformResponse.jsonPath().getString("componentList[1].size"));    	
    	 params.put("credentialFields[1].value",sitePassword);    	
    	 params.put("credentialFields[1].valueIdentifier",loginformResponse.jsonPath().getString("componentList[1].valueIdentifier"));    	
    	 params.put("credentialFields[1].valueMask",loginformResponse.jsonPath().getString("componentList[1].valueMask"));
    	
		

         Response response = RestUtil.post(params,restversion + "/jsonsdk/ExtendedInstantVerificationDataService/addItemAndStartVerificationDataRequest1");
         response.then().log().all();
         String itemId = response.jsonPath().getString("itemId");
         return itemId;

	}
	
	
	
	
	
	// IAV REST Services
		public String AddItemAndStartVerificationDataRequestWithInstant(String coBrandSessionToken, String  userSessionToken, String accountNumber, String routingNumber, String siteName, String sitePassword) {
			 String contentServiceID = getContentServiceInfoByRN(coBrandSessionToken, routingNumber);
			 Response loginformResponse = getLoginFormForContentService(contentServiceID,coBrandSessionToken);
			 HashMap<String, String> params = new HashMap<String, String> ();
	         String enclosedType= PropertyLoader.getEnvProperty("credentialFields.enclosedType");
	         params.put("cobSessionToken", coBrandSessionToken);    
	         params.put("userSessionToken", userSessionToken);  
	        // different parameter for new API
	        params.put("contentServiceId",contentServiceID);
	        params.put("credentialFields.enclosedType",enclosedType);       
	        params.put("credentialFields[0].displayName",loginformResponse.jsonPath().getString("componentList[0].displayName"));
	        params.put("credentialFields[0].fieldType.typeName",loginformResponse.jsonPath().getString("componentList[0].fieldType.typeName"));          
	        params.put("credentialFields[0].helpText",loginformResponse.jsonPath().getString("componentList[0].helpText"));          
	        params.put("credentialFields[0].isEditable",loginformResponse.jsonPath().getString("componentList[0].isEditable"));          
	        params.put("credentialFields[0].maxlength",loginformResponse.jsonPath().getString("componentList[0].maxlength"));          
	        params.put("credentialFields[0].name",loginformResponse.jsonPath().getString("componentList[0].name"));          
	        params.put("credentialFields[0].size",loginformResponse.jsonPath().getString("componentList[0].size"));          
	        params.put("credentialFields[0].value",siteName);          
	        params.put("credentialFields[0].valueIdentifier",loginformResponse.jsonPath().getString("componentList[0].valueIdentifier"));          
	        params.put("credentialFields[0].valueMask",loginformResponse.jsonPath().getString("componentList[0].valueMask"));          
	        params.put("credentialFields[1].displayName",loginformResponse.jsonPath().getString("componentList[1].displayName"));          
	        params.put("credentialFields[1].fieldType.typeName",loginformResponse.jsonPath().getString("componentList[1].fieldType.typeName"));          
	        params.put("credentialFields[1].helpText",loginformResponse.jsonPath().getString("componentList[1].helpText"));          
	        params.put("credentialFields[1].isEditable",loginformResponse.jsonPath().getString("componentList[1].isEditable"));         
	        params.put("credentialFields[1].maxlength",loginformResponse.jsonPath().getString("componentList[1].maxlength"));          
	        params.put("credentialFields[1].name",loginformResponse.jsonPath().getString("componentList[1].name"));          
	        params.put("credentialFields[1].size",loginformResponse.jsonPath().getString("componentList[1].size"));          
	        params.put("credentialFields[1].value",sitePassword);          
	        params.put("credentialFields[1].valueIdentifier",loginformResponse.jsonPath().getString("componentList[1].valueIdentifier"));          
	        params.put("credentialFields[1].valueMask",loginformResponse.jsonPath().getString("componentList[1].valueMask"));
	      
	             
	 
	        Response response = RestUtil.post(params,restversion + "/jsonsdk/InstantVerificationDataService/addItemAndStartVerificationDataRequest1");
	        response.then().log().all();
	        String itemId = response.jsonPath().getString("primitiveObj");
	        return itemId;

		}
	

	
	public Response getItemVerificationData(String cobSession,String userSession,String itemId) throws Exception 
	{
		
		HashMap<String, String> params = new HashMap<String, String> ();
		params.put("cobSessionToken", cobSession);	
		params.put("userSessionToken", userSession);
		params.put("itemIds[0]",itemId);
		Response response = RestUtil.post(params,restversion+"/jsonsdk/InstantVerificationDataService/getItemVerificationData");
		response.then().log().all();	
		return response;
		
	}
	
	public boolean pollIAVRefreshStatus(String cobSession,String userSession,String itemId) throws Exception { 		
		System.out.println ("\nChecking the refresh status...");
        boolean flag = false;
        String requestStatus = "[IN_PROGRESS]";
        String statusCode ="";
        while (requestStatus.equals("[IN_PROGRESS]")) {
        	Response response1 = getItemVerificationData(cobSession,userSession,itemId);
	    	statusCode = response1.jsonPath().getString("itemVerificationInfo.statusCode");
	    	requestStatus =response1.jsonPath().getString("itemVerificationInfo.requestStatus.verificationRequestStatus");
	    	System.out.println("\nstatusCode code = " +statusCode+ "\tstatus = " +requestStatus);
        }
        if (statusCode.equalsIgnoreCase("[0]")) {
        	flag = true;
        }
        return flag;
	}
        	

	public void startRefresh(String cobSession,String userSession,String memSiteAccId, String refreshPriority) throws Exception { 		
		HashMap<String, String> params = new HashMap<String, String> ();
		params.put("cobSessionToken", cobSession);	
		params.put("userSessionToken", userSession);
		params.put("memSiteAccId",memSiteAccId);
		params.put("refreshParameters.refreshPriority",refreshPriority);
		params.put("refreshParameters.refreshMode.refreshModeId", "2");
		params.put("refreshParameters.refreshMode.refreshMode", "NORMAL");
		params.put("refreshParameters.forceRefresh", "true");
		Response response = RestUtil.post(params,restversion+"/jsonsdk/Refresh/startSiteRefresh");
		response.then().log().all();	
        
	}*/
	
  public void addManualAccBank(String cobSession,String userSession,String enable,String continerName,String contentServiceId) throws Exception { 
		HashMap<String, String> params = new HashMap<String, String> ();
	    if ("FALSE".equalsIgnoreCase(enable))throw new SkipException("Testing skip.");

		
		
	if(continerName.equals("Bank"))
	{
	params.put("cobSessionToken", cobSession);  
    
    params.put("userSessionToken", userSession);
   
    params.put("contentServiceId",contentServiceId);
    params.put("credentials[0].enclosedType", "com.yodlee.common.FieldInfoSingle");
  
    params.put("credentials[0].name", "LOGIN");
    params.put("credentials[0].displayName", "Username");
    params.put("credentials[0].isEditable", "true");
    params.put("credentials[0].isOptional", "false");
    params.put("credentials[0].isEscaped", "false");
    params.put("credentials[0].helpText", "22065");
    params.put("credentials[0].isOptionalMFA", "false");
    params.put("credentials[0].isMFA", "false");
    params.put("credentials[0].valueIdentifier", "LOGIN");
    params.put("credentials[0].valueMask", "LOGIN_FIELD");
    params.put("credentials[0].fieldType.typeName", "TEXT");
    params.put("credentials[0].size", "20");
    params.put("credentials[0].maxlength", "40");
    params.put("credentials[0].value","mallu.bank1");

    params.put("credentials[1].name", "PASSWORD");
    params.put("credentials[1].displayName", "Password");
    params.put("credentials[1].isEditable", "true");
    params.put("credentials[1].isOptional", "false");
    params.put("credentials[1].isEscaped", "false");
    params.put("credentials[1].helpText", "22064");
    params.put("credentials[1].isOptionalMFA", "false");
    params.put("credentials[1].isMFA", "false");
    params.put("credentials[1].valueIdentifier", "LOGIN");
    params.put("credentials[1].valueMask", "LOGIN_FIELD");
    params.put("credentials[1].fieldType", "IF_PASSWORD");
    params.put("credentials[1].size", "20");
    params.put("credentials[1].maxlength", "19");
    params.put("credentials[1].value","bank1");
   
   params.put("itemData.accounts[0].enclosedType", "com.yodlee.core.dataservice.types.BankData");
   
    params.put("itemData.accounts[0].shortNickName","Banking");
    params.put("itemData.accounts[0].isBusinessExpense", "1");
    params.put("itemData.accounts[0].includeInNetworth", "1");
    params.put("itemData.accounts[0].isLinkedItemAccount", "false");
    params.put("itemData.accounts[0].isUpdatePastTransaction", "true");
    params.put("itemData.accounts[0].createOpeningTxn", "true");
    params.put("itemData.accounts[0].isSeidMod", "0");
    params.put("itemData.accounts[0].acctType", "cash");
    params.put("itemData.accounts[0].customDescription", "Banking");
    params.put("itemData.accounts[0].isDeleted", "0");
    params.put("itemData.accounts[0].lastUpdated", "999000");
    params.put("itemData.accounts[0].hasDetails", "1");
    params.put("itemData.accounts[0].accountNumber", "11111111");
    params.put("itemData.accounts[0].tranListToDate.date", "2014-10-02");
    params.put("itemData.accounts[0].tranListFromDate.date", "2014-12-02");
    params.put("itemData.accounts[0].availableBalance.amount", "12345");
    params.put("itemData.accounts[0].availableBalance.currencyCode", "USD");
    params.put("itemData.accounts[0].currentBalance.amount", "234567");
    params.put("itemData.accounts[0].currentBalance.currencyCode", "USD");
    params.put("itemData.accounts[0].interestEarnedYtd.amount", "1222.0");
    params.put("itemData.accounts[0].interestEarnedYtd.currencyCode", "USD");
    params.put("itemData.accounts[0].prevYrInterest.amount", "3000.0");
    params.put("itemData.accounts[0].prevYrInterest.currencyCode", "USD");
    params.put("itemData.accounts[0].overdraftProtection.amount", "3110.0");
    params.put("itemData.accounts[0].overdraftProtection.currencyCode", "USD");
    params.put("itemData.accounts[0].accountName", "bank");
    params.put("itemData.accounts[0].maturityDate.date", "2020-01-01");
    params.put("itemData.accounts[0].asOfDate.date", "2015-01-05");
    params.put("itemData.accounts[0].asOfDate.timezone", "-05:00");
    params.put("itemData.accounts[0].asOfDate.displayTimezone", "EST");
   
    
    params.put("itemData.accounts[1].enclosedType", "com.yodlee.core.dataservice.types.BankData");
    params.put("itemData.accounts[1].shortNickName", "Bank");
    params.put("itemData.accounts[1].isBusinessExpense", "1");
    params.put("itemData.accounts[1].includeInNetworth", "1");
    params.put("itemData.accounts[1].isLinkedItemAccount", "false");
    params.put("itemData.accounts[1].isUpdatePastTransaction", "true");
    params.put("itemData.accounts[1].createOpeningTxn", "true");
    params.put("itemData.accounts[1].isSeidMod", "0");
    params.put("itemData.accounts[1].acctType", "Banking");
    params.put("itemData.accounts[1].customDescription","Bank");
    params.put("itemData.accounts[1].isDeleted", "0");
    params.put("itemData.accounts[1].lastUpdated", "999000");
    params.put("itemData.accounts[1].hasDetails", "1");
    params.put("itemData.accounts[1].accountNumber", "11111");
    params.put("itemData.accounts[1].tranListToDate.date", "2014-10-02");
    params.put("itemData.accounts[1].tranListFromDate.date", "2014-12-02");
    params.put("itemData.accounts[1].availableBalance.amount", "12345");
    params.put("itemData.accounts[1].availableBalance.currencyCode", "USD");
    params.put("itemData.accounts[1].currentBalance.amount", "12345");
    params.put("itemData.accounts[1].currentBalance.currencyCode", "USD");
    params.put("itemData.accounts[1].interestEarnedYtd.amount", "1222.0");
    params.put("itemData.accounts[1].interestEarnedYtd.currencyCode", "USD");
    params.put("itemData.accounts[1].prevYrInterest.amount", "3000.0");
    params.put("itemData.accounts[1].prevYrInterest.currencyCode", "USD");
    params.put("itemData.accounts[1].overdraftProtection.amount", "3110.0");
    params.put("itemData.accounts[1].overdraftProtection.currencyCode", "USD");
    params.put("itemData.accounts[1].accountName", "Bank");
    params.put("itemData.accounts[1].maturityDate.date", "2020-01-01");
    params.put("itemData.accounts[1].asOfDate.date", "2015-01-05");
    params.put("itemData.accounts[1].asOfDate.timezone", "-05:00");
    params.put("itemData.accounts[1].asOfDate.displayTimezone", "EST");
    
    
	} 
    
    
    
    if(continerName.equals("Rewards"))
    {
    params.put("cobSessionToken", cobSession);  
	 params.put("userSessionToken", userSession);
	 params.put("contentServiceId","9801");
	 params.put("credentialFields.enclosedType","com.yodlee.common.FieldInfoSingle");
	 params.put("credentialFields[0].displayName","Catalog");
	 params.put("credentialFields[0].fieldType","LOGIN");
    params.put("credentialFields[0].helpText","150862");
    params.put("credentialFields[0].maxlength","40");
    params.put("credentialFields[0].name","LOGIN1");
    params.put("credentialFields[0].size","20");
    params.put("credentialFields[0].value","mallu.Loans1");
    params.put("credentialFields[0].valueIdentifier","LOGIN1");
    params.put("credentialFields[0].valueMask","LOGIN_FIELD");
    params.put("credentialFields[1].displayName","Password");
    params.put("credentialFields[1].fieldType","PASSWORD");
    params.put("credentialFields[1].helpText","150863");
    params.put("credentialFields[1].maxlength","40");
    params.put("credentialFields[1].name","PASSWORD1");
    params.put("credentialFields[1].size","20");
    params.put("credentialFields[1].value","Loans1");
    params.put("credentialFields[1].valueIdentifier","PASSWORD1");
    params.put("credentialFields[1].valueMask","LOGIN_FIELD");
    params.put("itemData.accounts[0].enclosedType","com.yodlee.core.dataservice.types.RewardPgm");
    params.put("itemData.accounts[0].isDeleted","0");
    params.put("itemData.accounts[0].lastUpdated","999000");
    params.put("itemData.accounts[0].customName","REWARDS");
    params.put("itemData.accounts[0].memo","memo");
    params.put("itemData.accounts[0].accountNumber","accountNumber");
    params.put("itemData.accounts[0].nickName","Tesying SBI");
    params.put("itemData.accounts[0].primaryRewardUnit","USD");
    params.put("itemData.accounts[0].currentLevel","12121");
    params.put("itemData.accounts[0].link","URL");
    params.put("itemData.accounts[0].isSeidMod","0");
    params.put("itemData.accounts[0].hasDetails","1");
    params.put("itemData.accounts[0].isAsset","0");
    params.put("itemData.accounts[0].includeInNetworth","1");
    params.put("itemData.accounts[0].isBusinessExpense","0");
    params.put("itemData.accounts[0].isLinkedItemAccount","false");
    params.put("itemData.accounts[0].isUpdatePastTransaction","true");
    params.put("itemData.accounts[0].isUpdateTxCategory","false");
    params.put("itemData.accounts[0].createOpeningTxn","false");
   params.put("itemData.accounts[0].isItemAccountDeleted","0");
    }
    
    
    
    
    if(continerName.equals("Loans")){
    
    
    params.put("cobSessionToken", cobSession);  
    params.put("userSessionToken", userSession);
       
    params.put("contentServiceId","9739");
    params.put("credentialFields.enclosedType","com.yodlee.common.FieldInfoSingle");
    params.put("credentialFields[0].displayName","Catalog");
    params.put("credentialFields[0].fieldType","LOGIN");
    params.put("credentialFields[0].helpText","150862");
    params.put("credentialFields[0].maxlength","40");
    params.put("credentialFields[0].name","LOGIN1");
    params.put("credentialFields[0].size","20");
    params.put("credentialFields[0].value","mallu.Loans1");
    params.put("credentialFields[0].valueIdentifier","LOGIN1");
    params.put("credentialFields[1].valueMask","LOGIN_FIELD");
    params.put("credentialFields[1].displayName","LOGIN1");
    params.put("credentialFields[1].fieldType","PASSWORD");
    params.put("credentialFields[1].helpText","150863");
    params.put("credentialFields[1].maxlength","40");
    params.put("credentialFields[1].name","PASSWORD1");
    params.put("credentialFields[1].size","20");
    params.put("credentialFields[1].value","Loans1");
    params.put("credentialFields[1].valueIdentifier","PASSWORD1");
    params.put("credentialFields[1].valueMask","LOGIN_FIELD");
    params.put("itemData.accounts[0].enclosedType","com.yodlee.core.dataservice.types.LoanLoginAccountData");
    params.put("itemData.accounts[0].isDeleted","0");
    params.put("itemData.accounts[0].hasDetails","1");
    params.put("itemData.accounts[0].isItemAccountDeleted","0");
    params.put("itemData.accounts[0].lastUpdated","999000");
    params.put("itemData.accounts[0].customName","CustomLoanName");
    params.put("itemData.accounts[0].customDescription","memo");
    params.put("itemData.accounts[0].accountNickName","nickName");
    params.put("itemData.accounts[0].loanAccountNumber","accountNumber");
    params.put("itemData.accounts[0].loans[0].enclosedType","com.yodlee.core.dataservice.types.Loan");
    params.put("itemData.accounts[0].loans[0].customName","CustomLoanName&itemData.accounts[0].loans[0].memo=memo");
    params.put("itemData.accounts[0].loans[0].nickName","nickName");
    params.put("itemData.accounts[0].loans[0].accountName","CustomLoanName");
    params.put("itemData.accounts[0].loans[0].accountNumber","123121");
     
    params.put("itemData.accounts[0].loans[0].amountDue.amount","1500.0");
    params.put("itemData.accounts[0].loans[0].amountDue.currencyCode","USD");
    params.put("itemData.accounts[0].loans[0].dueDate.date","2015-01-05");
    params.put("itemData.accounts[0].loans[0].dueDate.timezone","-05:00");
    params.put("itemData.accounts[0].loans[0].dueDate.displayTimezone","EST");
    params.put("itemData.accounts[0].loans[0].isDeleted","0");
    params.put("itemData.accounts[0].loans[0].lastUpdated","999000");
    params.put("itemData.accounts[0].loans[0].loanType","LOAN/MORTGAGE");
    params.put("itemData.accounts[0].loans[0].setLoanFrequencyType","UNKNOWN");
    params.put("itemData.accounts[0].loans[0].setLoanInterestRateType", "UNKNOWN");
    params.put("itemData.accounts[0].loans[0].principalBalance.amount", "100");
    params.put("itemData.accounts[0].loans[0].principalBalance.currencyCode", "USD");
    params.put("itemData.accounts[0].loans[0].link", "URL");
    params.put("itemData.accounts[0].loans[0].isSeidMod", "1");
    params.put("itemData.accounts[0].loans[0].hasDetails", "0");
    params.put("itemData.accounts[0].loans[0].isItemAccountDeleted","0");
    params.put("itemData.accounts[0].loans[0].isAsset", "0");
    params.put("itemData.accounts[0].loans[0].includeInNetworth", "1");
    params.put("itemData.accounts[0].loans[0].isBusinessExpense","0");
    params.put("itemData.accounts[0].loans[0].isLinkedItemAccount","false");
    params.put("itemData.accounts[0].loans[0].isUpdatePastTransaction","false");
    params.put("itemData.accounts[0].loans[0].isUpdateTxCategory","false");
    params.put("itemData.accounts[0].loans[0].createOpeningTxn","false");
    params.put("itemData.accounts[0].loans[0].billPreference.objectInstanceType","com.yodlee.core.dataservice.types.BillPreferenceData");
    params.put("itemData.accounts[0].loans[0].billPreference.hasDetails","0");
    params.put("itemData.accounts[0].loans[0].billPreference.isDeleted","0");
    params.put("itemData.accounts[0].loans[0].billPreference.isSeidMod","0");
    params.put("itemData.accounts[0].loans[0].billPreference.isItemAccountDeleted","0");
    params.put("itemData.accounts[0].loans[0].billPreference.lastUpdated","999000");
    params.put("itemData.accounts[0].loans[0].billPreference.srcElementId","customSrcElementId.credits");
    params.put("itemData.accounts[0].loans[0].billPreference.amountDue.amount","1500.0");
    params.put("itemData.accounts[0].loans[0].billPreference.amountDue.currencyCode","USD");
    params.put("itemData.accounts[0].loans[0].billPreference.frequencyType","daily");
    params.put("itemData.accounts[0].loans[0].billPreference.nextDueDate.date","2015-01-05");
    params.put("itemData.accounts[0].loans[0].billPreference.startDueDate.date","2015-01-05");
 }
    
    
    if(continerName.equals("Bills"))
    {
    
    params.put("cobSessionToken", cobSession);     
    params.put("userSessionToken", userSession);
    params.put("contentServiceId","9741");
    params.put("credentialFields.enclosedType","com.yodlee.common.FieldInfoSingle");
    params.put("credentialFields[0].displayName","Catalog");
    params.put("credentialFields[0].fieldType","LOGIN");
    params.put("credentialFields[0].helpText","150862");
    params.put("credentialFields[0].maxlength","40");
    params.put("credentialFields[0].name","LOGIN1");
    params.put("credentialFields[0].size", "20");    
    params.put("credentialFields[0].value","mallu.Loans1");
    params.put("credentialFields[1].displayName","Password");    
    params.put("credentialFields[1].fieldType","PASSWORD");
    params.put("credentialFields[1].helpText","150863");
    params.put("credentialFields[1].maxlength","40");  
    params.put("credentialFields[1].name","PASSWORD1");
    params.put("credentialFields[1].size","20");   
    params.put("credentialFields[1].value","Loans1");    
    params.put("credentialFields[1].valueIdentifier","PASSWORD1");   
    params.put("credentialFields[1].valueMask","LOGIN_FIELD");   
    params.put("itemData.accounts[0].enclosedType","com.yodlee.core.dataservice.types.BillsData");  
    params.put("itemData.accounts[0].isDeleted","0");
    params.put("itemData.accounts[0].lastUpdated","999000");    
    params.put("itemData.accounts[0].customName","customName");    
    params.put("itemData.accounts[0].memo","memo");    
    params.put("itemData.accounts[0].nickName","Test");    
    params.put("itemData.accounts[0].accountName","ICICI");    
    params.put("itemData.accounts[0].amountDue.amount","8880");    
    params.put("itemData.accounts[0].amountDue.currencyCode","USD");    
    params.put("itemData.accounts[0].dueDate.date","2015-01-05");    
    params.put("itemData.accounts[0].dueDate.timezone","-05:00");    
    params.put("itemData.accounts[0].dueDate.displayTimezone","EST");    
    params.put("itemData.accounts[0].accountNumber","CustomBankNumber");    
    params.put("itemData.accounts[0].link","URL");    
    params.put("itemData.accounts[0].isSeidMod","0");    
    params.put("itemData.accounts[0].hasDetails","1");    
    params.put("itemData.accounts[0].isAsset","0");    
    params.put("itemData.accounts[0].includeInNetworth","1");    
    params.put("itemData.accounts[0].isBusinessExpense","0");    
    params.put("itemData.accounts[0].isLinkedItemAccount","false");    
    params.put("itemData.accounts[0].isUpdatePastTransaction","true");    
    params.put("itemData.accounts[0].isUpdateTxCategory","false");    
    params.put("itemData.accounts[0].createOpeningTxn","true");    
    params.put("itemData.accounts[0].acctType","credit");    
    params.put("itemData.accounts[0].billPreference.objectInstanceType","com.yodlee.core.dataservice.types.BillPreferenceData");    
    params.put("itemData.accounts[0].billPreference.hasDetails","0");    
    params.put("itemData.accounts[0].billPreference.isDeleted","0");    
    params.put("itemData.accounts[0].billPreference.isSeidMod","0");    
    params.put("itemData.accounts[0].billPreference.isItemAccountDeleted","0");    
    params.put("itemData.accounts[0].billPreference.lastUpdated","999000");   
    params.put("itemData.accounts[0].billPreference.srcElementId","customSrcElementId.credits");    
    params.put("itemData.accounts[0].billPreference.amountDue.amount","1500.0");   
    params.put("itemData.accounts[0].billPreference.amountDue.currencyCode","USD");    
    params.put("itemData.accounts[0].billPreference.frequencyType","daily");    
    params.put("itemData.accounts[0].billPreference.nextDueDate.date","2015-01-05");    
    params.put("itemData.accounts[0].billPreference.startDueDate.date","2015-01-05");

    
    }
    
    if(continerName.equals("Insurance")){
    	
    	  params.put("cobSessionToken", cobSession);     
  	    params.put("userSessionToken", userSession);
        params.put("contentServiceId","9743");    
        params.put("credentialFields.enclosedType","com.yodlee.common.FieldInfoSingle");
        params.put("credentialFields[0].displayName","Catalog");
        params.put("credentialFields[0].fieldType","LOGIN");
        params.put("credentialFields[0].helpText","150862");
        params.put("credentialFields[0].maxlength","40");
        params.put("credentialFields[0].name","LOGIN1");
        params.put("credentialFields[0].size","20");
        params.put("credentialFields[0].value","mallu.Insurance1");
        params.put("credentialFields[0].valueIdentifier","LOGIN1");
        params.put("credentialFields[0].valueMask","LOGIN_FIELD");
        params.put("credentialFields[1].displayName","Password");
        params.put("credentialFields[1].fieldType","PASSWORD");
        params.put("credentialFields[1].helpText","150863");
        params.put("credentialFields[1].maxlength","40");
        params.put("credentialFields[1].name","PASSWORD1");
        params.put("credentialFields[1].size","20");
        params.put("credentialFields[1].value","Insurance1");
        params.put("credentialFields[1].valueIdentifier","PASSWORD1");
        params.put("credentialFields[1].valueMask","LOGIN_FIELD");
        params.put("itemData.accounts[0].enclosedType","com.yodlee.core.dataservice.types.InsuranceLoginAccountData");
        params.put("itemData.accounts[0].customName","CustomInusranceName");
        params.put("itemData.accounts[0].customDescription","memo");
        params.put("itemData.accounts[0].isItemAccountDeleted","0");
        params.put("itemData.accounts[0].hasDetails","1");
        params.put("itemData.accounts[0].isDeleted","0");
        params.put("itemData.accounts[0].lastUpdated","999000");
        params.put("itemData.accounts[0].insurancePolicys[0].enclosedType","com.yodlee.core.dataservice.types.InsuranceData");
        params.put("itemData.accounts[0].insurancePolicys[0].isDeleted","0");
        params.put("itemData.accounts[0].insurancePolicys[0].lastUpdated","999000");
        params.put("itemData.accounts[0].insurancePolicys[0].accountName","Mahindra");
        params.put("itemData.accounts[0].insurancePolicys[0].customName","CustomInusranceName");
        params.put("itemData.accounts[0].insurancePolicys[0].memo","memo");
        params.put("itemData.accounts[0].insurancePolicys[0].accountNumber","CustomCardNumber");
        params.put("itemData.accounts[0].insurancePolicys[0].nickName","User");
        params.put("itemData.accounts[0].insurancePolicys[0].annuityBalance.amount","N/A");
        params.put("itemData.accounts[0].insurancePolicys[0].annuityBalance.currencyCode","USD");
        params.put("itemData.accounts[0].insurancePolicys[0].amountDue.amount","1500.0");
        params.put("itemData.accounts[0].insurancePolicys[0].amountDue.currencyCode","USD");
        params.put("itemData.accounts[0].insurancePolicys[0].dueDate.date","2015-01-05");
        params.put("itemData.accounts[0].insurancePolicys[0].dueDate.timezone","-05:00");
        params.put("itemData.accounts[0].insurancePolicys[0].dueDate.displayTimezone","EST");
        params.put("itemData.accounts[0].insurancePolicys[0].link","URL");
        params.put("itemData.accounts[0].insurancePolicys[0].isSeidMod","0");
        params.put("itemData.accounts[0].insurancePolicys[0].hasDetails","0");
        params.put("itemData.accounts[0].insurancePolicys[0].isAsset","0");
        params.put("itemData.accounts[0].insurancePolicys[0].includeInNetworth","1");
        params.put("itemData.accounts[0].insurancePolicys[0].isBusinessExpense","0");
        params.put("itemData.accounts[0].insurancePolicys[0].isLinkedItemAccount","false");
        params.put("itemData.accounts[0].insurancePolicys[0].isUpdatePastTransaction","false");
        params.put("itemData.accounts[0].insurancePolicys[0].isUpdateTxCategory","false");
        params.put("itemData.accounts[0].insurancePolicys[0].createOpeningTxn","false");
        params.put("itemData.accounts[0].insurancePolicys[0].isItemAccountDeleted","0");
        params.put("itemData.accounts[0].insurancePolicys[0].insuranceType","Unknown");
        params.put("itemData.accounts[0].insurancePolicys[0].policyStatus","Unknown");   
        params.put("itemData.accounts[0].insurancePolicys[0].homeInsuranceType","Unknown");    
        params.put("itemData.accounts[0].insurancePolicys[0].lifeInsuranceType","Unknown");    
        params.put("itemData.accounts[0].insurancePolicys[0].billPreference.objectInstanceType","com.yodlee.core.dataservice.types.BillPreferenceData");    
        params.put("itemData.accounts[0].insurancePolicys[0].billPreference.hasDetails","0");      
        params.put("itemData.accounts[0].insurancePolicys[0].billPreference.isDeleted","0");
        params.put("itemData.accounts[0].insurancePolicys[0].billPreference.isSeidMod","0");
        params.put("itemData.accounts[0].insurancePolicys[0].billPreference.isItemAccountDeleted","0");
        params.put("itemData.accounts[0].insurancePolicys[0].billPreference.lastUpdated","999000");
        params.put("itemData.accounts[0].insurancePolicys[0].billPreference.srcElementId","customSrcElementId.credits");
        params.put("itemData.accounts[0].insurancePolicys[0].billPreference.amountDue.amount","1500.0");
        params.put("itemData.accounts[0].insurancePolicys[0].billPreference.amountDue.currencyCode","USD");
        params.put("itemData.accounts[0].insurancePolicys[0].billPreference.frequencyType","daily");
        params.put("itemData.accounts[0].insurancePolicys[0].billPreference.nextDueDate.date","2015-01-05");
        params.put("itemData.accounts[0].insurancePolicys[0].billPreference.startDueDate.date","2015-01-05");

    }
    
    
    if(continerName.equals("Assets"))
    {

    	
    	  params.put("cobSessionToken", cobSession);     
  	    params.put("userSessionToken", userSession);
    	  params.put("contentServiceId","9974");
    	  params.put("credentialFields.enclosedType","com.yodlee.common.FieldInfoSingle");
    	  params.put("credentialFields[0].displayName","Catalog");
    	  params.put("credentialFields[0].fieldType","LOGIN");
    	  params.put("credentialFields[0].helpText","150862");
    	  params.put("credentialFields[0].maxlength","40");
    	  params.put("credentialFields[0].name","LOGIN1");
    	  params.put("credentialFields[0].size","20");
    	  params.put("credentialFields[0].value","mallu.Loans1");
    	  params.put("credentialFields[0].valueIdentifier","LOGIN1");
    	  params.put("credentialFields[0].valueMask","LOGIN_FIELD");
    	  params.put("credentialFields[1].displayName","Password");
    	  params.put("credentialFields[1].fieldType","PASSWORD");
    	  params.put("credentialFields[1].helpText","150863");
    	  params.put("credentialFields[1].maxlength","40");
    	  params.put("credentialFields[1].name","PASSWORD1");
    	  params.put("credentialFields[1].size","20");
    	  params.put("credentialFields[1].value","Loans1");
    	  params.put("credentialFields[1].valueIdentifier","PASSWORD1");
    	  params.put("credentialFields[1].valueMask","LOGIN_FIELD");
    	  params.put("itemData.accounts[0].enclosedType","com.yodlee.core.dataservice.types.OtherAssetsData");
    	  params.put("itemData.accounts[0].isDeleted","0");
    	  params.put("itemData.accounts[0].lastUpdated","999000");
    	  params.put("itemData.accounts[0].customName","customName");
    	  params.put("itemData.accounts[0].memo","memo");
    	  params.put("itemData.accounts[0].accountName","CustomOtherLiabilities");
    	  params.put("itemData.accounts[0].accountNumber","accountNumber");
    	  params.put("itemData.accounts[0].nickName","nickName");
    	  params.put("itemData.accounts[0].currentBalance.amount","1500.0");
    	  params.put("itemData.accounts[0].currentBalance.currencyCode","USD");
    	  params.put("itemData.accounts[0].link","URL");
    	  params.put("itemData.accounts[0].isSeidMod","0");
    	  params.put("itemData.accounts[0].hasDetails","1");
    	  params.put("itemData.accounts[0].isAsset","0");
    	  params.put("itemData.accounts[0].includeInNetworth","1");
    	  params.put("itemData.accounts[0].isBusinessExpense","0");
    	  params.put("itemData.accounts[0].isLinkedItemAccount","false");
    	  params.put("itemData.accounts[0].isUpdatePastTransaction","true");
    	  params.put("itemData.accounts[0].isUpdateTxCategory","false");
    	  params.put("itemData.accounts[0].createOpeningTxn","false");
    	  params.put("itemData.accounts[0].isItemAccountDeleted","0");

    }
    
    
    if(continerName.equals("Cards"))
    {
    
    	
    	   params.put("cobSessionToken", cobSession);     
    	    params.put("userSessionToken", userSession);
    	   params.put("contentServiceId","9738");
    	   params.put("credentialFields.enclosedType","com.yodlee.common.FieldInfoSingle");
    	   params.put("credentialFields[0].displayName","Catalog");
    	   params.put("credentialFields[0].fieldType","LOGIN");
    	   params.put("credentialFields[0].helpText","150862");
    	   params.put("credentialFields[0].maxlength","40");
    	   params.put("credentialFields[0].name","LOGIN1");
    	   params.put("credentialFields[0].size","20");
    	   params.put("credentialFields[0].value","mallu.bank1");
    	   params.put("credentialFields[0].valueIdentifier","LOGIN1");
    	   params.put("credentialFields[0].valueMask","LOGIN_FIELD");
    	   params.put("credentialFields[1].displayName","Password");
    	   params.put("credentialFields[1].fieldType","PASSWORD");
    	   params.put("credentialFields[1].helpText","150863");
    	   params.put("credentialFields[1].maxlength","40"); 
    	   params.put("credentialFields[1].name","PASSWORD1");
    	   params.put("credentialFields[1].size","20");
    	   params.put("credentialFields[1].value","bank1");
    	   params.put("credentialFields[1].valueIdentifier","PASSWORD1");
    	   params.put("credentialFields[1].valueMask","LOGIN_FIELD");
    	   params.put("itemData.accounts[0].enclosedType","com.yodlee.core.dataservice.types.CardData");
    	   params.put("itemData.accounts[0].isDeleted","0");
    	   params.put("itemData.accounts[0].lastUpdated","999000");
    	   params.put("itemData.accounts[0].customName","customName");
    	   params.put("itemData.accounts[0].memo","memo");
    	   params.put("itemData.accounts[0].nickName","Test User");
    	   params.put("itemData.accounts[0].accountName","KOTAk");
    	   params.put("itemData.accounts[0].runningBalance.amount","53739");
    	   params.put("itemData.accounts[0].runningBalance.currencyCode","USD");
    	   params.put("itemData.accounts[0].amountDue.amount","1500.0");
    	   params.put("itemData.accounts[0].amountDue.currencyCode","USD");
    	   params.put("itemData.accounts[0].dueDate.date","2015-01-05");
    	   params.put("itemData.accounts[0].dueDate.timezone","-05:00");
    	   params.put("itemData.accounts[0].dueDate.displayTimezone","EST");
    	   params.put("itemData.accounts[0].accountNumber","CustomBankNumber");
    	   params.put("itemData.accounts[0].link","URL");
    	   params.put("itemData.accounts[0].isSeidMod","0");
    	   params.put("itemData.accounts[0].hasDetails","1");
    	   params.put("itemData.accounts[0].isAsset","0");
    	   params.put("itemData.accounts[0].includeInNetworth","1");
    	   params.put("itemData.accounts[0].isBusinessExpense","0");
    	   params.put("itemData.accounts[0].isLinkedItemAccount","false");
    	   params.put("itemData.accounts[0].isUpdatePastTransaction","true");
    	   params.put("itemData.accounts[0].isUpdateTxCategory","false");
    	   params.put("itemData.accounts[0].createOpeningTxn","true");
    	   params.put("itemData.accounts[0].acctType","credit");
    	   params.put("itemData.accounts[0].billPreference.objectInstanceType","com.yodlee.core.dataservice.types.BillPreferenceData");
    	   params.put("itemData.accounts[0].billPreference.hasDetails","0");
    	   params.put("itemData.accounts[0].billPreference.isDeleted","0");
    	   params.put("itemData.accounts[0].billPreference.isSeidMod","0");
    	   params.put("itemData.accounts[0].billPreference.isItemAccountDeleted","0");
    	   params.put("itemData.accounts[0].billPreference.lastUpdated","999000");
    	   params.put("itemData.accounts[0].billPreference.srcElementId","customSrcElementId.credits");
    	   params.put("itemData.accounts[0].billPreference.amountDue.amount","1500.0");
    	   params.put("itemData.accounts[0].billPreference.amountDue.currencyCode","USD");
    	   params.put("itemData.accounts[0].billPreference.frequencyType","daily");
    	   params.put("itemData.accounts[0].billPreference.nextDueDate.date","2015-01-05");
    	   params.put("itemData.accounts[0].billPreference.startDueDate.date","2015-01-05");



    	
    }
    
    
    if(continerName.equals("Investment"))
    {
    	
    	  params.put("cobSessionToken", cobSession);     
  	    params.put("userSessionToken", userSession);


  	  params.put("contentServiceId","9737");
  	  params.put("credentialFields.enclosedType","com.yodlee.common.FieldInfoSingle");
  	   params.put("credentialFields[0].displayName","Catalog");
  	params.put("credentialFields[0].fieldType=LOGIN&credentialFields[0].helpText","150862");
  		params.put("credentialFields[0].maxlength=40&credentialFields[0].name=LOGIN1&credentialFields[0].size","20");
  	 params.put("credentialFields[0].value=mallu.Loans1&credentialFields[0].valueIdentifier","LOGIN1");
  	params.put("credentialFields[0].valueMask=LOGIN_FIELD&credentialFields[1].displayName","Password");
  	 params.put("credentialFields[1].fieldType=PASSWORD&credentialFields[1].helpText","150863");
  	 params.put("credentialFields[1].maxlength=40&credentialFields[1].name","PASSWORD1");
  	 params.put("credentialFields[1].size","20");
  	 params.put("credentialFields[1].value","Loans1");
  	params.put("credentialFields[1].valueIdentifier","PASSWORD1");
  	 params.put("credentialFields[1].valueMask","LOGIN_FIELD");
  	  params.put("itemData.accounts[0].enclosedType","com.yodlee.core.dataservice.types.InvestmentData");
  	 params.put("itemData.accounts[0].isDeleted","0");
  	 params.put("itemData.accounts[0].lastUpdated","999000");
  	params.put("itemData.accounts[0].customName","customName");
	params.put("itemData.accounts[0].memo","memo");
  	params.put("itemData.accounts[0].nickName","Testing Moneycenter");
  	params.put("itemData.accounts[0].accountName","Qwerty");
   params.put("itemData.accounts[0].accountNumber","accountNumber");
   params.put("itemData.accounts[0].totalBalance.amount","4540.0");
   params.put("itemData.accounts[0].totalBalance.currencyCode","USD");
   params.put("itemData.accounts[0].link","URL");
   params.put("itemData.accounts[0].isSeidMod","0");
   params.put("itemData.accounts[0].hasDetails","1");
   params.put("itemData.accounts[0].isAsset","0");
  	params.put("itemData.accounts[0].includeInNetworth","1");
  	params.put("itemData.accounts[0].isBusinessExpense","0");
   params.put("itemData.accounts[0].isLinkedItemAccount","false");
   params.put("itemData.accounts[0].isUpdatePastTransaction","true");
  params.put("itemData.accounts[0].isUpdateTxCategory","false");
  params.put("itemData.accounts[0].createOpeningTxn","true");
  params.put("itemData.accounts[0].isItemAccountDeleted","0");

    }
    
    if(continerName.equals("liability"))
    {
    	params.put("cobSessionToken", cobSession);  
    	params.put("userSessionToken", userSession);
    	params.put("contentServiceId","9975");
    	params.put("credentialFields.enclosedType","com.yodlee.common.FieldInfoSingle");
    	params.put("credentialFields[0].displayName","Catalog");
    	params.put("credentialFields[0].fieldType","LOGIN");
    	params.put("credentialFields[0].helpText","150862");
    	params.put("credentialFields[0].maxlength","40");
    	params.put("credentialFields[0].name","LOGIN1");
    	params.put("credentialFields[0].size","20");
    	params.put("credentialFields[0].value","mallu.Loans1");
    	params.put("credentialFields[0].valueIdentifier","LOGIN1");
    	params.put("credentialFields[0].valueMask","LOGIN_FIELD");
    	params.put("credentialFields[1].displayName","Password");
    	params.put("credentialFields[1].fieldType","PASSWORD");
    	params.put("credentialFields[1].helpText","150863");
    	params.put("credentialFields[1].maxlength","40");
    	params.put("credentialFields[1].name","PASSWORD1");
    	params.put("credentialFields[1].size","20");
    	params.put("credentialFields[1].value","Loans1");
    	params.put("credentialFields[1].valueIdentifier","PASSWORD1");
    	params.put("credentialFields[1].valueMask","LOGIN_FIELD");
    	params.put("itemData.accounts[0].enclosedType","com.yodlee.core.dataservice.types.OtherLiabilitiesData");
    	params.put("itemData.accounts[0].isDeleted","0");
    	params.put("itemData.accounts[0].lastUpdated","999000");
    	params.put("itemData.accounts[0].customName","customName");
    	params.put("itemData.accounts[0].memo","memo");
    	params.put("itemData.accounts[0].accountName","CustomOtherLiabilities");
    	params.put("itemData.accounts[0].accountNumber","accountNumber");
    	params.put("itemData.accounts[0].nickName","nickName");
    	params.put("itemData.accounts[0].currentBalance.amount","1500.0");
    	params.put("itemData.accounts[0].currentBalance.currencyCode","USD");
    	params.put("itemData.accounts[0].link","URL");
    	params.put("itemData.accounts[0].isSeidMod","0");
    	params.put("itemData.accounts[0].hasDetails","1");
    	params.put("itemData.accounts[0].isAsset","0");
    	params.put("itemData.accounts[0].includeInNetworth","1");
    	params.put("itemData.accounts[0].isBusinessExpense","0");
    	params.put("itemData.accounts[0].isLinkedItemAccount","false");
    	params.put("itemData.accounts[0].isUpdatePastTransaction","true");
    	params.put("itemData.accounts[0].isUpdateTxCategory","false");
    	params.put("itemData.accounts[0].createOpeningTxn","false");
    	params.put("itemData.accounts[0].isItemAccountDeleted","0");


    }
    Response response = RestUtil.post(params,restversion+"/jsonsdk/ItemManagement/addManualAccount");
	response.then().log().all();
	}

	
	

	public void addManualAcc1Loans(String cobSession,String userSession) throws Exception { 
		HashMap<String, String> params = new HashMap<String, String> ();
		//String accParams[] = Parameter.split(",");	
		params.put("cobSessionToken", cobSession);  
		 params.put("userSessionToken", userSession);
	    
		 params.put("contentServiceId","9739");
		 params.put("credentialFields.enclosedType","com.yodlee.common.FieldInfoSingle");
		 params.put("credentialFields[0].displayName","Catalog");
		 params.put("credentialFields[0].fieldType","LOGIN");
		 params.put("credentialFields[0].helpText","150862");
		 params.put("credentialFields[0].maxlength","40");
		 params.put("credentialFields[0].name","LOGIN1");
		 params.put("credentialFields[0].size","20");
		 params.put("credentialFields[0].value","mallu.Loans1");
		 params.put("credentialFields[0].valueIdentifier","LOGIN1");
		 params.put("credentialFields[1].valueMask","LOGIN_FIELD");
		 params.put("credentialFields[1].displayName","LOGIN1");
		 params.put("credentialFields[1].fieldType","PASSWORD");
		 params.put("credentialFields[1].helpText","150863");
		 params.put("credentialFields[1].maxlength","40");
		 params.put("credentialFields[1].name","PASSWORD1");
		 params.put("credentialFields[1].size","20");
		 params.put("credentialFields[1].value","Loans1");
		 params.put("credentialFields[1].valueIdentifier","PASSWORD1");
		 params.put("credentialFields[1].valueMask","LOGIN_FIELD");
		 params.put("itemData.accounts[0].enclosedType","com.yodlee.core.dataservice.types.LoanLoginAccountData");
		 params.put("itemData.accounts[0].isDeleted","0");
	     params.put("itemData.accounts[0].hasDetails","1");
		 params.put("itemData.accounts[0].isItemAccountDeleted","0");
		 params.put("itemData.accounts[0].lastUpdated","999000");
		 params.put("itemData.accounts[0].customName","CustomLoanName");
	     params.put("itemData.accounts[0].customDescription","memo");
		 params.put("itemData.accounts[0].accountNickName","nickName");
	     params.put("itemData.accounts[0].loanAccountNumber","accountNumber");
	     params.put("itemData.accounts[0].loans[0].enclosedType","com.yodlee.core.dataservice.types.Loan");
		 params.put("itemData.accounts[0].loans[0].customName","CustomLoanName&itemData.accounts[0].loans[0].memo=memo");
		 params.put("itemData.accounts[0].loans[0].nickName","nickName");
		 params.put("itemData.accounts[0].loans[0].accountName","CustomLoanName");
		 params.put("itemData.accounts[0].loans[0].accountNumber","123121");
		 
		 params.put("itemData.accounts[0].loans[0].amountDue.amount","1500.0");
		 params.put("itemData.accounts[0].loans[0].amountDue.currencyCode","USD");
		 params.put("itemData.accounts[0].loans[0].dueDate.date","2015-01-05");
		 params.put("itemData.accounts[0].loans[0].dueDate.timezone","-05:00");
		 params.put("itemData.accounts[0].loans[0].dueDate.displayTimezone","EST");
		 params.put("itemData.accounts[0].loans[0].isDeleted","0");
		 params.put("itemData.accounts[0].loans[0].lastUpdated","999000");
		 params.put("itemData.accounts[0].loans[0].loanType","LOAN/MORTGAGE");
		 params.put("itemData.accounts[0].loans[0].setLoanFrequencyType","UNKNOWN");
	     params.put("itemData.accounts[0].loans[0].setLoanInterestRateType", "UNKNOWN");
         params.put("itemData.accounts[0].loans[0].principalBalance.amount", "100");
	     params.put("itemData.accounts[0].loans[0].principalBalance.currencyCode", "USD");
		 params.put("itemData.accounts[0].loans[0].link", "URL");
				
				params.put("itemData.accounts[0].loans[0].isSeidMod", "1");
				params.put("itemData.accounts[0].loans[0].hasDetails", "0");
				params.put("itemData.accounts[0].loans[0].isItemAccountDeleted", "");
				params.put("itemData.accounts[0].loans[0].isAsset", "0");
				params.put("itemData.accounts[0].loans[0].includeInNetworth", "1");
				params.put("itemData.accounts[0].loans[0].isBusinessExpense","0");
				params.put("itemData.accounts[0].loans[0].isLinkedItemAccount","false");
				
						
					
				params.put("itemData.accounts[0].loans[0].isUpdatePastTransaction","false");
			   params.put("itemData.accounts[0].loans[0].isUpdateTxCategory","false");
				params.put("itemData.accounts[0].loans[0].createOpeningTxn","false");
				params.put("itemData.accounts[0].loans[0].billPreference.objectInstanceType","com.yodlee.core.dataservice.types.BillPreferenceData");
				params.put("itemData.accounts[0].loans[0].billPreference.hasDetails","0");
				params.put("itemData.accounts[0].loans[0].billPreference.isDeleted","0");
			   params.put("itemData.accounts[0].loans[0].billPreference.isSeidMod","0");
				params.put("itemData.accounts[0].loans[0].billPreference.isItemAccountDeleted","0");
				params.put("itemData.accounts[0].loans[0].billPreference.lastUpdated","999000");
				params.put("itemData.accounts[0].loans[0].billPreference.srcElementId","customSrcElementId.credits");
				params.put("itemData.accounts[0].loans[0].billPreference.amountDue.amount","1500.0");
			 params.put("itemData.accounts[0].loans[0].billPreference.amountDue.currencyCode","USD");
			  params.put("itemData.accounts[0].loans[0].billPreference.frequencyType","daily");
			  params.put("itemData.accounts[0].loans[0].billPreference.nextDueDate.date","2015-01-05");
			  params.put("itemData.accounts[0].loans[0].billPreference.startDueDate.date","2015-01-05");



																				

    Response response = RestUtil.post(params,restversion+"/jsonsdk/ItemManagement/addManualAccount");
	response.then().log().all();
	}

	
	
	
	public void addManualAcc1Rewards(String cobSession,String userSession) throws Exception {
		HashMap<String, String> params = new HashMap<String, String> ();
		//String accParams[] = Parameter.split(",");	
		params.put("cobSessionToken", cobSession);  
		 params.put("userSessionToken", userSession);
		 params.put("contentServiceId","9801");
		 params.put("credentialFields.enclosedType","com.yodlee.common.FieldInfoSingle");
		 params.put("credentialFields[0].displayName","Catalog");
		 params.put("credentialFields[0].fieldType","LOGIN");
	     params.put("credentialFields[0].helpText","150862");
	     params.put("credentialFields[0].maxlength","40");
	     params.put("credentialFields[0].name","LOGIN1");
	     params.put("credentialFields[0].size","20");
	     params.put("credentialFields[0].value","mallu.Loans1");
	     params.put("credentialFields[0].valueIdentifier","LOGIN1");
	     params.put("credentialFields[0].valueMask","LOGIN_FIELD");
	     params.put("credentialFields[1].displayName","Password");
	     params.put("credentialFields[1].fieldType","PASSWORD");
	     params.put("credentialFields[1].helpText","150863");
	     params.put("credentialFields[1].maxlength","40");
	     params.put("credentialFields[1].name","PASSWORD1");
	     params.put("credentialFields[1].size","20");
	     params.put("credentialFields[1].value","Loans1");
	     params.put("credentialFields[1].valueIdentifier","PASSWORD1");
	     params.put("credentialFields[1].valueMask","LOGIN_FIELD");
	     params.put("itemData.accounts[0].enclosedType","com.yodlee.core.dataservice.types.RewardPgm");
	     params.put("itemData.accounts[0].isDeleted","0");
	     params.put("itemData.accounts[0].lastUpdated","999000");
	     params.put("itemData.accounts[0].customName","customName");
	     params.put("itemData.accounts[0].memo","memo");
	     params.put("itemData.accounts[0].accountNumber","accountNumber");
	     params.put("itemData.accounts[0].nickName","nickName");
	     params.put("itemData.accounts[0].primaryRewardUnit","USD");
	     params.put("itemData.accounts[0].currentLevel","12121");
	     params.put("itemData.accounts[0].link","URL");
	     params.put("itemData.accounts[0].isSeidMod","0");
	     params.put("itemData.accounts[0].hasDetails","1");
	     params.put("itemData.accounts[0].isAsset","0");
	     params.put("itemData.accounts[0].includeInNetworth","1");
	     params.put("itemData.accounts[0].isBusinessExpense","0");
	     params.put("itemData.accounts[0].isLinkedItemAccount","false");
	     params.put("itemData.accounts[0].isUpdatePastTransaction","true");
	     params.put("itemData.accounts[0].isUpdateTxCategory","false");
	     params.put("itemData.accounts[0].createOpeningTxn","false");
	    params.put("itemData.accounts[0].isItemAccountDeleted","0");
        Response response = RestUtil.post(params,restversion+"/jsonsdk/ItemManagement/addManualAccount");
		response.then().log().all();
	}
	
	
	
	
	public void addManualAccZillow(String cobSession,String userSession) throws Exception { 
		HashMap<String, String> params = new HashMap<String, String> ();


		params.put("cobSessionToken", cobSession);  
		params.put("userSessionToken", userSession);
		params.put("contentServiceId","13059");
		params.put("propertyAddress.objectInstanceType","com.yodlee.core.realestate.PropertyAddress");
		params.put("propertyAddress.cityStateZip","Seattle,WA 98101");
		params.put("propertyAddress.street","1301 Second Avenue,Floor 31");
		Response response = RestUtil.post(params,restversion+"/jsonsdk/HomeValueAccountService/searchProperty");
		response.then().log().all();
		}

		public void addManualAccZillow1(String cobSession,String userSession) throws Exception { 
		HashMap<String, String> params = new HashMap<String, String> ();


		params.put("cobSessionToken", cobSession);  
		params.put("userSessionToken", userSession);
		params.put("contentServiceId","13059");
		params.put("propertyAccountInfo.accountName","HomeAccount");
		params.put("propertyAccountInfo.nickName","HomeAccount");
		params.put("propertyAccountInfo.isIncludeInNetworth","true");
		params.put("propertyAccountInfo.isTaxDeductible","true");
		params.put("propertyAccountInfo.isBusinessExpense","true");
		params.put("propertyAccountInfo.isMedicalExpense","true");
		params.put("propertyAccountInfo.isReimbursable","true");
		params.put("propertyAccountInfo.memo","memo");
		params.put("propertyInfo.propertyId","2106497274");
		params.put("propertyInfo.street","1301 2nd Ave FL 31&");
		params.put("propertyInfo.state","WA");
		params.put("propertyInfo.city","Seattle");
		params.put("propertyInfo.zipCode","98101");
		params.put("propertyInfo.currentValue.currencyCode","USD");
		params.put("propertyInfo.currentValue.amount","121212");
		params.put("propertyInfo.lowerRange.currencyCode","USD");
		params.put("propertyInfo.lowerRange.amount","121211");
		params.put("propertyInfo.higherRange.currencyCode","USD");
		params.put("propertyInfo.higherRange.amount","123456");
		params.put("propertyInfo.estimateDate.date","2015-02-05T00:00:00+0530");
        Response response = RestUtil.post(params,restversion+"/jsonsdk/HomeValueAccountService/addHomeValueItemForContentService");
		response.then().log().all();
}
}
