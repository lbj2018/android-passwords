package com.derek.dpasswords.model;

import java.util.Date;

public class Account {
	private String mAccountId;
	private String mAccountName;
	private String mUsername;
	private byte[] mEncryptedPassword;
	private Date mDateCreated;

	public Account() {
	}

	public String getAccountId() {
		return mAccountId;
	}

	public void setAccountId(String accountId) {
		mAccountId = accountId;
	}

	public String getAccountName() {
		return mAccountName;
	}

	public void setAccountName(String accountName) {
		mAccountName = accountName;
	}

	public String getUsername() {
		return mUsername;
	}

	public void setUsername(String username) {
		mUsername = username;
	}

	public byte[] getEncryptedPassword() {
		return mEncryptedPassword;
	}

	public void setEncryptedPassword(byte[] encryptedPassword) {
		mEncryptedPassword = encryptedPassword;
	}

	public Date getDateCreated() {
		return mDateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		mDateCreated = dateCreated;
	}
}
