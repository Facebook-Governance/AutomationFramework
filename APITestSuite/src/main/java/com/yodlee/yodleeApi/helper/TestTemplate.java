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

import org.bouncycastle.jce.provider.ProviderUtil;

import com.yodlee.commonUtils.RestUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.CobrandUtils;
import com.yodlee.yodleeApi.utils.apiUtils.DataExtractUtils;
import com.yodlee.yodleeApi.utils.apiUtils.DocumentUtils;
import com.yodlee.yodleeApi.utils.apiUtils.HoldingUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.StatementUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TransactionUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.apiUtils.VerificationUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import com.yodlee.yodleeApi.utils.commonUtils.PropertyFileUtil;

/**
 * @author Soujanya Kokala
 *
 *         This class will have all the instances of Utils and Helper classes.
 */
public class TestTemplate extends Base {

	protected CobrandUtils cobrandUtils;
	protected UserUtils userUtils;
	protected AccountUtils accountUtils;

	protected DataExtractUtils dataExtractUtils;
	protected DocumentUtils documentUtils;
	protected HoldingUtils holdingUtils;

	protected ProviderAccountUtils providerAccountUtils;
	protected ProviderUtil providerUtil;
	protected StatementUtils statementUtils;

	protected TransactionUtils transactionUtils;
	protected VerificationUtils verificationUtils;

	protected CommonUtils commonUtils;
	protected PropertyFileUtil propertyFileUtil;
	protected RestUtil restUtil;
	protected SessionHelper sessionUtil;

	protected AccountsHelper accountsHelper;
	protected ProviderAccountsHelper providerAccountsHelper;
	protected ProvidersHelper providersHelper;
	protected DocumentsHelper documentHelper;
	protected TransactionsHelper transactionsHelper;
	protected VerificationHelper verificationHelper;
	protected CobrandHelper cobrandHelper;
	protected UserHelper userHelper;

	public TestTemplate() {

		cobrandUtils = new CobrandUtils();
		userUtils = new UserUtils();
		accountUtils = new AccountUtils();
		dataExtractUtils = new DataExtractUtils();
		documentUtils = new DocumentUtils();
		holdingUtils = new HoldingUtils();
		providerAccountUtils = new ProviderAccountUtils();
		providerUtil = new ProviderUtil();
		statementUtils = new StatementUtils();
		transactionUtils = new TransactionUtils();
		verificationUtils = new VerificationUtils();
		commonUtils = new CommonUtils();
		propertyFileUtil = new PropertyFileUtil();
		restUtil = new RestUtil();
		sessionUtil = new SessionHelper();

		accountsHelper = new AccountsHelper();
		providerAccountsHelper = new ProviderAccountsHelper();
		providersHelper = new ProvidersHelper();
		documentHelper = new DocumentsHelper();
		transactionsHelper = new TransactionsHelper();

		verificationHelper = new VerificationHelper();

		cobrandHelper = new CobrandHelper();
		userHelper = new UserHelper();

	}

}
