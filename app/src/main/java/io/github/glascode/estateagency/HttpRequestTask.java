package io.github.glascode.estateagency;

import android.os.AsyncTask;
import android.util.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class HttpRequestTask extends AsyncTask<String, Void, JSONArray> {


	@Override
	protected JSONArray doInBackground(String... urls) {
		JSONArray jsonPropertyListArray = new JSONArray();
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url(urls[0]).build();

		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				throw new IOException("Unexpected code " + response);
			}

			ResponseBody responseBody = response.body();
			JSONObject jsonObject;

			if (responseBody != null) {
				jsonObject = new JSONObject(responseBody.string());
				jsonPropertyListArray = new JSONArray(jsonObject.getString("response"));
			} else {
				throw new NullPointerException("The response body is null.");
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}

		return jsonPropertyListArray;
	}
}
