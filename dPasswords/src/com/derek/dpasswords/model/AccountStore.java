package com.derek.dpasswords.model;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;

public class AccountStore {
	public static final String PASSWORD_KEY = "com.derek.dpasswords.model.PASSWORD_KEY";
	public static final String PREFS_NAME = "PasswordsPrefsFile";
	private static AccountStore sAccountStore;
	private DatabaseHelper mDBHelper;
	private Context mAppContext;
	private ArrayList<Account> mAccounts;

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

	public ArrayList<Account> getAllAccounts() {
		return mAccounts;
	}

	public void addAccount(String accountName, String username, byte[] encryptedPassword) {
		Account account = new Account();
		account.setAccountId("");
		account.setAccountName(accountName);
		account.setUsername(username);
		account.setEncryptedPassword(encryptedPassword);
		account.setDateCreated(new Date());

		mDBHelper.insertAccount(account);

		mAccounts.add(0, account);
	}
}
