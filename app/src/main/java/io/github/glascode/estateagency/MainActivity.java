package io.github.glascode.estateagency;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Date;
import android.util.Log;
import android.view.View;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

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

    public void launchPropertyActivity(View v) {
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

	public void okhttp(View view) {
		makeHttpRequest("https://ensweb.users.info.unicaen.fr/android-estate/mock-api/immobilier.json");
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
						Log.i("JML", responseHeaders.name(i) + ": " + responseHeaders.value(i));
					}

					Snackbar.make(findViewById(R.id.mainLayout), responseBody.string(), Snackbar.LENGTH_LONG).show();

					//Property property1 = makePropertyFromJson(responseBody.string());
				}
			}
		});
	}

	public Property makePropertyFromJson(String jsonString) {
		Moshi moshi = new Moshi.Builder().build();
		JsonAdapter<Property> jsonAdapter = moshi.adapter(Property.class);

		try {
			Property property = jsonAdapter.fromJson(jsonString);
			String message = property.getId() + ", " + property.getTitle() + ", " + property.getDescription();
			Snackbar.make( findViewById(R.id.mainLayout), message, Snackbar.LENGTH_LONG).show();
			return property;
		} catch (IOException e) {
			Log.i("JML", "Erreur I/O");
		}

		return null;
	}

}
