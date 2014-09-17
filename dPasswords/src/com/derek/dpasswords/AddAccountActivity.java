package com.derek.dpasswords;

import android.app.Activity;
import android.os.Bundle;

public class AddAccountActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_account);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new AddAccountFragment()).commit();
		}
	}
}
