/**
 * 
 */
package com.credence.bank.banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.credence.bank.info.AccountsInfo;
import com.credence.bank.info.TransactionInfo;
import com.credence.bank.info.UserInfo;
import com.credence.bank.util.BMException;
import com.credence.bank.util.EnvProperties;
import com.credence.bank.util.Utilities;

/**
 * @author Balamurugan
 *
 */
public enum DBBanking implements Storage 
{
	INST;
	@Override
	public void setup() {}
	@Override
	public void saveChanges() throws BMException {}
	private Connection getConnection() throws BMException
	{
		Connection connection = null;
		String url = EnvProperties.INST.envProps.getProperty("url");
		String userName = EnvProperties.INST.envProps.getProperty("username");
		String password = EnvProperties.INST.envProps.getProperty("password");
		try 
		{
			connection = DriverManager.getConnection(url,userName,password);
		} 
		catch (SQLException e) 
		{
			throw new BMException("connection failed",e);
		}
		return connection;
	}
	private void closeConnection(Connection connection) throws BMException
	{
		try 
		{
			connection.close();
		}
		catch (SQLException e) 
		{
			throw new BMException("Failed to Close Connection",e);
		}
	}
	@Override
	public UserInfo getUserInfo(Integer userId) throws BMException 
	{
		Utilities.INST.isNull(userId);
		// TODO getUserInfo
		String query = "SELECT * FROM Info WHERE UserId=? ;";
		UserInfo userInfo = new UserInfo();
		Connection connection = getConnection();
		try(PreparedStatement prepareStatement = connection.prepareStatement(query);
				ResultSet result = prepareStatement.getResultSet()) 
		{
			prepareStatement.setInt(1, userId);
			boolean response = prepareStatement.execute();
			if(!response)
			{
				throw new BMException("Could not Find UserInfo");
			}
			if(response)
			{
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
			throw new BMException("get UserInfo failed",e);
		}
		finally
		{
			closeConnection(connection);
		}
		return userInfo;

	}

	@Override
	public AccountsInfo getMyAccount(Integer userId, Integer accountNumber) throws BMException {
		// TODO getMyAccount
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		String query = "SELECT * FROM Accounts WHERE AccountNumber=? AND UserId = ? ;";
		AccountsInfo accountsInfo = new AccountsInfo();
		Connection connection = getConnection();
		try(PreparedStatement prepareStatement = connection.prepareStatement(query);
				ResultSet result = prepareStatement.getResultSet()) 
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
				while(result.next())
				{
					accountsInfo.setUserId(result.getInt(1));
					accountsInfo.setAccountNumber(result.getInt(2));
					accountsInfo.setIfsc(result.getString(3));
					accountsInfo.setBranch(result.getString(4));
					accountsInfo.setType(result.getString(5));
					accountsInfo.setStatus(result.getString(6));
					accountsInfo.setBalance(result.getDouble(7));
					accountsInfo.setAtmPin(result.getInt(8));
				}
			}
		}
		catch (SQLException e)
		{
			throw new BMException("Failed to Fetch AccountInfo",e);
		}
		finally
		{
			closeConnection(connection);
		}
		return accountsInfo;
	}

	@Override
	public Double getBalance(Integer userId, Integer accountNumber) throws BMException {
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		// TODO getBalance
		String query = "SELECT Balance FROM Accounts WHERE UserId = ? AND AccountNumber = ?;";
		Double balance = null;
		Connection connection = getConnection();
		try(PreparedStatement prepareStatement = connection.prepareStatement(query);
				ResultSet result = prepareStatement.getResultSet()) 
		{
			prepareStatement.setInt(1, userId);
			prepareStatement.setInt(2, accountNumber);
			boolean response = prepareStatement.execute();
			if(response)
			{
				result.next();
				balance = result.getDouble(1);
			}
		}
		catch (SQLException e) 
		{
			throw new BMException("Failed to Get Balance", e);
		}
		finally
		{
			closeConnection(connection);
		}
		return balance;
	}

	@Override
	public void selfDeposit(Integer userId, Integer accountNumber, Double amount) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		// TODO selfDeposit
		String query = "UPDATE Accounts SET Balance = Balance + ? WHERE UserId = ? AND AccountNumber = ? ;";
		Connection connection = getConnection();
		try (PreparedStatement prepareStatement = connection.prepareStatement(query))
		{

			prepareStatement.setDouble(1, amount);
			prepareStatement.setInt(2, userId);
			prepareStatement.setInt(3, accountNumber);
			prepareStatement.execute();
		}
		catch (SQLException e) 
		{
			throw new BMException("Self Deposit Failed",e);
		}
		finally
		{
			closeConnection(connection);
		}
	}

	@Override
	public void otherDeposit(Integer accountNumber, Double amount) throws BMException 
	{
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		String query = "UPDATE Accounts SET Balance = Balance + ? WHERE AccountNumber = ? ;";
		Connection connection = getConnection();
		try(PreparedStatement prepareStatement = connection.prepareStatement(query)) 
		{
			prepareStatement.setDouble(1, amount);
			prepareStatement.setInt(2, accountNumber);
			prepareStatement.execute();
		} 
		catch (SQLException e) 
		{
			throw new BMException("Deposit Failed",e);
		}
		finally
		{
			closeConnection(connection);
		}
	}

	@Override
	public void withDraw(Integer userId, Integer accountNumber, Double amount) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		String query = "UPDATE Accounts SET Balance = Balance - ? WHERE UserId = ? AND AccountNumber = ? AND Balance > ? ;";
		Connection connection = getConnection();
		try (PreparedStatement prepareStatement = connection.prepareStatement(query))
		{

			prepareStatement.setDouble(1, amount);
			prepareStatement.setInt(2, userId);
			prepareStatement.setInt(3, accountNumber);
			prepareStatement.setDouble(4, amount);
			prepareStatement.execute();
		}
		catch (SQLException e) 
		{
			throw new BMException("Withdraw Failed",e);
		}
		finally
		{
			closeConnection(connection);
		}
	}


	public void changeProfileInfo(Integer userId, String field,Object value) throws BMException
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(field);
		Utilities.INST.isNull(value);
		String query = "UPDATE Info SET "
				+ field
				+ "= ? WHERE UserId = ? ;";
		Connection connection = getConnection();
		try(PreparedStatement prepareStatement = connection.prepareStatement(query)) 
		{
			prepareStatement.setObject(1, value);
			prepareStatement.setInt(2, userId);
			prepareStatement.execute();
		}
		catch (SQLException e) 
		{
			throw new BMException("Failed to Change Profile Information",e);
		}
		finally
		{
			closeConnection(connection);
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
		String query = "UPDATE Info SET Password = ? WHERE UserId = ? AND Password = ? ;";
		Connection connection = getConnection();
		try(PreparedStatement prepareStatement = connection.prepareStatement(query)) 
		{
			prepareStatement.setString(1, newPassword);
			prepareStatement.setInt(2, userId);
			prepareStatement.setString(3, newPassword);
			prepareStatement.execute();
		} 
		catch (SQLException e) 
		{
			throw new BMException("Failed to Update Password",e);
		}
		finally
		{
			closeConnection(connection);
		}
	}

	@Override
	public void moneyTransfer(Integer userId, Integer senderAccountNo, Integer receiverAccountNo, Double amount) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(senderAccountNo);
		Utilities.INST.isNull(receiverAccountNo);
		Utilities.INST.isNull(amount);
		String withDrawQuery = "UPDATE Accounts SET Balance = Balance - ?  WHERE AccountNumber = ? AND Balance > ? ;";
		String creditQuery = "UPDATE Accounts SET Balance = Balance + ?  WHERE AccountNumber = ? ;";
		Connection connection = getConnection();
		try(PreparedStatement withDrawStatement = connection.prepareStatement(withDrawQuery);
				PreparedStatement creditStatement = connection.prepareStatement(creditQuery)) 
		{
			withDrawStatement.setDouble(1,amount);
			withDrawStatement.setInt(2,senderAccountNo);
			withDrawStatement.setDouble(3,amount);
			withDrawStatement.execute();
			creditStatement.setDouble(1,amount);
			creditStatement.setInt(2,receiverAccountNo);
			creditStatement.execute();
		}
		catch (SQLException e) 
		{
			throw new BMException("Transcation Failed",e);
		}
		finally
		{
			closeConnection(connection);
		}
	}

	@Override
	public void updateAtmPin(Integer userId, Integer accountNumber, Integer oldPin, Integer newPin) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(oldPin);
		Utilities.INST.isNull(newPin);
		String query = "UPDATE Accounts SET AtmPin = ? WHERE UserId = ? AND AccountNumber = ? AND AtmPin = ? ;";
		Connection connection = getConnection();
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
			throw new BMException("Failed to Update pin",e);
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
		String query = "SELECT * FROM Accounts WHERE UserId = ? ;";
		Map<Integer,AccountsInfo> myAccounts = new HashMap<>();
		Connection connection = getConnection();
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet result = preparedStatement.getResultSet())
		{
			preparedStatement.setInt(1, userId);
			boolean rescode = preparedStatement.execute();
			if(rescode)
			{
				while(result.next())
				{
					AccountsInfo accountsInfo = new AccountsInfo();
					accountsInfo.setUserId(result.getInt(1));
					accountsInfo.setAccountNumber(result.getLong(2));
					accountsInfo.setIfsc(result.getString(3));
					accountsInfo.setBranch(result.getString(4));
					accountsInfo.setType(result.getString(5));
					accountsInfo.setStatus(result.getString(6));
					accountsInfo.setBalance(result.getDouble(7));
					accountsInfo.setAtmPin(result.getInt(9));
					myAccounts.put((int) accountsInfo.getAccountNumber(), accountsInfo);
				}
			}
		} 
		catch (SQLException e) 
		{
			throw new BMException("Could not fetch accounts Info",e);
		}
		finally
		{
			closeConnection(connection);
		}
		return myAccounts;
	}
	@Override
	public void createUser(UserInfo userInfo) throws BMException 
	{
		Utilities.INST.isNull(userInfo);
		String query = "INSERT INTO Info VALUES(?,?,?,?,?,?,?,?) ;";
		Connection connection = getConnection();
		try(PreparedStatement preparedStatement = connection.prepareStatement(query)) 
		{
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
			throw new BMException("Failed to create user",e);
		}
		finally
		{
			closeConnection(connection);
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
		String query = "INSERT INTO Accounts VALUES(?,?,?,?,?,?,?,?,?) ;";
		Connection connection = getConnection();
		try(PreparedStatement preparedStatement = connection.prepareStatement(query)) 
		{
			preparedStatement.setInt(1, accountsInfo.getUserId());
			preparedStatement.setLong(2, accountsInfo.getAccountNumber());
			preparedStatement.setString(3, accountsInfo.getIfsc());
			preparedStatement.setString(4, accountsInfo.getBranch());
			preparedStatement.setString(5, accountsInfo.getType());
			preparedStatement.setString(6, accountsInfo.getStatus());
			preparedStatement.setDouble(7, accountsInfo.getBalance());
			preparedStatement.setInt(8, accountsInfo.getAtmPin());
		}
		catch (SQLException e) 
		{
			throw new BMException("Failed to create account",e);
		}
		finally
		{
			closeConnection(connection);
		}
	}
	@Override
	public void deleteAccount(Integer userId, AccountsInfo accountsInfo) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountsInfo);
		String query = "UPDATE Accounts SET Status = \"Inactive\" WHERE AccountNumber = ? AND UserId = ? ;";
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(query))
		{

			preparedStatement.setLong(1, accountsInfo.getAccountNumber());
			preparedStatement.setInt(2, accountsInfo.getUserId());
			preparedStatement.execute();
		}
		catch (SQLException e) 
		{
			throw new BMException("Deactivate Account Failed",e);
		}
		finally
		{
			closeConnection(connection);
		}
	}
	@Override
	public void dumpUserProfileData(List<UserInfo> userInfo) throws BMException 
	{
		Utilities.INST.isNull(userInfo);
		String query = "INSERT INTO Info VALUES(?,?,?,?,?,?,?,?)";
		Connection connection = getConnection();
		try(PreparedStatement dumpUserInfoQuery = connection.prepareStatement(query)) 
		{

			for(UserInfo user: userInfo)
			{
				dumpUserInfoQuery.setInt(1, user.getUserId());
				dumpUserInfoQuery.setString(2, user.getPassword());
				dumpUserInfoQuery.setString(3, user.getName());
				dumpUserInfoQuery.setString(4, user.getEmail());
				dumpUserInfoQuery.setLong(5, user.getPhone());
				dumpUserInfoQuery.setLong(6, user.getAadhar());
				dumpUserInfoQuery.setString(7, user.getRole());
				dumpUserInfoQuery.setString(8, user.getCity());
				dumpUserInfoQuery.addBatch();
			}
			dumpUserInfoQuery.executeBatch();
		} 
		catch (SQLException e) 
		{
			throw new BMException("Failed To Dump Profile Records",e);
		}
		finally
		{
			closeConnection(connection);
		}
	}
	@Override
	public void dumpAccountsData(List<AccountsInfo> accountsInfo) throws BMException 
	{
		Utilities.INST.isNull(accountsInfo);
		String query = "INSERT INTO Accounts VALUES(?,?,?,?,?,?,?,?,?)";
		Connection connection = getConnection();
		try(PreparedStatement dumpAccountsInfoQuery = connection.prepareStatement(query)) 
		{

			for(AccountsInfo account: accountsInfo)
			{
				dumpAccountsInfoQuery.setInt(1, account.getUserId());
				dumpAccountsInfoQuery.setLong(2, account.getAccountNumber());
				dumpAccountsInfoQuery.setString(3, account.getIfsc());
				dumpAccountsInfoQuery.setString(4, account.getBranch());
				dumpAccountsInfoQuery.setString(5, account.getType());
				dumpAccountsInfoQuery.setString(6, account.getStatus());
				dumpAccountsInfoQuery.setDouble(7, account.getBalance());
				dumpAccountsInfoQuery.setInt(8, account.getAtmPin());
				dumpAccountsInfoQuery.addBatch();
			}
			dumpAccountsInfoQuery.executeBatch();
		} 
		catch (SQLException e) 
		{
			throw new BMException("Failed To Dump Profile Records",e);
		}
		finally
		{
			closeConnection(connection);
		}

	}
	@Override
	public void addTransaction(TransactionInfo transactionInfo) throws BMException 
	{
		Utilities.INST.isNull(transactionInfo);
		String addTransactionQuery = "INSERT INTO Transaction VALUES(?,?,?,?,?,?,?)";
		Connection connection = getConnection();
		try(PreparedStatement addTransaction = connection.prepareStatement(addTransactionQuery))
		{
			addTransaction.setInt(1, transactionInfo.getTransactionId());
			addTransaction.setInt(2, transactionInfo.getUserId());
			addTransaction.setLong(3, transactionInfo.getSenderAccountNumber());
			addTransaction.setLong(4, transactionInfo.getReceiverAccountNumber());
			addTransaction.setFloat(5, (float) transactionInfo.getAmount());
			addTransaction.setLong(6, transactionInfo.getTime());
			addTransaction.setString(7, transactionInfo.getStatus());
			addTransaction.execute();
		} 
		catch (SQLException e)
		{
			throw new BMException("Failed To add Transaction",e);
		}
		finally
		{
			closeConnection(connection);
		}

	}
	@Override
	public TransactionInfo getTransaction(Integer transactionId) throws BMException 
	{
		Utilities.INST.isNull(transactionId);
		String getTransactionQuery = "SELECT * FROM Transaction WHERE TransactionId = ? ;";
		Connection connection = getConnection();
		TransactionInfo myTransaction = new TransactionInfo();
		try(PreparedStatement getTransaction = connection.prepareStatement(getTransactionQuery);
				ResultSet result = getTransaction.getResultSet())
		{
			getTransaction.setInt(1, transactionId);
			boolean rescode = getTransaction.execute();
			if(rescode)
			{
				result.next();
				myTransaction.setTransactionId(result.getInt(1));
				myTransaction.setUserId(result.getInt(2));
				myTransaction.setSenderAccountNumber(result.getInt(3));
				myTransaction.setReceiverAccountNumber(result.getInt(4));
				myTransaction.setAmount(result.getDouble(5));
				myTransaction.setTime(result.getLong(6));
				myTransaction.setStatus(result.getString(7));
			}
		}
		catch (SQLException e) 
		{
			throw new BMException("Failed To get Transaction",e);
		}
		finally
		{
			closeConnection(connection);
		}
		return myTransaction;
	}
	@Override
	public Map<?, ?> getAllTransaction() throws BMException 
	{
		String getAllTransactionQuery = "SELECT * FROM Transaction";
		Map<Integer,TransactionInfo> transactions = new HashMap<>();
		Connection connection = getConnection();
		try(PreparedStatement getAllTransaction = connection.prepareStatement(getAllTransactionQuery);
				ResultSet result = getAllTransaction.getResultSet())
		{
			boolean rescode = getAllTransaction.execute();
			if(rescode)
			{
				while(result.next())
				{
					TransactionInfo transactionInfo = new TransactionInfo();
					transactionInfo.setTransactionId(result.getInt(1));
					transactionInfo.setUserId(result.getInt(2));
					transactionInfo.setSenderAccountNumber(result.getInt(3));
					transactionInfo.setReceiverAccountNumber(result.getInt(4));
					transactionInfo.setAmount(result.getDouble(5));
					transactionInfo.setTime(result.getLong(6));
					transactionInfo.setStatus(result.getString(7));
					transactions.put(transactionInfo.getTransactionId(), transactionInfo);
				}
			}
		}
		catch (SQLException e) 
		{
			throw new BMException("Failed To Approve Transaction status",e);
		}
		finally
		{
			closeConnection(connection);
		}
		return transactions;
	}
	@Override
	public void grantApproval(Integer transactionId) throws BMException 
	{
		Utilities.INST.isNull(transactionId);
		String grantApproveQuery = "UPDATE Transaction SET Status= \"Approved\" WHERE TransactionId = ? ;";
		Connection connection = getConnection();
		try(PreparedStatement grantApprove = connection.prepareStatement(grantApproveQuery))
		{
			grantApprove.setInt(1, transactionId);
			grantApprove.execute();
		}
		catch (SQLException e) 
		{
			throw new BMException("Failed To Approve Transaction status",e);
		}
		finally
		{
			closeConnection(connection);
		}
	}

}
