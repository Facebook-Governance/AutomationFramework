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
package com.yodlee.insights.subscriptions.pojo.v2;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class Entity {

	private ArrayList<EntityDetail> account ;
	private ArrayList<EntityDetail> view ;
	private ArrayList<EntityDetail> goal;
	private ArrayList<EntityDetail> accountGroup ;

	public ArrayList<EntityDetail> getAccount() {
		return account;
	}

	public void setAccount(ArrayList<EntityDetail> account) {
		this.account = account;
	}

	public ArrayList<EntityDetail> getView() {
		return view;
	}

	public void setView(ArrayList<EntityDetail> view) {
		this.view = view;
	}

	public ArrayList<EntityDetail> getGoal() {
		return goal;
	}

	public void setGoal(ArrayList<EntityDetail> goal) {
		this.goal = goal;
	}

	public ArrayList<EntityDetail> getAccountGroup() {
		return accountGroup;
	}

	public void setAccountGroup(ArrayList<EntityDetail> accountGroup) {
		this.accountGroup = accountGroup;
	}
	
	 @Override
	    public String toString() {
	        return new ToStringBuilder(this).append("account", account).append("view", view).append("accountGroup", accountGroup).append("goal", goal).toString();
	    }
	 
	 @Override
	    public int hashCode() {
	        return new HashCodeBuilder().append(accountGroup).append(goal).append(account).append(view).toHashCode();
	    }

	    /**
	     * Override equals.
	     *
	     * @param other the other
	     * @return true, if successful
	     */
	    @Override
	    public boolean equals(Object other) {
	        if (other == this) {
	            return true;
	        }
	        if ((other instanceof Entity) == false) {
	            return false;
	        }
	        Entity entityParameter = ((Entity) other);
	        return new EqualsBuilder().append(accountGroup, entityParameter.accountGroup).append(goal, entityParameter.goal).append(account, entityParameter.account).append(view, entityParameter.view).isEquals();
	    }


}
