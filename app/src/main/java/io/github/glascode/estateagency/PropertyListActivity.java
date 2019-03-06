package io.github.glascode.estateagency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class PropertyListActivity extends AppCompatActivity {

	private List<Property> propertyList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_property_list);

		makeRequest("https://ensweb.users.info.unicaen.fr/android-estate/mock-api/dernieres.json");
	}

	private void updateUI() {
		for (Property property : propertyList) {
			Log.d("UpdateUI", "Property:\n" + property);
		}
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
				try (ResponseBody responseBody = response.body()) {
					if (!response.isSuccessful()) {
						throw new IOException("Unexpected HTTP code " + response);
					}

					Moshi moshi = new Moshi.Builder().build();

					JsonAdapter<PropertiesResponse> adapter = moshi.adapter(PropertiesResponse.class);

					PropertiesResponse propertiesResponse = adapter.fromJson(responseBody.string());
					propertyList = propertiesResponse.getResponse();

					// Update UI
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							updateUI();
						}
					});
				}
			}
		});
	}

	public static class PropertiesResponse {

		public List<Property> response;

		public List<Property> getResponse() {
			return response;
		}

	}

}
