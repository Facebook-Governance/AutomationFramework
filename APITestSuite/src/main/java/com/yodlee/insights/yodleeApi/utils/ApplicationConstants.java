/*
* Copyright (c) 2020 Yodlee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of Yodlee, Inc.
* Use is subject to license terms.
*
*/
package com.yodlee.insights.yodleeApi.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The Class ApplicationConstants includes all the String literals to use for
 * the Subscritpion APIs
 * 
 * 
 * @author Bindumalini KK
 * @author dkrishna
 */
public class ApplicationConstants {

	private ApplicationConstants() {
	}

	public static final String ACCOUNT = "account";
	public static final String VIEW = "view";
	public static final String ACCOUNT_GROUP = "accountGroup";
	public static final String GOAL = "goal";
	public static final String AGGREGATE = "AGGREGATE";
	public static final String AGGREGATED = "AGGREGATED";
	public static final String MANUAL = "MANUAL";
	public static final String OTHER = "OTHER";
	public static final String REFRESH = "REFRESH";
	public static final String SCHEDULED = "SCHEDULED";
	public static final String ENTITY_PARAMETER = "entityParameter";
	public static final String ID = "id";
	public static final String IS_SUBSCRIBED = "isSubscribed";	
	public static final String NAME = "name";
	public static final String THRESHOLD = "threshold";
	public static final String FREQUENCY = "frequency";
	public static final String DURATION = "duration";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	public static final String RULE_EXPRESSION = "ruleExpression";
	public static final String CONTAINER = "container";
	public static final String KEYS = "keys";
	public static final String APPLICABLE_ENTITY = "applicableEntity";
	public static final String ENTITY_CONFIGURATION = "entityConfiguration";
	public static final String TYPE = "type";
	public static final String VALUE = "value";
	public static final String THRESHOLD_TYPE = "threshold.type";
	public static final String THRESHOLD_NAME = "threshold.name";
	public static final String THRESHOLD_VALUE = "threshold.value";
	public static final String USER_SUBSCRIPTION = "UserSubscription";
	public static final String COBRAND_SUBSCRIPTION = "CobrandSubscription";
	public static final String TRIGGER_TYPE = "triggerType";
	public static final String INSIGHT_TYPE = "insightType";
	public static final String DAYS = "Days";
	public static final String AMOUNT = "Amount";
	public static final String PERCENT = "Percent";
	public static final String MEM_ID = "memId";
	public static final String COBRAND_ID = "cobrandId";
	public static final String IN = "in";
	public static final String ONE = "1";
	public static final String TEN = "10";
	public static final String THIRTY = "30";
	public static final String HUNDRED = "100";
	public static final String ONE_MILLION = "1000000";
	public static final String IS = "is";
	public static final String LOOK_IN_FUTURE = "lookInFuture";
	public static final String LOOK_IN_FUTURE_PERIOD = "lookInFuturePeriod";
	public static final String EXCLUDED_SPECIAL_CHARACTERS ="[%|<>{}\\[/\\][/\\\\]~^\"]";
	public static final String CHANGE = "change";
	public static final String DERIVED_NETWORTH_PATH = "/derived/networth";
	public static final String ACCOUNT_PATH = "/accounts";
	public static final String VIEW_URL_PATH = "/views/";
	public static final String NETWORTH = "networths";
	public static final String DECIMAL_FORMAT = "0.00";
	public static final String DECIMAL_FORMAT_ZERO_DOT_ZERO = "0.0";
	public static final String DECIMAL_FORMAT_DOT_ZERO_ZERO = ".00";
	public static final String DECIMAL_FORMAT_DOT_ZERO = ".0";
	public static final Double ZERO_DOUBLE = 0d;
	public static final String YSL_PATH = "ysl/";
	public static final String VERSION_ONE = "/v1";
	public static final String ADD_EVALUATION_METHOD = "add";
	public static final int PRECISION_NUMBER_DECIMAL_PLACES = 2;
	public static final String CATEGORY_TYPE = "categoryType";
	public static final String CURRENCY_USD = "USD";
	public static final String INVALID_REQUEST = "Invalid request";
	public static final String AMOUNT_RANGE_EXCEPTION = "fromAmount/toAmount";
	public static final String APPLICATION_JSON = "application/json";
	public static final String CONTENT_TYEP = "Content-Type";
	public static final String ACCEPT = "Accept";

