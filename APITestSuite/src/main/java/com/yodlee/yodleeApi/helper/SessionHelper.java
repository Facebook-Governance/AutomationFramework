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

import org.jose4j.lang.JoseException;

import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;

public class SessionHelper {
	
	private static Configuration configurationObj =Configuration.getInstance();
	public static String getSessionToken(String cobSessionType,String type){
		String session="";
		switch (cobSessionType){
		case "VALID":
		if(type.equals("cob")) {
			session = configurationObj.getCobrandSessionObj().getCobSession();
			System.out.println("Session is = "+configurationObj.getCobrandSessionObj().getCobSession());
		}
		else if(type.equals("user")) {
			session = configurationObj.getCobrandSessionObj().getUserSession();
		}
			break;
		case "NULL":
			session = null;
			break;
		case "INVALID":
			session = "INVALID";
			break;
		case "EXPIRED":
			if (type.equals("cob")) {
				session = "06142010_1:3d7738e100137f488aede4338a76315125c7670da91fc43016ccfe16fc6ce718a13eefe8b6f22b0e16fbe7e1965578361f86693c7610c1cee9faf55346a2ed54";
			} else if (type.equals("user")) {
				session = "06142010_0:604a74b4d968c66deb7d512952f39e858476e6769d967c3c3b95679a3b9bf73df7f3c31927dcf6deaa204204a996769fc0755e09e5d3dc278974f860e70ad4c2";
			}
			break;
		case "EMPTY":
			session = "";
			break;
		case "NO":
		
			session=null;
			break;
		
		}
		
		System.out.println("Cobrand Session is " + session);
		return session;
	}

	public static String getSessionTokenUser(String cobSessionType,String type, String userSessionType){
		String session="";
		switch (cobSessionType){
			case "VALID":
				if(type.equals("cob")) {
					session = configurationObj.getCobrandSessionObj().getCobSession();
					System.out.println("Session is = "+configurationObj.getCobrandSessionObj().getCobSession());
				}
				else if(type.equals("user")) {
					session = configurationObj.getCobrandSessionObj().getUserSession();
				}
				break;
			case "NULL":
				if(userSessionType.equals("NULL")) {
					session = null;
				}else {
					session = "{userSession={{userToken}}}";
				}
				break;
			case "INVALID":
				session = "INVALID";
				break;
			case "EXPIRED":
				if (type.equals("cob")) {
					session = "06142010_1:3d7738e100137f488aede4338a76315125c7670da91fc43016ccfe16fc6ce718a13eefe8b6f22b0e16fbe7e1965578361f86693c7610c1cee9faf55346a2ed54";
				} else if (type.equals("user")) {
					session = "06142010_0:604a74b4d968c66deb7d512952f39e858476e6769d967c3c3b95679a3b9bf73df7f3c31927dcf6deaa204204a996769fc0755e09e5d3dc278974f860e70ad4c2";
				}
				break;
			case "EMPTY":
				session = "";
				break;
			case "NULLTest":
				session = "{cobSession=null}";
				break;
			case "NO":
				session=null;
				break;
		}
		
		System.out.println("Cobrand Session is " + session);
		return session;
	}
	/**
	 * This method is used by SDG Test classes to set and get new userSession object
	 * by user login.
	 * 
	 * @param configurationObj
	 * @param httpParams
	 * @return
	 * @throws JoseException
	 */
	public EnvSession getSessionObjByUserLogin(String userName, String password) throws JoseException {

		UserHelper userHelper = new UserHelper();
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(userHelper.userLoginJSON(userName, password));

	/*	EnvSession envSessionObj = new EnvSession(Configuration.getInstance().getCobrandSessionObj().getCobSession(),
				configurationObj.getCobrandSessionObj().getPath());*/
		
		EnvSession envSessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		UserUtils userUtils = new UserUtils();
		String newUserSession = userUtils.userLogin(httpParams, envSessionObj);
		System.out.println("Inside getSessionObjByUserLogin()::");

		envSessionObj.setUserSession(newUserSession);

		return envSessionObj;
	}

	/**
	 * This method is used by SDG Test classes to set and get new userSession object
	 * by user registration.
	 * 
	 * @param userInfo
	 * @return
	 */
	public EnvSession getSessionObjectByUserRegistration(User userInfo) {

		UserHelper userHelper = new UserHelper();
		UserUtils userUtils = new UserUtils();
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(userHelper.userRegisterJson(userInfo));

		// Creating new user session
		/*EnvSession envSessionObj = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
				configurationObj.getCobrandSessionObj().getPath());*/
		
		EnvSession envSessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
					.path(configurationObj.getCobrandSessionObj().getPath()).build();
		String newUserSession = userUtils.userRegistration(envSessionObj, httpParams);
		System.out.println("userSessionToken with new user:::" + newUserSession);
		envSessionObj.setUserSession(newUserSession);

		return envSessionObj;
	}

}
