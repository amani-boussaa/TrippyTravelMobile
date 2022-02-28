package com.esprit.myapp.entities;

public class Excursion {
    private int id;
    private String libelle,duration,programme,description,ville;
    private Float prix;
    private String excursioncategorie;

    public Excursion() {
    }

    public Excursion(int id, String libelle, String duration, String programme, String description, String ville, Float prix, String excursioncategorie) {
        this.id = id;
        this.libelle = libelle;
        this.duration = duration;
        this.programme = programme;
        this.description = description;
        this.ville = ville;
        this.prix = prix;
        this.excursioncategorie = excursioncategorie;
    }

    public Excursion(String libelle, String duration, String programme, String description, String ville, Float prix, String excursioncategorie) {
        this.libelle = libelle;
        this.duration = duration;
        this.programme = programme;
        this.description = description;
        this.ville = ville;
        this.prix = prix;
        this.excursioncategorie = excursioncategorie;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public String getExcursioncategorie() {
        return excursioncategorie;
    }

    public void setExcursioncategorie(String excursioncategorie) {
        this.excursioncategorie = excursioncategorie;
    }

    @Override
    public String toString() {
        return "Excursion{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", duration='" + duration + '\'' +
                ", programme='" + programme + '\'' +
                ", description='" + description + '\'' +
                ", ville='" + ville + '\'' +
                ", prix=" + prix +
                ", excursioncategorie=" + excursioncategorie +
                '}';
    }
}