	public static final Long ALL_COBRANDS_ALLOWED = -1L;
	public static final double HUNDRED_DOUBLE = 100D;

	private static final String COMMA_SEPERATED_STRINGS_PREFIX = "^([0-9a-zA-z]+)((,([0-9a-zA-z]+)){0,";
	public static final String COMMA_SEPERATED_NUMBERS = "^(\\d){1,10}((,(\\d){1,10})*)$";
	public static final String SPLIT_COMMA_VALUE = ",";
	
	public static final String NUMBER_ID_LIMIT_ACCOUNT = "100";
	public static final String NUMBER_ID_LIMIT_ACCOUNT_GROUP = "100";
	public static final String NUMBER_ID_LIMIT_GOAL = "50";
	public static final String NUMBER_ID_LIMIT_BOARD = "20";
	public static final String NUMBER_ID_LIMIT_CATEGORYID = "100";
	
	// These should be one value less than required
	private static final String NUMBER_ID_LIMIT_ACCOUNT_REGEX = "99";
	private static final String NUMBER_ID_LIMIT_ACCOUNT_GROUP_REGEX = "99";
	private static final String NUMBER_ID_LIMIT_GOAL_REGEX = "49";
	private static final String NUMBER_ID_LIMIT_BOARD_REGEX = "19";
	private static final String NUMBER_ID_LIMIT_CATEGORYID_REGEX = "99";

	public static final String COMMA_SEPERATED_STRINGS_REGEX_WITH_LIMIT_ACCOUNT = COMMA_SEPERATED_STRINGS_PREFIX
			+ NUMBER_ID_LIMIT_ACCOUNT_REGEX + "})$";
	public static final String COMMA_SEPERATED_STRINGS_REGEX_WITH_LIMIT_ACCOUNT_GROUP = COMMA_SEPERATED_STRINGS_PREFIX
			+ NUMBER_ID_LIMIT_ACCOUNT_GROUP_REGEX + "})$";
	public static final String COMMA_SEPERATED_STRINGS_REGEX_WITH_LIMIT_GOAL = COMMA_SEPERATED_STRINGS_PREFIX
			+ NUMBER_ID_LIMIT_GOAL_REGEX + "})$";
	public static final String COMMA_SEPERATED_STRINGS_REGEX_WITH_LIMIT_BOARD = COMMA_SEPERATED_STRINGS_PREFIX
			+ NUMBER_ID_LIMIT_BOARD_REGEX + "})$";
	public static final String COMMA_SEPERATED_STRINGS_REGEX_WITH_LIMIT_CATEGORYID = COMMA_SEPERATED_STRINGS_PREFIX
			+ NUMBER_ID_LIMIT_CATEGORYID_REGEX + "})$";
	
	public static final Double INSIGHTS_THRESHOLD_PERCENT  = 100D;
	
	public static final String CONFIG_PRIVATE_KEY = "com.yodlee.insights.config.private.key";
	
	public static final String KAFKA_CLIENT_ID_CONFIG = "Insights";
	
	public static final String KAFKA_STREAMS_LATEST_AUTO_OFFSET_RESET_CONFIG = "latest";
	
	public static final String MASTER_TXN_API_URI="/master/transactions";
	public static final String MASTER_TXN_SUMMARY_API_URI="/master/transactionSummary";
	public static final String MASTER_TXN_TRENDS_API_URI="/master/transactionTrends";
	public static final String INVOKER_SERVICE_BASE_URL="insights/invoker";
	
