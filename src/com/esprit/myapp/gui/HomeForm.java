package com.esprit.myapp.gui;

import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Button;
import com.codename1.ui.Label;


public class HomeForm extends Form {
    Form current;
    public HomeForm() {
        current = this;
        setTitle("Accueil");
        setLayout(BoxLayout.y());
        add(new Label("Choisir une option"));
        Button btnAddExcursion = new Button("Ajouter excursion");
        Button btnListExcursions = new Button("Liste des excursions");

        btnAddExcursion.addActionListener(e -> new AddExcursionForm(current).show());
        btnListExcursions.addActionListener(e-> new ListExcursionsForm(current).show());
        addAll(btnAddExcursion,btnListExcursions);
    }
}
