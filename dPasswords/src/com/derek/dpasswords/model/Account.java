package com.derek.dpasswords.model;

import java.util.Date;

public class Account {
	private String accountId;
	private String accountName;
	private String username;
	private String encryptedPassword;
	private Date mDateCreated;

	public Account() {
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public Date getDateCreated() {
		return mDateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		mDateCreated = dateCreated;
	}
}
