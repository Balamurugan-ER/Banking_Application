/**
 * 
 */
package com.bank.bm.info;

/**
 * @author Balamurugan
 *
 */
public class AccountsInfo 
{
	private int userId;
	private long accountNumber;
	private String ifsc;
	private String branch;
	private String status;
	private String type;
	private Integer balance;
	private long deposit;
	private int atmPin;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getIfsc() {
		return ifsc;
	}
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public long getDeposit() {
		return deposit;
	}
	public void setDeposit(long deposit) {
		this.deposit = deposit;
	}
	public void setAtmPin(int oldpin,int newPin) {
		if(this.atmPin == oldpin)
		{
			this.atmPin = newPin;
		}
	}
	public String toString()
	{
		String row = "{ "+getUserId()+","+getAccountNumber()+","+getIfsc()+","+getBalance()+","+getBranch()+","+getDeposit()+","+getType()+","+getStatus()+" }";
		return row;
	}
}
