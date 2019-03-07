package io.github.glascode.estateagency;

import android.support.annotation.NonNull;

import java.util.List;

public class Property {

	private String id;
	private String titre;
	private String description;
	private int nbPieces;
	private List<String> caracteristiques;
	private int prix;
	private String ville;
	private String codePostal;
	private Seller vendeur;
	private List<String> images;
	private long date;

	public Property(String id, String titre, String description, int nbPieces,
					List<String> caracteristiques, int prix, String ville, String codePostal,
					Seller vendeur, List<String> images, long date) {
		this.id = id;
		this.titre = titre;
		this.description = description;
		this.nbPieces = nbPieces;
		this.caracteristiques = caracteristiques;
		this.prix = prix;
		this.ville = ville;
		this.codePostal = codePostal;
		this.vendeur = vendeur;
		this.images = images;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNbPieces() {
		return nbPieces;
	}

	public void setNbPieces(int nbPieces) {
		this.nbPieces = nbPieces;
	}

	public List<String> getCaracteristiques() {
		return caracteristiques;
	}

	public void addFeature(String feature) {
		caracteristiques.add(feature);
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public Seller getVendeur() {
		return vendeur;
	}

	public void setVendeur(Seller vendeur) {
		this.vendeur = vendeur;
	}

	public List<String> getImages() {
		return images;
	}

	public void addImageUrl(String imageUrl) {
		images.add(imageUrl);
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	@NonNull
	public String toString() {
		return "{\n\tid: " + id + ",\n" +
				"\ttitre: " + titre + ",\n" +
				"\tdescription: " + description + ",\n" +
				"\tnbPieces: " + nbPieces + "\n" +
				"\tcaracteristiques: " + caracteristiques + "\n" +
				"\tprix: " + prix + "\n" +
				"\tville: " + ville + "\n" +
				"\tcodePostal: " + codePostal + "\n" +
				"\tvendeur: " + vendeur + "\n" +
				"\tdate: " + date + "\n}\n";
	}
}
