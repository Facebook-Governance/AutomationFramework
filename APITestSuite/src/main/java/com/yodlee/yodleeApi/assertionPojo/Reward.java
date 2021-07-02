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
public class Reward extends Account{

	
	@JsonProperty("classification")
	private String classification;
	
	@JsonProperty("classification")
	public String getClassification() {
		return classification;
	}

	@JsonProperty("classification")
	public void setClassification(String classification) {
		this.classification = classification;
	}
	
	@JsonProperty("enrollmentDate")
	private Date enrollmentDate ;

	@JsonProperty("enrollmentDate")
	public Date getEnrollmentDate() {
		return enrollmentDate;
	}

	@JsonProperty("enrollmentDate")	
	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	@JsonProperty("primaryRewardUnit")
	private String primaryRewardUnit ;
	
	@JsonProperty("primaryRewardUnit")	
	public String getPrimaryRewardUnit() {
		return primaryRewardUnit;
	}

	@JsonProperty("primaryRewardUnit")	
	public void setPrimaryRewardUnit(String primaryRewardUnit) {
		this.primaryRewardUnit = primaryRewardUnit;
	}
	
	@JsonProperty("rewardBalance")
	private RewardBalance[] rewardBalance;

	@JsonProperty("rewardBalance")	
	public RewardBalance[] getRewardBalance() {
		return rewardBalance;
	}

	@JsonProperty("rewardBalance")	
	public void setRewardBalance(RewardBalance[] rewardBalance) {
		this.rewardBalance = rewardBalance;
	}
	
	@JsonProperty("currentLevel")
	private String currentLevel ;
	
	@JsonProperty("currentLevel")	
	public String getCurrentLevel() {
		return currentLevel;
	}

	@JsonProperty("currentLevel")	
	public void setCurrentLevel(String currentLevel) {
		this.currentLevel = currentLevel;
	}
	

	@JsonProperty("nextLevel")
	private String nextLevel ;

	@JsonProperty("nextLevel")
	public String getNextLevel() {
		return nextLevel;
	}

	@JsonProperty("nextLevel")
	public void setNextLevel(String nextLevel) {
		this.nextLevel = nextLevel;
	}
	
	@JsonProperty("holderProfile")
	private HolderProfile[] holderProfile;

	@JsonProperty("holderProfile")
	public HolderProfile[] getHolderProfile() {
		return holderProfile;
	}

	@JsonProperty("holderProfile")
	public void setHolderProfile(HolderProfile[] holderProfile) {
		this.holderProfile = holderProfile;
	}

}
