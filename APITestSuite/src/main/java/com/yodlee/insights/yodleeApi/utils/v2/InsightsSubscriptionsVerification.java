/*******************************************************************************
 *
 * * Copyright (c) 2020 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/
package com.yodlee.insights.yodleeApi.utils.v2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotSame;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jetty.http.HttpStatus;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.yodlee.insights.persistence.dao.document.CustomerConfiguration;
import com.yodlee.insights.persistence.dao.document.CustomerSubscription;
import com.yodlee.insights.persistence.dao.document.CustomerSubscriptions;
import com.yodlee.insights.persistence.dao.document.Threshold;
import com.yodlee.insights.persistence.dao.document.UserConfiguration;
import com.yodlee.insights.persistence.dao.document.UserSubscription;
import com.yodlee.insights.persistence.dao.document.UserSubscriptions;
import com.yodlee.insights.yodleeApi.utils.InsightsConstants;
import com.yodlee.insights.yodleeApi.utils.NotifiedInsightsDataValidation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import io.restassured.response.Response;
//import com.yodlee.insights.service.CustomerSubscriptionService;




public class InsightsSubscriptionsVerification {
	
	@Autowired
	private CustomerSubscription customerSubscription;
	@Autowired
	private CustomerSubscriptions customerSubscriptions;
	@Autowired
	private UserSubscriptions userSubscriptions;
	@Autowired
	private UserSubscription userSubscription;
	
	JsonParser jsonParser = new JsonParser();
	InsightsGenerics insightsGenerics = new InsightsGenerics();
	
	private boolean testExecutionStatus = true;
	
	
	public HashMap<String,Boolean> verifyResponse(Response cobSubscriptionResponse,String insightName,String insightTitle,String insightType ,String triggerType,  String  containerSupported, 
			String  insightDescription, String  applicableEntity,String entityType, String isSubscribed, String  frequency,String duration,
			String thresholdDetails,String schemaFile) {		
		List<CustomerSubscription> customerSubscriptionList;
		HashMap<String , Boolean> testResultMap = new HashMap<String,Boolean>();
		CustomerSubscriptions customerSubscriptions;		
		customerSubscriptions = cobSubscriptionResponse.getBody().as(CustomerSubscriptions.class);
		customerSubscriptionList =  customerSubscriptions.getCustomerSubscription();
		for(int i=0;i<customerSubscriptionList.size();i++) {
			CustomerSubscription custSubscription = customerSubscriptionList.get(i);
			String customerInsightName = custSubscription.getInsightName();
			if(customerInsightName.equals(insightName)) {
				//Validate JsonSchema
				ObjectMapper objMapper = new ObjectMapper();
				try {	
					String str = objMapper.writeValueAsString(custSubscription);					
					validateResponseAgainstCustomerSubscriptionSchema(str, schemaFile);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				if(!insightTitle.isEmpty()) {
					testExecutionStatus = custSubscription.getInsightTitle().equals(insightTitle)?true:false;
					testResultMap.put("Verify Title for "+customerInsightName+ " Insight", testExecutionStatus);
				}					
				if(!insightType.isEmpty()) {
					testExecutionStatus = custSubscription.getInsightType().equals(insightType)?true:false;
					testResultMap.put("Verify Insight Type for "+customerInsightName+" Insight" , testExecutionStatus);
				}					
				if(!triggerType.isEmpty()) {
					testExecutionStatus = custSubscription.getTriggerType().equals(triggerType)?true:false;
					testResultMap.put("Verify Insight Trigger Type for "+customerInsightName+ " Insight", testExecutionStatus);
				}
					
				if(!containerSupported.isEmpty()) {
					List<String> containersList = new ArrayList<String>(Arrays.asList(containerSupported.split(",")));
					testExecutionStatus = custSubscription.getContainer().containsAll(containersList)?true:false;
					testResultMap.put("Verify supported Containers for "+customerInsightName + " Insights", testExecutionStatus);
				}					
				if(!insightDescription.isEmpty()) {
					testExecutionStatus = custSubscription.getDescription().equals(insightDescription)?true:false;
					testResultMap.put("Verify Insight Description for "+customerInsightName + " Insights", testExecutionStatus);
				}
					
				if(!applicableEntity.isEmpty()) {					
					List<String> applicableEntityList = new ArrayList<String>(Arrays.asList(applicableEntity.split(",")));
					testExecutionStatus = custSubscription.getApplicableEntity().containsAll(applicableEntityList)?true:false;
					testResultMap.put("Verify Insight Applicable Entity for "+customerInsightName + " Insights", testExecutionStatus);
				}
				List<CustomerConfiguration> customerConfigurationsList = custSubscription.getCustomerConfiguration();			
				if(!entityType.isEmpty() || !isSubscribed.isEmpty() || !frequency.isEmpty() || !duration.isEmpty() || !thresholdDetails.isEmpty()) {
					
					for(int entityIndex=0;entityIndex<customerConfigurationsList.size();entityIndex++) {
						CustomerConfiguration customerConfiguration = customerConfigurationsList.get(entityIndex);
						if(!entityType.isEmpty()) {
							testExecutionStatus = customerConfiguration.getEntityType().equals(entityType.split(",")[entityIndex])?true:false;
							testResultMap.put("Verify Insight entityType for "+customerInsightName + " Insights", testExecutionStatus);
						}							
						if(!isSubscribed.isEmpty()) {
							testExecutionStatus = customerConfiguration.getIsSubscribed().equals(Boolean.parseBoolean(isSubscribed.split(",")[entityIndex].toUpperCase()))?true:false;
							testResultMap.put("Verify Insight Subscription for "+customerInsightName + " Insights", testExecutionStatus);
						}							
						if(!frequency.isEmpty()) {
							testExecutionStatus = customerConfiguration.getFrequency().equals(frequency.split(",")[entityIndex])?true:false;
							testResultMap.put("Verify Insight frequency for "+customerInsightName + " Insights", testExecutionStatus);
						}
							
						if(!duration.isEmpty()) {
							testExecutionStatus = customerConfiguration.getDuration().equals(duration.split(",")[entityIndex])?true:false;
							testResultMap.put("Verify Insight duration for "+customerInsightName + " Insights", testExecutionStatus);
						}							
						if(!thresholdDetails.isEmpty()) {
							String[] thresholds = thresholdDetails.split("\\s+");
							List<Threshold> thresholdList = customerConfiguration.getThreshold();
							for(int thresholdIndex=0;thresholdIndex<thresholdList.size();thresholdIndex++) {
								testExecutionStatus = thresholdList.get(thresholdIndex).getName().equals(thresholds[thresholdIndex].split(",")[0])?true:false;
								testResultMap.put("Verify Insight Threshold Name for "+customerInsightName + " Insights", testExecutionStatus);
								String thresholdValue = thresholds[thresholdIndex].split(",")[1];
								if(thresholdValue.contains("@")) {
									thresholdValue = (thresholds[thresholdIndex].split(",")[1]).replace("@", ",");
								}
								testExecutionStatus = thresholdList.get(thresholdIndex).getValue().equals(thresholdValue)?true:false;
								testResultMap.put("Verify Insight Threshold Value for "+customerInsightName + " Insights", testExecutionStatus);
								testExecutionStatus = thresholdList.get(thresholdIndex).getType().equals(thresholds[thresholdIndex].split(",")[2])?true:false;
								testResultMap.put("Verify Insight Threshold Type for "+customerInsightName + " Insights", testExecutionStatus);
							}
							
						}
							
					}
				}			
				break;
			}
			
		}
		
		return testResultMap;
		
	}	
	
	
	public HashMap<String,Boolean> verifyUserSubscriptionResponse(Response userSubscriptionResponse,Response customerSubscriptionResponse,String insightName,String insightTitle,String insightType ,String triggerType,  String  containerSupported, 
			String  insightDescription, String  applicableEntity,String entityType, String isSubscribed, String  frequency,String duration,
			String thresholdDetails,String schemaFile) {		
		List<UserSubscription> userSubscriptionList;
		HashMap<String , Boolean> testResultMap = new HashMap<String,Boolean>();
		UserSubscriptions userSubscriptions;		
		userSubscriptions = userSubscriptionResponse.getBody().as(UserSubscriptions.class);
		userSubscriptionList =  userSubscriptions.getUserSubscription();
		for(int i=0;i<userSubscriptionList.size();i++) {
			UserSubscription userSubscription = userSubscriptionList.get(i);
			String customerInsightName = userSubscription.getInsightName();			
			if(customerInsightName.equals(insightName)) {				
		
				
				if(!insightTitle.isEmpty()) {
					testExecutionStatus = userSubscription.getInsightTitle().equals(insightTitle)?true:false;
					testResultMap.put("Verify Title for "+customerInsightName+ " Insight", testExecutionStatus);
				}					
				if(!insightType.isEmpty()) {
					testExecutionStatus = userSubscription.getInsightType().equals(insightType)?true:false;
					testResultMap.put("Verify Insight Type for "+customerInsightName+" Insight" , testExecutionStatus);
				}					
				if(!triggerType.isEmpty()) {
					testExecutionStatus = userSubscription.getTriggerType().equals(triggerType)?true:false;
					testResultMap.put("Verify Insight Trigger Type for "+customerInsightName+ " Insight", testExecutionStatus);
				}
					
				if(!containerSupported.isEmpty()) {
					List<String> containersList = new ArrayList<String>(Arrays.asList(containerSupported.split(",")));
					testExecutionStatus = userSubscription.getContainer().containsAll(containersList)?true:false;
					testResultMap.put("Verify supported Containers for "+customerInsightName + " Insights", testExecutionStatus);
				}					
				if(!insightDescription.isEmpty()) {
					testExecutionStatus = userSubscription.getDescription().equals(insightDescription)?true:false;
					testResultMap.put("Verify Insight Description for "+customerInsightName + " Insights", testExecutionStatus);
				}
					
				if(!applicableEntity.isEmpty()) {					
					List<String> applicableEntityList = new ArrayList<String>(Arrays.asList(applicableEntity.split(",")));
					testExecutionStatus = userSubscription.getApplicableEntity().containsAll(applicableEntityList)?true:false;
					testResultMap.put("Verify Insight Applicable Entity for "+customerInsightName + " Insights", testExecutionStatus);
				}
				
				//UserConfiguration Verification
				
				List<UserConfiguration> userConfigurationsList = userSubscription.getUserConfiguration();		
				if(!entityType.isEmpty() || !isSubscribed.isEmpty() || !frequency.isEmpty() || !duration.isEmpty() || !thresholdDetails.isEmpty()) {
					
					for(int entityIndex=0;entityIndex<userConfigurationsList.size();entityIndex++) {
						CustomerConfiguration userConfiguration = userConfigurationsList.get(entityIndex);
						if(!entityType.isEmpty()) {
							testExecutionStatus = userConfiguration.getEntityType().equals(entityType.split(",")[entityIndex])?true:false;
							testResultMap.put("Verify User entityType for "+customerInsightName + " Insights", testExecutionStatus);
						}							
						if(!isSubscribed.isEmpty()) {
							testExecutionStatus = userConfiguration.getIsSubscribed().equals(Boolean.parseBoolean(isSubscribed.split(",")[entityIndex].toUpperCase()))?true:false;
							testResultMap.put("Verify User Subscription for "+customerInsightName + " Insights", testExecutionStatus);
						}							
						if(!frequency.isEmpty()) {
							testExecutionStatus = userConfiguration.getFrequency().equals(frequency.split(",")[entityIndex])?true:false;
							testResultMap.put("Verify User frequency for "+customerInsightName + " Insights", testExecutionStatus);
						}
							
						if(!duration.isEmpty()) {
							testExecutionStatus = userConfiguration.getDuration().equals(duration.split(",")[entityIndex])?true:false;
							testResultMap.put("Verify User duration for "+customerInsightName + " Insights", testExecutionStatus);
						}							
						if(!thresholdDetails.isEmpty()) {
							String[] thresholds = thresholdDetails.split("\\s+");
							List<Threshold> thresholdList = userConfiguration.getThreshold();
							for(int thresholdIndex=0;thresholdIndex<thresholdList.size();thresholdIndex++) {
								testExecutionStatus = thresholdList.get(thresholdIndex).getName().equals(thresholds[thresholdIndex].split(",")[0])?true:false;
								testResultMap.put("Verify User Threshold Name for "+customerInsightName + " Insights", testExecutionStatus);
								String thresholdValue = thresholds[thresholdIndex].split(",")[1];
								if(thresholdValue.contains("@")) {
									thresholdValue = (thresholds[thresholdIndex].split(",")[1]).replace("@", ",");
								}
								testExecutionStatus = thresholdList.get(thresholdIndex).getValue().equals(thresholdValue)?true:false;
								testResultMap.put("Verify User Threshold Value for "+customerInsightName + " Insights", testExecutionStatus);
								testExecutionStatus = thresholdList.get(thresholdIndex).getType().equals(thresholds[thresholdIndex].split(",")[2])?true:false;
								testResultMap.put("Verify User Threshold Type for "+customerInsightName + " Insights", testExecutionStatus);
							}
							
						}
							
					}
				}
				
				
				
				//CustomerConfiguration Verification
				List<CustomerConfiguration> customerConfigurationsList = userSubscription.getCustomerConfiguration();			
				if(!entityType.isEmpty() || !isSubscribed.isEmpty() || !frequency.isEmpty() || !duration.isEmpty() || !thresholdDetails.isEmpty()) {
					
					for(int entityIndex=0;entityIndex<customerConfigurationsList.size();entityIndex++) {
						CustomerConfiguration customerConfiguration = customerConfigurationsList.get(entityIndex);
						if(!entityType.isEmpty()) {
							testExecutionStatus = customerConfiguration.getEntityType().equals(entityType.split(",")[entityIndex])?true:false;
							testResultMap.put("Verify Customer  entityType for "+customerInsightName + " Insights", testExecutionStatus);
						}							
						if(!isSubscribed.isEmpty()) {
							testExecutionStatus = customerConfiguration.getIsSubscribed().equals(Boolean.parseBoolean(isSubscribed.split(",")[entityIndex].toUpperCase()))?true:false;
							testResultMap.put("Verify Customer Subscription for "+customerInsightName + " Insights", testExecutionStatus);
						}							
						if(!frequency.isEmpty()) {
							testExecutionStatus = customerConfiguration.getFrequency().equals(frequency.split(",")[entityIndex])?true:false;
							testResultMap.put("Verify Customer frequency for "+customerInsightName + " Insights", testExecutionStatus);
						}
							
						if(!duration.isEmpty()) {
							testExecutionStatus = customerConfiguration.getDuration().equals(duration.split(",")[entityIndex])?true:false;
							testResultMap.put("Verify Customer duration for "+customerInsightName + " Insights", testExecutionStatus);
						}							
						if(!thresholdDetails.isEmpty()) {
							String[] thresholds = thresholdDetails.split("\\s+");
							List<Threshold> thresholdList = customerConfiguration.getThreshold();
							for(int thresholdIndex=0;thresholdIndex<thresholdList.size();thresholdIndex++) {
								testExecutionStatus = thresholdList.get(thresholdIndex).getName().equals(thresholds[thresholdIndex].split(",")[0])?true:false;
								testResultMap.put("Verify Customer Threshold Name for "+customerInsightName + " Insights", testExecutionStatus);
								String thresholdValue = thresholds[thresholdIndex].split(",")[1];
								if(thresholdValue.contains("@")) {
									thresholdValue = (thresholds[thresholdIndex].split(",")[1]).replace("@", ",");
								}
								testExecutionStatus = thresholdList.get(thresholdIndex).getValue().equals(thresholdValue)?true:false;
								testResultMap.put("Verify Customer Threshold Value for "+customerInsightName + " Insights", testExecutionStatus);
								testExecutionStatus = thresholdList.get(thresholdIndex).getType().equals(thresholds[thresholdIndex].split(",")[2])?true:false;
								testResultMap.put("Verify Customer Threshold Type for "+customerInsightName + " Insights", testExecutionStatus);
							}
							
						}
							
					}
				}	
				
				ObjectMapper objMapper = new ObjectMapper();
				try {	
					String userSub = objMapper.writeValueAsString(userSubscription);							
					validateResponseAgainstUserSubscriptionSchema(userSub, schemaFile);
					UserSubscription removedUserConfigurationUserSubscription = userSubscription;
					removedUserConfigurationUserSubscription.setUserConfiguration(null);
					String customerSub = objMapper.writeValueAsString(removedUserConfigurationUserSubscription);
					validateResponseAgainstCustomerSubscriptionSchema(customerSub, schemaFile);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				break;				
			//end
				
			}
			
		}
		
		return testResultMap;
		
	}	
	
		
	public String prepareCustomerSubscriptionPatchRequest(String name,String editInsightName,String editInsightTitle,String editInsightType,String editTriggerType,String editContainers,
			String editDescription,String editApplicableEntities,int noOfSupportedEntities,String entityTypes,String editSubscription,String editFrequency,
			String editDuration,String editThreshold) {
		Gson gson = new Gson();
		CustomerSubscription custSubscription = new CustomerSubscription();
		CustomerConfiguration customerConfiguration = new CustomerConfiguration();
		List<CustomerConfiguration> customerConfigurationList = new ArrayList<CustomerConfiguration>();
		List<Threshold> thresholdsList = new ArrayList<Threshold>();
		if(!name.isEmpty())
			custSubscription.setInsightName(name);
		if(!editInsightTitle.isEmpty())
			custSubscription.setInsightTitle(editInsightTitle);
		if(!editInsightType.isEmpty())
			custSubscription.setInsightType(editInsightType);
		if(!editTriggerType.isEmpty())
			custSubscription.setTriggerType(editTriggerType);
		if(!editContainers.isEmpty())
			custSubscription.setContainer(new ArrayList<String>(Arrays.asList(editContainers.split(","))));
		if(!editDescription.isEmpty())
			custSubscription.setDescription(editDescription);
		if(!editApplicableEntities.isEmpty())
			custSubscription.setApplicableEntity(new ArrayList<String>(Arrays.asList(editApplicableEntities.split(","))));
		for(int i=0;i<noOfSupportedEntities;i++) {
			if(!entityTypes.isEmpty())
				customerConfiguration.setEntityType(entityTypes.split(",")[i]);
			if(!editDuration.isEmpty())
				customerConfiguration.setDuration(editDuration.split(",")[i]);
			if(!editSubscription.isEmpty())
				customerConfiguration.setIsSubscribed(Boolean.parseBoolean(editSubscription.split(",")[i]));
			if(!editFrequency.isEmpty())
				customerConfiguration.setFrequency(editFrequency.split(",")[i]);
			String[] totalThresolds = editThreshold.split("\\s+");
			for(int j=0;j<totalThresolds.length;j++) {
				Threshold threshold = new Threshold();				
				threshold.setName(totalThresolds[j].split(",")[0]);
				threshold.setValue(totalThresolds[j].split(",")[1]);
				threshold.setType(totalThresolds[j].split(",")[2]);
				threshold.setAdditionalProperty(null, null);
				thresholdsList.add(threshold);
			}
			if(!thresholdsList.isEmpty() && thresholdsList!=null) {
				customerConfiguration.setThreshold(thresholdsList);	
			}
			customerConfiguration.setAdditionalProperty(null, null);
			customerConfigurationList.add(customerConfiguration);
		}
		custSubscription.setCustomerConfiguration(customerConfigurationList);
		custSubscription.setAdditionalProperty(null, null);
		String patchCustomerSuscriptionPayload = gson.toJson(custSubscription).replace("\"additionalProperties\": {}", "");
		return patchCustomerSuscriptionPayload;
	}
	
	
	@SuppressWarnings("deprecation")
	public void verifyPatchSubscriptionRequestResponse(String subscriptionContext,String testCaseId, String insightName, Response patchResponse,Response beforePatchResponse, 
			Response afterPatchResponse, String negativeTestScenario,	HashMap<String, ArrayList<String>> negativeExpectedValuesMap,int expectedStatusCode,String schemaFile) {
		FailureReason failureReason = new FailureReason();
		boolean testExecutionStatus = true;
		if(patchResponse.getStatusCode()!= expectedStatusCode || patchResponse.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR_500) {
			String errMsg = ((JsonObject) jsonParser.parse(patchResponse.asString()).getAsJsonObject()).get("errorMessage").getAsString();
			testExecutionStatus=false;
			failureReason.setFailureReason(errMsg);
			Assert.fail(errMsg);
		}			
		if (patchResponse.getStatusCode() == HttpStatus.NO_CONTENT_204)  {			
			verifyResponseSchemaPostPatchSubscription(subscriptionContext,afterPatchResponse,insightName,schemaFile);
		} 
		if (patchResponse.getStatusCode() == HttpStatus.BAD_REQUEST_400) {			
			if(!insightsGenerics.verifyInsightsPatchResponseForNegativeTests(testCaseId,patchResponse, negativeExpectedValuesMap, negativeTestScenario, insightName, failureReason)) {
				assertEquals(((JsonObject) jsonParser.parse(patchResponse.asString()).getAsJsonObject()).get("errorMessage").getAsString(), negativeExpectedValuesMap.get(negativeTestScenario).get(2));
			}
		}
	}
	
	public void verifyResponseSchemaPostPatchSubscription(String subscriptionContext,Response postSubscriptionPatchResponse, String insightName,String schemaFile) {
		@SuppressWarnings("deprecation")
		JsonObject customerResponseObj = (JsonObject) jsonParser.parse(postSubscriptionPatchResponse.asString());
		JsonArray subscriptionsArray = null;
		if(subscriptionContext.equals(InsightsConstants.COB_SUBSCRIPTION_CONTEXT))
			subscriptionsArray = customerResponseObj.getAsJsonArray("customerSubscription");
		if(subscriptionContext.equals(InsightsConstants.USER_SUBSCRIPTION_CONTEXT))
			subscriptionsArray = customerResponseObj.getAsJsonArray("userSubscription");
		JsonObject insightObjInCob = null;
		for (int i = 0; i < subscriptionsArray.size(); i++) {
			insightObjInCob = (JsonObject) subscriptionsArray.get(i);
			if (insightObjInCob.get("insightName").getAsString().equals(insightName)) {
				validateResponseAgainstCustomerSubscriptionSchema(insightObjInCob.toString(),schemaFile);					
				break;
			}
		}

	}
	
	public void validateResponseAgainstCustomerSubscriptionSchema(String actualResponse, String expectedJsonSchema) {
			
		System.out.println("Validating Customer Subscription Schema for Attributes and Data Types Schema Name -->"+expectedJsonSchema);
		try {
			JSONObject jsonSchema = new JSONObject(new JSONTokener(InsightsSubscriptionsVerification.class
					.getResourceAsStream("/TestData" + File.separator + "CSVFiles" + File.separator + "Insights"
							+ File.separator + "CustomerSubscriptionSchema" + File.separator + expectedJsonSchema)));
			JSONObject jsonSubject = new JSONObject(new JSONTokener(actualResponse));
			Schema schema = SchemaLoader.load(jsonSchema);
			schema.validate(jsonSubject);
		} catch (ValidationException e) {
			Assert.fail(e.getMessage());
		}
	}

	public void validateResponseAgainstUserSubscriptionSchema(String actualResponse, String expectedJsonSchema) {
			
			System.out.println("Validating User Subscription Schema for Attributes and Data Types Schema Name -->"+expectedJsonSchema);
		try {
			JSONObject jsonSchema = new JSONObject(new JSONTokener(InsightsSubscriptionsVerification.class
					.getResourceAsStream("/TestData" + File.separator + "CSVFiles" + File.separator + "Insights"
							+ File.separator + "UserSubscriptionSchema" + File.separator + expectedJsonSchema)));
			JSONObject jsonSubject = new JSONObject(new JSONTokener(actualResponse));
			Schema schema = SchemaLoader.load(jsonSchema);

			schema.validate(jsonSubject);
		} catch (ValidationException e) {
			Assert.fail(e.getMessage());
		}
	}

}
