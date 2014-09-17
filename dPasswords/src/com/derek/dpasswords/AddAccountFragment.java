package com.derek.dpasswords;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.derek.dpasswords.model.AESUtil;
import com.derek.dpasswords.model.AccountStore;

public class AddAccountFragment extends Fragment {
	private EditText mAccountNameEditText;
	private EditText mUsernameEditText;
	private EditText mPasswordEditText;
	private String mPassword;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		mPassword = "369288";
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
				try {
					byte[] encryptedPassword = AESUtil.encrypt(mPassword, password);
					AccountStore.get(getActivity()).addAccount(accountName, username, encryptedPassword);

					getActivity().finish();
				} catch (Exception e) {
					e.printStackTrace();
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
}