	public static final int BOARD_MAX_RULE_YEAR_RANGE = 10;
	public static final String INSIGHT_CACHEKEY_USERBOARD = "view";
	public static final String INSIGHT_CACHEKEY_USERBOARD_TRANSACTIONSUMMARY = "summary";
	public static final String INSIGHT_CACHEKEY_MASTER_TRANSACTION = "transaction";
	public static final String INSIGHT_CACHEKEY_MASTER_TRANSACTION_TRENDS = "transactionTrends";
	public static final String TIMEZONE_UTC = "UTC";
	public static final String INSIGHT_CACHEKEY_BOARDACCOUNT = "account";
	public static final String INSIGHT_CACHEKEY_ACCOUNTGROUP = "accountgroup";
	public static final String INSIGHT_CACHEKEY_ACCOUNT = "account";
	public static final String INSIGHT_CACHEKEY_ACCOUNTPATH = "/accounts";
	public static final String INSIGHT_CACHEKEY_STATEMENTPATH = "/statements";
	public static final String INSIGHT_CACHEKEY_PREDICTEDEVENTSPATH = "/predictedEvents";
	public static final String INSIGHT_CACHEKEY_EXCEEDED_BUDGET = "budget";
	public static final String INSIGHT_CACHEKEY_BOARDTRANSACTION = "transaction";
	public static final String INSIGHT_CACHEKEY_BOARDTRANSACTION_TRENDS = "transactionTrends";
	public static final String INSIGHT_CACHEKEY_BETATRANSACTION = "transaction";
	public static final String INSIGHT_CACHEKEY_PREDICTED_EVENT = "predictedevent";
	public static final String INSIGHT_CACHEKEY_MERCHANT = "merchant";
	public static final String INSIGHT_CACHEKEY_FREQUENCY = "frequency";
	public static final String INSIGHT_CACHEKEY_ACCOUNTPROXY = "accountProxy";
	public static final String INSIGHT_CACHEKEY_ACCOUNTLEVELPROXY = "accountLevelProxy";
	public static final String INSIGHT_CACHEKEY_BOARDPROXY = "boardProxy";
	public static final String INSIGHT_CACHEKEY_BOARDLEVELPROXY = "boardLevelProxy";
	public static final String INSIGHT_CACHEKEY_VIEWLEVELPROXY = "viewLevelProxy";
	public static final String INSIGHT_CACHEKEY_ACCOUNTGROUPPROXY = "accountGroupProxy";
	public static final String INSIGHT_CACHEKEY_ACCOUNTGROUPLEVELPROXY = "accountGroupLevelProxy";
	public static final String INSIGHT_CACHEKEY_STATEMENT = "statement";
	public static final String INSIGHT_CACHEKEY_HOLDINGPATH = "/holdings";
	public static final String INSIGHT_CACHEKEY_HOLDING = "holding";
	public static final String GET_METHOD_TYPE = "GET";
	public static final String ACTIVE = "ACTIVE";
	/*
	 * User Goal Constants : Start
	 */
	public static final String INSIGHT_CACHEKEY_GOALPROXY = "goalProxy";
	public static final String INSIGHT_CACHEKEY_GOALPATH = "/goals";
	public static final String INSIGHT_CACHEKEY_GOALENTITY = "goal";
	public static final String INSIGHT_CACHEKEY_GOALLEVELPROXY = "goalLevelProxy";
	// Goal Constants Ends

	public static final String INSIGHT_PATH_EXCEEDED_BUDGET = "/budgets";
	public static final String BEARER = "Bearer ";
	
	public static final String  SPEL_FOR_COMMON_ID_IN_RELATED_ENTITIES = "#idValuesFromForeignRelation(#accounts?.![id], #transactions?.![accountId])";
	
	public static final String MULTI_VALUED_KEY_MARKER="[]";
	public static final String MULTI_VALUED_KEY_MARKER_ESCAPE_SPCL_CHAR="\\[\\]";
	public static final String PROXY_CLASSNAME_TOKEN="Proxy";
	
