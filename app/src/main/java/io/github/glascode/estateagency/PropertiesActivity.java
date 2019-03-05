package io.github.glascode.estateagency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class PropertiesActivity extends AppCompatActivity {

	private List<Property> properties;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_properties);

		makeRequest("https://ensweb.users.info.unicaen.fr/android-estate/mock-api/dernieres.json");
	}

	private void updateUI() {
		for (Property property : properties) {
			Log.d("Execution", "----- Property: " + property);
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

					// TODO: Test with PropertiesResponse.class instead of Property.class
					Type type = Types.newParameterizedType(List.class, Property.class);
					JsonAdapter<List<Property>> jsonAdapter = moshi.adapter(type);

					Log.d("Execution", "Adapting response from JSON");

					properties = jsonAdapter.fromJson(responseBody.string());
					Log.d("Size", properties.size() + " size");

					for (int i = 0; i < properties.size(); i++) {
						Log.d("Property", properties.get(i).toString());
					}

					Log.d("Execution", "Adapted.");

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
}
