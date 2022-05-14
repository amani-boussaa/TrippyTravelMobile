package com.esprit.myapp.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.esprit.myapp.entities.Excursion;
import com.esprit.myapp.services.ServiceExcursion;

public class ModifierExcursionForm extends BaseForm{
    Form current;
    public ModifierExcursionForm(Resources res, Excursion excursion){
        super("Newsfeed", BoxLayout.y());//heritage de newsfeed et formulaire vertical
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Modifier Réservation excursion");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> {});

        TextField Libelle = new TextField(excursion.getLibelle(),"Libellé",20, TextField.ANY);
        TextField Description = new TextField(excursion.getDescription(),"Description",20, TextField.ANY);
        TextField Prix = new TextField(excursion.getPrix(),"Prix",20, TextField.ANY);
        TextField Programme = new TextField(excursion.getProgramme(),"Programme",20, TextField.ANY);
        TextField Duration = new TextField(excursion.getDuration(),"Durée",20, TextField.ANY);

        Libelle.setUIID("NewsTopLine");
        Description.setUIID("NewsTopLine");
        Prix.setUIID("NewsTopLine");
        Programme.setUIID("NewsTopLine");
        Duration.setUIID("NewsTopLine");

        Libelle.setSingleLineTextArea(true);
        Description.setSingleLineTextArea(true);

        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        //Event onclick btn modifier
        btnModifier.addPointerPressedListener(l->{
            excursion.setLibelle(Libelle.getText());
            excursion.setDescription(Description.getText());
            excursion.setPrix(Prix.getText());
            excursion.setProgramme(Programme.getText());
            excursion.setDuration(Duration.getText());


        //appel fonction liste excursion
        if (ServiceExcursion.getInstance().updateExcursion(excursion)){
            new ListExcursionForm(res).show();
        }

        });
        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e->{
            new ListExcursionForm(res).show();
        });

        Label l1 = new Label("");
        Label l2 = new Label("");
        Label l3 = new Label("");
        Label l4 = new Label("");
        Label l5 = new Label("");

        Container content = BoxLayout.encloseY(
                l1,l2,
                new FloatingHint(Libelle),
                createLineSeparator(),
                new FloatingHint(Description),
                createLineSeparator(), //create ligne separation
                new FloatingHint(Prix),
                createLineSeparator(), //create ligne separation
                new FloatingHint(Programme),
                createLineSeparator(), //create ligne separation

                new FloatingHint(Duration),
                createLineSeparator(), //create ligne separation

                l4,l5,
                btnModifier,
                btnAnnuler
        );
        add(content);
        show();
    }
}
