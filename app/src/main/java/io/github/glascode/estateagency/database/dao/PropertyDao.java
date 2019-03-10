package io.github.glascode.estateagency.database.dao;

import android.arch.persistence.room.*;
import io.github.glascode.estateagency.model.Property;

@Dao
public interface PropertyDao {

	@Query("SELECT * FROM Property WHERE id = :propertyId")
	Property getProperty(String propertyId);

	@Insert
	long insertProperty(Property property);

	@Update
	int updateProperty(Property property);

	@Delete
	int deleteProperty(Property property);
}
