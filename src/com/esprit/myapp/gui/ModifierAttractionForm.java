package com.esprit.myapp.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.esprit.myapp.entities.Attraction;
import com.esprit.myapp.services.ServiceAttraction;

public class ModifierAttractionForm extends BaseForm{
    Form current;
    public ModifierAttractionForm(Resources res, Attraction attraction){
        super("Newsfeed", BoxLayout.y());//heritage de newsfeed et formulaire vertical
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Réservation attraction");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> {});

        TextField libelle = new TextField(attraction.getLibelle(),"libelle",20, TextField.ANY);
        TextField localisation = new TextField(attraction.getLocalisation(),"localisation",20, TextField.ANY);
        TextField horraire = new TextField(attraction.getHorraire(),"horraire",20, TextField.ANY);


        libelle.setUIID("NewsTopLine");
        localisation.setUIID("NewsTopLine");
        horraire.setUIID("NewsTopLine");

        libelle.setSingleLineTextArea(true);
        localisation.setSingleLineTextArea(true);
        horraire.setSingleLineTextArea(true);


        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        //Event onclick btn modifier
        btnModifier.addPointerPressedListener(l->{
            attraction.setLibelle(libelle.getText());
            attraction.setLocalisation(localisation.getText());
            attraction.setHorraire(horraire.getText());



            //appel fonction modifier excursion
            if (ServiceAttraction.getInstance().updateAttraction(attraction)){
                new ListAttractionForm(res).show();
            }

        });
        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e->{
            new ListAttractionForm(res).show();
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
                new FloatingHint(localisation),
                createLineSeparator(),
                new FloatingHint(horraire),
                createLineSeparator(),
                l4,l5,
                btnModifier,
                btnAnnuler
        );
        add(content);
        show();
    }
}