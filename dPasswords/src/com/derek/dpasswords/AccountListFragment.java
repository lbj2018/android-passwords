package com.derek.dpasswords;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.derek.dpasswords.model.Account;
import com.derek.dpasswords.model.AccountStore;
import com.derek.dpasswords.model.User;
import com.derek.dpasswords.model.WebServices;

public class AccountListFragment extends Fragment {
	private ListView mListView;
	private ArrayList<Account> mAccounts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		new LoadAccountsTask().execute();

		mAccounts = AccountStore.get(getActivity()).getAllAccounts();
	}

	@Override
	public void onStart() {
		super.onStart();
		reloadListView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_account_list, container, false);

		mListView = (ListView) rootView.findViewById(R.id.list_view_account_list);

		AccountsAdapter adapter = new AccountsAdapter(mAccounts);
		mListView.setAdapter(adapter);

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				Account account = mAccounts.get(position);
				Intent intent = new Intent(getActivity(), AccountActivity.class);
				intent.putExtra("account_id", account.getAccountId());
				startActivity(intent);
			}
		});

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

	public void reloadListView() {
		if (mListView != null) {
			AccountsAdapter adapter = (AccountsAdapter) mListView.getAdapter();
			adapter.notifyDataSetChanged();
		}
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

	private class LoadAccountsTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			User user = AccountStore.get(getActivity()).getUser();
			if (user != null) {
				return WebServices.loadAccounts(user.getUserId());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null || result.equals("0")) {
				Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_SHORT).show();
			} else {
				try {
					JSONArray jsonArray = new JSONArray(result);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject json = jsonArray.getJSONObject(i);
						String accountId = json.getString("account_id");
						String accountName = json.getString("account_name");
						String username = json.getString("user_name");
						String password = json.getString("password");
						String dateCreatedString = json.getString("date_created");
						Date dateCreated = AccountStore.getDateFromDateString(dateCreatedString);

						AccountStore.get(getActivity()).addAccount(accountId, accountName, username, password,
								dateCreated);
					}
					reloadListView();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
