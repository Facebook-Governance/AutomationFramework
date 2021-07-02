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
package com.yodlee.insights.yodleeApi.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.testng.Assert;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yodlee.insights.subscriptions.pojo.EntityDetail;
import com.yodlee.insights.subscriptions.pojo.Subscription;
import com.yodlee.insights.subscriptions.pojo.Subscriptions;
import com.yodlee.insights.subscriptions.pojo.v2.Configuration;
import com.yodlee.insights.subscriptions.pojo.v2.*;
import com.yodlee.insights.views.pojo.CommonEntity;

import io.restassured.response.Response;

public class InsightsCommons {

	JsonParser jsonParser = new JsonParser();
	String insightsEntites = "account,goal,view,accountGroup";
	List<String> supportingEntiesList = new ArrayList<String>(Arrays.asList(insightsEntites.split(",")));

	public ExpectedResultValues getExpectedAttributesToBeValidated(String testCaseId, String insightName,
			String triggerType, String insightType, String entityParameterName, String thresholdNameValueType,
			int noumberOfInsights, int numberOfKeys, boolean isUserPatched, int numberOfInsightsPostPatch,
			FailureReason failureReason, JsonArray keysArray, HashMap<String, String> entityIdsMap,
			String validateEntityKeys, Response userSubscriptionResponse) {
		HashMap<String, Entities> subscriptionsMap = new HashMap<String, Entities>();
		ExpectedResultValues expectedResultValues = new ExpectedResultValues();

		expectedResultValues.setTestCaseId(testCaseId);
		expectedResultValues.setInsightName(insightName);
		expectedResultValues.setTriggerType(triggerType);
		expectedResultValues.setEntityParameterName(entityParameterName);
		expectedResultValues.setThresholdNameValueType(thresholdNameValueType);
		expectedResultValues.setNumberOfInsights(noumberOfInsights);
		expectedResultValues.setUserPatched(isUserPatched);
		expectedResultValues.setNumberOfPatchedInisghts(numberOfInsightsPostPatch);
		expectedResultValues.setFailureReason(failureReason);
		expectedResultValues.setKeysArray(keysArray);
		expectedResultValues.setEntityIdsMap(entityIdsMap);
		expectedResultValues.setValidateEntityKeys(validateEntityKeys);

		JsonObject userResponseObject = jsonParser.parse(userSubscriptionResponse.asString()).getAsJsonObject();
		JsonArray userSubscriptionArr = userResponseObject.getAsJsonArray("userSubscription");
		JsonObject insightObject = userSubscriptionArr.get(0).getAsJsonObject();

		JsonObject entityParameterObject = insightObject.getAsJsonObject("entityParameter");
		Set<String> userSubscriptionEntities = entityParameterObject.keySet();
		Entities userEntities = new Entities();
		ArrayList<Entity> userEntityList = new ArrayList<Entity>();

		for (String entity : userSubscriptionEntities) {
			System.out.println("Supported Insight Entity " + entity);

			for (int index = 0; index < entityParameterObject.getAsJsonArray(entity).size(); index++) {
				Entity userEntity = new Entity();
				JsonObject entityObject = entityParameterObject.getAsJsonArray(entity).get(index).getAsJsonObject();
				userEntity.setEntityType(entity);
				if (entityObject.has("isSubscribed")) {
					userEntity.setSubscribed(entityObject.get("isSubscribed").getAsBoolean());
				}
				if (entityObject.has("frequency")) {
					userEntity.setFrequency(entityObject.get("frequency").getAsString());
				}
				if (entityObject.has("insightType")) {
					userEntity.setInsightType(entityObject.get("insightType").getAsString());
				}
				if (entityObject.has("duration")) {
					userEntity.setDuration(entityObject.get("duration").getAsString());
				}
				if (entityObject.has("id")) {
					userEntity.setId(entityObject.get("id").getAsString());
				}
				ArrayList<Threshold> thresholdList = new ArrayList<Threshold>();
				if (entityObject.has("threshold")) {
					JsonArray thresholdArr = entityObject.getAsJsonArray("threshold");
					Threshold threshold = null;
					for (int i = 0; i < thresholdArr.size(); i++) {
						threshold = new Threshold();
						threshold.setName(thresholdArr.get(i).getAsJsonObject().get("name").getAsString());
						threshold.setValue(thresholdArr.get(i).getAsJsonObject().get("value").getAsString());
						threshold.setType(thresholdArr.get(i).getAsJsonObject().get("type").getAsString());
						thresholdList.add(threshold);
					}
					userEntity.setThresholds(thresholdList);
				}
				userEntityList.add(userEntity);
			}
		}
		userEntities.setEntities(userEntityList);
		subscriptionsMap.put("userSubscription", userEntities);
		expectedResultValues.setSubscriptions(subscriptionsMap);

		JsonObject configMetaObject = insightObject.getAsJsonObject("configMeta");
		Set<String> cobrandSubscriptionEntities = configMetaObject.keySet();
		Entities cobrandEntities = new Entities();
		ArrayList<Entity> cobrandEntityList = new ArrayList<Entity>();
		for (String entity : cobrandSubscriptionEntities) {
			Entity cobrandEntity = new Entity();
			System.out.println("Supported Insight Entity " + entity);
			JsonObject entityObject = configMetaObject.getAsJsonArray(entity).get(0).getAsJsonObject();
			cobrandEntity.setEntityType(entity);
			if (entityObject.has("isSubscribed")) {
				cobrandEntity.setSubscribed(entityObject.get("isSubscribed").getAsBoolean());
			}
			if (entityObject.has("frequency")) {
				cobrandEntity.setFrequency(entityObject.get("frequency").getAsString());
			}
			if (entityObject.has("insightType")) {
				cobrandEntity.setInsightType(entityObject.get("insightType").getAsString());
			}
			if (entityObject.has("duration")) {
				cobrandEntity.setDuration(entityObject.get("duration").getAsString());
			}
			ArrayList<Threshold> thresholdList = new ArrayList<Threshold>();
			if (entityObject.has("threshold")) {
				JsonArray thresholdArr = entityObject.getAsJsonArray("threshold");
				Threshold threshold = null;
				for (int i = 0; i < thresholdArr.size(); i++) {
					threshold = new Threshold();
					threshold.setName(thresholdArr.get(i).getAsJsonObject().get("name").getAsString());
					threshold.setValue(thresholdArr.get(i).getAsJsonObject().get("value").getAsString());
					threshold.setType(thresholdArr.get(i).getAsJsonObject().get("type").getAsString());
					thresholdList.add(threshold);
				}
				cobrandEntity.setThresholds(thresholdList);
			}
			cobrandEntityList.add(cobrandEntity);
		}
		cobrandEntities.setEntities(cobrandEntityList);
		subscriptionsMap.put("cobrandSubscription", cobrandEntities);
		expectedResultValues.setSubscriptions(subscriptionsMap);

		return expectedResultValues;

	}

