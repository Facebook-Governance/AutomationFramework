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
package com.yodlee.insights.views.pojo;

import java.util.List;

public class InvokerPayload {
	
	private Long memId;
	private Long cobrandId;
	private String type="REFRESH";	
	private List<Payload> payload;	
	private RuntimeParams runtimeParameters = null;
	private String executionConfiguration = null;
	private String securityToken=null;
	public Long getMemId() {
		return memId;
	}
	public void setMemId(Long memId) {
		this.memId = memId;
	}
	public Long getCobrandId() {
		return cobrandId;
	}
	public void setCobrandId(Long cobrandId) {
		this.cobrandId = cobrandId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Payload> getPayload() {
		return payload;
	}
	public void setPayload(List<Payload> payload) {
		this.payload = payload;
	}
	public RuntimeParams getRuntimeParameters() {
		return runtimeParameters;
	}
	public void setRuntimeParameters(RuntimeParams runtimeParameters) {
		this.runtimeParameters = runtimeParameters;
	}
	public String getExecutionConfiguration() {
		return executionConfiguration;
	}
	public void setExecutionConfiguration(String executionConfiguration) {
		this.executionConfiguration = executionConfiguration;
	}
	public String getSecurityToken() {
		return securityToken;
	}
	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}
	

}
