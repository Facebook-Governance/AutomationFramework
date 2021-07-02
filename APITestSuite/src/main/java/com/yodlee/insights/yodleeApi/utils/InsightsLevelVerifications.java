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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.restassured.response.Response;
import junit.framework.Assert;

public class InsightsLevelVerifications {

	protected Logger logger = LoggerFactory.getLogger(InsightsLevelVerifications.class);
	InsightsGenerics insightsGenerics = new InsightsGenerics();
	boolean notificationInsightsStatus;
	JsonParser jsonParser = new JsonParser();

	// LowBalanceWarning Insights /notifications API response verification
	public boolean verifyLowBalanceWarningNotificationsResponse(Response userInsightsResponse, String insightName,
			String triggerType, String dataLevels, String entityParameterName, String thresholdNameValueType,
			String expectedNumberOfInsights, int numberOfKeys, FailureReason failureReason, String patchAccountIds,
			String insightExpectedKeys) {
		notificationInsightsStatus = true;
		if (Integer.parseInt(expectedNumberOfInsights) == 0) {
			String insightResponse = userInsightsResponse.asString();
			boolean insightsNotAvailable = (insightResponse.equals("{}")) ? true : false;
			if (!insightsNotAvailable) {
				failNotificationTest(insightName,
						"Expected  ZERO insights triggered but found more than ZERO Insights ", failureReason);
			}
		} else {
			JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
			JsonArray notificationArray = notificationResponseObject.getAsJsonArray("notification");
			int actualInsights = (notificationArray.get(0).getAsJsonObject().getAsJsonObject("data")
					.getAsJsonObject("model").getAsJsonArray("keys").size() / numberOfKeys);
			if (Integer.parseInt(expectedNumberOfInsights) != actualInsights) {
				/*
				 * failNotificationTest( insightName,
				 * "Expected insights are not generated , Expected = " +
				 * Integer.parseInt(expectedNumberOfInsights) + "  Actual = " + actualInsights,
				 * failureReason);
				 */
			} else {
				for (int i = 0; i < 1; i++) {
					JsonObject insightObj = (JsonObject) notificationArray.get(i);
					if (!insightObj.get("name").getAsString().equals(insightName))
						failNotificationTest(insightName, "insightName", failureReason);
					if (!insightObj.get("triggerType").getAsString().equals(triggerType))
						failNotificationTest(insightName, "triggerType", failureReason);
					verifyDateTimeStamp(insightName, insightObj.get("asOfDate").getAsString());
					JsonObject dataEntity = insightObj.getAsJsonObject("data");
					JsonObject levelObject = dataEntity.getAsJsonObject("level");
					if (!levelObject.get("type").getAsString().equals(dataLevels))
						failNotificationTest(insightName, "leveltype", failureReason);
					JsonObject thresholdObject = (JsonObject) (levelObject.getAsJsonArray("threshold")).get(0);
					try {
						thresholdObject.get("name");
						thresholdObject.get("value");
						thresholdObject.get("type");
					} catch (Exception e) {
						failNotificationTest(insightName, "threshold Object keys are missing", failureReason);
					}
					if (!thresholdObject.get("name").getAsString().equals("BALANCE")
							&& !thresholdObject.get("type").getAsString().equals("AMOUNT")) {
						failNotificationTest(insightName, "name OR type in the threshold objects are incorrect",
								failureReason);
					}

					String[] thresholdDetails = thresholdNameValueType.split(",");
					int thresholdValue = Integer.parseInt(thresholdDetails[1]);

					int actualThresholdAmount = Integer.parseInt(thresholdObject.get("value").getAsString());
					if (actualThresholdAmount != thresholdValue) {
						failNotificationTest(insightName,
								"ThresholdAmount is not matching in notifications response expected threshold is = "
										+ thresholdValue + " but found actual = " + actualThresholdAmount,
								failureReason);
					}

					JsonObject modelObject = dataEntity.getAsJsonObject("model");
					JsonArray keysArray = modelObject.getAsJsonArray("keys");
					if ((numberOfKeys * Integer.parseInt(expectedNumberOfInsights)) != keysArray.size()) {
						failNotificationTest(insightName,
								"Number of Keys in the notification are not matching expected = " + numberOfKeys
										+ " but found actual = " + keysArray.size(),
								failureReason);
					}

					// check for correct keys expected keys vs actual keys
					String[] expKeysArray = insightExpectedKeys.split(",");
					StringBuilder expectedKeys = new StringBuilder();
					for (int index = 0; index < Integer.parseInt(expectedNumberOfInsights); index++) {
						for (int j = 0; j < numberOfKeys; j++) {
							expectedKeys.append(expKeysArray[0] + "[" + index + "]." + expKeysArray[j + 1]);
						}
					}
					StringBuilder actualKeys = new StringBuilder();
					for (int index1 = 0; index1 < keysArray.size(); index1++) {
						actualKeys.append(keysArray.get(index1).getAsString());
					}
					// expected and actual keys check
					if (!expectedKeys.toString().equals(actualKeys.toString())) {
						failNotificationTest(
								insightName, "Keys in the notification are not correct expected = "
										+ expectedKeys.toString() + " but found actual = " + actualKeys.toString(),
								failureReason);
						System.err.println("Both(Keys) Are not same");
					}
					// validations.append(" Keys for one OR more insights generated ,");
					JsonArray valuesArray = modelObject.getAsJsonArray("values");
					int availableBalanceInitialPos = 5, totalKeysValues = 7;
					if (patchAccountIds.isEmpty()) {
						for (int j = 0; j < Integer.parseInt(expectedNumberOfInsights); j++) {
							try {
								String actualInsightValue = valuesArray.get(availableBalanceInitialPos).toString()
										.substring(1,
												valuesArray.get(availableBalanceInitialPos).toString().length() - 3);
								availableBalanceInitialPos += totalKeysValues;
								int actualValInInsight = Integer.parseInt(actualInsightValue);
								if (actualValInInsight > thresholdValue) {
									/*
									 * failNotificationTest(insightName, "Expected value must be less than " +
									 * thresholdValue + "But found actual value " + actualValInInsight,
									 * failureReason);
									 */
								}
							} catch (Exception e) {
								failNotificationTest(insightName,
										"Order of Values presenting in the values[] array of notifications response are Incorrect/Inconsistent between Insights",
										failureReason);
							}
						}
					}
				}
			}
		}
		return notificationInsightsStatus;
	}

