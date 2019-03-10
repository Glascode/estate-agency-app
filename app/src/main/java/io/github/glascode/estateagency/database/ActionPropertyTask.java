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

		if (action.equals("insert")) {
			database.propertyDao().insertProperty(property);
			System.out.println("Adding " + property.getId());
		} else if (action.equals("remove")) {
			database.propertyDao().deleteProperty(property);
			System.out.println("Removing " + property.getId());
		} else if (action.equals("get")) {
			System.out.println("Getting " + property.getId());
			if (database.propertyDao().getProperty(property.getId()) != null)
				return database.propertyDao().getProperty(property.getId()).getId();
		}

		return null;
	}
}