	public String constructCobrandandUserPatchRequest(String SubscriptionCtx, String insightName,
			String entityParameterName, String thresholdNameValueType, String frequency, String duration,
			String editSubscription, String isUserSubscribed, String patchEntityIds,
			HashMap<String, String> entityIdsMap) {
		Subscriptions insightSubscriptions = new Subscriptions();
		String patchDiffEntityIds = getEntityIdsToPatch(patchEntityIds, entityIdsMap);
		ArrayList<Subscription> subscriptionsList = new ArrayList<Subscription>();
		Subscription userSubscription = new Subscription();
		Gson gson = new Gson();
		if (!insightName.isEmpty()) {
			userSubscription.setInsightName(insightName);
		}
		String[] entities = entityParameterName.split("\\s+");
		Set<String> entitiesSet = new HashSet<>(Arrays.asList(entities));
		com.yodlee.insights.subscriptions.pojo.Entity accountEntity = null;
		com.yodlee.insights.subscriptions.pojo.Entity goalEntity = null;
		com.yodlee.insights.subscriptions.pojo.Entity viewEntity = null;
		com.yodlee.insights.subscriptions.pojo.Entity accountGroupEntity = null;
		ArrayList<com.yodlee.insights.subscriptions.pojo.Entity> subscriptionEntityList = new ArrayList<com.yodlee.insights.subscriptions.pojo.Entity>();
		ArrayList<EntityDetail> entityDetailList = null;
		ArrayList<EntityDetail> accountEntityDetailList = null;
		ArrayList<EntityDetail> viewEntityDetailList = null;
		ArrayList<EntityDetail> goalEntityDetailList = null;
		ArrayList<EntityDetail> accountGroupEntityDetailList = null;
		String[] thresholds = thresholdNameValueType.split("\\s+");
		List<String> thresholdValList = new ArrayList<String>(Arrays.asList(thresholds));
		HashSet<com.yodlee.insights.subscriptions.pojo.Threshold> thresholdsList = null, accountThresholdsList = null,
				viewThresholdsList = null, goalThresholdsList = null, accountGroupThresholdsList = null;
		int thresholdIndex = 0;
		EntityDetail definedEntity = null;
		String entityName = null;
		for (int i = 0; i < entities.length; i++) {
			entityName = entities[i].toString();
			accountThresholdsList = new HashSet<com.yodlee.insights.subscriptions.pojo.Threshold>();
			viewThresholdsList = new HashSet<com.yodlee.insights.subscriptions.pojo.Threshold>();
			goalThresholdsList = new HashSet<com.yodlee.insights.subscriptions.pojo.Threshold>();
			accountGroupThresholdsList = new HashSet<com.yodlee.insights.subscriptions.pojo.Threshold>();
			if (accountEntity == null && entities[i].equals("account")) {
				accountEntityDetailList = new ArrayList<EntityDetail>();
				accountEntity = new com.yodlee.insights.subscriptions.pojo.Entity();
			}
			if (viewEntity == null && entities[i].equals("view")) {
				viewEntityDetailList = new ArrayList<EntityDetail>();
				viewEntity = new com.yodlee.insights.subscriptions.pojo.Entity();
			}
			if (goalEntity == null && entities[i].equals("goal")) {
				goalEntityDetailList = new ArrayList<EntityDetail>();
				goalEntity = new com.yodlee.insights.subscriptions.pojo.Entity();
			}
			if (accountGroupEntity == null && entities[i].equals("accountGroup")) {
				accountGroupEntityDetailList = new ArrayList<EntityDetail>();
				accountGroupEntity = new com.yodlee.insights.subscriptions.pojo.Entity();
			}
			entityDetailList = new ArrayList<EntityDetail>();
			if (supportingEntiesList.contains(entities[i])) {
				definedEntity = new EntityDetail();
				String[] durations = duration.split("\\s+");
				if (!duration.isEmpty()) {
					definedEntity.setDuration(durations[i]);
				}
				String[] frequencies = frequency.split("\\s+");
				if (!frequency.isEmpty()) {
					definedEntity.setFrequency(frequencies[i]);
				}
				String[] subscriptions = isUserSubscribed.split("\\s+");
				if (!isUserSubscribed.isEmpty()) {
					definedEntity.setSubscribed(Boolean.parseBoolean(subscriptions[i]));
				}
				String[] patchEntites = patchDiffEntityIds.split("\\s+");
				if (!patchDiffEntityIds.isEmpty()) {
					if (!patchEntites[i].equals("SKIP") && SubscriptionCtx.equalsIgnoreCase("User"))
						definedEntity.setId(patchEntites[i]);
				}
				com.yodlee.insights.subscriptions.pojo.Threshold entityThreshold = null;
				int thresholdLength = thresholds.length;
				if (thresholds[0].length() > 2 && thresholds.length > 0) {
					for (int j = 0; j < thresholdLength; j++) {
						entityThreshold = new com.yodlee.insights.subscriptions.pojo.Threshold();
						if (thresholdIndex < thresholdValList.size()) {
							String[] threshold = thresholdValList.get(thresholdIndex++).toString().split(",");
							entityThreshold.setName(threshold[0]);
							if (threshold[1].contains("&"))
								entityThreshold.setValue(threshold[1].replace('&', ','));
							else
								entityThreshold.setValue(threshold[1]);
							entityThreshold.setType(threshold[2]);
							if (entities[i].equals("account")) {
								accountThresholdsList.add(entityThreshold);
								definedEntity.setThresholds(accountThresholdsList);
							}
							if (entities[i].equals("view")) {
								viewThresholdsList.add(entityThreshold);
								definedEntity.setThresholds(viewThresholdsList);
							}
							if (entities[i].equals("goal")) {
								goalThresholdsList.add(entityThreshold);
								definedEntity.setThresholds(goalThresholdsList);
							}
							if (entities[i].equals("accountGroup")) {
								accountGroupThresholdsList.add(entityThreshold);
								definedEntity.setThresholds(accountGroupThresholdsList);
							}
							if (entities.length == thresholdValList.size()) {
								if (accountThresholdsList.size() > 0)
									accountEntityDetailList.add(definedEntity);
								if (viewThresholdsList.size() > 0)
									viewEntityDetailList.add(definedEntity);
								if (goalThresholdsList.size() > 0)
									goalEntityDetailList.add(definedEntity);
								if (accountGroupThresholdsList.size() > 0)
									accountGroupEntityDetailList.add(definedEntity);
								break;
							}
						}
						if (thresholdLength / 2 == entities.length)
							thresholdLength = 2;

					}
					if (entities.length != thresholdValList.size()) {
						if (accountThresholdsList.size() > 0)
							accountEntityDetailList.add(definedEntity);
						if (viewThresholdsList.size() > 0)
							viewEntityDetailList.add(definedEntity);
						if (goalThresholdsList.size() > 0)
							goalEntityDetailList.add(definedEntity);
						if (accountGroupThresholdsList.size() > 0)
							accountGroupEntityDetailList.add(definedEntity);
					}
				} else {
					if (entityName.equals("account")) {
						accountEntityDetailList.add(definedEntity);
					}
					if (entityName.equals("view")) {
						viewEntityDetailList.add(definedEntity);
					}
					if (entityName.equals("goal")) {
						goalEntityDetailList.add(definedEntity);
					}
					if (entityName.equals("accountGroup")) {
						accountGroupEntityDetailList.add(definedEntity);
					}

				}

			}
		}
		JSONObject accountSubscriptionsArrayObject = new JSONObject();
		JSONObject viewSubscriptionsArrayObject = new JSONObject();
		JSONObject entityParameterObject = new JSONObject();
		if (accountEntity != null) {
			entityParameterObject.put("account", accountEntityDetailList);
		}
		if (viewEntity != null) {
			entityParameterObject.put("view", viewEntityDetailList);
		}
		if (goalEntity != null) {
			entityParameterObject.put("goal", goalEntityDetailList);
		}
		if (accountGroupEntity != null) {
			entityParameterObject.put("accountGroup", accountGroupEntityDetailList);
		}
		JSONObject patchSubscriptionPayload = new JSONObject();
		JSONArray subscriptionsArray = new JSONArray();
		JSONObject subscriptionsArrayObject = new JSONObject();
		subscriptionsArrayObject.put("name", insightName);
		subscriptionsArrayObject.put("entityParameter", entityParameterObject);
		subscriptionsArray.add(subscriptionsArrayObject);
		String patchSubscriptionJson = null;
		if (SubscriptionCtx.equals("Cobrand")) {
			patchSubscriptionPayload.put("cobrandSubscription", subscriptionsArray);
			patchSubscriptionJson = patchSubscriptionPayload.toString().replace("subscribed", "isSubscribed")
					.replace("thresholds", "threshold");
			System.out.println("Cobrand Subscription Json Payload = " + patchSubscriptionJson);
		}
		if (SubscriptionCtx.equals("User")) {
			patchSubscriptionPayload.put("userSubscription", subscriptionsArray);
			patchSubscriptionJson = patchSubscriptionPayload.toString().replace("subscribed", "isSubscribed")
					.replace("thresholds", "threshold");
			System.out.println("User Subscription Json Payload = " + patchSubscriptionJson);
		}

		return patchSubscriptionJson;
	}

