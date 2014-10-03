package com.derek.dpasswords;

import java.util.Date;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.derek.dpasswords.model.AES;
import com.derek.dpasswords.model.Account;
import com.derek.dpasswords.model.DateFormatUtil;
import com.derek.dpasswords.model.PasswordsStore;
import com.derek.dpasswords.model.User;
import com.derek.dpasswords.model.WebServices;

public class AccountFragment extends Fragment {
	private EditText accountNameEditText;
	private EditText usernameEditText;
	private EditText passwordEditText;
	private Account account;
	private boolean isEditing;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		isEditing = false;

		String accountId = getArguments().getString("account_id");
		this.account = PasswordsStore.get(getActivity()).getAccount(accountId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_account, container, false);

		this.accountNameEditText = (EditText) rootView.findViewById(R.id.edit_text_account_account_name);
		this.usernameEditText = (EditText) rootView.findViewById(R.id.edit_text_account_username);
		this.passwordEditText = (EditText) rootView.findViewById(R.id.edit_text_account_password);

		updateUI();

		this.accountNameEditText.setText(this.account.getAccountName());
		this.usernameEditText.setText(this.account.getUsername());

		User user = PasswordsStore.get(getActivity()).getUser();
		if (user != null) {
			String password;
			try {
				AES gAes = new AES(user.getPassword());
				password = gAes.decrypt(this.account.getEncryptedPassword());
				this.passwordEditText.setText(password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		if (isEditing) {
			inflater.inflate(R.menu.fragment_account_edit, menu);
		} else {
			inflater.inflate(R.menu.fragment_account, menu);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_item_account_edit) {
			isEditing = true;
			updateUI();
			getActivity().invalidateOptionsMenu();
			return true;
		} else if (id == R.id.menu_item_account_cancel) {
			isEditing = false;
			updateUI();
			getActivity().invalidateOptionsMenu();

		} else if (id == R.id.menu_item_account_save) {
			isEditing = false;
			updateUI();
			getActivity().invalidateOptionsMenu();

			// change account
			String accountName = accountNameEditText.getText().toString();
			String username = usernameEditText.getText().toString();
			String password = passwordEditText.getText().toString();

			if (check(accountName, username, password)) {

				User user = PasswordsStore.get(getActivity()).getUser();
				if (user != null) {
					String accoungId = account.getAccountId();
					AES aes = new AES(user.getPassword());
					String encryptedPassword = aes.encrypt(password);
					new changeAccountTask().execute(accoungId, accountName, username, encryptedPassword);
				}
			}
		}
		return super.onOptionsItemSelected(item);
	}

	private boolean check(String accountName, String username, String password) {
		if (accountName == null || accountName.length() == 0) {
			Toast.makeText(getActivity(), "Account name cannot be empty", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (username == null || username.length() == 0) {
			Toast.makeText(getActivity(), "Username name cannot be empty", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (password == null || password.length() == 0) {
			Toast.makeText(getActivity(), "Password name cannot be empty", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private void updateUI() {
		this.accountNameEditText.setEnabled(isEditing);
		this.usernameEditText.setEnabled(isEditing);
		this.passwordEditText.setEnabled(isEditing);
	}

	private class changeAccountTask extends AsyncTask<String, Void, String> {
		private String accountId;
		private String accountName;
		private String username;
		private String password;

		@Override
		protected String doInBackground(String... params) {
			if (params.length == 4) {
				this.accountId = params[0];
				this.accountName = params[1];
				this.username = params[2];
				this.password = params[3];

				User user = PasswordsStore.get(getActivity()).getUser();

				return WebServices.changeAccount(this.accountId, this.accountName, this.username, this.password,
						user.getUserId());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null || result.equals("0")) {
				Toast.makeText(getActivity(), "Fai to change account", Toast.LENGTH_SHORT).show();
			} else {
				Date date = DateFormatUtil.getDateFromDateString(result);
				PasswordsStore.get(getActivity()).addAccount(accountId, accountName, username, password, date);
				getActivity().finish();
			}
		}
	}
}
