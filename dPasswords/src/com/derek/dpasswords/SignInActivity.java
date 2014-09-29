package com.derek.dpasswords;

import android.app.Activity;
import android.os.Bundle;

public class SignInActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new SignInFragment()).commit();
		}
	}
}