	public String constructSubscriptionPatchRequest(String SubscriptionCtx, String insightName,
			String entityParameterName, String thresholdNameValueType, String frequency, String duration,
			String editSubscription, String isSubscribed, String patchEntityIds, HashMap<String, String> entityIdsMap) {
		String patchDiffEntityIds = getEntityIdsToPatch(patchEntityIds, entityIdsMap);
		com.yodlee.insights.subscriptions.pojo.v2.Subscriptions allSubscriptions = new com.yodlee.insights.subscriptions.pojo.v2.Subscriptions();
		ArrayList<com.yodlee.insights.subscriptions.pojo.v2.Subscription> subscriptionsList = new ArrayList<com.yodlee.insights.subscriptions.pojo.v2.Subscription>();
		com.yodlee.insights.subscriptions.pojo.v2.Subscription subscription = new com.yodlee.insights.subscriptions.pojo.v2.Subscription();
		if (!insightName.isEmpty()) {
			subscription.setInsightName(insightName);
		}
		String[] entities = entityParameterName.split("\\s+");
		String entityName = null;
		Configuration configuration = null;
		ArrayList<Configuration> configurationsList = new ArrayList<Configuration>();
		String[] thresholds = thresholdNameValueType.split("\\s+");
		List<String> thresholdValList = new ArrayList<String>(Arrays.asList(thresholds));
		ArrayList<com.yodlee.insights.subscriptions.pojo.v2.Threshold> thresholdsList = null;
		int thresholdIndex = 0;
		for (int i = 0; i < entities.length; i++) {
			entityName = entities[i].toString();

			if (thresholdsList == null) {
				thresholdsList = new ArrayList<com.yodlee.insights.subscriptions.pojo.v2.Threshold>();
			}

			if (supportingEntiesList.contains(entities[i])) {
				configuration = new Configuration();
				configuration.setEntityType(entityName.toUpperCase());
				String[] durations = duration.split("\\s+");
				if (!duration.isEmpty()) {
					configuration.setDuration(durations[i]);
				}
				String[] frequencies = frequency.split("\\s+");
				if (!frequency.isEmpty()) {
					configuration.setFrequency(frequencies[i]);
				}
				String[] subscriptions = isSubscribed.split("\\s+");
				if (!isSubscribed.isEmpty()) {
					configuration.setSubscribed(Boolean.parseBoolean(subscriptions[i]));
				}
				String[] patchEntites = patchDiffEntityIds.split("\\s+");
				if (!patchDiffEntityIds.isEmpty()) {
					if (!patchEntites[i].equals("SKIP") && SubscriptionCtx.equalsIgnoreCase("User"))
						configuration.setEntityId(patchEntites[i]);
				}
				com.yodlee.insights.subscriptions.pojo.v2.Threshold entityThreshold = null;
				int thresholdLength = thresholds.length;
				boolean flag = false;
				if (thresholds[0].length() > 2 && thresholds.length > 0) {
					for (int j = 0; j < thresholdLength; j++) {
						entityThreshold = new com.yodlee.insights.subscriptions.pojo.v2.Threshold();
						flag = false;
						if (thresholdIndex <= thresholdValList.size()) {
							String[] threshold = thresholdValList.get(thresholdIndex++).toString().split(",");
							entityThreshold.setName(threshold[0]);
							if (threshold[1].contains("&")) {
								entityThreshold.setValue(threshold[1].replace('&', ','));
							} else {
								entityThreshold.setValue(threshold[1]);
							}
							entityThreshold.setType(threshold[2]);

							if (entities.length == thresholdValList.size()) {
								thresholdsList.add(entityThreshold);
								configuration.setThreshold(thresholdsList);
								configurationsList.add(configuration);
								thresholdsList = null;
								flag = true;
								break;
							}
							if (thresholdLength / 2 == entities.length)
								thresholdLength = 2;
							thresholdsList.add(entityThreshold);
						}

					}
					if (!flag) {
						configuration.setThreshold(thresholdsList);
						configurationsList.add(configuration);
					}

				}
				if (!entityName.isEmpty() && !isSubscribed.isEmpty() && thresholdNameValueType.isEmpty())
					configurationsList.add(configuration);
			}
		}

		if (SubscriptionCtx.equals("Cobrand")) {
			if (configurationsList != null)
				subscription.setCustomerConfiguration(configurationsList);
		}
		if (SubscriptionCtx.equals("User")) {

			subscription.setUserConfiguration(configurationsList);
		}
		subscriptionsList.add(subscription);
		JSONObject subscriptionObj = new JSONObject();
		String patchSubscriptionJson = null;
		if (SubscriptionCtx.equals("Cobrand")) {
			subscriptionObj.put("customerSubscription", subscriptionsList);
			patchSubscriptionJson = subscriptionObj.toString().replace("subscribed", "isSubscribed");
			System.out.println("Customer Subscription Json Payload = " + patchSubscriptionJson);
		}
		if (SubscriptionCtx.equals("User")) {
			subscriptionObj.put("userSubscription", subscriptionsList);
			patchSubscriptionJson = subscriptionObj.toString().replace("subscribed", "isSubscribed");
			System.out.println("User Subscription Json Payload = " + patchSubscriptionJson);
		}

		return patchSubscriptionJson;
	}

