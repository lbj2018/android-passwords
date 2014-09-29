package com.derek.dpasswords;

import android.app.Activity;
import android.os.Bundle;

public class AccountListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_list);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new AccountListFragment()).commit();
		}
	}
}
