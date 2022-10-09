/**
 * 
 */
package com.credence.bank.banking;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author inc5
 *
 */
public enum EnvProperties 
{
	INST;
	public Properties envProps = new Properties();
	private String propsName = "EnvProps.properties";
	private void writingProps()
	{
		envProps.setProperty("url", "jdbc:mysql://localhost:3306/incubationDB");
		envProps.setProperty("username", "root");
		envProps.setProperty("password", "Root@123");
		envProps.setProperty("storage", "BankingDB");
		try 
		{
			OutputStream writeProps = new FileOutputStream(propsName);
			envProps.store(writeProps, "Writing Props");
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void loadProps()
	{
		try 
		{
			InputStream loadProps = new FileInputStream(propsName);
			envProps.load(loadProps);
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
