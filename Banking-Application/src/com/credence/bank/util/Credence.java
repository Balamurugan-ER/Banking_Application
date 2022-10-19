/**
 * 
 */
package com.credence.bank.util;

/**
 * @author Balamurugan
 *
 */
public class Credence 
{
	public enum Status
	{
		ACTIVE("Active"),
		INACTIVE("InActive"),
		PENDING("pending"),
		APPROVED("approved"),
		REJECTED("rejected");
		private String status;
		private Status(String status)
		{
			this.status = status;
		}
		public String getStatus()
		{
			return this.status;
		}
	}
	public enum RequestType
	{
		REACTIVATEUSER("reActivateUser"),
		REACTIVATEACCOUNT("reActivateAccount");
		private String requestType;
		private RequestType(String requestType)
		{
			this.requestType = requestType;
		}
		public String getRequest()
		{
			return this.requestType;
		}
	}
	public enum AccountType
	{
		SAVINGS("savings"),
		CURRENT("current");
		private String accountType;
		private AccountType(String accountType)
		{
			this.accountType = accountType;
		}
		public String getAccountTypeChoice()
		{
			return this.accountType;
		}
	}
	public enum Role
	{
		CUSTOMER("customer"),
		EMPLOYEE("employee");
		private String role;
		private Role(String role)
		{
			this.role = role;
		}
		public String getRole()
		{
			return this.role;
		}
	}
	public enum Ifsc
	{
		KK123OPQ("KK123OPQ"),
		ABM190QAZ("ABM190QAZ");
		private String ifsc;
		private Ifsc(String ifsc)
		{
			this.ifsc = ifsc;
		}
		public String getIfsc()
		{
			return this.ifsc;
		}
	}
	public enum Access
	{
		USER("user"),
		ADMIN("admin");
		private String access;
		private Access(String access)
		{
			this.access = access;
		}
		public String getAccess()
		{
			return this.access;
		}
	}
	public enum TransactionType
	{
		WITHDRAW("withDraw"),
		SELFDEPOSIT("selfDeposit"),
		OTHERDEPOSIT("otherDeposit"),
		MONEYTRANSFER("moneyTransfer");
		TransactionType(String type) 
		{
			this.transactionType = type;
		}
		private String transactionType;
		public String getType()
		{
			return this.transactionType;
		}
	}
}