	public boolean verifyCreditLimitWarningNotificationsResponse(Response userInsightsResponse, String insightName,
			String triggerType, String dataLevels, String entityParameterName, String thresholdNameValueType,
			String expectedNumberOfInsights, int numberOfKeys, FailureReason failureReason, String patchAccountIds,
			String insightExpectedKeys, StringBuilder validations) {
		notificationInsightsStatus = true;
		if (Integer.parseInt(expectedNumberOfInsights) == 0) {
			String insightResponse = userInsightsResponse.asString();
			boolean insightsNotAvailable = (insightResponse.equals("{}")) ? true : false;
			validations.append("  Response ");
			if (!insightsNotAvailable) {
				failNotificationTest(insightName,
						"Expected  ZERO insights triggered but found more than ZERO Insights ", failureReason);
			}
		} else {
			JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
			JsonArray notificationArray = notificationResponseObject.getAsJsonArray("notification");
			int actualInsights = (notificationArray.get(0).getAsJsonObject().getAsJsonObject("data")
					.getAsJsonObject("model").getAsJsonArray("keys").size() / numberOfKeys);
			if (Integer.parseInt(expectedNumberOfInsights) != actualInsights) {
				failNotificationTest(
						insightName, "Expected insights are not generated , Expected = "
								+ Integer.parseInt(expectedNumberOfInsights) + "  Actual = " + actualInsights,
						failureReason);
			} else {
				validations.append(" Expected VS Actual number of Insights ,");
				for (int i = 0; i < 1; i++) {
					JsonObject insightObj = (JsonObject) notificationArray.get(i);
					if (!insightObj.get("name").getAsString().equals(insightName))
						failNotificationTest(insightName, "insightName", failureReason);
					validations.append("  Insight Name ,");
					if (!insightObj.get("triggerType").getAsString().equals(triggerType))
						failNotificationTest(insightName, "triggerType", failureReason);
					validations.append(" Trigger Type ,");
					verifyDateTimeStamp(insightName, insightObj.get("asOfDate").getAsString());
					validations.append("  Insight triggered date  ,");
					JsonObject dataEntity = insightObj.getAsJsonObject("data");
					JsonObject levelObject = dataEntity.getAsJsonObject("level");
					if (!levelObject.get("type").getAsString().equals(dataLevels))
						failNotificationTest(insightName, "leveltype", failureReason);
					validations.append(" supporting entity ,");
					JsonObject thresholdObject = (JsonObject) levelObject.getAsJsonArray("threshold").get(0);
					try {
						thresholdObject.get("name");
						thresholdObject.get("value");
						thresholdObject.get("type");
					} catch (Exception e) {
						failNotificationTest(insightName, "threshold Object keys are missing", failureReason);
					}

					String[] thresholdDetails = thresholdNameValueType.split(",");
					if (!thresholdObject.get("name").getAsString().equals(thresholdDetails[0])
							&& !thresholdObject.get("type").getAsString().equals(thresholdDetails[1])) {
						failNotificationTest(insightName, "name OR type in the threshold objects are incorrect",
								failureReason);
					}
					validations.append(" Name and Type ,");
					Float thresholdValue = Float.parseFloat(thresholdDetails[1]) / 100;
					Float actualThresholdAmount = Float.parseFloat(thresholdObject.get("value").getAsString());
					if (Math.abs(actualThresholdAmount) != Math.abs(thresholdValue)) {
						failNotificationTest(insightName,
								"ThresholdAmount is not matching in notifications response expected threshold is = "
										+ thresholdValue + " but found actual = " + actualThresholdAmount,
								failureReason);
					}
					validations.append(" threshold Amount ,");
					JsonObject modelObject = dataEntity.getAsJsonObject("model");
					JsonArray keysArray = modelObject.getAsJsonArray("keys");
					if ((numberOfKeys * Integer.parseInt(expectedNumberOfInsights)) != keysArray.size()) {
						failNotificationTest(insightName,
								"Number of Keys in the notification are not matching expected = " + numberOfKeys
										+ " but found actual = " + keysArray.size(),
								failureReason);
					}
					// check for correct keys expected keys vs actual keys
					String[] expKeysArray = insightExpectedKeys.split(",");
					StringBuilder expectedKeys = new StringBuilder();
					for (int index = 0; index < Integer.parseInt(expectedNumberOfInsights); index++) {
						for (int j = 0; j < numberOfKeys; j++) {
							expectedKeys.append(expKeysArray[0] + "[" + index + "]." + expKeysArray[j + 1]);
						}
					}
					StringBuilder actualKeys = new StringBuilder();
					for (int index1 = 0; index1 < keysArray.size(); index1++) {
						actualKeys.append(keysArray.get(index1).getAsString());
					}
					// expected and actual keys check
					if (!expectedKeys.toString().equals(actualKeys.toString())) {
						/*
						 * failNotificationTest(
						 * insightName,"Keys in the notification are not correct expected = "+
						 * expectedKeys.toString() + " but found actual = " + actualKeys.toString(),
						 * failureReason);
						 */
						System.err.println("Both(Keys) Are not same");
					}
					validations.append(" Keys for one OR more insights generated ,");

					JsonArray valuesArray = modelObject.getAsJsonArray("values");
					int creditPercentPos = 9;
					if (patchAccountIds.isEmpty()) {
						for (int j = 0; j < Integer.parseInt(expectedNumberOfInsights); j++) {
							try {
								String actualInsightValue = valuesArray.get(creditPercentPos).toString().substring(1,
										valuesArray.get(creditPercentPos).toString().length() - 1);
								creditPercentPos += numberOfKeys;
								Float actualValInInsight = Float.parseFloat(actualInsightValue);
								if (Math.abs(actualValInInsight) <= Math.abs(thresholdValue)) {
									failNotificationTest(insightName, "Expected PERCENT is less than threshold  "
											+ thresholdValue + "  But found actual value  " + actualValInInsight,
											failureReason);
								}
							} catch (Exception e) {
								failNotificationTest(insightName,
										"Order of Values presenting in the values[] array of notifications response are Incorrect/Inconsistent between Insights",
										failureReason);
							}
						}
					}
					validations.append(" and Values for one OR more insights generated ,");
				}
			}
		}
		return notificationInsightsStatus;
	}

	public void verifyDateTimeStamp(String insightName, String notificationDate) {

		Date currentDate = new Date();
		Date insightGeneratedDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			insightGeneratedDate = sdf.parse(notificationDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (!sdf.format(insightGeneratedDate).equals(sdf.format(currentDate))) {
			logger.info("Insight generated date is not matching with current Date");
			Assert.fail("Test FAILED ### " + insightName);
		}

	}

	public boolean verifyCreatedDateTimeStamp(String insightName, String notificationDate) {

		Date currentDate = new Date();
		Date insightGeneratedDate = null;
		boolean validateDate = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			insightGeneratedDate = sdf.parse(notificationDate.substring(0, 10));
			Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z");
			validateDate = p.matcher(notificationDate).matches();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!sdf.format(insightGeneratedDate).equals(sdf.format(currentDate))) {
			logger.info("Insight generated date is not matching with current Date");
			validateDate = false;
		}
		return validateDate;
	}

	public void failNotificationTest(String insightName, String expectedAttribute, FailureReason failureReason) {
		logger.info(
				"Test FAILED for insight " + insightName + " Reason  " + expectedAttribute + " values doesn't match");
		failureReason.setFailureReason(expectedAttribute);
		notificationInsightsStatus = false;

	}

