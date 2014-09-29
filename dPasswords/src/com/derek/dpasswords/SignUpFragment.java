package com.derek.dpasswords;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.derek.dpasswords.model.WebServices;

public class SignUpFragment extends Fragment {
	private EditText usernameEditText;
	private EditText passwordEditText;
	private EditText confirmPasswordEditText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

		this.usernameEditText = (EditText) rootView.findViewById(R.id.edit_text_sign_up_username);
		this.passwordEditText = (EditText) rootView.findViewById(R.id.edit_text_sign_up_password);
		this.confirmPasswordEditText = (EditText) rootView.findViewById(R.id.edit_text_sign_up_confirm_password);

		this.usernameEditText.setText("derekzhou2");
		this.passwordEditText.setText("654321");
		this.confirmPasswordEditText.setText("654321");

		Button signUpButton = (Button) rootView.findViewById(R.id.button_sign_up);
		signUpButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = usernameEditText.getText().toString();
				String password = passwordEditText.getText().toString();
				String confirmPassword = confirmPasswordEditText.getText().toString();

				if (check(username, password, confirmPassword)) {
					new SignUpTask().execute(username, password);
				}
			}
		});

		return rootView;
	}

	private boolean check(String username, String password, String confirmPassword) {
		if (username == null || username.length() == 0) {
			Toast.makeText(getActivity(), "Username cannot be empty", Toast.LENGTH_SHORT).show();
			return false;
		}

		if (password == null || password.length() == 0) {
			Toast.makeText(getActivity(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
			return false;
		}

		if (confirmPassword == null || !confirmPassword.equals(password)) {
			Toast.makeText(getActivity(), "Passwords don't match", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private class SignUpTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			if (params.length == 2) {
				String username = params[0];
				String password = params[1];

				return WebServices.signUp(username, password);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null && result.equals("1")) {
				Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
