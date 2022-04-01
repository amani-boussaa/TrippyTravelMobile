package com.esprit.myapp.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.esprit.myapp.entities.Article;
import com.esprit.myapp.services.ServiceArticle;

public class ModifierArticleForm extends BaseForm{
    Form current;
    public ModifierArticleForm(Resources res, Article article){
        super("Newsfeed", BoxLayout.y());//heritage de newsfeed et formulaire vertical
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Réservation excursion");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        TextField Title = new TextField(article.getTitle(),"title",20, TextField.ANY);
        TextField Content = new TextField(article.getContent(),"content",20, TextField.ANY);

        Title.setUIID("NewsTopLine");
        Content.setUIID("NewsTopLine");

        Title.setSingleLineTextArea(true);
        Content.setSingleLineTextArea(true);

        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        //Event onclick btn modifier
        btnModifier.addPointerPressedListener(l->{
            article.setTitle(Title.getText());
            article.setContent(Content.getText());


            //appel fonction modifier excursion
            if (ServiceArticle.getInstance().updateArticle(article)){
                new ListArticlesForm(res).show();
            }

        });
        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e->{
            new ListArticlesForm(res).show();
        });

        Label l1 = new Label("");
        Label l2 = new Label("");
        Label l3 = new Label("");
        Label l4 = new Label("");
        Label l5 = new Label("");

        Container content = BoxLayout.encloseY(
                l1,l2,
                new FloatingHint(Title),
                createLineSeparator(),
                new FloatingHint(Content),
                createLineSeparator(), //create ligne separation
                l4,l5,
                btnModifier,
                btnAnnuler
        );
        add(content);
        show();
    }
}