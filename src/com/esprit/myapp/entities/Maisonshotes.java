/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.myapp.entities;

/**
 *
 * @author samar
 */
public class Maisonshotes {
     private int id,capacite,nbrChambres;
    private String libelle,localisation,proprietaire;
    private Float prix;
    private String typeMaison;

    public Maisonshotes() {
    }
    
    
    

  

    public Maisonshotes(String libelle, String localisation, String proprietaire, Float prix, String typeMaison) {
        this.libelle = libelle;
        this.localisation = localisation;
        this.proprietaire = proprietaire;
        this.prix = prix;
        this.typeMaison = typeMaison;
    }

 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public int getNbrChambres() {
        return nbrChambres;
    }

    public void setNbrChambres(int nbrChambres) {
        this.nbrChambres = nbrChambres;
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

    public String getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(String proprietaire) {
        this.proprietaire = proprietaire;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public String getTypeMaison() {
        return typeMaison;
    }

    public void setTypeMaison(String typeMaison) {
        this.typeMaison = typeMaison;
    }
    
        
    
}
