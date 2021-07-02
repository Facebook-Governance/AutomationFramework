/*******************************************************************************
 *
 * * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/

package com.yodlee.yodleeApi.helper;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static net.javacrumbs.jsonunit.JsonAssert.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import com.yodlee.yodleeApi.constants.HttpStatus;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;
import net.javacrumbs.jsonunit.core.Option;;

/**
 * @author Soujanya Kokala
 *
 */
public class PojoAssertionUtil {

	/**
	 * This method will replace null values with json-unit-ignore in the given
	 * JSONArray. Internally used in replaceNullValuesWithUnitIgnore()
	 * 
	 * @param jsonArray
	 *            - JSONArray
	 * @return
	 * @throws JSONException
	 */
	private List toList(JSONArray jsonArray) throws JSONException {
		Object value = null;
		if (jsonArray == null) {
			return null;
		}
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {

			if (jsonArray.get(i) instanceof JSONObject) {
				JSONObject jSONObject = jsonArray.getJSONObject(i);
				Map<String, Object> hashMap = new HashMap();
				Iterator keys = jSONObject.keys();
				while (keys.hasNext()) {
					String key1 = (String) keys.next();
					try {
						value = jSONObject.get(key1);
						if (value == null || String.valueOf(value).equalsIgnoreCase("null")
								|| value.toString().equalsIgnoreCase("null")) {
							value = "${json-unit.ignore}";

						} else if (jSONObject.get(key1) instanceof JSONObject) {
							value = toMap(jSONObject.getJSONObject(key1));

						} else if (jSONObject.get(key1) instanceof JSONArray) {
							value = toList(jSONObject.getJSONArray(key1));
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
					hashMap.put(key1, value);
				}
				list.add(hashMap);
			}

		}
		return list;

	}

	/**
	 * This method will replace null values with json-unit-ignore in the given
	 * JSONObject. Internally used in the method replaceNullValuesWithUnitIgnore()
	 * 
	 * @param jsonObject
	 *            - JSONObject
	 * @return
	 */
	private Map<String, Object> toMap(JSONObject jsonObject) {
		Object value = null;
		if (jsonObject == null) {
			return null;
		}
		Map<String, Object> hashMap = new HashMap();
		Iterator keys = jsonObject.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			try {
				value = jsonObject.get(key);
				if (value == null || value.toString().equalsIgnoreCase("null")
						|| String.valueOf(value).equalsIgnoreCase("null")) {
					value = "${json-unit.ignore}";
				} else {
					if (value instanceof JSONObject) {
						value=toMap(jsonObject.getJSONObject(key));
					} else if (value instanceof JSONArray) {
						value=toList(jsonObject.getJSONArray(key));
					}
				}

			} catch (JSONException e) {
			}
			hashMap.put(key, value);
		}
		return hashMap;
	}

	/**
	 * @param pojo
	 * @return
	 * @throws JsonProcessingException
	 */
	public JSONObject getJsonObjectFromPojo(Object pojo) throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonStr = ow.writeValueAsString(pojo);
		JSONObject jsonObj = new JSONObject(jsonStr);

		return jsonObj;
	}

