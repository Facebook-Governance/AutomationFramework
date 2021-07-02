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

package com.yodlee.yodleeApi.schemaValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.everit.json.schema.FormatValidator;

/**
 * This class validates tyoes of Transactions i.e. if it is BUY,SELL,SHARE_IN
 * etc.
 * 
 * @author Rahul Kumar
 *
 */
public class EnumTransactionTypes implements FormatValidator {

	static List<String> transactionType = new ArrayList<String>();
	static {
		transactionType.add("BUY");
		transactionType.add("SELL");
		transactionType.add("DIVIDEND_PAYMENT");
		transactionType.add("DIVIDEND_REINVESTMENT");
		transactionType.add("MISCELLANEOUS_INCOME");
		transactionType.add("INTEREST_INCOME");
		transactionType.add("INTEREST_REINVESTMENT");
		transactionType.add("LONG_TERM_CAPITAL_GAINS_DISTRIBUTION");
		transactionType.add("REINVEST_LONG_TERM_CAPITAL_GAINS");
		transactionType.add("SHORT_TERM_CAPITAL_GAINS_DISTRIBUTION");
		transactionType.add("REINVEST_SHORT_TERM_CAPITAL_GAINS");
		transactionType.add("SHARES_IN");
		transactionType.add("SHARES_OUT");
		transactionType.add("TRANSFER_CASH_IN");
		transactionType.add("TRANSFER_CASH_OUT");
		transactionType.add("STOCK_SPLIT");
		transactionType.add("RETURN_OF_CAPITAL");
		transactionType.add("MISCELLANEOUS_EXPENSE");
		transactionType.add("TRANSFER_SHARES_IN");
		transactionType.add("TRANSFER_SHARES_OUT");
		transactionType.add("MARGIN_INTEREST_EXPENSE");
		transactionType.add("REMINDER");
		transactionType.add("CORPORATE_ACQUISITION");
		transactionType.add("STOCK_DIVIDEND");
		transactionType.add("SHORT_SELL");
		transactionType.add("BOND_CALL");
		transactionType.add("BOND_MATURES");
		transactionType.add("BUY_TO_COVER");
		transactionType.add("BUY_OPTION");
		transactionType.add("SELL_OPTION");
		transactionType.add("EXERCISE_OPTION");
		transactionType.add("ASSIGN_OPTION");
		transactionType.add("EXPIRE_OPTION");
		transactionType.add("OTHER");
		transactionType.add("SWEEP");
		transactionType.add("CAPITAL_GAINS_REINVESTED");
		transactionType.add("_1035_EXCHANGE");
		transactionType.add("_401K_CONTRIBUTION");
		transactionType.add("_401K_EMPLOYER_CONTRIBUTION");
		transactionType.add("LOAN_DISTRIBUTION");
		transactionType.add("LOAN_PAYMENT");
		transactionType.add("PENALTY");
		transactionType.add("ACCOUNT_FEE");
		transactionType.add("ACCOUNT_MAINTENANCE_FEE");
		transactionType.add("ACCUMULATION");
		transactionType.add("ACH_OUT");
		transactionType.add("ADJUSTED_BUY");
		transactionType.add("ADJUSTED_SELL");
		transactionType.add("ADMINISTRATIVE_FEE");
		transactionType.add("ANNUITY_CREDIT");
		transactionType.add("ATM_FEE");
		transactionType.add("ATM_WITHDRAWAL");
		transactionType.add("ATM_WITHDRAWAL_FEE");
		transactionType.add("ATM_WITHDRAWAL_FEE_CREDIT");
		transactionType.add("AUTOMATIC_INVESTMENT");
		transactionType.add("BAD_CHECK");
		transactionType.add("BILL_PAY");
		transactionType.add("BILL_PAY_IN");
		transactionType.add("BILL_PAY_OUT");
		transactionType.add("BUY_ACCRUED_INTEREST");
		transactionType.add("BUY_TO_CLOSE");
		transactionType.add("BUY_TO_OPEN");
		transactionType.add("CAPITAL_GAINS_RECIEVED");
		transactionType.add("CHARGE");
		transactionType.add("CHARGE_CREDIT");
		transactionType.add("CHECK");
		transactionType.add("CHECKBOOK_REORDER_FEE");
		transactionType.add("CREDIT_IN_LIEU_OF_FRACTIONAL_SHARE");
		transactionType.add("CS_ADJUSTMENT");
		transactionType.add("DEATH_BENEFIT_PAYOUT");
		transactionType.add("DEFERRED_COMPENSATION_CONTRIBUTION");
		transactionType.add("DEFERRED_COMPENSATION_DISTRIBUTION");
		transactionType.add("DEPOSIT");
		transactionType.add("DIRECT_DEPOSIT");
		transactionType.add("EDUCATIONAL_PLAN_CONTRIBUTION");
		transactionType.add("ESOP_ALLOCATION");
		transactionType.add("FEDERAL_TAX_FREE_DIVIDEND");
		transactionType.add("FEDEX_FEE");
		transactionType.add("FED_TAX_WITHHELD");
		transactionType.add("FOREIGN_TAX");
		transactionType.add("FOREIGN_TAX_CREDIT");
		transactionType.add("FRACTIONAL_SHARE_LIQUIDATION");
		transactionType.add("FUND_EXCHANGE");
		transactionType.add("FUND_EXPENSE");
		transactionType.add("IRA_CONTRIBUTION");
		transactionType.add("IRA_DISTRIBUTION");
		transactionType.add("IRA_NON_QUALIFIED_DISTRIBUTION");
		transactionType.add("MERGER");
		transactionType.add("MISC_CREDIT");
		transactionType.add("MISC_JRL_CASHTO_MARGIN");
		transactionType.add("MISC_JRL_MARGIN_TO_CASH");
		transactionType.add("MMF_DIVIDEND");
		transactionType.add("MMF_IN");
		transactionType.add("MMF_LIQ");
		transactionType.add("MMF_REIN");
		transactionType.add("MMF_SWEEP");
		transactionType.add("MMF_TRANSACTION");
		transactionType.add("MONEY_FUNDS_JOURNAL_CASH_TO_MARGIN");
		transactionType.add("MONEY_FUNDS_JOURNAL_MARGIN_TO_CASH");
		transactionType.add("MORTALITY_AND_EXPENSE_RISK_CHARGE");
		transactionType.add("NAME_CHANGE");
		transactionType.add("NSF_FEE");
		transactionType.add("ORDER_OUT");
		transactionType.add("ORDER_OUT_FEE");
		transactionType.add("OTHER_ANNUITY_FEE");
		transactionType.add("PAYOUT");
		transactionType.add("REORGANIZATION_CHARGE");
		transactionType.add("RETURNED_CHECK_FEE");
		transactionType.add("ROLLOVER_CONTRIBUTION");
		transactionType.add("ROTH_CONTRIBUTION");
		transactionType.add("RTQ_FEE");
		transactionType.add("SELL_ACCRUED_INTEREST");
		transactionType.add("SELL_TO_CLOSE");
		transactionType.add("SELL_TO_OPEN");
		transactionType.add("SEP_CONTRIBUTION");
		transactionType.add("SIMPLE_PLAN_CONTRIBUTION");
		transactionType.add("SPINOFF");
		transactionType.add("STATE_TAX_FREE_DIVIDEND");
		transactionType.add("STATE_TAX_WITHHELD");
		transactionType.add("STAX");
		transactionType.add("STOCK_FUND_OPTION_JOURNAL_CASH_TO_MARGIN");
		transactionType.add("STOCK_FUND_OPTION_JOURNAL_MARGIN_TO_CASH");
		transactionType.add("STOCK_OPTION_EXERCISE");
		transactionType.add("STOCK_OPTION_WITHHOLDING");
		transactionType.add("SURRENDER_CHARGE");
		transactionType.add("SYMBOL_CHANGE");
		transactionType.add("TAX_FREE_DIVIDEND");
		transactionType.add("WIRE_FEE");
		transactionType.add("WIRE_FUNDS_IN");
		transactionType.add("WIRE_FUNDS_OUT");
		transactionType.add("WORTHLESS_SECURITIES");
		transactionType.add("_529_PLAN_CONTRIBUTION");
		transactionType.add("ADJUSTED_ASSIGN");
		transactionType.add("REVERSAL");
		transactionType.add("DVP");
		transactionType.add("RVP");
		transactionType.add("ADJUSTMENT");
		transactionType.add("ADJUSTED_CREDIT");
		transactionType.add("ADJUSTED_DEBIT");
		transactionType.add("TENDERED");
		transactionType.add("ESOP_ALLOCATION_1");
		transactionType.add("EXCESS_CONTRIBUTION");
		transactionType.add("RECHARACTERIZATION");
		transactionType.add("CONVERSION");
		transactionType.add("ROLLOVER_TO_QUAL");
		transactionType.add("FEDERAL_TAX_FREE_INTEREST_INCOME");
		transactionType.add("STATE_TAX_FREE_INTEREST_INCOME");
		transactionType.add("FORFEITURE");
		transactionType.add("WITHDRAWAL");
		transactionType.add("LOAN_WITHDRAWAL");
		transactionType.add("BALANCE_FORWARD");
		transactionType.add("GENERIC_CONTRIBUTION");
		transactionType.add("CAPITAL_CALLS");
		transactionType.add("DISTRIBUTIONS_OUT");
		transactionType.add("PRINCIPAL_PAYMENT");

	}

	@Override
	public Optional<String> validate(final String subject) {

		if (!transactionType.contains(subject)) {
			return Optional.of(String.format("Invalid Transaction Type " + subject, subject));
		} else {
			return Optional.empty();
		}

	}
}
