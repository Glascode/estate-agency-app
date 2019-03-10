package io.github.glascode.estateagency.database;

import android.content.Context;
import android.os.AsyncTask;
import io.github.glascode.estateagency.model.Property;

public class ActionPropertyTask extends AsyncTask<Void, Void, String> {

	private Context context;
	private Property property;
	private String action;

	public ActionPropertyTask(Context context, Property property, String action) {
		this.context = context;
		this.property = property;
		this.action = action;
	}

	@Override
	protected String doInBackground(Void... voids) {
		Database database = Database.getInstance(context);

		switch (action) {
			case "insert":
				database.propertyDao().insertProperty(property);
				break;
			case "remove":
				database.propertyDao().deleteProperty(property);
				break;
			case "get":
				if (database.propertyDao().getProperty(property.getId()) != null)
					return database.propertyDao().getProperty(property.getId()).getId();
				break;
		}

		return null;
	}
}
