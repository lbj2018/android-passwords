package com.derek.dpasswords;

import android.app.Activity;
import android.os.Bundle;

public class SignUpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new SignUpFragment()).commit();
		}
	}
}
