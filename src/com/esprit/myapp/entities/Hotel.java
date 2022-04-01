package com.esprit.myapp.entities;

public class Hotel {
    private int id;
    private String libelle;
    private String localisation;
    private int nbetoile;
    private int nbchdispo;
    private String description_hotel;


    public Hotel() {
        this.id = id;
        this.libelle = libelle;
        this.localisation = localisation;
        this.nbetoile = nbetoile;
        this.nbchdispo = nbchdispo;
        this.description_hotel = description_hotel;

    }

    public Hotel(String libelle, String localisation, int nbetoile, int nbchdispo, String description_hotel) {
        this.libelle = libelle;
        this.localisation = localisation;
        this.nbetoile = nbetoile;
        this.nbchdispo = nbchdispo;
        this.description_hotel = description_hotel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getNbetoile() {
        return nbetoile;
    }

    public void setNbetoile(int nbetoile) {
        this.nbetoile = nbetoile;
    }

    public int getNbchdispo() {
        return nbchdispo;
    }

    public void setNbchdispo(int nbchdispo) {
        this.nbchdispo = nbchdispo;
    }

    public String getDescription_hotel() {
        return description_hotel;
    }

    public void setDescription_hotel(String description_hotel) {
        this.description_hotel = description_hotel;
    }
}
