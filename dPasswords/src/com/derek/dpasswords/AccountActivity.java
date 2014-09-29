package com.derek.dpasswords;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AccountActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);

		Intent intent = getIntent();
		String accountId = intent.getStringExtra("account_id");

		Bundle args = new Bundle();
		args.putString("account_id", accountId);
		if (savedInstanceState == null) {
			AccountFragment fragment = new AccountFragment();
			fragment.setArguments(args);
			getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
		}
	}
}
