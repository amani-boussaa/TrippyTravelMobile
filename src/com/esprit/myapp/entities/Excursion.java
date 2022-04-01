package com.esprit.myapp.entities;

public class Excursion {
    private int id;
    private String libelle,duration,programme,description,ville,image;
    private String prix;
    private String excursioncategorie_id;
    private ExcursionComments[] comments;

    public Excursion() {
    }

//    public Excursion(String libelle, String duration, String programme, String description, String ville, String prix, String excursioncategorie_id) {
//        this.libelle = libelle;
//        this.duration = duration;
//        this.programme = programme;
//        this.description = description;
//        this.ville = ville;
//        this.prix = prix;
//        this.excursioncategorie_id = excursioncategorie_id;
//    }


    public Excursion(String libelle, String duration, String programme, String description, String ville, String image, String prix, String excursioncategorie_id) {
        this.libelle = libelle;
        this.duration = duration;
        this.programme = programme;
        this.description = description;
        this.ville = ville;
        this.image = image;
        this.prix = prix;
        this.excursioncategorie_id = excursioncategorie_id;
    }

    public void setComments(ExcursionComments[] comments) {
        this.comments = comments;
    }

    public String getImage() {
        return image;
    }

    public ExcursionComments[] getComments() {
        return comments;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getExcursioncategorie_id() {
        return excursioncategorie_id;
    }

    public void setExcursioncategorie_id(String excursioncategorie_id) {
        this.excursioncategorie_id = excursioncategorie_id;
    }
}