package com.esprit.myapp.gui;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.myapp.entities.Excursion;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.esprit.myapp.services.ServiceExcursion;

public class AddExcursionForm extends Form {
    public AddExcursionForm(Form previous) {
        setTitle("Ajouter Excursion");
        setLayout(BoxLayout.y());
        TextField tfLibelle = new TextField("","libelle");
        TextField tfDescription = new TextField("","description");
        TextField tfProgramme = new TextField("","programme");
        TextField tfDuration = new TextField("","duration");
        TextField tfVille = new TextField("","ville");
        TextField tfPrix = new TextField("","prix");
        TextField tfExcursioncategorie = new TextField("","excursioncategorie");
        Button btnValider = new Button("Ajouter excursion");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((tfDescription.getText().length()==0) || (tfDuration.getText().length()==0) || (tfLibelle.getText().length()==0) || (tfPrix.getText().length()==0) || (tfProgramme.getText().length()==0) || (tfVille.getText().length()==0))
                    Dialog.show("Alert","Remplissez tous les champs svp",new Command("OK"));
                else {
                    try {
                        Excursion excursion = new Excursion(tfLibelle.getText(),tfDuration.getText(),tfProgramme.getText(),tfDescription.getText(),tfVille.getText(),Float.parseFloat(tfPrix.getText()),tfExcursioncategorie.getText());
                        if (ServiceExcursion.getInstance().addExcursion(excursion))
                            Dialog.show("Success","Connection Accepted",new Command("ok"));
                        else
                            Dialog.show("Error","Server Error",new Command("ok"));
                    }catch (Exception exception){
                        Dialog.show("ERROR",exception.getMessage(),new Command("OK"));
                    }
                }
            }
        });
        addAll(tfLibelle,tfDuration,tfDescription,tfProgramme,tfVille,tfPrix,tfExcursioncategorie,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,e->previous.showBack());
    }
}
