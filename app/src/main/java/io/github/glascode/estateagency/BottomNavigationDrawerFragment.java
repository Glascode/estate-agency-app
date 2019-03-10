package io.github.glascode.estateagency;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment {
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_bottomsheet, container, false);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.nav1:
				Toast.makeText(getContext(), "Bottom Navigation Drawer menu item 1", Toast.LENGTH_SHORT).show();
				break;
			case R.id.nav2:
				Toast.makeText(getContext(), "Bottom Navigation Drawer menu item 2", Toast.LENGTH_SHORT).show();
				break;
			case R.id.nav3:
				Toast.makeText(getContext(), "Bottom Navigation Drawer menu item 3", Toast.LENGTH_SHORT).show();
				break;
		}

		return true;
	}
}