	public static final int MAX_DATE_RANGE_MASTER_TXN_SUMMARY_API = 366;
	
	public static final int MAX_DATE_RANGE_MASTER_TXN_TRENDS_API = 365;
	
	public static final String INSIGHT_CACHEKEY_NETWORTH = "networth";
	public static final Long BANK_ACCOUNT = 10L;
	public static final Long CARD_ACCOUNT = 18L;
	public static final Long INVESTMENT_ACCOUNT = 47L;
	public static final Long LOAN_ACCOUNT = 54L; 
	public static final Long INSURANCE_ACCOUNT = 32L; 
	public static final Long BILLING_ACCOUNT = 13L; 
	public static final Long REWARD_PGM = 76L;
	public static final Long[] HOMEVALUE_ITEMDATATABLE_IDS = {98L,100L};
	public static final String INSIGHT_CACHEKEY_TRANSACTIONTRENDS = "transcationTrends";
	public static final String ERROR_MODIFIED_STRING = "Modifying ";
	public static final String ERROR_INVALID_VALUE_STRING = "Invalid value for ";
	
	public static final String CATEGORY_EXPENSE="EXPENSE";
	/* Derived keys start*/
	public static final String  DERIVED_KEY_AVERGAE_NET_TOTAL_AMOUNT = "_averageNetTotalAmount"; 
	public static final String  DERIVED_KEY_AVERAGE_NET_TOTAL_CURRENCY = "_averageNetTotalCurrency"; 
	public static final String  DERIVED_KEY_NET_TOTAL_AMOUNT = "_netTotalAmount"; 
	public static final String  DERIVED_KEY_NET_TOTAL_CURRENCY = "_netTotalCurrency"; 
	public static final String  DERIVED_KEY_CATEGORY_ID ="_categoryId";
	public static final String  DERIVED_KEY_CATEGORY_NAME = "_categoryName";
	public static final String  DERIVED_KEY_MERCHANT_NAME = "_merchantName";
	public static final String  DERIVED_KEY_BENCH_MARK_AS_OF = "_benchmarkAsOf";
	public static final String  DERIVED_KEY_TRANSACTION_CHANGE_AMOUNT = "_transactionChange.amount"; 
	public static final String  DERIVED_KEY_TRANSACTION_CHANGE_CURRENCY = "_transactionChange.currency"; 
	public static final String  DERIVED_KEY_TRANSACTION_CHANGE_PERCENT = "_transactionChangePercent"; 
	/* Derived keys end*/
	
	public static final String CATEGORY_INCOME="INCOME";
	
	public static final String CATEGORY_TRANSFER="TRANSFER";
	
	public static final String CATEGORY_UNCATEGORIZE = "UNCATEGORIZE";
	
	public static final String AVERAGE_NET_CASHFLOW_AMOUNT ="averageNetCashFlowAmount";
	
	public static final String CAMEINUNDERBUDGET = "CameinUnderBudget";
	public static final String BUDGET_HISTORY = "budgetHistory";
	
	public static final String INSIGHT_CACHEKEY_ACCOUNT_GROUP_PATH = "/groups";
	public static final String INSIGHT_CACHEKEY_BUDGET_HISTORY = "/budgetHistory";
	
	public static final String UNDERSCORE = "_";
	public static final String DOT = ".";
	
	public static final String ALL_COBRANDS_NAME = "all";
	public static final String ALL_COBRANDS_TITLE = "All";
	public static final Long ALL_COBRANDS_CHANNEL_ID = -1L;
	public static final String YODLEE_EMAIL_PREFIX = "@yodlee.com";
	public static final String LOGGER_FOR_COBRAND = " cobrandId is ... ";
	public static final String LOGGER_FOR_MEMID = " cobrandId is ... ";
	public static final String LOGGER_FOR_INSIGHTNAME = " insightName is ... ";
	public static final String API_VERSION ="1.1";
	public static final String KEY_FOR_DETAILS="details[]";
	public static final String KEY_FOR_DETAILS_DATE="details[].date";
	public static final String KEY_FOR_SUMMARY="summary[]";
	public static final String KEY_FOR_PEER_DATA="peerData";
	public static final String KEY_FOR_PEER_DATA_PEER_CATEGORY_SUMMARY="peerDataProxy.peerCategorySummary[]";
	public static final String KEY_FOR_PEER_DATA_PEER_MERCHANT_SUMMARY="peerDataProxy.peerMerchantSummary[]";
	public static final String BANK="bank";
	
