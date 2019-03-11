package io.github.glascode.estateagency;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Objects;

@SuppressWarnings("WeakerAccess")
public class ProfileFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile_create, container, false);

		BottomAppBar bottomAppBar = Objects.requireNonNull(Objects.requireNonNull(getActivity())).findViewById(R.id.bottom_app_bar);
		bottomAppBar.replaceMenu(R.menu.menu_profile_bottomappbar);

		ExtendedFloatingActionButton extendedFloatingActionButton = getActivity().findViewById(R.id.fab);
		extendedFloatingActionButton.setIcon(null);
		extendedFloatingActionButton.setText(R.string.action_profile_validate);

		return view;
	}

}
