/*
* Copyright (c) 2020 Yodlee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of Yodlee, Inc.
* Use is subject to license terms.
*
*/
package com.yodlee.insights.yodleeApi.utils.v2;


public enum PropertiesEnum {

	SPRING_DATA_MONGO_DB_USERNAME("spring.data.mongodb.username"),
	SPRING_DATA_MONGO_DB_DATABASE("spring.data.mongodb.database"),
	SPRING_DATA_MONGO_DB_PASSWORD("spring.data.mongodb.password"),
	SPRING_DATA_MONGO_DB_REPLICA_SET("spring.data.mongodb.replica.set"),
	YODLEE_KEYSTORE_FILE_NAME("com.yodlee.keystore.fileName"), 
	YODLEE_KEYSTORE_PASSWORD("com.yodlee.keystore.password"),
	DEVELOPER_SERVICE_SERVER_PORT("developerservice.server.port"),
	INVOKER_SERVICE_SERVER_PORT("invokerservice.server.port"),
	SMART_INSIGHTS_SERVICE_SERVER_PORT("smartinsightservice.server.port"),
	INSIGHTS_COMPONENT_NAME("com.yodlee.insights.component.name"),
	SPRING_APPLICATION_JSON("spring.application.json"),
	INSIGHTS_ENGINE_SMART_SERVICE("smartinsights"),
	INSIGHTS_ENGINE_DEVELOPER_SERVICE("insightsdeveloper"),
	INSIGHTS_ENGINE_INVOKER_SERVICE("insightsinvoker"),
	
	LOGGING_PATH("logging.path");

	private String key;

	PropertiesEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
