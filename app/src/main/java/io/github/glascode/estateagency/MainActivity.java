package io.github.glascode.estateagency;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.snackbar.Snackbar;
import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {

	private final String API_URL = "https://ensweb.users.info.unicaen.fr/android-estate/mock-api/dernieres.json";
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

		try {
			jsonPropertyListArray = new HttpRequestTask().execute(API_URL).get();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonPropertyListArray != null) {
			PropertyListFragment propertyListFragment = new PropertyListFragment();
			Bundle bundle = new Bundle();
			bundle.putString("json_property_list", jsonPropertyListArray.toString());
			propertyListFragment.setArguments(bundle);
			getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, propertyListFragment).commit();
		} else {
			displayMaterialSnackBar("jsonPropertyListArray is null");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.bottomappbar_menu, menu);

		return true;
	}

	private void displayMaterialSnackBar(String text) {
		int marginSide = 0;
		int marginBottom = 550;
		Snackbar snackbar = Snackbar.make(
				findViewById(R.id.layout_main),
				text,
				Snackbar.LENGTH_LONG
		);

		View snackbarView = snackbar.getView();
		CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbarView.getLayoutParams();

		params.setMargins(
				params.leftMargin + marginSide,
				params.topMargin,
				params.rightMargin + marginSide,
				params.bottomMargin + marginBottom
		);

		snackbarView.setLayoutParams(params);
		snackbar.show();
	}


}