	public static final String EXCEED="EXCEED";
	public static final String REMAINING="REMAINING";
	
	public static final String TRANSACTION_SOURCETYPE_SYSTEM_PREDICTED = "PREDICTED";
	public static final String SOURCETYPE_SYSTEM_PREDICTED = "SYSTEM_PREDICTED";
	public static final String CONTAINER_BANK = "bank";
	public static final String CONTAINER_CREDIT_CARD = "creditCard";
	public static final String CONTAINER_INVESTMENT = "investment";
	public static final String CONTAINER_LOAN = "loan";
	
	public static final String SPEL_TO_FILTER_ACCOUNTS_ON_CONTAINER = "account?.?[CONTAINER.name() == 'BANK' ||  CONTAINER.name() == 'CREDITCARD' ]";
	
	public static final String SPEL_TO_FILTER_LARGE_DEPOSIT_NOTICE_EXCLUDED_CATEGORY = "transaction?.?[status == 'POSTED' && categoryId != 227 && categoryId != 114 && categoryId != 29 && amount?.amount >= $0]";
	
	public static final String LOGGER_COBRAND_MEM_INSIGHT_ACCOUNT = " for CobrandId: {} , MemId: {} , Insight: {} , AccountId: {} ";
	public static final String LOGGER_COBRAND_MEM_INSIGHT_ACCOUNT_GROUP = " for CobrandId: {} , MemId: {} , Insight: {} , GroupId: {} ";
	public static final String LOGGER_COBRAND_MEM_INSIGHT_VIEW = " for CobrandId: {} , MemId: {} , Insight: {} , ViewId: {} ";
	public static final String LOGGER_COBRAND_MEM_INSIGHT = " for CobrandId: {} , MemId: {} , Insight: {} ";
	public static final String LOGGER_COBRAND_MEM_INSIGHT_EXCEPTION = "Exception for CobrandId: {} , MemId: {} , Insight: {} , Found: {} ";
	public static final String GROUP_BY_MERCHNAT= "merchant";
	public static final String ORDER_BY_TXN_COUNT= "txncount";
	
	public static final String TRANSACTION_BASE_TYPE_CREDIT ="CREDIT";
	public static final String TRANSACTION_BASE_TYPE_DEBIT ="DEBIT";
	public static final Long[]  LARGE_DEPOSIT_NOTICE_EXCLUDED_CATEGORY= {227L,114L,29L};
	public static final String[] ALLOWED_GROUP_BY_VALUES = new String[]{"merchant", "category", "categoryType"};
	public static final String[] ALLOWED_ORDER_BY_VAUES = new String[] {"amount","txncount"};
	public static final String[] ALLOWED_ORDER_BY_VAUES_TXN = new String[] {"amount","date"};
	public static final String[] ALLOWED_SORT_BY_VALUES = new String[] {"asc","desc"};
	
	public static final String ITEMACCOUNTIDS ="accountId";
	public static final String SOURCETYPE ="SourceType";
	public static final String USERAPPROVALSTATUS = "userApprovalStatus";
	public static final String PREDICTEDEVENTSTATUS = "predictedEventStatus";
	
