package com.esprit.myapp.gui;


import com.codename1.components.FloatingHint;
import com.codename1.ui.*;
import com.codename1.ui.util.Resources;
import com.esprit.myapp.entities.Hotel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.Form;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.myapp.services.ServiceHotel;


public class UpdateHotelForm extends BaseForm{

    Form current;
    public UpdateHotelForm(Resources res , Hotel p){
        super ("Newsfeed",BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("modifier Hotel");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> { });

        TextField libelle = new TextField(p.getLibelle(),"libelle",20,TextField.ANY);
        TextField localisation = new TextField(p.getLocalisation(),"localisation",20,TextField.ANY);
        TextField nbetoile= new TextField(Integer.toString(p.getNbetoile()));
        TextField nbchdispo= new TextField(Integer.toString(p.getNbchdispo()));
        TextField descriptionHotel = new TextField(p.getDescription_hotel(),"descriptionHotel",20,TextField.ANY);


        libelle.setSingleLineTextArea(true);
        localisation.setSingleLineTextArea(true);
        nbetoile.setSingleLineTextArea(true);
        nbchdispo.setSingleLineTextArea(true);
        descriptionHotel.setSingleLineTextArea(true);


        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        btnModifier.addPointerPressedListener(l -> {

            p.setLibelle(libelle.getText());
            p.setLocalisation(localisation.getText());

            p.setDescription_hotel(descriptionHotel.getText());



            // appel fn modif

            if (ServiceHotel.getInstance().modifierHotel(p)){

                new ListHotelForm(res).show();


            }
        });

        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e -> {

            new ListHotelForm(res).show();


        });


        Label a = new Label("");
        Label b = new Label("");
        Label c = new Label("");
        Label d = new Label("");
        Label e = new Label("");
        Label f = new Label ();


        Container content = BoxLayout.encloseY(


                f,a,b,new FloatingHint(libelle),
                createLineSeparator(),new FloatingHint (localisation),
                createLineSeparator(),new FloatingHint (nbetoile),
                createLineSeparator(),new FloatingHint (nbchdispo),
                createLineSeparator(),new FloatingHint (descriptionHotel),

                btnModifier,
                btnAnnuler


        );

        add(content);
        show();




    }


    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