	public String constructPatchSubscriptionRequestForCustomerAndUser(String SubscriptionCtx, String insightName,
			String insightTitle, String insightType, String triggerType, String container, String description,
			String applicableEntity, String entityParameterName, String thresholdNameValueType, String frequency,
			String duration, String isSubscribed, String patchEntityIds, HashMap<String, String> entityIdsMap,
			String editCustomerConfigurationByUser) {

		String patchDiffEntityIds = getEntityIdsToPatch(patchEntityIds, entityIdsMap);
		com.yodlee.insights.subscriptions.pojo.v2.Subscriptions allSubscriptions = new com.yodlee.insights.subscriptions.pojo.v2.Subscriptions();
		ArrayList<com.yodlee.insights.subscriptions.pojo.v2.Subscription> subscriptionsList = new ArrayList<com.yodlee.insights.subscriptions.pojo.v2.Subscription>();
		com.yodlee.insights.subscriptions.pojo.v2.Subscription subscription = new com.yodlee.insights.subscriptions.pojo.v2.Subscription();
		if (!insightName.isEmpty()) {
			subscription.setInsightName(insightName);
		}
		if (!insightTitle.isEmpty()) {
			subscription.setInsightTitle(insightTitle);
		}
		if (!insightType.isEmpty()) {
			subscription.setInsightType(insightType);
		}
		if (!triggerType.isEmpty()) {
			subscription.setTriggerType(triggerType);
		}
		if (!container.isEmpty()) {
			subscription.setContainer(container.split("\\s+"));
		}
		if (!description.isEmpty()) {
			subscription.setDescription(description);
		}
		if (!applicableEntity.isEmpty()) {
			subscription.setApplicableEntity(applicableEntity.split("\\s+"));
		}
		String[] entities = entityParameterName.split("\\s+");
		String entityName = null;
		Configuration configuration = null;
		ArrayList<Configuration> configurationsList = new ArrayList<Configuration>();
		String[] thresholds = thresholdNameValueType.split("\\s+");
		List<String> thresholdValList = new ArrayList<String>(Arrays.asList(thresholds));
		ArrayList<com.yodlee.insights.subscriptions.pojo.v2.Threshold> thresholdsList = null;
		int thresholdIndex = 0;
		for (int i = 0; i < entities.length; i++) {
			entityName = entities[i].toString();

			if (thresholdsList == null) {
				thresholdsList = new ArrayList<com.yodlee.insights.subscriptions.pojo.v2.Threshold>();
			}

			if (supportingEntiesList.contains(entities[i])) {
				configuration = new Configuration();
				configuration.setEntityType(entityName.toUpperCase());
				String[] durations = duration.split("\\s+");
				if (!duration.isEmpty()) {
					configuration.setDuration(durations[i]);
				}
				String[] frequencies = frequency.split("\\s+");
				if (!frequency.isEmpty()) {
					configuration.setFrequency(frequencies[i]);
				}
				String[] subscriptions = isSubscribed.split("\\s+");
				if (!isSubscribed.isEmpty()) {
					configuration.setSubscribed(Boolean.parseBoolean(subscriptions[i]));
				}
				String[] patchEntites = patchDiffEntityIds.split("\\s+");
				if (!patchDiffEntityIds.isEmpty()) {
					if (!patchEntites[i].equals("SKIP") && SubscriptionCtx.equalsIgnoreCase("User")
							|| SubscriptionCtx.equalsIgnoreCase("Cobrand"))
						configuration.setEntityId(patchEntites[i]);
				}
				com.yodlee.insights.subscriptions.pojo.v2.Threshold entityThreshold = null;
				int thresholdLength = thresholds.length;
				boolean flag = false;
				if (thresholds[0].length() > 2 && thresholds.length > 0) {
					for (int j = 0; j < thresholdLength; j++) {
						entityThreshold = new com.yodlee.insights.subscriptions.pojo.v2.Threshold();
						flag = false;
						if (thresholdIndex <= thresholdValList.size()) {
							String[] threshold = thresholdValList.get(thresholdIndex++).toString().split(",");
							entityThreshold.setName(threshold[0]);
							if (threshold[1].contains("&")) {
								entityThreshold.setValue(threshold[1].replace('&', ','));
							} else {
								entityThreshold.setValue(threshold[1]);
							}
							entityThreshold.setType(threshold[2]);

							if (entities.length == thresholdValList.size()) {
								thresholdsList.add(entityThreshold);
								configuration.setThreshold(thresholdsList);
								configurationsList.add(configuration);
								thresholdsList = null;
								flag = true;
								break;
							}
							if (thresholdLength / 2 == entities.length)
								thresholdLength = 2;
							thresholdsList.add(entityThreshold);
						}

					}
					if (!flag) {
						configuration.setThreshold(thresholdsList);
						configurationsList.add(configuration);
						thresholdsList = null;
					}

				}
				if (thresholdNameValueType.isEmpty()
						&& (!isSubscribed.isEmpty() || !frequency.isEmpty() || !duration.isEmpty()))
					configurationsList.add(configuration);
			}
		}

		if (SubscriptionCtx.equals("Cobrand")) {
			if (configurationsList != null)
				subscription.setCustomerConfiguration(configurationsList);
		}
		if (SubscriptionCtx.equals("User")) {

			subscription.setUserConfiguration(configurationsList);
		}
		subscriptionsList.add(subscription);
		JSONObject subscriptionObj = new JSONObject();
		String patchSubscriptionJson = null;
		if (SubscriptionCtx.equals("Cobrand")) {
			subscriptionObj.put("customerSubscription", subscriptionsList);
			patchSubscriptionJson = subscriptionObj.toString().replace("subscribed", "isSubscribed");
			System.out.println("Customer Subscription Json Payload = " + patchSubscriptionJson);
		}
		if (SubscriptionCtx.equals("User")) {
			subscriptionObj.put("userSubscription", subscriptionsList);
			patchSubscriptionJson = subscriptionObj.toString().replace("subscribed", "isSubscribed");
			System.out.println("User Subscription Json Payload = " + patchSubscriptionJson);
		}

		return patchSubscriptionJson;
	}

