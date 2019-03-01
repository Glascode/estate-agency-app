package io.github.glascode.estateagency;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import android.util.Log;

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

    private Property getRandomProperty() {
    	Seller seller = new Seller("0", "","CarlImmo", "carl@immo.com", "06.56.87.51.89");

    	Property property = new Property("0","Maison 250m2 proche Caen", "Maison traditionnelle", 0, null, 350000, "Caen", 14000, seller, new ArrayList<>(Collections.singletonList("house")), 1547251200000l);

        return property;
    }

    public void launchRandomPropertyActivity(View v) {
		Intent intent = new Intent(this, PropertyActivity.class);

		Property property = getRandomProperty();

		intent.putExtra("propertyTitle", property.getTitle());
		intent.putExtra("propertyPrice", property.getPrice());
		intent.putExtra("propertyLocation", property.getCity());
		intent.putExtra("propertyDescription", property.getDescription());
		intent.putExtra("propertyPublicationDate", DateFormat.format("dd MMMM yyyy",  property.getDate()));
		intent.putExtra("propertySellerName", property.getSeller().getName());
		intent.putExtra("propertySellerMail", property.getSeller().getEmail());
		intent.putExtra("propertySellerNumber", property.getSeller().getPhone());

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

					Snackbar.make(findViewById(R.id.layout_main), responseBody.string(), Snackbar.LENGTH_LONG).show();

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
			Snackbar.make( findViewById(R.id.layout_main), message, Snackbar.LENGTH_LONG).show();
			return property;
		} catch (IOException e) {
			Log.i("JML", "Erreur I/O");
		}

		return null;
	}

}
