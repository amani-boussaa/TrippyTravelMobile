/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.myapp.gui;
import com.codename1.components.FloatingHint;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.esprit.myapp.entities.Maisonshotes;
import com.esprit.myapp.services.ServicesMaisonshotes;

/**
 *
 * @author samar
 */
public class ModifierMaisonshotesForm extends BaseForm{
    Form current;
    public ModifierMaisonshotesForm(Resources res, Maisonshotes Maisonshotes){
        super("Newsfeed", BoxLayout.y());//heritage de newsfeed et formulaire vertical
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Réservation excursion");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        TextField libelle = new TextField(Maisonshotes.getLibelle(),"libelle",20, TextField.ANY);
        TextField capacite= new TextField(Integer.toString(Maisonshotes.getCapacite()));
        TextField localisation = new TextField(Maisonshotes.getLocalisation(),"localisation",20, TextField.ANY);
        TextField proprietaire = new TextField(Maisonshotes.getProprietaire(),"proprietaire",20, TextField.ANY);
        TextField nbrChambres= new TextField(Integer.toString(Maisonshotes.getNbrChambres()));
        TextField type_maison = new TextField(Maisonshotes.getTypeMaison(),"type_maison",20, TextField.ANY);

        libelle.setUIID("NewsTopLine");
        capacite.setUIID("NewsTopLine");
        localisation.setUIID("NewsTopLine");
        proprietaire.setUIID("NewsTopLine");
        nbrChambres.setUIID("NewsTopLine");
        type_maison.setUIID("NewsTopLine");

        libelle.setSingleLineTextArea(true);
        capacite.setSingleLineTextArea(true);
        proprietaire.setSingleLineTextArea(true);
        nbrChambres.setSingleLineTextArea(true);
        type_maison.setSingleLineTextArea(true);



        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        //Event onclick btn modifier
        btnModifier.addPointerPressedListener(l->{
            Maisonshotes.setLibelle(libelle.getText());
            Maisonshotes.setLocalisation(localisation.getText());
            Maisonshotes.setCapacite(0);
            Maisonshotes.setProprietaire("0");
            Maisonshotes.setNbrChambres(0);
            Maisonshotes.setTypeMaison("1");


            //appel fonction modifier excursion
            if (ServicesMaisonshotes.getInstance().ModifierMaisonshotes(Maisonshotes)){
                new ListMaisonshotesForm(res).show();
            }

        });
        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e->{
            new ListMaisonshotesForm(res).show();
        });

        Label l1 = new Label("");
        Label l2 = new Label("");
        Label l3 = new Label("");
        Label l4 = new Label("");
        Label l5 = new Label("");

        Container content = BoxLayout.encloseY(
                l1,l2,
                new FloatingHint(libelle),
                createLineSeparator(),

                l4,l5,
                btnModifier,
                btnAnnuler
        );
        add(content);
        show();
    }




}
