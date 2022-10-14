/**
 * 
 */
package com.credence.bank.controller;

import java.util.Map;

import com.credence.bank.banking.Storage;
import com.credence.bank.info.TransactionInfo;
import com.credence.bank.util.BMException;
import com.credence.bank.util.Utilities;

/**
 * @author Balamurugan
 *
 */
public class TransactionRouter implements Transaction
{
	private static Storage banking;
	
	private static Storage getStorage() throws BMException 
	{
		if(banking == null)
		{
			banking = Utilities.INST.getStorage();
		}
		return banking;
	}
	@Override
	public void addTransaction(TransactionInfo transactionInfo) throws BMException 
	{
		Utilities.INST.isNull(transactionInfo);
		banking = getStorage();
		banking.addTransaction(transactionInfo);
	}

	@Override
	public TransactionInfo getTransaction(Integer transactionId) throws BMException
	{
		Utilities.INST.isNull(transactionId);
		banking = getStorage();
		return banking.getTransaction(transactionId);
	}

	public Map<?, ?> getAllTransaction() throws BMException
	{
		banking = getStorage();
		return banking.getAllTransaction();
	}

	public void grantApproval(Integer transactionId) throws BMException
	{
		Utilities.INST.isNull(transactionId);
		banking = getStorage();
		banking.grantApproval(transactionId);
		TransactionInfo thisTransaction = getTransaction(transactionId);
		String status = thisTransaction.getStatus();
		String type = thisTransaction.getType();
		double amount = thisTransaction.getAmount();
		int userId = thisTransaction.getUserId();
		int receiverAccountNumber = thisTransaction.getReceiverAccountNumber();
		int senderAccountNumber = thisTransaction.getSenderAccountNumber();
		if(status.equals("Approved"))
		{
			if(type.equals(TransactionType.SELFDEPOSIT.getType()))
			{
				banking.selfDeposit(userId, receiverAccountNumber, amount);
			}
			if(type.equals(TransactionType.OTHERDEPOSIT.getType()))
			{
				banking.otherDeposit(receiverAccountNumber, amount);
			}
			if(type.equals(TransactionType.WITHDRAW.getType()))
			{
				Double curentBalance = banking.getBalance(userId, receiverAccountNumber);
				if(curentBalance < amount)
				{
					throw new BMException("Insufficient Balance to do this Transaction");
				}
				banking.withDraw(userId, receiverAccountNumber, amount);
			}
			if(type.equals(TransactionType.MONEYTRANSFER.getType()))
			{
				banking.moneyTransfer(userId, senderAccountNumber, receiverAccountNumber, amount);
			}
		}
	}
	@Override
	public Map<?, ?> getAllPendingTransaction() throws BMException 
	{
		return banking.getAllPendingTransaction();
	}
	@Override
	public void rejectTransaction(Integer transactionId) throws BMException 
	{
		banking.rejectTransaction(transactionId);
	}

}