	public String getEntityIdsToPatch(String patchAccountIds, HashMap<String, String> entityIdsMap) {
		StringBuffer entityIds = new StringBuffer();
		if (!patchAccountIds.isEmpty()) {
			String[] itemAccounts = patchAccountIds.split("\\s+");

			for (int i = 0; i < itemAccounts.length; i++) {
				String itemAccountId = entityIdsMap.get(itemAccounts[i]);
				if (i != itemAccounts.length - 1) {
					entityIds.append(itemAccountId);
					entityIds.append("   ");
				} else {
					entityIds.append(itemAccountId);
				}
			}
		}
		return entityIds.toString();
	}

	public HashMap<String, Boolean> getEntityIdsSubscriptionMap(CommonEntity commonEntity, String patchEntityIds,
			String expectedInsightsForPatchedIds) {
		HashMap<String, Boolean> entityIdsSubscriptionMap = new HashMap<String, Boolean>();
		String[] subscriptions = expectedInsightsForPatchedIds.split("\\s+");
		int i = 0;
		for (String entityId : patchEntityIds.split("\\s+")) {
			if (!patchEntityIds.isEmpty() && subscriptions.length > 0
					&& !commonEntity.getEntityIdsMap().get(entityId).isEmpty()) {
				entityIdsSubscriptionMap.put(commonEntity.getEntityIdsMap().get(entityId),
						Boolean.parseBoolean(subscriptions[i++]));
			}
		}
		return entityIdsSubscriptionMap;
	}

