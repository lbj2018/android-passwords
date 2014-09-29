package com.derek.dpasswords;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.derek.dpasswords.model.AES;
import com.derek.dpasswords.model.Account;
import com.derek.dpasswords.model.AccountStore;
import com.derek.dpasswords.model.User;

public class AccountFragment extends Fragment {
	private TextView accountNameTextView;
	private TextView usernameTextView;
	private TextView passwordTextView;
	private Account account;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String accountId = getArguments().getString("account_id");
		this.account = AccountStore.get(getActivity()).getAccount(accountId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_account, container, false);

		this.accountNameTextView = (TextView) rootView.findViewById(R.id.text_view_account_name);
		this.usernameTextView = (TextView) rootView.findViewById(R.id.text_view_account_username);
		this.passwordTextView = (TextView) rootView.findViewById(R.id.text_view_account_password);

		this.accountNameTextView.setText(this.account.getAccountName());
		this.usernameTextView.setText(this.account.getUsername());

		User user = AccountStore.get(getActivity()).getUser();
		if (user != null) {
			String password;
			try {
				AES gAes = new AES(user.getPassword());
				password = gAes.decrypt(this.account.getEncryptedPassword());
				this.passwordTextView.setText(password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return rootView;
	}
}
