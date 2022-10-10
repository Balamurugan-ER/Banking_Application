/**
 * 
 */
package com.credence.bank.controller;

import java.util.Map;

import com.credence.bank.info.TransactionInfo;

/**
 * @author Balamurugan
 *
 */
public interface Transaction 
{
	/**
	 * 
	 * @param transactionInfo
	 */
	public void addTransaction(TransactionInfo transactionInfo);
	
	/**
	 * 
	 * @param transactionId 
	 * @return TransactionInfo {@link TransactionInfo}
	 */
	public TransactionInfo getTransaction(Integer transactionId);
	
	/**
	 * 
	 * @param userId
	 * @return Map of transactions 
	 * @implNote admin only method
	 */
	public Map<?,?> getAllTransaction();
}