	/*Date related start*/
	public static final String FROM_DATE_PARSE_ERROR_MSG = "fromDate; valid value must be of format \"yyyy-MM-dd'T'HH:mm:ss\" example: \"2019-12-05T02:15:11\"";
	public static final String TO_DATE_PARSE_ERROR_MSG = "toDate; valid value must be of format \"yyyy-MM-dd'T'HH:mm:ss\" example: \"2019-12-05T02:15:11\"";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String DATE_TIME_FORMAT_WITH_NO_TIMESTAMP = "yyyy-MM-dd";
	public static final String UTC_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static final String DATE_FORMAT = "yyyy-MM-dd";	
	public static final String UTC_DATE_REGEX = "^((?:[1-9][0-9]*)?[0-9]{4})-(1[0-2]|0[1-9])-(3[01]|0[1-9]|[12][0-9])T(2[0-3]|[01][0-9]):([0-5][0-9]):([0-5][0-9])(\\\\.[0-9]+)?(Z)$";	 
	public static final String DATE_REGEX = "^[0-9]{4}-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$";	    
	public static final Pattern UTC_DATE_PATTERN = Pattern.compile(UTC_DATE_REGEX);
	public static final Pattern DATE_PATTERN = Pattern.compile(DATE_REGEX);
	public static final Pattern EXCLUDED_SPECIAL_CHARACTERS_PATTERN =  Pattern.compile(ApplicationConstants.EXCLUDED_SPECIAL_CHARACTERS);
	/*Date related end*/
	
	public static final String REGION_US= "US";
	
	/*Transaction Category start*/
	public static final Long TRANSACTION_CATEGORY_ID_SERVICE_CHARGE_FEES = 24L;
	public static final String TRANSACTION_CATEGORY_NAME_SERVICE_CHARGE_FEES = "Service Charges/Fees";
	/*Transaction Category end*/
	public static final String CATEGORY="category";
	public static final String CATEGORY_ID_QUERY_PARAM="&categoryId";
	
	
	/*
	 * Adding Constants for PeerBenchmarking API Authorization:
	 * Start:
	 */
	public static final String RSA_ECB_PKCS5 = "RSA/ECB/PKCS1Padding";
	public static final String RSA = "RSA";	
	public static final String DEV_PROFILE_NAME = "dev";
	
	/*
	 * Ends
	 */
	
	/* Sort Order start*/
	public static final String SORT_ORDER_ASC = "asc";
	public static final String SORT_ORDER_DESC = "desc";
	/* Sort Order end*/
	
	public static final String INCLUDE = "include" ;
	public static final String EXCLUDE = "exclude" ;
	
	public static final String CURRENT_MONTH ="currentMonth";
	public static final String START_MONTH_NETWORTH ="startMonthNetworth";
	public static final String DETAILS = "details";
	
	public static final String YSL_API_VERSION_ONE_ZERO = "1.0";
	public static final String YSL_API_VERSION_ONE_ONE = "1.1";
	public static final int MAXIMUM_NOTIFICTIONS_SIZE = 50;
	public static final int DEFAULT_NOTIFICTIONS_SIZE = 50;
	public static final String NOTIFICATION_SKIP = "skip";
	public static final String NOTIFICATION_TOP = "top";
	public static final String NOTIFICATION_VALUE = "value";
	public static final String VERSION_1_DOT_1 = "1.1";
	public static final String VERSION_2_DOT_0 = "2.0";
	public static final String API_VERSIONS = "Api-Version";
	public static final String NOTIFICATION_COBRAND = "CobrandId";
	public static final String NOTIFICATION_MEM = "MemId";
	public static final String NOTIFICATION_CREATED_DATE = "CreatedDate";
	public static final String NOTIFICATION_NAME = "Name";
	public static final String COM_YODLEE ="com.yodlee";
	public static final String LOGGER_COM_YODLEE_INSIGHTS ="logging.level.com.yodlee.insights";
	
	
	/*
	 * Master transaction related constant
	 */
	public static final String TRANX_WITH_FROM_DATE_PATH = "/transactions?fromDate";
	public static final Character PLUS = '+';
	public static final String MERCHANT_NAME_QUERY_PARAM = "&merchantName";
	public static final String TO_DATE_QUERY_PARAM = "&toDate";
	public static final String EQUAL_TO_STRING = "=";
	
	
	public static final String IS_DELETED = "isDeleted";
	public static final String ROW_LAST_UPDATED = "rowLastUpdated";
	public static final String INSIGHTS_APPLICATION_ID = "insightsApp";
	public static final String  DELETE_CONFIGURATION_FOR_ALL_INSIGHTS = "Deleting configurations for all insights using a single API call is";
	public static final String DUPLICATE_VALUE="Duplicate values are";
	public static final String DELETE_OPERATION_FOR_DISABLED_INSIGHTS="Delete operation on insights without any custom consumer subscription is";
	public static final String ACCOUNT_GROUP_UPERCASE ="ACCOUNT_GROUP";
	
