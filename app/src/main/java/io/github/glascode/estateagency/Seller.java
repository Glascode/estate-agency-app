package io.github.glascode.estateagency;

import android.support.annotation.NonNull;

public class Seller {

	private String id;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;

	/**
	 * Constructs a new {@code Seller}.
	 * <p>
	 * Note: the attributes of the Property had to be written in french because
	 * of the attributes of the JSON response it depends on.
	 *
	 * @param id the id of the Property
	 * @param nom the name of the Property
	 * @param prenom the surname of the Property
	 * @param email the email address of the Property
	 * @param telephone the phone number of the Property
	 */
	public Seller(String id, String nom, String prenom, String email, String telephone) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
	}

	/**
	 * Returns the id of this Seller
	 *
	 * @return the id of this Seller
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the name of this Seller
	 *
	 * @return the name of this Seller
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Returns the surname of this Seller
	 *
	 * @return the surname of this Seller
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Returns the email address of this Seller
	 *
	 * @return the email address of this Seller
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the phone number of this Seller
	 *
	 * @return the phone number of this Seller
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * Returns the string representation of this Seller
	 *
	 * @return the string representation of this Seller
	 */
	@NonNull
	@Override
	public String toString() {
		return "{\n\tid: " + id + ",\n" +
				"\tnom: " + nom + ",\n" +
				"\tprenom: " + prenom + ",\n" +
				"\temail: " + email + "\n" +
				"\ttelephone: " + telephone + "\n}\n";
	}
}
