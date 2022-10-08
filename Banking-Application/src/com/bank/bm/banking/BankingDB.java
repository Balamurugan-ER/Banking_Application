/**
 * 
 */
package com.bank.bm.banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.bank.bm.info.AccountsInfo;
import com.bank.bm.info.UserInfo;
import com.bank.bm.util.BMException;

/**
 * @author Balamurugan
 *
 */
public enum BankingDB implements Storage 
{
	INST;
	private Properties envProps = new Properties();
	private Connection connection;
	private void establishConnection()
	{
		try 
		{
			connection = DriverManager.getConnection(envProps.getProperty("url"), envProps.getProperty("username"), envProps.getProperty("password"));
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	private void closeConnection() throws BMException
	{
		try 
		{
			connection.close();
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			throw new BMException(e.getMessage());
		}
	}
	BankingDB()
	{
		envProps.setProperty("url", "jdbc:mysql://localhost:3306/incubationDB");
		envProps.setProperty("username", "root");
		envProps.setProperty("password", "Root@123");
		establishConnection();
	}

	@Override
	public UserInfo getUserInfo(Integer userId) throws BMException {
		// TODO getUserInfo
		String query = "SELECT * FROM Info WHERE UserId=?";
		UserInfo userInfo = new UserInfo();
		try(PreparedStatement prepareStatement = connection.prepareStatement(query)) 
		{
			prepareStatement.setInt(1, userId);
			boolean response = prepareStatement.execute();
			if(!response)
			{
				throw new BMException("Could not Find UserInfo");
			}
			if(response)
			{
				ResultSet result = prepareStatement.getResultSet();
				
				while(result.next())
				{
					userInfo.setUserId(result.getInt(1));
					userInfo.setPassword(result.getString(2));
					userInfo.setName(result.getString(3));
					userInfo.setEmail(result.getString(4));
					userInfo.setPhone(result.getInt(5));
					userInfo.setAadhar(result.getInt(6));
					userInfo.setRole(result.getString(7));
					userInfo.setCity(result.getString(8));
				}
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BMException(e.getMessage());
		}
		return userInfo;
		
	}

	@Override
	public AccountsInfo getMyAccount(Integer userId, Integer accountNumber) throws BMException {
		// TODO getMyAccount
	
		String query = "SELECT * FROM Accounts WHERE AccountNumber=?";
		AccountsInfo accountsInfo = new AccountsInfo();
		try(PreparedStatement prepareStatement = connection.prepareStatement(query)) 
		{
			prepareStatement.setInt(1, accountNumber);
			boolean rescode = prepareStatement.execute();
			if(!rescode)
			{
				throw new BMException("Could not Find AccountInfo");
			}
			if(rescode)
			{
				ResultSet result = prepareStatement.getResultSet();
				while(result.next())
				{
					accountsInfo.setUserId(result.getInt(1));
					accountsInfo.setAccountNumber(result.getInt(2));
					accountsInfo.setIfsc(result.getString(3));
					accountsInfo.setBranch(result.getString(4));
					accountsInfo.setType(result.getString(5));
					accountsInfo.setStatus(result.getString(6));
					accountsInfo.setBalance(result.getInt(7));
					accountsInfo.setDeposit(result.getInt(8));
					accountsInfo.setAtmPin(0, result.getInt(9));
				}
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BMException(e.getMessage());
		}
		return accountsInfo;
	}

	@Override
	public Integer getBalance(Integer userId, Integer accountNumber) throws BMException {
		// TODO getBalance
		String query = "SELECT Balance FROM Accounts WHERE UserId = ? AND AccountNumber = ?";
		Integer balance = -1213;
		try(PreparedStatement prepareStatement = connection.prepareStatement(query)) 
		{
			prepareStatement.setInt(1, userId);
			prepareStatement.setInt(2, accountNumber);
			boolean response = prepareStatement.execute();
			if(response)
			{
				ResultSet result = prepareStatement.getResultSet();
				result.next();
				balance = result.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BMException(e.getMessage());
		}
		closeConnection();
		return balance;
	}

	@Override
	public void selfDeposit(Integer userId, Integer accountNumber, Integer amount) throws BMException {
		// TODO selfDeposit
		String query = "UPDATE Accounts SET Balance = ? WHERE UserId = ? AND AccountNumber = ?";
		int balance = getBalance(userId, accountNumber);
		balance += amount;
		try (PreparedStatement prepareStatement = connection.prepareStatement(query))
		{
			
			prepareStatement.setInt(1, balance);
			prepareStatement.setInt(2, userId);
			prepareStatement.setInt(3, accountNumber);
			prepareStatement.execute();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BMException(e.getMessage());
		}
		
	}

	@Override
	public void otherDeposit(Integer accountNumber, Integer amount) throws BMException 
	{
		// TODO Auto-generated method stub
		String query = "UPDATE Accounts SET Balance = ? WHERE AccountNumber = ?";
		AccountsInfo accountsInfo = getMyAccount(0, accountNumber);
		int balance = accountsInfo.getBalance();
		balance += amount;
		try(PreparedStatement prepareStatement = connection.prepareStatement(query)) 
		{
			prepareStatement.setInt(1, balance);
			prepareStatement.setInt(2, accountNumber);
			prepareStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BMException(e.getMessage());
		}
	}

	@Override
	public void withDraw(Integer userId, Integer accountNumber, Integer amount) throws BMException {
		// TODO Auto-generated method stub
		String query = "UPDATE Accounts SET Balance = ? WHERE UserId = ? AND AccountNumber = ?";
		int balance = getBalance(userId, accountNumber);
		if(balance < amount)
		{
			throw new BMException("Insufficient Balance");
		}
		balance -= amount;
		try (PreparedStatement prepareStatement = connection.prepareStatement(query))
		{
			
			prepareStatement.setInt(1, balance);
			prepareStatement.setInt(2, userId);
			prepareStatement.setInt(3, accountNumber);
			prepareStatement.execute();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BMException(e.getMessage());
		}
	}

	@Override
	public void changeEmail(Integer userId, String email) throws BMException {
		// TODO Auto-generated method stub
		String query = "UPDATE Info SET Email = ? WHERE UserId = ?";
		try(PreparedStatement prepareStatement = connection.prepareStatement(query)) 
		{
			prepareStatement.setString(1, email);
			prepareStatement.setInt(2, userId);
			prepareStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BMException(e.getMessage());
		}
	}

	@Override
	public void changeMobileNumber(Integer userId, Integer number) throws BMException {
		// TODO Auto-generated method stub
		String query = "UPDATE Info SET Phone = ? WHERE UserId = ?";
		try(PreparedStatement prepareStatement = connection.prepareStatement(query)) 
		{
			prepareStatement.setInt(1, number);
			prepareStatement.setInt(2, userId);
			prepareStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BMException(e.getMessage());
		}
	}

	@Override
	public void updatePassword(Integer userId, String oldPassword, String newPassword) throws BMException {
		// TODO Auto-generated method stub
		String query = "UPDATE Info SET Password = ? WHERE UserId = ? AND Password = ?";
		try(PreparedStatement prepareStatement = connection.prepareStatement(query)) 
		{
			prepareStatement.setString(1, newPassword);
			prepareStatement.setInt(2, userId);
			prepareStatement.setString(3, newPassword);
			prepareStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BMException(e.getMessage());
		}
	}

	@Override
	public void moneyTransfer(Integer userId, Integer senderAccountNo, Integer receiverAccountNo, Integer amount) throws BMException 
	{
		// TODO moneyTransfer
		String updateBalanceInfo = "UPDATE Accounts SET Balance = ? WHERE AccountNumber = ?";
		int senderBalance = getBalance(userId, senderAccountNo);
		AccountsInfo accountsInfo = getMyAccount(userId, receiverAccountNo);
		int receiverBalance = accountsInfo.getBalance();
		if(senderBalance < amount)
		{
			throw new BMException("Insufficient Balance");
		}
		senderBalance -= amount;
		receiverBalance += amount;
		try(PreparedStatement prepareStatement = connection.prepareStatement(updateBalanceInfo)) 
		{
			prepareStatement.setInt(1,senderBalance);
			prepareStatement.setInt(2,senderAccountNo);
			prepareStatement.execute();
			prepareStatement.setInt(1,receiverBalance);
			prepareStatement.setInt(2,receiverAccountNo);
			prepareStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BMException(e.getMessage());
		}
		
	}

	@Override
	public void updateAtmPin(Integer userId, Integer accountNumber, Integer oldPin, Integer newPin) throws BMException {
		// TODO updateAtmPin
		String query = "UPDATE Accounts SET AtmPin = ? WHERE UserId = ? AND AccountNumber = ? AND AtmPin = ?";
		try(PreparedStatement prepareStatement = connection.prepareStatement(query)) 
		{
			prepareStatement.setInt(1, newPin);
			prepareStatement.setInt(2, userId);
			prepareStatement.setInt(3, accountNumber);
			prepareStatement.setInt(4, oldPin);
			prepareStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BMException(e.getMessage());
		}
		

	}

}
