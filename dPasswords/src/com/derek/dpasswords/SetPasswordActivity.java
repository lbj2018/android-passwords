package com.derek.dpasswords;

import android.app.Activity;
import android.os.Bundle;

public class SetPasswordActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_password);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new SetPasswordFragment()).commit();
		}
	}
}
