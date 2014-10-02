package com.derek.dpasswords.model;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "passwords.sqlite";
	private static final int VERSION = 1;

	private static final String TABLE_ACCOUNT = "account";
	private static final String COLUMN_ACCOUNT_ID = "account_id";
	private static final String COLUMN_ACCOUNT_NAME = "account_name";
	private static final String COLUMN_ACCOUNT_USERNAME = "username";
	private static final String COLUMN_ACCOUNT_ENCRYPTED_PASSWORD = "encrypted_password";
	private static final String COLUMN_ACCOUNT_DATE_CREATED = "date_created";

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// create the "account" table
		db.execSQL("create table account (account_id text, account_name text, username text, encrypted_password text, date_created integer)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

	public void insertAccount(Account account) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_ACCOUNT_ID, account.getAccountId());
		cv.put(COLUMN_ACCOUNT_NAME, account.getAccountName());
		cv.put(COLUMN_ACCOUNT_USERNAME, account.getUsername());
		cv.put(COLUMN_ACCOUNT_ENCRYPTED_PASSWORD, account.getEncryptedPassword());
		cv.put(COLUMN_ACCOUNT_DATE_CREATED, account.getDateCreated().getTime());

		db.insert(TABLE_ACCOUNT, null, cv);
		db.close();
	}

	public void updateAccount(Account account) {
		SQLiteDatabase db = getWritableDatabase();

		db.close();
	}

	private AccountCursor queryAccountsForCursor() {
		Cursor wrapped = getReadableDatabase().query(TABLE_ACCOUNT, null, null, null, null, null,
				COLUMN_ACCOUNT_DATE_CREATED + " desc");
		return new AccountCursor(wrapped);
	}

	public ArrayList<Account> queryAccounts() {
		SQLiteDatabase db = getReadableDatabase();

		String selectQuery = "SELECTÊ * FROM " + TABLE_ACCOUNT;
		Cursor cursor = db.rawQuery(selectQuery, null);

		ArrayList<Account> accounts = new ArrayList<Account>();
		if (cursor.moveToFirst()) {
			do {
				Account account = new Account();
				account.setAccountId(cursor.getString(0));
				account.setAccountName(cursor.getString(1));
				account.setUsername(cursor.getString(2));
				account.setEncryptedPassword(cursor.getString(3));
				account.setDateCreated(new Date(cursor.getLong(4)));

				accounts.add(account);
			} while (cursor.moveToNext());
		}

		return accounts;

		// ArrayList<Account> accounts = new ArrayList<Account>();
		//
		//
		// AccountCursor cursor = queryAccountsForCursor();
		//
		// do {
		// Account account = cursor.getAccount();
		// if (account != null)
		// accounts.add(account);
		// } while (cursor.moveToNext());
		//
		// return accounts;
	}

	public static class AccountCursor extends CursorWrapper {

		public AccountCursor(Cursor c) {
			super(c);
		}

		/**
		 * Returns a Run object configured for the current row, or null if the
		 * current row is invalid.
		 */
		public Account getAccount() {
			if (isBeforeFirst() || isAfterLast())
				return null;
			Account account = new Account();
			account.setAccountId(getString(getColumnIndex(COLUMN_ACCOUNT_ID)));
			account.setAccountName(getString(getColumnIndex(COLUMN_ACCOUNT_NAME)));
			account.setUsername(getString(getColumnIndex(COLUMN_ACCOUNT_USERNAME)));
			account.setEncryptedPassword(getString(getColumnIndex(COLUMN_ACCOUNT_ENCRYPTED_PASSWORD)));
			account.setDateCreated(new Date(getLong(getColumnIndex(COLUMN_ACCOUNT_DATE_CREATED))));

			return account;
		}
	}
}
