package com.derek.dpasswords;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.derek.dpasswords.model.AccountStore;

public class AccountListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_list);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new AccountListFragment()).commit();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

		SharedPreferences preferences = getSharedPreferences(AccountStore.PREFS_NAME, MODE_PRIVATE);
		String md5Password = preferences.getString(AccountStore.PASSWORD_KEY, "");
		if (md5Password == null || md5Password.length() == 0) {
			Intent intent = new Intent(this, SetPasswordActivity.class);
			startActivity(intent);
		}
	}
}
