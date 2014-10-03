package com.derek.dpasswords.model;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PasswordsSQLiteHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "passwords.sqlite";
	private static final int VERSION = 1;

	public static final String TABLE_ACCOUNT = "account";
	public static final String COLUMN_ACCOUNT_ID = "account_id";
	public static final String COLUMN_ACCOUNT_NAME = "account_name";
	public static final String COLUMN_ACCOUNT_USERNAME = "username";
	public static final String COLUMN_ACCOUNT_ENCRYPTED_PASSWORD = "encrypted_password";
	public static final String COLUMN_ACCOUNT_DATE_CREATED = "date_created";

	private static final String DATABASE_CREATE = "create table " + TABLE_ACCOUNT + "(" + COLUMN_ACCOUNT_ID
			+ " varchar(50) primary key, " + COLUMN_ACCOUNT_NAME + " text not null, " + COLUMN_ACCOUNT_USERNAME
			+ " text not null, " + COLUMN_ACCOUNT_ENCRYPTED_PASSWORD + " text not null, " + COLUMN_ACCOUNT_DATE_CREATED
			+ "integer" + ");";

	private SQLiteDatabase mDatabase;

	public PasswordsSQLiteHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

	public void openDataBase() {
		mDatabase = getWritableDatabase();
	}

	public void closeDataBase() {
		if (mDatabase != null)
			mDatabase.close();
	}

	public void createAccount(Account account) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_ACCOUNT_ID, account.getAccountId());
		cv.put(COLUMN_ACCOUNT_NAME, account.getAccountName());
		cv.put(COLUMN_ACCOUNT_USERNAME, account.getUsername());
		cv.put(COLUMN_ACCOUNT_ENCRYPTED_PASSWORD, account.getEncryptedPassword());
		cv.put(COLUMN_ACCOUNT_DATE_CREATED, account.getDateCreated().getTime());

		mDatabase.insert(TABLE_ACCOUNT, null, cv);
	}

	public ArrayList<Account> retrieveAccounts() {
		ArrayList<Account> accounts = new ArrayList<Account>();

		String[] columns = { COLUMN_ACCOUNT_ID, COLUMN_ACCOUNT_NAME, COLUMN_ACCOUNT_USERNAME,
				COLUMN_ACCOUNT_ENCRYPTED_PASSWORD, COLUMN_ACCOUNT_DATE_CREATED };
		Cursor cursor = mDatabase.query(TABLE_ACCOUNT, columns, null, null, null, null, null);

		cursor.moveToFirst();

		cursor.getCount();

		if (cursor.getCount() > 0) {
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
	}

	public void updateAccount(Account account) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_ACCOUNT_NAME, account.getAccountName());
		cv.put(COLUMN_ACCOUNT_USERNAME, account.getUsername());
		cv.put(COLUMN_ACCOUNT_ENCRYPTED_PASSWORD, account.getEncryptedPassword());
		cv.put(COLUMN_ACCOUNT_DATE_CREATED, account.getDateCreated().getTime());

		mDatabase.update(TABLE_ACCOUNT, cv, COLUMN_ACCOUNT_ID + "=" + account.getAccountId(), null);
	}

	public void deleteAccount(Account account) {
		mDatabase.delete(TABLE_ACCOUNT, COLUMN_ACCOUNT_ID + "=" + account.getAccountId(), null);
	}
}
