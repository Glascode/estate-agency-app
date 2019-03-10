package io.github.glascode.estateagency;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import io.github.glascode.estateagency.database.ListPropertyTask;
import io.github.glascode.estateagency.model.Property;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

	private JSONArray jsonPropertyListArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Objects.requireNonNull(getSupportActionBar()).hide();

		setContentView(R.layout.activity_main);

		if (checkConnectivity(this)) {

			/* Get properties list from the API */
			makeRequest("https://ensweb.users.info.unicaen.fr/android-estate/mock-api/dernieres.json");
		}
	}

	public void launchPropertyActivity(View view) throws JSONException {
		try {
			Random rand = new Random();
			int randomPos = rand.nextInt(jsonPropertyListArray.length());
			String jsonPropertyString = jsonPropertyListArray.get(randomPos).toString();

			Intent intent = new Intent(this, PropertyActivity.class);
			intent.putExtra("json_property", jsonPropertyString);

			startActivity(intent);
		} catch (NullPointerException e) {
			Snackbar.make(
					findViewById(R.id.layout_main),
					"Unable to retrieve a property",
					Snackbar.LENGTH_LONG
			).show();
		}
	}

	public void launchPropertyListActivity(View view) {
		try {
			Intent intent = new Intent(this, PropertyListActivity.class);
			intent.putExtra("json_property_list", jsonPropertyListArray.toString());

			startActivity(intent);
		} catch (NullPointerException e) {
			Snackbar.make(
					findViewById(R.id.layout_main),
					"Unable to retrieve the list of properties",
					Snackbar.LENGTH_LONG
			).show();
		}
	}

	public void launchProfileActivity(View view) {
		List<Property> savedPropertyList;
		try {
			savedPropertyList = new ListPropertyTask(getApplicationContext()).execute().get();

			if (savedPropertyList != null && !savedPropertyList.isEmpty()) {
				Moshi moshi = new Moshi.Builder().build();
				JsonAdapter<List<Property>> adapter = moshi.adapter(Types.newParameterizedType(List.class, Property.class));

				Intent intent = new Intent(this, PropertyListActivity.class);
				intent.putExtra("json_property_list", adapter.toJson(savedPropertyList));

				startActivity(intent);
			}
		} catch (ExecutionException | InterruptedException e) {
			Snackbar.make(findViewById(R.id.layout_main), "Unable to retrieve the list of properties", Snackbar.LENGTH_LONG).show();
		}
	}

	private boolean checkConnectivity(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

		if (activeNetwork != null && activeNetwork.isConnected()) {

			/* Connected to Internet */
			if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {

				/* Connected to Wi-Fi */
				Snackbar.make(
						findViewById(R.id.layout_main),
						"Connected to Wi-Fi",
						Snackbar.LENGTH_LONG
				).show();
			} else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

				/* Connected to the mobile provider's data plan */
				Snackbar.make(
						findViewById(R.id.layout_main),
						"Connected to Data",
						Snackbar.LENGTH_LONG
				).show();
			}
			return true;
		}

		/* Not connected */
		Snackbar.make(findViewById(R.id.layout_main), "Not connected", Snackbar.LENGTH_LONG).show();
		return false;
	}

	private void makeRequest(String url) {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url(url).build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(@NonNull Call call, @NonNull IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

				try (ResponseBody responseBody = response.body()) {
					if (!response.isSuccessful()) {
						throw new IOException("Unexpected code " + response);
					}

					JSONObject jsonObject;

					if (responseBody != null) {
						jsonObject = new JSONObject(responseBody.string());
						jsonPropertyListArray = new JSONArray(jsonObject.getString("response"));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
