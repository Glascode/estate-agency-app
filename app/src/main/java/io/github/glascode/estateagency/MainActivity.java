package io.github.glascode.estateagency;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomappbar.BottomAppBar;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements PropertyListFragment.OnPropertySelectedListener {

	private JSONArray jsonPropertyListArray;
	private Menu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
		setSupportActionBar(bottomAppBar);

		bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BottomNavigationDrawerFragment bottomNavigationDrawerFragment = new BottomNavigationDrawerFragment();
				bottomNavigationDrawerFragment.show(getSupportFragmentManager(), bottomNavigationDrawerFragment.getTag());
			}
		});

		makeRequest("https://ensweb.users.info.unicaen.fr/android-estate/mock-api/dernieres.json");

		while (jsonPropertyListArray == null) {
			System.out.println("waiting...");
		}

		if (findViewById(R.id.fragment_container) != null) {
			if (savedInstanceState != null)
				return;

			Bundle bundle = new Bundle();
			bundle.putString("json_property_list", jsonPropertyListArray.toString());

			PropertyListFragment propertyListFragment = new PropertyListFragment();
			propertyListFragment.setArguments(bundle);

			getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, propertyListFragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.bottomappbar_menu, menu);

		return true;
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

	@Override
	public void onItemSelected(int position) {

	}
}