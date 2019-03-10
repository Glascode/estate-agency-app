package io.github.glascode.estateagency.database;

import android.content.Context;
import android.os.AsyncTask;
import io.github.glascode.estateagency.model.Property;

import java.util.List;

public class ListPropertyTask extends AsyncTask<Void, Void, List<Property>> {

	private Context context;

	public ListPropertyTask(Context context) {
		this.context = context;
	}

	@Override
	protected List<Property> doInBackground(Void... voids) {
		return Database.getInstance(context).propertyDao().getPropertyList();
	}
}
