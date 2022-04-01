package com.esprit.myapp.gui;


import com.codename1.components.FloatingHint;
import com.codename1.ui.*;
import com.codename1.ui.util.Resources;
import com.esprit.myapp.entities.Chambre;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.Form;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.myapp.services.ServiceChambre;


public class UpdateChambreForm extends BaseForm{

    Form current;
    public UpdateChambreForm(Resources res , Chambre p){
        super ("Newsfeed",BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("modifier Chambre");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> { });

        TextField typechambre = new TextField(p.gettypechambre(),"typechambre",20,TextField.ANY);
        TextField prix= new TextField(Integer.toString(p.getprix()));
        TextField description_chambre = new TextField(p.getdescription_chambre(),"description_chambre",20,TextField.ANY);


        typechambre.setSingleLineTextArea(true);
        prix.setSingleLineTextArea(true);
        description_chambre.setSingleLineTextArea(true);


        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        btnModifier.addPointerPressedListener(l -> {

            p.settypechambre(typechambre.getText());


            p.setdescription_chambre(description_chambre.getText());



            // appel fn modif

            if (ServiceChambre.getInstance().modifierChambre(p)){

                new ListChambreForm(res).show();


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


                f,a,b,new FloatingHint(typechambre),
                createLineSeparator(),new FloatingHint (prix),
                createLineSeparator(),new FloatingHint (description_chambre),

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
