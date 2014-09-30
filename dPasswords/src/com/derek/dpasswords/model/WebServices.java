package com.derek.dpasswords.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class WebServices {
	private static final String BASE_URL = "http://121.199.0.190:8080/dPasswords/";

	// private static final String BASE_URL =
	// "http://192.168.232.149:8080/dPasswords/";
	// private static final String BASE_URL =
	// "http://192.168.1.2:8080/dPasswords/";

	// private static final String BASE_URL =
	// "http://localhost:8080/dPasswords/";

	public static String signUp(String username, String password) {
		String result = null;

		String uri = BASE_URL + "register";

		String md5Password = MD5Util.md5(password);

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(uri);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("username", username));
		nameValuePairs.add(new BasicNameValuePair("password", md5Password));

		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				String responseString = out.toString();
				result = responseString;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String signIn(String username, String password) {
		String result = null;

		String uri = BASE_URL + "login";

		String md5Password = MD5Util.md5(password);

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(uri);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("username", username));
		nameValuePairs.add(new BasicNameValuePair("password", md5Password));

		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				String responseString = out.toString();
				result = responseString;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String addAccount(String accountId, String accountName, String username, String password, int userId) {
		String result = null;

		String uri = BASE_URL + "insertAccount";

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(uri);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
		nameValuePairs.add(new BasicNameValuePair("user_id", userId + ""));
		nameValuePairs.add(new BasicNameValuePair("account_id", accountId));
		nameValuePairs.add(new BasicNameValuePair("account_name", accountName));
		nameValuePairs.add(new BasicNameValuePair("user_name", username));
		nameValuePairs.add(new BasicNameValuePair("password", password));

		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				String responseString = out.toString();
				result = responseString;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String loadAccounts(int userId) {
		String result = null;

		String uri = BASE_URL + "loadAccounts";

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(uri);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("user_id", userId + ""));

		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				String responseString = out.toString();
				result = responseString;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String deleteAccount(String accountId, int userId) {
		String result = null;

		String uri = BASE_URL + "deleteAccount";

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(uri);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("user_id", userId + ""));
		nameValuePairs.add(new BasicNameValuePair("account_id", accountId));

		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				String responseString = out.toString();
				result = responseString;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