	/**
	 * This method is to replace null values with Json-unit-ignore
	 * 
	 * @param jsonObj
	 *            - JSONObject
	 * @return
	 */
	public JSONObject replaceNullValuesWithUnitIgnore(JSONObject jsonObj) {
		Iterator<?> keys = jsonObj.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			if (jsonObj.get(key) instanceof JSONObject) {
				Map mapObj = toMap(jsonObj.getJSONObject(key));
				jsonObj.put(key, mapObj);

			} else if (jsonObj.get(key) instanceof JSONArray) {
				List list = toList(jsonObj.getJSONArray(key));
				jsonObj.put(key, new JSONArray(list));
			} else if (jsonObj.get(key) == null || jsonObj.get(key).toString().equalsIgnoreCase("null")) {
				jsonObj.put(key, "${json-unit.ignore}");
			}
		}
		return jsonObj;
	}

	public Object loadJsonObject2Pojo(JSONObject jsonObject,Object pojoObject) {
		
		ObjectMapper mapper = new ObjectMapper().registerModule(new JsonOrgModule());
		pojoObject = mapper.convertValue(jsonObject,pojoObject.getClass());
		
		return pojoObject;
	
	}
	
	
	

	/**
	 * This method is to load json file into the given POJO Object.
	 * 
	 * @param jsonFilePath
	 *            -This should have filepath and filename
	 * @param pojoObject
	 *            -This is the POJO object, into which we want to load our jsonFile.
	 * @return This is the json mapped POJO object.
	 * @throws IOException
	 */
	public Object loadJsonToPojo(String filePath, String resFile, Object pojoObject) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		CommonUtils commonUtils = new CommonUtils();
		String jsonFilePath = commonUtils.getPath(filePath + File.separator + resFile);
		pojoObject = mapper.readValue(new File(jsonFilePath), pojoObject.getClass());
		return pojoObject;
	}

	/**
	 * This method is to assert POJO object with the API Response.
	 * 
	 * @param pojoObject
	 *            - POJO object with which we want to assert our response. This
	 *            should not be an empty POJO object. Before calling this method we
	 *            need to load our json file into this POJO.
	 * @param response
	 *            - Response object should have the API response.
	 * @throws JsonProcessingException
	 */
	public void assertResponseWithJsonMappedPojo(Object pojoObject, Response response) throws JsonProcessingException {

		PojoAssertionUtil pojoAssertionUtil = new PojoAssertionUtil();
		JSONObject jsonObj = null;

		jsonObj = getJsonObjectFromPojo(pojoObject);

		JSONObject responseJson = new JSONObject(response.asString());

		System.out.println("responseJson:::" + responseJson);

		pojoAssertionUtil.replaceNullValuesWithUnitIgnore(jsonObj);

		System.out.println("PojoAssertionHelper.assertResponseWithPojo()::jsonObj:::" + jsonObj);
		System.out.println("PojoAssertionHelper.assertResponseWithPojo()::response.asString()::" + response.asString());

		assertJsonEquals(jsonObj, responseJson, when(Option.IGNORING_ARRAY_ORDER));

	}

	

	/**
	 * This method will create and POJO object based on entityName passed and loads
	 * json from the json file into the POJO object. Asserts response with the
	 * mapped POJO object. If Response code is 204 it will assert the status code
	 * else asserts with the input json. If the json file not exists just creates
	 * the json with the response.
	 * 
	 * @param statusCode
	 *            - expected status code
	 * @param filePath
	 *            - Path of the JSON file
	 * @param fileName
	 *            - json file name
	 * @param response
	 *            - Response object should have complete API response.
	 * @param entityName
	 *            -entity should be like cobrand, user, account etc. This will be
	 *            used to map Response with the appropriate POJO object
	 * @throws IOException
	 */
	public void assertResponse(int statusCode, String filePath, String fileName, Response response,
			Object mappedPojoObject, String exclusionKeys) throws IOException {
		CommonUtils commonUtils = new CommonUtils();
		if (response.getStatusCode() == HttpStatus.NO_CONTENT_204) {
			commonUtils.assertStatusCode(response, statusCode);
		} else {
			if (!commonUtils.saveJson(response.prettyPrint(), filePath, fileName)) {
				System.out.println("filePath + fileName:::" + filePath + fileName);

				assertResponseWithPojo(response, mappedPojoObject, exclusionKeys);

			} else
				System.out.println("Response files are newly created");
		}
	}
	
	public void assertResponseWithPojo(Response getAccountsResponse, JSONObject jsonObj, String exclusionKeys)
			throws JsonProcessingException {
		PojoAssertionUtil pojoAssertionUtil = new PojoAssertionUtil();
		System.out.println("PojoAssertionUtil:assertResponseWithPojo:jsonObj before deleting exclusion Keys:::"+jsonObj);
		System.out.println("PojoAssertionUtil:assertResponseWithPojo:exclusionKeys:::: "+exclusionKeys);
		jsonObj = pojoAssertionUtil.deleteExclusionKeys(jsonObj, exclusionKeys);
		
		System.out.println("PojoAssertionUtil:assertResponseWithPojo:jsonObj before unitIgnore:::"+jsonObj);
		jsonObj = replaceNullValuesWithUnitIgnore(jsonObj);
		System.out.println("PojoAssertionUtil:assertResponseWithPojo:jsonObj after UnitIgnore:::"+jsonObj);
		
		JSONObject responseJson = new JSONObject(getAccountsResponse.asString());
		System.out.println("PojoAssertionUtil:assertResponseWithPojo:responseJson::"+responseJson);
		
		
		assertJsonEquals(jsonObj, responseJson, when(Option.IGNORING_ARRAY_ORDER));
	}

	public void assertResponseWithPojo(Response getAccountsResponse, Object getAccountsPojo, String exclusionKeys)
			throws JsonProcessingException {
		PojoAssertionUtil pojoAssertionUtil = new PojoAssertionUtil();
		
		JSONObject jsonObj = pojoAssertionUtil.getJsonObjectFromPojo(getAccountsPojo);
		
		System.out.println("PojoAssertionUtil:assertResponseWithPojo:jsonObj before deleting exclusion Keys:::"+jsonObj);
		System.out.println("PojoAssertionUtil:assertResponseWithPojo:exclusionKeys:::: "+exclusionKeys);
		jsonObj = pojoAssertionUtil.deleteExclusionKeys(jsonObj, exclusionKeys);
		
		System.out.println("PojoAssertionUtil:assertResponseWithPojo:jsonObj before unitIgnore:::"+jsonObj);
		jsonObj = replaceNullValuesWithUnitIgnore(jsonObj);
		System.out.println("PojoAssertionUtil:assertResponseWithPojo:jsonObj after UnitIgnore:::"+jsonObj);
		
		JSONObject responseJson = new JSONObject(getAccountsResponse.asString());
		System.out.println("PojoAssertionUtil:assertResponseWithPojo:responseJson::"+responseJson);
		
		
		assertJsonEquals(jsonObj, responseJson, when(Option.IGNORING_ARRAY_ORDER));
	}

	
	/**
	 * This method is used to delete keys from the jsonObject. This method is
	 * internally used by the method loadJsonToPojo(String jsonFilePath, Object
	 * pojoObject,String exclusionKeys)
	 * 
	 * @param jsonObj
	 *            - JSONObject
	 * @param exclusionKeys
	 *            - common seperated list of keys those needs to be excluded from
	 *            the pojo.
	 */

	public JSONObject deleteExclusionKeys(JSONObject jsonObj, String exclusionKeys) {

		if (exclusionKeys != null && !exclusionKeys.isEmpty()) {
			String[] ignoreKeys = exclusionKeys.split(",");
			List<String> compositeKeys = new ArrayList<>();
			for (int i = 0; i < ignoreKeys.length; i++) {
				if (ignoreKeys[i].contains(".")) {
					compositeKeys.add(ignoreKeys[i]);
				}
			}
			System.out.println("Composite Keys::" + compositeKeys);

			deleteKeysFromJson(jsonObj, ignoreKeys);

			deleteCompositeKeys(jsonObj, compositeKeys);

		}
		return jsonObj;
	}
	
	private void deleteKeysFromJson(JSONObject jsonObj, String[] ignoreKeys) {
		JSONArray keys = jsonObj.names();
		for (int i = 0; i < keys.length(); i++) {
			String key = keys.getString(i);
			if (isIgnorableKey(ignoreKeys, key)) {
				jsonObj.remove(key);
			} else if (jsonObj.get(key) instanceof JSONArray) {
				JSONArray jsonArr = jsonObj.getJSONArray(key);
				deleteKeysFromJsonArray(jsonArr, ignoreKeys);
			} else if (jsonObj.get(key) instanceof JSONObject) {
				JSONObject jsonObj1 = jsonObj.getJSONObject(key);
				deleteKeysFromJson(jsonObj1, ignoreKeys);
			}
		}
	}
	
	/**
	 * This method is used to delete keys from the jsonObject. This method is
	 * internally used by the method loadJsonToPojo(String jsonFilePath, Object
	 * pojoObject,String exclusionKeys)
	 * 
	 * @param jsonObj
	 *            - JSONObject
	 * @param exclusionKeys
	 *            - common seperated list of keys those needs to be excluded from
	 *            the pojo.
	 */

	private void deleteKeysFromJsonArray(JSONArray jsonArray, String[] ignoreKeys) {
		for (int i = 0; i < jsonArray.length(); i++) {
			if (jsonArray.get(i) instanceof JSONObject) {
				deleteKeysFromJson(jsonArray.getJSONObject(i), ignoreKeys);
			}
		}
	}
	
	private void deleteCompositeKeys(JSONObject jsonObj, List<String> compositeKeys) {

		for (String str : compositeKeys) {

			System.out.println("Entered first forloop deleteCompositeKeys:::" + str);

			String[] strArray = str.split("\\.");

			String parentKey = strArray[0];
			String childKey = strArray[1];

			System.out.println("parentKey::::" + parentKey + "::::childKey::::" + childKey);

			deleteChildKeyOfParentFromJson(jsonObj, parentKey, childKey);
		}

	}
	
	private void deleteChildKeyOfParentFromJson(JSONObject jsonObj, String parentKey, String childKey) {


		JSONArray keys = jsonObj.names();

		System.out.println("Keys.lenth::" + keys.length());
		
		
		for (int index = 0; index < keys.length(); index++) {

			
			String key = keys.getString(index);
			
			System.out.println("Parentkey1111:::"+key);
			System.out.println("parentKey:::"+parentKey);
			
			if (key.equals(parentKey)) {
				System.out.println("Entered first IF");
				if (jsonObj.get(key) instanceof JSONObject) {
					System.out.println("Entered Second IF");
					JSONArray tempKeys = jsonObj.getJSONObject(key).names();

					for (int index1 = 0; index1 < tempKeys.length(); index1++) {

						String key1 = tempKeys.getString(index1);
						System.out.println("after second if key1::::"+key1);
						System.out.println("after second if childKey::::"+childKey);
						if (childKey.equals(key1)) {
							System.out.println("jsonObject before removing::"+jsonObj.getJSONObject(key));
							System.out.println("removing key1 from jsonObj::"+key1);
							jsonObj.getJSONObject(key).remove(key1);
							System.out.println("jsonObject after removing::"+jsonObj.getJSONObject(key));
							//break;
						}
					}

				} else if (jsonObj.get(key) instanceof JSONArray) {
					System.out.println("Entered Second ELSEIF");
					JSONArray tempJsonArr = jsonObj.getJSONArray(key);

					for (int i = 0; i < tempJsonArr.length(); i++) {
						JSONObject tempJsonObj = tempJsonArr.getJSONObject(i);

						JSONArray tempKeys1 = tempJsonObj.names();

						for (int index1 = 0; index1 < tempKeys1.length(); index1++) {
							
							String key1 = tempKeys1.getString(index1);
							
							System.out.println("childKey111:::"+key1);
							System.out.println("childKey:::"+childKey);
							if (childKey.equals(key1)) {
								System.out.println("Removing");
								tempJsonObj.remove(key1);
							}
						}
					}

				}
			} else {
				System.out.println("Entered else 111111111111:::key:::"+key);
				if (jsonObj.get(key) instanceof JSONObject) {
					System.out.println("Entered if inside else1111111111");
					deleteChildKeyOfParentFromJson(jsonObj.getJSONObject(key), parentKey, childKey);
				} else if (jsonObj.get(key) instanceof JSONArray) {
					System.out.println("Entered elseIf inside else2222222222");
					JSONArray tempJsonArr = jsonObj.getJSONArray(key);
					for (int i = 0; i < tempJsonArr.length(); i++) {
						JSONObject tempJsonObj = tempJsonArr.getJSONObject(i);
						deleteChildKeyOfParentFromJson(tempJsonObj, parentKey, childKey);
					}
				}

			}
		}

	
	}
	
	
	
	
	private boolean isIgnorableKey(String[] ignoreKeys, String key) {

		for (String str : ignoreKeys) {
			if (str.trim().equals(key)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method will will map JSON file to the POJO object. Also deletes those
	 * keys which needs to be excluded from the pojo.
	 * 
	 * @param jsonFilePath
	 *            - Complete path of the json file including filename.
	 * @param pojoObject
	 *            - POJO class to be mapped with the json.
	 * @param exceptionKey
	 *            - comma seperated keys to be excluded from the pojo
	 * @return
	 * @throws IOException
	 */
	public Object loadJsonToPojo(String jsonFilePath, Object pojoObject, String exclusionKeys) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		pojoObject = mapper.readValue(new File(jsonFilePath), pojoObject.getClass());

		return pojoObject;
	}

}
