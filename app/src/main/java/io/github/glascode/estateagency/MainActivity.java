package io.github.glascode.estateagency;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.util.Log;

import java.io.IOException;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

	private String jsonArrayString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		makeRequest("https://ensweb.users.info.unicaen.fr/android-estate/mock-api/dernieres.json");
	}

	public void launchPropertyActivity(View view) {
		// TODO: Pick random element from the JSONArray
		startActivity(new Intent(this, PropertyActivity.class));
	}

	public void launchPropertyListActivity(View view) {
		Intent intent = new Intent(this, PropertyListActivity.class);
		intent.putExtra("json_property_list", jsonArrayString);
		startActivity(intent);
	}

	private void makeRequest(String url) {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url(url).build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				try {
					ResponseBody responseBody = response.body();

					JSONObject jsonObject = new JSONObject(responseBody.string());
					JSONArray jsonArray = new JSONArray(jsonObject.getString("response"));

					jsonArrayString = jsonArray.toString();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