	public HashMap<String, Boolean> getCobrandSubscriptionMap(String entityParameters, String cobrandSubscriptions) {
		HashMap<String, Boolean> cobrandSubscriptionMap = new HashMap<String, Boolean>();
		String[] entityParams = entityParameters.split("\\s+");
		String[] cobSubscriptions = cobrandSubscriptions.split("\\s+");
		int i = 0;
		for (String supportingEntity : entityParams) {
			cobrandSubscriptionMap.put(supportingEntity, Boolean.parseBoolean(cobSubscriptions[i++]));
		}
		// NA Case
		if (cobrandSubscriptionMap.size() == 1 && cobSubscriptions.length == 2) {
			if (cobrandSubscriptionMap.containsKey("view"))
				cobrandSubscriptionMap.put("account", true);
			if (cobrandSubscriptionMap.containsKey("account"))
				cobrandSubscriptionMap.put("view", true);
		}
		return cobrandSubscriptionMap;
	}

	public CreateSegment constructCreateUserSegmentPayload(String income, String incomeCurrency, String incomeRange,
			String city, String state, String country, String zipCode, String age, String networthValue,
			String networthCurrency, String homeOwneShip, String lifeStage) {
		CreateSegment segment = new CreateSegment();
		UserSegment userSegment = new UserSegment();
		UserGeoLocation geoLocation = new UserGeoLocation();
		Money incomeMoney = new Money();
		Money networthMoney = new Money();
		if (!income.isEmpty())
			incomeMoney.setAmount(new BigDecimal(income));
		if (!incomeCurrency.isEmpty())
			incomeMoney.setCurrency(incomeCurrency);
		if (!income.isEmpty() || !incomeCurrency.isEmpty())
			userSegment.setIncome(incomeMoney);
		if (!incomeRange.isEmpty())
			userSegment.setIncomeRange(incomeRange);
		if (!city.isEmpty()) {
			if (city.equals("null"))
				city = null;
			geoLocation.setCity(city);
		}
		if (!state.isEmpty()) {
			if (state.equals("null"))
				state = null;
			geoLocation.setState(state);
		}
		if (!country.isEmpty()) {
			if (country.equals("null"))
				country = null;
			geoLocation.setCountry(country);
		}
		if (!zipCode.isEmpty()) {
			if (zipCode.equals("null"))
				zipCode = null;
			geoLocation.setZipcode(zipCode);
		}
		if (geoLocation.getZipcode() != null || geoLocation.getCountry() != null || geoLocation.getState() != null
				|| geoLocation.getCity() != null) {
			userSegment.setGeoLocation(geoLocation);
		}
		if (!networthValue.isEmpty())
			networthMoney.setAmount(new BigDecimal(networthValue));
		if (!networthCurrency.isEmpty())
			networthMoney.setCurrency(networthCurrency);
		if (!networthValue.isEmpty() || !networthCurrency.isEmpty())
			userSegment.setNetWorth(networthMoney);
		if (!homeOwneShip.isEmpty()) {
			userSegment.setHomeOwnership(Boolean.parseBoolean(homeOwneShip));
		}
		if (!lifeStage.isEmpty())
			userSegment.setLifeStage(lifeStage.toUpperCase());
		if (!age.isEmpty())
			userSegment.setAge(Double.parseDouble(age));
		segment.setSegment(userSegment);
		Gson gson = new Gson();
		String createUserSegmentJson = gson.toJson(segment);
		System.out.println("Create User Segment Body =  " + createUserSegmentJson);
		return segment;
	}

