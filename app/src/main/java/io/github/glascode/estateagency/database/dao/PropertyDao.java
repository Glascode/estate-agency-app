package io.github.glascode.estateagency.database.dao;

import android.arch.persistence.room.*;
import io.github.glascode.estateagency.model.Property;

import java.util.List;

@Dao
public interface PropertyDao {

	@Query("SELECT * FROM Property WHERE id = :propertyId")
	Property getProperty(String propertyId);

	@Query("SELECT * FROM Property")
	List<Property> getPropertyList();

	@Insert
	void insertProperty(Property property);

	@Update
	void updateProperty(Property property);

	@Delete
	void removeProperty(Property property);
}
