    package com.esprit.myapp.entities;

public class Attraction {
    private int id, prix;
    private String libelle,localisation,horraire, image;

    public Attraction() {
    }

    
    
    public Attraction(int id, String libelle, String localisation, String horraire, int prix, String image) {
        this.id = id;
        this.libelle = libelle;
        this.localisation = localisation;
        this.horraire = horraire;
        this.prix = prix;
        this.image = image;
    }

    public Attraction(String libelle, String localisation, String horraire, int prix, String image) {
        this.libelle = libelle;
        this.localisation = localisation;
        this.horraire = horraire;
        this.prix = prix;
        this.image = image;
    }
    
        public Attraction(String libelle, String localisation, String horraire, String image) {
        this.libelle = libelle;
        this.localisation = localisation;
        this.horraire = horraire;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getHorraire() {
        return horraire;
    }

    public void setHorraire(String horraire) {
        this.horraire = horraire;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    
}
 