	public void verifyUserSegmentResponse(String testCaseId, int statusCode, Response userSegmentResponse,
			HashMap<String, ArrayList<String>> expetedExpenseNegativeMap) {
		boolean testExecutionStatus = true;
		JsonObject expNegResponseObj = (JsonObject) jsonParser.parse(userSegmentResponse.asString());
		if (statusCode == 400 || statusCode == 401 || statusCode == 500) {
			if (statusCode == Integer.parseInt(expetedExpenseNegativeMap.get(testCaseId).get(0))) {
				if (!expNegResponseObj.get("errorCode").getAsString()
						.equals(expetedExpenseNegativeMap.get(testCaseId).get(1))) {
					Assert.fail("Test FAILED Segment Response Does NOT - Error Code Doesn't Match");
				}
				String expectedErrorMessage = expetedExpenseNegativeMap.get(testCaseId).get(2);
				if (testCaseId.equals("AT-SpecialChars1") || testCaseId.equals("AT-SpecialChars2")
						|| testCaseId.equals("AT-SpecialChars3") || testCaseId.equals("AT-SpecialChars4")) {
					expNegResponseObj.get("errorMessage").getAsString().contains("Invalid value for");
				} else {
					if (!expNegResponseObj.get("errorMessage").getAsString().equals(expectedErrorMessage)) {
						Assert.fail("Test FAILED Segment Response Does NOT - Error Message Doesn't Match");
					}
				}
			}
		} else {
			if (statusCode == HttpStatus.CREATED_201) {
				if (userSegmentResponse.asString().equals("{}")) {
					Assert.fail(testCaseId + "  Failed Expected response.... Segment Id "
							+ "Actual Response  :   Doesn't Contain Segment Id");
				} else {
					JsonObject segmentObject = (JsonObject) jsonParser.parse(userSegmentResponse.asString());
					if (!segmentObject.has("id"))
						Assert.fail("Test FAILED Segment Response Does NOT contain segment Id");
				}
			}
		}

	}

	public void verifyGetUserSegmentResponse(CreateSegment segment, Response getUserSegmentResponse) {
		JsonObject userSegmentGetResObject = ((JsonObject) jsonParser.parse(getUserSegmentResponse.asString()))
				.getAsJsonObject("segment");

		if (segment.getSegment().getIncome() != null) {

			if (((JsonObject) userSegmentGetResObject.getAsJsonObject("income")).get("amount").getAsBigDecimal()
					.longValue() != Math.round(segment.getSegment().getIncome().getAmount().doubleValue())) {
				Assert.fail("Income amount is Incorrect");
			}
			if (!((JsonObject) userSegmentGetResObject.getAsJsonObject("income")).get("currency").getAsString()
					.equals(segment.getSegment().getIncome().getCurrency())) {
				Assert.fail("Income Currency is Incorrect");
			}
		}
		if (segment.getSegment().getIncomeRange() != null) {
			if (!userSegmentGetResObject.get("incomeRange").getAsString()
					.equals(segment.getSegment().getIncomeRange())) {
				Assert.fail("Income Range is Incorrect");
			}
		}
		if (segment.getSegment().getHomeOwnership() != null) {
			if (!userSegmentGetResObject.get("homeOwnership").getAsBoolean() == segment.getSegment().getHomeOwnership()
					.booleanValue()) {
				Assert.fail("homeOwnership is Incorrect");
			}
		}
		if (segment.getSegment().getLifeStage() != null) {
			if (!userSegmentGetResObject.get("lifeStage").getAsString().equals(segment.getSegment().getLifeStage())) {
				Assert.fail("Life Stage is Incorrect");
			}
		}

		if (segment.getSegment().getNetWorth() != null) {
			if (((JsonObject) userSegmentGetResObject.getAsJsonObject("netWorth")).get("amount").getAsBigDecimal()
					.longValue() != Math.round(segment.getSegment().getNetWorth().getAmount().doubleValue())) {
				Assert.fail("NetWorth Amount is Incorrect");
			}
			if (!((JsonObject) userSegmentGetResObject.getAsJsonObject("netWorth")).get("currency").getAsString()
					.equals(segment.getSegment().getNetWorth().getCurrency())) {
				Assert.fail("NetWorth Currency is Incorrect");
			}
		}

		if (segment.getSegment().getAge() != null) {
			if (userSegmentGetResObject.get("age").getAsInt() != (int) segment.getSegment().getAge().doubleValue()) {
				Assert.fail("Age is Incorrect");
			}
		}

		if (segment.getSegment().getGeoLocation() != null) {
			if (((JsonObject) userSegmentGetResObject.getAsJsonObject("geoLocation")).has("city")) {
				if (!((JsonObject) userSegmentGetResObject.getAsJsonObject("geoLocation")).get("city").getAsString()
						.equals(segment.getSegment().getGeoLocation().getCity())) {
					Assert.fail("City is Incorrect its NOT matching");
				}
			}
			if (((JsonObject) userSegmentGetResObject.getAsJsonObject("geoLocation")).has("state")) {
				if (!((JsonObject) userSegmentGetResObject.getAsJsonObject("geoLocation")).get("state").getAsString()
						.equals(segment.getSegment().getGeoLocation().getState())) {
					Assert.fail("State is Incorrect its NOT matching");
				}
			}
			if (((JsonObject) userSegmentGetResObject.getAsJsonObject("geoLocation")).has("country")) {
				if (!((JsonObject) userSegmentGetResObject.getAsJsonObject("geoLocation")).get("country").getAsString()
						.equals(segment.getSegment().getGeoLocation().getCountry())) {
					Assert.fail("Country is Incorrect its NOT matching");
				}
			}
			if (((JsonObject) userSegmentGetResObject.getAsJsonObject("geoLocation")).has("zipCode")) {
				if (!((JsonObject) userSegmentGetResObject.getAsJsonObject("geoLocation")).get("zipCode").getAsString()
						.equals(segment.getSegment().getGeoLocation().getZipcode())) {
					Assert.fail("ZipCode is Incorrect its NOT matching");
				}
			}

		}
	}

