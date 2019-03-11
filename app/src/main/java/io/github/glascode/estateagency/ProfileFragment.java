package io.github.glascode.estateagency;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

@SuppressWarnings("WeakerAccess")
public class ProfileFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view;

		BottomAppBar bottomAppBar = Objects.requireNonNull(Objects.requireNonNull(getActivity())).findViewById(R.id.bottom_app_bar);
		bottomAppBar.replaceMenu(R.menu.menu_profile_bottomappbar);

		ExtendedFloatingActionButton extendedFloatingActionButton = getActivity().findViewById(R.id.fab);
		extendedFloatingActionButton.setIcon(null);
		extendedFloatingActionButton.extend();
		extendedFloatingActionButton.show();

		if(((MainActivity) getActivity()).checkProfile()) {
			view = inflater.inflate(R.layout.fragment_profile_infos, container, false);
			extendedFloatingActionButton.setText(R.string.action_profile_edit);

			TextView profileLastnameText = view.findViewById(R.id.text_profile_infos_lastname_value);
			TextView profileFirstnameText = view.findViewById(R.id.text_profile_infos_firstname_value);
			TextView profileEmailText = view.findViewById(R.id.text_profile_infos_email_value);
			TextView profilePhoneText = view.findViewById(R.id.text_profile_infos_phone_value);

			SharedPreferences profilePreferences = ((MainActivity) getActivity()).getProfilePreferences();
			profileLastnameText.setText(profilePreferences.getString("lastname", null));
			profileFirstnameText.setText(profilePreferences.getString("firstname", null));
			profileEmailText.setText(profilePreferences.getString("email", null));
			profilePhoneText.setText(profilePreferences.getString("phone", null));
		} else {
			view = inflater.inflate(R.layout.fragment_profile_create, container, false);
			extendedFloatingActionButton.setText(R.string.action_profile_validate);
		}

		extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (((MainActivity) getActivity()).checkProfile()){

				} else {
					TextInputEditText profileLastnameInput = getActivity().findViewById(R.id.input_edit_profile_create_lastname);
					TextInputEditText profileFirstnameInput = getActivity().findViewById(R.id.input_edit_profile_create_firstname);
					TextInputEditText profileEmailInput = getActivity().findViewById(R.id.input_edit_profile_creation_email);
					TextInputEditText profilePhoneInput = getActivity().findViewById(R.id.input_edit_profile_creation_phone);

					if (profileLastnameInput.getText().length() != 0 && profileFirstnameInput.getText().length() != 0 && profileEmailInput.getText().length() != 0 && profilePhoneInput.getText().length() != 0) {
						((MainActivity) getActivity()).updateProfile(profileLastnameInput.getText().toString(), profileFirstnameInput.getText().toString(), profileEmailInput.getText().toString(), profilePhoneInput.getText().toString());
					}

					((MainActivity) getActivity()).showProfile();
				}
			}
		});

		return view;
	}

}
