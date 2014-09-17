package com.derek.dpasswords;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.derek.dpasswords.model.Account;
import com.derek.dpasswords.model.AccountStore;

public class AccountListFragment extends Fragment {
	private ListView mListView;
	private ArrayList<Account> mAccounts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		mAccounts = AccountStore.get(getActivity()).getAllAccounts();
	}

	@Override
	public void onStart() {
		super.onStart();
		if (mListView != null) {
			AccountsAdapter adapter = (AccountsAdapter) mListView.getAdapter();
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_account_list, container, false);

		mListView = (ListView) rootView.findViewById(R.id.list_view_account_list);

		AccountsAdapter adapter = new AccountsAdapter(mAccounts);
		mListView.setAdapter(adapter);

		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_account_list, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_item_account_list_add) {
			Intent intent = new Intent(getActivity(), AddAccountActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class AccountsAdapter extends ArrayAdapter<Account> {
		public AccountsAdapter(ArrayList<Account> accounts) {
			super(getActivity(), 0, accounts);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_accounts, null, false);
			}

			Account account = getItem(position);

			TextView accountNameTextView = (TextView) convertView
					.findViewById(R.id.text_view_acccount_list_acccount_name);
			TextView usernameTextView = (TextView) convertView
					.findViewById(R.id.text_view_acccount_list_acccount_username);
			TextView passwordTextView = (TextView) convertView
					.findViewById(R.id.text_view_acccount_list_acccount_password);

			accountNameTextView.setText(account.getAccountName());
			usernameTextView.setText(account.getUsername());
			passwordTextView.setText("**************");

			return convertView;
		}
	}
}
