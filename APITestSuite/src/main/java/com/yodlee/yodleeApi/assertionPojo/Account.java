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

package com.yodlee.yodleeApi.assertionPojo;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
public class Account {

	@JsonProperty("accountName")
	private String accountName ;
	
	@JsonProperty("accountName")
	public String getAccountName() {
		return accountName;
	}

	@JsonProperty("accountName")
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@JsonProperty("accountNumber")
	private String accountNumber ;
	
	@JsonProperty("accountNumber")
	public String getAccountNumber() {
		return accountNumber;
	}

	@JsonProperty("accountNumber")
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	@JsonProperty("aggregationSource")
	private String aggregationSource;
	
	@JsonProperty("aggregationSource")
	public String getAggregationSource() {
		return aggregationSource;
	}

	@JsonProperty("aggregationSource")
	public void setAggregationSource(String aggregationSource) {
		this.aggregationSource = aggregationSource;
	}
	
	@JsonProperty("isAsset")
	private Boolean isAsset ;
	
	@JsonProperty("isAsset")
	public Boolean getIsAsset() {
		return isAsset;
	}

	@JsonProperty("isAsset")
	public void setIsAsset(Boolean isAsset) {
		this.isAsset = isAsset;
	}
	

	@JsonProperty("CONTAINER")
	private String container ;
	
	
	@JsonProperty("CONTAINER")
	public String getContainer() {
		return container;
	}

	@JsonProperty("CONTAINER")
	public void setContainer(String container) {
		this.container = container;
	}
	

	@JsonProperty("lastUpdated")
	private Date lastUpdated ;
	
	@JsonProperty("lastUpdated")
	public Date getLastUpdated() {
		return lastUpdated;
	}

	@JsonProperty("lastUpdated")
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@JsonProperty("isManual")
	private Boolean isManual ;
	
	
	@JsonProperty("isManual")
	public Boolean getIsManual() {
		return isManual;
	}

	@JsonProperty("isManual")
	public void setIsManual(Boolean isManual) {
		this.isManual = isManual;
	}
	
	@JsonProperty("nickname")
	private String nickname;
	
	@JsonProperty("nickname")
	public String getNickname() {
		return nickname;
	}

	@JsonProperty("nickname")
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	

	@JsonProperty("accountStatus")
	private String accountStatus ;
	
	@JsonProperty("accountStatus")
	public String getAccountStatus() {
		return accountStatus;
	}

	@JsonProperty("accountStatus")
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	@JsonProperty("accountType")
	private String accountType ;
	
	@JsonProperty("accountType")
	public String getAccountType() {
		return accountType;
	}

	@JsonProperty("accountType")
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	
		@JsonProperty("providerId")
		private String providerId ;

		@JsonProperty("providerId")
		public String getProviderId() {
			return providerId;
		}

		@JsonProperty("providerId")
		public void setProviderId(String providerId) {
			this.providerId = providerId;
		}
		
		@JsonProperty("providerName")
		private String providerName ;
		
		@JsonProperty("providerName")
		public String getProviderName() {
			return providerName;
		}

		@JsonProperty("providerName")
		public void setProviderName(String providerName) {
			this.providerName = providerName;
		}
		
		@JsonProperty("providerAccountId")
		private Long providerAccountId ;
		
		@JsonProperty("providerAccountId")
		public Long getProviderAccountId() {
			return providerAccountId;
		}

		@JsonProperty("providerAccountId")
		public void setProviderAccountId(Long providerAccountId) {
			this.providerAccountId = providerAccountId;
		}
		
		
		
		@JsonProperty("createdDate")
		private Date createdDate ;
		
		@JsonProperty("createdDate")
		public Date getCreatedDate() {
			return createdDate;
		}

		@JsonProperty("createdDate")
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}
		
	
		
		@JsonProperty("refreshinfo")
		private RefreshInfo refreshInfo;

		@JsonProperty("refreshinfo")
		public RefreshInfo getRefreshInfo() {
			return refreshInfo;
		}

		@JsonProperty("refreshinfo")
		public void setRefreshInfo(RefreshInfo refreshInfo) {
			this.refreshInfo = refreshInfo;
		}
		
		@JsonProperty("id")
		private String id;

		@JsonProperty("id")
		public String getId() {
			return id;
		}

		@JsonProperty("id")
		public void setId(String id) {
			this.id = id;
		}
		
		
}
