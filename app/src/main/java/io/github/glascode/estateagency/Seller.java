package io.github.glascode.estateagency;

import android.support.annotation.NonNull;

public class Seller {

	private String id;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;

	public Seller(String id, String nom, String prenom, String email, String telephone) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
	}

	public String getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getEmail() {
		return email;
	}

	public String getTelephone() {
		return telephone;
	}

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
