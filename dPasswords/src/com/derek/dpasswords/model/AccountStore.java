package com.derek.dpasswords.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.Context;

public class AccountStore {
	public static final String PASSWORD_KEY = "com.derek.dpasswords.model.PASSWORD_KEY";
	public static final String PREFS_NAME = "PasswordsPrefsFile";
	private static AccountStore sAccountStore;
	private DatabaseHelper mDBHelper;
	private Context mAppContext;
	private ArrayList<Account> mAccounts;
	private User user;

	private AccountStore(Context appContext) {
		mAppContext = appContext;

		mDBHelper = new DatabaseHelper(mAppContext);

		mAccounts = mDBHelper.queryAccounts();
		if (mAccounts == null)
			mAccounts = new ArrayList<Account>();
	}

	public static AccountStore get(Context context) {
		if (sAccountStore == null) {
			sAccountStore = new AccountStore(context.getApplicationContext());
		}
		return sAccountStore;
	}

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

	public ArrayList<Account> getAllAccounts() {
		return mAccounts;
	}

	public void addAccount(String accountId, String accountName, String username, String encryptedPassword, Date date) {
		if (!isExistForAccountId(accountId)) {
			Account account = new Account();
			account.setAccountId(accountId);
			account.setAccountName(accountName);
			account.setUsername(username);
			account.setEncryptedPassword(encryptedPassword);
			account.setDateCreated(date);

			mDBHelper.insertAccount(account);

			mAccounts.add(0, account);
		}
	}

	public void updateAccount(String accountId, String accountName, String username, String encryptedPassword) {
		if (!isExistForAccountId(accountId)) {
			// Account account = new Account();
			// account.setAccountId(accountId);
			// account.setAccountName(accountName);
			// account.setUsername(username);
			// account.setEncryptedPassword(encryptedPassword);
			// account.setDateCreated(date);
			//
			// mDBHelper.insertAccount(account);
			//
			// mAccounts.add(0, account);
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static Date getDateFromDateString(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getDateStringFromDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		String dateString = sdf.format(date);

		return dateString;
	}
}
