package io.github.glascode.estateagency;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.squareup.moshi.Types;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void launchPropertyActivity(View view) {
		String url = "https://ensweb.users.info.unicaen.fr/android-estate/mock-api/immobilier.json";
		makeHttpRequest(url);

		Intent intent = new Intent(this, PropertyActivity.class);
		intent.putExtra("propertyTitle", "Maison 250m2 proche Caen");
		intent.putExtra("propertyPrice", 350000);
		intent.putExtra("propertyLocation", "Caen");
		intent.putExtra("propertyDescription", "Maison traditionnelle");
		intent.putExtra("propertyPublicationDate", "12 january 2019");
		intent.putExtra("propertySellerName", "CarlImmo");
		intent.putExtra("propertySellerMail", "carl@immo.com");
		intent.putExtra("propertySellerNumber", "06.56.87.51.89");

		startActivity(intent);
	}

	public void launchPropertiesListActivity(View view) {
		String url = "https://ensweb.users.info.unicaen.fr/android-estate/mock-api/liste.json";

	}

	private void makeHttpRequest(String url) {
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

					Headers responseHeaders = response.headers();
					for (int i = 0, size = responseHeaders.size(); i < size; i++) {
						Log.i("JML", responseHeaders.name(i) + ": "
								+ responseHeaders.value(i));
					}

					Property property = makePropertyFromJson(response.body().string());
//					List<Property> properties = makePropertiesFromJson(response.body().string());

				}
			}
		});
	}

	private Property makePropertyFromJson(String jsonResponse) {
		Moshi moshi = new Moshi.Builder().build();  // create Moshi

		// Create the adapter for Property
		Property property = null;
		PropertyResponse propertyResponse;
		JsonAdapter<PropertyResponse> jsonAdapter = moshi.adapter(PropertyResponse.class);

		try {
			Log.d("Execution", "Adapting the property from JSON");
			propertyResponse = jsonAdapter.fromJson(jsonResponse);
			property = propertyResponse.getResponse();
			Log.d("Execution", "Adapted!");
			Log.d("Execution", "Property:\n" + property);
		} catch (IOException e) {
			Log.i("JML", "Erreur I/O");
		}

		return property;
	}

	public List<Property> makePropertiesFromJson(String jsonResponse) {

		// Add the PropertiesJsonAdapter to the created Moshi
		Moshi moshi = new Moshi.Builder().build();

//		PropertiesJsonAdapter adapter1 = new PropertiesJsonAdapter();

		Type type = Types.newParameterizedType(List.class, Property.class);
		JsonAdapter<List<Property>> adapter = moshi.adapter(type);

		try {
			Log.d("Execution", "List<Property> properties = adapter.fromJson(jsonResponse);");

			List<Property> properties = adapter.fromJson(jsonResponse);

			Log.d("Execution", "Executed!");
			Log.d("Size", properties.size() + " size");
			System.out.println("Size: " + properties.size());

			for (int i = 0; i < properties.size(); i++) {
				Log.d("Property", properties.get(i).toString());
			}

			return properties;
		} catch (IOException e) {
			Log.i("JML", "Error I/O");
		}

		return null;
	}

}
