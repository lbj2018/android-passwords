package com.derek.dpasswords;

import java.util.Date;
import java.util.UUID;

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
import com.derek.dpasswords.model.DateFormatUtil;
import com.derek.dpasswords.model.PasswordsStore;
import com.derek.dpasswords.model.User;
import com.derek.dpasswords.model.WebServices;

public class AddAccountFragment extends Fragment {
	private EditText mAccountNameEditText;
	private EditText mUsernameEditText;
	private EditText mPasswordEditText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_add_account, container, false);

		mAccountNameEditText = (EditText) rootView.findViewById(R.id.edit_text_add_account_account_name);
		mUsernameEditText = (EditText) rootView.findViewById(R.id.edit_text_add_account_username);
		mPasswordEditText = (EditText) rootView.findViewById(R.id.edit_text_add_account_password);

		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_add_account, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_item_add_account_save) {
			String accountName = mAccountNameEditText.getText().toString();
			String username = mUsernameEditText.getText().toString();
			String password = mPasswordEditText.getText().toString();

			if (check(accountName, username, password)) {

				User user = PasswordsStore.get(getActivity()).getUser();
				if (user != null) {
					String accoungId = UUID.randomUUID().toString();
					AES aes = new AES(user.getPassword());
					String encryptedPassword = aes.encrypt(password);
					new addAccountTask().execute(accoungId, accountName, username, encryptedPassword);
				}
			}
			return true;
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

	private class addAccountTask extends AsyncTask<String, Void, String> {
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

				return WebServices.addAccount(this.accountId, this.accountName, this.username, this.password,
						user.getUserId());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null || result.equals("0")) {
				Toast.makeText(getActivity(), "Fai to add account", Toast.LENGTH_SHORT).show();
			} else {
				Date date = DateFormatUtil.getDateFromDateString(result);
				PasswordsStore.get(getActivity()).addAccount(accountId, accountName, username, password, date);
				getActivity().finish();
			}
		}
	}
}