	public String constructSubscriptionPatchRequestV2(String SubscriptionCtx, String insightName,
			String entityParameterName, String thresholdNameValueType, String frequency, String duration,
			String editSubscription, String isSubscribed, String patchEntityIds, HashMap<String, String> entityIdsMap) {
		String patchDiffEntityIds = getEntityIdsToPatch(patchEntityIds, entityIdsMap);
		com.yodlee.insights.subscriptions.pojo.v2.Subscriptions allSubscriptions = new com.yodlee.insights.subscriptions.pojo.v2.Subscriptions();
		ArrayList<com.yodlee.insights.subscriptions.pojo.v2.Subscription> subscriptionsList = new ArrayList<com.yodlee.insights.subscriptions.pojo.v2.Subscription>();
		com.yodlee.insights.subscriptions.pojo.v2.Subscription subscription = new com.yodlee.insights.subscriptions.pojo.v2.Subscription();
		if (!insightName.isEmpty()) {
			subscription.setInsightName(insightName);
		}
		String[] entities = entityParameterName.split("\\s+");
		String entityName = null;
		Configuration configuration = null;
		ArrayList<Configuration> configurationsList = new ArrayList<Configuration>();
		String[] thresholds = thresholdNameValueType.split("\\s+");
		List<String> thresholdValList = new ArrayList<String>(Arrays.asList(thresholds));
		ArrayList<com.yodlee.insights.subscriptions.pojo.v2.Threshold> thresholdsList = null;
		int thresholdIndex = 0;
		for (int i = 0; i < entities.length; i++) {
			entityName = entities[i].toString();

			if (thresholdsList == null) {
				thresholdsList = new ArrayList<com.yodlee.insights.subscriptions.pojo.v2.Threshold>();
			}

			if (supportingEntiesList.contains(entities[i])) {
				configuration = new Configuration();
				configuration.setEntityType(entityName.toUpperCase());
				String[] durations = duration.split("\\s+");
				if (!duration.isEmpty()) {
					configuration.setDuration(durations[i]);
				}
				String[] frequencies = frequency.split("\\s+");
				if (!frequency.isEmpty()) {
					configuration.setFrequency(frequencies[i]);
				}
				String[] subscriptions = isSubscribed.split("\\s+");
				if (!isSubscribed.isEmpty()) {
					configuration.setSubscribed(Boolean.parseBoolean(subscriptions[i]));
				}
				String[] patchEntites = patchDiffEntityIds.split("\\s+");
				if (!patchDiffEntityIds.isEmpty()) {
					if (!patchEntites[i].equals("SKIP") && SubscriptionCtx.equalsIgnoreCase("User")
							|| SubscriptionCtx.equalsIgnoreCase("Cobrand"))
						configuration.setEntityId(patchEntites[i]);
				}
				com.yodlee.insights.subscriptions.pojo.v2.Threshold entityThreshold = null;
				int thresholdLength = thresholds.length;
				boolean flag = false;
				if (thresholds[0].length() > 2 && thresholds.length > 0) {
					for (int j = 0; j < thresholdLength; j++) {
						entityThreshold = new com.yodlee.insights.subscriptions.pojo.v2.Threshold();
						flag = false;
						if (thresholdIndex <= thresholdValList.size()) {
							String[] threshold = thresholdValList.get(thresholdIndex++).toString().split(",");
							entityThreshold.setName(threshold[0]);
							if (threshold[1].contains("&")) {
								entityThreshold.setValue(threshold[1].replace('&', ','));
							} else {
								entityThreshold.setValue(threshold[1]);
							}
							entityThreshold.setType(threshold[2]);

							if (entities.length == thresholdValList.size()) {
								thresholdsList.add(entityThreshold);
								configuration.setThreshold(thresholdsList);
								configurationsList.add(configuration);
								thresholdsList = null;
								flag = true;
								break;
							}
							if (thresholdLength / 2 == entities.length)
								thresholdLength = 2;
							thresholdsList.add(entityThreshold);
						}

					}
					if (!flag) {
						configuration.setThreshold(thresholdsList);
						configurationsList.add(configuration);
						thresholdsList = null;
					}

				}
				if (thresholdNameValueType.isEmpty()
						&& (!isSubscribed.isEmpty() || !frequency.isEmpty() || !duration.isEmpty()))
					configurationsList.add(configuration);
			}
		}

		if (SubscriptionCtx.equals("Cobrand")) {
			if (configurationsList != null)
				subscription.setCustomerConfiguration(configurationsList);
		}
		if (SubscriptionCtx.equals("User")) {

			subscription.setUserConfiguration(configurationsList);
		}
		subscriptionsList.add(subscription);
		JSONObject subscriptionObj = new JSONObject();
		String patchSubscriptionJson = null;
		if (SubscriptionCtx.equals("Cobrand")) {
			subscriptionObj.put("customerSubscription", subscriptionsList);
			patchSubscriptionJson = subscriptionObj.toString().replace("subscribed", "isSubscribed");
			System.out.println("Customer Subscription Json Payload = " + patchSubscriptionJson);
		}
		if (SubscriptionCtx.equals("User")) {
			subscriptionObj.put("userSubscription", subscriptionsList);
			patchSubscriptionJson = subscriptionObj.toString().replace("subscribed", "isSubscribed");
			System.out.println("User Subscription Json Payload = " + patchSubscriptionJson);
		}

		return patchSubscriptionJson;
	}

}
