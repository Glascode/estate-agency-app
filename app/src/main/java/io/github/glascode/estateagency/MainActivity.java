package io.github.glascode.estateagency;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import io.github.glascode.estateagency.database.GetPropertyListTask;
import io.github.glascode.estateagency.database.InsertPropertyTask;
import io.github.glascode.estateagency.database.RemovePropertyTask;
import io.github.glascode.estateagency.model.Property;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements PropertyListFragment.OnPropertySelectedListener {

	private volatile JSONArray jsonPropertyListArray;
	private List<Property> savedPropertyList;
	private Property property;
	private BottomAppBar bottomAppBar;
	private ExtendedFloatingActionButton extendedFloatingActionButton;

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		bottomAppBar = findViewById(R.id.bottom_app_bar);
		setSupportActionBar(bottomAppBar);

		bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BottomNavigationDrawerFragment bottomNavigationDrawerFragment = new BottomNavigationDrawerFragment();
				bottomNavigationDrawerFragment.show(getSupportFragmentManager(), bottomNavigationDrawerFragment.getTag());
			}
		});

		extendedFloatingActionButton = findViewById(R.id.fab);

		if (checkConnectivity(getApplicationContext()))
			makeRequest("https://ensweb.users.info.unicaen.fr/android-estate/mock-api/dernieres.json");
		else
			jsonPropertyListArray = new JSONArray();

		while (true) if (jsonPropertyListArray != null) break;

		if (findViewById(R.id.fragment_container) != null)
			if (savedInstanceState != null)
				return;

		showPropertyList(jsonPropertyListArray.toString());
	}

	private boolean checkConnectivity(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

		return activeNetwork != null && activeNetwork.isConnected();
	}

	private void showPropertyList(String json) {
		Bundle bundle = new Bundle();
		bundle.putString("json_property_list", json);

		PropertyListFragment propertyListFragment = new PropertyListFragment();
		propertyListFragment.setArguments(bundle);

		if (findViewById(R.id.fragment_container) == null)
			getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, propertyListFragment).commit();
		else
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, propertyListFragment).commit();

		extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO: Integrate the creation of a property
				Toast.makeText(getApplicationContext(), "Creation of a property", Toast.LENGTH_LONG).show();
			}
		});
	}

	public void showSavedPropertyList() {
		try {
			savedPropertyList = new GetPropertyListTask(getApplicationContext()).execute().get();

			if (savedPropertyList != null) {
				Moshi moshi = new Moshi.Builder().build();

				Type type = Types.newParameterizedType(List.class, Property.class);
				JsonAdapter<List<Property>> adapter = moshi.adapter(type);

				showPropertyList(adapter.toJson(savedPropertyList));

				editNavigationMenu();
			}
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void showProfile() {
		ProfileFragment profileFragment = new ProfileFragment();

		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();

		extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO: Integrate the creation of an user
				Toast.makeText(getApplicationContext(), "Validation", Toast.LENGTH_LONG).show();
			}
		});

		editNavigationMenu();
	}

	private void editNavigationMenu() {
		bottomAppBar.setNavigationIcon(R.drawable.ic_arrow_back);
		bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showPropertyList(jsonPropertyListArray.toString());

				bottomAppBar.setNavigationIcon(R.drawable.ic_menu);
				bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						BottomNavigationDrawerFragment bottomNavigationDrawerFragment = new BottomNavigationDrawerFragment();
						bottomNavigationDrawerFragment.show(getSupportFragmentManager(), bottomNavigationDrawerFragment.getTag());
					}
				});
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main_bottomappbar, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.app_bar_call:
				Intent intent_call = new Intent(Intent.ACTION_DIAL);
				intent_call.setData(Uri.parse("tel:" + property.getVendeur().getTelephone()));

				startActivity(intent_call);
				break;
			case R.id.app_bar_mail:
				Intent intent_mail = new Intent(Intent.ACTION_VIEW);
				intent_mail.setData(Uri.parse("mailto:" + property.getVendeur().getEmail()));
				startActivity(intent_mail);

				break;
			case R.id.app_bar_save:
				if (!item.isChecked()) {
					new InsertPropertyTask(getApplicationContext(), property).execute();
					item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_colored));
					item.setChecked(true);
				} else if (item.isChecked()) {
					new RemovePropertyTask(getApplicationContext(), property).execute();
					item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));
					item.setChecked(false);
				}
				break;
		}

		return true;
	}

	public void setProperty(Property property) {
		this.property = property;
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
		PropertyFragment propertyFragment = new PropertyFragment();

		Bundle bundle = new Bundle();
		if (checkConnectivity(getApplicationContext()))
			try {
				bundle.putString("json_property", jsonPropertyListArray.get(position).toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		else {
			Moshi moshi = new Moshi.Builder().build();

			Type type = Types.newParameterizedType(Property.class);
			JsonAdapter<Property> adapter = moshi.adapter(type);

			bundle.putString("json_property", adapter.toJson(savedPropertyList.get(position)));
		}

		propertyFragment.setArguments(bundle);

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_container, propertyFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
}