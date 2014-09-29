package com.derek.dpasswords;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.derek.dpasswords.model.AccountStore;
import com.derek.dpasswords.model.User;
import com.derek.dpasswords.model.WebServices;

public class SignInFragment extends Fragment {
	private EditText usernameEditText;
	private EditText passwordEditText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);

		this.usernameEditText = (EditText) rootView.findViewById(R.id.edit_text_sign_in_username);
		this.passwordEditText = (EditText) rootView.findViewById(R.id.edit_text_sign_in_password);

		this.usernameEditText.setText("derekzhou");
		this.passwordEditText.setText("123456");

		Button signInButton = (Button) rootView.findViewById(R.id.button_sign_in);
		signInButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = usernameEditText.getText().toString();
				String password = passwordEditText.getText().toString();

				if (check(username, password)) {
					new SignInTask().execute(username, password);
				}
			}
		});

		return rootView;
	}

	private boolean check(String username, String password) {
		if (username == null || username.length() == 0) {
			Toast.makeText(getActivity(), "Username cannot be empty", Toast.LENGTH_SHORT).show();
			return false;
		}

		if (password == null || password.length() == 0) {
			Toast.makeText(getActivity(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_sign_in, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_item_login_sign_up) {
			Intent intent = new Intent(getActivity(), SignUpActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class SignInTask extends AsyncTask<String, Void, String> {
		private String username;
		private String password;

		@Override
		protected String doInBackground(String... params) {
			if (params.length == 2) {
				String username = params[0];
				String password = params[1];

				this.username = username;
				this.password = password;

				return WebServices.signIn(username, password);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null && !result.equals("0")) {
				try {
					int userId = Integer.parseInt(result);
					User user = new User();
					user.setUserId(userId);
					user.setUsername(this.username);
					user.setPassword(this.password);

					AccountStore.get(getActivity()).setUser(user);

					Intent intent = new Intent(getActivity(), AccountListActivity.class);
					startActivity(intent);
				} catch (NumberFormatException e) {
					Toast.makeText(getActivity(), "Fail to login", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getActivity(), "Fail to login", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
