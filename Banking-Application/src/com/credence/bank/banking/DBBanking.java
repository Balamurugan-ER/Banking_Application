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
import com.credence.bank.info.RequestInfo;
import com.credence.bank.info.TransactionInfo;
import com.credence.bank.info.UserInfo;
import com.credence.bank.util.BMException;
import com.credence.bank.util.Credence;
import com.credence.bank.util.EnvProperties;
import com.credence.bank.util.Utilities;

/**
 * @author Balamurugan
 *
 */
public class DBBanking implements Storage 
{
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
			if(connection != null)
			{
				connection.close();
			}
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
				try(ResultSet result = prepareStatement.getResultSet())
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
						userInfo.setStatus(result.getString(9));
						userInfo.setAdminAccess(result.getString(10));
					}
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
				try(ResultSet result = prepareStatement.getResultSet())
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
	public Double getBalance(Integer accountNumber) throws BMException
	{
		Utilities.INST.isNull(accountNumber);
		String query = "SELECT Balance FROM Accounts WHERE AccountNumber = ?";
		Connection connection = getConnection();
		Double balance = null;
		try(PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			preparedStatement.setInt(1, accountNumber);
			boolean response = preparedStatement.execute();
			if(response)
			{
				try(ResultSet result = preparedStatement.getResultSet())
				{
					result.next();
					balance = result.getDouble(1);
				}
			}
		} 
		catch (SQLException e) 
		{
			throw new BMException("Balanced could not find",e);
		}
		finally
		{
			closeConnection(connection);
		}
		return balance;
	}
	@Override
	public Double getBalance(Integer userId, Integer accountNumber) throws BMException {
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		// TODO getBalance
		String query = "SELECT Balance FROM Accounts WHERE UserId = ? AND AccountNumber = ?;";
		Double balance = null;
		Connection connection = getConnection();
		try(PreparedStatement prepareStatement = connection.prepareStatement(query)) 
		{
			prepareStatement.setInt(1, userId);
			prepareStatement.setInt(2, accountNumber);
			boolean response = prepareStatement.execute();
			if(response)
			{
				try(ResultSet result = prepareStatement.getResultSet())
				{
					result.next();
					balance = result.getDouble(1);
				}
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
			prepareStatement.setString(3, oldPassword);
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
		String query = "SELECT * FROM Accounts WHERE UserId = ? ";
		Map<Integer,AccountsInfo> myAccounts = new HashMap<>();
		Connection connection = getConnection();
		try(PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			preparedStatement.setInt(1, userId);
			boolean rescode = preparedStatement.execute();
			if(rescode)
			{
				try(ResultSet result = preparedStatement.getResultSet())
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
						accountsInfo.setAtmPin(result.getInt(8));
						myAccounts.put((int) accountsInfo.getAccountNumber(), accountsInfo);
					}
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
//		String query = "INSERT INTO Info () VALUES(?,?,?,?,?,?,?,?,?) ;";
		String query = "INSERT INTO Info (Password,Name,Email,Phone,Aadhar,Role,City,Status,AdminAccess) VALUES(?,?,?,?,?,?,?,?,?)";
		Connection connection = getConnection();
		try(PreparedStatement preparedStatement = connection.prepareStatement(query)) 
		{
			//preparedStatement.setInt(1, userInfo.getUserId());
			preparedStatement.setString(1, userInfo.getPassword());
			preparedStatement.setString(2, userInfo.getName());
			preparedStatement.setString(3, userInfo.getEmail());
			preparedStatement.setLong(4, userInfo.getPhone());
			preparedStatement.setLong(5, userInfo.getAadhar());
			preparedStatement.setString(6, userInfo.getRole());
			preparedStatement.setString(7, userInfo.getCity());
			preparedStatement.setString(8, userInfo.getStatus());
			preparedStatement.setString(9, userInfo.getAdminAccess());
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
		changeProfileInfo(userId, "Status", Credence.Status.INACTIVE.getStatus());
	}
	@Override
	public void createAccount(Integer userId, AccountsInfo accountsInfo) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountsInfo);
		String query = "INSERT INTO Accounts VALUES(?,?,?,?,?,?,?,?) ;";
//		String query = "INSERT INTO Accounts (AccountNumber,Ifsc,Branch,Type,Status,Balance,Atmpin) VALUES(?,?,?,?,?,?,?)";
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
			preparedStatement.execute();
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
		String query = "UPDATE Accounts SET Status = ? WHERE AccountNumber = ? AND UserId = ? ;";
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			preparedStatement.setString(1, Credence.Status.INACTIVE.getStatus());
			preparedStatement.setLong(2, accountsInfo.getAccountNumber());
			preparedStatement.setInt(3, accountsInfo.getUserId());
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
		String query = "INSERT INTO Info (Password,Name,Email,Phone,Aadhar,Role,City,Status,AdminAccess) VALUES(?,?,?,?,?,?,?,?,?)";
		Connection connection = getConnection();
		try(PreparedStatement dumpUserInfoQuery = connection.prepareStatement(query)) 
		{

			for(UserInfo user: userInfo)
			{
//				dumpUserInfoQuery.setInt(1, user.getUserId());
				dumpUserInfoQuery.setString(1, user.getPassword());
				dumpUserInfoQuery.setString(2, user.getName());
				dumpUserInfoQuery.setString(3, user.getEmail());
				dumpUserInfoQuery.setLong(4, user.getPhone());
				dumpUserInfoQuery.setLong(5, user.getAadhar());
				dumpUserInfoQuery.setString(6, user.getRole());
				dumpUserInfoQuery.setString(7, user.getCity());
				dumpUserInfoQuery.setString(8, user.getStatus());
				dumpUserInfoQuery.setString(9, user.getAdminAccess());
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
//		String query = "INSERT INTO Accounts (AccountNumber,Ifsc,Branch,Type,Status,Balance,Atmpin) VALUES(?,?,?,?,?,?,?)";
		String query = "INSERT INTO Accounts VALUES(?,?,?,?,?,?,?,?) ;";
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
//		String addTransactionQuery = "INSERT INTO Transaction (UserId,Sender,Receiver,Amount,Time,Status) VALUES(?,?,?,?,?,?)";
		String addTransactionQuery = "INSERT INTO Transaction (UserId,Sender,Receiver,Amount,Time,Status,Type) VALUES(?,?,?,?,?,?,?);";
		Connection connection = getConnection();
		try(PreparedStatement addTransaction = connection.prepareStatement(addTransactionQuery))
		{
//			addTransaction.setInt(1, transactionInfo.getTransactionId());
			addTransaction.setInt(1, transactionInfo.getUserId());
			addTransaction.setLong(2, transactionInfo.getSenderAccountNumber());
			addTransaction.setLong(3, transactionInfo.getReceiverAccountNumber());
			addTransaction.setDouble(4,transactionInfo.getAmount());
			addTransaction.setLong(5, transactionInfo.getTime());
			addTransaction.setString(6, transactionInfo.getStatus());
			addTransaction.setString(7,transactionInfo.getType());
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
		String getTransactionQuery = "SELECT * FROM Transaction WHERE Id = ?";
		Connection connection = getConnection();
		TransactionInfo myTransaction = new TransactionInfo();
		try(PreparedStatement getTransaction = connection.prepareStatement(getTransactionQuery))
		{
			getTransaction.setInt(1, transactionId);
			boolean rescode = getTransaction.execute();
			try(ResultSet result = getTransaction.getResultSet())
			{
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
					myTransaction.setType(result.getString(8));
				}
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
		try(PreparedStatement getAllTransaction = connection.prepareStatement(getAllTransactionQuery))
		{
			boolean rescode = getAllTransaction.execute();
			if(rescode)
			{
				try(ResultSet result = getAllTransaction.getResultSet())
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
						transactionInfo.setType(result.getString(8));
						transactions.put(transactionInfo.getTransactionId(), transactionInfo);
					}
				}
			}
		}
		catch (SQLException e) 
		{
			throw new BMException("Failed To get all Transactions",e);
		}
		finally
		{
			closeConnection(connection);
		}
		return transactions;
	}
	@Override
	public Map<?, ?> getAllPendingTransaction() throws BMException 
	{
		String getAllPendingTransactionQuery = "SELECT * from Transaction WHERE Status = ?;";
		Map<Integer,TransactionInfo> transactions = new HashMap<>();
		Connection connection = getConnection();
		try(PreparedStatement getAllPendingTransaction = connection.prepareStatement(getAllPendingTransactionQuery))
		{
			getAllPendingTransaction.setString(1, "Pending");
			boolean rescode = getAllPendingTransaction.execute();
			if(rescode)
			{
				try(ResultSet result = getAllPendingTransaction.getResultSet())
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
						transactionInfo.setType(result.getString(8));
						transactions.put(transactionInfo.getTransactionId(), transactionInfo);
					}
				}
			}
		}
		catch (SQLException e) 
		{
			throw new BMException("Failed To get all Transactions",e);
		}
		finally
		{
			closeConnection(connection);
		}
		return transactions;
	}
	private void transactionStatus(String status,Integer transactionId) throws BMException 
	{
		Utilities.INST.isNull(status);
		Utilities.INST.isNull(transactionId);
		String grantApproveQuery = "UPDATE Transaction SET Status= ? WHERE Id = ? ;";
		Connection connection = getConnection();
		try(PreparedStatement grantApprove = connection.prepareStatement(grantApproveQuery))
		{
			grantApprove.setString(1, status);
			grantApprove.setInt(2, transactionId);
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
	@Override
	public void grantApproval(Integer transactionId) throws BMException 
	{
		transactionStatus("Approved", transactionId);
	}
	@Override
	public void rejectTransaction(Integer transactionId) throws BMException 
	{
		transactionStatus("Rejected", transactionId);
	}
	@Override
	public void reActivateUser(Integer userId) throws BMException 
	{
		Utilities.INST.isNull(userId);
		String query = "UPDATE Info SET Status = ? WHERE UserId = ?;";
		Connection connection = getConnection();
		try(PreparedStatement reActiveQuery = connection.prepareStatement(query))
		{
			reActiveQuery.setString(1, Credence.Status.APPROVED.getStatus());
			reActiveQuery.setInt(2, userId);
			
		} catch (SQLException e) 
		{
			throw new BMException("Failed to reactive user",e);
		}
		finally
		{
			closeConnection(connection);
		}
	}
	@Override
	public void reActivateAccount(Integer accountNumber) throws BMException 
	{
		Utilities.INST.isNull(accountNumber);
		String query = "UPDATE Accounts SET Status = ? WHERE AccountNumber = ?;";
		Connection connection = getConnection();
		try(PreparedStatement reActiveQuery = connection.prepareStatement(query))
		{
			reActiveQuery.setString(1, Credence.Status.APPROVED.getStatus());
			reActiveQuery.setInt(2, accountNumber);
			
		} catch (SQLException e) 
		{
			throw new BMException("Failed to reactive user Credence Account",e);
		}
		finally
		{
			closeConnection(connection);
		}
	}
	@Override
	public void reActivateUserRequest(Integer userId) throws BMException 
	{
		Utilities.INST.isNull(userId);
		String reActiveUserRequestQuery = "INSERT INTO Request (UserId,Type,Status) VALUES (?,?,?)";
		Connection connection = getConnection();
		try(PreparedStatement reActivateUser = connection.prepareStatement(reActiveUserRequestQuery))
		{
			reActivateUser.setInt(1, userId);
			reActivateUser.setString(2, Credence.RequestType.REACTIVATEUSER.getRequest());
			reActivateUser.setString(3, Credence.Status.PENDING.getStatus());
			reActivateUser.execute();
			
		} 
		catch (SQLException e) 
		{
			throw new BMException("Failed to Request reactive user",e);
		}
		finally
		{
			closeConnection(connection);
		}
		
	}
	@Override
	public void reActivateAccountRequest(Integer userId,Integer accountNumber) throws BMException 
	{
		
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(userId);
		String reActiveAccountRequest = "INSERT INTO Request (UserId,Type,Status,AccountNumber) VALUES (?,?,?,?)";
		Connection connection = getConnection();
		try(PreparedStatement reActivateAccountRequest = connection.prepareStatement(reActiveAccountRequest))
		{
			reActivateAccountRequest.setInt(1, userId);
			reActivateAccountRequest.setString(2, Credence.RequestType.REACTIVATEACCOUNT.getRequest());
			reActivateAccountRequest.setString(3, Credence.Status.PENDING.getStatus());
			reActivateAccountRequest.setInt(4, accountNumber);
			reActivateAccountRequest.execute();
			
		} 
		catch (SQLException e) 
		{
			throw new BMException("Failed to Request reactive Credence Account",e);
		}
		finally
		{
			closeConnection(connection);
		}
	}
	@Override
	public Map<?,?> getAllUserTransaction(Integer userId) throws BMException 
	{
		Utilities.INST.isNull(userId);
		String getAllUserTransaction = "SELECT * FROM Transaction WHERE UserId = ?";
		Connection connection = getConnection();
		Map<Integer,TransactionInfo> userTransaction = new HashMap<>();
		try(PreparedStatement getAllUserTransactionRequest = connection.prepareStatement(getAllUserTransaction))
		{
			getAllUserTransactionRequest.setInt(1, userId);
			boolean rescode = getAllUserTransactionRequest.execute();
			if(rescode)
			{
				try(ResultSet result = getAllUserTransactionRequest.getResultSet())
				{
					while(result.next())
					{
						TransactionInfo transaction = new TransactionInfo();
						transaction.setTransactionId(result.getInt(1));
						transaction.setUserId(result.getInt(2));
						transaction.setSenderAccountNumber(result.getInt(3));
						transaction.setReceiverAccountNumber(result.getInt(4));
						transaction.setAmount(result.getDouble(5));
						transaction.setTime(result.getLong(6));
						transaction.setStatus(result.getString(7));
						transaction.setType(result.getString(8));
						userTransaction.put(transaction.getTransactionId(), transaction);
					}
				}
			}
			return  userTransaction;
		} 
		catch (SQLException e) 
		{
			throw new BMException("Failed to get all Transaction ",e);
		}
		finally
		{
			closeConnection(connection);
		}
	}
	@Override
	public Map<?, ?> getAllRequest() throws BMException 
	{
		String getAllRequestQuery = "SELECT * FROM Request";
		Connection connection = getConnection();
		Map<Integer,RequestInfo> allRequests = new HashMap<>();
		try(PreparedStatement getAllRequest = connection.prepareStatement(getAllRequestQuery))
		{
			boolean rescode = getAllRequest.execute();
			if(rescode)
			{
				try(ResultSet result = getAllRequest.getResultSet())
				{
					while(result.next())
					{
						RequestInfo request = new RequestInfo();
						request.setId(result.getInt(1));
						request.setUserId(result.getInt(2));
						request.setType(result.getString(3));
						request.setStatus(result.getString(4));
						request.setAccountNumber(result.getInt(5));
						allRequests.put(request.getId(), request);
					}
				}
			}
			return  allRequests;
		} 
		catch (SQLException e) 
		{
			throw new BMException("Failed to get All requests",e);
		}
		finally
		{
			closeConnection(connection);
		}
	}
	@Override
	public void setRequestFlag(Credence.Status status,Integer requestId) throws BMException 
	{
		
		String setRequestFlag = "UPDATE Request SET Status = ? WHERE Id =?";
		Connection connection = getConnection();
		try(PreparedStatement setRequestFlagStatement = connection.prepareStatement(setRequestFlag))
		{
			setRequestFlagStatement.setString(1, status.getStatus());
			setRequestFlagStatement.setInt(2, requestId);
			setRequestFlagStatement.execute();
		} 
		catch (SQLException e) 
		{
			throw new BMException("Failed to set Status At Request",e);
		}
		finally
		{
			closeConnection(connection);
		}
	}
	

}
