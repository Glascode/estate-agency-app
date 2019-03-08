package io.github.glascode.estateagency;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private List<Property> propertyList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		makeRequest("https://ensweb.users.info.unicaen.fr/android-estate/mock-api/dernieres.json");
	}

	public void launchPropertyActivity(View view) {
		startActivity(new Intent(this, PropertyActivity.class));
	}

	public void launchPropertiesActivity(View view) {
		startActivity(new Intent(this, PropertyListActivity.class));
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
					if (!response.isSuccessful())
						throw new IOException("Unexpected HTTP code " + response);

					Moshi moshi = new Moshi.Builder().build();

					JsonAdapter<PropertiesResponse> adapter = moshi.adapter(PropertiesResponse.class);

					PropertiesResponse propertiesResponse = adapter.fromJson(responseBody.string());

					propertyList = propertiesResponse.getResponse();

					for (Property property : propertyList)
						property.setDate(property.getDate() * 1000);
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
