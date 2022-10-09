/**
 * 
 */
package com.credence.bank.banking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.credence.bank.info.AccountsInfo;
import com.credence.bank.info.UserInfo;
import com.credence.bank.util.BMException;
import com.credence.bank.util.Utilities;

/**
 * @author Balamurugan
 *
 */
public enum BankingDB implements Storage 
{
	INST;
	private Connection connection;
	private void establishConnection()
	{
		try 
		{
			connection = DriverManager.getConnection(EnvProperties.INST.envProps.getProperty("url"), EnvProperties.INST.envProps.getProperty("username"), EnvProperties.INST.envProps.getProperty("password"));
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
		establishConnection();
	}

	@Override
	public UserInfo getUserInfo(Integer userId) throws BMException 
	{
		Utilities.INST.isNull(userId);
		// TODO getUserInfo
		String query = "SELECT * FROM Info WHERE UserId=?";
		UserInfo userInfo = new UserInfo();
		ResultSet result = null;
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
				result = prepareStatement.getResultSet();

				while(result.next())
				{
					userInfo.setUserId(result.getInt(1));
					userInfo.setPassword(result.getString(2));
					userInfo.setName(result.getString(3));
					userInfo.setEmail(result.getString(4));
					userInfo.setPhone(result.getLong(5));
					userInfo.setAadhar(result.getLong(6));
					userInfo.setRole(result.getString(7));
					userInfo.setCity(result.getString(8));
				}
			}
		} 
		catch (SQLException e) 
		{
			throw new BMException(e.getMessage());
		}
		finally
		{
			closeConnection();
			try 
			{
				result.close();
			} 
			catch (SQLException e) {}
		}
		return userInfo;

	}

	@Override
	public AccountsInfo getMyAccount(Integer userId, Integer accountNumber) throws BMException {
		// TODO getMyAccount
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		String query = "SELECT * FROM Accounts WHERE AccountNumber=? AND UserId = ?";
		ResultSet result = null;
		AccountsInfo accountsInfo = new AccountsInfo();
		try(PreparedStatement prepareStatement = connection.prepareStatement(query)) 
		{
			prepareStatement.setInt(1, accountNumber);
			prepareStatement.setInt(2, userId);
			boolean rescode = prepareStatement.execute();
			if(!rescode)
			{
				throw new BMException("Could not Find AccountInfo");
			}
			if(rescode)
			{
				result = prepareStatement.getResultSet();
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
					accountsInfo.setAtmPin(result.getInt(9));
				}
			}
		}
		catch (SQLException e)
		{
			throw new BMException(e.getMessage());
		}
		finally
		{
			closeConnection();
			try 
			{
				result.close();
			} 
			catch (SQLException e) {}
		}
		return accountsInfo;
	}

	@Override
	public Integer getBalance(Integer userId, Integer accountNumber) throws BMException {
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		// TODO getBalance
		String query = "SELECT Balance FROM Accounts WHERE UserId = ? AND AccountNumber = ?";
		ResultSet result = null;
		Integer balance = -1213;
		try(PreparedStatement prepareStatement = connection.prepareStatement(query)) 
		{
			prepareStatement.setInt(1, userId);
			prepareStatement.setInt(2, accountNumber);
			boolean response = prepareStatement.execute();
			if(response)
			{
				result = prepareStatement.getResultSet();
				result.next();
				balance = result.getInt(1);
			}
		}
		catch (SQLException e) 
		{
			throw new BMException(e.getMessage());
		}
		finally
		{
			closeConnection();
			try 
			{
				result.close();
			} 
			catch (SQLException e) {}
		}
		return balance;
	}

	@Override
	public void selfDeposit(Integer userId, Integer accountNumber, Integer amount) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
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
		catch (SQLException e) 
		{
			throw new BMException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
	}

	@Override
	public void otherDeposit(Integer accountNumber, Integer amount) throws BMException 
	{
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		String query = "UPDATE Accounts SET Balance = ? WHERE AccountNumber = ?";
		AccountsInfo accountsInfo = getMyAccount(0, accountNumber);
		int balance = accountsInfo.getBalance();
		balance += amount;
		try(PreparedStatement prepareStatement = connection.prepareStatement(query)) 
		{
			prepareStatement.setInt(1, balance);
			prepareStatement.setInt(2, accountNumber);
			prepareStatement.execute();
		} 
		catch (SQLException e) 
		{
			throw new BMException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
	}

	@Override
	public void withDraw(Integer userId, Integer accountNumber, Integer amount) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
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
		catch (SQLException e) 
		{
			throw new BMException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
	}


	public void changeProfileInfo(Integer userId, String field,Object value) throws BMException
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(field);
		Utilities.INST.isNull(value);
		String query = "UPDATE Info SET "
				+ field
				+ "= ? WHERE UserId = ?";
		try(PreparedStatement prepareStatement = connection.prepareStatement(query)) 
		{
			prepareStatement.setObject(1, value);
			prepareStatement.setInt(2, userId);
			prepareStatement.execute();
		}
		catch (SQLException e) 
		{
			throw new BMException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
	}
	@Override
	public void changeEmail(Integer userId, String email) throws BMException 
	{
		changeProfileInfo(userId, "Email", email);
	}

	@Override
	public void changeMobileNumber(Integer userId, Integer number) throws BMException 
	{
		changeProfileInfo(userId, "Phone", number);
	}

	@Override
	public void updatePassword(Integer userId, String oldPassword, String newPassword) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(oldPassword);
		Utilities.INST.isNull(newPassword);
		String query = "UPDATE Info SET Password = ? WHERE UserId = ? AND Password = ?";
		try(PreparedStatement prepareStatement = connection.prepareStatement(query)) 
		{
			prepareStatement.setString(1, newPassword);
			prepareStatement.setInt(2, userId);
			prepareStatement.setString(3, newPassword);
			prepareStatement.execute();
		} 
		catch (SQLException e) 
		{
			throw new BMException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
	}

	@Override
	public void moneyTransfer(Integer userId, Integer senderAccountNo, Integer receiverAccountNo, Integer amount) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(senderAccountNo);
		Utilities.INST.isNull(receiverAccountNo);
		Utilities.INST.isNull(amount);
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
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			throw new BMException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
	}

	@Override
	public void updateAtmPin(Integer userId, Integer accountNumber, Integer oldPin, Integer newPin) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(oldPin);
		Utilities.INST.isNull(newPin);
		String query = "UPDATE Accounts SET AtmPin = ? WHERE UserId = ? AND AccountNumber = ? AND AtmPin = ?";
		try(PreparedStatement prepareStatement = connection.prepareStatement(query)) 
		{
			prepareStatement.setInt(1, newPin);
			prepareStatement.setInt(2, userId);
			prepareStatement.setInt(3, accountNumber);
			prepareStatement.setInt(4, oldPin);
			prepareStatement.execute();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			throw new BMException(e.getMessage());
		}


	}
	@Override
	public void changeName(Integer userId, String name) throws BMException 
	{
		changeProfileInfo(userId, "Name", name);
	}
	@Override
	public void changeRole(Integer userId, String role) throws BMException 
	{
		changeProfileInfo(userId, "Role", role);

	}
	@Override
	public void changeCity(Integer userId, String name) throws BMException 
	{
		changeProfileInfo(userId, "City", name);

	}
	@Override
	public void changeType(Integer userId, String type) throws BMException 
	{
		changeProfileInfo(userId, "Type", type);
	}
	@Override
	public void changeAadhar(Integer userId, Integer aadharNumber) throws BMException 
	{
		changeProfileInfo(userId, "Aadhar", aadharNumber);

	}
	@Override
	public Map<?, ?> getMyAccountsInfo(Integer userId) throws BMException 
	{
		Utilities.INST.isNull(userId);
		String query = "SELECT * FROM Accounts WHERE UserId = ?";
		ResultSet result = null;
		Map<Integer,AccountsInfo> myAccounts = new HashMap<>();
		try 
		{
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			boolean rescode = preparedStatement.execute();
			if(rescode)
			{
				result = preparedStatement.getResultSet();
				while(result.next())
				{
					AccountsInfo accountsInfo = new AccountsInfo();
					accountsInfo.setUserId(result.getInt(1));
					accountsInfo.setAccountNumber(result.getLong(2));
					accountsInfo.setIfsc(result.getString(3));
					accountsInfo.setBranch(result.getString(4));
					accountsInfo.setType(result.getString(5));
					accountsInfo.setStatus(result.getString(6));
					accountsInfo.setBalance(result.getInt(7));
					accountsInfo.setDeposit(result.getLong(8));
					accountsInfo.setAtmPin(result.getInt(9));
					myAccounts.put((int) accountsInfo.getAccountNumber(), accountsInfo);
				}
			}
		} 
		catch (SQLException e) 
		{
			throw new BMException(e.getMessage());
		}
		finally
		{
			closeConnection();
			try 
			{
				result.close();
			} 
			catch (SQLException e) {}
		}
		return myAccounts;
	}
	@Override
	public void fixedDeposit(Integer accountNumber, Integer amount) throws BMException 
	{
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		String query = "SELECT Deposit FROM Accounts WHERE AccountNumber = ?";
		String updateQuery = "UPDATE Accounts SET Deposit = ? WHERE AccountNumber = ?";
		Integer deposit = null;
		ResultSet result = null;
		try 
		{
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, accountNumber);
			boolean rescode = preparedStatement.execute();
			if(rescode)
			{
				result = preparedStatement.getResultSet();
				result.next();
				deposit = result.getInt(1);
				deposit += amount;
			}
			preparedStatement = connection.prepareStatement(updateQuery);
			preparedStatement.setInt(1, deposit);
			preparedStatement.setInt(2, accountNumber);
			preparedStatement.execute();			
		}
		catch (SQLException e) 
		{
			throw new BMException(e.getMessage());
		}
		finally
		{
			closeConnection();
			try 
			{
				result.close();
			} 
			catch (SQLException e) {}
		}

	}
	@Override
	public void createUser(UserInfo userInfo) throws BMException 
	{
		Utilities.INST.isNull(userInfo);
		String query = "INSERT INTO Info VALUES(?,?,?,?,?,?,?,?)";
		try 
		{
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userInfo.getUserId());
			preparedStatement.setString(2, userInfo.getPassword());
			preparedStatement.setString(3, userInfo.getName());
			preparedStatement.setString(4, userInfo.getEmail());
			preparedStatement.setLong(5, userInfo.getPhone());
			preparedStatement.setLong(6, userInfo.getAadhar());
			preparedStatement.setString(7, userInfo.getRole());
			preparedStatement.setString(8, userInfo.getCity());
			preparedStatement.execute();
		}
		catch (SQLException e) 
		{
			throw new BMException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}

	}
	@Override
	public void removeUser(Integer userId) throws BMException 
	{
		changeProfileInfo(userId, "Status", "Inactive");
	}
	@Override
	public void createAccount(Integer userId, AccountsInfo accountsInfo) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountsInfo);
		String query = "INSERT INTO Accounts VALUES(?,?,?,?,?,?,?,?,?)";
		try 
		{
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, accountsInfo.getUserId());
			preparedStatement.setLong(2, accountsInfo.getAccountNumber());
			preparedStatement.setString(3, accountsInfo.getIfsc());
			preparedStatement.setString(4, accountsInfo.getBranch());
			preparedStatement.setString(5, accountsInfo.getType());
			preparedStatement.setString(6, accountsInfo.getStatus());
			preparedStatement.setInt(7, accountsInfo.getBalance());
			preparedStatement.setLong(8, accountsInfo.getDeposit());
			preparedStatement.setInt(9, accountsInfo.getAtmPin());
		}
		catch (SQLException e) 
		{
			throw new BMException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
	}
	@Override
	public void deleteAccount(Integer userId, AccountsInfo accountsInfo) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountsInfo);
		String query = "UPDATE Accounts SET Status = \"Inactive\" WHERE AccountNumber = ? AND UserId = ?";
		try 
		{
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, accountsInfo.getAccountNumber());
			preparedStatement.setInt(2, accountsInfo.getUserId());
			preparedStatement.execute();
		}
		catch (SQLException e) 
		{
			throw new BMException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
	}

}