	public static final String INSIGHTS_NAME="insightName";
	public static final String ENTITY_NAME="entityName";
	public static final String EMPTY_JSON_OBJECT = "{}";
	public static final String ACCOUNT_ID="accountId";
	public static final String ACCOUNT_GROUP_ID="accountGroupId";
	public static final String VIEW_ID="viewId";
	public static final String GOAL_ID="goalId";
	public static final Character SEMICOLON=';';
	public static final String DELETE_API_ENTITY_ID_ERROR = "entity IDs that are not currently part of the consumer API for the requested insight.";
	public static final String DELETION ="Deletion";
	public static final String ID_NOT_SUPPORTED_ERROR= " This id is either invalid or not supported for this insight.";
	public static final String DELETE_OPERATION_FOR_COBRAND_DISABLED_INSIGHTS ="Delete operation for disabled insights is";
	public static final Long INTEREST_CHARGE = 1009L;
	public static final String DETAIL_CATEGORY = "detailCategory";
	
	public static final String FROM_AMOUNT_GREATER_THEN_TO_AMOUNT_ERROR= "fromAmount. fromAmount cannot be greater than toAmount";
	public static final String FROM_AMOUNT_QUERY_PARAM = "&fromAmount";
	public static final String TO_AMOUNT_QUERY_PARAM = "&toAmount";
	public static final String DATE_QUERY_PARAM = "date";
	public static final Long[] UNCATEGORIZED_CAT_IDS = { 1L, 19L, 32L };
	public static final String CREDIT_AND_DEBIT = "CREDIT,DEBIT";
	public static final String DUPLICATE="Duplicate";
	public static final String AGGREAGE_MORTGAGE_ACCOUNT_MESSAGE = "You have mortgage payments but you haven't added the mortgage account. Aggregate to let us help track your mortgage.";
	public static final String MORTGAGE="MORTGAGE";
	public static final String AGGREAGE_REALESTATE_ACCOUNT_MESSAGE = "You have mortgage payments but you haven't added real estate account. Aggregate to let us help track your property value";
	public static final String AGGREAGE_INSURANCE_ACCOUNT_MESSAGE = "You have insurance payments but you haven't added the insurance account. Aggregate to let us help track your insurance accounts";
	public static final List<String> INVESTMENT_SUPPORTED_ACCT_TYPES = Arrays.asList("BROKERAGE_CASH", "CMA",
			"MONEY_MARKET", "CASH_ISA");
	public static final String INSURANCE="INSURANCE";
	public static final String AGGREAGE_CREDIT_CARD_ACCOUNT_MESSAGE = "You have credit card payments but you haven't aggregated any credit card account. Aggregate to let us help track your spending accurately";

	public static final Long CATAGORY_ID_CREDIT_CARD_PAYMENTS = 26L;
	/*
	 * Analytics API Call Related constants
	 */
	public static final String ANALYTICS_HEADER_APP_ID = "insightsApp";
	public static final String SECRET_KEY = "InsightEngineApplication";
	public static final String MERCHANT_TYPE_BILLERS = "BILLERS"; 
	public static final String FROM_DATE = "fromDate";
	public static final String TO_DATE = "toDate";
	public static final String CATEGORY_ID = "categoryId";
	
}
