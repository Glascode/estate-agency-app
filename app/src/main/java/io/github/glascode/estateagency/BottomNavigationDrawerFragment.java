package io.github.glascode.estateagency;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment {
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_bottomsheet, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		final NavigationView navigationView = Objects.requireNonNull(Objects.requireNonNull(getView())).findViewById(R.id.navigation_view);
		navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				switch (item.getItemId()) {
					case R.id.nav_profile:
						((MainActivity) Objects.requireNonNull(Objects.requireNonNull(getActivity()))).showProfile();

						dismiss();
						break;
					case R.id.nav_saved_properties:
						((MainActivity) Objects.requireNonNull(Objects.requireNonNull(getActivity()))).showSavedPropertyList();

						dismiss();
						break;
				}

				return true;
			}
		});
	}
}
