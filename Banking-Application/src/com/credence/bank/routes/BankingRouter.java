/**
 * 
 */
package com.credence.bank.routes;

import java.util.Map;

import com.credence.bank.info.AccountsInfo;
import com.credence.bank.info.TransactionInfo;
import com.credence.bank.info.UserInfo;
import com.credence.bank.util.BMException;

/**
 * @author Balamurugan
 *
 */
public interface BankingRouter extends UserRouter
{	
	public Map<?,?> getAllTransaction()throws BMException;
	
	public void grantApproval(Integer transactionId)throws BMException;
	
}
