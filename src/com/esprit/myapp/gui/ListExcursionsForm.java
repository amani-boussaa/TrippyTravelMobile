package com.esprit.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.esprit.myapp.services.ServiceExcursion;

public class ListExcursionsForm extends Form {
    public ListExcursionsForm(Form previous) {
        setTitle("Liste des excursions");
        SpanLabel sp = new SpanLabel();
        sp.setText(ServiceExcursion.getInstance().getAllExcursion().toString());
        add(sp);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,e->previous.showBack());
    }
}
