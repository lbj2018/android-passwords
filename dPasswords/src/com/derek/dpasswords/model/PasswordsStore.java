package com.derek.dpasswords.model;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;

public class PasswordsStore {
	public static final String PASSWORD_KEY = "com.derek.dpasswords.model.PASSWORD_KEY";
	public static final String PREFS_NAME = "PasswordsPrefsFile";
	private static PasswordsStore sAccountStore;
	private PasswordsSQLiteHelper mDBHelper;
	private Context mAppContext;
	private ArrayList<Account> mAccounts;
	private User user;

	private PasswordsStore(Context appContext) {
		mAppContext = appContext;

		mDBHelper = new PasswordsSQLiteHelper(mAppContext);

		openDataBase();
		retrieveAccounts();

		if (mAccounts == null)
			mAccounts = new ArrayList<Account>();
	}

	public static PasswordsStore get(Context context) {
		if (sAccountStore == null) {
			sAccountStore = new PasswordsStore(context.getApplicationContext());
		}
		return sAccountStore;
	}

	public void openDataBase() {
		if (mDBHelper != null)
			mDBHelper.openDataBase();
	}

	public void closeDataBase() {
		if (mDBHelper != null)
			mDBHelper.closeDataBase();
	}

	/*
	 * Some methods for data from sqlite and memory
	 */
	public Account addAccount(String accountId, String accountName, String username, String encryptedPassword, Date date) {
		if (!isExistForAccountId(accountId)) {
			Account account = new Account();
			account.setAccountId(accountId);
			account.setAccountName(accountName);
			account.setUsername(username);
			account.setEncryptedPassword(encryptedPassword);
			account.setDateCreated(date);

			mDBHelper.createAccount(account);
			mAccounts.add(0, account);
			return account;
		}
		return null;
	}

	private ArrayList<Account> retrieveAccounts() {
		mAccounts = mDBHelper.retrieveAccounts();
		return mAccounts;
	}

	public boolean changeAccountFromDB(Account account) {
		mDBHelper.updateAccount(account);
		return true;
	}

	public boolean deleteAccountFromDB(Account account) {
		mDBHelper.deleteAccount(account);
		return true;
	}

	/*
	 * Some methods only for memory data
	 */
	public boolean isExistForAccountId(String accountId) {
		return (getAccount(accountId) != null);
	}

	public Account getAccount(String accountId) {
		Account result = null;

		if (accountId == null)
			return result;

		for (Account account : mAccounts) {
			if (account.getAccountId().equals(accountId)) {
				result = account;
				break;
			}
		}

		return result;
	}

	public ArrayList<Account> getAccounts() {
		return mAccounts;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