	public boolean verifyAllInsightsResponse(Response userInsightsResponse, String insightName, String triggerType,
			String dataLevels, String entityParameterName, String thresholdNameValueType,
			String expectedNumberOfInsights, int numberOfKeys, FailureReason failureReason, JsonArray expKeysArray,
			HashMap<String, String> entityIdsMap, String validateKeys) {
		notificationInsightsStatus = true;
		JsonArray insightsArray = null;
		int thresholdValue = 0;
		if (Integer.parseInt(expectedNumberOfInsights) == 0) {
			boolean insightsNotAvailable = false;
			String insightResponse = userInsightsResponse.asString();
			if (insightResponse.equals("{}")) {
				insightsNotAvailable = true;
			} else {
				JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(insightResponse);
				try {
					insightsArray = notificationResponseObject.getAsJsonArray("notification");
					insightsNotAvailable = (insightsArray.size() == 0) ? true : false;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!insightsNotAvailable) {
				failNotificationTest(insightName, "Expected   " + expectedNumberOfInsights
						+ " Insights but Actual Inssights " + insightsArray.size(), failureReason);
			}

		} else {
			JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
			JsonArray notificationArray = notificationResponseObject.getAsJsonArray("notification");
			int actualInsights = notificationArray.size();
			if (Integer.parseInt(expectedNumberOfInsights) != actualInsights) {
				failNotificationTest(
						insightName, "Expected insights are not generated , Expected = "
								+ Integer.parseInt(expectedNumberOfInsights) + "  Actual = " + actualInsights,
						failureReason);
			} else {
				for (int i = 0; i < notificationArray.size(); i++) {
					JsonObject insightObj = (JsonObject) notificationArray.get(i);
					if (!insightObj.get("name").getAsString().equals(insightName))
						failNotificationTest(insightName, "insightName", failureReason);
					if (!insightObj.get("triggerType").getAsString().equals(triggerType))
						failNotificationTest(insightName, "triggerType", failureReason);
					if (!verifyCreatedDateTimeStamp(insightName, insightObj.get("createdDate").getAsString())) {
						failNotificationTest(insightName,
								"InValid createdDate either format is not correct OR created date may be greater than current date",
								failureReason);
					}
					JsonObject dataEntity = insightObj.getAsJsonObject("data");
					JsonObject levelObject = dataEntity.getAsJsonObject("level");
					if (entityParameterName.equals("account") && !insightName.equals("CreditUtilization")
							&& !insightName.equals("LiabilityChange") && !insightName.equals("PropertyValueChange")
							&& !insightName.equals("NetworthChange") && !insightName.equals("AssetChange")) {
						if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
							failNotificationTest(insightName,
									"Account Id = " + levelObject.get("id").getAsString() + " Doesn't Exists",
									failureReason);
						}
					}
					if (entityParameterName.equals("view")) {
						if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
							failNotificationTest(insightName,
									"View Id = " + levelObject.get("id").getAsString() + " Doesn't Exists",
									failureReason);
						}
					}
					if (entityParameterName.equals("accountGroup")) {
						if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
							failNotificationTest(insightName,
									"Account Group Id = " + levelObject.get("id").getAsString() + " Doesn't Exists",
									failureReason);
						}
					}
					if (entityParameterName.equals("goal")) {
						/*
						 * if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
						 * failNotificationTest(insightName,"Goal Id = " +
						 * levelObject.get("id").getAsString() + " Doesn't Exists",failureReason); }
						 */
					}
					String[] applicableEntity = dataLevels.split("\\s+");
					String expectedLeveType = applicableEntity[0];
					if (applicableEntity.length > 1) {
						expectedLeveType = applicableEntity[i];
					}
					if (!levelObject.get("type").getAsString().equals(expectedLeveType))
						failNotificationTest(insightName, "leveltype", failureReason);

					JsonObject thresholdObject = null;
					if (!thresholdNameValueType.isEmpty()) {
						thresholdObject = (JsonObject) (levelObject.getAsJsonArray("threshold")).get(0);
						try {
							thresholdObject.get("name");
							thresholdObject.get("value");
							thresholdObject.get("type");
						} catch (Exception e) {
							failNotificationTest(insightName, "threshold Object keys are missing", failureReason);
						}
						String expectedThresholds = thresholdNameValueType.split("\\s+")[0];
						String[] expectedThresholdValues = expectedThresholds.split(",");
						if (!thresholdObject.get("name").getAsString().equals(expectedThresholdValues[0])
								&& !thresholdObject.get("type").getAsString().equals(expectedThresholdValues[2])) {
							failNotificationTest(insightName, "name OR type in the threshold objects are incorrect",
									failureReason);
						}
						int expectedThresholdValue = (int) Double.parseDouble(expectedThresholdValues[1]);
						System.out.println("Threshold Value" + expectedThresholdValue);
						thresholdValue = expectedThresholdValue;
						int actualThresholdValue = (int) Double.parseDouble(thresholdObject.get("value").getAsString());
						if (actualThresholdValue != expectedThresholdValue) {
							failNotificationTest(insightName,
									"ThresholdAmount is not matching in notifications response expected threshold is = "
											+ thresholdValue + " but found actual = " + actualThresholdValue,
									failureReason);
						}
					}

					JsonObject modelObject = dataEntity.getAsJsonObject("model");
					JsonArray keysArray = modelObject.getAsJsonArray("keys");
					boolean merchantPresent = false;
					StringBuilder actualKeys = new StringBuilder();
					for (int index1 = 0; index1 < keysArray.size(); index1++) {
						if (keysArray.get(index1).getAsString().contains("merchant")
								&& !insightName.equals("BillNotPaid") && !insightName.equals("LargeDepositNotice")
								&& !insightName.equals("SalaryDeposited")
								&& !insightName.equals("RecurringDepositLate")) {
							System.out.println("MERCHANT Present -->" + keysArray.get(index1).getAsString());
							merchantPresent = true;
						}
						actualKeys.append(keysArray.get(index1).getAsString());
					}

					// charraeck for correct keys expected keys vs actual keys
					StringBuilder expectedKeys = new StringBuilder();
					for (int keys = 0; keys < expKeysArray.size(); keys++) {
						JsonObject entityKeyObject = (JsonObject) expKeysArray.get(keys);
						if (entityKeyObject.keySet().toString().contains(validateKeys)) {
							String[] keyArray = entityKeyObject.get(entityKeyObject.keySet().toString().substring(1,
									entityKeyObject.keySet().toString().length() - 1)).getAsString().split(",");
							for (int keys1 = 1; keys1 < keyArray.length; keys1++) {
								if (merchantPresent && keyArray[keys1 - 1].toString().equals("transaction[0].date")) {
									expectedKeys.append(keyArray[0] + ".transaction[0].merchant.name");// Large Deposit
																										// Notice extra
																										// key will be
																										// appended when
																										// merchant is
																										// present
									expectedKeys.append(keyArray[0] + "." + keyArray[keys1]);
								} else {
									expectedKeys.append(keyArray[0] + "." + keyArray[keys1]);
								}

							}
						}
					}
					int expectedNoOfKeys = numberOfKeys;
					expectedNoOfKeys = (merchantPresent) ? expectedNoOfKeys += 1 : numberOfKeys;
					if (expectedNoOfKeys != keysArray.size()) {
						failNotificationTest(insightName,
								"Number of Keys in the notification are not matching expected = " + expectedNoOfKeys
										+ " but found actual = " + keysArray.size(),
								failureReason);
					}

					// expected and actual keys check
					if (!expectedKeys.toString().equals(actualKeys.toString())) {
						failNotificationTest(
								insightName, "Keys in the notification are not correct expected = "
										+ expectedKeys.toString() + " but found actual = " + actualKeys.toString(),
								failureReason);
						System.err.println("Both(Keys) Are not same");
					}
					// validations.append(" Keys for one OR more insights generated ,");
					JsonArray valuesArray = modelObject.getAsJsonArray("values");
					if (insightName.equals("LowBalanceWarning")) {
						int availableBalanceInitialPos = 5, totalKeysValues = 7;
						String actualGenereatedInsightValue = valuesArray.get(availableBalanceInitialPos).toString()
								.substring(1, valuesArray.get(availableBalanceInitialPos).toString().length() - 4);
						if ((int) Double.parseDouble(actualGenereatedInsightValue) >= thresholdValue) {
							failNotificationTest(insightName, "Expected value must be less than " + thresholdValue
									+ "But found actual value " + Integer.parseInt(actualGenereatedInsightValue),
									failureReason);
						}
					}

				}
			}
		}
		return notificationInsightsStatus;
	}

	public boolean verifyInsightsForSmokeTest(Response userInsightsResponse, String insightName, String triggerType,
			String dataLevels, String entityParameterName, String thresholdNameValueType,
			String expectedNumberOfInsights, int numberOfKeys, FailureReason failureReason, JsonArray expKeysArray,
			HashMap<String, String> entityIdsMap, String validateKeys) {
		notificationInsightsStatus = true;
		JsonArray insightsArray = null;
		int thresholdValue = 0;
		if (Integer.parseInt(expectedNumberOfInsights) == 0) {
			boolean insightsNotAvailable = false;
			String insightResponse = userInsightsResponse.asString();
			if (insightResponse.equals("{}")) {
				insightsNotAvailable = true;
			} else {
				JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(insightResponse);
				try {
					insightsArray = notificationResponseObject.getAsJsonArray("notification");
					insightsNotAvailable = (insightsArray.size() == 0) ? true : false;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!insightsNotAvailable) {
				failNotificationTest(insightName, "Expected   " + expectedNumberOfInsights
						+ " Insights but Actual Inssights " + insightsArray.size(), failureReason);
			}

		} else {
			JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
			JsonArray notificationArray = notificationResponseObject.getAsJsonArray("notification");
			int actualInsights = notificationArray.size();
			if (!(actualInsights >= 1)) {
				failNotificationTest(
						insightName, "Expected insights are not generated , Expected = "
								+ Integer.parseInt(expectedNumberOfInsights) + "  Actual = " + actualInsights,
						failureReason);
			}
		}
		return notificationInsightsStatus;
	}

	public boolean verifyFinancialFeesInsightsResponse(Response userInsightsResponse, String insightName,
			String triggerType, String dataLevels, String entityParameterName, String thresholdNameValueType,
			String expectedNumberOfInsights, int numberOfKeys, FailureReason failureReason, JsonArray expKeysArray,
			HashMap<String, String> entityIdsMap, String validateKeys, String duration) {
		notificationInsightsStatus = true;
		JsonArray insightsArray = null;
		int thresholdValue = 0;
		if (Integer.parseInt(expectedNumberOfInsights) == 0) {
			boolean insightsNotAvailable = false;
			String insightResponse = userInsightsResponse.asString();
			if (insightResponse.equals("{}")) {
				insightsNotAvailable = true;
			} else {
				JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(insightResponse);
				try {
					insightsArray = notificationResponseObject.getAsJsonArray("notification");
					insightsNotAvailable = (insightsArray.size() == 0) ? true : false;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!insightsNotAvailable) {
				failNotificationTest(insightName, "Expected   " + expectedNumberOfInsights
						+ " Insights but Actual Inssights " + insightsArray.size(), failureReason);
			}

		} else {
			JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
			JsonArray notificationArray = notificationResponseObject.getAsJsonArray("notification");
			int actualInsights = notificationArray.size();
			if (Integer.parseInt(expectedNumberOfInsights) != actualInsights) {
				failNotificationTest(
						insightName, "Expected insights are not generated , Expected = "
								+ Integer.parseInt(expectedNumberOfInsights) + "  Actual = " + actualInsights,
						failureReason);
			} else {
				for (int i = 0; i < notificationArray.size(); i++) {
					JsonObject insightObj = (JsonObject) notificationArray.get(i);
					if (!insightObj.get("name").getAsString().equals(insightName))
						failNotificationTest(insightName, "insightName", failureReason);
					if (!insightObj.get("triggerType").getAsString().equals(triggerType))
						failNotificationTest(insightName, "triggerType", failureReason);
					/*
					 * if(!verifyCreatedDateTimeStamp(insightName,
					 * insightObj.get("createdDate").getAsString())) {
					 * failNotificationTest(insightName,
					 * "InValid createdDate either format is not correct OR created date may be greater than current date"
					 * , failureReason); }
					 */
					JsonObject dataEntity = insightObj.getAsJsonObject("data");
					JsonObject levelObject = dataEntity.getAsJsonObject("level");
					if (entityParameterName.equals("account")) {
						if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
							failNotificationTest(insightName,
									"Account Id = " + levelObject.get("id").getAsString() + " Doesn't Exists",
									failureReason);
						}
					}
					if (entityParameterName.equals("view")) {
						if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
							failNotificationTest(insightName,
									"View Id = " + levelObject.get("id").getAsString() + " Doesn't Exists",
									failureReason);
						}
					}
					if (entityParameterName.equals("accountGroup")) {
						if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
							failNotificationTest(insightName,
									"Account Group Id = " + levelObject.get("id").getAsString() + " Doesn't Exists",
									failureReason);
						}
					}
					if (entityParameterName.equals("goal")) {
						/*
						 * if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
						 * failNotificationTest(insightName,"Goal Id = " +
						 * levelObject.get("id").getAsString() + " Doesn't Exists",failureReason); }
						 */
					}
					String[] applicableEntity = dataLevels.split("\\s+");
					String expectedLeveType = applicableEntity[0];
					if (applicableEntity.length > 1) {
						expectedLeveType = applicableEntity[i];
					}
					if (!levelObject.get("type").getAsString().equalsIgnoreCase(validateKeys))
						failNotificationTest(insightName, "leveltype", failureReason);

					JsonObject thresholdObject = null;
					if (!thresholdNameValueType.isEmpty()) {
						thresholdObject = (JsonObject) (levelObject.getAsJsonArray("threshold")).get(0);
						try {
							thresholdObject.get("name");
							thresholdObject.get("value");
							thresholdObject.get("type");
						} catch (Exception e) {
							failNotificationTest(insightName, "threshold Object keys are missing", failureReason);
						}
						String expectedThresholds = thresholdNameValueType.split("\\s+")[0];
						String[] expectedThresholdValues = expectedThresholds.split(",");
						if (!thresholdObject.get("name").getAsString().equals(expectedThresholdValues[0])
								&& !thresholdObject.get("type").getAsString().equals(expectedThresholdValues[2])) {
							failNotificationTest(insightName, "name OR type in the threshold objects are incorrect",
									failureReason);
						}
						int expectedThresholdValue = Integer.parseInt(expectedThresholdValues[1]);
						thresholdValue = expectedThresholdValue;
						int actualThresholdValue = (int) Double.parseDouble(thresholdObject.get("value").getAsString());
						if (actualThresholdValue != expectedThresholdValue) {
							failNotificationTest(insightName,
									"ThresholdAmount is not matching in notifications response expected threshold is = "
											+ thresholdValue + " but found actual = " + actualThresholdValue,
									failureReason);
						}
					}

					JsonObject modelObject = dataEntity.getAsJsonObject("model");
					JsonArray keysArray = modelObject.getAsJsonArray("keys");
					boolean merchantPresent = false;
					StringBuilder actualKeys = new StringBuilder();
					for (int index1 = 0; index1 < keysArray.size(); index1++) {
						if (keysArray.get(index1).getAsString().contains("merchant")
								&& (!insightName.equals("TopMerchants"))) {
							System.out.println("MERCHANT Present -->" + keysArray.get(index1).getAsString());
							merchantPresent = true;
							System.out.println("isMerchantPresent:" + merchantPresent);
						}
						actualKeys.append(keysArray.get(index1).getAsString());
					}

					// charraeck for correct keys expected keys vs actual keys
					StringBuilder expectedKeys = new StringBuilder();

					for (int keys = 0; keys < expKeysArray.size(); keys++) {
						JsonObject entityKeyObject = (JsonObject) expKeysArray.get(keys);
						if (entityKeyObject.keySet().toString().contains(validateKeys)) {
							String[] keyArray = entityKeyObject.get(entityKeyObject.keySet().toString().substring(1,
									entityKeyObject.keySet().toString().length() - 1)).getAsString().split(",");
							for (int keys1 = 1; keys1 < keyArray.length; keys1++) {
								if (merchantPresent && keyArray[keys1 - 1].toString().equals("transaction[0].date")) {
									expectedKeys.append(keyArray[0] + ".transaction[0].merchant.name");// Large Deposit
																										// Notice extra
																										// key will be
																										// appended when
																										// merchant is
																										// present
									expectedKeys.append(keyArray[0] + "." + keyArray[keys1]);
								} else {
									expectedKeys.append(keyArray[0] + "." + keyArray[keys1]);
								}

							}
						}
					}

					if ((insightName.equals("FinancialFees") || insightName.equals("SpendingbyCategory")
							|| insightName.equals("TopMerchants")) && !duration.equals("THIS_MONTH")
							&& !validateKeys.equalsIgnoreCase("view")) {
						JsonArray expPBKeysArray = null;
						if (!insightName.equals("FinancialFees"))
							expPBKeysArray = insightsGenerics.getInsightsKeysforPBDuration(insightName, duration);
						else
							expPBKeysArray = insightsGenerics.getInsightsKeysforPB(insightName);

						for (int keys = 0; keys < expPBKeysArray.size(); keys++) {
							JsonObject entityKeyObject = (JsonObject) expPBKeysArray.get(keys);
							if (entityKeyObject.keySet().toString().contains("PB")) {
								String[] keyArray = entityKeyObject
										.get(entityKeyObject.keySet().toString().substring(1,
												entityKeyObject.keySet().toString().length() - 1))
										.getAsString().split(",");
								for (int keys1 = 1; keys1 < keyArray.length; keys1++) {
									expectedKeys.append(keyArray[0] + "." + keyArray[keys1]);
								}
							}
						}
					}
					int expectedNoOfKeys = numberOfKeys;
					expectedNoOfKeys = (merchantPresent) ? expectedNoOfKeys += 1 : numberOfKeys;
					if (expectedNoOfKeys != keysArray.size()) {
						failNotificationTest(insightName,
								"Number of Keys in the notification are not matching expected = " + expectedNoOfKeys
										+ " but found actual = " + keysArray.size(),
								failureReason);
					}

					if (!expectedKeys.toString().equals(actualKeys.toString())) {
						failNotificationTest(
								insightName, "Keys in the notification are not correct expected = "
										+ expectedKeys.toString() + " but found actual = " + actualKeys.toString(),
								failureReason);
						System.err.println("Both(Keys) Are not same");
					}
				}
			}
		}
		return notificationInsightsStatus;
	}

	public boolean verifyAllRefeshInsightsResponseForSmoke(Response userInsightsResponse, String insightName,
			String triggerType, String dataLevels, String entityParameterName, String thresholdNameValueType,
			String expectedNumberOfInsights, int numberOfKeys, FailureReason failureReason, JsonArray expKeysArray,
			HashMap<String, String> entityIdsMap, String validateKeys) {
		notificationInsightsStatus = true;
		JsonArray insightsArray = null;
		int thresholdValue = 0;
		if (Integer.parseInt(expectedNumberOfInsights) == 0) {
			boolean insightsNotAvailable = false;
			String insightResponse = userInsightsResponse.asString();
			if (insightResponse.equals("{}")) {
				insightsNotAvailable = true;
			} else {
				JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(insightResponse);
				try {
					insightsArray = notificationResponseObject.getAsJsonArray("notification");
					insightsNotAvailable = (insightsArray.size() == 0) ? true : false;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!insightsNotAvailable) {
				failNotificationTest(insightName, "Expected   " + expectedNumberOfInsights
						+ " Insights but Actual Inssights " + insightsArray.size(), failureReason);
			}

		} else {
			JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
			JsonArray notificationArray = notificationResponseObject.getAsJsonArray("notification");
			int actualInsights = notificationArray.size();
			if (actualInsights == 0) {
				failNotificationTest(
						insightName, "Expected insights are not generated , Expected = "
								+ Integer.parseInt(expectedNumberOfInsights) + "  Actual = " + actualInsights,
						failureReason);
			} else {
				for (int i = 0; i < notificationArray.size(); i++) {
					JsonObject insightObj = (JsonObject) notificationArray.get(i);
					if (!insightObj.get("name").getAsString().equals(insightName))
						failNotificationTest(insightName, "insightName", failureReason);
					if (!insightObj.get("triggerType").getAsString().equals(triggerType))
						failNotificationTest(insightName, "triggerType", failureReason);
					if (!verifyCreatedDateTimeStamp(insightName, insightObj.get("createdDate").getAsString())) {
						failNotificationTest(insightName,
								"InValid createdDate either format is not correct OR created date may be greater than current date",
								failureReason);
					}
					JsonObject dataEntity = insightObj.getAsJsonObject("data");
					JsonObject levelObject = dataEntity.getAsJsonObject("level");
					if (entityParameterName.equals("account")) {
						if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
							failNotificationTest(insightName,
									"Account Id = " + levelObject.get("id").getAsString() + " Doesn't Exists",
									failureReason);
						}
					}
					if (entityParameterName.equals("view")) {
						if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
							failNotificationTest(insightName,
									"View Id = " + levelObject.get("id").getAsString() + " Doesn't Exists",
									failureReason);
						}
					}
					if (entityParameterName.equals("accountGroup")) {
						if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
							failNotificationTest(insightName,
									"Account Group Id = " + levelObject.get("id").getAsString() + " Doesn't Exists",
									failureReason);
						}
					}
					if (entityParameterName.equals("goal")) {
						/*
						 * if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
						 * failNotificationTest(insightName,"Goal Id = " +
						 * levelObject.get("id").getAsString() + " Doesn't Exists",failureReason); }
						 */
					}
					String[] applicableEntity = dataLevels.split("\\s+");
					String expectedLeveType = applicableEntity[0];
					if (applicableEntity.length > 1) {
						expectedLeveType = applicableEntity[i];
					}
					if (!levelObject.get("type").getAsString().equals(expectedLeveType))
						failNotificationTest(insightName, "leveltype", failureReason);

					JsonObject thresholdObject = null;
					if (!thresholdNameValueType.isEmpty()) {
						thresholdObject = (JsonObject) (levelObject.getAsJsonArray("threshold")).get(0);
						try {
							thresholdObject.get("name");
							thresholdObject.get("value");
							thresholdObject.get("type");
						} catch (Exception e) {
							failNotificationTest(insightName, "threshold Object keys are missing", failureReason);
						}
						String expectedThresholds = thresholdNameValueType.split("\\s+")[0];
						String[] expectedThresholdValues = expectedThresholds.split(",");
						if (!thresholdObject.get("name").getAsString().equals(expectedThresholdValues[0])
								&& !thresholdObject.get("type").getAsString().equals(expectedThresholdValues[2])) {
							failNotificationTest(insightName, "name OR type in the threshold objects are incorrect",
									failureReason);
						}
						int expectedThresholdValue = Integer.parseInt(expectedThresholdValues[1]);
						thresholdValue = expectedThresholdValue;
						int actualThresholdValue = (int) Double.parseDouble(thresholdObject.get("value").getAsString());
						if (actualThresholdValue != expectedThresholdValue) {
							failNotificationTest(insightName,
									"ThresholdAmount is not matching in notifications response expected threshold is = "
											+ thresholdValue + " but found actual = " + actualThresholdValue,
									failureReason);
						}
					}

					JsonObject modelObject = dataEntity.getAsJsonObject("model");
					JsonArray keysArray = modelObject.getAsJsonArray("keys");
					boolean merchantPresent = false;
					StringBuilder actualKeys = new StringBuilder();
					for (int index1 = 0; index1 < keysArray.size(); index1++) {
						if (keysArray.get(index1).getAsString().contains("merchant")) {
							System.out.println("MERCHANT Present -->" + keysArray.get(index1).getAsString());
							merchantPresent = true;
						}
						actualKeys.append(keysArray.get(index1).getAsString());
					}

					// charraeck for correct keys expected keys vs actual keys
					StringBuilder expectedKeys = new StringBuilder();
					for (int keys = 0; keys < expKeysArray.size(); keys++) {
						JsonObject entityKeyObject = (JsonObject) expKeysArray.get(keys);
						if (entityKeyObject.keySet().toString().contains(validateKeys)) {
							String[] keyArray = entityKeyObject.get(entityKeyObject.keySet().toString().substring(1,
									entityKeyObject.keySet().toString().length() - 1)).getAsString().split(",");
							for (int keys1 = 1; keys1 < keyArray.length; keys1++) {
								if (merchantPresent && keyArray[keys1 - 1].toString().equals("transaction[0].date")) {
									expectedKeys.append(keyArray[0] + ".transaction[0].merchant.name");// Large Deposit
																										// Notice extra
																										// key will be
																										// appended when
																										// merchant is
																										// present
									expectedKeys.append(keyArray[0] + "." + keyArray[keys1]);
								} else {
									expectedKeys.append(keyArray[0] + "." + keyArray[keys1]);
								}

							}
						}
					}
					int expectedNoOfKeys = numberOfKeys;
					expectedNoOfKeys = (merchantPresent) ? expectedNoOfKeys += 1 : numberOfKeys;
					if (expectedNoOfKeys != keysArray.size()) {
						failNotificationTest(insightName,
								"Number of Keys in the notification are not matching expected = " + expectedNoOfKeys
										+ " but found actual = " + keysArray.size(),
								failureReason);
					}

					// expected and actual keys check
					if (!expectedKeys.toString().equals(actualKeys.toString())) {
						failNotificationTest(
								insightName, "Keys in the notification are not correct expected = "
										+ expectedKeys.toString() + " but found actual = " + actualKeys.toString(),
								failureReason);
						System.err.println("Both(Keys) Are not same");
					}
					// validations.append(" Keys for one OR more insights generated ,");
					JsonArray valuesArray = modelObject.getAsJsonArray("values");
					if (insightName.equals("LowBalanceWarning")) {
						int availableBalanceInitialPos = 2, totalKeysValues = 7;
						String actualGenereatedInsightValue = valuesArray.get(availableBalanceInitialPos).toString()
								.substring(1, valuesArray.get(availableBalanceInitialPos).toString().length() - 4);
						if ((int) Double.parseDouble(actualGenereatedInsightValue) >= thresholdValue) {
							failNotificationTest(insightName, "Expected value must be less than " + thresholdValue
									+ "But found actual value " + Integer.parseInt(actualGenereatedInsightValue),
									failureReason);
						}
					}

				}
			}
		}
		return notificationInsightsStatus;
	}

	public boolean verifyInsightsResponseUpcomingBillSub(Response userInsightsResponse, String insightName,
			String triggerType, String dataLevels, String entityParameterName, String thresholdNameValueType,
			String expectedNumberOfInsights, int numberOfKeys, FailureReason failureReason, JsonArray expKeysArray,
			HashMap<String, String> entityIdsMap, String validateKeys) {
		notificationInsightsStatus = true;
		JsonArray insightsArray = null;
		int thresholdValue = 0;
		if (Integer.parseInt(expectedNumberOfInsights) == 0) {
			boolean insightsNotAvailable = false;
			String insightResponse = userInsightsResponse.asString();
			if (insightResponse.equals("{}")) {
				insightsNotAvailable = true;
			} else {
				JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(insightResponse);
				try {
					insightsArray = notificationResponseObject.getAsJsonArray("notification");
					insightsNotAvailable = (insightsArray.size() == 0) ? true : false;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!insightsNotAvailable) {
				failNotificationTest(insightName, "Expected   " + expectedNumberOfInsights
						+ " Insights but Actual Inssights " + insightsArray.size(), failureReason);
			}

		} else {
			JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
			JsonArray notificationArray = notificationResponseObject.getAsJsonArray("notification");
			int actualInsights = notificationArray.size();
			if (Integer.parseInt(expectedNumberOfInsights) != actualInsights) {
				failNotificationTest(
						insightName, "Expected insights are not generated , Expected = "
								+ Integer.parseInt(expectedNumberOfInsights) + "  Actual = " + actualInsights,
						failureReason);
			} else {
				for (int i = 0; i < notificationArray.size(); i++) {
					JsonObject insightObj = (JsonObject) notificationArray.get(i);
					if (!insightObj.get("name").getAsString().equals(insightName))
						failNotificationTest(insightName, "insightName", failureReason);
					if (!insightObj.get("triggerType").getAsString().equals(triggerType))
						failNotificationTest(insightName, "triggerType", failureReason);
					if (!verifyCreatedDateTimeStamp(insightName, insightObj.get("createdDate").getAsString())) {
						failNotificationTest(insightName,
								"InValid createdDate either format is not correct OR created date may be greater than current date",
								failureReason);
					}
					JsonObject dataEntity = insightObj.getAsJsonObject("data");
					JsonObject levelObject = dataEntity.getAsJsonObject("level");
					if (entityParameterName.equals("account")) {
						if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
							failNotificationTest(insightName,
									"Account Id = " + levelObject.get("id").getAsString() + " Doesn't Exists",
									failureReason);
						}
					}
					if (entityParameterName.equals("view")) {
						if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
							failNotificationTest(insightName,
									"View Id = " + levelObject.get("id").getAsString() + " Doesn't Exists",
									failureReason);
						}
					}
					if (entityParameterName.equals("accountGroup")) {
						if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
							failNotificationTest(insightName,
									"Account Group Id = " + levelObject.get("id").getAsString() + " Doesn't Exists",
									failureReason);
						}
					}
					if (entityParameterName.equals("goal")) {
						/*
						 * if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
						 * failNotificationTest(insightName,"Goal Id = " +
						 * levelObject.get("id").getAsString() + " Doesn't Exists",failureReason); }
						 */
					}
					String[] applicableEntity = dataLevels.split("\\s+");
					String expectedLeveType = applicableEntity[0];
					if (applicableEntity.length > 1) {
						expectedLeveType = applicableEntity[i];
					}
					if (!levelObject.get("type").getAsString().equals(expectedLeveType))
						failNotificationTest(insightName, "leveltype", failureReason);

					JsonObject thresholdObject = null;
					if (!thresholdNameValueType.isEmpty()) {
						thresholdObject = (JsonObject) (levelObject.getAsJsonArray("threshold")).get(0);
						try {
							thresholdObject.get("name");
							thresholdObject.get("value");
							thresholdObject.get("type");
						} catch (Exception e) {
							failNotificationTest(insightName, "threshold Object keys are missing", failureReason);
						}
						String expectedThresholds = thresholdNameValueType.split("\\s+")[0];
						String[] expectedThresholdValues = expectedThresholds.split(",");
						if (!thresholdObject.get("name").getAsString().equals(expectedThresholdValues[0])
								&& !thresholdObject.get("type").getAsString().equals(expectedThresholdValues[2])) {
							failNotificationTest(insightName, "name OR type in the threshold objects are incorrect",
									failureReason);
						}
						int expectedThresholdValue = Integer.parseInt(expectedThresholdValues[1]);
						thresholdValue = expectedThresholdValue;
						int actualThresholdValue = (int) Double.parseDouble(thresholdObject.get("value").getAsString());
						if (actualThresholdValue != expectedThresholdValue) {
							failNotificationTest(insightName,
									"ThresholdAmount is not matching in notifications response expected threshold is = "
											+ thresholdValue + " but found actual = " + actualThresholdValue,
									failureReason);
						}
					}

					JsonObject modelObject = dataEntity.getAsJsonObject("model");
					JsonArray keysArray = modelObject.getAsJsonArray("keys");
					boolean merchantPresent = false;
					StringBuilder actualKeys = new StringBuilder();
					for (int index1 = 0; index1 < keysArray.size(); index1++) {
						if (keysArray.get(index1).getAsString().contains("merchant")
								&& !(insightName.equals("BillNotPaid") || insightName.equals("UpcomingBills")
										|| insightName.equals("UpcomingSubscription"))) {
							System.out.println("MERCHANT Present -->" + keysArray.get(index1).getAsString());
							merchantPresent = true;
						}
						actualKeys.append(keysArray.get(index1).getAsString());
					}

					// charraeck for correct keys expected keys vs actual keys
					StringBuilder expectedKeys = new StringBuilder();
					for (int keys = 0; keys < expKeysArray.size(); keys++) {
						JsonObject entityKeyObject = (JsonObject) expKeysArray.get(keys);
						if (entityKeyObject.keySet().toString().contains(validateKeys)) {
							String[] keyArray = entityKeyObject.get(entityKeyObject.keySet().toString().substring(1,
									entityKeyObject.keySet().toString().length() - 1)).getAsString().split(",");
							for (int keys1 = 1; keys1 < keyArray.length; keys1++) {
								if (merchantPresent && keyArray[keys1 - 1].toString().equals("transaction[0].date")) {
									expectedKeys.append(keyArray[0] + ".transaction[0].merchant.name");// Large Deposit
																										// Notice extra
																										// key will be
																										// appended when
																										// merchant is
																										// present
									expectedKeys.append(keyArray[0] + "." + keyArray[keys1]);
								} else {
									expectedKeys.append(keyArray[0] + "." + keyArray[keys1]);
								}

							}
						}
					}
					int expectedNoOfKeys = numberOfKeys;
					expectedNoOfKeys = (merchantPresent) ? expectedNoOfKeys += 1 : numberOfKeys;
					if (expectedNoOfKeys != keysArray.size()) {
						failNotificationTest(insightName,
								"Number of Keys in the notification are not matching expected = " + expectedNoOfKeys
										+ " but found actual = " + keysArray.size(),
								failureReason);
					}

					// expected and actual keys check
					if (!expectedKeys.toString().equals(actualKeys.toString())) {
						failNotificationTest(
								insightName, "Keys in the notification are not correct expected = "
										+ expectedKeys.toString() + " but found actual = " + actualKeys.toString(),
								failureReason);
						System.err.println("Both(Keys) Are not same");
					}
				}
			}
		}
		return notificationInsightsStatus;
	}

	public void verifyResponse(JsonObject insightObj, String insightName, String triggerType, String dataLevels,
			String entityParameterName, String thresholdNameValueType, String expectedNumberOfInsights,
			FailureReason failureReason, HashMap<String, String> entityIdsMap) {
		notificationInsightsStatus = true;
		JsonArray insightsArray = null;
		int thresholdValue = 0;
		if (!insightObj.get("name").getAsString().equals(insightName))
			failNotificationTest(insightName, "insightName", failureReason);
		if (!insightObj.get("triggerType").getAsString().equals(triggerType))
			failNotificationTest(insightName, "triggerType", failureReason);
		if (!verifyCreatedDateTimeStamp(insightName, insightObj.get("createdDate").getAsString())) {
			// failNotificationTest(insightName, "InValid createdDate either format is not
			// correct OR created date may be greater than current date", failureReason);
		}
		JsonObject dataEntity = insightObj.getAsJsonObject("data");
		JsonObject levelObject = dataEntity.getAsJsonObject("level");
		if (entityParameterName.equals("account") && !insightName.equals("CreditUtilization")) {
			if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
				failNotificationTest(insightName,
						"Account Id = " + levelObject.get("id").getAsString() + " Doesn't Exists", failureReason);
			}
		}
		if (entityParameterName.equals("view")) {
			if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
				failNotificationTest(insightName,
						"View Id = " + levelObject.get("id").getAsString() + " Doesn't Exists", failureReason);
			}
		}
		if (entityParameterName.equals("accountGroup")) {
			if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
				failNotificationTest(insightName,
						"Account Group Id = " + levelObject.get("id").getAsString() + " Doesn't Exists", failureReason);
			}
		}
		if (entityParameterName.equals("goal")) {
			/*
			 * if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
			 * failNotificationTest(insightName,"Goal Id = " +
			 * levelObject.get("id").getAsString() + " Doesn't Exists",failureReason); }
			 */
		}
		String[] applicableEntity = dataLevels.split("\\s+");
		String expectedLeveType = applicableEntity[0];
		if (applicableEntity.length > 1) {
			expectedLeveType = applicableEntity[0];
		}
		if (!levelObject.get("type").getAsString().equals(expectedLeveType))
			failNotificationTest(insightName, "leveltype", failureReason);

		JsonObject thresholdObject = null;
		if (!thresholdNameValueType.isEmpty()) {
			thresholdObject = (JsonObject) (levelObject.getAsJsonArray("threshold")).get(0);
			try {
				thresholdObject.get("name");
				thresholdObject.get("value");
				thresholdObject.get("type");
			} catch (Exception e) {
				failNotificationTest(insightName, "threshold Object keys are missing", failureReason);
			}
			String expectedThresholds = thresholdNameValueType.split("\\s+")[0];
			String[] expectedThresholdValues = expectedThresholds.split(",");
			if (!thresholdObject.get("name").getAsString().equals(expectedThresholdValues[0])
					&& !thresholdObject.get("type").getAsString().equals(expectedThresholdValues[2])) {
				failNotificationTest(insightName, "name OR type in the threshold objects are incorrect", failureReason);
			}
			int expectedThresholdValue = (int) Double.parseDouble(expectedThresholdValues[1]);
			System.out.println("Threshold Value" + expectedThresholdValue);
			thresholdValue = expectedThresholdValue;
			int actualThresholdValue = (int) Double.parseDouble(thresholdObject.get("value").getAsString());
			if (actualThresholdValue != expectedThresholdValue) {
				failNotificationTest(insightName,
						"ThresholdAmount is not matching in notifications response expected threshold is = "
								+ thresholdValue + " but found actual = " + actualThresholdValue,
						failureReason);
			}
		}

	}

	public boolean verifyInsightsResponseForLargeDepositNotice(Response userInsightsResponse, String insightName,
			String triggerType, String dataLevels, String entityParameterName, String thresholdNameValueType,
			String expectedNumberOfInsights, int numberOfKeys, FailureReason failureReason, JsonArray expKeysArray,
			HashMap<String, String> entityIdsMap, String validateKeys, String viewsAndAccountInsightsCount) {
		notificationInsightsStatus = true;
		JsonArray insightsArray = null;
		int thresholdValue = 0;
		if (Integer.parseInt(expectedNumberOfInsights) == 0) {
			boolean insightsNotAvailable = false;
			String insightResponse = userInsightsResponse.asString();
			if (insightResponse.equals("{}")) {
				insightsNotAvailable = true;
			} else {
				JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(insightResponse);
				try {
					insightsArray = notificationResponseObject.getAsJsonArray("notification");
					insightsNotAvailable = (insightsArray.size() == 0) ? true : false;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!insightsNotAvailable) {
				failNotificationTest(insightName, "Expected   " + expectedNumberOfInsights
						+ " Insights but Actual Inssights " + insightsArray.size(), failureReason);
			}

		} else {
			JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
			JsonArray notificationArray = notificationResponseObject.getAsJsonArray("notification");
			int actualInsights = notificationArray.size();
			if (Integer.parseInt(expectedNumberOfInsights) != actualInsights) {
				failNotificationTest(
						insightName, "Expected insights are not generated , Expected = "
								+ Integer.parseInt(expectedNumberOfInsights) + "  Actual = " + actualInsights,
						failureReason);
			} else {
				for (int i = 0; i < notificationArray.size(); i++) {
					JsonObject insightObj = (JsonObject) notificationArray.get(i);
					if (!viewsAndAccountInsightsCount.isEmpty()) {
						int viewsCount = Integer.parseInt(viewsAndAccountInsightsCount.split(",")[0]);
						if (i < viewsCount) {
							dataLevels = "VIEW";
							validateKeys = "View";
							entityParameterName = "view";
						} else {
							dataLevels = "ACCOUNT";
							validateKeys = "Account";
							entityParameterName = "account";
						}
						JsonArray keysArray = insightsGenerics.getInsightKeys(insightName);
						numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
					}
					verifyResponse(insightObj, insightName, triggerType, dataLevels, entityParameterName,
							thresholdNameValueType, expectedNumberOfInsights, failureReason, entityIdsMap);
					JsonObject dataEntity = insightObj.getAsJsonObject("data");
					JsonObject levelObject = dataEntity.getAsJsonObject("level");
					JsonObject modelObject = dataEntity.getAsJsonObject("model");
					JsonArray keysArray = modelObject.getAsJsonArray("keys");
					boolean merchantPresent = false;
					StringBuilder actualKeys = new StringBuilder();
					for (int index1 = 0; index1 < keysArray.size(); index1++) {
						if (keysArray.get(index1).getAsString().contains("merchant")
								&& !insightName.equals("BillNotPaid") && !insightName.equals("LargeDepositNotice")
								&& !insightName.equals("UpcomingBills")
								&& !insightName.equals("ProjectedInsufficientBalance")
								&& !insightName.equals("MostFrequentPurchases")
								&& !insightName.equals("IncomeThreshold")) {
							System.out.println("MERCHANT Present -->" + keysArray.get(index1).getAsString());
							merchantPresent = true;
						}
						actualKeys.append(keysArray.get(index1).getAsString());
					}

					// charraeck for correct keys expected keys vs actual keys
					StringBuilder expectedKeys = new StringBuilder();
					for (int keys = 0; keys < expKeysArray.size(); keys++) {
						JsonObject entityKeyObject = (JsonObject) expKeysArray.get(keys);
						if (entityKeyObject.keySet().toString().contains(validateKeys)) {
							String[] keyArray = entityKeyObject.get(entityKeyObject.keySet().toString().substring(1,
									entityKeyObject.keySet().toString().length() - 1)).getAsString().split(",");
							for (int keys1 = 1; keys1 < keyArray.length; keys1++) {
								if (merchantPresent && keyArray[keys1 - 1].toString().equals("transaction[0].date")) {
									expectedKeys.append(keyArray[0] + ".transaction[0].merchant.name");// Large Deposit
																										// Notice extra
																										// key will be
																										// appended when
																										// merchant is
																										// present
									expectedKeys.append(keyArray[0] + "." + keyArray[keys1]);
								} else {
									expectedKeys.append(keyArray[0] + "." + keyArray[keys1]);
								}
							}
						}
					}
					int expectedNoOfKeys = numberOfKeys;
					expectedNoOfKeys = (merchantPresent) ? expectedNoOfKeys += 1 : numberOfKeys;
					if (expectedNoOfKeys != keysArray.size()) {
						failNotificationTest(insightName,
								"Number of Keys in the notification are not matching expected = " + expectedNoOfKeys
										+ " but found actual = " + keysArray.size(),
								failureReason);
					}

					// expected and actual keys check
					if (!expectedKeys.toString().equals(actualKeys.toString())) {
						failNotificationTest(
								insightName, "Keys in the notification are not correct expected = "
										+ expectedKeys.toString() + " but found actual = " + actualKeys.toString(),
								failureReason);
						System.err.println("Both(Keys) Are not same");
					}
					// validations.append(" Keys for one OR more insights generated ,");
					JsonArray valuesArray = modelObject.getAsJsonArray("values");
					if (insightName.equals("LowBalanceWarning")) {
						int availableBalanceInitialPos = 5, totalKeysValues = 7;
						String actualGenereatedInsightValue = valuesArray.get(availableBalanceInitialPos).toString()
								.substring(1, valuesArray.get(availableBalanceInitialPos).toString().length() - 4);
						if ((int) Double.parseDouble(actualGenereatedInsightValue) >= thresholdValue) {
							failNotificationTest(insightName, "Expected value must be less than " + thresholdValue
									+ "But found actual value " + Integer.parseInt(actualGenereatedInsightValue),
									failureReason);
						}
					}

				}
			}
		}
		return notificationInsightsStatus;
	}

	public boolean verifyBillsSubscriptionResponse(Response userInsightsResponse, String insightName,
			String triggerType, String dataLevels, String entityParameterName, String thresholdNameValueType,
			String expectedNumberOfInsights, int numberOfKeys, FailureReason failureReason, JsonArray expKeysArray,
			HashMap<String, String> entityIdsMap, String validateKeys) {
		notificationInsightsStatus = true;
		JsonArray insightsArray = null;
		int thresholdValue = 0;
		if (Integer.parseInt(expectedNumberOfInsights) == 0) {
			boolean insightsNotAvailable = false;
			String insightResponse = userInsightsResponse.asString();

			if (insightResponse.equals("{}")) {
				insightsNotAvailable = true;
			} else {
				JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(insightResponse);
				try {
					insightsArray = notificationResponseObject.getAsJsonArray("notification");
					insightsNotAvailable = (insightsArray.size() == 0) ? true : false;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!insightsNotAvailable) {
				failNotificationTest(insightName, "Expected   " + expectedNumberOfInsights
						+ " Insights but Actual Inssights " + insightsArray.size(), failureReason);
			}

		} else {
			userInsightsResponse.then().log().all();
			JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
			JsonArray notificationArray = notificationResponseObject.getAsJsonArray("notification");
			int actualInsights = notificationArray.size();
			if (Integer.parseInt(expectedNumberOfInsights) != actualInsights) {
				failNotificationTest(
						insightName, "Expected insights are not generated , Expected = "
								+ Integer.parseInt(expectedNumberOfInsights) + "  Actual = " + actualInsights,
						failureReason);
			} else {
				for (int i = 0; i < notificationArray.size(); i++) {
					JsonObject insightObj = (JsonObject) notificationArray.get(i);
					if (!insightObj.get("name").getAsString().equals(insightName))
						failNotificationTest(insightName, "insightName", failureReason);
					if (!insightObj.get("triggerType").getAsString().equals(triggerType))
						failNotificationTest(insightName, "triggerType", failureReason);
					if (!verifyCreatedDateTimeStamp(insightName, insightObj.get("createdDate").getAsString())) {
						failNotificationTest(insightName,
								"InValid createdDate either format is not correct OR created date may be greater than current date",
								failureReason);

					}
					JsonObject dataEntity = insightObj.getAsJsonObject("data");
					JsonObject levelObject = dataEntity.getAsJsonObject("level");
					if (entityParameterName.equals("account") && !insightName.equals("CreditUtilization")
							&& !insightName.equals("LiabilityChange") && !insightName.equals("NetworthChange")
							&& !insightName.equals("AssetChange")) {
						if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
							failNotificationTest(insightName,
									"Account Id = " + levelObject.get("id").getAsString() + " Doesn't Exists",
									failureReason);
						}
					}
					if (entityParameterName.equals("view")) {
						if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
							failNotificationTest(insightName,
									"View Id = " + levelObject.get("id").getAsString() + " Doesn't Exists",
									failureReason);
						}
					}
					if (entityParameterName.equals("accountGroup")) {
						if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
							failNotificationTest(insightName,
									"Account Group Id = " + levelObject.get("id").getAsString() + " Doesn't Exists",
									failureReason);
						}
					}
					if (entityParameterName.equals("goal")) {
						/*
						 * if (!entityIdsMap.containsValue(levelObject.get("id").getAsString())) {
						 * failNotificationTest(insightName,"Goal Id = " +
						 * levelObject.get("id").getAsString() + " Doesn't Exists",failureReason); }
						 */
					}
					/*
					 * String[] applicableEntity = dataLevels.split("\\s+"); String expectedLeveType
					 * = applicableEntity[0]; if (applicableEntity.length > 1) { expectedLeveType =
					 * applicableEntity[i]; }
					 */
					JsonObject thresholdObject = null;
					if (!thresholdNameValueType.isEmpty()) {
						thresholdObject = (JsonObject) (levelObject.getAsJsonArray("threshold")).get(0);
						try {
							thresholdObject.get("name");
							thresholdObject.get("value");
							thresholdObject.get("type");
						} catch (Exception e) {
							failNotificationTest(insightName, "threshold Object keys are missing", failureReason);
						}
						String expectedThresholds = thresholdNameValueType.split("\\s+")[0];
						String[] expectedThresholdValues = expectedThresholds.split(",");
						if (!thresholdObject.get("name").getAsString().equals(expectedThresholdValues[0])
								&& !thresholdObject.get("type").getAsString().equals(expectedThresholdValues[2])) {
							failNotificationTest(insightName, "name OR type in the threshold objects are incorrect",
									failureReason);
						}
						int expectedThresholdValue = (int) Double.parseDouble(expectedThresholdValues[1]);
						System.out.println("Threshold Value" + expectedThresholdValue);
						thresholdValue = expectedThresholdValue;
						int actualThresholdValue = (int) Double.parseDouble(thresholdObject.get("value").getAsString());
						if (actualThresholdValue != expectedThresholdValue) {
							failNotificationTest(insightName,
									"ThresholdAmount is not matching in notifications response expected threshold is = "
											+ thresholdValue + " but found actual = " + actualThresholdValue,
									failureReason);
						}
					}

					JsonObject modelObject = dataEntity.getAsJsonObject("model");
					JsonArray keysArray = modelObject.getAsJsonArray("keys");
					boolean merchantPresent = false;
					StringBuilder actualKeys = new StringBuilder();
					for (int index1 = 0; index1 < keysArray.size(); index1++) {
						if (keysArray.get(index1).getAsString().contains("merchant")
								&& !insightName.equals("BillNotPaid") && !insightName.equals("BillPaid")
								&& !insightName.equals("SalaryChanged") && !insightName.equals("SubscriptionPaid")
								&& !insightName.equals("LargeDepositNotice")
								&& !insightName.equals("SalaryDeposited")) {
							merchantPresent = true;
						}
						actualKeys.append(keysArray.get(index1).getAsString());
					}

					// charraeck for correct keys expected keys vs actual keys
					StringBuilder expectedKeys = new StringBuilder();
					for (int keys = 0; keys < expKeysArray.size(); keys++) {
						JsonObject entityKeyObject = (JsonObject) expKeysArray.get(keys);
						if (entityKeyObject.keySet().toString().contains(validateKeys)) {
							String[] keyArray = entityKeyObject.get(entityKeyObject.keySet().toString().substring(1,
									entityKeyObject.keySet().toString().length() - 1)).getAsString().split(",");
							for (int keys1 = 1; keys1 < keyArray.length; keys1++) {
								if (merchantPresent && keyArray[keys1 - 1].toString().equals("transaction[0].date")) {
									expectedKeys.append(keyArray[0] + ".transaction[0].merchant.name");
									expectedKeys.append(keyArray[0] + "." + keyArray[keys1]);
								} else {
									expectedKeys.append(keyArray[0] + "." + keyArray[keys1]);
								}

							}
						}
					}
					int expectedNoOfKeys = numberOfKeys;
					expectedNoOfKeys = (merchantPresent) ? expectedNoOfKeys += 1 : numberOfKeys;

					if (expectedNoOfKeys != keysArray.size()) {

						failNotificationTest(insightName,
								"Number of Keys in the notification are not matching expected = " + expectedNoOfKeys
										+ " but found actual = " + keysArray.size(),
								failureReason);

					}
					if (!expectedKeys.toString().equals(actualKeys.toString())) {
						failNotificationTest(
								insightName, "Keys in the notification are not correct expected = "
										+ expectedKeys.toString() + " but found actual = " + actualKeys.toString(),
								failureReason);
						System.err.println("Both(Keys) Are not same");
					}
					JsonArray valuesArray = modelObject.getAsJsonArray("values");
					if (insightName.equals("LowBalanceWarning")) {
						int availableBalanceInitialPos = 5, totalKeysValues = 7;
						String actualGenereatedInsightValue = valuesArray.get(availableBalanceInitialPos).toString()
								.substring(1, valuesArray.get(availableBalanceInitialPos).toString().length() - 4);
						if ((int) Double.parseDouble(actualGenereatedInsightValue) >= thresholdValue) {
							failNotificationTest(insightName, "Expected value must be less than " + thresholdValue
									+ "But found actual value " + Integer.parseInt(actualGenereatedInsightValue),
									failureReason);
						}
					}

				}
			}
		}
		return notificationInsightsStatus;
	}

	public boolean verifyBillsSubscriptionResponseV2(Response userInsightsResponse, String insightName,
			String triggerType, String dataLevels, String entityParameterName, String thresholdNameValueType,
			String expectedNumberOfInsights, int numberOfKeys, FailureReason failureReason, JsonArray expKeysArray,
			HashMap<String, String> entityIdsMap, String validateKeys, String accountSchemaDefinitionFile,
			String viewSchemaFile) {
		notificationInsightsStatus = true;
		JsonArray insightsArray = null;
		int thresholdValue = 0;
		if (Integer.parseInt(expectedNumberOfInsights) == 0) {
			boolean insightsNotAvailable = false;
			String insightResponse = userInsightsResponse.asString();

			if (insightResponse.equals("{}")) {
				insightsNotAvailable = true;
			} else {
				JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(insightResponse);
				try {
					insightsArray = notificationResponseObject.getAsJsonArray("feed");
					insightsNotAvailable = (insightsArray.size() == 0) ? true : false;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!insightsNotAvailable) {
				failNotificationTest(insightName, "Expected   " + expectedNumberOfInsights
						+ " Insights but Actual Inssights " + insightsArray.size(), failureReason);
			}

		} else {
			userInsightsResponse.then().log().all();
			JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
			JsonArray notificationArray = notificationResponseObject.getAsJsonArray("feed");
			int actualInsights = notificationArray.size();
			if (Integer.parseInt(expectedNumberOfInsights) != actualInsights) {
				failNotificationTest(
						insightName, "Expected insights are not generated , Expected = "
								+ Integer.parseInt(expectedNumberOfInsights) + "  Actual = " + actualInsights,
						failureReason);
			} else {
				for (int i = 0; i < notificationArray.size(); i++) {
					JsonObject insightObj = (JsonObject) notificationArray.get(i);
					if (!insightObj.get("insightName").getAsString().equals(insightName))
						failNotificationTest(insightName, "insightName", failureReason);
					if (!insightObj.get("triggerType").getAsString().equals(triggerType))
						failNotificationTest(insightName, "triggerType", failureReason);
					if (!verifyCreatedDateTimeStamp(insightName, insightObj.get("createdDate").getAsString())) {
						failNotificationTest(insightName,
								"InValid createdDate either format is not correct OR created date may be greater than current date",
								failureReason);

					}
					String entityType = ((JsonObject) (((JsonArray) (insightObj.getAsJsonArray("subscription")))
							.get(0))).get("entityType").getAsString();
					String schemaFile = entityType.equalsIgnoreCase("Account") ? accountSchemaDefinitionFile
							: viewSchemaFile;
					boolean validateSchema = true;
					JSONObject jsonSchema = null;
					try {
						jsonSchema = new JSONObject(new JSONTokener(new FileReader(System.getProperty("user.dir")
										+ "/src/test/resources/TestData" + File.separator + "CSVFiles" + File.separator
										+ "Insights" + File.separator + "FeedsSchema" + File.separator + schemaFile)));
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JSONObject jsonSubject = new JSONObject(new JSONTokener(insightObj.toString()));
					Schema schema = SchemaLoader.load(jsonSchema);
					try {
						schema.validate(jsonSubject);
					} catch (ValidationException e) {
						// e.printStackTrace();
						failureReason.setFailureReason(e.getMessage());
						validateSchema = false;
					}

					if (!validateSchema) {
						failNotificationTest(insightName, "Schema validation failed", failureReason);
						break;
					}
				}
			}
		}
		return notificationInsightsStatus;

	}

	public boolean verifyResponseForTriggerCheckTests(Response userInsightsResponse, String expectedNumberOfInsights,
			String insightName, FailureReason failureReason) {
		notificationInsightsStatus = true;
		JsonArray insightsArray = null;
		int thresholdValue = 0;
		JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
		JsonArray notificationArray = notificationResponseObject.getAsJsonArray("notification");
		int actualInsights = notificationArray.size();
		if (Integer.parseInt(expectedNumberOfInsights) != actualInsights) {
			failNotificationTest(insightName,
					"Expected insights are not generated Trigger check Failed, Expected = "
							+ Integer.parseInt(expectedNumberOfInsights) + "  Actual = " + actualInsights,
					failureReason);
		}
		return notificationInsightsStatus;
	}

}
