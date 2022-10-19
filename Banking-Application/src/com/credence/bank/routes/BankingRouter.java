/**
 * 
 */
package com.credence.bank.routes;

import java.util.Map;

import com.credence.bank.util.BMException;

/**
 * @author Balamurugan
 *
 */
public interface BankingRouter extends UserRouter //should authenticate and authorise for each methods
{	
	public Map<?,?> getAllTransaction()throws BMException;
	
	public Map<?, ?> getAllPendingTransaction() throws BMException;
	
	public void grantApproval(Integer transactionId)throws BMException;
	
	public void rejectTransaction(Integer transactionId) throws BMException;
	
	public void reActivateUser(Integer userId,Integer requestId) throws BMException;
	
	public void reActivateAccount(Integer accountNumber,Integer requestId) throws BMException;
	
	public Map<?,?> getAllRequests() throws BMException;
	
}
