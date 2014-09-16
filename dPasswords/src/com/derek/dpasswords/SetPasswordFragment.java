package com.derek.dpasswords;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.derek.dpasswords.model.AccountStore;
import com.derek.dpasswords.model.StringUtil;

public class SetPasswordFragment extends Fragment {
	private EditText mPasswordEditText;
	private EditText mConfirmPasswordEditText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_set_password, container, false);

		mPasswordEditText = (EditText) rootView.findViewById(R.id.edit_text_set_password_password);
		mConfirmPasswordEditText = (EditText) rootView.findViewById(R.id.edit_text_set_password_confirm_password);

		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_set_password, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_item_set_password_done) {
			String password = mPasswordEditText.getText().toString();
			String confirmPassword = mConfirmPasswordEditText.getText().toString();

			if (check(password, confirmPassword)) {
				String md5Password = StringUtil.md5(password);

				// save md5 password into Share Preference
				SharedPreferences preferences = getActivity().getSharedPreferences(AccountStore.PREFS_NAME,
						Context.MODE_PRIVATE);
				preferences.edit().putString(AccountStore.PASSWORD_KEY, md5Password).commit();

				getActivity().finish();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private boolean check(String password, String confirmPassword) {
		if (password == null || password.length() == 0) {
			Toast.makeText(getActivity(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (confirmPassword == null || !password.equals(confirmPassword)) {
			Toast.makeText(getActivity(), "Passwords don't match", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
}
